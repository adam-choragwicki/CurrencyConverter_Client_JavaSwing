package messages;

public class RawInboundMessage
{
    /*Inbound string wrapper, needs parsing, potentially malicious*/
    public RawInboundMessage(String string)
    {
        string_ = string;
    }

    public String getString()
    {
        return string_;
    }

    private final String string_;
}
