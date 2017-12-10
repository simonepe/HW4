package HW4.integration;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import HW4.model.Currency;
import HW4.model.ExchangeFailedException;
import javax.persistence.NoResultException;


@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class CurrencyConverterDAO {
    @PersistenceContext(unitName = "currencyConverterPU")
    private EntityManager em;
    
    public Currency convertCurrency(String exchangeFromCurrency, String exchangeToCurrency, double exchangeAmount) throws ExchangeFailedException {
        if (exchangeFromCurrency == null) {
            return null;
        } 
            //EntityManager em = beginTransaction();
            try {
                Currency fromCurrency = findCurrencyByName(exchangeFromCurrency);
                if(fromCurrency == null){
                            throw new ExchangeFailedException(
                             "The currency " + exchangeFromCurrency + " doesn't exist yet. Add it first!");
                 }
                Currency toCurrency = findCurrencyByName(exchangeToCurrency);
                if(toCurrency == null){
                            throw new ExchangeFailedException(
                             "The currency " + exchangeToCurrency + " doesn't exist yet. Add it first!");
                 }
                
                fromCurrency.exchange(fromCurrency.getExchangeRate(), toCurrency.getExchangeRate(), exchangeAmount);
                return fromCurrency;
            } catch (NoResultException e) {
               return null;
            }     
    }
    public Currency findCurrencyByName(String searchedCurrency) {
        if (searchedCurrency == null) {
            return null;
        } 
            try {
                Currency foundCurrency = em.createNamedQuery("findCurrencyByName", Currency.class).
                        setParameter("currencyName", searchedCurrency).getSingleResult();    
                return foundCurrency;
            } catch (NoResultException e) {
                System.out.println("FIND ERROR");
                return null;
            }     
    }

    public void storeCurrency(Currency acct) {
        em.persist(acct);
    }

}
