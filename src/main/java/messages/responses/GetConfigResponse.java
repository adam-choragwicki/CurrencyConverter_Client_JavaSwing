package messages.responses;

import messages.MessageContract;
import messages.AbstractResponse;

public class GetConfigResponse extends AbstractResponse
{
    public GetConfigResponse(String messageBody, String correlationId, String currenciesNamesAndCodes)
    {
        super(MessageContract.ResponseType.GET_CONFIG_RESPONSE, messageBody, correlationId);
//        exchangeRatesTimestamp_ = exchangeRatesTimestamp;
        currenciesNamesAndCodes_ = currenciesNamesAndCodes;
    }

//    public String getExchangeRatesTimestamp()
//    {
//        return exchangeRatesTimestamp_;
//    }

    public String getCurrenciesNamesAndCodes()
    {
        return currenciesNamesAndCodes_;
    }

//    private final String exchangeRatesTimestamp_;
    private final String currenciesNamesAndCodes_;
}
