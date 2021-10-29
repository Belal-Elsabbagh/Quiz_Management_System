package quiz_management_system;

public class Teacher
{
    private int userID;
    private String username;
    private String password;
    private Quiz[] createdQuizzes;

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

    public void listMainMenu()
    {
        System.out.println("----------Teacher Operations Main Menu-----------");
        System.out.println("1. Open Quiz.");
        System.out.println("2. List my quizzes.");
        System.out.println("3. Create new quiz.");
    }
}