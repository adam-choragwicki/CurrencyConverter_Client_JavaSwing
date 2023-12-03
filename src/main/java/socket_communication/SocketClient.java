package socket_communication;

import messages.RawInboundMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class SocketClient
{
    void connect(String host, int port) throws IOException
    {
        System.out.println(" [*] Connecting to " + host + ":" + port);

        clientSocket_ = new Socket(host, port);

        System.out.println("Connected");
        System.out.println("IP address: " + clientSocket_.getLocalAddress());
        System.out.println("Local port: " + clientSocket_.getLocalPort());

        clientSocketOutput_ = new PrintWriter(clientSocket_.getOutputStream(), true);
        clientSocketInput_ = new BufferedReader(new InputStreamReader(clientSocket_.getInputStream()));
    }

    void setRawInboundMessageCallback(java.util.function.Consumer<RawInboundMessage> callback)
    {
        rawInboundMessageCallback_ = callback;
    }

    void setConnectionClosedCallback(java.util.function.Consumer<String> callback)
    {
        connectionClosedCallback_ = callback;
    }

    public void closeConnection()
    {
        System.out.println("Closing connection");

        try
        {
            if (clientSocket_.isConnected())
            {
                clientSocket_.close();
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    static void startConsumingMessages(SocketClient socketClient)
    {
        System.out.println(" [*] Starting consuming");

        String inboundMessage = "";

        do
        {
            try
            {
                inboundMessage = socketClient.receiveMessage();
                rawInboundMessageCallback_.accept(new RawInboundMessage(inboundMessage));
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }
        while (!inboundMessage.isEmpty());

        System.out.println("Finished consuming");
    }

    String receiveMessage() throws IOException
    {
        try
        {
            String inboundMessage = clientSocketInput_.readLine();

            if (inboundMessage != null)
            {
                return inboundMessage;
            }
            else
            {
                String message = "Error, connection closed abruptly by server";
                System.err.println("Error, connection closed abruptly by server");
                connectionClosedCallback_.accept(message);
                return null;
            }
        }
        catch (SocketException exception)
        {
            clientSocketOutput_.close();
            System.exit(0);
            return null;
        }
    }

    void sendMessage(String message)
    {
        clientSocketOutput_.println(message);
    }

    private static java.util.function.Consumer<RawInboundMessage> rawInboundMessageCallback_;
    private static java.util.function.Consumer<String> connectionClosedCallback_;
    protected Socket clientSocket_;
    protected PrintWriter clientSocketOutput_;
    protected BufferedReader clientSocketInput_;
}
