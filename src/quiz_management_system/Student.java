/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz_management_system;
import quiz_management_system.Quiz.Question;

/**
 *
 * @author belsa
 */
public class Student
{
    private int userID;
    private String username;
    private String password;
    
    class Attempt
    {
        private Quiz quiz;
        private Question[] model; 
        private double result;
        public Attempt()
        {
            
        }
        
        public Attempt(Quiz newQuiz)
        {
            model = new Question[quiz.getnQuestions()];
            model = quiz.generateQuizModel();
            result = 0;
        }  
        }
        
        public void doAttempt()
        {
            //loop through model
            //display each question
            //take answer
            //if answer is right, get question grade and add to result
        }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

}
