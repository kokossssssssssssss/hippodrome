import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class HippodromeTest {
    @Test
    public void nullInConstructor(){
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () ->new Hippodrome(null));
        Assertions.assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    public void emptyListInConstructorTest(){
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        Assertions.assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    public void getHoursesTest(){
        List horses = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            horses.add(new Horse("horse" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        Assertions.assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    //проверить, что move вызовется у всех лошадей
    public void moveTest(){
        List<Horse> mockedHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse mockedHorse = Mockito.mock(Horse.class);
            mockedHorses.add(mockedHorse);
        }
        new Hippodrome(mockedHorses).move();

        for(Horse h : mockedHorses){
            Mockito.verify(h).move();
        }
    }

    @Test
    public void getWinnerTest(){
        Horse h1 = new Horse("first", 1, 1);
        Horse h2 = new Horse("second", 1, 2);
        Horse h3 = new Horse("third", 1, 3);
        Hippodrome hippodrome = new Hippodrome(List.of(h1, h2, h3));

        Assertions.assertSame(h3, hippodrome.getWinner());
    }
}
