package pll;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import bll.ELO;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class MainForm {
	private ELO elo = new ELO();
	private JFrame frame;
	private JTextField textField;
	private JButton btnExport;

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
		
		textField = new JTextField();
		textField.setBounds(134, 21, 259, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Choose File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setFileFilter(new FileNameExtensionFilter("Word Document", "docx"));
			        int returnValue = fileChooser.showOpenDialog(null);
			        if (returnValue == JFileChooser.APPROVE_OPTION) {
			          String selectedFile = fileChooser.getSelectedFile().toString();
			          textField.setText(selectedFile);
			        }
			        elo.exam = elo.readFile(textField.getText());
			}
		});
		btnNewButton.setBounds(394, 21, 100, 24);
		frame.getContentPane().add(btnNewButton);
		
		btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				elo.ExportFile("D:/test", elo.exam);
				
			}
		});
		btnExport.setBounds(405, 227, 89, 23);
		frame.getContentPane().add(btnExport);
		
		JButton btnExportRand = new JButton("Export Rand");
		btnExportRand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				elo.ExportFile("D:/test_rand", elo.randomizeExam(elo.exam));
			}
		});
		btnExportRand.setBounds(292, 227, 89, 23);
		frame.getContentPane().add(btnExportRand);
	}
}
