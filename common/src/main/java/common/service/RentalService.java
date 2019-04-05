package common.service;

import common.domain.Client;
import common.domain.Movie;
import common.domain.Rental;
import common.message.utils.MockPredicate;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface RentalService {


    /**
     * Saves a new rental to the repository
     * @param cId, the ID of the client who rented the movie
     * @param mId, the ID of the movie rented
     * @param startDate, the start date of the rental
     * @param days, the number of days rented for
     * @return and {@code Optional} which is empty if the rental was added
     * or contains the already existing rental
     */
    public Optional<Rental> save(long cId, int mId, LocalDate startDate, int days);
    /**
     * Provides all rentals
     * @return a {@code Set} containing all rentals in the repository
     */
    public Set<Rental> getAllRentals();
    /**
     * Provides all rentals of a given client
     * @param cId, the ID of the client
     * @return a {@code Set} containing the rentals for the given client
     */
    public Set<Rental> getRentalsByClient(long cId);

    public void setPageSize(int size);

    /**
     * Provides all rentals of a given client
     * @param mId, the ID of the movie
     * @return a {@code Set} containing the rentals for the given movie
     */
    public Set<Rental> getRentalByMovie(int mId);

    /**
     * Filters rentals based on a predicate
     * @param predicate, the filtering method to apply
     * @return a {@code Set} containing the rentals which respect the given predicate
     */
    public Set<Rental> filterCustom(Predicate<? super Rental> predicate);
    /**
     * Gets a statistics map from every movie to its rentals
     * @return an {@code Map} mapping from every Movie its set of rentals
     */
    public Map<Movie,Set<Rental>> getMovieRental();

    /**
     * Gets a statistics map from every client to its rentals
     * @return an {@code Map} mapping from every Client its set of rentals
     */
    public Map<Client,Set<Rental>> getClientRental();

    public Stream<Rental> findAllPaged();

}
