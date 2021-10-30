package quiz_management_system;

import java.io.IOException;

public class Quiz_Management_System
{

    public static void main(String[] args)
    {
        testCreateAndAttemptQuiz();
    }
    public static void testCreateAndAttemptQuiz()
    {
        Quiz q1;
        q1 = new Quiz();
        Teacher activeTeacher = new Teacher();
        q1.createQuiz(activeTeacher);
        
        Student user = new Student();
        user.startAttempt(q1);
    }
}
