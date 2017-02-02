package ell;

import java.util.ArrayList;
import java.util.List;

public class Exam {
public List<Quiz> Quizs = new ArrayList<Quiz>();
public Exam clone()
{
	Exam ex = new Exam();
	for(Quiz q:Quizs)
	{
		Quiz quiz = new Quiz();
		for(Answer a: q.Answers)
		{
			Answer at = new Answer();
			at.result = a.result;
			at.value = a.value;
			quiz.Answers.add(at);
		}
		quiz.value = q.value;
		ex.Quizs.add(quiz);
	}
	
	return ex;
}
}
