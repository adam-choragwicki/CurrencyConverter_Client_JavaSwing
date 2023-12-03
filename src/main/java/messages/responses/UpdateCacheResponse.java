package messages.responses;

import messages.MessageContract;
import messages.AbstractResponse;

public class UpdateCacheResponse extends AbstractResponse
{
    public UpdateCacheResponse(String messageBody, String correlationId)
    {
        super(MessageContract.ResponseType.UPDATE_CACHE_RESPONSE, messageBody, correlationId);
    }

    public void setValid(boolean valid)
    {
        valid_ = valid;
    }

    public boolean isValid()
    {
        return valid_;
    }

    public String getNewExchangeRatesTimestamp()
    {
        return newExchangeRatesTimestamp_;
    }

    public void setNewExchangeRatesTimestamp(String newExchangeRatesTimestamp)
    {
        newExchangeRatesTimestamp_ = newExchangeRatesTimestamp;
    }

    private boolean valid_;
    private String newExchangeRatesTimestamp_;
}
