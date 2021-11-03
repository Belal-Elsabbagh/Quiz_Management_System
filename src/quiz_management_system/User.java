package quiz_management_system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable
{
    //default serialVersion id
    private static final long serialVersionUID = 1L;
    

    private int userID;
    private String username;
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
    
    public int checkLogin()
    {
        int status = 0;
        //load data into user array;
        User[] userData;
        userData = new User[1000];// just for testing. Change when figuring out how to manage files.
        
        for(User user : userData)
        {
            if(username.equals(user.getUsername()))
            {
                status = 1;
                if(password.equals(user.getPassword()))
                {
                    status = 2;
                    this.userID = user.getUserID();
                    this.accessLevel = user.getAccessLevel();
                }
            }
        }
        //traverse through user records
        //check if username exists, status = 1
        //check if corresponding password is equal to input, status = 2
        return status;
    }
    
    public Student convert2Student()
    {
        Student s = new Student();
        //load Student data;
        return s;
    }
    public Teacher convert2Teacher()
    {
        Teacher t = new Teacher();
        //load Teacher data;
        return t;
    }
}
