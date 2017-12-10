
package HW4.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
        @NamedQuery(
            name = "findCurrencyByName",
            query = "SELECT curr FROM Currency curr WHERE curr.currencyName LIKE :currencyName",
           lockMode = LockModeType.NONE
    )
})

@Entity(name = "Currency")
public class Currency implements CurrencyDTO, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int currencyIndex;
    private double exchangeRate;
    private String currencyName;
    private double exchangedAmount;

    public Currency() {
    }
    
    public Currency(String currencyName, double exchangeRate) {
        this.currencyName = currencyName;
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String getCurrencyName() {
        return currencyName;
    }

    @Override
    public double getExchangeRate() {
        return exchangeRate;
    }

    @Override
    public int getCurrencyIndex() {
        return currencyIndex;
    }
    
    @Override
    public double getExchangedAmount() {
        return exchangedAmount;
    }

    public void exchange(double exchangeRateFrom, double exchangeRateTo, double amount) throws ExchangeFailedException{
         if (amount < 0) {
            throw new ExchangeFailedException(
                    "You can't exchange a negative amount. Amount entered was: " + amount);
        } 
        exchangedAmount = (exchangeRateFrom * amount) / exchangeRateTo;
    }
}
