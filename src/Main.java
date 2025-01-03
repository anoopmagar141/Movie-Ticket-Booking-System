import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

class MarvelMovieBookingSystem {

    private static final String[] MOVIES = {
            "Avengers: Endgame", "Spider-Man: No Way Home", "Black Panther",
            "Iron Man", "Thor: Ragnarok", "Doctor Strange",
            "Guardians of the Galaxy", "Captain Marvel", "Ant-Man", "The Avengers"
    };

    private static final String[] FACILITIES = {"Popcorn", "Cold Drinks", "3D Glasses"};
    private static final int BASE_TICKET_PRICE = 150;
    private int[][] seats;
    private int[] ticketSales;

    public MarvelMovieBookingSystem() {
        this.seats = new int[MOVIES.length][30];
        this.ticketSales = new int[MOVIES.length];
        preBookSeats();
    }

    private void greetUser() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.NOON)) {
            System.out.println("Good Morning! Welcome to QFX Booking!");
        } else if (now.isBefore(LocalTime.of(18, 0))) {
            System.out.println("Good Afternoon! Welcome to QFX Booking!");
        } else {
            System.out.println("Good Evening! Welcome to QFX Booking!");
        }
    }

    private void preBookSeats() {
        Random random = new Random();
        for (int i = 0; i < MOVIES.length; i++) {
            if (i < 2) {
                for (int j = 0; j < seats[i].length; j++) {
                    seats[i][j] = 1;
                }
                ticketSales[i] = seats[i].length;
            } else {
                int randomSeats = random.nextInt(25);
                for (int j = 0; j < randomSeats; j++) {
                    seats[i][j] = 1;
                }
                ticketSales[i] = randomSeats;
            }
        }
    }


    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        greetUser();

        int choice;
        do {
            System.out.println("\nMain Menu:");
            System.out.println("1. View Movies and Seats");
            System.out.println("2. Book Tickets");
            System.out.println("3. View Offers");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMoviesAndSeats();
                    break;
                case 2:
                    bookTickets(scanner);
                    break;
                case 3:
                    viewOffers();
                    break;
                case 4:
                    System.out.println("Thank you for using QFX Booking! Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }

    public static void main(String[] args) {
        MarvelMovieBookingSystem bookingSystem = new MarvelMovieBookingSystem();
        bookingSystem.mainMenu();
    }
}
