package quiz_management_system;

import java.io.*;
import java.util.ArrayList;

/**
 * @author belsa
 */
public class DataHandler
{
    public static ArrayList<Quiz> quizData;
    public static ArrayList<User> userData;
    public File quiz = new File("quizData.dat"),
            user = new File("userData.dat");

    public DataHandler()
    {
        //quizData = (ArrayList<Quiz>) readFileInObject(quiz);
        //userData = (ArrayList<User>) readFileInObject(user);
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
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public static User hasUser(User inUser)
    {
        for (User i : userData)
        {
            if (i.getUsername().equals(inUser.getUsername()))
                return i;
        }
        return null;
    }

    public void save()
    {
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
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
