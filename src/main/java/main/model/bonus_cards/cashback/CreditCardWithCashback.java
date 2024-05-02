package main.model.bonus_cards.cashback;

import lombok.Setter;
import main.bonus_programs.BonusProgram;
import main.model.CreditCard;

import java.math.BigDecimal;

@Setter
public class CreditCardWithCashback extends CreditCard {
    private BonusProgram bonusProgram;

    public CreditCardWithCashback(BigDecimal initialBalance, BigDecimal creditLimit, BonusProgram bonusProgram) {
        super(initialBalance, creditLimit);
        this.bonusProgram = bonusProgram;
    }

    @Override
    public boolean pay(BigDecimal amount) {
        boolean paid = super.pay(amount);
        if (paid) {
            BigDecimal bonus = bonusProgram.calculateBonus(amount);
            super.topUp(bonus);
            System.out.println("Зачислен кэшбэк: " + bonus + " рублей");
        }
        return paid;
    }
}