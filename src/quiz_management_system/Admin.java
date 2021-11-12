/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    public void createUser()
    {
        ArrayList<User> userData = (ArrayList<User>) readFileInObject("student.txt");
        
        System.out.println("-----------Creating new user------------");
        Scanner sc = new Scanner(System.in);
        String inUsername = new String(), inPassword;
        int aLevel;
        
        int status = 0;
        while(status == 0)
        {
            System.out.println("Enter username:");
            inUsername = sc.next();        
            for(User i : userData)
            {
                if(inUsername.equals(""))
                {
                    System.out.println("You must enter a username.");
                }
                if(inUsername.equals(i.getUsername()))
                {
                    System.out.println("Username already exists.");
                }
            }
        }
        
        System.out.println("Enter password:");
        inPassword = sc.next();
        System.out.println("Set Access Level:");
        aLevel = sc.nextInt();
         User newUser = null;
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
