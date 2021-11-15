package quiz_management_system;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Teacher extends User
{

    ArrayList<Quiz> createdQuizzes;

    Teacher()
    {
        super(2);
        createdQuizzes = new ArrayList();
    }

    public Teacher(String username, String password)
    {
        super(username, password);
        createdQuizzes = new ArrayList();
    }

    public void listTeacherMenu(FileHandler data) throws ParseException
    {
        System.out.println("----------Teacher Operations Main Menu-----------");
        System.out.println("1. List my quizzes.");
        System.out.println("2. Create new quiz.");
        System.out.println("3. edit quiz.");
        System.out.println("4. remove quiz.");
        Scanner sc = new Scanner(System.in);
        short n;
        n = sc.nextShort();
        switch(n)
        {
            case 1: 
            {
                listQuizzes();
            }
            case 2:
            {
                createNewQuiz(data);
            }
            case 3:
            {

            }
        }    
    }

    public void listQuizzes()
    {
        for (Quiz i : createdQuizzes)
        {
            i.displayQuizProperties();
        }
    }

    public void createNewQuiz(FileHandler data) throws ParseException
    {
        Quiz newQuiz = new Quiz();
        newQuiz.createQuiz(this);
        
        data.quizData.add(newQuiz);
        createdQuizzes.add(newQuiz);
    }
}
