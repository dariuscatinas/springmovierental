package ui;

import common.domain.Client;
import common.domain.Movie;
import common.domain.Rental;
import common.domain.exceptions.*;
import common.message.utils.MockPredicate;
import common.service.ClientService;
import common.service.MovieService;
import common.service.RentalService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Stream;
import static common.domain.FilterPredicates.*;
import static java.lang.Thread.sleep;

public class Console {
    private MovieService movieService;
    private ClientService clientService;
    private RentalService rentalService;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Console(MovieService movieService, ClientService clientService, RentalService rentalService) {
        this.movieService = movieService;
        this.clientService = clientService;
        this.rentalService = rentalService;

    }

    public Console() {

    }

    public void setSerivces(MovieService movieService,ClientService clientService,RentalService rentalService){
        this.movieService=movieService;
        this.clientService=clientService;
        this.rentalService=rentalService;
    }

    private void printCommands() {
        System.out.println("0.Exit");
        System.out.println("1.View all movies");
        System.out.println("2.Add a movie");
        System.out.println("3.Remove a movie");
        System.out.println("4.View all clients");
        System.out.println("5.Add a client");
        System.out.println("6.Remove a client");
        System.out.println("7.View all rentals");
        System.out.println("8.Add a rental");
        System.out.println("9.Filter movies by rating");
        System.out.println("10.Filter movies by genre");
        System.out.println("11.Filter clients by age");
        System.out.println("12.Filter rentals by duration");
        System.out.println("13.Filter rentals by client ID");
        System.out.println("14.Filter rental by movie ID");
        System.out.println("15.Update movie");
        System.out.println("16.Update client");
        System.out.println("17.Client statistics");
        System.out.println("18.Movie statistics");
        System.out.println("19.Top 3 Movies");
        System.out.println("20.Top 3 Clients");
        System.out.println("21. View movies paged");
        System.out.println("22. View clients paged");
        System.out.println("23. View Rentals paged");
        System.out.println("24. SetClientPageSize");
        System.out.println("25. Flood movies");
        System.out.println("26. Flood clients");
    }

    public void run() {
        String cmd;
        while (true) {
            try {
                printCommands();
                System.out.print("Enter your command:");
                cmd = reader.readLine();
                switch (cmd) {
                    case "0":
                        System.exit(0);
                    case "1":
                        listAllMovies();
                        break;
                    case "2":
                        addMovie();
                        break;
                    case "3":
                        deleteMovie();
                        break;
                    case "4":
                        listAllClients();
                        break;
                    case "5":
                        addClient();
                        break;
                    case "6":
                        removeClient();
                        break;
                    case "7":
                        listAllRentals();
                        break;
                    case "8":
                        addRental();
                        break;
                    case "9":
                        filterMoviesRating();
                        break;
                    case "10":
                        filterMoviesGenre();
                        break;
                    case "11":
                        filterClientsAge();
                        break;
                    case "12":
                        filterRentalDuration();
                        break;
                    case "13":
                        filterRentalClient();
                        break;
                    case "14":
                        filterRentalMovie();
                        break;
                    case "15":
                        updateMovie();
                        break;
                    case "16":
                        updateClient();
                        break;
                    case "17":
                        clientStatistics();
                        break;
                    case "18":
                        movieStatistics();
                        break;
                    case "19":
                        bestMovies();
                        break;
                    case "20":
                        bestClients();
                        break;
                    case "21":
                        listAllMoviesPaged();
                        break;
                    case "22":
                        listAllClientsPaged();
                        break;
                    case "23":
                        listAllRentalsPaged();
                        break;
                    case "24":
                        setClientPageSize();
                        break;
                    default:
                        System.out.println("Invalid command");

                }
            } catch (ValidatorException ex) {
                ex.printStackTrace();
            } catch (ClientNotFound | MovieNotFound | RentalNotAdded cnf) {
                System.out.println(cnf.getMessage());
            }catch (IOException ioe) {
                System.out.println("IOException");
                ioe.printStackTrace();
            }
            catch (Exception ex) {
                System.out.println("An unknown exception has occured");
                ex.printStackTrace();
            }
        }
    }


    private void setClientPageSize() {
        int pageSize=readInt("Enter the page size:");
        CompletableFuture.runAsync(()-> {
            clientService.setPageSize(pageSize);
        });
    }

    private void bestMovies() {
        CompletableFuture.runAsync(()-> {
            rentalService.getMovieRental().entrySet().stream()
                    .sorted(Comparator.comparingInt(o -> o.getValue().size() * (-1))).limit(3)
                    .forEach((c) -> System.out.println(c.getKey().getTitle() + " - " + c.getValue().size()));
        });
    }

    private void bestClients(){

        CompletableFuture.runAsync(()-> {

            rentalService.getClientRental().entrySet().stream().sorted(Comparator.comparingInt(o -> o.getValue().size() * (-1))).limit(3)
                    .forEach((c) -> System.out.println(c.getKey().getName() + " - " + c.getValue().size()));
        });
    }

