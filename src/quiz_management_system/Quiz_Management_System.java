package quiz_management_system;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Scanner;

public class Quiz_Management_System {

    /**
     *
     * @param args
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException, ParseException {
       LocalTime date = LocalTime.now();
       System.out.println(date);

        FileHandler data = new FileHandler();
        Scanner sc = new Scanner(System.in);
        System.out.println("*****Quiz Management System*****");
        System.out.println("Enter user:\n1. Student.\n2. Teacher.");
        int type = sc.nextInt();
        Student activeS;Teacher activeT;
        if (type == 1)
        {
            activeS = studentLogin(data);
            while(activeS != null)
            {
                if(activeS.listStudentMenu(data) == 0)
                    break;
            }
        }
        else if (type == 2)
        {
            activeT = teacherLogin(data);
            while(activeT != null)
            {
               if(activeT.listTeacherMenu(data) == 0)
                   break;
                       data.save();
            }
                
        }
        testStudent(data);
        testTeacher(data);
        testCreateQuiz(data);
        data.save();
        
    }

    public static void testCreateQuiz(FileHandler data) throws ParseException {
        Quiz q1;
        q1 = new Quiz();
        Teacher activeTeacher = new Teacher("Ali", "123");
        activeTeacher.listTeacherMenu(data);
    }

    public static void testStudent(FileHandler data) {
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

    public static void testTeacher(FileHandler data) {
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

    public static Student studentLogin(FileHandler data)
    {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword;

        System.out.println("Enter username:");
        inUsername = sc.next();
        System.out.println("Enter password:");
        inPassword = sc.next();

        Student newStudent = null;
        for (Iterator<Student> it = data.studentData.iterator(); it.hasNext();) {
            Student i = it.next();
            if (inUsername.equals(i.getUsername())) {
                if (inPassword.equals(i.getPassword())) {
                    newStudent = i;
                    System.out.println("Login Successful");
                    break;
                } else {
                    System.err.println("Incorrect password.");
                }
            } else {
                System.err.println("User not found.");
            }
        }
        return newStudent;
    }

    public static Teacher teacherLogin(FileHandler data) {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword;

        System.out.println("Enter username:");
        inUsername = sc.next();
        System.out.println("Enter password:");
        inPassword = sc.next();

        Teacher newTeacher = null;
        for (Teacher i : data.teacherData) {
            if (inUsername.equals(i.getUsername())) {
                if (inPassword.equals(i.getPassword())) {
                    newTeacher = i;
                    System.out.println("Login Successful");
                    break;
                } else {
                    System.err.println("Incorrect password.");
                    break;
                }
            } else {
                System.err.println("User not found.");
            }
        }
        return newTeacher;
    }
}
