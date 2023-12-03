package message_processing;

import json_processing.JsonReader;
import messages.MessageContract;
import messages.RawInboundMessage;
import messages.responses.CalculateExchangeResponse;
import messages.responses.GetConfigResponse;
import messages.responses.UpdateCacheResponse;

public class ResponseParser
{
    public static MessageContract.ResponseType parseResponseType(RawInboundMessage rawInboundMessage) throws InboundMessageError
    {
        JsonReader jsonReader = new JsonReader(rawInboundMessage.getString());

        if (jsonReader.hasKey(MessageContract.TYPE))
        {
            final String typeValue = jsonReader.getValue(MessageContract.TYPE);

            try
            {
                return MessageContract.ResponseType.valueOf(typeValue.toUpperCase());
            }
            catch (IllegalArgumentException exception)
            {
                throw new InboundMessageError("Error, unsupported message type: " + typeValue + ".");
            }
        }
        else
        {
            throw new InboundMessageError("Error, message does not have type.");
        }
    }

    public static String parseCorrelationId(RawInboundMessage rawInboundMessage) throws InboundMessageError
    {
        JsonReader jsonReader = new JsonReader(rawInboundMessage.getString());

        if (jsonReader.hasKey(MessageContract.CORRELATION_ID))
        {
            return jsonReader.getValue(MessageContract.CORRELATION_ID);
        }
        else
        {
            throw new InboundMessageError("Error, message does not have correlation ID");
        }
    }

    public static GetConfigResponse parseGetConfigResponse(String messageBody, String correlationId)
    {
        JsonReader jsonReader = new JsonReader(messageBody);

        final String exchangeRatesTimestamp = jsonReader.getValue(MessageContract.GetConfigResponseContract.EXCHANGE_RATES_TIMESTAMP);
        final String currenciesNamesAndCodesString = jsonReader.getValue(MessageContract.GetConfigResponseContract.CURRENCIES_NAMES_AND_CODES);

        return new GetConfigResponse(messageBody, correlationId, exchangeRatesTimestamp, currenciesNamesAndCodesString);
    }

    public static CalculateExchangeResponse parseCalculateExchangeResponse(String messageBody, String correlationId)
    {
        JsonReader jsonReader = new JsonReader(messageBody);

        String status = jsonReader.getValue(MessageContract.CalculateExchangeResponseContract.STATUS);

        CalculateExchangeResponse calculateExchangeResponse = new CalculateExchangeResponse(messageBody, correlationId);
        calculateExchangeResponse.setValid(status.equals(MessageContract.CalculateExchangeResponseContract.OK_STATUS));

        if (calculateExchangeResponse.isValid())
        {
            String calculationResult = jsonReader.getValue(MessageContract.CalculateExchangeResponseContract.CALCULATION_RESULT);
            calculateExchangeResponse.setCalculationResult(calculationResult);
        }
        else
        {
            String failureReason = jsonReader.getValue(MessageContract.CalculateExchangeResponseContract.FAILURE_REASON);
            calculateExchangeResponse.setFailureReason(failureReason);
        }

        return calculateExchangeResponse;
    }

    public static UpdateCacheResponse parseUpdateCacheResponse(String messageBody, String correlationId)
    {
        JsonReader jsonReader = new JsonReader(messageBody);

        String status = jsonReader.getValue(MessageContract.UpdateCacheResponseContract.STATUS);

        UpdateCacheResponse updateCacheResponse = new UpdateCacheResponse(messageBody, correlationId);
        updateCacheResponse.setValid(status.equals(MessageContract.UpdateCacheResponseContract.OK_STATUS));

        if (updateCacheResponse.isValid())
        {
            String newExchangeRatesTimestamp = jsonReader.getValue(MessageContract.UpdateCacheResponseContract.NEW_EXCHANGE_RATES_TIMESTAMP);
            updateCacheResponse.setNewExchangeRatesTimestamp(newExchangeRatesTimestamp);
        }

        return updateCacheResponse;
    }
}
