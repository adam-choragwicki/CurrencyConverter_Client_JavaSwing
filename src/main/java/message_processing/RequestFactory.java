package message_processing;

import json_processing.JsonWriter;
import messages.MessageContract;
import types.Currency;
import messages.requests.CalculateExchangeRequest;
import messages.requests.GetConfigRequest;
import messages.requests.UpdateCacheRequest;

import java.util.UUID;

public class RequestFactory
{
    public static GetConfigRequest makeGetConfigRequest()
    {
        JsonWriter jsonWriter = new JsonWriter();

        assignRequestType(jsonWriter, MessageContract.RequestType.GET_CONFIG_REQUEST.getName());
        String correlationId = assignCorrelationId(jsonWriter);

        return new GetConfigRequest(jsonWriter.toJsonString(), correlationId);
    }

    public static CalculateExchangeRequest makeCalculateExchangeRequest(Currency sourceCurrency, Currency targetCurrency, String moneyAmount)
    {
        JsonWriter jsonWriter = new JsonWriter();

        assignRequestType(jsonWriter, MessageContract.RequestType.CALCULATE_EXCHANGE_REQUEST.getName());
        String correlationId = assignCorrelationId(jsonWriter);

        jsonWriter.addKeyValuePair(MessageContract.CalculateExchangeRequestContract.SOURCE_CURRENCY, sourceCurrency.getCode());
        jsonWriter.addKeyValuePair(MessageContract.CalculateExchangeRequestContract.TARGET_CURRENCY, targetCurrency.getCode());
        jsonWriter.addKeyValuePair(MessageContract.CalculateExchangeRequestContract.MONEY_AMOUNT, moneyAmount);

        return new CalculateExchangeRequest(jsonWriter.toJsonString(), correlationId);
    }

    public static UpdateCacheRequest makeUpdateCacheRequest()
    {
        JsonWriter jsonWriter = new JsonWriter();

        assignRequestType(jsonWriter, MessageContract.RequestType.UPDATE_CACHE_REQUEST.getName());
        String correlationId = assignCorrelationId(jsonWriter);

        return new UpdateCacheRequest(jsonWriter.toJsonString(), correlationId);
    }

    private static void assignRequestType(JsonWriter jsonWriter, String requestType)
    {
        jsonWriter.addKeyValuePair(MessageContract.TYPE, requestType);
    }

    private static String assignCorrelationId(JsonWriter jsonWriter)
    {
        final String correlationId = UUID.randomUUID().toString();
        jsonWriter.addKeyValuePair(MessageContract.CORRELATION_ID, correlationId);
        return correlationId;
    }
}
