package quiz_management_system;

public class User
{
    private static int count = 0;
    private int userID;
    private String username;
    private String password;

    public User()
    {
        this.userID = ++count;
    }
}
