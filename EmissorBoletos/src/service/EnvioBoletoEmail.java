package service;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import controller.ClienteController;

public class EnvioBoletoEmail {

	public EnvioBoletoEmail() {
		ClienteController cliCon = new ClienteController();
		
		//List<String> listaEmailsClientes = cliCon.recuperaCooperadosEmailGlosaRepasse();

//		    if (listaEmailsClientes != null && listaEmailsClientes.size() > 0) {
//		    	
//		    }
		
		String pathRessourceReal = ("C:/Boletos");


	      // Assuming you are sending email from localhost
	      String host = "localhost";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);
		
		
//		try {
//			MimeMessage m = new MimeMessage(session);
//			Address from = new InternetAddress("gabrieland22@gmail.com");
//			
//			Address[] to = new InternetAddress[] {
//	                new InternetAddress(item.getEmailCorrespondencia()) };
//			
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
	}
}
