package quiz_management_system;

public class Teacher extends User
{
    private Quiz[] createdQuizzes;
    
    public void listMainMenu()
    {
        System.out.println("----------Teacher Operations Main Menu-----------");
        System.out.println("1. Open Quiz.");
        System.out.println("2. List my quizzes.");
        System.out.println("3. Create new quiz.");
    }
}
