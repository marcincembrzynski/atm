package cembrzynski.atm;

import javafx.util.Pair;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Banknotes {
    public final static int FIVE = 5, TEN = 10, TWENTY = 20, FIFTY = 50;
    private final Map<Integer, Integer> storage;


    public Banknotes(){
        storage = new HashMap<>();
    }

    public Banknotes(int... args) {
        storage = new HashMap<>();
        IntStream.iterate(0, n -> n + 2).limit(args.length / 2).forEach(i -> storage.put(args[i], args[i + 1]));
    }

    public int put(int denomination, int amount, Banknotes vault, Banknotes dispense){
        int value = vault.getAvailableBanknotes(amount, denomination, dispense);
        if(storage.containsKey(denomination)){
            storage.put(denomination, storage.get(denomination) + value);
        }else {
            storage.put(denomination, value);
        }
        return amount - denomination * value;
    }

    public Optional<Banknotes> check(int amount, Banknotes vault){
        if(this.sum() == amount){
            vault.remove(this);
            return Optional.of(this);
        }else{
            return Optional.empty();
        }
    }

    public int get(int key){
        return storage.get(key) == null ? 0 : storage.get(key);
    }

    public int sum(){
        return sum(storage);
    }

    public int sum(Map<Integer, Integer> dispensed){
        return dispensed.keySet().stream().filter(e -> e > 0).reduce(0, (acc, key) -> acc + (dispensed.get(key) * key));
    }

    public void remove(Banknotes toDispense){
        toDispense.storage.entrySet().stream().filter(e -> storage.containsKey(e.getKey())).forEach(e -> storage.put(e.getKey(), storage.get(e.getKey()) - e.getValue()));
    }

    public int getAvailableBanknotes(int amount, int denomination, Banknotes banknotes){
        int inStorage = this.get(denomination) - banknotes.get(denomination);
        int required = amount / denomination;
        return inStorage >= required ? required : inStorage;
    }

    public List<Integer> getDenominations(){
        return storage.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .map(e -> e.getKey())
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    private boolean is5(int amount) {
        return (amount - 5) % 10 == 0;
    }

    public Pair<Integer, Banknotes> getMinNoOfFivesAndGetRemainingAmount(final int amount, Banknotes vault) {
        if (is5(amount) && vault.get(5) > 0) {
            storage.put(5, 1);
            return new Pair<>(amount - 5, this);
        } else if (vault.get(5) > 1) {
            storage.put(5, 2);
            return new Pair<>(amount - 10, this);
        }
        return new Pair<>(amount, this);
    }
}