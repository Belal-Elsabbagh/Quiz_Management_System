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
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword = null;
        int aLevel;
        User newUser;
        System.out.println("-----------Creating new user------------");


        int status = 0;
        do
        {
            System.out.println("Enter username:");
            inUsername = sc.next();
            newUser = new User(inUsername, null);

            if (DataHandler.userData.contains(newUser) || DataHandler.userData.contains(newUser))
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
            newUser.setUserID(1000 + DataHandler.userData.size() + 1);
            DataHandler.userData.add((Student) newUser);
        }
        else if(aLevel == 2)
        {
            //newUser = new Teacher(inUsername, inPassword, aLevel);
            newUser.setUserID(2 * 1000 + DataHandler.userData.size() + 1);
            DataHandler.userData.add((Teacher) newUser);
        }
    }
}
