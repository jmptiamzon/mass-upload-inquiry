package com.sprint.massupload.controller;

import java.util.HashMap;
import java.util.Map;

import com.sprint.massupload.model.ExcelContent;
import com.sprint.massupload.model.Model;

public class Controller {
	private Model model;
	
	public Controller() {
		model = new Model();
	}
	
	public void runApp(String filepath) {
		
	}
	
	public Map<String, ExcelContent> readExcel() {
		ExcelContent tempContent = new ExcelContent();
		Map<String, ExcelContent> excelContent = new HashMap<>();
		
		
		
		return excelContent;
	}

}
