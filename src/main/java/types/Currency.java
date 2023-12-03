package types;

public class Currency
{
    public Currency(String name, String code)
    {
        name_ = name;
        code_ = code.toLowerCase();
    }

    public String getName()
    {
        return name_;
    }

    public String getCode()
    {
        return code_;
    }

    @Override
    public String toString()
    {
        return name_;
    }

    private final String name_;
    private final String code_;
}
