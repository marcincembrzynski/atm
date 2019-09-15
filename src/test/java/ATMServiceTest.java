import cembrzynski.atm.ATMService;
import cembrzynski.atm.Banknotes;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class ATMServiceTest {

    @Test
    public void shouldDispense(){

        ATMService atmService = new ATMService(Banknotes.FIVE, 10, Banknotes.TEN, 1, Banknotes.TWENTY, 2, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(75);

        Assert.assertEquals(1, actual.get().get(50));
        Assert.assertEquals(1, actual.get().get(20));
        Assert.assertEquals(1, actual.get().get(5));
        Assert.assertEquals(75, atmService.getTotalAvailable());

    }

    @Test
    public void shouldNotDispenseWhenNotEnoughInVault(){
        ATMService atmService = new ATMService(Banknotes.FIVE, 10, Banknotes.TEN, 1, Banknotes.TWENTY, 2, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(155);

        Assert.assertFalse(actual.isPresent());
        Assert.assertEquals(150, atmService.getTotalAvailable());
    }

    @Test
    public void shouldNotDispenseWhenVaultEmpty(){
        ATMService atmService = new ATMService(Banknotes.FIVE, 0, Banknotes.TEN, 0, Banknotes.TWENTY, 0, Banknotes.FIFTY, 0);

        Optional<Banknotes> actual = atmService.dispense(155);

        Assert.assertFalse(actual.isPresent());
        Assert.assertEquals(0, atmService.getTotalAvailable());
    }

    @Test
    public void shouldDispense2BankNotes(){
        ATMService atmService = new ATMService(Banknotes.FIVE, 10, Banknotes.TEN, 1, Banknotes.TWENTY, 2, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(15);

        Assert.assertEquals(1, actual.get().get(10));
        Assert.assertEquals(1, actual.get().get(5));
        Assert.assertEquals(135, atmService.getTotalAvailable());
    }

    @Test
    public void shouldNotDispenseWhenFiveNotAvailable(){
        ATMService atmService = new ATMService(Banknotes.TEN, 10, Banknotes.TWENTY, 2, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(15);

        Assert.assertFalse(actual.isPresent());
        Assert.assertEquals(190, atmService.getTotalAvailable());
    }

    @Test
    public void shouldDispense2FivesAndTwoTwenties(){
        ATMService atmService = new ATMService(Banknotes.FIVE, 10, Banknotes.TEN, 10, Banknotes.TWENTY, 2, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(50);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(190, atmService.getTotalAvailable());
        Assert.assertEquals(2, actual.get().get(5));
        Assert.assertEquals(2, actual.get().get(20));
    }

    @Test
    public void shouldDispense2FivesAnd1Ten(){
        ATMService atmService = new ATMService(Banknotes.FIVE, 10, Banknotes.TEN, 10, Banknotes.TWENTY, 2, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(20);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(220, atmService.getTotalAvailable());
        Assert.assertEquals(2, actual.get().get(5));
        Assert.assertEquals(1, actual.get().get(10));
    }

    @Test
    public void shouldDispense2Ten(){
        ATMService atmService = new ATMService(Banknotes.FIVE, 1, Banknotes.TEN, 10, Banknotes.FIFTY, 1);
        System.out.println(atmService.getTotalAvailable());

        Optional<Banknotes> actual = atmService.dispense(20);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(135, atmService.getTotalAvailable());
        Assert.assertEquals(2, actual.get().get(10));
    }

    @Test
    public void shouldDispense2TenWhenFiveNotAvailable(){
        ATMService atmService = new ATMService(Banknotes.TEN, 10, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(20);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(130, atmService.getTotalAvailable());
        Assert.assertEquals(2, actual.get().get(10));
    }

    @Test
    public void shouldNotDispenseTens(){
        ATMService atmService = new ATMService(Banknotes.TEN, 1, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(20);

        Assert.assertFalse(actual.isPresent());
        Assert.assertEquals(60, atmService.getTotalAvailable());
    }

    @Test
    public void shouldDispenseOnlyFives(){
        ATMService atmService = new ATMService(Banknotes.FIVE, 10, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(20);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(4, actual.get().get(5));
        Assert.assertEquals(80, atmService.getTotalAvailable());
    }

    @Test
    public void shouldDispense3FivesAnd1Twenty(){
        ATMService atmService = new ATMService(Banknotes.FIVE, 10, Banknotes.TWENTY, 10, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(35);

        Assert.assertTrue(actual.isPresent());
        Assert.assertEquals(3, actual.get().get(5));
        Assert.assertEquals(1, actual.get().get(20));
        Assert.assertEquals(265, atmService.getTotalAvailable());
    }

    @Test
    public void shouldNotDispense(){
        ATMService atmService = new ATMService(Banknotes.FIVE, 6, Banknotes.FIFTY, 1);

        Optional<Banknotes> actual = atmService.dispense(35);

        Assert.assertFalse(actual.isPresent());
        Assert.assertEquals(80, atmService.getTotalAvailable());
    }

}
