package messages;

abstract class AbstractMessage
{
    public AbstractMessage(String messageBody, String correlationId)
    {
        messageBody_ = messageBody;
        correlationId_ = correlationId;
    }

    public String getMessageBody()
    {
        return messageBody_;
    }

    public String getCorrelationId_()
    {
        return correlationId_;
    }

    private final String messageBody_;
    private final String correlationId_;
}
