package bll;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
private final String fontFamily = "Times New Roman";
private final int fontSize = 13;
private XWPFDocument xdoc;
private XWPFDocument exportdoc;
InputStream in = null;
CTAbstractNum abstractNum = null;
	//Read Docx Word File
	public FileManager()
	{
		try {
            in = new FileInputStream("resources/numbering.xml");
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
		try {
			exportdoc = new XWPFDocument(new FileInputStream("resources/template.docx"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		exportdoc.createNumbering();
        XWPFNumbering numbering=null;
        numbering = exportdoc.createNumbering();
        for(Quiz quiz:exam.Quizs) {
        	XWPFParagraph para = exportdoc.createParagraph();
        	para.setNumID(addListStyle(abstractNum,numbering));
        	para.getCTP().getPPr().getNumPr().addNewIlvl().setVal(BigInteger.valueOf(0));
            XWPFRun run=para.createRun();
            run.setText(quiz.value);
            for(Answer ans:quiz.Answers)
            {
            	XWPFParagraph para1 = exportdoc.createParagraph();
            	para1.setStyle("ListParagraph");
            	para1.setNumID(addListStyle(abstractNum,numbering));
            	para1.getCTP().getPPr().getNumPr().addNewIlvl().setVal(BigInteger.valueOf(1));
                XWPFRun run1=para1.createRun();
                run1.setText(ans.value);
            }
        }
        
        try {
            FileOutputStream out = new FileOutputStream(path);
            exportdoc.write(out);
            out.close();
            in.close();
        } catch(Exception e) {}
	}
    private BigInteger addListStyle(CTAbstractNum abstractNum, XWPFNumbering numbering) {
        try {

            XWPFAbstractNum abs = new XWPFAbstractNum(abstractNum, numbering);
            BigInteger id = BigInteger.valueOf(0);
            /*
            boolean found = false;
            while (!found) {
                Object o = numbering.getAbstractNum(id);
                found = (o == null);
                if (!found)
                    id = id.add(BigInteger.valueOf(1));
                //System.out.println(id );
            }*/
            abs.getAbstractNum().setAbstractNumId(id);
            id = numbering.addAbstractNum(abs);
            return exportdoc.getNumbering().addNum(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /*
    private BigInteger addListStyle(CTAbstractNum abstractNum)
    {
        try
        {
            XWPFNumbering numbering = xdoc.getNumbering();
            // generate numbering style from XML
            XWPFAbstractNum abs = new XWPFAbstractNum(abstractNum, numbering);

            // find available id in document
            BigInteger id = BigInteger.valueOf(41);
            /*
            boolean found = false;
            while (!found)
            {
                Object o = numbering.getAbstractNum(id);
                found = (o == null);
                if (!found)
                	{
                	id = id.add(BigInteger.ONE);
                	System.out.println(id);
                	}
                
            }
            // assign id
            abs.getAbstractNum().setAbstractNumId(id);
            // add to numbering, should get back same id
            id = numbering.addAbstractNum(abs);
            // add to num list, result is numid
            return exportdoc.getNumbering().addNum(id);           
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }*/
}
