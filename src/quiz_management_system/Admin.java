package quiz_management_system;

import java.util.Scanner;

/**
 *
 * @author belsa
 */
public class Admin extends User
{  
    public static Admin adminLogin(DataHandler data)
    {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword;

        System.out.println("Enter username:");
        inUsername = sc.next();
        System.out.println("Enter password:");
        inPassword = sc.next();

        Admin newAdmin = null;
        for (Admin i : data.adminData)
        {
            if (inUsername.equals(i.getUsername()))
            {
                if (inPassword.equals(i.getPassword()))
                {
                    newAdmin = i;
                    System.out.println("Login Successful");
                    break;
                }
                else
                {
                    System.err.println("Incorrect password.");
                    break;
                }
            }
            else
            {
                System.err.println("User not found.");
            }
        }
        return newAdmin;
    }
        
    public void createUser(DataHandler data)
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
            
            if(data.studentData.contains(newUser) || data.teacherData.contains(newUser))
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
            //newUser = new Student(inUsername, inPassword, aLevel);
            newUser.setUserID(1*1000 + data.studentData.size() + 1);
            data.studentData.add((Student)newUser);
        }
        else if(aLevel == 2)
        {
            //newUser = new Teacher(inUsername, inPassword, aLevel);
            newUser.setUserID(2*1000 + data.teacherData.size() + 1);
            data.teacherData.add((Teacher)newUser);
        }
    }
    public void removeUser(DataHandler data)
    {
        
    }
    public void editUser(DataHandler data)
    {
        
    }
}
