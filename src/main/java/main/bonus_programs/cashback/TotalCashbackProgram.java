package main.bonus_programs.cashback;

import main.bonus_programs.BonusProgram;

import java.math.BigDecimal;

public class TotalCashbackProgram extends BonusProgram {
    private final BigDecimal percent;

    public TotalCashbackProgram(BigDecimal percent) {
        this.percent = percent;
    }

    @Override
    public BigDecimal calculateBonus(BigDecimal amount) {
        return amount.multiply(percent);
    }
}