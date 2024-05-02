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
            // Если текущий кредит превышает или равен кредитному лимиту,
            // пополнение добавляется на баланс
            balance = balance.add(amount);
        } else {
            // Если доступный кредит ниже кредитного лимита,
            // то пополнение делится между кредитом и балансом
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
        } else if (credit.compareTo(amount) >= 0) {
            credit = credit.subtract(amount);
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