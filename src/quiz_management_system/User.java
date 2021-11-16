package quiz_management_system;

import java.io.Serializable;

public class User implements Serializable
{
    //default serialVersion id
    private static final long serialVersionUID = 1L;
    

    private int userID;
    protected String username;
    private String password;
    private int accessLevel;
    
    public User()
    {
        accessLevel = -1;
    }
    
    public User(int accessLevel)
    {
        this.accessLevel = accessLevel;
    }
    
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
    public User(User og)
    {
        this.userID = og.userID;
        this.username = og.username;
        this.password = og.password;
        this.accessLevel = og.accessLevel;
    }
    
    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    @Override
    public String toString()
    {
        return "User{" + "userID=" + userID + ", username=" + username + ", password=" + password + ", accessLevel=" + accessLevel + '}';
    }
    
    public int getUserID()
    {
        return userID;
    }

    public int getAccessLevel()
    {
        return accessLevel;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }  
}
