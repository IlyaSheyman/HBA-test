package main.model;

import java.math.BigDecimal;

import static main.constants.Constants.CREDIT_CARD_LIMIT;

public class CreditCard extends BankCard {
    private BigDecimal credit;

    public CreditCard(BigDecimal initialBalance, BigDecimal credit) {
        super(initialBalance);
        this.credit = credit;
    }

    @Override
    public void topUp(BigDecimal amount) {
        BigDecimal creditLimit = BigDecimal.valueOf(CREDIT_CARD_LIMIT);
        if (credit.compareTo(creditLimit) >= 0) {
            balance = balance.add(amount);
        } else {
            BigDecimal remainingCreditLimit = creditLimit.subtract(credit);

            if (amount.compareTo(remainingCreditLimit) > 0) {
                BigDecimal amountToBalance = amount.subtract(remainingCreditLimit);
                credit = credit.add(remainingCreditLimit);
                balance = balance.add(amountToBalance);
            } else {
                credit = credit.add(amount);
            }
        }
    }

    @Override
    public boolean pay(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
            return true;
        } else if (balance.add(credit).compareTo(amount) >= 0) {
            BigDecimal remainingAmount = amount.subtract(balance);
            balance = BigDecimal.ZERO;
            credit = credit.subtract(remainingAmount);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BigDecimal getAvailableFunds() {
        return balance;
    }

    public BigDecimal getCreditFunds() {
        return credit;
    }
}