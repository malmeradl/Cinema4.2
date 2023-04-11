import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Film {
    String cinemaName;
    String filmName;
    String filmGenre;
    LocalDateTime dateAndTime;

    Film(String cName, String fName, String genre, LocalDateTime dateAndTime) {
        this.cinemaName = cName;
        this.filmName = fName;
        this.filmGenre = genre;
        this.dateAndTime = dateAndTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "\"" + cinemaName + "\";\"" + filmName + "\";\"" + filmGenre + "\";\"" + formatter.format(dateAndTime) + "\"\n";
    }


}
