package quiz_management_system;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable
{
    ServerSocket skt;

    static Socket s;
    static DataInputStream dataIn;
    static DataOutputStream dataOut;

    public Server()
    {
        try
        {
            skt = new ServerSocket(2611);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                s = skt.accept();
                dataIn = new DataInputStream(s.getInputStream());
                dataOut = new DataOutputStream(s.getOutputStream());
            }
        } catch (IOException e)
        {

        }
    }
}