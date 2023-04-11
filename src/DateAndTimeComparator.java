import java.util.Comparator;

public class DateAndTimeComparator implements Comparator<Film> {
    @Override
    public int compare(Film film1, Film film2) {
        return film1.dateAndTime.compareTo(film2.dateAndTime);
    }
}