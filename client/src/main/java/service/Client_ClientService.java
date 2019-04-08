package service;
import common.domain.Client;
import common.domain.exceptions.ValidatorException;
import common.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class Client_ClientService implements ClientService{

    @Autowired
    ClientService clientService;

    @Override
    public Optional<Client> addClient(Client client) throws ValidatorException {
        return clientService.addClient(client);
    }

    @Override
    public Optional<Client> deleteClient(Long id) throws ValidatorException {
        return clientService.deleteClient(id);
    }

    @Override
    public Set<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @Override
    public Set<Client> filterCustom(Predicate<? super Client> predicate) {
        return clientService.filterCustom(predicate);
    }

    @Override
    public Optional<Client> update(Client newClient) {
        return clientService.update(newClient);
    }

    @Override
    public Optional<Client> findOne(long cnp) {
        return clientService.findOne(cnp);
    }

    @Override
    public List<Client> findAllPaged() {
        return clientService.findAllPaged();
    }

    @Override
    public void setPageSize(int size) {
        clientService.setPageSize(size);
    }
}
