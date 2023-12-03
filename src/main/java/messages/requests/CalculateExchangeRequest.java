package messages.requests;

import messages.MessageContract;
import messages.AbstractRequest;

public class CalculateExchangeRequest extends AbstractRequest
{
    public CalculateExchangeRequest(String messageBody, String correlationId)
    {
        super(MessageContract.RequestType.CALCULATE_EXCHANGE_REQUEST, messageBody, correlationId);
    }
}
