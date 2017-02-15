package pll;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import bll.ELO;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MainForm {
	private ELO elo = new ELO();
	private JFrame frame;
	private JTextField inputText;
	private OutputForm outputform;
	private ButtonGroup bttgroup;
	private final ButtonGroup buttonGroup = new ButtonGroup();

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
		frame.setTitle("Tạo đề thi trắc nghiệm");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		inputText = new JTextField();
		inputText.setBounds(106, 22, 269, 25);
		frame.getContentPane().add(inputText);
		inputText.setColumns(10);
		
		JButton btnNewButton = new JButton("Ch\u1ECDn File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inputText.setText(getFileLocation());
			        
			}
		});
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		spinner.setBounds(156, 143, 89, 20);
		frame.getContentPane().add(spinner);
		
		btnNewButton.setBounds(377, 22, 117, 24);
		frame.getContentPane().add(btnNewButton);
		
		JRadioButton rdbtnAbc = new JRadioButton("A,B,C,....");
		rdbtnAbc.setSelected(true);
		buttonGroup.add(rdbtnAbc);
		
		rdbtnAbc.setBounds(42, 91, 109, 23);
		frame.getContentPane().add(rdbtnAbc);
		JRadioButton radioButton = new JRadioButton("1,2,3,...");
		buttonGroup.add(radioButton);
		rdbtnAbc.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
			}
		});
		radioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
			}
		});
		radioButton.setBounds(153, 91, 109, 23);
		frame.getContentPane().add(radioButton);
		//bttgroup.add(radioButton);
		//bttgroup.add(rdbtnAbc);
		JButton btnExportRand = new JButton("Xu\u1EA5t File");
		btnExportRand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fileLocation = inputText.getText();
				if(fileExist(fileLocation)==false)
				{
					return;
				}
				String outPutLocation = getDirectoryLocation();
				elo.exam = elo.readFile(inputText.getText());
				int number = (int)spinner.getValue();
				boolean type = false;
				if(rdbtnAbc.isSelected())
					type=true;
				elo.ExportFile(outPutLocation, elo.exam,number,type);
				 JOptionPane.showMessageDialog(null, "Hoàn Tất!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnExportRand.setBounds(383, 227, 96, 23);
		frame.getContentPane().add(btnExportRand);
		
		
		

		//bttgroup.add(rdbtnAbc);
		//bttgroup.add(radioButton);
		JLabel lblFileLocation = new JLabel("\u0110\u01B0\u1EDDng d\u1EABn");
		lblFileLocation.setBounds(28, 27, 68, 14);
		frame.getContentPane().add(lblFileLocation);
		
		JLabel lblChnKiuXut = new JLabel("Ch\u1ECDn ki\u1EC3u xu\u1EA5t t\u00EAn file:");
		lblChnKiuXut.setBounds(28, 66, 142, 14);
		frame.getContentPane().add(lblChnKiuXut);
		
		
		
		JLabel lblSLng = new JLabel("S\u1ED1 l\u01B0\u1EE3ng \u0111\u1EC1 thi");
		lblSLng.setBounds(42, 146, 104, 14);
		frame.getContentPane().add(lblSLng);
	}
	private String getFileLocation()
	{
		String location="";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText("Chọn");
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
