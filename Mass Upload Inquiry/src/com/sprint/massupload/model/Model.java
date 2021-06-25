package com.sprint.massupload.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class Model {
	
	public void getStatusAndQty(String parameter, Map<String, ExcelContent> excelContent) {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn =
					DriverManager.getConnection("", "", "");
			
			PreparedStatement statement = conn.prepareStatement(
					"SELECT msi.segment1, SUM (moq.transaction_quantity), msi.inventory_item_status_code " + 
					"FROM apps.mtl_system_items_b msi, apps.mtl_onhand_quantities moq " + 
					"WHERE moq.organization_id = msi.organization_id " + 
					"AND moq.inventory_item_id = msi.inventory_item_id " + 
					"and moq.ORGANIZATION_ID = 204 " + 
					"and moq.subinventory_code='MAIN' " + 
					"and msi.SEGMENT1 in (" + parameter + ") " + 
					"GROUP BY msi.segment1, msi.organization_id,moq.subinventory_code, msi.inventory_item_status_code"
			);
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				excelContent.get(rs.getString(1)).setOmimQty(rs.getLong(2));
				excelContent.get(rs.getString(1)).setStatus(rs.getString(3));
			}
			
			
			conn.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Driver error: " + e.getMessage());
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("SQL Connection error: " + e1.getMessage());
		}
		
		
	} 
	
}
