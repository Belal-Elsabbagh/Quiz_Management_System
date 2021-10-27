package quiz_management_system;

import java.util.Calendar;
import java.util.Random;

public class Quiz
{
    private int quizID;
    private String quizTitle;
    private int nAttempts, nQuestions;
    private Calendar openTime, duration;
    private Question[] questionBank;
    
    public Question[] generateQuizModel()
    {
        Question[] newModel;
        newModel = new Question[nQuestions];
        
        for (int i = 0; i < nQuestions; i++)
        {
            int rnd = new Random().nextInt(questionBank.length);
            newModel[i] = questionBank[rnd];
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
