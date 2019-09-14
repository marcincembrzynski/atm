package cembrzynski.atm;

import javafx.util.Pair;
import java.util.*;

public class ATMService {

    private final Banknotes vault;

    public ATMService(int... args) {
        this.vault = new Banknotes(args);
    }

    public Optional<Banknotes> dispense(final int amount) {
        Pair<Integer, Banknotes> amountAndBanknotes = new Banknotes().getMinNoOfFivesAndGetRemainingAmount(amount, vault);
        return dispense(amountAndBanknotes.getKey(), vault, vault.getDenominations(), amountAndBanknotes.getValue())
                .check(amount, vault);
    }

    private Banknotes dispense(int amount, Banknotes vault, List<Integer> denominations, Banknotes dispense) {
        return denominations.size() > 0 ? getBanknotes(amount, vault, denominations, dispense) : dispense;
    }

    private Banknotes getBanknotes(int amount, Banknotes vault, List<Integer> denominations, Banknotes dispense) {
        Integer denomination = getNext(denominations);
        if (amount >= denomination) {
            return dispense(dispense.put(denomination, amount, vault, dispense), vault, denominations, dispense);
        } else {
            return dispense(amount, vault, denominations, dispense);
        }
    }

    public int getTotalAvailable() {
        return vault.sum();
    }

    public Integer getNext(List<Integer> denominations){
        Integer denomination = denominations.get(0);
        denominations.remove(0);
        return denomination;
    }
}