package common.service;

import common.domain.Client;
import common.domain.exceptions.ValidatorException;
import common.message.utils.MockPredicate;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ClientService {

    /**
     * Adds a {@code Client} to the repository
     * @param client, the Client to add to the repository
     * @throws ValidatorException, if the client is not valid
     */
    public Optional<Client> addClient(Client client) throws ValidatorException;

    /**
     * Deletes a {@code Client} from the repository
     * @param id, representing the identification of a Client
     * @return true if the Client was found and deleted, false otherwise
     * @throws ValidatorException .
     */

    public Optional<Client> deleteClient(Long id) throws ValidatorException;

    /**
     * @return an {@code Set<Client>} of the entire collection of Clients.
     */
    public Set<Client> getAllClients();

    /**
     * filter the clients based on a predice
     * @param predicate a {@code Predicate} predicate which takes in a Client and returns true if the Client matches the conditions
     * @return A set of clients, which match the predicate
     */
    public Set<Client> filterCustom(Predicate<? super Client> predicate);

    /**
     * Updates an existing client
     * @param newClient, the client with the new information of the client
     * @return an {@code Optional} which contains the previous movie information
     */
    public Optional<Client> update(Client newClient);

    /**
     * Retrieves a client by its id
     * @param cnp, the clients id
     * @return an {@code Optional} containing the client with the given id or null if it does not exist
     */
    public Optional<Client> findOne(long cnp);

    public List<Client> findAllPaged();

    void setPageSize(int size);
}
