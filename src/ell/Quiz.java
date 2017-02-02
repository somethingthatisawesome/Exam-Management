package ell;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
	public List<Answer> Answers= new ArrayList<Answer>();
	public String value; 
	public String answer()
	{
		int len = Answers.size();
		String a="N/A";
		for(int i=0;i<len;i++)
		{
			if(Answers.get(i).result==true)
			{
				int ascii = 65+i;
				char as = (char)ascii;
				a = ""+as;
			}
		}
		return a;
	}
}
