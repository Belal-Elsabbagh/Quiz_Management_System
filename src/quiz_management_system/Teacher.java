package quiz_management_system;

import java.util.ArrayList;
import java.util.Scanner;

public class Teacher extends User
{
    private static final long serialVersionUID = 1L;
    
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

    public int listTeacherMenu(FileHandler data)
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
            case -1:
                return 0;
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
        return 1;
    }

    public void listQuizzes()
    {
        for (Quiz i : createdQuizzes)
        {
            i.displayQuizProperties();
        }
    }

    public void createNewQuiz(FileHandler data)
    {
        Quiz newQuiz = new Quiz();
        newQuiz.createQuiz(this);
        newQuiz.setQuizID(data.quizData.size()+1);
        data.quizData.add(newQuiz);
        createdQuizzes.add(newQuiz);
    }
}
