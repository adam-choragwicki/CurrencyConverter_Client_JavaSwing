package view;

import types.Currency;
import main.Model;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class MainWindow extends JFrame
{
    public MainWindow(Model model)
    {
        super("Currency converter");
        model_ = model;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        mainFont = new Font(null, Font.BOLD, 20);

        addComponents();
    }

    private void addComponents()
    {
        JPanel timestampPanel = new JPanel();
        addTimestampLabel(timestampPanel);

        JPanel requestPanel = new JPanel();
        addExchangeLabel(requestPanel);
        addSourceCurrencyTextField(requestPanel);
        addSourceCurrencyComboBox(requestPanel);
        addToLabel(requestPanel);
        addTargetCurrencyComboBox(requestPanel);

        JPanel calculatePanel = new JPanel();
        addCalculateButton(calculatePanel);

        JPanel resultPanel = new JPanel();
        addTargetCurrencyAmountLabel(resultPanel);
        addTargetCurrencyNameLabel(resultPanel);
        resultPanel.setPreferredSize(new Dimension(100, 50));

        JPanel bottomPanel = new JPanel();
        addUpdateCacheButton(bottomPanel);
        addCloseButton(bottomPanel);

        add(timestampPanel);
        add(new JSeparator());
        add(requestPanel);
        add(new JSeparator());
        add(calculatePanel);
        add(new JSeparator());
        add(resultPanel);
        add(new JSeparator());
        add(bottomPanel);
    }

    private void addTimestampLabel(JPanel timestampPanel)
    {
        timestampLabel = new JLabel();
        timestampLabel.setFont(mainFont);
        setExchangeRateTimestampLabel("NULL");
        timestampPanel.add(timestampLabel);
    }

    private void addExchangeLabel(JPanel requestPanel)
    {
        JLabel exchangeLabel = new JLabel("Exchange");
        exchangeLabel.setFont(mainFont);
        requestPanel.add(exchangeLabel);
    }

    private void addSourceCurrencyTextField(JPanel requestPanel)
    {
        sourceCurrencyTextField = new JTextField("1");
        sourceCurrencyTextField.setHorizontalAlignment(JTextField.CENTER);
        sourceCurrencyTextField.setFont(mainFont);
        sourceCurrencyTextField.setPreferredSize(new Dimension(150, 40));
        sourceCurrencyTextField.setCaretPosition(sourceCurrencyTextField.getText().length());
        sourceCurrencyTextField.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyTyped(KeyEvent evt)
            {
                final int CHARACTER_LIMIT = 8;

                if (sourceCurrencyTextField.getText().length() >= CHARACTER_LIMIT && !(evt.getKeyChar() == KeyEvent.VK_DELETE || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE))
                {
                    getToolkit().beep();
                    evt.consume();
                }
            }
        });

        sourceCurrencyTextField.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void changedUpdate(DocumentEvent e)
            {
                processUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                processUpdate();
            }

            @Override
            public void insertUpdate(DocumentEvent e)
            {
                processUpdate();
            }

            private void processUpdate()
            {
                if (sourceCurrencyTextField.getText().isEmpty())
                {
                    sourceCurrencyTextField.setBackground(Color.red);
                }
                else
                {
                    sourceCurrencyTextField.setBackground(Color.white);
                }
            }
        });

        requestPanel.add(sourceCurrencyTextField);
    }

    private void addSourceCurrencyComboBox(JPanel requestPanel)
    {
        sourceCurrencyComboBox = new JComboBox<>();
        sourceCurrencyComboBox.setFont(mainFont);
        ((JLabel) sourceCurrencyComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        requestPanel.add(sourceCurrencyComboBox);
    }

    private void addToLabel(JPanel requestPanel)
    {
        JLabel toLabel = new JLabel("to");
        toLabel.setFont(mainFont);
        requestPanel.add(toLabel);
    }

    private void addTargetCurrencyComboBox(JPanel requestPanel)
    {
        targetCurrencyComboBox = new JComboBox<>();
        targetCurrencyComboBox.setFont(mainFont);
        ((JLabel) targetCurrencyComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        requestPanel.add(targetCurrencyComboBox);
    }

    private void addCalculateButton(JPanel calculatePanel)
    {
        calculateButton = new JButton("Calculate");
        calculateButton.setFont(mainFont);

        calculatePanel.add(calculateButton);
    }

    private void addTargetCurrencyAmountLabel(JPanel resultPanel)
    {
        targetCurrencyAmountLabel = new JLabel();
        targetCurrencyAmountLabel.setFont(mainFont);
        targetCurrencyAmountLabel.setFocusable(false);

        resultPanel.add(targetCurrencyAmountLabel);
    }

    private void addTargetCurrencyNameLabel(JPanel resultPanel)
    {
        targetCurrencyNameLabel = new JLabel();
        targetCurrencyNameLabel.setFont(mainFont);
        resultPanel.add(targetCurrencyNameLabel);
    }

    private void addUpdateCacheButton(JPanel panel)
    {
        updateCacheButton = new JButton("Update cache");
        panel.add(updateCacheButton);

        updateCacheButton.setFont(mainFont);

        panel.add(updateCacheButton);
    }

    private void addCloseButton(JPanel panel)
    {
        closeButton = new JButton("Close");
        panel.add(closeButton);

        closeButton.setFont(mainFont);

        panel.add(closeButton);
    }

    public void updateTargetCurrencyTextField(String result)
    {
        targetCurrencyAmountLabel.setText(result);
        targetCurrencyAmountLabel.setForeground(Color.black);
        targetCurrencyNameLabel.setText(((Currency) targetCurrencyComboBox.getSelectedItem()).getName());
    }

    public void showError(String errorMessage)
    {
        targetCurrencyAmountLabel.setText("Error, " + errorMessage);
        targetCurrencyAmountLabel.setForeground(Color.red);
        targetCurrencyNameLabel.setText("");
    }

    public int showConnectionErrorDialog(String message)
    {
        Object[] options = {"Yes", "Quit"};
        return JOptionPane.showOptionDialog(this, message, "Connection error", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
    }

    public void showConnectionClosedByServerDialog(String message)
    {
        int dialogIcon = JOptionPane.ERROR_MESSAGE;
        JOptionPane.showConfirmDialog(this, message, "", JOptionPane.DEFAULT_OPTION, dialogIcon);
    }

    public int showUpdateCacheDialog()
    {
        Object[] options = {"Yes", "Cancel"};
        String message = "Current exchange rates will be downloaded from source website. This might take a while. Do you want to continue?";
        return JOptionPane.showOptionDialog(this, message, "Update cache requested", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    public void showConfirmationDialog(boolean success, String message)
    {
        int dialogIcon;

        if (success)
        {
            dialogIcon = JOptionPane.INFORMATION_MESSAGE;
        }
        else
        {
            dialogIcon = JOptionPane.ERROR_MESSAGE;
        }

        JOptionPane.showConfirmDialog(this, message, "", JOptionPane.DEFAULT_OPTION, dialogIcon);
    }

    public void initializeComboBoxes()
    {
        sourceCurrencyComboBox.removeAllItems();
        targetCurrencyComboBox.removeAllItems();

        ArrayList<Currency> currencies = model_.getCurrencies();

        for (Currency currency : currencies)
        {
            sourceCurrencyComboBox.addItem(currency);
        }

        for (Currency currency : currencies)
        {
            targetCurrencyComboBox.addItem(currency);
        }

        /*Set to EUR*/
        sourceCurrencyComboBox.setSelectedIndex(42);

        /*Set to USD*/
        targetCurrencyComboBox.setSelectedIndex(136);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateLabels()
    {
        setExchangeRateTimestampLabel(model_.getExchangeRatesTimestamp());
        targetCurrencyAmountLabel.setText("");
        targetCurrencyNameLabel.setText("");
    }

    public JButton getCalculateButton()
    {
        return calculateButton;
    }

    public JButton getUpdateCacheButton()
    {
        return updateCacheButton;
    }

    public JButton getCloseButton()
    {
        return closeButton;
    }

    public JTextField getSourceCurrencyTextField()
    {
        return sourceCurrencyTextField;
    }

    public JComboBox<Currency> getSourceCurrencyComboBox()
    {
        return sourceCurrencyComboBox;
    }

    public JComboBox<Currency> getTargetCurrencyComboBox()
    {
        return targetCurrencyComboBox;
    }

    private void setExchangeRateTimestampLabel(String exchangeRateTimestamp)
    {
        timestampLabel.setText("Exchange rates timestamp: " + exchangeRateTimestamp);
    }

    public void lockButtons()
    {
        calculateButton.setEnabled(false);
        updateCacheButton.setEnabled(false);
        closeButton.setEnabled(false);
    }

    public void unlockButtons()
    {
        calculateButton.setEnabled(true);
        updateCacheButton.setEnabled(true);
        closeButton.setEnabled(true);
    }

    private final Font mainFont;

    private JLabel timestampLabel;
    private JTextField sourceCurrencyTextField;
    private JComboBox<Currency> sourceCurrencyComboBox;
    private JComboBox<Currency> targetCurrencyComboBox;
    private JLabel targetCurrencyAmountLabel;
    private JLabel targetCurrencyNameLabel;

    private JButton calculateButton;
    private JButton updateCacheButton;
    private JButton closeButton;

    private final Model model_;
}
