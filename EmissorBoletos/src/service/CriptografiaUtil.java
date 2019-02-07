package service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class CriptografiaUtil {

  private static String ALGORITIMO = "SHA-256";

  public static String criptografar(String senha) {

    try {
      MessageDigest digest = MessageDigest.getInstance(ALGORITIMO);
      digest.update(senha.getBytes());

      return DatatypeConverter.printBase64Binary(digest.digest());

    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Nao existe algoritimo para SHA-256.", e);
    }

  }

  public static String hashMD5(String mensagem, String charsetName) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(mensagem.getBytes(charsetName));

      byte byteData[] = messageDigest.digest();

      StringBuffer stringBuffer = new StringBuffer();
      for (int i = 0; i < byteData.length; i++) {
        stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
            .substring(1));
      }

      return stringBuffer.toString();
    } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
      throw new RuntimeException("Nao existe algoritimo para MD5.", e);
    }

  }

  public static void main(String[] args) {

    System.out.println(criptografar("12345"));

  }

}

