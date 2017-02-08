package pll;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import bll.ELO;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class MainForm {
	private ELO elo = new ELO();
	private JFrame frame;
	private JTextField inputText;
	private JButton btnExport;
	private OutputForm outputform;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 520, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		inputText = new JTextField();
		inputText.setBounds(52, 21, 259, 25);
		frame.getContentPane().add(inputText);
		inputText.setColumns(10);
		
		JButton btnNewButton = new JButton("Choose File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputText.setText(getFileLocation());
			        
			}
		});
		btnNewButton.setBounds(323, 21, 136, 24);
		frame.getContentPane().add(btnNewButton);
		
		btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileLocation = inputText.getText();
				//Check if file exist or not
				if(fileExist(fileLocation)==false)
				{
					return;
				}
				
				//
				elo.exam = elo.readFile(fileLocation);
				String outputLocation="";
				outputLocation = getDirectoryLocation();
				elo.ExportFile(outputLocation, elo.exam,"test");
				
			}
		});
		btnExport.setBounds(405, 227, 89, 23);
		frame.getContentPane().add(btnExport);
		
		JButton btnExportRand = new JButton("Export Rand");
		btnExportRand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fileLocation = inputText.getText();
				if(fileExist(fileLocation)==false)
				{
					return;
				}
				String outPutLocation = getDirectoryLocation();
				elo.exam = elo.readFile(inputText.getText());
				elo.ExportFile(outPutLocation, elo.randomizeExam(elo.exam),"test_random");
			}
		});
		btnExportRand.setBounds(228, 227, 153, 23);
		frame.getContentPane().add(btnExportRand);
	}
	private String getFileLocation()
	{
		String location="";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText("Choose");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Word Document", "docx"));
	        int returnValue = fileChooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          String selectedFile = fileChooser.getSelectedFile().toString();
	          location=selectedFile;
	        }
	        return location;
	}
	private String getDirectoryLocation()
	{
		String location="";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText("Save");
		//fileChooser.setFileFilter(new FileNameExtensionFilter("Word Document", "docx"));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        int returnValue = fileChooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          String selectedFile = fileChooser.getSelectedFile().toString();
	          location=selectedFile;
	        }
	        return location;
	}
	private boolean fileExist(String path)
	{
		File varTmpDir = new File(path);
		return varTmpDir.exists();
	}
}
