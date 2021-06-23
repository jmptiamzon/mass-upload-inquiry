package com.sprint.massupload.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sprint.massupload.controller.Controller;

public class View extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField filenameField;
	private JButton chooseBtn, submitBtn;
	private JPanel panel;
	private GridBagConstraints gbc;
	private Insets inset;
	private JFileChooser filechooser;
	private Controller controller;
	private String filepath = "";
	
	public View() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		controller = new Controller();
		filechooser = new JFileChooser();
		inset = new Insets(5, 5, 5, 5);
		gbc = new GridBagConstraints();
		panel = new JPanel(new GridBagLayout());
		
		filenameField = new JTextField(20);
		filenameField.setEnabled(false);
		submitBtn = new JButton("Submit");
		chooseBtn = new JButton("Choose File");
		
		setLayout(new BorderLayout());
		setResizable(false);
		setTitle("Mass Upload Inquiry QTY and Status");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(panel, BorderLayout.CENTER);
		setComponent(filenameField, panel, 0.5, 0, 0, 0, 0);
		setComponent(chooseBtn, panel, 0.5, 1, 0, 0, 0);
		setComponent(submitBtn, panel, 1, 0, 1, 0, 0);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	public void setComponent(Component component, JPanel panel, double weightx, int gridx, int gridy, int ipady, int ipadx) {
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = inset;
		gbc.weightx = weightx;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.ipady = ipady;
		gbc.ipadx = ipadx;
		panel.add(component, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == submitBtn) {
			controller.runApp(filepath);
		}
		
		if (event.getSource() == chooseBtn) {
			filechooser.setAcceptAllFileFilterUsed(false);
			filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Select xlsx files", "xlsx"));
			
			int flag = filechooser.showOpenDialog(this);
			
			if (flag == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				filepath = Paths.get(file.getPath()).toString();
				filenameField.setText(filepath);
			}
		}
		
	}

}
