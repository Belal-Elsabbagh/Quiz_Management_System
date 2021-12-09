package quiz_management_system;

import java.text.ParseException;
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

        consoleLogin();

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
}
