package HW4.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import HW4.integration.CurrencyConverterDAO;
import HW4.model.Currency;
import HW4.model.ExchangeFailedException;
import HW4.model.CurrencyDTO;

@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Facade {
    @EJB CurrencyConverterDAO currencyDB;

    public CurrencyDTO addNewCurrency(String currencyName, double exchangeRate) throws ExchangeFailedException {
        Currency newCurrency = new Currency(currencyName, exchangeRate);
        Currency existsInDB = currencyDB.findCurrencyByName(currencyName);
        if(existsInDB != null){
            throw new ExchangeFailedException(
                "The currency " + currencyName + " already exists in the database. You don't have to add it!");
        }
        currencyDB.storeCurrency(newCurrency);
        return newCurrency;
    }

    public CurrencyDTO convertCurrency(String currencyName, String exchangeToCurrency, double exchangeAmount) throws ExchangeFailedException {
        return currencyDB.convertCurrency(currencyName, exchangeToCurrency, exchangeAmount);
    }

}
