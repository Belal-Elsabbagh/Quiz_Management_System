package quiz_management_system;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Scanner;
import static quiz_management_system.Student.studentLogin;
import static quiz_management_system.Teacher.teacherLogin;

public class Quiz_Management_System {

    /**
     *
     * @param args
     * @throws java.lang.ClassNotFoundException
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws ClassNotFoundException, ParseException 
    {
       LocalTime date = LocalTime.now();
       System.out.println(date);

        DataHandler data = new DataHandler();
        Scanner sc = new Scanner(System.in);
        System.out.println("*****Quiz Management System*****");
        System.out.println("Enter user:\n1. Student.\n2. Teacher.");
        int type = sc.nextInt();
        Student activeS;Teacher activeT;
        if (type == 1)
        {
            activeS = studentLogin(data);
            while(activeS != null)
                if(activeS.listStudentMenu(data) == 0)
                    break;
        }
        else if (type == 2)
        {
            activeT = teacherLogin(data);
            while(activeT != null)
               if(activeT.listTeacherMenu(data) == 0)
                   break;
        }
        data.save();  
    }

    /********Test Functions (EXPERIMENTAL********/
    /**
     * @param data)*
     * @throws java.text.ParseException
     *******/
    public static void testCreateQuiz(DataHandler data) throws ParseException {
        Quiz q1;
        q1 = new Quiz();
        Teacher activeTeacher = new Teacher("Ali", "123");
        activeTeacher.listTeacherMenu(data);
    }
    public static void testStudent(DataHandler data) {
        Student u1, u2, u3, u4;
        u1 = new Student("Belal", "123");
        u2 = new Student("Habiba", "456");
        u3 = new Student("Mariam", "789");
        u4 = new Student("Lojain", "101112");

        u1.setUserID(data.studentData.size() + 1);
        data.studentData.add(u1);

        u2.setUserID(data.studentData.size() + 1);
        data.studentData.add(u2);

        u3.setUserID(data.studentData.size() + 1);
        data.studentData.add(u3);

        u4.setUserID(data.studentData.size() + 1);
        data.studentData.add(u4);
    }
    public static void testTeacher(DataHandler data) {
        Teacher u1, u2, u3, u4;
        u1 = new Teacher("Belal", "123");
        u2 = new Teacher("Habiba", "456");
        u3 = new Teacher("Mariam", "789");
        u4 = new Teacher("Lojain", "101112");

        u1.setUserID(data.teacherData.size() + 1);
        data.teacherData.add(u1);

        u2.setUserID(data.teacherData.size() + 1);
        data.teacherData.add(u2);

        u3.setUserID(data.teacherData.size() + 1);
        data.teacherData.add(u3);

        u4.setUserID(data.teacherData.size() + 1);
        data.teacherData.add(u4);
    }
}
