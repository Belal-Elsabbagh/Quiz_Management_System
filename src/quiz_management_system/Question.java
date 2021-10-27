package quiz_management_system;

public class Question
{
    private int questionID;
    private String prompt;
    private double grade;
    //Java doesn't include structs. Using a class to make MCQ easier.
    private class MCQ
    {
        private int nChoices;
        private String[] choices;
        private String answerKey;
        
        public boolean checkAnswer(String inAnswer)
        {
            boolean result = false;
            for(int i = 0; i < nChoices; i++)
            {
                if(inAnswer == answerKey)
                {
                    result = true;
                    break;
                }
            }
            return result;
        }
    }
}
