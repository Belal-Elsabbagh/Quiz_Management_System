package quiz_management_system;

import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Quiz
{
    private static int count = 0;
    private int quizID;
    private String quizTitle;
    private int nAttempts, nQuestions;
    private Calendar openTime, duration; //still uncertain of the data type.
    private Question[] questionBank;

    public Quiz()
    {
        this.quizID = ++count;
    }
     
    public void display()
    {
        System.out.println("-----------Displaying quiz properties------------");
        System.out.println("Quiz ID: " + quizID);
        System.out.println("Quiz Title: " + quizTitle);
        System.out.println("Number of questions: " + nQuestions);
        System.out.println("Number of attempts: " + nAttempts);
        System.out.println("-------------------------------------------------");
    }
    
    public void create()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------Creating a new quiz-------------------");
        System.out.println("Enter quiz title: "); quizTitle = sc.next();
        System.out.println("Enter number of questions: "); nQuestions = sc.nextInt();
        System.out.println("Enter number of attempts: "); nAttempts = sc.nextInt();
    }
    
    public Question[] generateQuizModel()
    {
        Question[] newModel;
        newModel = new Question[nQuestions];
        
        Random generator = new Random();
        int randIndex;
        for (int i = 0; i < nQuestions; i++)
        {
            randIndex = generator.nextInt(questionBank.length);
            newModel[i] = questionBank[randIndex];
        }
        return newModel;
    }

    public String getQuizTitle()
    {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle)
    {
        this.quizTitle = quizTitle;
    }

    public int getnAttempts()
    {
        return nAttempts;
    }

    public void setnAttempts(int nAttempts)
    {
        this.nAttempts = nAttempts;
    }

    public int getnQuestions()
    {
        return nQuestions;
    }

    public void setnQuestions(int nQuestions)
    {
        this.nQuestions = nQuestions;
    }

    public Calendar getOpenTime()
    {
        return openTime;
    }

    public void setOpenTime(Calendar openTime)
    {
        this.openTime = openTime;
    }

    public Calendar getDuration()
    {
        return duration;
    }

    public void setDuration(Calendar duration)
    {
        this.duration = duration;
    }
}
