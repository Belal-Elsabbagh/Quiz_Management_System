package quiz_management_system;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * @author belsa
 */
public class Admin extends User
{
    public Admin(String username, String password)
    {
        super(username, password, Access.ADMIN);
    }

    @Override
    public int listMenu()
    {
        Scanner sc = new Scanner(in);
        out.println("*****Logged in as " + Quiz_Management_System.getActiveUser().getUsername() + "*****");
        out.println("1. View all users.\n2. Create new User.\n3. Edit user.\n4. Remove user.\nEnter -1 to quit\n");
        short n;
        n = sc.nextShort();
        switch (n)
        {
            case -1:
                return -1;
            case 1:
            {
                listUsers();
            }
            case 2:
            {
                createUser();
            }
            case 3:
            {

            }
        }
        return 1;
    }

    public void listUsers()
    {
        for (User i : DataHandler.userData)
            out.println(i.toString());
    }

    public void createUser()
    {
        System.out.println("-----------Creating new user------------");
        Scanner sc = new Scanner(System.in);

        String inUsername, inPassword = null;
        int aLevel;
        User newUser;


        int status = 0;
        do
        {
            System.out.println("Enter username:");
            inUsername = sc.next();
            newUser = new User(inUsername, null);

            if (DataHandler.studentData.contains(newUser) || DataHandler.teacherData.contains(newUser))
            {
                System.err.println("Username already exists.");
                continue;
            }
            System.out.println("Enter password:");
            inPassword = sc.next();
        } while(status == 1);
        

        System.out.println("Set Access Level.\n 1 for student\n2 for teacher:");
        aLevel = sc.nextInt();
        if(aLevel == 1)
        {
            //newUser = new Student(inUsername, inPassword, aLevel);
            newUser.setUserID(1000 + DataHandler.studentData.size() + 1);
            DataHandler.studentData.add((Student) newUser);
        }
        else if(aLevel == 2)
        {
            //newUser = new Teacher(inUsername, inPassword, aLevel);
            newUser.setUserID(2 * 1000 + DataHandler.teacherData.size() + 1);
            DataHandler.teacherData.add((Teacher) newUser);
        }
    }
}
