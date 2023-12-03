package messages;

public class AbstractResponse extends AbstractMessage
{
    public AbstractResponse(MessageContract.ResponseType responseType, String messageBody, String correlationId)
    {
        super(messageBody, correlationId);
        responseType_ = responseType;
    }

    final MessageContract.ResponseType responseType_;
}
