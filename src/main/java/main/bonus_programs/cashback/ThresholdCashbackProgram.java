package main.bonus_programs.cashback;

import main.bonus_programs.BonusProgram;

import java.math.BigDecimal;

public class ThresholdCashbackProgram extends BonusProgram {
    private final BigDecimal threshold;
    private final BigDecimal cashback;

    public ThresholdCashbackProgram(BigDecimal threshold, BigDecimal cashback) {
        this.threshold = threshold;
        this.cashback = cashback;
    }

    @Override
    public BigDecimal calculateBonus(BigDecimal amount) {
        if (amount.compareTo(threshold) >= 0) {
            return amount.multiply(cashback);
        }
        return BigDecimal.ZERO;
    }
}
