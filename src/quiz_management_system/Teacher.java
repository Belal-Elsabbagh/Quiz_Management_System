package quiz_management_system;

import java.util.ArrayList;

public class Teacher extends User
{

    ArrayList<Quiz> createdQuizzes;

    Teacher()
    {
        super(1);
    }

    public void listMainMenu()
    {
        System.out.println("----------Teacher Operations Main Menu-----------");
        System.out.println("1. List my quizzes.");
        System.out.println("2. Create new quiz.");
        System.out.println("3. edit quiz.");
        System.out.println("4. remove quiz.");
    }

    public void listQuizzes()
    {
        for (Quiz i : createdQuizzes)
        {
            i.displayQuizProperties();
        }
    }

    public void createNewQuiz()
    {
        Quiz newQuiz = new Quiz();
        newQuiz.createQuiz(this);

        createdQuizzes.add(newQuiz);
    }
}
