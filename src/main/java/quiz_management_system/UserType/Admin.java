package quiz_management_system.UserType;

import quiz_management_system.DataHandler;
import quiz_management_system.Quiz_Management_System;

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
        super(username, password, 3);
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
                createUserConsole();
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


    /**
     * @deprecated not used with GUI
     */
    public void createUserConsole()
    {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword = null;
        int aLevel;
        User newUser;
        System.out.println("-----------Creating new user------------");


        boolean status = false;
        do
        {
            System.out.println("Enter username:");
            inUsername = sc.next();

            if (User.searchByUsername(inUsername, Access.NONE) != null)
            {
                System.err.println("Username already exists.");
                continue;
            }
            System.out.println("Enter password:");
            inPassword = sc.next();
            status = true;
        } while (!status);


        System.out.println("Set Access Level.\n 1 for student\n2 for teacher\n3 for Admin:");
        aLevel = sc.nextInt();
        // TODO Set an ID for the user
        if(aLevel == 1)
        {
            newUser = new Student(inUsername, inPassword, Access.STUDENT);
            DataHandler.userData.add(newUser);
        }
        else if (aLevel == 2)
        {
            newUser = new Teacher(inUsername, inPassword, Access.TEACHER);
            newUser.setUserID(2 * 1000 + DataHandler.userData.size() + 1);
            DataHandler.userData.add(newUser);
        }
        else if (aLevel == 3)
        {
            newUser = new Teacher(inUsername, inPassword, Access.ADMIN);
            newUser.setUserID(2 * 1000 + DataHandler.userData.size() + 1);
            DataHandler.userData.add(newUser);
        }
    }
}
