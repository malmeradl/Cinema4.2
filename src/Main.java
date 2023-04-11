import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
//cinemaName;FilmName;FilmGenre;yyy-MM-dd HH:mm

public class Main {
    static Scanner in;

    public static void main(String[] args) throws ParseException {

        Schedule schedule = new Schedule("film.csv");
        in = new Scanner(System.in);


        System.out.println("Enter the commands: ");
        System.out.println("0 - exit");
        System.out.println("1 - print the contents of the CSV file");
        System.out.println("2 - add film");
        System.out.println("3 - delete film by index");
        System.out.println("4 - edit film by index");
        System.out.println("5 - print the film schedule by cinemaName and date");
        System.out.println("6 - print the film schedule by genre");

        System.out.print("\nInput: ");
        int choice = Integer.parseInt(in.nextLine());

        while (choice != 0) {

            switch (choice) {
                case 0 -> {
                    System.out.println("Exit.");
                }
                case 1 -> {
                    schedule.csvPrinter();
                }
                case 2 -> {
                    System.out.print("Input cinemaName: ");
                    String cinemaName = in.nextLine();
                    System.out.print("Input filmName: ");
                    String filmName = in.nextLine();
                    System.out.print("Input filmGenre: ");
                    String filmGenre = in.nextLine();
                    System.out.print("Input date and time (yyyy-MM-dd HH:mm): ");
                    String dateAndTime = in.nextLine();

                    schedule.addFilm(cinemaName, filmName, filmGenre, Time.getLocalDateTime(dateAndTime));
                }
                case 3 -> {
                    System.out.print("Index of the film to be deleted: ");
                    String index = in.nextLine();
                    schedule.deleteFilmByIndex(Integer.parseInt(index));
                }
                case 4 -> {
                    System.out.print("Index of the film to be edited: ");
                    String index = in.nextLine();
                    schedule.editFilmByIndex(Integer.parseInt(index));
                }
                case 5 -> {
                    System.out.println("Print the film schedule by cinemaName and date");
                    System.out.print("Input cinemaName: ");
                    String cinemaName = in.nextLine();
                    System.out.print("Input date and time (yyyy-MM-dd HH:mm): ");
                    String dateAndTime = in.nextLine();
                    Main.printList(schedule.getFilmByCinemaAndDate(cinemaName, dateAndTime));
                    //"Мир", "2023-09-13 00:00"
                }
                case 6 -> {
                    System.out.print("Input filmGenre: ");
                    String filmGenre = in.nextLine();
                    Main.printList(schedule.getFilmByGenre(filmGenre));

                }
                default -> {
                    System.out.println("No such command.");
                }

            }

            System.out.print("\nInput: ");
            choice = Integer.parseInt(in.nextLine());
        }
        in.close();
    }

    static void printList(List<Film> list) {
        for (Film i : list
        ) {
            System.out.println(i.toString());
        }
    }

}
