package main.model.bonus_cards.accumulation;

import lombok.Setter;
import main.bonus_programs.BonusProgram;
import main.model.DebitCard;

import java.math.BigDecimal;

@Setter
public class DebitCardWithAccumulation extends DebitCard {
    private BonusProgram bonusProgram;

    public DebitCardWithAccumulation(BigDecimal initialBalance, BonusProgram bonusProgram) {
        super(initialBalance);
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