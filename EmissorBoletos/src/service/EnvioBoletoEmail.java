package service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

import controller.ClienteController;
import vo.ClienteEnvioVO;

public class EnvioBoletoEmail {

	public EnvioBoletoEmail() {
		ClienteController cliCon = new ClienteController();
		File[] listaBoletos = (new File ("c:/Boletos")).listFiles (new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".pdf");
            }
        });
		String pathRessourceReal = ("C:/Boletos");

	      // Assuming you are sending email from localhost
	      String host = "localhost";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);
		
	      List<ClienteEnvioVO> listaClientesEnvio = cliCon.recuperaClientesParaEnvio();
		    if (listaClientesEnvio != null && listaClientesEnvio.size() > 0) {
		    	for(ClienteEnvioVO cliEnv : listaClientesEnvio){
		    		for (int i = 0; i < listaBoletos.length; ++i) {
		    			if(cliEnv.getCpf().contains(listaBoletos[i].getName().substring(0, 10))){
		    				try {
		    					MimeMessage msg = new MimeMessage(session);
		    					Address from = new InternetAddress("gabrieland22@gmail.com");
		    					Address[] to = new InternetAddress[] {
		    			                new InternetAddress(cliEnv.getEmail()) };
		    					
		    					msg.setFrom(from);
		    					msg.setRecipients(Message.RecipientType.TO, to);
		    					msg.setSubject(
		    			                " Boleto de pagamento Valem  [ Favor Nao Responder ]");
		    					String texto = " Prezado(a) cliente "+cliEnv.getNome()+ " segue anexo o boleto de pagamento.";
		    					MimeBodyPart corpoMsg = new MimeBodyPart();
		    					corpoMsg.setText(texto);
		    					
		    					// Anexa o boleto por cliente.
		    					
		    					MimeBodyPart anexo = new MimeBodyPart();
		    					DataSource ds = new FileDataSource(pathRessourceReal +listaBoletos[i].getName()+".pdf");
		    					anexo.setDataHandler(new DataHandler(ds));
		    					anexo.setFileName("BoletoPagamento.pdf");
		    					
		    					// cria a Multipart
		    		            Multipart mp = new MimeMultipart();
		    		            mp.addBodyPart(corpoMsg);
		    		            mp.addBodyPart(anexo);
		    		            
		    		            // adiciona a Multipart na mensagem
		    		            msg.setContent(mp);
		    		            msg.setSentDate(new java.util.Date());
		    		            Transport.send(msg);
		    					
		    				} catch (Exception e) {
		    					System.out.println("[ ERRO ] ao enviar Boleto. Cliente: "+cliEnv.getNome());
		    				}
		    				
			    		}
		            }
		    		
		    	}
		    }else{
		    	JOptionPane.showMessageDialog(null, "Não foram encontrados clientes aptos ao recebimento.", "", JOptionPane.WARNING_MESSAGE);
		    }
		  
	}
}
