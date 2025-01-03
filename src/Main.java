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

    private void viewMoviesAndSeats() {
        System.out.println("\nMarvel Movies and Seat Availability:");
        for (int i = 0; i < MOVIES.length; i++) {
            int bookedSeats = ticketSales[i];
            int availableSeats = seats[i].length - bookedSeats;
            System.out.printf("%d. %s - %s\n",
                    (i + 1), MOVIES[i],
                    availableSeats == 0 ? "House Full" : "Available Seats: " + availableSeats + ", Booked Seats: " + bookedSeats);
        }
    }

    private void viewOffers() {
        System.out.println("\nCurrent Offers:");
        System.out.println("1. Buy 5 Tickets, Get 1 Free.");
        System.out.println("2. Buy 10 or more Tickets for a 20% Discount.");
    }

    private void bookTickets(Scanner scanner) {
        System.out.println("\nChoose a movie to book tickets for:");
        for (int i = 0; i < MOVIES.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), MOVIES[i]);
        }

        int movieChoice = scanner.nextInt() - 1;
        if (movieChoice < 0 || movieChoice >= MOVIES.length) {
            System.out.println("Invalid movie choice! Returning to main menu.");
            return;
        }

        int bookedSeats = ticketSales[movieChoice];
        int availableSeats = seats[movieChoice].length - bookedSeats;

        if (availableSeats == 0) {
            System.out.println("Sorry, this movie is house-full. Please choose another movie.");
            return;
        }

        System.out.println("\nEnter the number of tickets to book: ");
        int numTickets = scanner.nextInt();
        if (numTickets <= 0 || numTickets > availableSeats) {
            System.out.printf("Invalid number of tickets! Only %d seats are available.\n", availableSeats);
            return;
        }

        int freeTickets = 0;
        double discount = 0.0;
        if (numTickets >= 5 && numTickets < 10) {
            freeTickets = 1;
        } else if (numTickets >= 10) {
            discount = 0.2;
        }

        int totalTickets = numTickets + freeTickets;
        int totalPrice = numTickets * BASE_TICKET_PRICE;
        totalPrice -= totalPrice * discount;

        System.out.println("\nSelect facilities (optional, additional charges may apply):");
        for (int i = 0; i < FACILITIES.length; i++) {
            System.out.printf("%d. %s (+ Rs. 50)\n", (i + 1), FACILITIES[i]);
        }
        System.out.print("Enter the numbers separated by space (or 0 for no facilities): ");
        scanner.nextLine();
        String facilityChoices = scanner.nextLine();
        int facilityCharges = 0;

        if (!facilityChoices.equals("0")) {
            String[] choices = facilityChoices.split(" ");
            for (String choice : choices) {
                int facilityIndex = Integer.parseInt(choice) - 1;
                if (facilityIndex >= 0 && facilityIndex < FACILITIES.length) {
                    facilityCharges += 50;
                }
            }
        }

        totalPrice += facilityCharges;

        for (int i = bookedSeats; i < bookedSeats + numTickets; i++) {
            seats[movieChoice][i] = 1;
        }
        ticketSales[movieChoice] += numTickets;

        System.out.printf("\nBooking successful! Total Price: Rs. %d\n", totalPrice);
        System.out.printf("You booked %d tickets (including %d free tickets) for '%s'.\n",
                totalTickets, freeTickets, MOVIES[movieChoice]);

        if (discount > 0) {
            System.out.printf("You received a 20%% discount, saving Rs. %.2f!\n", numTickets * BASE_TICKET_PRICE * discount);
        }

        processPayment(scanner, totalPrice);
    }

    private void processPayment(Scanner scanner, int totalPrice) {
        Random random = new Random();
        int randomCardNumber = 1000 + random.nextInt(9000);
        int randomPassword = 1000 + random.nextInt(9000);

        System.out.println("\nPayment Options:");
        System.out.println("1. Debit Card");
        System.out.println("2. Credit Card");
        System.out.print("Choose your payment method: ");
        int paymentChoice = scanner.nextInt();

        if (paymentChoice != 1 && paymentChoice != 2) {
            System.out.println("Invalid payment choice! Returning to main menu.");
            return;
        }

        System.out.println("\nPayment Details:");
        System.out.printf("Card Number (Sample): %d\n", randomCardNumber);
        System.out.print("Enter your card number: ");
        scanner.next();

        System.out.printf("Password (Sample): %d\n", randomPassword);
        System.out.print("Enter your password: ");
        scanner.next();

        System.out.printf("\nPayment Successful! Rs. %d has been debited from your account.\n", totalPrice);
        System.out.println("Thank you for booking with the QFX Booking! Enjoy your movie!");
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
