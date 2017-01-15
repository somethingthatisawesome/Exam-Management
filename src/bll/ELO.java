package bll;

import ell.Exam;

public class ELO {
	public Exam exam = new Exam(); 
	private FileManager fm = new FileManager();
	public Exam readFile(String path)
	{
		return fm.readDocFile(path);
	}
	public void ExportFile(String path,Exam exam)
	{
		fm.exportExam(path, exam);
	}
	public void randomizeExam()
	{
		
	}
}
