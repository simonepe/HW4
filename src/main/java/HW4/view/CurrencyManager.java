
package HW4.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import HW4.controller.Facade;
import HW4.model.CurrencyDTO;

/**
 * Handles all interaction with the account JSF page.
 */
@Named("currencyManager")
@ConversationScoped
public class CurrencyManager implements Serializable {
    @EJB
    private Facade facade;
    private CurrencyDTO currentCurrency;
    private String newCurrencyName;
    private double newExchangeRate;
    private double exchangeAmount;
    private String searchedCurrency;
    private String exchangeToCurrency;
    private CurrencyDTO currencyAdded;
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
    }

    public boolean getSuccess() {
        return transactionFailure == null;
    }

    public Exception getException() {
        return transactionFailure;
    }

    public void addNewCurrency() {
        try {
            startConversation();
            transactionFailure = null;
            currencyAdded = facade.addNewCurrency(newCurrencyName, newExchangeRate);
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public void convertCurrency() {
        try {
            startConversation();
            transactionFailure = null;
            currentCurrency = facade.convertCurrency(searchedCurrency, exchangeToCurrency, exchangeAmount);       
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public void setSearchedCurrency(String searchedCurrency) {
        this.searchedCurrency = searchedCurrency;
    }

    public String getSearchedCurrency() {
        return null;
    }
    
    public void setExchangeToCurrency(String exchangeToCurrency) {
        this.exchangeToCurrency = exchangeToCurrency;
    }

    public String getExchangeToCurrency() {
        return null;
    }

    public void setNewExchangeRate(double newExchangeRate) {
        this.newExchangeRate = newExchangeRate;
    }

    public double getNewExchangeRate() {
        return 0;
    }

    public void setExchangeAmount(double newExchangeAmount) {
        this.exchangeAmount = newExchangeAmount;
    }

    public double getExchangeAmount() {
        return 0;
    }
    
    public void setNewCurrencyName(String newCurrencyName) {
        this.newCurrencyName = newCurrencyName;
    }

    public String getNewCurrencyName() {
        return null;
    }
    public void setCurrencyAdded(CurrencyDTO currencyAdded) {
        this.currencyAdded = currencyAdded;
    }

    public CurrencyDTO getCurrencyAdded() {
        return currencyAdded;
    }

    public CurrencyDTO getCurrentCurrency() {
        return currentCurrency;
    }
}