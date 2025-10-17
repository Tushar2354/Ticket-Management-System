import java.util.*;

class Route {
    private int routeId;
    private String source;
    private String destination;
    private double price;

    public Route(int routeId, String source, String destination, double price) {
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
        this.price = price;
    }

    public int getRouteId() { return routeId; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getPrice() { return price; }

    public void displayRoute() {
        System.out.println(routeId + ". " + source + " -> " + destination + " | Rs. " + price);
    }
}

class Ticket {
    private static int idCounter = 1000;
    private int ticketId;
    private Route route;
    private String passengerName;

    public Ticket(Route route, String passengerName) {
        this.route = route;
        this.passengerName = passengerName;
        this.ticketId = idCounter++;
    }

    public int getTicketId() { return ticketId; }

    public void displayTicket() {
        System.out.println("Ticket ID: " + ticketId +
                " | Passenger: " + passengerName +
                " | Route: " + route.getSource() + " -> " + route.getDestination() +
                " | Price: Rs. " + route.getPrice());
    }
}

class User {
    private String username;
    private String password;
    private List<Ticket> bookedTickets = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void bookTicket(Ticket ticket) {
        bookedTickets.add(ticket);
        System.out.println("✅ Ticket booked successfully!");
    }

    public void cancelTicket(int ticketId) {
        Ticket found = null;
        for (Ticket t : bookedTickets) {
            if (t.getTicketId() == ticketId) {
                found = t;
                break;
            }
        }
        if (found != null) {
            bookedTickets.remove(found);
            System.out.println("❌ Ticket cancelled successfully!");
        } else {
            System.out.println("⚠ Ticket not found!");
        }
    }

    public void viewBookings() {
        if (bookedTickets.isEmpty()) {
            System.out.println("No tickets booked yet!");
            return;
        }
        for (Ticket t : bookedTickets) {
            t.displayTicket();
        }
    }
}

public class TicketManagementSystem {
    private static Scanner sc = new Scanner(System.in);
    private static List<User> users = new ArrayList<>();
    private static List<Route> routes = new ArrayList<>();

    public static void main(String[] args) {
        seedRoutes();
        mainMenu();
    }

    private static void seedRoutes() {
        routes.add(new Route(1, "Pune", "Mumbai", 450));
        routes.add(new Route(2, "Pune", "Nashik", 600));
        routes.add(new Route(3, "Mumbai", "Nagpur", 1500));
        routes.add(new Route(4, "Pune", "Kolhapur", 700));
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n====== Ticket Management System ======");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1: registerUser(); break;
                case 2: loginUser(); break;
                case 3: adminLogin(); break;
                case 4: System.out.println("Thank you!"); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String user = sc.nextLine();
        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(user)) {
                System.out.println("User already exists!");
                return;
            }
        }
        users.add(new User(user, pass));
        System.out.println("✅ Registration successful!");
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String user = sc.nextLine();
        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        User found = null;
        for (User u : users) {
            if (u.getUsername().equals(user) && u.getPassword().equals(pass)) {
                found = u;
                break;
            }
        }

        if (found == null) {
            System.out.println("⚠ Invalid login!");
            return;
        }
        userMenu(found);
    }

    private static void userMenu(User user) {
        while (true) {
            System.out.println("\n===== Welcome, " + user.getUsername() + " =====");
            System.out.println("1. View Routes");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. View My Bookings");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1: displayRoutes(); break;
                case 2: bookTicket(user); break;
                case 3: cancelTicket(user); break;
                case 4: user.viewBookings(); break;
                case 5: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void bookTicket(User user) {
        displayRoutes();
        System.out.print("Enter Route ID to book: ");
        int id = sc.nextInt();
        sc.nextLine();

        Route selected = null;
        for (Route r : routes) {
            if (r.getRouteId() == id) selected = r;
        }

        if (selected == null) {
            System.out.println("⚠ Invalid Route ID!");
            return;
        }

        System.out.print("Enter passenger name: ");
        String name = sc.nextLine();
        Ticket t = new Ticket(selected, name);
        user.bookTicket(t);
    }

    private static void cancelTicket(User user) {
        System.out.print("Enter Ticket ID to cancel: ");
        int id = sc.nextInt();
        user.cancelTicket(id);
    }

    private static void adminLogin() {
        System.out.print("Enter admin username: ");
        String u = sc.nextLine();
        System.out.print("Enter admin password: ");
        String p = sc.nextLine();

        if (u.equals("admin") && p.equals("1234")) {
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials!");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. View Routes");
            System.out.println("2. Add Route");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1: displayRoutes(); break;
                case 2: addRoute(); break;
                case 3: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void addRoute() {
        System.out.print("Enter Route ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Source: ");
        String source = sc.nextLine();
        System.out.print("Enter Destination: ");
        String dest = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();

        routes.add(new Route(id, source, dest, price));
        System.out.println("✅ Route added successfully!");
    }

    private static void displayRoutes() {
        System.out.println("\nAvailable Routes:");
        for (Route r : routes) r.displayRoute();
    }
}