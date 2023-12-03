package config;

public class Config
{
    public Config(String ip, int port, boolean debug)
    {
        this.ip = ip;
        this.port = port;
        this.debug = debug;
    }

    public String getIp()
    {
        return ip;
    }

    public int getPort()
    {
        return port;
    }

    public boolean isDebug()
    {
        return debug;
    }

    String ip;
    int port;
    boolean debug;
}
