package quiz_management_system;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Scanner;
import static quiz_management_system.Student.studentLogin;
import static quiz_management_system.Teacher.teacherLogin;

public class Quiz_Management_System
{
    public static void main(String[] args) throws ParseException
    {
       LocalTime date = LocalTime.now();
       System.out.println(date);
        DataHandler data = new DataHandler();
        Scanner sc = new Scanner(System.in);

        System.out.println("*****Quiz Management System*****");
        System.out.println("Enter user:\n1. Student.\n2. Teacher.");
        int type = sc.nextInt();

        if (type == 1)
        {
            Student activeS = studentLogin(data);
            if(activeS == null)
                System.err.println("Login unsuccessful.");
            else
                do
                {

                }while(activeS.listStudentMenu(data) != 0);
        }
        else if (type == 2)
        {
            Teacher activeT = teacherLogin(data);
            if(activeT == null)
                System.err.println("Login unsuccessful.");
            else
                do
                {

                }while(activeT.listTeacherMenu(data) != 0);
        }
        data.save();  
    }
}
