package quiz_management_system;

import java.util.ArrayList;
import java.util.Scanner;
import static quiz_management_system.FileHandler.readFileInObject;

/**
 *
 * @author belsa
 */
public class Admin
{
    public void createUser(ArrayList<User> userData)
    {        
        System.out.println("-----------Creating new user------------");
        Scanner sc = new Scanner(System.in);
        
        String inUsername = new String(), inPassword = new String();
        int aLevel;
        User newUser = null;

        
        int status = 0;
        do
        {
            System.out.println("Enter username:");
            inUsername = sc.next();
            newUser = new User(inUsername, inPassword);
            status = newUser.checkLogin(userData);
            if(status == 1)
            {
                System.err.println("Username already exists.");
                continue;
            }
            System.out.println("Enter password:");
            inPassword = sc.next();
        }while(status == 1);
        

        System.out.println("Set Access Level.\n 1 for student\n2 for teacher:");
        aLevel = sc.nextInt();
        if(aLevel == 1)
        {
            newUser = new Student(inUsername, inPassword, aLevel);
            newUser.setUserID(1*1000 + userData.size() + 1);
        }
        else if(aLevel == 2)
        {
            newUser = new Teacher(inUsername, inPassword, aLevel);
            newUser.setUserID(2*1000 + userData.size() + 1);
        }

        userData.add(newUser);
    }
    public void removeUser()
    {
        
    }
    public void editUser()
    {
        
    }
}
