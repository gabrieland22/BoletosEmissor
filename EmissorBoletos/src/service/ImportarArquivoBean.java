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
        
        // Parte de importação das informações do arquivo aqui
        
        Iterator<Row> rowIterator = sheet.iterator();
        Cliente cooperado = null;
        Date dataExcel = null;
        Date dataCriacao = null;
        Date dataAlteracao = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Long registro = 0L;
        ClienteController coopCon = new ClienteController();
        
        while (rowIterator.hasNext()) {
        	Row row;
        	if (registro.compareTo(0L) == 0) {
        		rowIterator.next();
        		row = rowIterator.next();
            }else {
            	row = rowIterator.next();
            }
        	
            
            cooperado = new Cliente();
            
            cooperado.setCodigo(row.getCell(0) != null && row.getCell(0).getStringCellValue() != null && !row.getCell(0).getStringCellValue().trim().equals("") ? row.getCell(0).getStringCellValue().trim() : "");
            cooperado.setNome(row.getCell(1) != null && row.getCell(1).getStringCellValue() != null && !row.getCell(1).getStringCellValue().trim().equals("") ? row.getCell(1).getStringCellValue().trim() : "");
            cooperado.setSexo(row.getCell(2) != null && row.getCell(2).getStringCellValue() != null && !row.getCell(2).getStringCellValue().trim().equals("") ? row.getCell(2).getStringCellValue().trim() : "");
            cooperado.setRua(row.getCell(3) != null && row.getCell(3).getStringCellValue() != null && !row.getCell(3).getStringCellValue().trim().equals("") ? row.getCell(3).getStringCellValue().trim() : "");
            cooperado.setBairro(row.getCell(4) != null && row.getCell(4).getStringCellValue() != null && !row.getCell(4).getStringCellValue().trim().equals("") ? row.getCell(4).getStringCellValue().trim() : "");
            cooperado.setCidade(row.getCell(5) != null && row.getCell(5).getStringCellValue() != null && !row.getCell(5).getStringCellValue().trim().equals("") ? row.getCell(5).getStringCellValue().trim() : "");
            cooperado.setCep(row.getCell(6) != null && row.getCell(6).getStringCellValue() != null && !row.getCell(6).getStringCellValue().trim().equals("") ? row.getCell(6).getStringCellValue().trim() : "");
            cooperado.setEstado(row.getCell(7) != null && row.getCell(7).getStringCellValue() != null && !row.getCell(7).getStringCellValue().trim().equals("") ? row.getCell(7).getStringCellValue().trim() : "");
            cooperado.setCpf(row.getCell(8) != null && row.getCell(8).getStringCellValue() != null && !row.getCell(8).getStringCellValue().trim().equals("") ? row.getCell(8).getStringCellValue().trim() : "");
            
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
            cooperado.setAniversario(dataExcel);
            cooperado.setTelefone(row.getCell(15) != null && row.getCell(15).getStringCellValue() != null && !row.getCell(15).getStringCellValue().trim().equals("") ? row.getCell(15).getStringCellValue().trim() : "");
            cooperado.setCelular(row.getCell(88) != null && row.getCell(88).getStringCellValue() != null && !row.getCell(88).getStringCellValue().trim().equals("") ? row.getCell(88).getStringCellValue().trim() : "");
            cooperado.setEmail(row.getCell(89) != null && row.getCell(89).getStringCellValue() != null && !row.getCell(89).getStringCellValue().trim().equals("") ? row.getCell(89).getStringCellValue().trim() : "");
            cooperado.setUsuarioCriacao(usuarioLogado.getNome());
            cooperado.setUsuarioAlteracao(usuarioLogado.getNome());
            cooperado.setDataCriacao(new Date());
            cooperado.setDataAlteracao(new Date());
            
            coopCon.salvar(cooperado);
            registro++;
            
        }
        
        JOptionPane.showMessageDialog(null, "Planilha Salva com Sucesso! " + registro + " Foram Carregados", "", JOptionPane.INFORMATION_MESSAGE);
        
	}

}
