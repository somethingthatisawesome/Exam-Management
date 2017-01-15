import bll.FileManager;
import ell.Exam;

public class Program {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileManager fm = new FileManager();
		Exam exam = fm.readDocFile("D:/On-tap-trac-nghiem.docx");
		fm.exportExam("d:/test.docx", exam);
	}

}
