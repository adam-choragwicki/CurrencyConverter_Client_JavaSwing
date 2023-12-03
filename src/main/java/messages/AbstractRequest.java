package messages;

public abstract class AbstractRequest extends AbstractMessage
{
    public AbstractRequest(MessageContract.RequestType requestType, String messageBody, String correlationId)
    {
        super(messageBody, correlationId);
        requestType_ = requestType;
    }

    public MessageContract.RequestType getRequestType()
    {
        return requestType_;
    }

    final MessageContract.RequestType requestType_;
}
