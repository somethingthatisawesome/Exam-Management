package bll;

import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import ell.Answer;
import ell.Exam;
import ell.Quiz;

public class FileManager {
private final String quizFm = "decimal";
private final String answerFm = "lowerLetter";
private XWPFDocument xdoc;
	//Read Docx Word File
	public Exam ReadDocFile(String path)
	{
		// Initialize Variable
		Exam exam = new Exam();
		//Actions
		 try {
			 //Initialize the Input File
		       FileInputStream fis = new FileInputStream(path);
		       xdoc = new XWPFDocument(OPCPackage.open(fis));
		       List<XWPFParagraph> paragraphList =  xdoc.getParagraphs();
		       //for (XWPFParagraph paragraph: paragraphList){
			   //System.out.println(paragraph.getText());
		    	   
		       //}
		       int size = paragraphList.size();
		       for(int i=0;i<size;i++)
		       {
		    	   
		    	   XWPFParagraph parapgraphHolder = paragraphList.get(i);
		    	   if(parapgraphHolder.getNumFmt()==quizFm)
		    	   {
		    		   Quiz quiz = new Quiz();
		    		   quiz.value = parapgraphHolder.getText();
		    		   
		    		   do {
		    			   i++;
			    		   parapgraphHolder = paragraphList.get(i);
		    			   if(parapgraphHolder.getNumFmt()==answerFm)
		    			   {
		    				   
		    				   Answer ans = new Answer();
		    				   ans.value = parapgraphHolder.getText();
		    				   if(parapgraphHolder.getRuns().get(0).isBold())
		    				   {
		    					   ans.result = true;
		    				   }
		    				   quiz.Answers.add(ans);
		    			   }
		    		   } while(i<size-1&&parapgraphHolder.getNumFmt()!=quizFm);
		    		   i--;
		    		   exam.Quizs.add(quiz);
		    		   
		    	   }
		       }
		     } catch(Exception ex) {
			   ex.printStackTrace();
		     } 
		return exam;
	}
	public void ExportExam(String path,Exam exam)
	{
		
	}
}
