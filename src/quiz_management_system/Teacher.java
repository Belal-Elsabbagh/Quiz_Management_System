package quiz_management_system;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import quiz_management_system.Student.Attempt;

public class Teacher extends User
{
    private static final long serialVersionUID = 1L;

    ArrayList<Quiz> createdQuizzes;

    Teacher()
    {
        super(2);
        createdQuizzes = new ArrayList();
    }

    public Teacher(String username, String password)
    {
        super(username, password);
        createdQuizzes = new ArrayList();
    }

    public int listTeacherMenu(FileHandler data) throws ParseException
    {
        System.out.println("----------Teacher Operations Main Menu-----------");
        System.out.println("1. List my quizzes.");
        System.out.println("2. Create new quiz.");
        System.out.println("3. review Quiz Grades");
        Scanner sc = new Scanner(System.in);
        short n;
        n = sc.nextShort();
        switch(n)
        {
            case -1: return 0;
            case 1: 
            {
                listQuizzes();
                break;
            }
            case 2:
            {
                createNewQuiz(data);
                break;
            }
            case 3:
            {
                reviewGrades(data);
                break;
            }
        }    
        return 1;
    }

    public void listQuizzes()
    {
        for (Quiz i : createdQuizzes)
        {
            i.displayQuizProperties();
        }
    }

    public void createNewQuiz(FileHandler data) throws ParseException
    {
        Quiz newQuiz = new Quiz();
        newQuiz.createQuiz(this);
        
        data.quizData.add(newQuiz);
        createdQuizzes.add(newQuiz);
    }
    
        public void reviewGrades(FileHandler f)
    {
        System.out.println("please enter quiz ID : ");
        Scanner inputID = new Scanner(System.in);
        int inID = inputID.nextInt();
        System.out.println("Enter Quiz ID: ");
        Quiz Q = f.searchQuizByID(inID);
        if(Q == null)
            System.err.println("No Quiz Found.");
        else
        {
            for (Student i : f.studentData)
            {
                for(Attempt j :i.getAttemptHistory())
                {
                    if(j.getQuiz().equals(Q))
                        System.out.println(i.getUsername() + ": " + j.getResult());
                        
                }
            }
//            if ((Q.getQuizID())==inID)
//            {
//                System.out.println("Quiz: " + Q.getQuizTitle());
//                for (int i = 0 ; i < a.attemptHistory.size() ; i++ )
//                {
//                   System.out.println("Student :" + a.attemptHistory.forEach(i -> 
//                   {
//                       System.out.println();
//                   }) );
//                 }  
//            }
        }
//        for(int i = 0; i < noOfStu ; i++){
//            System.out.println("Student" + "Grade" + gradestu[i]);
//        }
        
    }
}
