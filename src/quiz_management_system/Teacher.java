package quiz_management_system;

public class Teacher extends User
{
    private Quiz[] createdQuizzes;
    
    Teacher()
    {
        super(1);
    }

    public void listMainMenu()
    {
        System.out.println("----------Teacher Operations Main Menu-----------");
        System.out.println("1. List my quizzes.");
        System.out.println("2. Create new quiz.");
        System.out.println("3. edit quiz.");
        System.out.println("4. remove quiz.");
    }
}