    private void updateMovie() throws IOException{

        int id=readInt("Give movie ID:");
        System.out.println("Enter the new title:");
        String title=reader.readLine();
        System.out.println("Enter the new Genre:");
        String genre=reader.readLine();
        float newRating=readFloat("Enter the new rating:");
        CompletableFuture.runAsync(()->{
            Optional<Movie> mo=movieService.update(new Movie(id,title,genre,newRating));
            mo.ifPresent(mi->System.out.println("Movie updated"));
            mo.orElseThrow(()->new MovieNotFound("Movie not found"));

        });
    }

    private void updateClient() throws IOException{
        long id=readInt("Give client ID:");
        System.out.println("Enter the new name:");
        String name=reader.readLine();
        System.out.println("Enter the new email:");
        String email=reader.readLine();
        int newAge=readInt("Enter the new age:");
        CompletableFuture.runAsync(()->{
            Optional<Client> mo=clientService.update(new Client(id,name,email,newAge));
            mo.ifPresent(mi->System.out.println("Client updated"));
            mo.orElseThrow(()->new ClientNotFound("Client not found"));
        });
    }

    private void clientStatistics(){

      CompletableFuture.runAsync(()->{
          System.out.println("Client name - Number of rentals");
          rentalService.getClientRental().forEach((c,s)->System.out.println(c.getName()+" - "+s.size()));
      });
    }

    private  void movieStatistics(){
        CompletableFuture.runAsync(()->{
            System.out.println("Movie name - Number of rentals");
            rentalService.getMovieRental().forEach((c,s)->System.out.println(c.getTitle()+" - "+s.size()));
        });
    }

    private Client readClient() throws IOException {
        String name, email;
        int age;
        long cnp = readLong("Give client CNP:");
        System.out.print("Give client name:");
        name = reader.readLine();
        System.out.print("Give client email:");
        email = reader.readLine();
        age = readInt("Give client age: ");
        return new Client(cnp, name, email, age);

    }

    private Movie readMovie() throws IOException {

        String title, genre;
        float rating;
        int ID = readInt("Give internal movie ID:");
        System.out.println("Give title: ");
        title = reader.readLine();
        System.out.println("Give genre: ");
        genre = reader.readLine();
        rating = readFloat("Give rating: ");

        return new Movie(ID, title, genre, rating);
    }

    private void addMovie() throws IOException{

        Movie m = readMovie();

        CompletableFuture.runAsync(()->{
            try {
                Optional<Movie> mo = movieService.addMovie(m);
                mo.ifPresent(mi -> System.out.println("Movie already exists"));
                mo.orElseThrow(() -> new MovieAdded("Movie added"));
            } catch (MovieAdded ma) {
                System.out.println(ma.getMessage());
            }
        });

    }

    private void listAllMovies() {
        CompletableFuture.runAsync(() -> {
            movieService.getAllMovies().forEach(m -> System.out.println(m.toString()));
        });
    }

    private void listAllMoviesPaged(){

        CompletableFuture.runAsync(() -> {
            movieService.findAllPaged().forEach(System.out::println);
        });

    }

    private void listAllClientsPaged(){

        CompletableFuture.runAsync(()->{
            clientService.findAllPaged().forEach(System.out::println);
        });
    }

    private void listAllRentalsPaged(){

        CompletableFuture.runAsync(()->{
            rentalService.findAllPaged().forEach(System.out::println);
        });
    }

    private void deleteMovie() {
        int ID = readInt("Give movie ID:");

        CompletableFuture.runAsync(()->{
            try {
                Optional<Movie> mo=movieService.deleteMovie(ID);
                mo.ifPresent(m -> System.out.println("Movie deleted"));
                mo.orElseThrow(() -> new MovieNotFound("Movie not found, nothing deleted"));
            }catch (MovieNotFound mnf){
                System.out.println(mnf.getMessage());
            }
        });

    }

    private void addClient() throws IOException{
        Client c = readClient();

        CompletableFuture.runAsync(()->{
            try {
                Optional<Client> mo=clientService.addClient(c);
                mo.ifPresent(cl -> System.out.println("Client already exists"));
                mo.orElseThrow(() -> new ClientAdded("Client added"));
            } catch (ClientAdded ca){
                System.out.println(ca.getMessage());
            }
        });
    }

    private void listAllClients() {
        CompletableFuture.runAsync(()-> {
            clientService.getAllClients().forEach(c -> System.out.println(c + "\n"));
        });
    }

    private void removeClient() {
        long cnp;
        boolean ok = false;
        while (!ok) try {
            System.out.print("Give client CNP:");
            cnp = Long.parseLong(reader.readLine());
            final long cnp2=cnp;
            CompletableFuture.runAsync(() -> {
                try {
                    Optional<Client> mo = clientService.deleteClient(cnp2);
                    mo.ifPresent(c -> System.out.println("Client deleted"));
                    mo.orElseThrow(() -> new ClientNotFound("Client not found, nothing deleted"));
                } catch (ClientNotFound cnf){
                    System.out.println(cnf.getMessage());
                }
            });
            ok = true;
        } catch (NumberFormatException | IOException ex) {
            System.out.println("CNP format not correct");
        }
    }

