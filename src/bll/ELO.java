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
	public void ExportFile(String path,Exam exam,int number,boolean type)
	{
		
		if(type==false)
			for(int i=0;i<number;i++)
			{
			Exam ex_r = randomizeExam(exam);
			fm.exportExam(path+"/Đề "+(i+1)+".docx", ex_r);
			fm.exportAnswer(path+"/Đề "+(i+1)+"_Đáp án.docx", ex_r);
			};
		if(type==true)
			for(int i=0;i<number;i++)
			{
				Exam ex_r = randomizeExam(exam);
				String st = convertNumbertoAphabet(i);
			fm.exportExam(path+"/Đề "+st+".docx", ex_r);
			fm.exportAnswer(path+"/Đề "+st+"_Đáp án.docx", ex_r);
			};
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
	public String convertNumbertoAphabet(int number)
	{
		String str = Character.toString((char) (number+65));
		return str;
	}
}
