package main;

import config.Config;
import message_processing.MessageProcessor;
import socket_communication.SocketCommunicator;
import types.Currency;

import java.util.ArrayList;

public class Model
{
    public Model(Config config)
    {
        socketCommunicator_ = new SocketCommunicator(config.getIp(), config.getPort());
        messageProcessor_ = new MessageProcessor();
    }

    public SocketCommunicator getMessenger()
    {
        return socketCommunicator_;
    }

    public MessageProcessor getMessageProcessor()
    {
        return messageProcessor_;
    }

    public void setExchangeRatesTimestamp(String exchangeRatesTimestamp)
    {
        exchangeRatesTimestamp_ = exchangeRatesTimestamp;
    }

    public String getExchangeRatesTimestamp()
    {
        return exchangeRatesTimestamp_;
    }

    public void setCurrencies(ArrayList<Currency> currencies)
    {
        currencies_ = currencies;
    }

    public ArrayList<Currency> getCurrencies()
    {
        return currencies_;
    }

    private final SocketCommunicator socketCommunicator_;
    private final MessageProcessor messageProcessor_;
    private String exchangeRatesTimestamp_;
    private ArrayList<Currency> currencies_ = new ArrayList<>();
}
