package socket_communication;

public class ConnectionClosedError extends Exception
{
    public ConnectionClosedError(String message)
    {
        super(message);
    }
}
