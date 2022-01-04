package quiz_management_system;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame implements Runnable
{
    ServerSocket skt;
    
    static Socket s;
    static DataInputStream dataIn;
    static DataOutputStream dataOut;

    JTextArea chat;

    public Server() throws HeadlessException
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
                chat.setText(chat.getText() + dataIn.readUTF());
                dataOut = new DataOutputStream(s.getOutputStream());
                dataOut.writeUTF(chat.getText());
            }
        } catch (IOException e)
        {

        }
    }
}