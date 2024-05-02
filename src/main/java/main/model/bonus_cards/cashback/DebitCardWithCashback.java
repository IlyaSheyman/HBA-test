package main.model.bonus_cards.cashback;

import lombok.Setter;
import main.bonus_programs.BonusProgram;
import main.model.DebitCard;

import java.math.BigDecimal;

@Setter
public class DebitCardWithCashback extends DebitCard {
    private BonusProgram bonusProgram;

    public DebitCardWithCashback(BigDecimal initialBalance, BonusProgram bonusProgram) {
        super(initialBalance);
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