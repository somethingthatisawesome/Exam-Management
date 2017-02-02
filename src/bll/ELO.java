package bll;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import ell.Answer;
import ell.Exam;
import ell.Quiz;

public class ELO {
	public Exam exam = new Exam(); 
	private FileManager fm = new FileManager();
	public Exam readFile(String path)
	{
		return fm.readDocFile(path);
	}
	public void ExportFile(String path,Exam exam)
	{
		fm.exportExam(path+".docx", exam);
		fm.exportAnswer(path+"_answer.docx", exam);
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
