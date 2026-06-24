import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    private ParkingLot parkingLot;

    @BeforeAll
    static void initAll() {
        System.out.println("🚀 Запуск всех тестов для ParkingLot");
        ParkingLot.resetTotalCarsParked();
        System.out.println("   Счетчик припаркованных машин сброшен: " + ParkingLot.getTotalCarsParked());
        System.out.println();
    }

    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot(50, 100.0);
        System.out.println("  ✅ Создана новая парковка на " + parkingLot.getTotalSpaces() + " мест");
        System.out.println("     Тариф: " + parkingLot.getHourlyRate() + " руб/час");
    }

    @AfterEach
    void tearDown() {
        System.out.println("  🧹 Тест завершен. Текущий счетчик машин: " + ParkingLot.getTotalCarsParked());
        System.out.println();
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("🏁 Все тесты ParkingLot завершены");
        System.out.println("   📊 ИТОГО припарковано машин: " + ParkingLot.getTotalCarsParked());
    }

    // ЗАДАНИЕ 1 (Простое)
    @Test
    @DisplayName("Задание 1: Проверка начального состояния парковки")
    void testInitialState() {
        System.out.println("    ⚔️ testInitialState");


        assertEquals(50, parkingLot.getAvailableSpaces(), "Должно быть 50 свободных мест");
        assertEquals(0, parkingLot.getOccupiedSpaces(), "Должно быть 0 занятых мест");
        assertEquals(50, parkingLot.getTotalSpaces(), "Общее количество мест должно быть 50");
        assertEquals(100.0, parkingLot.getHourlyRate(), "Тариф должен быть 100 руб/час");

        System.out.println("    ✅ Парковка создана корректно, все места свободны");
    }

    // ЗАДАНИЕ 2 (Среднее)
    @Test
    @DisplayName("Задание 2: Проверка парковки и расчета оплаты")
    void testParkAndPayment() {
        System.out.println("    ⚔️ testParkAndPayment");

        //  Часть 1: Парковка одной машины
        int availableBefore = parkingLot.getAvailableSpaces();

        boolean parkResult = parkingLot.parkCar();

        assertTrue(parkResult, "Парковка должна быть успешной");
        assertEquals(availableBefore - 1, parkingLot.getAvailableSpaces(),
                "Свободных мест должно стать на 1 меньше");
        assertEquals(1, parkingLot.getOccupiedSpaces(),
                "Занятых мест должно стать 1");

        System.out.println("    ✅ Машина припаркована. Свободно: " + parkingLot.getAvailableSpaces());

        //  Часть 2: Расчет оплаты за 3 часа
        double payment = parkingLot.calculatePayment(3);

        assertEquals(300.0, payment, 0.001,
                "Оплата за 3 часа по 100 руб/час должна быть 300 руб");

        System.out.println("    ✅ Оплата за 3 часа: " + payment + " руб");

        // Часть 3 (дополнительно): Парковка при полном заполнении

        for (int i = 0; i < 49; i++) {
            parkingLot.parkCar();
        }


        assertEquals(0, parkingLot.getAvailableSpaces(), "Не должно быть свободных мест");
        assertEquals(50, parkingLot.getOccupiedSpaces(), "Все места должны быть заняты");

        boolean resultFull = parkingLot.parkCar();

        assertFalse(resultFull, "Парковка должна быть невозможна при полном заполнении");
        assertEquals(50, parkingLot.getOccupiedSpaces(),
                "Количество занятых мест не должно измениться");

        System.out.println("    ✅ Парковка невозможна - все места заняты");
    }

    // ЗАДАНИЕ 3 (Сложное)
    @Test
    @DisplayName("Задание 3: Проверка статического счетчика totalCarsParked")
    void testTotalCarsParkedCounter() {
        System.out.println("    ⚔️ testTotalCarsParkedCounter");

        int before = ParkingLot.getTotalCarsParked();

        parkingLot.parkCar();
        parkingLot.parkCar();
        parkingLot.parkCar();

        int after = ParkingLot.getTotalCarsParked();
        assertEquals(before + 3, after, "Счетчик должен увеличиться на 3");

        System.out.println("    ✅ Счетчик увеличился корректно: " + before + " -> " + after);


        ParkingLot smallParking = new ParkingLot(1, 50.0);
        smallParking.parkCar(); // успешно
        boolean failed = smallParking.parkCar(); // неуспешно (место занято)

        assertFalse(failed, "Парковка должна быть неуспешной");

        int afterFailed = ParkingLot.getTotalCarsParked();

        assertEquals(after + 1, afterFailed,
                "Счетчик должен увеличиться только на 1 (успешная парковка)");

        System.out.println("    ✅ Неуспешная парковка не увеличила счетчик");


        ParkingLot fullParking = new ParkingLot(5, 100.0);
        int beforeFull = ParkingLot.getTotalCarsParked();

        for (int i = 0; i < 5; i++) {
            fullParking.parkCar();
        }

        int afterFull = ParkingLot.getTotalCarsParked();
        assertEquals(beforeFull + 5, afterFull, "Счетчик должен увеличиться на 5");

        System.out.println("    ✅ Полная загрузка: счетчик увеличился на 5");
    }
}