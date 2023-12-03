package message_processing;

public class InboundMessageError extends Exception
{
    public InboundMessageError(String message)
    {
        super(message);
    }
}
