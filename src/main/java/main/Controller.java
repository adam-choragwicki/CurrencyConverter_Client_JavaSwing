package main;

import message_processing.RequestFactory;
import messages.requests.CalculateExchangeRequest;
import messages.requests.GetConfigRequest;
import messages.requests.UpdateCacheRequest;
import socket_communication.ConnectionError;
import types.Currency;
import view.MainWindow;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Controller
{
    public Controller(Model model, MainWindow view)
    {
        model_ = model;
        view_ = view;

        model_.getMessenger().setInboundMessageCallback(model_.getMessageProcessor()::processMessage);
        model_.getMessenger().setConnectionClosedCallback((String message) ->
        {
            view_.showConnectionClosedByServerDialog(message);
            System.exit(1);
        });

        model_.getMessageProcessor().setGetConfigResponseCallback(this::getConfigResponseCallback);
        model_.getMessageProcessor().setCalculateExchangeResponseCallback(this::calculateExchangeResponseCallback);
        model_.getMessageProcessor().setUpdateCacheExchangeResponseCallback(this::updateCacheResponseCallback);

        setViewListeners();
        openConnection();
        sendGetConfigRequest();
    }

    private void setViewListeners()
    {
        view_.getCalculateButton().addActionListener(e -> sendCalculateExchangeRequest());
        view_.getUpdateCacheButton().addActionListener(e -> processUpdateCacheButtonClicked());
        view_.getCloseButton().addActionListener(e -> view_.dispatchEvent(new WindowEvent(view_, WindowEvent.WINDOW_CLOSING)));

        view_.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                closeApplication();
            }
        });
    }

    private void openConnection()
    {
        try
        {
            model_.getMessenger().openConnection();
            model_.getMessenger().startConsumingMessages();
        }
        catch (ConnectionError exception)
        {
            int chosenOption = view_.showConnectionErrorDialog(exception.getMessage());

            if (chosenOption == 0)
            {
                openConnection();
            }
            else
            {
                System.exit(0);
            }
        }
    }

    private void closeConnection()
    {
        model_.getMessenger().closeConnection();
    }

    private void sendGetConfigRequest()
    {
        GetConfigRequest getConfigRequest = RequestFactory.makeGetConfigRequest();
        model_.getMessenger().sendRequest(getConfigRequest);
    }

    private void sendCalculateExchangeRequest()
    {
        Currency sourceCurrency = (Currency) view_.getSourceCurrencyComboBox().getSelectedItem();
        Currency targetCurrency = (Currency) view_.getTargetCurrencyComboBox().getSelectedItem();
        String moneyAmount = view_.getSourceCurrencyTextField().getText();

        CalculateExchangeRequest calculateExchangeRequest = RequestFactory.makeCalculateExchangeRequest(sourceCurrency, targetCurrency, moneyAmount);
        model_.getMessenger().sendRequest(calculateExchangeRequest);
    }

    private void sendUpdateCacheRequest()
    {
        UpdateCacheRequest updateCacheRequest = RequestFactory.makeUpdateCacheRequest();
        model_.getMessenger().sendRequest(updateCacheRequest);
    }

    private void processUpdateCacheButtonClicked()
    {
        int chosenOption = view_.showUpdateCacheDialog();

        if (chosenOption == 0)
        {
            view_.lockButtons();
            sendUpdateCacheRequest();
        }
    }

    private void getConfigResponseCallback(ArrayList<Currency> currencies)
    {
//        model_.setExchangeRatesTimestamp(exchangeRatesTimestamp);
        model_.setCurrencies(currencies);
        view_.initializeComboBoxes();
        view_.updateLabels();
    }

    private void calculateExchangeResponseCallback(String calculationResult, String failureReason)
    {
        if (calculationResult != null)
        {
            view_.updateTargetCurrencyTextField(calculationResult);
        }
        else
        {
            view_.showError(failureReason);
            System.err.println("Error, could not get calculation result because " + failureReason);
        }
    }

    private void updateCacheResponseCallback(Boolean valid, String newExchangeRatesTimestamp)
    {
        String message;

        if (valid)
        {
            message = "Cache update successful";
            view_.showConfirmationDialog(true, message);
            System.out.println(message);
            model_.setExchangeRatesTimestamp(newExchangeRatesTimestamp);
            view_.updateLabels();
        }
        else
        {
            message = "Cache update failed. See server logs for details.";
            view_.showConfirmationDialog(false, message);
            System.err.println(message);
        }

        view_.unlockButtons();
    }

    private void closeApplication()
    {
        closeConnection();
        System.out.println("Closing application");
        System.exit(0);
    }

    private final Model model_;
    private final MainWindow view_;
}
