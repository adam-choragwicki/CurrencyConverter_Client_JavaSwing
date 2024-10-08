package messages.responses;

import messages.MessageContract;
import messages.AbstractResponse;

public class CalculateExchangeResponse extends AbstractResponse
{
    public CalculateExchangeResponse(String messageBody, String correlationId)
    {
        super(MessageContract.ResponseType.CALCULATE_EXCHANGE_RESPONSE, messageBody, correlationId);
    }

    public void setValid(boolean valid)
    {
        valid_ = valid;
    }

    public boolean isValid()
    {
        return valid_;
    }

    public void setCalculationResult(String calculationResult)
    {
        calculationResult_ = calculationResult;
    }

    public void setExchangeRateTimestamp(String exchangeRateTimestamp)
    {
        exchangeRateTimestamp_ = exchangeRateTimestamp;
    }

    public String getCalculationResult()
    {
        return calculationResult_;
    }

    public String getExchangeRateTimestamp()
    {
        return exchangeRateTimestamp_;
    }

    public void setFailureReason(String failureReason)
    {
        failureReason_ = failureReason;
    }

    public String getFailureReason()
    {
        return failureReason_;
    }

    private String calculationResult_;
    private String exchangeRateTimestamp_;
    private boolean valid_;
    private String failureReason_;
}
