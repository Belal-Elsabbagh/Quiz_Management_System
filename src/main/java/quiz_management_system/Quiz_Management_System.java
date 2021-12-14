package quiz_management_system;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz_Management_System
{
    private static User activeUser;

    public static User getActiveUser()
    {
        return activeUser;
    }

    public static void main(String[] args) throws ParseException
    {
        DataHandler data = new DataHandler();
        activeUser = new User("", "");

        buildUserData();
        activeUser.listMenu();
//        consoleLogin();

        data.save();
    }

    public static void consoleLogin() throws ParseException
    {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword;
        System.out.println("*****Quiz Management System*****");
        System.out.println("Enter username:");
        inUsername = sc.next();
        System.out.println("Enter password:");
        inPassword = sc.next();
        activeUser = User.login(inUsername, inPassword);
        while (activeUser != null)
            activeUser.listMenu();
    }

    public static void buildUserData()
    {
        DataHandler.userData = new ArrayList<>();
        User u1 = new Student("Belal", "123");
        DataHandler.userData.add(u1);
        User u2 = new Teacher("Adel", "123");
        DataHandler.userData.add(u2);
        User u3 = new Admin("Mohamed", "123");
        DataHandler.userData.add(u3);
    }
}