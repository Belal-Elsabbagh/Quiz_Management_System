package quiz_management_system;

import java.util.Scanner;

/**
 *
 * @author belsa
 */
public class Admin extends User
{
    public Admin(String inUsername, String inPassword)
    {
        super(inUsername, inPassword, 3);
    }

    public void createUser(DataHandler data)
    {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword = "";
        int aLevel;

        System.out.println("-----------Creating new user------------");
        System.out.println("Enter User");
        System.out.println("Enter username:"); inUsername = sc.next();
        User newUser = new User(inUsername, inPassword);

        System.out.println("Enter password:");
        inPassword = sc.next();

        System.out.println("Set Access Level.\n 1 for student\n2 for teacher\n3 for admin:");
        aLevel = sc.nextInt();
        if(aLevel == 1)
        {
            newUser = new Student(inUsername, inPassword);
            newUser.setUserID(10000 + data.studentData.size() + 1);
            data.studentData.add((Student)newUser);
        }
        else if(aLevel == 2)
        {
            newUser = new Teacher(inUsername, inPassword);
            newUser.setUserID(20000 + data.teacherData.size() + 1);
            data.teacherData.add((Teacher)newUser);
        }
        else if(aLevel == 3)
        {
            newUser = new Admin(inUsername, inPassword);
            newUser.setUserID(30000 + data.adminData.size() + 1);
            data.adminData.add((Admin)newUser);
        }
    }
}
