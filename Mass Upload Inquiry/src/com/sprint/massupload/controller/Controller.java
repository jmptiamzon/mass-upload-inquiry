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
	
	public void runApp(String filepath, String dropdownItem) {
		String orgId = dropdownItem.equalsIgnoreCase("rw2") ? "204" : "107";
		readExcelAndProcess(filepath, orgId);
	}
	
	public Map<String, ExcelContent> readExcelAndProcess(String filepath, String orgId) {
		ExcelContent tempContent = new ExcelContent();
		Map<String, ExcelContent> excelContent = new HashMap<>();
		String parameter = "";
		int ctr = 0;
		
		try {
			FileInputStream xlsxFile = new FileInputStream(new File(filepath));
			Workbook workbook = new XSSFWorkbook(xlsxFile);
			Sheet sheet = workbook.getSheetAt(0);
			XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
			XSSFFont font = (XSSFFont) workbook.createFont();
			
			Iterator<Row> itr = sheet.iterator();
			
			while (itr.hasNext()) {
				Row nextRow = itr.next();
				
				if (!nextRow.getCell(0).getStringCellValue().trim().equalsIgnoreCase("sku") && !nextRow.getCell(0).getStringCellValue().trim().isEmpty()) {
					tempContent = new ExcelContent();
					tempContent.setSku(nextRow.getCell(0).getStringCellValue().trim());
					tempContent.setQty((long) nextRow.getCell(1).getNumericCellValue());
					tempContent.setAsurionAccept((long) nextRow.getCell(2).getNumericCellValue());
					tempContent.setOkToAuction((long) nextRow.getCell(3).getNumericCellValue());
					excelContent.put(nextRow.getCell(0).getStringCellValue().trim() + "-" + ctr, tempContent);
					ctr++;
				}
				
			}
			

			ctr = 0;
			for (String key : excelContent.keySet()) {
				parameter += "'" + key.split("-")[0] + "',";
				
				if ((ctr % 999) == 0 && ctr != 0) {
					parameter = parameter.substring(0, parameter.length() - 1);
					model.getStatusAndQty(parameter, orgId, excelContent);
					parameter = "";
				}
				
				if (ctr == excelContent.size() - 1) {
					parameter = parameter.substring(0, parameter.length() - 1);
					model.getStatusAndQty(parameter, orgId, excelContent);
					parameter = "";
				}
				
				ctr++;
			}
			
			
			Cell cell;
			Row row;

			style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			font.setBold(true);
			style.setFont(font);
			
			row = sheet.getRow(0);
			
			cell = row.createCell(4);
			cell.setCellValue("OMIM Qty");
			cell.setCellStyle(style);
			
			cell = row.createCell(5);
			cell.setCellValue("Status");
			cell.setCellStyle(style);
			
			for (ctr = 0; ctr < excelContent.size(); ctr++) {
				row = sheet.getRow(ctr + 1);
				
				cell = row.createCell(4);
				cell.setCellValue(excelContent.get(row.getCell(0).getStringCellValue().trim() + "-" + ctr).getOmimQty());

				cell = row.createCell(5);
				cell.setCellValue(excelContent.get(row.getCell(0).getStringCellValue().trim() + "-" + ctr).getStatus());
					
			}
			
			xlsxFile.close();
			
			FileOutputStream fout = new FileOutputStream(filepath);
			workbook.write(fout);
			workbook.close();
			fout.close();
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File error: " + e.getMessage());
		}
		
		
		return excelContent;
	}
	

}
