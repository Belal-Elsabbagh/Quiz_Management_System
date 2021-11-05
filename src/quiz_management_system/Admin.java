/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz_management_system;

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
        StaticList userData = new StaticList();
        userData = (StaticList) readFileInObject("student.txt");
        
        System.out.println("-----------Creating new user------------");
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword;
        int aLevel;
        System.out.println("Enter username:");
        inUsername = sc.next();
        //check if username already exists
        System.out.println("Enter password:");
        inPassword = sc.next();
        System.out.println("Set Access Level:");
        aLevel = sc.nextInt();
        User newUser = new User(inUsername, inPassword, aLevel);
        
        //check if field is empty
        
        newUser.setUserID(userData.getMySize() + 1);
        userData.append(newUser);
    }
    public void removeUser()
    {
        
    }
    public void editUser()
    {
        
    }
}
