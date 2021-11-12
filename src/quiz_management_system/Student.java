package quiz_management_system;
import java.util.ArrayList;
import java.util.Scanner;
import quiz_management_system.Quiz.Question;

/**
 *
 * @author belsa
 */
public class Student extends User
{
    //default serialVersion id
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Attempt> attemptHistory; 

    public Student(String username, String password, int accessLevel)
    {
        super(username, password, accessLevel);
    }
    
    public void startAttempt(Quiz newQuiz)
    {
        Attempt newAttempt = new Attempt(newQuiz);
        newAttempt.doAttempt();
        attemptHistory.add(newAttempt);
    }
    
    public void viewAttemptHistory()
    {
        for(Attempt i : attemptHistory)
        {
            double sum = 0;
            for(Question j : i.getModel())
            {
                sum += j.getGrade();
            }
            System.out.println("Quiz: " + i.quiz.getQuizTitle());
            System.out.println("Your result: " + i.result + "/" + sum);
        }
    }
  
    class Attempt
    {
        private Quiz quiz;
        private Question[] model;
        private int[] answerIndex;
        private double result;
        
        public Attempt(Quiz newQuiz)
        {
            model = new Question[quiz.getnQuestions()];
            model = quiz.generateQuizModel();
            result = 0;
        }  

        public Question[] getModel()
        {
            return model;
        }
        
        public void doAttempt()
        {
            Scanner sc = new Scanner(System.in);

            System.out.println("Starting Quiz...Enter answer index after the prompt appears.");
        
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
}
