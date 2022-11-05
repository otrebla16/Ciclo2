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

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Object [] colNames = {"ID", "URL", "EID", "DocCount", "CitedByCount", "CitationCount", "Affiliation", "Name", "Classification", "PublicationStart", "PublicationEnd"};
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Top de Investigación", TitledBorder.CENTER, TitledBorder.TOP));
		
		JTable table = new JTable();
		
		DefaultTableModel tModel = new DefaultTableModel(colNames,0);
		table.setModel(tModel);
		
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JButton btnUpdate = new JButton("Ejecutar");
		btnUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Investigador investigador = new Investigador();
				Investigador investigadores[] = new Investigador[25];
				
				InvestigadorController controlador = new InvestigadorController(investigador);
				
				try {
					investigadores = controlador.fillData();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				for(int i = 0; i < investigadores.length; i++) {
					investigador = investigadores[i];
					String [] dataArr = { investigador.getID(), investigador.getUrl(), investigador.getEID(), String.valueOf(investigador.getDocumentCount()), String.valueOf(investigador.getCitedByCount()), String.valueOf(investigador.getCitationCount()), investigador.getAffiliation(), investigador.getGivenName(), investigador.getClassification(), investigador.getPublicationStart(), investigador.getPublicationEnd() };					
					tModel.addRow(dataArr);
				}
				
			}
		});
		
		btnUpdate.setBounds(163, 527, 89, 23);
		
		panel.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		panel.add(btnUpdate);
		
		frame.getContentPane().add(panel);
		frame.setSize(550, 600);
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
	}

	
}
