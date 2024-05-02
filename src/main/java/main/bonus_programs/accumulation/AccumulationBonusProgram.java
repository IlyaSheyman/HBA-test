package main.bonus_programs.accumulation;

import main.bonus_programs.BonusProgram;

import java.math.BigDecimal;

public class AccumulationBonusProgram extends BonusProgram {
    private final BigDecimal accumulation;

    public AccumulationBonusProgram(BigDecimal accumulationPercent) {
        this.accumulation = accumulationPercent;
    }

    @Override
    public BigDecimal calculateBonus(BigDecimal amount) {
        return amount.multiply(accumulation);
    }

}