package quiz_management_system;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;

public class User implements Serializable, Interactive
{
    @Serial
    private static final long serialVersionUID = 1L;

    private int userID;
    private Access accessLevel;
    private String username, password;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.accessLevel = Access.NONE;
    }

    public User(String username, String password, Access accessLevel)
    {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public static User login(String inUsername, String inPassword)
    {
        User newUser = new User(inUsername, inPassword);
        User searchResult = DataHandler.hasUser(newUser);

        if (searchResult == null)
        {
            System.err.println("No User Found.");
            return null;
        }

        if (!newUser.password.equals(searchResult.password))
        {
            System.err.println("Incorrect password.");
            return null;
        }

        System.out.println("Login Successful");
        return searchResult;
    }

    public String getUsername()
    {
        return username;
    }

    public int getUserID()
    {
        return userID;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public String toString()
    {
        return "User{" + "userID=" + userID + ", username=" + username + ", password=" + password + ", accessLevel=" + accessLevel + '}';
    }

    @Override
    public int listMenu() throws ParseException
    {
        return 0;
    }

    enum Access
    {
        NONE, STUDENT, TEACHER, ADMIN;
    }
}
