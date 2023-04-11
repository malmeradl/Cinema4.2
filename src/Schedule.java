import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Schedule {
    ArrayList<Film> filmList;
    private final String fileCsvPath;
    File file;

    public Schedule(String fileCsvPath) {

        file = new File(fileCsvPath);
        try {
            if (!file.exists()) { // проверяем, существует ли файл
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.fileCsvPath = fileCsvPath;
        this.filmList = csvReader();
    }

    public ArrayList<Film> csvReader() {
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(fileCsvPath));
            String line;

            this.filmList = new ArrayList<>();

            while ((line = buffReader.readLine()) != null) {
                try {

                    filmList.add(new Film(line.split(";")[0].replace("\"", ""),
                            line.split(";")[1].replace("\"", ""),
                            line.split(";")[2].replace("\"", ""),
                            Time.getLocalDateTime(line.split(";")[3].replace("\"", ""))));

                } catch (ParseException e) {
                    System.out.println(e);
                }
            }
            buffReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException e) {
            System.err.println("I/O error.");
        }
        sortByDate();
        return this.filmList;
    }

    public void csvPrinter() {
        try {
            Reader reader = new FileReader(fileCsvPath);
            BufferedReader buffReader = new BufferedReader(reader);
            String line;
            int i = 0;
            while ((line = buffReader.readLine()) != null) {
                System.out.println("film " + i + ": " + line);
                i++;
            }
            buffReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException e) {
            System.err.println("I/O error.");
        }
    }

    public void addFilm(String cinemaName, String filmName, String filmGenre, LocalDateTime dateAndTime) throws ParseException {
        this.filmList.add(new Film(cinemaName, filmName, filmGenre, dateAndTime));
        sortByDate();
        writeFilmInFile(fileCsvPath);
    }

    private void writeFilmInFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Film film : this.filmList) {
                writer.write(film.toString());
            }
            writer.close();
            System.out.println("...saving...");

        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFilmByIndex(int index) {
        if (index > filmList.size()) {
            System.err.println("There is no film with such an index.");
            return;
        }
        filmList.remove(index);
        this.writeFilmInFile(fileCsvPath);
    }

    public void editFilmByIndex(int index) throws ParseException {
        if (index > filmList.size()) {
            System.err.println("There is no film with such an index.");
            return;
        }

        System.out.println("Enter new data for the required fields (skip the input if you want to leave the field unchanged): ");

        System.out.print("Input cinemaName: ");
        String cinemaName = Main.in.nextLine();
        System.out.print("Input filmName: ");
        String filmName = Main.in.nextLine();
        System.out.print("Input filmGenre: ");
        String filmGenre = Main.in.nextLine();
        System.out.print("Input date and time (yyyy-MM-dd HH:mm): ");
        String dateAndTime = Main.in.nextLine();


        Film temp = new Film(
                cinemaName.isEmpty() ? filmList.get(index).cinemaName : cinemaName,
                filmName.isEmpty() ? filmList.get(index).filmName : filmName,
                filmGenre.isEmpty() ? filmList.get(index).filmGenre : filmGenre,
                dateAndTime.isEmpty() ? filmList.get(index).dateAndTime : Time.getLocalDateTime(dateAndTime)

        );
        filmList.set(index, temp);
        sortByDate();
        this.writeFilmInFile(fileCsvPath);
    }

    List<Film> getFilmByCinemaAndDate(String cinema, String dateString) throws ParseException {
        List<Film> list = new ArrayList<>();

        LocalDate date = Time.getLocalDate(dateString); // converting a string to an object LocalDate
        LocalDate filmDate;
        for (Film film : filmList) {
            filmDate = film.dateAndTime.toLocalDate();
            if (filmDate.isEqual(date) && Objects.equals(film.cinemaName, cinema)) {
                list.add(film);
            }
        }
        return list;
    }

    List<Film> getFilmByGenre(String genre) throws ParseException {
        List<Film> list = new ArrayList<>();

        for (Film film : filmList) {
            if (Objects.equals(film.filmGenre, genre)) {
                list.add(film);
            }
        }
        return list;
    }

    void sortByDate() {
        filmList.sort(new DateAndTimeComparator());
    }
}