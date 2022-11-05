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

public class InvestigadorController {
	private Investigador iv;
	
	public InvestigadorController(Investigador modelo) {
		this.iv = modelo;
	}
	
	public String getID() {
		return iv.getID();
	}
	
	public String getUrl() {
		return iv.getUrl();
	}
	
	public String getEID() {
		return iv.getEID();
	}
	
	public int getDocumentCount() {
		return iv.getDocumentCount();
	}
	
	public int getCitedByCount() {
		return iv.getCitedByCount();
	}
	
	public int getCitationCount() {
		return iv.getCitationCount();
	}
	
	public String getAffiliation() {
		return iv.getAffiliation();
	}
	
	public String getGivenName() {
		return iv.getGivenName();
	}
	
	public String getClassification() {
		return iv.getClassification();
	}
	
	public String getPublicationStart() {
		return iv.getPublicationStart();
	}
	
	public String getPublicationEnd() {
		return iv.getPublicationEnd();
	}
	
	public void setID(String id) {
		this.iv.setID(id);
	}
	
	public void setUrl(String url) {
		this.iv.setUrl(url);
	}
	
	public void setEID(String eid) {
		this.iv.setEID(eid);
	}
	
	public void setDocumentCount(int documentCount) {
		this.iv.setDocumentCount(documentCount);
	}
	
	public void setCitedByCount(int citedByCount) {
		this.iv.setCitedByCount(citedByCount);
	}
	
	public void setCitationCount(int citationCount) {
		this.iv.setCitationCount(citationCount);
	}
	
	public void setAffiliation(String affiliation) {
		this.iv.setAffiliation(affiliation);
	}
	
	public void setGivenName(String givenName) {
		this.iv.setGivenName(givenName);
	}
	
	public void setClassification(String classification) {
		this.iv.setClassification(classification);
	}
	
	public void setPublicationStart(String publicationStart) {
		this.iv.setPublicationStart(publicationStart);
	}
	
	public void setPublicationEnd(String publicationEnd) {
		this.iv.setPublicationEnd(publicationEnd);
	}
	
	public Investigador[] fillData() throws IOException {
		Investigador investigadores[] = new Investigador[25];
		
		URL url = new URL("https://api.elsevier.com/content/search/scopus?query=AU-ID(35227147500)");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("X-ELS-APIKey", "c7f561bbf42783c2becfd919cc8d27c2");		
		con.setDoOutput(true);
		con.setConnectTimeout(10000);
		con.setReadTimeout(10000);
		
		int status = con.getResponseCode();
		
		//System.out.println("Status: " + status);
		
		if (status > 299) {
		    BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			
		    System.out.println("Error: " + content);
		} else {
			JSONParser jsonParser = new JSONParser();
			JSONArray resultados = new JSONArray();
			
			try(BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) con.getContent()))) {
				Object obj = jsonParser.parse(reader);
				
				JSONObject jsonObj = (JSONObject) obj;
				
				//System.out.println("JSON: " + jsonObj);
				
				JSONObject searchResults = (JSONObject) jsonObj.get("search-results");

				resultados = (JSONArray) searchResults.get("entry");

				@SuppressWarnings("rawtypes")
				Iterator objIter = resultados.iterator();
				
				Connection conn = null;
				Statement query = null;
				
				conn = DriverManager.getConnection("jdbc:mysql://localhost/reto3?user=alperez&password=Naoisnow2022");
				query = conn.createStatement();
				
				int i = 0;
				
				while(objIter.hasNext()) {
					Investigador inv = new Investigador();
					
					Object resultado = objIter.next();
					JSONObject resultadoJSON = (JSONObject) resultado;
					
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
				
					investigadores[i] = inv;
				
					query.execute("INSERT INTO authors VALUES('" + inv.getID() + "','" + inv.getUrl() + "','" + inv.getEID() + "'," + inv.getDocumentCount() + "," + inv.getCitedByCount() + "," + inv.getCitationCount() + ",'" + inv.getAffiliation() + "','" + inv.getGivenName() + "','" + inv.getClassification() + "','" + inv.getPublicationStart() + "','" + inv.getPublicationEnd() + "')");
				
					i++;
				}
				
				query.close();
				
				JOptionPane.showMessageDialog(null, "Los datos se insertaron correctamente en la Base de Datos: ", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			}
			catch(FileNotFoundException ex){ex.printStackTrace();}
			catch(IOException ex){ex.printStackTrace();}
			catch(ParseException ex){ex.printStackTrace();}
			catch(SQLException ex){ex.printStackTrace();}
			catch(Exception ex){ex.printStackTrace();}
			
		}
		
		con.disconnect();
		
		return investigadores;
	}
}