    private void listAllRentals() {
        CompletableFuture.runAsync(()->{
            rentalService.getAllRentals().forEach(System.out::println);
        });
    }

    private void addRental() {
        LocalDate startDate = LocalDate.now();
        int mID, days, dateDay, dateMonth, dateYear;
        long cnp;
        mID = readInt("Give movie id:");
        cnp = readLong("Give CNP:");
        days = readInt("Give duration:");
        boolean ok = false;
        while (!ok) {
            try {
                dateDay = readInt("Give day of beginning of rental:");
                dateMonth = readInt("Give month of beginning of rental:");
                dateYear = readInt("Give year of beginning of rental: ");
                startDate = LocalDate.of(dateYear, dateMonth, dateDay);
                ok = true;
            } catch (DateTimeException ex) {
                System.out.println("Invalid date. Try again.");
            }
        }
        final LocalDate startDate2=startDate;
        CompletableFuture.runAsync(()->{
            Optional<Rental> mo=rentalService.save(cnp, mID, startDate2, days);
            if (mo.isPresent()) {
                System.out.println("Rental already present.");
            } else {
                System.out.println("Rental added successfully.");
            }
        });
    }

    private int readInt(String message) {
        System.out.println(message);
        int returned = -1;
        try {
            returned = Integer.parseInt(reader.readLine());
        } catch (IOException ioex) {
            System.out.println("I/O exception");
            ioex.printStackTrace();
        } catch (NumberFormatException nex) {
            System.out.println("Invalid integer format.");
            return readInt(message);
        }
        return returned;
    }

    private float readFloat(String message) {
        System.out.println(message);
        float returned = -1;
        try {
            returned = Float.parseFloat(reader.readLine());
        } catch (IOException ioex) {
            System.out.println("I/O exception");
            ioex.printStackTrace();
        } catch (NumberFormatException nex) {
            System.out.println("Invalid float format.");
            return readInt(message);
        }
        return returned;
    }

    private long readLong(String message) {
        System.out.println(message);
        long returned = -1;
        try {
            returned = Long.parseLong(reader.readLine());
        } catch (IOException ioex) {
            System.out.println("I/O exception");
            ioex.printStackTrace();
        } catch (NumberFormatException nex) {
            System.out.println("Invalid long format.");
            return readInt(message);
        }
        return returned;
    }

    private void filterMoviesRating() {
        float rating = readFloat("Give rating(>):");

        CompletableFuture.runAsync(()->{
            Set<Movie> mo=movieService.filterCustom(filterMR(rating));
            Optional.of(mo.size()).filter(s->s==0).ifPresent(x->System.out.println("No movies above that rating"));
            mo.forEach(System.out::println);
        });
    }

    private void filterMoviesGenre() throws IOException {
        System.out.println("Give genre: ");
        String genre = reader.readLine();

        CompletableFuture.runAsync(()->{
            Set<Movie> mo=movieService.filterCustom(filterMG(genre));
            Optional<Integer> o=Optional.of(mo.size()).filter(s->s==0);
            o.ifPresent(x->System.out.println("No movies with that genre"));
            mo.forEach(System.out::println);
        });
    }

    private void filterClientsAge() throws IOException {
        int age = readInt("Give age:");
        System.out.println("Specify if you want clients younger or older than the age(>, <):");
        String sign = reader.readLine();

        CompletableFuture.runAsync(()->{
            Set<Client> c=clientService.filterCustom(filterCA(age,sign));
            Optional.of(c.size()).filter(s->s==0).ifPresent(x->System.out.println("No clients above/below that age"));
            c.forEach(ce -> System.out.println(ce + "\n"));
        });
    }

    private void filterRentalDuration() throws IOException {
        int duration = readInt("Give duration:");
        System.out.println("Specify if you want rentals' with less or more than the given duration(>, <):");
        String sign = reader.readLine();
        CompletableFuture.runAsync(()->{
            Set<Rental> re=rentalService.filterCustom(filterRD(duration,sign));
            Optional.of(re.size()).filter(s->s==0).ifPresent(x->System.out.println("No rentals above/below that duration"));
            re.forEach(r -> System.out.println(r + "\n"));
        });
    }
    private void filterRentalClient(){
        long cID = readLong("Give client CNP:");

        CompletableFuture.runAsync(()->{
            Set<Rental> re=rentalService.filterCustom(filterRC(cID));
            Optional.of(re.size()).filter(s->s==0).ifPresent(x->System.out.println("No rentals for that client"));
            re.forEach(c -> System.out.println(c + "\n"));
        });
    }
    private void filterRentalMovie(){
        int mID = readInt("Give movie ID: ");
        CompletableFuture.runAsync(()->{
            Set<Rental> re=rentalService.filterCustom(filterRM(mID));
            Optional.of(re.size()).filter(s->s==0).ifPresent(x->System.out.println("No rentals for that movie"));
            re.forEach(System.out::println);
        });
    }
}

