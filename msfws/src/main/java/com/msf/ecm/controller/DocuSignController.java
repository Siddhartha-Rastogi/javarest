package com.msf.ecm.controller;

import java.io.IOException;
import org.springframework.http.MediaType;
import com.msf.ecm.model.DocuSignDocument;
import com.msf.ecm.services.DocuSignService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("docu-sign")
public class DocuSignController {
	@Autowired
	DocuSignService docuSingServiceObj;
	
	@GetMapping(value="/GetMsg")
	public String getSpringBootMsg(){
		
		return "welcome";
	}
	
	@PostMapping(value = "/generateEnvelope", 
			consumes=MediaType.APPLICATION_JSON_VALUE,	
			produces={MediaType.APPLICATION_JSON_VALUE},
			headers="Accept=application/json" )
	public String generateEnvelope(HttpServletRequest request, @RequestBody DocuSignDocument docuSignDoc) throws IOException {	
				
		String strReturned = docuSingServiceObj.generateEnvelope(request, docuSignDoc);
		System.out.println(strReturned+" - - - - - - - - - - - - - PUT CALLED - - - - - - - - ");
		return strReturned;
	}
}