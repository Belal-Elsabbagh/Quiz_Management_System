package quiz_management_system;

import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Quiz
{
    private static int count = 0;
    private int quizID;
    private String quizTitle;
    private Teacher creator;
    private int nAttempts, nQuestions;
    private Calendar openTime, duration; //still uncertain of the data type.
    private Question[] questionBank;

    public class Question
    {
        private int questionID;
        private String prompt;
        private double grade;
        private Choice mcq;
        //Java doesn't include structs. Using a class to make MCQ easier.
        private class Choice
        {
            private int nChoices;
            private String[] choices;
            private String answerKey;
        
            public boolean checkAnswer(String inAnswer)
            {
                boolean result = false;
                for(int i = 0; i < nChoices; i++)
                {
                    if(inAnswer == answerKey)
                    {
                        result = true;
                        break;
                    }
                }
                return result;
            }
        }
        public Question()
        {
            
        }
    }

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
    }
    
    public void create()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------Creating a new quiz-------------------");
        System.out.println("Enter quiz title: "); quizTitle = sc.next();
        System.out.println("Enter number of questions: "); nQuestions = sc.nextInt();
        System.out.println("Enter number of attempts: "); nAttempts = sc.nextInt();
    }
    
    public void createQuestionBank()
    {
        System.out.println("-----------Question Bank Creator-------------------");
        
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
}
