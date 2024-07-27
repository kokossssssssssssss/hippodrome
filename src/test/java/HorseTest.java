
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HorseTest {


    @Test
    //при передаче нуля первым параметром конструктора будет исключение и сообщение
    public void firstArgumentConstructorNotNull(){
        Exception e = Assertions.assertThrows(IllegalArgumentException.class , () ->new Horse(null, 1,1));
        Assertions.assertEquals("Name cannot be null.", e.getMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "    "})
    //если первым аргументом пустая строка или пробелы, то исключение и сообщение
    public void firstArgumentConstructorNotSpace(String string){
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(string, 1, 1));
        Assertions.assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    //второй параметр не отрицательный: исключение и сообщение
    public void secondParameterNotNegative(){
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("name", -1, 1));
        Assertions.assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    //третий параметр аналогично
    public void thirdParameterNotNegative(){
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("name", 1, -1));
        Assertions.assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    public void getNameTest(){
        Horse horse = new Horse("name", 1, 1);
        Assertions.assertEquals("name", horse.getName());
    }

    @Test
    public void getSpeedTest(){
        Horse horse = new Horse("name", 5, 1);
        Assertions.assertEquals(5, horse.getSpeed());
    }

    @Test
    public void getDistanceTestWithParameter(){
        Horse horse = new Horse("name", 1, 5);
        Assertions.assertEquals(5, horse.getDistance());
    }
    @Test
    //если конструктор с 2 параметрами, то дистанция 0
    public void getDistanceTestWithoutParameters(){
        Horse horse = new Horse("name", 1);
        Assertions.assertEquals(0, horse.getDistance());
    }

    @Test
    //проверить, что вызывается getRandomDouble с параметрами 0.2 и 0.9;
    public void moveTest(){
        try(MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            new Horse("name", 1, 1).move();

            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {2.5, 1, 1.6, 2.4})
    //присваивается дистанции значение высчитанное по формуле: distance + speed * getRandomDouble(0.2, 0.9)
    public void distanceTest(double random){
        try(MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)){
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            Horse horse = new Horse("name", 100, 200);

            horse.move();

            Assertions.assertEquals(200 + 100 * random, horse.getDistance());
        }

    }
}
