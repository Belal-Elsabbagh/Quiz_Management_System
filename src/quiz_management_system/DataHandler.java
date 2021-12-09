package quiz_management_system;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author belsa
 */
public class DataHandler
{
    public File student = new File("studentData.dat"),
                teacher = new File("teacherData.dat"), 
                quiz    = new File("quizData.dat"),
                admin   = new File("adminData.dat"),
                user = new File("userData.dat");
    public static ArrayList<Student> studentData;
    public static ArrayList<Teacher> teacherData;
    public static ArrayList<Admin> adminData;
    public static ArrayList<Quiz> quizData;
    public static ArrayList<User> userData;

    public DataHandler()
    {
        //studentData = (ArrayList <Student>) readFileInObject(student);
        //teacherData = (ArrayList <Teacher>) readFileInObject(teacher);
        //adminData   = (ArrayList <Admin>)   readFileInObject(admin);
        //quizData    = (ArrayList <Quiz>)    readFileInObject(quiz);
        userData    = (ArrayList<User>)     readFileInObject(user);
    }
    
    public void save()
    {
        //writeObjectToFile(studentData, student);
        //writeObjectToFile(teacherData, teacher);
        //writeObjectToFile(adminData, admin);
        writeObjectToFile(quizData, quiz);
        writeObjectToFile(userData, user);
    }
    
    public static void writeObjectToFile(Object out, File filepath)
    {
        try 
        {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(out);
            objectOut.close();
            fileOut.close();
            System.out.println("The Object was successfully written to a file");
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }
    public static Object readFileInObject(File filepath)
    {
        try 
        {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
 
            Object obj = objectIn.readObject();
 
            System.out.println("The Object has been read from the file");
            objectIn.close();
            fileIn.close();
            return obj;
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static User hasUser(User inUser)
    {
        for(User i : userData)
        {
            if(i.getUsername().equals(inUser.getUsername()))
                return i;
        }
        return null;
    }
}
