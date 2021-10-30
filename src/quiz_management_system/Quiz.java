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
//    private Calendar openTime, duration; //still uncertain of the data type.
    private Question[] questionBank;
    private int qBankSize;

    public Quiz()
    {
        this.quizID = ++count;
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

    public void displayQuizProperties()
    {
        System.out.println("-----------Displaying quiz properties------------");
        System.out.println("Quiz ID: " + quizID);
        System.out.println("Quiz Title: " + quizTitle);
        System.out.println("Number of questions: " + nQuestions);
        System.out.println("Number of attempts: " + nAttempts);
    }

    public void createQuiz(Teacher author)
    {
        Scanner sc = new Scanner(System.in);
        creator = author;
        
        System.out.println("-----------Creating a new quiz-------------------");
        System.out.println("Enter quiz title: ");
        quizTitle = sc.nextLine();
        System.out.println("Enter number of questions: ");
        nQuestions = sc.nextInt();
        System.out.println("Enter number of attempts: ");
        nAttempts = sc.nextInt();
        System.out.println("Enter size of question bank: ");
        qBankSize = sc.nextInt();

        createQuestionBank();
        
        System.out.println("Quiz created sucessfully.");
    }

    public void createQuestionBank()
    {
        questionBank = new Question[qBankSize];
        Question tempQ = new Question();
        
        System.out.println("-----------Question Bank Creator-----------------");
        for (int i = 0; i < qBankSize; i++)
        {
            tempQ.createQuestion();
            questionBank[i] = tempQ;
        }
        
        System.out.println("Question Bank created successfully.");
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
    
    public class Question
    {
        private int questionID;
        private String prompt;
        private double grade;
        protected Choice mcq;

        public void createQuestion()
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("-----------Creating a new question-----------");
            System.out.println("Enter prompt: ");
            prompt = sc.next();
            
            sc = new Scanner(System.in);
            System.out.println("Enter grade: ");
            grade = sc.nextDouble();
            
            mcq = new Choice();
            mcq.createMCQ();
            
            System.out.println("Question added successfully.");
        }

        public int getQuestionID()
        {
            return questionID;
        }
        public String getPrompt()
        {
            return prompt;
        }
        public double getGrade()
        {
            return grade;
        }
        public Choice getMcq()
        {
            return mcq;
        }

        public void displayQuestion()
        {
            System.out.println(prompt);
            for (int i = 0; i < mcq.nChoices; i++)
            {
                System.out.println( (i+1) + ". " + mcq.choices[i]);
            }      
        }
        
            public boolean checkAnswer(short inAnswer)
            {
                boolean result = false;
                if (inAnswer == mcq.getAnswerKeyIndex())
                {
                    result = true;
                }
                return result;
            }
                    
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

            public int getnChoices()
            {
                return nChoices;
            }

            public short getAnswerKeyIndex()
            {
                return answerKeyIndex;
            }
            
            public void createMCQ()
            {
                Scanner sc = new Scanner(System.in);
                
                System.out.println("-----------Creating MCQ------------------");
                System.out.println("Enter number of choices: ");
                nChoices = sc.nextShort();
                
                choices = new String[nChoices];
                
                for (int i = 0; i < nChoices; i++)
                {
                    System.out.println("Enter choice " + (i+1) + ": ");
                    sc = new Scanner(System.in);
                    choices[i] = sc.next();
                }
                System.out.println("Enter the index of right answer (count starts at 1): ");
                answerKeyIndex = sc.nextShort();
                
                System.out.println("Choices added successfully.");
            }


        }
    }
}
