package com.sprint.massupload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sprint.massupload.model.ExcelContent;
import com.sprint.massupload.model.Model;


public class Controller {
	private Model model;
	
	public Controller() {
		model = new Model();
	}
	
	public void runApp(String filepath) {
		readExcelAndProcess(filepath);
	}
	
	public Map<String, ExcelContent> readExcelAndProcess(String filepath) {
		ExcelContent tempContent = new ExcelContent();
		Map<String, ExcelContent> excelContent = new HashMap<>();
		String parameter = "";
		
		try {
			FileInputStream xlsxFile = new FileInputStream(new File(filepath));
			Workbook workbook = new XSSFWorkbook(xlsxFile);
			Sheet sheet = workbook.getSheetAt(0);
			XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
			XSSFFont font = (XSSFFont) workbook.createFont();
			
			Iterator<Row> itr = sheet.iterator();
			
			while (itr.hasNext()) {
				Row nextRow = itr.next();
				
				if (!nextRow.getCell(0).getStringCellValue().trim().equalsIgnoreCase("sku")) {
					tempContent = new ExcelContent();
					tempContent.setSku(nextRow.getCell(0).getStringCellValue().trim());
					tempContent.setQty((long) nextRow.getCell(1).getNumericCellValue());
					tempContent.setAsurionAccept((long) nextRow.getCell(2).getNumericCellValue());
					tempContent.setOkToAuction((long) nextRow.getCell(3).getNumericCellValue());
					excelContent.put(nextRow.getCell(0).getStringCellValue().trim(), tempContent);
				}
				
			}
			
			int ctr = 0;
			for (String key : excelContent.keySet()) {
				parameter += "'" + key + "',";
				
				if ((ctr % 999) == 0 && ctr != 0) {
					parameter = parameter.substring(0, parameter.length() - 1);
					model.getStatusAndQty(parameter, excelContent);
					parameter = "";
				}
				
				if (ctr == excelContent.size() - 1) {
					parameter = parameter.substring(0, parameter.length() - 1);
					model.getStatusAndQty(parameter, excelContent);
					parameter = "";
				}
				
				ctr++;
			}
			
			model.getStatusAndQty(parameter, excelContent);
			
			Cell cell;
			Row row;
			ctr = 0;

			style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			font.setBold(true);
			style.setFont(font);
			
			for (String key : excelContent.keySet()) {
				row = sheet.getRow(ctr);
				
				cell = row.createCell(4);
				if (ctr == 0) {
					cell.setCellValue("OMIM Qty");
					cell.setCellStyle(style);
				
				} else {
					cell.setCellValue(excelContent.get(key).getOmimQty());
					
				}
				
				cell = row.createCell(5);
				if (ctr == 0) {
					cell.setCellValue("Status");
					cell.setCellStyle(style);
				
				} else {
					cell.setCellValue(excelContent.get(key).getStatus());
					
				}
						
				ctr++;
			}
			
			xlsxFile.close();
			
			FileOutputStream fout = new FileOutputStream(filepath);
			workbook.write(fout);
			workbook.close();
			fout.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "File error: " + e.getMessage());
		}
		
		
		return excelContent;
	}
	

}
