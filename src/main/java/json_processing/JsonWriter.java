package json_processing;

import org.json.simple.JSONObject;

public class JsonWriter
{
    public JsonWriter()
    {
        json_ = new JSONObject();
    }

    public void addKeyValuePair(String key, String value)
    {
        json_.put(key, value);
    }

    public String toJsonString()
    {
        return json_.toJSONString();
    }

    private final JSONObject json_;
}
