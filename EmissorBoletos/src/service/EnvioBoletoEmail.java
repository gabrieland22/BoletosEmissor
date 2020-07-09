package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import vo.ClienteEnvioVO;
import vo.RelatorioEnvioVO;
import controller.ClienteController;

public class EnvioBoletoEmail {

  public EnvioBoletoEmail(final String contaEmail, final String senha) {
    ClienteController cliCon = new ClienteController();
    boolean achouBoleto = false;
    boolean connected = false;
    List<RelatorioEnvioVO> listaRelatorio = new ArrayList<>();
    int contador = 0;
    File[] listaBoletos = new File("c:/Boletos")
        .listFiles(new FilenameFilter() {
          @Override
          public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".pdf");
          }
        });
    String sourceBoletos = "C:/Boletos/";

    // String sourceRealImage = System.getProperty("user.dir").toString() +
    // "/src/images/logo_valem.png";
    // Properties prop = PropertiesLoader.propertiesLoader(sourceRealImage);
    // String pathRessourceReal = prop.getProperty("path_emails");

    Properties props = new Properties();
    /** Parâmetros de conexão com servidor Gmail */
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.socketFactory.port", "465");
    props
        .put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "465");

    Session session = Session.getDefaultInstance(props,
        new javax.mail.Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {

            // return new PasswordAuthentication("operacional1@valem.com.br",
            // "valem3013"

            // return new PasswordAuthentication("operacional3@valem.com.br",
            // "valem@1234"

            return new PasswordAuthentication(contaEmail, senha);
          }
        });

    if (testaAutenticacaoParaEnvio(session, contaEmail, senha)) {
      connected = true;
    } else {
      connected = false;
    }

    /** Ativa Debug para sessão */
    session.setDebug(true);

    List<ClienteEnvioVO> listaClientesEnvio = cliCon
        .recuperaClientesParaEnvio();

    if (connected) {
      if (listaClientesEnvio != null && listaClientesEnvio.size() > 0) {
        for (ClienteEnvioVO cliEnv : listaClientesEnvio) {
          RelatorioEnvioVO vO = new RelatorioEnvioVO();
          achouBoleto = false;
          for (int i = 0; i < listaBoletos.length; ++i) {
            if (cliEnv.getCpf().contains(
                listaBoletos[i].getName().substring(7, 17))) {
              achouBoleto = true;
              if (cliEnv.getEmail() != null
                  && !cliEnv.getEmail().trim().equals("")) {
                try {
                  MimeMessage msg = new MimeMessage(session);
                  Address from = new InternetAddress("operacional@valem.com.br");
                  Address[] to = new InternetAddress[] { new InternetAddress(
                      cliEnv.getEmail()) };

                  msg.setFrom(from);
                  msg.setRecipients(Message.RecipientType.TO, to);
                  msg.setSubject("Boleto Valem");
                  String texto = " Prezado(a) "
                      + cliEnv.getNome()
                      + "\n"
                      + " E-mail automático, não responder."
                      + "\n"
                      + " Segue anexo seu boleto/demonstrativo referente ao pagamento da Valem Administradora de Benefícios."
                      + "\n"
                      + " Você receberá este mesmo arquivo impresso no seu endereço cadastrado para cobrança."
                      + "\n\n"
                      + " Central de Atendimento Valem:"
                      + "\n"
                      + " Whatsapp: 313249-3000 (Adicione nosso número e solicite atendimento iniciando uma conversa)"
                      + "\n"
                      + " Telefone: 31 3249-3000 (Belo Horizonte e região metropolitana) e 0800 033 6000 (Demais Localidades)"
                      + "\n" + " E-mail: atendimento@valem.com.br" + "\n"
                      + " Site: www.valem.com.br";
                  MimeBodyPart corpoMsg = new MimeBodyPart();
                  corpoMsg.setText(texto);

                  // Anexa o boleto por cliente.

                  MimeBodyPart anexo = new MimeBodyPart();
                  DataSource ds = new FileDataSource(sourceBoletos
                      + listaBoletos[i].getName());
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
                  vO.setNomeCliente(cliEnv.getNome());
                  vO.setEnviouEmail("Sim");
                  listaRelatorio.add(vO);
                  contador++;
                } catch (Exception e) {
                  System.out.println("[ ERRO ] ao enviar Boleto. Cliente: "
                      + cliEnv.getNome());
                  e.printStackTrace();
                }

              } else {
                vO.setNomeCliente(cliEnv.getNome());
                vO.setEnviouEmail("Não");
                vO.setMotivo("Cliente sem email cadastrado");
                listaRelatorio.add(vO);
              }
            }
          }
          if (!achouBoleto) {
            vO.setNomeCliente(cliEnv.getNome());
            vO.setEnviouEmail("Não");
            vO.setMotivo("Não foi encontrado boleto com o CPF do cliente");
            listaRelatorio.add(vO);
          }
        }
        Object[] botoes = { "Sim", "Não" };
        int resposta = JOptionPane.showOptionDialog(null,
            "Deseja baixar o relatório de Envio?", "Confirmação",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
            botoes, botoes[0]);
        if (resposta == 0) {
          exportarExcel("C:/Relatorios/", listaRelatorio);
        }
      } else {
        JOptionPane.showMessageDialog(null,
            "Não foram encontrados clientes aptos ao recebimento.", "",
            JOptionPane.WARNING_MESSAGE);
      }
    } else {
      final String s = "A conta emissora e senha informadas não foram autenticadas."
          + " Ou as configurações da conta não atendem aos requisitos para envio."
          + " Favor REINICIAR a aplicação e enviar novamente os emails informando a conta emissora,"
          + " ou entre em contato com o suporte para verificação das configurações.";
      final String html = "<html><body style='width: %1spx'>%1s";
      JOptionPane.showMessageDialog(null, String.format(html, 400, s));
    }

    JOptionPane.showMessageDialog(null, "Foram enviados: " + contador
        + " emails.", "", JOptionPane.INFORMATION_MESSAGE);
  }

  public static InputStream exportarExcel(String templatePath, List<RelatorioEnvioVO> listRelatorio) {
    SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy-hh_mm");
    String dateString = df.format(new Date());
    File temp = new File(templatePath + "\\RelatorioEnvioEmail_" + dateString
        + ".xls");

    if (temp.exists()) {
      temp.delete();
    }
    try {
      if (!temp.createNewFile()) {
        System.out.println("bichou ...");
      }
    } catch (final IOException e) {
    }

    InputStream excel = null;
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet("Log Envio");
    HSSFRow row = sheet.createRow(0);
    HSSFFont font = wb.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    HSSFCellStyle style = wb.createCellStyle();
    style.setFont(font);

    HSSFCell cell = row.createCell(0);
    cell.setCellType(Cell.CELL_TYPE_STRING);
    cell.setCellValue("Nome do Cliente");
    cell.setCellStyle(style);

    cell = row.createCell(1);
    cell.setCellType(Cell.CELL_TYPE_STRING);
    cell.setCellValue("Enviou Email");
    cell.setCellStyle(style);

    cell = row.createCell(2);
    cell.setCellType(Cell.CELL_TYPE_STRING);
    cell.setCellValue("Motivo");
    cell.setCellStyle(style);

    for (int k = 0; k < listRelatorio.size(); k++) {

      row = sheet.createRow(1 + k);

      cell = row.createCell(0);
      cell.setCellType(Cell.CELL_TYPE_STRING);
      cell.setCellValue(listRelatorio.get(k).getNomeCliente() != null ? listRelatorio
          .get(k).getNomeCliente() : "");

      cell = row.createCell(1);
      cell.setCellType(Cell.CELL_TYPE_STRING);
      cell.setCellValue(listRelatorio.get(k).getEnviouEmail() != null ? listRelatorio
          .get(k).getEnviouEmail() : "");

      cell = row.createCell(2);
      cell.setCellType(Cell.CELL_TYPE_STRING);
      cell.setCellValue(listRelatorio.get(k).getMotivo() != null ? listRelatorio
          .get(k).getMotivo() : "");

    }
    try {
      FileOutputStream output = new FileOutputStream(temp);
      wb.write(output);
      output.close();
      excel = new FileInputStream(temp);
    } catch (final FileNotFoundException e) {
      System.out.println("Arquivo bichado ..." + e.getMessage());
    } catch (final IOException e) {
      System.out.println("Arquivo bichado ..." + e.getMessage());
    }
    return excel;
  }

  private Boolean testaAutenticacaoParaEnvio(Session session, String user, String password) {
    boolean autenticou = false;
    try {
      // boolean isConnected = session.getTransport("smtp").isConnected();
      session.getTransport("smtp").connect(user, password);
      autenticou = true;
    } catch (Exception e) {
      autenticou = false;
    }
    return autenticou;
  }
}
