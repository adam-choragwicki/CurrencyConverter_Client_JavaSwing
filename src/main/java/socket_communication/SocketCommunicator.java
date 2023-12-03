package socket_communication;

import messages.AbstractRequest;
import messages.RawInboundMessage;

import java.io.IOException;

public class SocketCommunicator
{
    public SocketCommunicator(String ip_, int port_)
    {
        this.ip_ = ip_;
        this.port_ = port_;
    }

    public void openConnection() throws ConnectionError
    {
        try
        {
            socketClient_ = new SocketClient();
            socketClient_.setRawInboundMessageCallback(rawInboundMessageCallback_);
            socketClient_.setConnectionClosedCallback(connectionClosedCallback_);
            socketClient_.connect(ip_, port_);
            System.out.println(" [*] Connected to server");
        }
        catch (java.net.ConnectException exception)
        {
            String message = "Error, cannot connect to server.\n" + "Please make sure you have server running on local host";
            System.err.println(message);
            throw new ConnectionError(message);
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public void closeConnection()
    {
        socketClient_.closeConnection();
    }

    public void startConsumingMessages()
    {
        Thread consumingThread = new Thread(() -> SocketClient.startConsumingMessages(socketClient_));
        consumingThread.start();
    }

    public void sendRequest(AbstractRequest request)
    {
        System.out.println(" [->] Sending " + request.getRequestType().toString().toLowerCase() + " [" + request.getCorrelationId_() + "]");
        System.out.println(" [->] Sending " + request.getMessageBody());
        socketClient_.sendMessage(request.getMessageBody());
    }

    public void setInboundMessageCallback(java.util.function.Consumer<RawInboundMessage> callback)
    {
        rawInboundMessageCallback_ = callback;
    }

    public void setConnectionClosedCallback(java.util.function.Consumer<String> callback)
    {
        connectionClosedCallback_ = callback;
    }

    private final String ip_;
    private final int port_;
    private SocketClient socketClient_;
    private java.util.function.Consumer<RawInboundMessage> rawInboundMessageCallback_;
    private java.util.function.Consumer<String> connectionClosedCallback_;
}
