package json_processing;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Set;

public class JsonReader
{
    public JsonReader(String string)
    {
        json_ = (JSONObject) JSONValue.parse(string);
    }

    public boolean hasKey(String key)
    {
        return json_.containsKey(key);
    }

    public String getValue(String key)
    {
        if (json_.containsKey(key))
        {
            return json_.get(key).toString();
        }
        else
        {
            throw new RuntimeException("Error, key does not exist: " + key);
        }
    }

    public Set getKeys()
    {
        return json_.keySet();
    }

    private final JSONObject json_;
}
