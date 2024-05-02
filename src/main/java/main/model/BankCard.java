package main.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public abstract class BankCard {
    protected BigDecimal balance;

    public BankCard(BigDecimal initialBalance) {
        this.balance = initialBalance;
    }

    public abstract void topUp(BigDecimal amount);

    public abstract boolean pay(BigDecimal amount);

    public abstract BigDecimal getAvailableFunds();
}