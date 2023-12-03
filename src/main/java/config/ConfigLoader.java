package config;

import json_processing.JsonReader;
import utilities.FileLoader;

public class ConfigLoader
{
    public static Config readConfigFile(String path)
    {
        String fileContent = FileLoader.loadFileContent(path);

        JsonReader jsonReader = new JsonReader(fileContent);

        String ip = jsonReader.getValue("ip");
        int port = Integer.parseInt(jsonReader.getValue("port"));
        boolean debug = Boolean.parseBoolean(jsonReader.getValue("debug"));

        return new Config(ip, port, debug);
    }
}
