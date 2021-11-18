package quiz_management_system;

import java.io.Serial;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import quiz_management_system.Student.Attempt;

public class Teacher extends User
{

    @Serial
    private static final long serialVersionUID = 1L;

    ArrayList<Quiz> createdQuizzes;

    public Teacher(String username, String password)
    {
        super(username, password);
        createdQuizzes = new ArrayList();
    }

    public int listTeacherMenu(DataHandler data) throws ParseException
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
                createNewQuiz(data);
                break;
            }
            case 3:
            {
                reviewGrades(data);
                break;
            }
        }
        return 1;
    }
    
    public Teacher searchByID(int uID, DataHandler data)
    {
        Teacher x = null;
        for(Teacher i : data.teacherData)
        {
            if(i.getUserID() == uID)
            {
                x = i;
            }
        }
        return x;
    }
    
    public void listQuizzes()
    {
        for (Quiz i : createdQuizzes)
        {
            i.displayQuizProperties();
        }
    }

    public void createNewQuiz(DataHandler data) throws ParseException
    {
        Quiz newQuiz = new Quiz();
        newQuiz.createQuiz(this);

        data.quizData.add(newQuiz);
        createdQuizzes.add(newQuiz);
    }

    public void reviewGrades(DataHandler data)
    {
        System.out.println("please enter quiz ID : ");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Quiz ID: ");
        Quiz newQuiz = null;
        newQuiz = newQuiz.searchByID(sc.nextInt(), data);
        if (newQuiz == null)
        {
            System.err.println("No Quiz Found.");
        }
        else
        {
            for (Student i : data.studentData)
            {
                for (Attempt j : i.getAttemptHistory())
                {
                    if (j.getQuiz().equals(newQuiz))
                    {
                        System.out.println(i.getUsername() + ": " + j.getResult());
                    }
                }
            }
        }
    }

    public static Teacher teacherLogin(DataHandler data)
    {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword;

        System.out.println("Enter username:");
        inUsername = sc.next();
        System.out.println("Enter password:");
        inPassword = sc.next();

        Teacher newTeacher = null;
        for (Teacher i : data.teacherData)
        {
            if (inUsername.equals(i.getUsername()))
            {
                if (inPassword.equals(i.getPassword()))
                {
                    newTeacher = i;
                    System.out.println("Login Successful");
                    break;
                }
                else
                {
                    System.err.println("Incorrect password.");
                    break;
                }
            }
            else
            {
                System.err.println("User not found.");
            }
        }
        return newTeacher;
    }
}
