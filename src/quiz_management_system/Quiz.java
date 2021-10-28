package quiz_management_system;

import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class Quiz
{
    Scanner sc = new Scanner(System.in);
    private static int count = 0;
    private int quizID;
    private String quizTitle;
    private Teacher creator;
    private int nAttempts, nQuestions;
    private Calendar openTime, duration; //still uncertain of the data type.
    private Question[] questionBank;
    private int qBankSize;

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
            private short answerKeyIndex;

            private Choice()
            {
                this.nChoices = 0;
                choices = new String[2];
            }
            
            public void createMCQ()
            {
                System.out.println("-----------Creating MCQ------------------");
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter number of choices: "); nChoices = sc.nextShort();
                choices = new String[nChoices];
                for(int i = 0; i < nChoices; i++)
                {
                    System.out.println("Enter choice " + i + ": ");
                    choices[i] = sc.next();
                }
                System.out.println("Enter the index of right answer (count starts at 0): "); answerKeyIndex = sc.nextShort();
            }
            public boolean checkAnswer(short inAnswer)
            {
                boolean result = false;
                    if(inAnswer == answerKeyIndex)
                    {
                        result = true;
                    }
                return result;
            }
        }
        public void createQuestion()
        {
            System.out.println("-----------Creating a new question-----------");
            System.out.println("Enter prompt: "); prompt = sc.next();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter grade: "); grade = sc.nextDouble();
            mcq = new Choice();
            mcq.createMCQ();
        }
    }

    public Quiz()
    {
        this.quizID = ++count;
    }
     
    public void displayQuizProperties()
    {
        System.out.println("-----------Displaying quiz properties------------");
        System.out.println("Quiz ID: " + quizID);
        System.out.println("Quiz Title: " + quizTitle);
        System.out.println("Number of questions: " + nQuestions);
        System.out.println("Number of attempts: " + nAttempts);
    }
    
    public void createQuiz()
    {
        System.out.println("-----------Creating a new quiz-------------------");
        System.out.println("Enter quiz title: "); quizTitle = sc.nextLine();
        System.out.println("Enter number of questions: "); nQuestions = sc.nextInt();
        System.out.println("Enter number of attempts: "); nAttempts = sc.nextInt();
        System.out.println("Enter size of question bank: "); qBankSize = sc.nextInt();
        
        createQuestionBank();
    }
    
    public void createQuestionBank()
    {
        questionBank = new Question[qBankSize];
        Question tempQ;
        System.out.println("-----------Question Bank Creator-----------------");
        for(int i = 0; i < qBankSize; i++)
        {
            tempQ = new Question();
            tempQ.createQuestion();
            questionBank[i] = tempQ;
        }
        
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
