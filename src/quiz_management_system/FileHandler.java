package quiz_management_system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author belsa
 */
public class FileHandler
{
    public static void writeObjectToFile(Object out, String filepath) 
    {
        try 
        {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(out);
            objectOut.close();
            fileOut.close();
            System.out.println("The Object was succesfully written to a file");
 
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }
    public static Object readFileInObject(String filepath)
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
}
