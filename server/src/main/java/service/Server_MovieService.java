package service;

import common.domain.Movie;
import common.domain.exceptions.MovieRentalException;
import common.domain.exceptions.ValidatorException;
import common.service.MovieService;
import pagination.Page;
import pagination.PageGenerator;
import repository.PagingRepository;
import repository.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Server_MovieService implements MovieService {
    private Repository<Integer, Movie> repo;
    private PageGenerator pageGenerator = new PageGenerator();

    public void setRepo(Repository<Integer, Movie> repo) {
        this.repo = repo;
    }

    public Server_MovieService() {
    }

    public Server_MovieService(Repository<Integer, Movie> repository){
        repo = repository;
    }

    
    public Optional<Movie> addMovie(Movie movie) throws ValidatorException {
        return repo.save(movie);
    }

    
    public Optional<Movie> deleteMovie(int ID) throws ValidatorException{
        return repo.delete(ID);
    }

    
    public Set<Movie> getAllMovies(){
        return StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toSet());
    }
    
    public Set<Movie> filterCustom(Predicate<? super Movie> predicate){

        return StreamSupport.stream(repo.findAll().spliterator(), false).filter(predicate).collect(Collectors.toSet());
    }

    
    public Optional<Movie> update(Movie newMovie){
        return repo.update(newMovie);
    }

    
    public Optional<Movie> findOne(int id){
        return repo.findOne(id);
    }

    public Stream<Movie> findAllPaged(){
        if(!(repo instanceof PagingRepository)){
            throw new MovieRentalException("Cannot use this functionality");
        }
        Page<Movie> moviePage = ((PagingRepository<Integer, Movie>) repo).findAll(pageGenerator);
        pageGenerator = moviePage.getNextPage();
        Optional.of(moviePage.getElements().count())
                .filter(c->c==0)
                .ifPresent(c->pageGenerator=new PageGenerator());

        return moviePage.getElements();
    }

    @Override
    public void setPageSize(int size) {
        this.pageGenerator=new PageGenerator(0,size);
    }
}
