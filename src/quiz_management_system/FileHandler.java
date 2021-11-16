package quiz_management_system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author belsa
 */
public class FileHandler
{
    public File student = new File("studentData.dat"),
            teacher = new File("teacherData.dat"), 
            quiz = new File("quizData.dat");
    public ArrayList<Student> studentData = new ArrayList();
    public ArrayList<Teacher> teacherData = new ArrayList();
    public ArrayList<Quiz> quizData = new ArrayList();

    public FileHandler()
    {
        studentData = (ArrayList <Student>) readFileInObject(student);
        teacherData = (ArrayList <Teacher>) readFileInObject(teacher);
        quizData = (ArrayList <Quiz>) readFileInObject(quiz);
    }
    
    public void save()
    {
        writeObjectToFile(studentData, student);
        writeObjectToFile(teacherData, teacher);
        writeObjectToFile(quizData, quiz);
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
            System.out.println("The Object was succesfully written to a file");
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
    
    public Quiz searchQuizByID(int qID)
    {
        Quiz x = null;
        for(Quiz i : quizData)
        {
            if(i.getQuizID() == qID)
            {
                x = i;
            }
        }
        return x;
    }
}
