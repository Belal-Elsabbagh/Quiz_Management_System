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
    public static File quiz, user;

    static
    {
        quiz = new File("quizData.dat");
        user = new File("userData.dat");
    }

    /**
     * initializes the ArrayList objects with the data in the files
     */
    public DataHandler()
    {
        quizData = (ArrayList<Quiz>) readFileInObject(quiz);
        userData = (ArrayList<User>) readFileInObject(user);
    }

    /**
     * reads a serializable object from a given file.
     *
     * @param filepath the file that we want to return the object from.
     * @return the object that was saved in the file (or null if an exception was thrown).
     */
    private static Object readFileInObject(File filepath)
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

    /**
     * writes current data to the files for saving.
     */
    static void save()
    {
        writeObjectToFile(quizData, quiz);
        writeObjectToFile(userData, user);
    }

    /**
     * a function that writes a serializable object to a file.
     *
     * @param out      the object to be written to the file
     * @param filepath the file to which the object will be written
     */
    private static void writeObjectToFile(Object out, File filepath)
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
