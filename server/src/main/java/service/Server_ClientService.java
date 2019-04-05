package service;

import common.domain.Client;
import common.domain.exceptions.MovieRentalException;
import common.domain.exceptions.ValidatorException;
import common.service.ClientService;
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

public class Server_ClientService implements ClientService {

    private Repository<Long,Client> repository;
    private PageGenerator pageGenerator = new PageGenerator();

    public void setRepository(Repository<Long, Client> repository) {
        this.repository = repository;
    }

    public Server_ClientService() {
        System.out.println("empty client constructor");
    }

    public Server_ClientService(Repository<Long,Client> repo){

        System.out.println("repo client constructor");
        repository=repo;
    }

    
    public Optional<Client> addClient(Client client) throws ValidatorException{
       return repository.save(client);
    }

    

    public Optional<Client> deleteClient(Long id) throws ValidatorException{
        return repository.delete(id);
    }

    
    public Set<Client> getAllClients(){
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toSet());
    }

    
    public Set<Client> filterCustom(Predicate<? super Client> predicate){

        return StreamSupport.stream(repository.findAll().spliterator(), false).filter(predicate).collect(Collectors.toSet());
    }

    
    public Optional<Client> update(Client newClient){
        return repository.update(newClient);
    }

    
    public Optional<Client> findOne(long cnp){
        return repository.findOne(cnp);
    }

    public Stream<Client> findAllPaged(){
        if(!(repository instanceof PagingRepository)){
            throw new MovieRentalException("Cannot use this functionality");
        }
        Page<Client> clientPage = ((PagingRepository<Long, Client>) repository).findAll(pageGenerator);
        pageGenerator = clientPage.getNextPage();
        Optional.of(clientPage.getElements().count())
                .filter(c->c==0)
                .ifPresent(c->pageGenerator=new PageGenerator());

        return clientPage.getElements();
    }
    public void setPageSize(int size){
        pageGenerator = new PageGenerator(0, size);
    }
}
