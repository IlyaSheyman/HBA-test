package main;

import main.bonus_programs.BonusProgram;
import main.bonus_programs.accumulation.AccumulationBonusProgram;
import main.bonus_programs.cashback.ThresholdCashbackProgram;
import main.bonus_programs.cashback.TotalCashbackProgram;
import main.exception.UnknownTypeException;
import main.model.BankCard;
import main.model.CreditCard;
import main.model.DebitCard;
import main.model.bonus_cards.accumulation.CreditCardWithAccumulation;
import main.model.bonus_cards.accumulation.DebitCardWithAccumulation;
import main.model.bonus_cards.cashback.CreditCardWithCashback;
import main.model.bonus_cards.cashback.DebitCardWithCashback;

import java.math.BigDecimal;
import java.util.Scanner;

import static main.constants.Constants.*;

/**
 * Класс представляет консольное приложение для управления банковскими картами.
 */
public class BankCardApp {
    public static void main(String[] args) {
        runBankCardApp();
    }

    /**
     * Метод запускает приложение для управления банковскими картами.
     */
    public static void runBankCardApp() {
        Scanner scanner = new Scanner(System.in);

        int choice = chooseCardType(scanner);
        BankCard card = createCard(scanner, choice);

        boolean exit = false;
        while (!exit) {
            printActionMenu();
            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    topUpCard(scanner, card);
                    break;
                case 2:
                    payWithCard(scanner, card);
                    break;
                case 3:
                    printCardBalance(card);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Неправильный выбор");
                    break;
            }
        }
    }

    /**
     * Метод создает банковскую карту
     */
    private static BankCard createCard(Scanner scanner, int choice) {
        if (choice == 1) {
            System.out.println("Пополните (введите) начальный баланс для дебетовой карты:");
            BigDecimal initialBalance = scanner.nextBigDecimal();
            return createCardWithBonus(initialBalance, BigDecimal.valueOf(0), scanner, "debit");
        } else if (choice == 2) {
            System.out.println("Пополните (введите) начальный баланс для кредитной карты:");
            BigDecimal initialBalance = scanner.nextBigDecimal();

            BigDecimal creditLimit = BigDecimal.valueOf(CREDIT_CARD_LIMIT);
            System.out.println("Ваш кредитный лимит: " + CREDIT_CARD_LIMIT);

            return createCardWithBonus(initialBalance, creditLimit, scanner, "credit");
        } else {
            System.out.println("Неправильный выбор");
            System.exit(1);
            return null;
        }
    }

    /**
     * Пополнение карты
     */
    private static void topUpCard(Scanner scanner, BankCard card) {
        System.out.println("Введите сумму для пополнения:");
        BigDecimal amountToTopUp = scanner.nextBigDecimal();
        card.topUp(amountToTopUp);
        System.out.println("Карта пополнена на " + amountToTopUp + " рублей");
    }

    /**
     * Оплата картой
     */
    private static void payWithCard(Scanner scanner, BankCard card) {
        System.out.println("Введите сумму для оплаты:");
        BigDecimal amountToPay = scanner.nextBigDecimal();
        boolean paid = card.pay(amountToPay);
        if (paid) {
            System.out.println("Оплата прошла успешно");
        } else {
            System.out.println("Недостаточно средств для оплаты");
        }
    }

    /**
     * Узнать баланс карты
     */
    private static void printCardBalance(BankCard card) {
        if (card instanceof CreditCard creditCard) {
            System.out.println("Кредитные средства: " + creditCard.getCreditFunds());
            System.out.println("Собственные средства: " + creditCard.getAvailableFunds());
        } else {
            System.out.println("Доступные средства: " + card.getAvailableFunds());
        }
    }

    /**
     * Создать карту с бонусной программой
     */
    private static BankCard createCardWithBonus(BigDecimal initialBalance,
                                                BigDecimal creditLimit,
                                                Scanner scanner,
                                                String type) {
        int bonusChoice = chooseBonusProgram(scanner);
        BonusProgram bonusProgram = null;

        if (bonusChoice == 1) {
            BigDecimal cashbackPercent = BigDecimal.valueOf(CASHBACK_FOR_ALL_PURCHASES);
            bonusProgram = new TotalCashbackProgram(cashbackPercent);
        } else if (bonusChoice == 2) {
            BigDecimal cashbackPercent = BigDecimal.valueOf(CASHBACK_WITH_THRESHOLD);
            BigDecimal threshold = BigDecimal.valueOf(THRESHOLD);
            bonusProgram = new ThresholdCashbackProgram(threshold, cashbackPercent);
        } else if (bonusChoice == 3) {
            BigDecimal accumulationPercent = BigDecimal.valueOf(ACCUMULATION);
            bonusProgram = new AccumulationBonusProgram(accumulationPercent);
        } else if (bonusChoice == 4) {
            if (type.equals("debit")) {
                return new DebitCard(initialBalance);
            } else if (type.equals("credit")) {
                return new CreditCard(initialBalance, creditLimit);
            } else {
                throw new UnknownTypeException("Неизвестный тип карты");
            }
        } else {
            System.out.println("Неправильный выбор");
            System.exit(1);
        }
        return createCard(initialBalance, creditLimit, type, bonusProgram);
    }

    private static BankCard createCard(BigDecimal initialBalance,
                                       BigDecimal creditLimit,
                                       String type,
                                       BonusProgram bonusProgram) {
        if (type.equals("debit")) {
            if (bonusProgram.getClass().equals(AccumulationBonusProgram.class)) {
                return new DebitCardWithAccumulation(initialBalance, bonusProgram);
            } else {
                return new DebitCardWithCashback(initialBalance, bonusProgram);
            }
        } else if (type.equals("credit")) {
            if (bonusProgram.getClass().equals(AccumulationBonusProgram.class)) {
                return new CreditCardWithAccumulation(initialBalance, creditLimit, bonusProgram);
            } else {
                return new CreditCardWithCashback(initialBalance, creditLimit, bonusProgram);
            }
        } else {
            throw new UnknownTypeException("Неизвестный тип карты");
        }
    }

    private static void printActionMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1. Пополнить");
        System.out.println("2. Оплатить");
        System.out.println("3. Получить информацию о балансе");
        System.out.println("4. Выйти");
    }

    private static int chooseCardType(Scanner scanner) {
        System.out.println("Выберите тип карты: ");
        System.out.println("1. Дебетовая карта");
        System.out.println("2. Кредитная карта");
        return scanner.nextInt();
    }

    private static int chooseBonusProgram(Scanner scanner) {
        System.out.println("Выберите бонусную программу для карты:");
        System.out.println("1. Процентный кэшбэк на все покупки (1%)");
        System.out.println("2. Процентный кэшбэк на покупки от 5000 рублей (5%)");
        System.out.println("3. Накопление бонусов от суммы пополнения (3%)");
        System.out.println("4. Без бонусной программы ");
        return scanner.nextInt();
    }
}