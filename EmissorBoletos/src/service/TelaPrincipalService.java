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

import controller.CooperadoController;
import model.Cooperado;


public class TelaPrincipalService {

	@SuppressWarnings("resource")
	public void importarArquivo(FileInputStream fis) throws InvalidFormatException, IOException{
		
		Workbook workbook = null; 
		Sheet sheet = null;
		workbook = WorkbookFactory.create(fis);
        sheet = workbook.getSheetAt(0);
        
        // Parte de importação das informações do arquivo aqui
        
        Iterator<Row> rowIterator = sheet.iterator();
        Cooperado cooperado = null;
        Date dataExcel = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Long registro = 0L;
        CooperadoController coopCon = new CooperadoController();
        
        while (rowIterator.hasNext()) {
        	Row row;
        	if (registro.compareTo(0L) == 0) {
        		rowIterator.next();
        		row = rowIterator.next();
            }else {
            	row = rowIterator.next();
            }
        	
            
            cooperado = new Cooperado();
            
            cooperado.setCodigo(row.getCell(0).getStringCellValue() != null && !row.getCell(0).getStringCellValue().equals("") ? row.getCell(0).getStringCellValue().trim() : "");
            cooperado.setNome(row.getCell(1).getStringCellValue() != null && !row.getCell(1).getStringCellValue().equals("") ? row.getCell(1).getStringCellValue().trim() : "");
            cooperado.setSexo(row.getCell(2).getStringCellValue() != null && !row.getCell(2).getStringCellValue().equals("") ? row.getCell(2).getStringCellValue().trim() : "");
            cooperado.setBairro(row.getCell(3).getStringCellValue() != null && !row.getCell(3).getStringCellValue().equals("") ? row.getCell(3).getStringCellValue().trim() : "");
            cooperado.setCidade(row.getCell(4).getStringCellValue() != null && !row.getCell(4).getStringCellValue().equals("") ? row.getCell(4).getStringCellValue().trim() : "");
            cooperado.setCep(row.getCell(5).getStringCellValue() != null && !row.getCell(5).getStringCellValue().equals("") ? row.getCell(5).getStringCellValue().trim() : "");
            cooperado.setEstado(row.getCell(6).getStringCellValue() != null && !row.getCell(6).getStringCellValue().equals("") ? row.getCell(6).getStringCellValue().trim() : "");
            cooperado.setCpf(row.getCell(7).getStringCellValue() != null && !row.getCell(7).getStringCellValue().equals("") ? row.getCell(7).getStringCellValue().trim() : "");
            
            dataExcel = null;
            if (row.getCell(8) != null) {
            if (row.getCell(8).getCellType() == 0) {
                if (row.getCell(8).getDateCellValue() != null) {
                  dataExcel = row.getCell(8).getDateCellValue();
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
            cooperado.setTelefone(row.getCell(14).getStringCellValue() != null && !row.getCell(14).getStringCellValue().equals("") ? row.getCell(14).getStringCellValue().trim() : "");
            cooperado.setCelular(row.getCell(87).getStringCellValue() != null && !row.getCell(87).getStringCellValue().equals("") ? row.getCell(87).getStringCellValue().trim() : "");
            cooperado.setEmail(row.getCell(88).getStringCellValue() != null && !row.getCell(88).getStringCellValue().equals("") ? row.getCell(88).getStringCellValue().trim() : "");
            
            coopCon.salvar(cooperado);
            
        }
        
        JOptionPane.showMessageDialog(null, "Planilha Salva com Sucesso!", "", JOptionPane.INFORMATION_MESSAGE);
        
	}

}
