import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    String filePath="filmTest.csv";

    @org.junit.jupiter.api.Test
    void getFilmByCinemaAndDate() throws ParseException {
        Schedule schedule = new Schedule(filePath);

        schedule.addFilm("Кашалот", "Конкур", "ужасы", Time.getLocalDateTime("2022-01-15 18:55"));
        schedule.addFilm("Кашалот2", "Конкур", "ужасы", Time.getLocalDateTime("2021-01-15 18:55"));
        schedule.addFilm("Кашалот", "Конкур", "ужасы", Time.getLocalDateTime("2021-01-15 18:55"));
        schedule.addFilm("Кашалот", "Конкур2", "ужасы", Time.getLocalDateTime("2021-01-15 20:55"));
        List<Film> actualList = schedule.getFilmByCinemaAndDate("Кашалот", "2021-01-15 18:55");
        List<Film> expectedList = new ArrayList<>();
        expectedList.add(schedule.filmList.get(2));
        expectedList.add(schedule.filmList.get(3));
        schedule.file.delete();

        assertNotNull(actualList);
        assertEquals(expectedList.size(), actualList.size());
        assertTrue(actualList.contains(expectedList.get(0)));
        assertTrue(actualList.contains(expectedList.get(0)));

    }

}