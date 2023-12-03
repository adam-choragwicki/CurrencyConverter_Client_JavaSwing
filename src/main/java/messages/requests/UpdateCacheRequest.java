package messages.requests;

import messages.MessageContract;
import messages.AbstractRequest;

public class UpdateCacheRequest extends AbstractRequest
{
    public UpdateCacheRequest(String messageBody, String correlationId)
    {
        super(MessageContract.RequestType.UPDATE_CACHE_REQUEST, messageBody, correlationId);
    }
}
