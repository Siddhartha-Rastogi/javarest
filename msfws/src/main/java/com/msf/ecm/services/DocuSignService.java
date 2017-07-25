package com.msf.ecm.services;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.msf.ecm.model.DocuSignDocument;
import com.msf.ecm.utils.DocuSignUtils;

@Service
public class DocuSignService {

	public String generateEnvelope(HttpServletRequest request, DocuSignDocument docuSignDoc) throws IOException{//		System.out.println(docuSignDoc.getDocumentGUID()+"11"+docuSignDoc.getUserEmail()+"CCC"+docuSignDoc.getUserName()+"BBBB"+docuSignDoc.getBase64File()+"AAA");

		HttpURLConnection conn				= null;
		int status							= 0;

		//	Loading values from DocuSignConfig.properties file
		InputStream isObject 				= DocuSignService.class.getClassLoader().getResourceAsStream("DocuSignconfig.properties");
        Properties propObject 				= new Properties();
        propObject.load(isObject);
        isObject.close();
         
		String strbaseURL 					= propObject.getProperty("baseURL");
		String strEnvelopURLPostFix			= propObject.getProperty("envelopURLPostFix");
		String strUserName					= propObject.getProperty("Username");
		String strUserPassword				= propObject.getProperty("Password");
		String strIntegratorKey				= propObject.getProperty("IntegratorKey");
		String authenticationHeader 		= "{ \"Username\": \""+strUserName+"\", \"Password\": \""+strUserPassword+"\", \"IntegratorKey\": \""+strIntegratorKey+"\" }";
		String strFinalURL					= strbaseURL + strEnvelopURLPostFix;									

		String baseSFDocument 				= docuSignDoc.getBase64File();       	

		String body 						= DocuSignUtils.getBodyAsString(docuSignDoc);
		String reqBody2 					= "\r\n" + "--AAA--\r\n\r\n";

		conn 								= DocuSignUtils.InitializeRequest(strFinalURL, "POST", body, authenticationHeader);
		conn 								= (HttpURLConnection)new URL(strFinalURL).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("X-DocuSign-Authentication", authenticationHeader);
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=AAA");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Content-Length", Integer.toString(body.length()));
		conn.setDoOutput(true);

		// write body of the POST request 
		DataOutputStream dos 				= new DataOutputStream(conn.getOutputStream());
		dos.writeBytes(body);

		if(baseSFDocument!=null){
			dos.write(Base64.getDecoder().decode(baseSFDocument.getBytes()));
		}

		dos.writeBytes(reqBody2.toString()); 
		dos.flush(); 
		dos.close();

		status 								= conn.getResponseCode(); // triggers the request

		if( status != 201 )	// 200 = OK
		{
			String responseStr 				= DocuSignUtils.errorParse(conn, status);
			System.out.println(responseStr);
			return responseStr;
		}
		return DocuSignUtils.getResponseBody(conn);

	}
}