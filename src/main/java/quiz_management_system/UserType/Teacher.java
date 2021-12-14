package quiz_management_system.UserType;

import quiz_management_system.DataHandler;
import quiz_management_system.Quiz;

import java.io.Serial;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Teacher extends User
{
    @Serial
    private static final long serialVersionUID = 1L;

    ArrayList<Quiz> createdQuizzes;

    public Teacher(String username, String password)
    {
        super(username, password);
        createdQuizzes = new ArrayList<>();
    }

    @Override
    public int listMenu() throws ParseException
    {
        System.out.println("----------Teacher Operations Main Menu-----------");
        System.out.println("1. List my quizzes.");
        System.out.println("2. Create new quiz.");
        System.out.println("3. review Quiz Grades");
        Scanner sc = new Scanner(System.in);
        short n;
        n = sc.nextShort();
        switch (n)
        {
            case -1:
                return 0;
            case 1:
            {
                listQuizzes();
                break;
            }
            case 2:
            {
                createNewQuiz();
                break;
            }
            case 3:
            {
                //reviewGrades();
                break;
            }
        }
        return 1;
    }

    public void listQuizzes()
    {
        for (Quiz i : createdQuizzes)
        {
            i.displayQuizProperties();
        }
    }

    public void createNewQuiz() throws ParseException
    {
        Quiz newQuiz = new Quiz();
        newQuiz.createQuiz(this);

        newQuiz.setQuizID(DataHandler.quizData.size());

        DataHandler.quizData.add(newQuiz);
        createdQuizzes.add(newQuiz);
    }

//    public void reviewGrades()
//    {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("please enter quiz ID : ");
//        while (!sc.hasNextInt())
//        {
//            System.err.println("INVALID INPUT.");
//            sc.next();
//        }
//        int inID = sc.nextInt();
//
//        Quiz newQuiz = Quiz.searchByID(inID);
//        if (newQuiz == null)
//        {
//            System.err.println("No Quiz Found.");
//            return;
//        }
//        for (Student i : DataHandler.userData)
//        {
//            for (Attempt j : i.getAttemptHistory())
//            {
//                if (j.getQuiz().equals(newQuiz))
//                {
//                    System.out.println(i.getUsername() + ": " + j.getResult());
//                }
//            }
//        }
//    }
}
