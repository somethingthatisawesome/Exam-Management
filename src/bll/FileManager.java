package bll;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;

import ell.Answer;
import ell.Exam;
import ell.Quiz;

public class FileManager {
private final String quizFm = "decimal";
private final String answerFm = "lowerLetter";
private XWPFDocument xdoc;
InputStream in = null;
CTAbstractNum abstractNum = null;
	//Read Docx Word File
	public FileManager()
	{
		try {
            in = new FileInputStream("numbering.xml");
            abstractNum = CTAbstractNum.Factory.parse(in);
        } catch (XmlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public Exam readDocFile(String path)
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
	public void exportExam(String path,Exam exam)
	{
		XWPFDocument doc = new XWPFDocument();
        doc.createNumbering();
        XWPFNumbering numbering=null;
        numbering = doc.createNumbering();
        for(Quiz quiz:exam.Quizs) {
        	XWPFParagraph para = doc.createParagraph();
            para.setNumID(addListStyle(abstractNum, doc, numbering));
            XWPFRun run=para.createRun();
            run.setText(quiz.value);
        }
        
        try {
            FileOutputStream out = new FileOutputStream(path);
            doc.write(out);
            out.close();
            in.close();
        } catch(Exception e) {}
	}
    private BigInteger addListStyle(CTAbstractNum abstractNum, XWPFDocument doc, XWPFNumbering numbering) {
        try {

            XWPFAbstractNum abs = new XWPFAbstractNum(abstractNum, numbering);
            BigInteger id = BigInteger.valueOf(0);
            boolean found = false;
            while (!found) {
                Object o = numbering.getAbstractNum(id);
                found = (o == null);
                if (!found)
                    id = id.add(BigInteger.ONE);
            }
            abs.getAbstractNum().setAbstractNumId(id);
            id = numbering.addAbstractNum(abs);
            return doc.getNumbering().addNum(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
