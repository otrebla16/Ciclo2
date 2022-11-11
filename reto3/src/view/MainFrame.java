package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controller.InvestigadorController;
import model.Investigador;

import javax.swing.JTable;
import javax.swing.JButton;

/**
 * 
 * @author Alberto Pérez
 * 
 * Clase que contiene la VISTA de los Investigadores
 *
 */
public class MainFrame extends JFrame {
	
	//Número de serie de la aplicación
	private static final long serialVersionUID = 1L;
	
	//Establecemos las columnas que utilizaremos en la tabla de resultados
	private static Object [] colNames = {"ID", "URL", "EID", "DocCount", "CitedByCount", "CitationCount", "Affiliation", "Name", "Classification", "PublicationStart", "PublicationEnd"};
		
	/**
	 * Lanzamiento de la aplicación
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame(); //Marco de trabajo de la ventana
		JPanel panel = new JPanel(); //Panel donde estableceremos los elementos a mostrar
		
		//Mostramos el panel que contendrá la tabla
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Top de Investigación", TitledBorder.CENTER, TitledBorder.TOP));
		
		JTable table = new JTable(); //Preparamos la tabla que mostrará los resultados
		
		DefaultTableModel tModel = new DefaultTableModel(colNames,0); //establecemos las columnas de la tabla en un modelo
		table.setModel(tModel); //establecemos el modelo que utilizará la tabla
		
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //Quitamos la opción de redimensionamiento automático
		
		JButton btnUpdate = new JButton("Ejecutar"); //Agregamos el botón que ejecutará el proceso de conexión con SCOUPS y el envío a MySQL
		btnUpdate.addActionListener(new ActionListener(){ //Cuando el usuario de clic en el botón de ejecutar...
			public void actionPerformed(ActionEvent e) {
				Investigador investigador = new Investigador(); //Generamos una instancia del modelo investigador
				Investigador investigadores[] = new Investigador[25]; //Preparamos un arreglo de investigadores para mostrar en la tabla
				
				InvestigadorController controlador = new InvestigadorController(investigador); //Generamos una instancia del controlador de investigadores
				
				try {
					investigadores = controlador.fillData(); //Ejecutamos el proceso de llenado de datos desde el controlador
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				//Agregamos los resultados que nos regresó el controlador en la tabla, utilizando los Getters del modelo de investigador
				for(int i = 0; i < investigadores.length; i++) {
					investigador = investigadores[i];
					String [] dataArr = { investigador.getID(), investigador.getUrl(), investigador.getEID(), String.valueOf(investigador.getDocumentCount()), String.valueOf(investigador.getCitedByCount()), String.valueOf(investigador.getCitationCount()), investigador.getAffiliation(), investigador.getGivenName(), investigador.getClassification(), investigador.getPublicationStart(), investigador.getPublicationEnd() };					
					tModel.addRow(dataArr);
				}
				
			}
		});
		
		btnUpdate.setBounds(163, 527, 89, 23);
		
		//Agregamos el scroll para la tabla
		panel.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		//Agregamos el botón de ejecución en el panel
		panel.add(btnUpdate);
		
		//Agregamos el panel al marvo visible
		frame.getContentPane().add(panel);
		frame.setSize(550, 600);
		frame.setVisible(true);
	}

	/**
	 * Creación del frame
	 */
	public MainFrame() {
		
	}

	
}
