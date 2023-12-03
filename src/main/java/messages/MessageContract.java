package messages;

/*Contents of this file have to match server counterpart*/

public class MessageContract
{
    public static final String TYPE = "type";
    public static final String CORRELATION_ID = "correlation_id";

    public enum RequestType
    {
        GET_CONFIG_REQUEST("get_config_request"),
        CALCULATE_EXCHANGE_REQUEST("calculate_exchange_request"),
        UPDATE_CACHE_REQUEST("update_cache_request");

        RequestType(String name)
        {
            name_ = name;
        }

        public String getName()
        {
            return name_;
        }

        private final String name_;
    }

    public enum ResponseType
    {
        GET_CONFIG_RESPONSE("get_config_response"),
        CALCULATE_EXCHANGE_RESPONSE("calculate_exchange_response"),
        UPDATE_CACHE_RESPONSE("update_cache_response");

        ResponseType(String name)
        {
            name_ = name;
        }

        public String getName()
        {
            return name_;
        }

        private final String name_;
    }

    public static class GetConfigResponseContract
    {
        public static final String CURRENCIES_NAMES_AND_CODES = "currencies_names_and_codes";
    }

    public static class CalculateExchangeRequestContract
    {
        public static final String SOURCE_CURRENCY = "source_currency";
        public static final String TARGET_CURRENCY = "target_currency";
        public static final String MONEY_AMOUNT = "money_amount";
    }

    public static class CalculateExchangeResponseContract
    {
        public static final String STATUS = "status";
        public static final String CALCULATION_RESULT = "calculation_result";
        public static final String EXCHANGE_RATE_TIMESTAMP = "exchange_rate_timestamp";
        public static final String FAILURE_REASON = "failure_reason";
        public static final String OK_STATUS = "OK";
    }

    public static class UpdateCacheResponseContract
    {
        public static final String STATUS = "status";
        public static final String OK_STATUS = "OK";
    }
}
