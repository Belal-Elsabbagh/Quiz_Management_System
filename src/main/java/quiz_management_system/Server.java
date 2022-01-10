package quiz_management_system;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public int port = 600;
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public Server(ServerSocket serverSocket)
    {
        try
        {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e)
        {
            System.out.println("Error creating server..");
            e.printStackTrace();
        }
    }

    public void startServer()
    {
        try
        {
            while (!serverSocket.isClosed())
            {
                Socket socket = serverSocket.accept();
                System.out.println("A student has connected");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void closeServerSocket()
    {
        try
        {
            if (serverSocket != null)
            {
                serverSocket.close();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}