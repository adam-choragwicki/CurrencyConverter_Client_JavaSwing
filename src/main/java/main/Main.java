package main;

import config.Config;
import config.ConfigLoader;

public class Main
{
    public static void main(String[] args)
    {
        Config config = ConfigLoader.readConfigFile("config.json");

        System.out.println("Started currency converter client");

        new Application(config);

        System.out.println("Shutting down currency converter client");
    }
}
