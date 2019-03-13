package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import controller.ClienteController;
import model.Cliente;
import model.Usuario;


public class ImportarArquivoBean {

	@SuppressWarnings("resource")
	public void importarArquivo(FileInputStream fis, Usuario usuarioLogado) throws InvalidFormatException, IOException{
		
		Workbook workbook = null; 
		Sheet sheet = null;
		workbook = WorkbookFactory.create(fis);
        sheet = workbook.getSheetAt(0);
        Long registro1 = 0L;
        Iterator<Row> rowIterator = sheet.iterator();
        ClienteController cliCon = new ClienteController();
        Cliente clienteComparacao;
        Cliente cliente = null;
        Date dataExcel = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Long registro2 = 0L;
        
        // Verifica se o registro já está na base de dados
        
        while (rowIterator.hasNext()) {
        	Row row;
        	if (registro1.compareTo(0L) == 0) {
        		rowIterator.next();
        		row = rowIterator.next();
            }else {
            	row = rowIterator.next();
            }
        	
        	clienteComparacao = new Cliente();
        	clienteComparacao.setCodigo(row.getCell(0) != null && row.getCell(0).getStringCellValue() != null 
        	&& !row.getCell(0).getStringCellValue().trim().equals("") ? row.getCell(0).getStringCellValue().trim() : "");
        	
        	clienteComparacao.setCpf(row.getCell(8) != null && row.getCell(8).getStringCellValue() != null
        	&& !row.getCell(8).getStringCellValue().trim().equals("") ? row.getCell(8).getStringCellValue().trim() : "");
        	
        	if(cliCon.existeCliente(clienteComparacao)) {
        		rowIterator.remove();
        	}else {
        		cliente = new Cliente();
            	cliente.setCodigo(row.getCell(0) != null && row.getCell(0).getStringCellValue() != null && !row.getCell(0).getStringCellValue().trim().equals("") ? row.getCell(0).getStringCellValue().trim() : "");
            	cliente.setNome(row.getCell(1) != null && row.getCell(1).getStringCellValue() != null && !row.getCell(1).getStringCellValue().trim().equals("") ? row.getCell(1).getStringCellValue().trim() : "");
            	cliente.setSexo(row.getCell(2) != null && row.getCell(2).getStringCellValue() != null && !row.getCell(2).getStringCellValue().trim().equals("") ? row.getCell(2).getStringCellValue().trim() : "");
            	cliente.setRua(row.getCell(3) != null && row.getCell(3).getStringCellValue() != null && !row.getCell(3).getStringCellValue().trim().equals("") ? row.getCell(3).getStringCellValue().trim() : "");
            	cliente.setBairro(row.getCell(4) != null && row.getCell(4).getStringCellValue() != null && !row.getCell(4).getStringCellValue().trim().equals("") ? row.getCell(4).getStringCellValue().trim() : "");
            	cliente.setCidade(row.getCell(5) != null && row.getCell(5).getStringCellValue() != null && !row.getCell(5).getStringCellValue().trim().equals("") ? row.getCell(5).getStringCellValue().trim() : "");
            	cliente.setCep(row.getCell(6) != null && row.getCell(6).getStringCellValue() != null && !row.getCell(6).getStringCellValue().trim().equals("") ? row.getCell(6).getStringCellValue().trim() : "");
            	cliente.setEstado(row.getCell(7) != null && row.getCell(7).getStringCellValue() != null && !row.getCell(7).getStringCellValue().trim().equals("") ? row.getCell(7).getStringCellValue().trim() : "");
            	cliente.setCpf(row.getCell(8) != null && row.getCell(8).getStringCellValue() != null && !row.getCell(8).getStringCellValue().trim().equals("") ? row.getCell(8).getStringCellValue().trim() : "");
                
                dataExcel = null;
                if (row.getCell(9) != null) {
                if (row.getCell(9).getCellType() == 0) {
                    if (row.getCell(9).getDateCellValue() != null) {
                      dataExcel = row.getCell(9).getDateCellValue();
                    }
                  } else {
                    if (row.getCell(9).getStringCellValue() != null
                        && row.getCell(9).getStringCellValue() != "") {
                      try {
    					dataExcel = sdf.parse(row.getCell(9).getStringCellValue());
    				} catch (ParseException e) {
    					e.printStackTrace();
    				}
                    }
                  }
                }
                cliente.setAniversario(dataExcel);
                cliente.setTelefone(row.getCell(15) != null && row.getCell(15).getStringCellValue() != null && !row.getCell(15).getStringCellValue().trim().equals("") ? row.getCell(15).getStringCellValue().trim() : "");
                cliente.setCelular(row.getCell(88) != null && row.getCell(88).getStringCellValue() != null && !row.getCell(88).getStringCellValue().trim().equals("") ? row.getCell(88).getStringCellValue().trim() : "");
                cliente.setEmail(row.getCell(89) != null && row.getCell(89).getStringCellValue() != null && !row.getCell(89).getStringCellValue().trim().equals("") ? row.getCell(89).getStringCellValue().trim() : "");
                cliente.setUsuarioCriacao(usuarioLogado.getNome());
                cliente.setUsuarioAlteracao(usuarioLogado.getNome());
                cliente.setDataCriacao(new Date());
                cliente.setDataAlteracao(new Date());
                cliente.setEnviarEmail(false);
                
                cliCon.salvar(cliente);
                registro2++;
        	}
        	registro1++;
        }
        
        JOptionPane.showMessageDialog(null, "Planilha Salva com Sucesso! " + registro2 + " Foram Carregados", "", JOptionPane.INFORMATION_MESSAGE);
        
	}

}
