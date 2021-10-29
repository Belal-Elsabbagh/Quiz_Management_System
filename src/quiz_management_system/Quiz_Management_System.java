package quiz_management_system;

import java.io.IOException;

public class Quiz_Management_System
{

    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {
        testCreateQuiz();
    }
    public static void testCreateQuiz()
    {
        Quiz q1;
        q1 = new Quiz();
        Teacher activeTeacher = new Teacher();
        q1.createQuiz(activeTeacher);
        q1.displayQuizProperties();
    }
}
