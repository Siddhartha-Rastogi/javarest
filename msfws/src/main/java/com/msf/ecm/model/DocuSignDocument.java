package com.msf.ecm.model;

public class DocuSignDocument {

	private String documentGUID = null;
	private String userName = null;
	private String userEmail = null;
	private String base64File = null;
	
	public DocuSignDocument(String documentGUID, String userName, String userEmail, String base64File) {
		
		this.documentGUID = documentGUID;
		this.userName = userName;
		this.userEmail = userEmail;
		this.base64File = base64File;
	}

	public DocuSignDocument() {
	}
	public String getDocumentGUID() {
		return documentGUID;
	}
	public void setDocumentGUID(String documentGUID) {
		this.documentGUID = documentGUID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getBase64File() {
		return base64File;
	}
	public void setBase64File(String base64File) {
		this.base64File = base64File;
	}
	
	
}
