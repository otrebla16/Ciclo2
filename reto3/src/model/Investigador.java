package model;

/**
 * 
 * @author Alberto Pérez
 * 
 * Clase que contiene el MODELO de la entidad Investigador
 *
 */
public class Investigador {
	private String strID;
	private String strUrl;
	private String strEID;
	private int iDocumentCount;
	private int iCitedByCount;
	private int iCitationCount;
	private String strAffiliation;
	private String strGivenName;
	private String strClassification;
	private String strPublicationStart;
	private String strPublicationEnd;
	
	/**
	 * Constructor default de la clase Investigador
	 */
	public Investigador() {
		
	}
	
	/**
	 * Metodo Getter del ID del autor
	 */
	public String getID() {
		return strID;
	}
	
	/**
	 * Metodo Getter del URL del autor
	 */
	public String getUrl() {
		return strUrl;
	}
	
	/**
	 * Metodo Getter del EID del autor
	 */
	public String getEID() {
		return strEID;
	}
	
	/**
	 * Metodo Getter del Document Count del autor
	 */
	public int getDocumentCount() {
		return iDocumentCount;
	}
	
	/**
	 * Metodo Getter del CitedByCount del autor
	 */
	public int getCitedByCount() {
		return iCitedByCount;
	}
	
	/**
	 * Metodo Getter del CitationCount del autor
	 */
	public int getCitationCount() {
		return iCitationCount;
	}
	
	/**
	 * Metodo Getter de la afiliación del autor
	 */
	public String getAffiliation() {
		return strAffiliation;
	}
	
	/**
	 * Metodo Getter del nombre del autor
	 */
	public String getGivenName() {
		return strGivenName;
	}
	
	/**
	 * Metodo Getter de la clasificación del autor
	 */
	public String getClassification() {
		return strClassification;
	}
	
	/**
	 * Metodo Getter de la fecha de inicio de la publicación del autor
	 */
	public String getPublicationStart() {
		return strPublicationStart;
	}
	
	/**
	 * Metodo Getter de la fecha de finalización de la publicación del autor
	 */
	public String getPublicationEnd() {
		return strPublicationEnd;
	}
	
	/**
	 * Metodo Setter del ID del autor
	 * 
	 * @param id ID del autor
	 */
	public void setID(String id) {
		this.strID = id;
	}
	
	/**
	 * Metodo Setter del URL del autor
	 * 
	 * @param url URL del autor
	 */
	public void setUrl(String url) {
		this.strUrl = url;
	}
	
	/**
	 * Metodo Setter del EID del autor
	 * 
	 * @param eid EID del autor
	 */
	public void setEID(String eid) {
		this.strEID = eid;
	}
	
	/**
	 * Metodo Setter del DocumentCount del autor
	 * 
	 * @param documentCount DocumentCount del autor
	 */
	public void setDocumentCount(int documentCount) {
		this.iDocumentCount = documentCount;
	}
	
	/**
	 * Metodo Setter del CitedByCount del autor
	 * 
	 * @param citedByCount CitedByCount del autor
	 */
	public void setCitedByCount(int citedByCount) {
		this.iCitedByCount = citedByCount;
	}
	
	/**
	 * Metodo Setter del CitationCount del autor
	 * 
	 * @param citationCount CitationCount del autor
	 */
	public void setCitationCount(int citationCount) {
		this.iCitationCount = citationCount;
	}
	
	/**
	 * Metodo Setter de la afiliación del autor
	 * 
	 * @param affiliation afiliación del autor
	 */
	public void setAffiliation(String affiliation) {
		this.strAffiliation = affiliation;
	}
	
	/**
	 * Metodo Setter del nombre del autor
	 * 
	 * @param givenName nombre del autor
	 */
	public void setGivenName(String givenName) {
		this.strGivenName = givenName;
	}
	
	/**
	 * Metodo Setter de la clasificación del autor
	 * 
	 * @param classification clasificación del autor
	 */
	public void setClassification(String classification) {
		this.strClassification = classification;
	}
	
	/**
	 * Metodo Setter de la fecha de inicio de la publicacioón del autor
	 * 
	 * @param publicationStart fecha de inicio de la publicación del autor
	 */
	public void setPublicationStart(String publicationStart) {
		this.strPublicationStart = publicationStart;
	}
	
	/**
	 * Metodo Setter de la fecha de finalización de la publicacioón del autor
	 * 
	 * @param publicationEnd fecha de finalización de la publicación del autor
	 */
	public void setPublicationEnd(String publicationEnd) {
		this.strPublicationEnd = publicationEnd;
	}
}
