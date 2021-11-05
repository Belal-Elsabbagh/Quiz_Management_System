package quiz_management_system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import static quiz_management_system.FileHandler.readFileInObject;

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
    
    public int checkLogin()
    {
        int status = 0;
        //load data into StaticList;
        StaticList userData;
        userData = (StaticList) readFileInObject("student.txt");

        
        for(int i = 0; i < userData.getMySize(); i++)
        {
            User record;
            record = (User) userData.returnByIndex(i);
            if(username.equals(record.getUsername()))
            {
                status = 1;
                if(password.equals(record.getPassword()))
                {
                    status = 2;
                    this.userID = record.getUserID();
                    this.accessLevel = record.getAccessLevel();
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
