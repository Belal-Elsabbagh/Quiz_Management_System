package quiz_management_system;

import java.io.Serializable;
import java.text.ParseException;

public class User implements Serializable, Interactive
{
    private static final long serialVersionUID = 1L;

    private int userID;
    private final int accessLevel;
    private String username,password;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.accessLevel = -1;
    }

    public User(String username, String password, int accessLevel)
    {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public String getUsername()
    {
        return username;
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

    public static User login(String inUsername, String inPassword)
    {
        User newUser = new User(inUsername, inPassword);
        User searchResult = DataHandler.hasUser(newUser);

        if(searchResult == null)
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

    @Override
    public int listMenu() throws ParseException
    {
        return 0;
    }
}
