package main.model.bonus_cards.accumulation;

import lombok.Setter;
import main.bonus_programs.BonusProgram;
import main.model.CreditCard;

import java.math.BigDecimal;

@Setter
public class CreditCardWithAccumulation extends CreditCard {
    private BonusProgram bonusProgram;

    public CreditCardWithAccumulation(BigDecimal initialBalance, BigDecimal creditLimit, BonusProgram bonusProgram) {
        super(initialBalance, creditLimit);
        this.bonusProgram = bonusProgram;
    }
    @Override
    public void topUp(BigDecimal amount) {
        BigDecimal bonus = bonusProgram.calculateBonus(amount);
        amount = amount.add(bonus);
        super.topUp(amount);
        System.out.println("Бонус к пополнению: " + bonus + " рублей.");
    }
}