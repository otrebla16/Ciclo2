package model;

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
	
	public Investigador() {
		
	}
	
	public String getID() {
		return strID;
	}
	
	public String getUrl() {
		return strUrl;
	}
	
	public String getEID() {
		return strEID;
	}
	
	public int getDocumentCount() {
		return iDocumentCount;
	}
	
	public int getCitedByCount() {
		return iCitedByCount;
	}
	
	public int getCitationCount() {
		return iCitationCount;
	}
	
	public String getAffiliation() {
		return strAffiliation;
	}
	
	public String getGivenName() {
		return strGivenName;
	}
	
	public String getClassification() {
		return strClassification;
	}
	
	public String getPublicationStart() {
		return strPublicationStart;
	}
	
	public String getPublicationEnd() {
		return strPublicationEnd;
	}
	
	public void setID(String id) {
		this.strID = id;
	}
	
	public void setUrl(String url) {
		this.strUrl = url;
	}
	
	public void setEID(String eid) {
		this.strEID = eid;
	}
	
	public void setDocumentCount(int documentCount) {
		this.iDocumentCount = documentCount;
	}
	
	public void setCitedByCount(int citedByCount) {
		this.iCitedByCount = citedByCount;
	}
	
	public void setCitationCount(int citationCount) {
		this.iCitationCount = citationCount;
	}
	
	public void setAffiliation(String affiliation) {
		this.strAffiliation = affiliation;
	}
	
	public void setGivenName(String givenName) {
		this.strGivenName = givenName;
	}
	
	public void setClassification(String classification) {
		this.strClassification = classification;
	}
	
	public void setPublicationStart(String publicationStart) {
		this.strPublicationStart = publicationStart;
	}
	
	public void setPublicationEnd(String publicationEnd) {
		this.strPublicationEnd = publicationEnd;
	}
}
