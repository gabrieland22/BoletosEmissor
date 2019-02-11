package service;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class TelaPrincipalService {

	@SuppressWarnings("resource")
	public void importarArquivo(FileInputStream fis) throws IOException {
		
		HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        workbook = new HSSFWorkbook(fis);
        sheet = workbook.getSheetAt(0);
        
        // Parte de importação das informações do arquivo aqui
        
	}

}
