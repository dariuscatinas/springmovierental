package service;
import common.domain.Client;
import common.domain.Movie;
import common.domain.Rental;
import common.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Client_RentalService implements RentalService{
    
    @Autowired
    RentalService rentalService;

    @Override
    public Optional<Rental> save(long cId, int mId, LocalDate startDate, int days) {
        return rentalService.save(cId,mId,startDate,days);
    }

    @Override
    public Set<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @Override
    public Set<Rental> getRentalsByClient(long cId) {
        return rentalService.getRentalsByClient(cId);
    }

    @Override
    public void setPageSize(int size) {
        rentalService.setPageSize(size);
    }

    @Override
    public Set<Rental> getRentalByMovie(int mId) {
        return rentalService.getRentalByMovie(mId);
    }

    @Override
    public Set<Rental> filterCustom(Predicate<? super Rental> predicate) {
        return rentalService.filterCustom(predicate);
    }

    @Override
    public Map<Movie, Set<Rental>> getMovieRental() {
        return rentalService.getMovieRental();
    }

    @Override
    public Map<Client, Set<Rental>> getClientRental() {
        return rentalService.getClientRental();
    }

    @Override
    public Stream<Rental> findAllPaged() {
        return rentalService.findAllPaged();
    }
}
