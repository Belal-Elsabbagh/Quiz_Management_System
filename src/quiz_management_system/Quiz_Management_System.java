package quiz_management_system;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Quiz_Management_System
{

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException
    {
        User newUser;
        newUser = new User("Hilary", "LingLing", 1);
        newUser.writeUserToFile();
        newUser = new User((User) newUser.readFileInUser());
        System.out.println(newUser.toString());

    }
    public static void testCreateQuiz()
    {
        Quiz q1;
        q1 = new Quiz();
        Teacher activeTeacher = new Teacher();
        q1.createQuiz(activeTeacher);
        System.out.println(q1.toString());
    }

    public void login()
    {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword;
        
        System.out.println("Enter username:");
        inUsername = sc.next();
        System.out.println("Enter password:");
        inPassword = sc.next();
        User newUser = new User(inUsername, inPassword);
        int status = -1;
        status = newUser.checkLogin();
        
        if(status == 0)
            System.out.println("User not found.");
        else if(status == 1)
            System.out.println("Incorrect password.");
        else if(status == 2)
        {
            if(newUser.getAccessLevel() == 0)
            {
                Student activeStudent = newUser.convert2Student();
            }
            else if(newUser.getAccessLevel() == 1)
            {
                Teacher activeTeacher = newUser.convert2Teacher();
            }
        }
    }
}