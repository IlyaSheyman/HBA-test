package main.model;

import java.math.BigDecimal;

public class DebitCard extends BankCard {
    public DebitCard(BigDecimal initialBalance) {
        super(initialBalance);
    }

    @Override
    public void topUp(BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public boolean pay(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal getAvailableFunds() {
        return balance;
    }
}