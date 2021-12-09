package quiz_management_system;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Quiz implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    private int quizID;
    private String quizTitle;
    private Teacher creator;
    private int nAttempts, nQuestions;
    public Date quizDate;
    public LocalTime openTime;
    private ArrayList<Question> questionBank;
    private long totalSec;

    public Quiz()
    {
        questionBank = new ArrayList<>();
    }

    public LocalTime openTime()
    {
        return openTime;
    }

    public static Quiz searchByID(int qID)
    {
        for (Quiz i : DataHandler.quizData)
        {
            if (i.getQuizID() == qID)
            {
                return i;
            }
        }
        return null;
    }

    public int getQuizID()
    {
        return quizID;
    }

    public String getQuizTitle()
    {
        return quizTitle;
    }

    public int getnQuestions()
    {
        return nQuestions;
    }

    public long totalSec()
    {
        return quizID;
    }

    public Date quizDate()
    {
        return quizDate;
    }

    public long getTotalSec()
    {
        return totalSec;
    }

    public void setTotalSec(long TotalSec)
    {
        this.totalSec = TotalSec;
    }

    public void setQuizID(int quizID)
    {
        this.quizID = quizID;
    }

    public void displayQuizProperties()
    {
        System.out.println("-----------Displaying quiz properties------------");
        System.out.println("Quiz ID: " + quizID);
        System.out.println("Quiz Title: " + quizTitle);
        System.out.println("Number of questions: " + nQuestions);
        System.out.println("Number of attempts: " + nAttempts);
        System.out.println("Size of question bank: " + questionBank.size());
    }

    @Override
    public String toString()
    {
        return "Quiz{" + "quizID=" + quizID + ", quizTitle=" + quizTitle + ", nAttempts=" + nAttempts + ", nQuestions=" + nQuestions + ", questionBank.size()=" + questionBank.size() + '}';
    }

    public void createQuiz(Teacher author) throws ParseException
    {
        Scanner sc = new Scanner(System.in);
        creator = author;

        System.out.println("-----------Creating a new quiz-------------------");
        System.out.println("Enter quiz title: ");
        quizTitle = sc.nextLine();
        System.out.println("Enter number of questions: ");
        while (!sc.hasNextInt())
        {
            System.err.println("INVALID INPUT.");
            sc.next();
        }
        nQuestions = sc.nextInt();
        System.out.println("Enter number of attempts: ");
        while (!sc.hasNextInt())
        {
            System.err.println("INVALID INPUT.");
            sc.next();
        }
        nAttempts = sc.nextInt();

        createQuestionBank();
        quiztime();

        System.out.println("Quiz created successfully.");
    }

    public void createQuestionBank()
    {
        questionBank = new ArrayList();
        Scanner sc = new Scanner(System.in);

        System.out.println("-----------Question Bank Creator-----------------");
        int i = 0;

        while (!sc.hasNextInt())
        {
            Question tempQ = new Question();
            tempQ.createQuestion();
            tempQ.questionID = i + 1;
            questionBank.add(tempQ);
            System.out.println("stop?");
            while (!sc.hasNextInt())
            {
                System.err.println("INVALID INPUT.");
                sc.next();
            }
            i = sc.nextInt();
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
            randIndex = generator.nextInt(questionBank.size());

            newModel[i] = questionBank.get(randIndex);
        }
        return newModel;
    }

    public void quiztime()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Date (day-month-year):");
        SimpleDateFormat datein = new SimpleDateFormat("dd-MM-yyyy");

        String takeDate = sc.nextLine();

        try
        {
            Date date = datein.parse(takeDate);
            System.out.println("The quiz will be on: " + new SimpleDateFormat("dd-MM-yyyy").format(date));
            quizDate = datein.parse(takeDate);

        }
        catch (ParseException e)
        {
            System.out.println("Invalid input");
        }
        System.out.println("Enter quiz time (hr:min:sec):");
        System.out.println("as in the format 00:00:00");
        String time = sc.next();
        try
        {
            LocalTime Time = LocalTime.parse(time);
            System.out.println("The quiz time: " + Time);
            openTime = LocalTime.parse(time);

        }
        catch (Exception e)
        {
            System.out.println("Invalid input");
        }
        System.out.println("Enter quiz duration:");
        System.out.println("Hour:");
        int hour;
        hour = sc.nextInt();
        System.out.println("Min:");
        int min;
        min = sc.nextInt();
        System.out.println("Sec:");
        int sec;
        sec = sc.nextInt();
        System.out.println("The quiz duration will be:" + hour + ":" + min + ":" + sec);
        totalSec = ((long) hour * 60 * 60) + (min * 60) + sec;
    }

    public class Question implements Serializable
    {
        private int questionID;
        private String prompt;
        private double grade;
        private Choice mcq;

        public Question()
        {

        }

        public void createQuestion()
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("-----------Creating a new question-----------");
            System.out.println("Enter prompt: ");
            prompt = sc.next();

            sc = new Scanner(System.in);
            System.out.println("Enter grade: ");
            while (!sc.hasNextDouble())
            {
                System.err.println("INVALID INPUT.");
                sc.next();
            }
            grade = sc.nextDouble();

            mcq = new Choice();
            mcq.createMCQ();

            System.out.println("Question added successfully.");
        }

        public double getGrade()
        {
            return grade;
        }

        public void displayQuestion()
        {
            System.out.println(prompt);
            for (int i = 0; i < mcq.nChoices; i++)
            {
                System.out.println((i + 1) + ". " + mcq.choices[i]);
            }
        }

        public boolean checkAnswer(short inAnswer)
        {
            return inAnswer == mcq.answerKeyIndex;
        }
    }

    class Choice implements Serializable
    {

        private int nChoices;
        private String[] choices;
        private int answerKeyIndex;

        private Choice()
        {
            this.nChoices = 0;
            choices = new String[2];
        }

        public void createMCQ()
        {

            Scanner sc = new Scanner(System.in);

            System.out.println("-----------Creating MCQ------------------");

            System.out.println("Enter number of choices: ");
            while (!sc.hasNextShort())
            {
                System.err.println("INVALID INPUT.");
                sc.next();
            }
            nChoices = sc.nextShort();

            choices = new String[nChoices];

            for (int i = 0; i < nChoices; i++)
            {
                System.out.println("Enter choice " + i + ": ");
                choices[i] = sc.next();
            }

            answerKeyIndex = nChoices;
            while (answerKeyIndex > nChoices - 1)
            {
                System.out.println("Enter the index of right answer (count starts at 0): ");
                while (!sc.hasNextShort())
                {
                    System.err.println("INVALID INPUT.");
                    sc.next();
                }
                answerKeyIndex = sc.nextShort();
                if (answerKeyIndex > nChoices - 1)
                {
                    System.out.println("Error: The number of choice doesn't exist");
                }
            }
            System.out.println("Choices added successfully.");
        }
    }
}
