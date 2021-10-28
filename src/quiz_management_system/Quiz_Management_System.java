package quiz_management_system;

import java.util.Scanner;

public class Quiz_Management_System
{
    public static void main(String[] args)
    {
        testCreateQuiz();
    }
    public static void testCreateQuiz()
    {
        Scanner sc = new Scanner(System.in);
        Quiz q1;
        q1 = new Quiz();
        q1.createQuiz();
        
        q1.displayQuizProperties();
    }
}
