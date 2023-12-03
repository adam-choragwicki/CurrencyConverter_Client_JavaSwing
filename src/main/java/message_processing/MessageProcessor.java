package message_processing;

import json_processing.JsonReader;
import messages.MessageContract;
import messages.RawInboundMessage;
import messages.responses.CalculateExchangeResponse;
import messages.responses.GetConfigResponse;
import messages.responses.UpdateCacheResponse;
import types.Currency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiConsumer;

public class MessageProcessor
{
    public void setGetConfigResponseCallback(BiConsumer<String, ArrayList<Currency>> callback)
    {
        getConfigResponseCallback_ = callback;
    }

    public void setCalculateExchangeResponseCallback(BiConsumer<String, String> callback)
    {
        calculateExchangeResponseCallback_ = callback;
    }

    public void setUpdateCacheExchangeResponseCallback(BiConsumer<Boolean, String> callback)
    {
        updateCacheResponseCallback_ = callback;
    }

    public void processMessage(RawInboundMessage rawInboundMessage)
    {
        try
        {
            final MessageContract.ResponseType responseType = ResponseParser.parseResponseType(rawInboundMessage);
            final String correlationId = ResponseParser.parseCorrelationId(rawInboundMessage);

            System.out.println(" [<-] Received " + responseType.getName() + " [" + correlationId + "]");

            switch (responseType)
            {
                case GET_CONFIG_RESPONSE:
                    GetConfigResponse getConfigResponse = ResponseParser.parseGetConfigResponse(rawInboundMessage.getString(), correlationId);
                    processGetConfigResponse(getConfigResponse);
                    break;

                case CALCULATE_EXCHANGE_RESPONSE:
                    CalculateExchangeResponse calculateExchangeResponse = ResponseParser.parseCalculateExchangeResponse(rawInboundMessage.getString(), correlationId);
                    processCalculateExchangeResponse(calculateExchangeResponse);
                    break;

                case UPDATE_CACHE_RESPONSE:
                    UpdateCacheResponse updateCacheResponse = ResponseParser.parseUpdateCacheResponse(rawInboundMessage.getString(), correlationId);
                    processUpdateCacheResponse(updateCacheResponse);
                    break;
            }
        }
        catch (InboundMessageError exception)
        {
            System.err.println(exception.getMessage() + " Message discarded");
        }
    }

    private void processGetConfigResponse(GetConfigResponse getConfigResponse)
    {
        String exchangeRatesTimestamp = getConfigResponse.getExchangeRatesTimestamp();
        String currenciesNamesAndCodes = getConfigResponse.getCurrenciesNamesAndCodes();

        JsonReader jsonReader = new JsonReader(currenciesNamesAndCodes);

        ArrayList<String> currencyNamesSorted = new ArrayList<>();

        for (Object currencyName : jsonReader.getKeys())
        {
            currencyNamesSorted.add((String) currencyName);
        }

        Collections.sort(currencyNamesSorted);

        ArrayList<Currency> currencies = new ArrayList<>();

        for (String currencyName : currencyNamesSorted)
        {
            currencies.add(new Currency(currencyName, jsonReader.getValue(currencyName)));
        }

        getConfigResponseCallback_.accept(exchangeRatesTimestamp, currencies);
    }

    private void processCalculateExchangeResponse(CalculateExchangeResponse calculateExchangeResponse)
    {
        calculateExchangeResponseCallback_.accept(calculateExchangeResponse.getCalculationResult(), calculateExchangeResponse.getFailureReason());
    }

    private void processUpdateCacheResponse(UpdateCacheResponse updateCacheResponse)
    {
        if (updateCacheResponse.isValid())
        {
            updateCacheResponseCallback_.accept(true, updateCacheResponse.getNewExchangeRatesTimestamp());
        }
        else
        {
            updateCacheResponseCallback_.accept(false, null);
        }
    }

    private java.util.function.BiConsumer<String, ArrayList<Currency>> getConfigResponseCallback_;
    private java.util.function.BiConsumer<String, String> calculateExchangeResponseCallback_;
    private java.util.function.BiConsumer<Boolean, String> updateCacheResponseCallback_;
}
