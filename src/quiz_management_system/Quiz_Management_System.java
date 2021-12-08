package quiz_management_system;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Scanner;

import static quiz_management_system.Student.studentLogin;
import static quiz_management_system.Teacher.teacherLogin;
import static quiz_management_system.Admin.adminLogin;

public class Quiz_Management_System
{
    public static void main(String[] args) throws ParseException
    {
        DataHandler data = new DataHandler();
        
        consoleLogin();

        data.save();
    }

    public static void consoleLogin() throws ParseException
    {
        DataHandler data = new DataHandler();

        LocalTime date = LocalTime.now();
        System.out.println(date);
        System.out.println("*****Quiz Management System*****");
        System.out.println("Enter user:\n1. Student.\n2. Teacher.\n3. Admin.");
        Scanner sc = new Scanner(System.in);
        int type = sc.nextInt();

        if (type == 1)
        {
            Student activeS = studentLogin();
            if (activeS == null)
                System.err.println("Login unsuccessful.");
            else
                activeS.listStudentMenu();
        }
        else if (type == 2)
        {
            Teacher activeT = teacherLogin();
            if (activeT == null)
                System.err.println("Login unsuccessful.");
            else
                activeT.listTeacherMenu();
        }
        else if (type == 3)
        {
            Admin activeA = adminLogin();
            if (activeA == null)
                System.err.println("Login unsuccessful.");
        }
    }
}
