package bll;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import ell.Answer;
import ell.Exam;
import ell.Quiz;

public class ELO {
	public Exam exam; 
	private FileManager fm = new FileManager();
	public ELO()
	{
		if(exam==null)
		{
			exam = new Exam();
		}
	}
	public Exam readFile(String path)
	{
		return fm.readDocFile(path);
	}
	public void ExportMultipleFiles(String path,Exam exam,int size)
	{
		
	}
	public void ExportFile(String path,Exam exam,String docName)
	{
		fm.exportExam(path+"/"+docName+".docx", exam);
		fm.exportAnswer(path+"/"+docName+"_answer.docx", exam);
	}
	public Exam randomizeExam(Exam exam)
	{	
		Exam rand_ex = exam.clone();
		
		Collections.shuffle(rand_ex.Quizs);
		for(Quiz q:rand_ex.Quizs)
		{
			Collections.shuffle(q.Answers);
		}
		return rand_ex;
	}
}
