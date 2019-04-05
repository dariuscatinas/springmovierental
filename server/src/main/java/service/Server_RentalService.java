package service;

import common.domain.Client;
import common.domain.Movie;
import common.domain.Rental;
import common.domain.exceptions.ClientNotFound;
import common.domain.exceptions.MovieNotFound;
import common.domain.exceptions.MovieRentalException;
import common.service.RentalService;
import pagination.Page;
import pagination.PageGenerator;
import repository.PagingRepository;
import repository.Repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Server_RentalService implements RentalService {

    private Repository<Integer,Rental> rentalRepository;
    private Repository<Integer,Movie> movieRepository;
    private Repository<Long,Client> clientRepository;
    private static AtomicInteger ID=new AtomicInteger(0);
    private PageGenerator pageGenerator = new PageGenerator();

    public Server_RentalService() {
        System.out.println("empty repo rental constructor");
    }

    @Override
    public void setPageSize(int size) {
        this.pageGenerator=new PageGenerator(0,size);
    }

    public void setRentalRepository(Repository<Integer, Rental> rentalRepository) {
        this.rentalRepository = rentalRepository;
        initID();
    }

    public void setMovieRepository(Repository<Integer, Movie> movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void setClientRepository(Repository<Long, Client> clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Server_RentalService(Repository rRepo, Repository mRepo, Repository cRepo){
        rentalRepository=rRepo;
        movieRepository=mRepo;
        clientRepository=cRepo;
        System.out.println("param repo constructor");
        initID();
    }

    
    public Optional<Rental> save(long cId, int mId, LocalDate startDate,int days){
        LocalDate endDate=startDate.plusDays(days);
        Optional<Client> client=clientRepository.findOne(cId);
        client.orElseThrow(()->new ClientNotFound("Client does not exist!"));
        Optional<Movie> movie=movieRepository.findOne(mId);
        movie.orElseThrow(()->new MovieNotFound("Movie does not exist!"));
        return rentalRepository.save(new Rental(ID.incrementAndGet(),
                    cId,mId,startDate,endDate));
    }

    
    public Set<Rental> getAllRentals(){
        return StreamSupport.stream(rentalRepository.findAll().spliterator(), false).collect(Collectors.toSet());
    }

    
    public Set<Rental> getRentalsByClient(long cId){
        return StreamSupport.stream(rentalRepository.findAll().spliterator(), false).filter(r->r.getClientId()==cId).collect(Collectors.toSet());
    }

    
    public Set<Rental> getRentalByMovie(int mId){
        return StreamSupport.stream(rentalRepository.findAll().spliterator(), false).filter(r->r.getMovieId()==mId).collect(Collectors.toSet());

    }

    
    public Set<Rental> filterCustom(Predicate<? super Rental> predicate){
        return StreamSupport.stream(rentalRepository.findAll().spliterator(), false).filter(predicate).collect(Collectors.toSet());
    }

    
    public Map<Movie,Set<Rental>> getMovieRental(){

        Iterable<Movie> movies=movieRepository.findAll();
        return StreamSupport.stream(movies.spliterator(),false)
                .collect(Collectors.toMap(m->m,m->getRentalByMovie(m.getId())));
                
    }

    
    public Map<Client,Set<Rental>> getClientRental(){
        Iterable<Client> clients=clientRepository.findAll();
        return StreamSupport.stream(clients.spliterator(),false)
                .collect(Collectors.toMap(client -> client,
                        client -> getRentalsByClient(client.getId())));
    }


    public Stream<Rental> findAllPaged(){
        if(!(rentalRepository instanceof PagingRepository)){
            throw new MovieRentalException("Cannot use this functionality");
        }
        Page<Rental> rentalPage = ((PagingRepository<Integer, Rental>) rentalRepository).findAll(pageGenerator);
        pageGenerator = rentalPage.getNextPage();
        Optional.of(rentalPage.getElements().count())
                .filter(c->c==0)
                .ifPresent(c->pageGenerator=new PageGenerator());

        return rentalPage.getElements();
    }











    public void initID(){
        ID .addAndGet((int)StreamSupport.stream(rentalRepository.findAll().spliterator(),false).count());
    }
}
