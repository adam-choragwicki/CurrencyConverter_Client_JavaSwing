package messages.requests;

import messages.MessageContract;
import messages.AbstractRequest;

public class GetConfigRequest extends AbstractRequest
{
    public GetConfigRequest(String messageBody, String correlationId)
    {
        super(MessageContract.RequestType.GET_CONFIG_REQUEST, messageBody, correlationId);
    }
}
