import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bll.FileManager;
import ell.Answer;
import ell.Exam;

public class Program {


	public static void main(String[] args) {
		   
		int yourInt = 25;
		String result="";
		while(yourInt>26)
		{
			int number=yourInt/26;
			yourInt=yourInt-number*26;
			result += Character.toString((char) (number+64));
		}
		System.out.println(result);
	  }

}
