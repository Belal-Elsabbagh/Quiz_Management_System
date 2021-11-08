package quiz_management_system;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import static quiz_management_system.FileHandler.readFileInObject;
import static quiz_management_system.FileHandler.writeObjectToFile;

public class Quiz_Management_System
{

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException
    {
        testStructure();
    }
    public static void testCreateQuiz()
    {
        Quiz q1;
        q1 = new Quiz();
        Teacher activeTeacher = new Teacher();
        q1.createQuiz(activeTeacher);
        System.out.println(q1.toString());
    }
    public static void testStructure()
    {
        ArrayList<User> userData = new ArrayList();
        User u1, u2, u3, u4;
        u1 = new User("Belal", "123");
        u2 = new User("Habiba", "456");
        u3 = new User("Mariam", "789");
        u4 = new User("Lojain", "101112");
        
        u1.setUserID(userData.size() + 1);
        userData.add(u1);
        
        u2.setUserID(userData.size() + 1);
        userData.add(u2); 
        
        u3.setUserID(userData.size() + 1);
        userData.add(u3);
        
        u4.setUserID(userData.size() + 1);
        userData.add(u4);
        
        writeObjectToFile(userData, "student.txt");
        
        userData = new ArrayList();
        userData = (ArrayList <User>) readFileInObject("student.txt");
        for(User i : userData)
            System.out.println(i.toString());
    }
    public static void login()
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
//            if(newUser.getAccessLevel() == 0)
//            {
//                Student activeStudent = newUser.convert2Student();
//            }
//            else if(newUser.getAccessLevel() == 1)
//            {
//                Teacher activeTeacher = newUser.convert2Teacher();
//            }
            System.out.println("Login Successful");
        }
    }
}