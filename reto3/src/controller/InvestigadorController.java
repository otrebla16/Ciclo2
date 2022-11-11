package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Iterator;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Investigador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Alberto Pérez
 * 
 * Clase que contiene el CONTROLADOR de la entidad Investigador
 *
 */
public class InvestigadorController {
	private Investigador iv;
	
	/**
	 * Metodo Getter del modelo a controlar
	 */
	public Investigador getIv() {
		return iv;
	}

	/**
	 * Metodo Setter del modelo a controlar
	 * 
	 * @param iv modelo del investigador
	 */
	public void setIv(Investigador iv) {
		this.iv = iv;
	}
	
	/**
	 * Metodo Setter del modelo a controlar
	 * 
	 * @param modelo modelo del investigador
	 */
	public InvestigadorController(Investigador modelo) {
		this.setIv(modelo);
	}
	
	/**
	 * Metodo que extrae la información de un investigador desde SCOPUS y lo envía a la base de datos de MySQL
	 * 
	 */
	public Investigador[] fillData() throws IOException {
		Investigador investigadores[] = new Investigador[25]; //Arreglo de datos recuperados desde SCOPUS
		
		URL url = new URL("https://api.elsevier.com/content/search/scopus?query=AU-ID(35227147500)"); //URL del API de Scopus Search
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); //Establecemos la conexión con el API
		con.setRequestMethod("GET"); //Definimos el método del request a GET
		con.setRequestProperty("Accept", "application/json"); //Le indicamos al API que nos regrese un JSON
		con.setRequestProperty("X-ELS-APIKey", "c7f561bbf42783c2becfd919cc8d27c2");	//Establecemos la API Key
		con.setDoOutput(true); //Le indicamos que leeremos resultados.
		con.setConnectTimeout(10000); //Establecemos el tiempo de expiración para la conexión
		con.setReadTimeout(10000); //Establecemos el tiempo de expiración para la lectura de datos
		
		int status = con.getResponseCode(); // Revisamos el estado de la conexión
		
		//System.out.println("Status: " + status);
		
		//En caso de que el estatus que nos regresa sea de error, mostramos los detalles del error
		if (status > 299) {
		    BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			
		    System.out.println("Error: " + content);
		    
		//En caso de que la consulta sea exitosa, extraemos los datos y los enviamos a MySQL
		} else {
			JSONParser jsonParser = new JSONParser(); //Preparamos el jsonParser
			JSONArray resultados = new JSONArray(); //Preparamos el arreglo para leer los resultados
			
			try(BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) con.getContent()))) { //recuperamos los resultados del JSON desde el API
				Object obj = jsonParser.parse(reader); //Realizamos el parsing del JSON recuperado
				
				JSONObject jsonObj = (JSONObject) obj; //Convertimos los resultados en un objeto JSON
				
				//System.out.println("JSON: " + jsonObj);
				
				JSONObject searchResults = (JSONObject) jsonObj.get("search-results"); //Obtenemos los resultados al nivel de Search-Results

				resultados = (JSONArray) searchResults.get("entry"); //Obtenemos los resultados a nivel Entry y lo asignamos al arreglo

				@SuppressWarnings("rawtypes")
				Iterator objIter = resultados.iterator(); //Preparamos la iteración en los resultados
				
				Connection conn = null; //Preparamos la conexión con MySQL
				Statement query = null; //Preparamos el query que enviaremos a MySQL
				
				conn = DriverManager.getConnection("jdbc:mysql://localhost/reto3?user=alperez&password=Naoisnow2022"); //Establecemos los parámetros de la conexión
				query = conn.createStatement(); //Generamos el query dentro de la conexión
				
				int i = 0;
				
				while(objIter.hasNext()) { //Mientras existan resultados en el arreglo de resultados
					Investigador inv = new Investigador(); //Generamos una nueva instancia del modelo de investigador
					
					Object resultado = objIter.next(); //Iteramos en el siguiente resultado
					JSONObject resultadoJSON = (JSONObject) resultado; //Transformamos el resultado en un objeto JSON
					
					//Utilizamos los Setters de la clase Investigador para establecer las propiedades del objeto
					inv.setID(((resultadoJSON.get("dc:identifier") == null) ? "" : resultadoJSON.get("dc:identifier").toString()));
					inv.setUrl(((resultadoJSON.get("prism:url") == null) ? "" : resultadoJSON.get("prism:url").toString()));
					inv.setEID(((resultadoJSON.get("eid") == null) ? "" : resultadoJSON.get("eid").toString()));
					inv.setDocumentCount(((resultadoJSON.get("citedby-count") == null) ? 0 :  Integer.parseInt(resultadoJSON.get("citedby-count").toString())));
					inv.setCitedByCount(((resultadoJSON.get("citedby-count") == null) ? 0 :  Integer.parseInt(resultadoJSON.get("citedby-count").toString())));
					inv.setCitationCount(((resultadoJSON.get("citedby-count") == null) ? 0 :  Integer.parseInt(resultadoJSON.get("citedby-count").toString())));
					inv.setAffiliation(((resultadoJSON.get("affiliation") == null) ? "" : resultadoJSON.get("affiliation").toString()));
					inv.setGivenName(((resultadoJSON.get("dc:creator") == null) ? "" : resultadoJSON.get("dc:creator").toString()));
					inv.setClassification(((resultadoJSON.get("subtype") == null) ? "" : resultadoJSON.get("subtype").toString()));
					inv.setPublicationStart(((resultadoJSON.get("prism:coverDate") == null) ? "" : resultadoJSON.get("prism:coverDate").toString()));
					inv.setPublicationEnd(((resultadoJSON.get("prism:coverDisplayDate") == null) ? "" : resultadoJSON.get("prism:coverDisplayDate").toString()));
				
					//Guardamos en el arreglo que enviaremos a la vista, el investigador
					investigadores[i] = inv;
				
					//Ejecutamos el Quey para insertar el valor en la base de datos, utilizando los Getters del modelo de investigador
					query.execute("INSERT INTO authors VALUES('" + inv.getID() + "','" + inv.getUrl() + "','" + inv.getEID() + "'," + inv.getDocumentCount() + "," + inv.getCitedByCount() + "," + inv.getCitationCount() + ",'" + inv.getAffiliation() + "','" + inv.getGivenName() + "','" + inv.getClassification() + "','" + inv.getPublicationStart() + "','" + inv.getPublicationEnd() + "')");
				
					i++;
				}
				
				query.close(); //Cerramos el query que ya no utilizaremos
				
				//Mostramos el mensaje de éxito
				JOptionPane.showMessageDialog(null, "Los datos se insertaron correctamente en la Base de Datos: ", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			}
			catch(FileNotFoundException ex){ex.printStackTrace();}
			catch(IOException ex){ex.printStackTrace();}
			catch(ParseException ex){ex.printStackTrace();}
			catch(SQLException ex){ex.printStackTrace();}
			catch(Exception ex){ex.printStackTrace();}
			
		}
		
		con.disconnect(); //Nos desconectamos del API
		
		return investigadores; //Regresamos un arreglo de resultados a la vista
	}
}
