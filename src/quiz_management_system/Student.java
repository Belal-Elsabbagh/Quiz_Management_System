package quiz_management_system;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import quiz_management_system.Quiz.Question;
/**
 *
 * @author belsa
 */
public class Student extends User
{  
    private static final long serialVersionUID = 1L;

    private ArrayList<Attempt> attemptHistory; 
    
    public Student(String username, String password)
    {
        super(username, password);
    }

    public ArrayList<Attempt> getAttemptHistory()
    {
        return attemptHistory;
    }
    
    public int listStudentMenu(DataHandler data)
    {
        System.out.println("*****Logged in as " + username + "*****");
        System.out.println("1. Attempt quiz.");
        System.out.println("2. View attempt history.");
        System.out.println("3. Review Attempt.");
        System.out.println("Enter -1 to quit");
        Scanner sc = new Scanner(System.in);
        short n;
        n = sc.nextShort();
        switch(n)
        {
            case -1:
                return 0;
            case 1: 
            {
                System.out.println("Enter Quiz ID: ");
                while(!sc.hasNextInt())
                {
                    System.err.println("INVALID INPUT.");
                    sc.next();
                }
                Quiz newQuiz = null;
                newQuiz = newQuiz.searchByID(sc.nextInt(), data);
                if(newQuiz == null)
                    System.err.println("No Quiz Found.");
                else
                {
                    System.out.println("Starting Quiz: ");
                    newQuiz.displayQuizProperties();
                    startAttempt(newQuiz);
                }
            }
            case 2:
                viewAttemptHistory();
            case 3:
            {
                System.out.println("Enter Quiz ID: ");
                while(!sc.hasNextInt())
                {
                    System.err.println("INVALID INPUT.");
                    sc.next();
                }
                Quiz newQuiz = null;
                newQuiz = newQuiz.searchByID(sc.nextInt(), data);
                if(newQuiz == null)
                    System.err.println("No Quiz Found.");
                else
                {
                    Attempt toReview = new Attempt (newQuiz);
                    toReview.reviewAttempt();
                }
            }
        }    
        return 1;
    }
    
    public Student searchByID(int uID, DataHandler data)
    {
        Student x = null;
        for(Student i : data.studentData)
        {
            if(i.getUserID() == uID)
            {
                x = i;
            }
        }
        return x;
    }
    
    public void startAttempt(Quiz newQuiz)
    {
        Attempt newAttempt = new Attempt(newQuiz);
        newAttempt.doAttempt();
        attemptHistory.add(newAttempt);
    }
    
    public void viewAttemptHistory()
    {
        attemptHistory.forEach(i ->
        {
            double sum = 0;
            for(Question j : i.getModel())
            {
                sum += j.getGrade();
            }
            System.out.println("Quiz: " + i.quiz.getQuizTitle());
            System.out.println("Your result: " + i.result + "/" + sum);
        });
    }
    
    public static Student studentLogin(DataHandler data)
    {
        Scanner sc = new Scanner(System.in);
        String inUsername, inPassword;

        System.out.println("Enter username:");
        inUsername = sc.next();
        System.out.println("Enter password:");
        inPassword = sc.next();

        Student newStudent = null;
        for (Student i : data.studentData)
        {
            if (inUsername.equals(i.getUsername())) {
                if (inPassword.equals(i.getPassword())) {
                    newStudent = i;
                    System.out.println("Login Successful");
                    break;
                } else {
                    System.err.println("Incorrect password.");
                }
            } else {
                System.err.println("User not found.");
            }
        }
        return newStudent;
    }
      
    class Attempt
    {
        private Quiz quiz;
        private Question[] model;
        private int[] answerIndex;
        private double result;
        
        public Attempt(Quiz newQuiz)
        {
            quiz = newQuiz;
            model = new Question[quiz.getnQuestions()];
            model = quiz.generateQuizModel();
            result = 0;
        }  

        public double getResult()
        {
            return result;
        }

        public Quiz getQuiz()
        {
            return quiz;
        }

        public Question[] getModel()
        {
            return model;
        }
        
        public void timeattempt(){
            Quiz q = new Quiz();
             LocalTime time = LocalTime.now();
            if (new Date() == q.quizdate() && time == q.openTime()){
            doAttempt();
        }
            else
                System.out.println("----------------Quiz is not open yet----------------");
        }
        
        public void doAttempt()
        {
            Scanner sc = new Scanner(System.in);
            Duration d = new Duration();
            d.run();
            System.out.println("Starting Quiz...Enter answer index after the prompt appears.");
            answerIndex = new int[quiz.getnQuestions()];
            for(int i = 0; i < quiz.getnQuestions(); i++)
            {
                model[i].displayQuestion();
                answerIndex[i] = sc.nextInt();
            
                if(model[i].checkAnswer((short) answerIndex[i]))
                    result += model[i].getGrade();
                
            }
            
        }
        
        public void reviewAttempt()
        {
            for(int i = 0; i < quiz.getnQuestions(); i++)
            {
                model[i].displayQuestion();
                System.out.println("Your Answer: " + answerIndex[i]);
                if(model[i].checkAnswer((short) answerIndex[i]))
                    System.out.println("Correct");
            }   
        }
    }
    
    public class Menu {
    
    public boolean exit;
    public Scanner kb = new Scanner(System.in);
    
    private void printHeader()
    {
        System.out.println("+---------------------------------+");
        System.out.println("|          Welcome to our         |");
        System.out.println("|         Menu Application        |");
        System.out.println("+---------------------------------+");
    }
    private void printMenu()
    {
        System.out.println("Please select one of the following choices: ");
        System.out.println("1)Save Answer");
        System.out.println("2)Edit Answer");
        System.out.println("3)Delete Answer");
        System.out.println("4)Exit");
    }
    public void runMenu()
    {
       printHeader();
       while(!exit)
       {
           printMenu();
           int choice = getInput();
           preformAction(choice);
       }
    }
    public int getInput()
    {
       
        int choice = -1;
        while(choice > 0 || choice < 4 )
        { 
            try
            {
                System.out.println("\nEnter your choice: ");
                choice = Integer.parseInt(kb.nextLine());
            }
            catch(NumberFormatException e)
                {
                    System.out.println("Invalid Selection, please try again!");
            }
            
        }
      return choice;
    }
    public void preformAction(int choice)
    {
        switch(choice)
        {
            case 1:
                saveAnswer();
                break;
            case 2:
                //editAnswer();
                 break;
            case 3:
                deleteAnswer();
                 break;
            case 4:
              System.out.println("Wishing you all the best on your test!");
               break;
           default:
               System.out.println("Unknown error has occured!");
        }
    }
    private void saveAnswer()
    {
        
    }
    /* private void editAnswer()
    {
       
        boolean yn = false;
         yn = kb.nextBoolean();
        System.out.println("Are you sure you want to change your answer?");
        if (yn == true)
        {
            
        }
    }*/
      private void deleteAnswer()
    {
        
    }
}
}

