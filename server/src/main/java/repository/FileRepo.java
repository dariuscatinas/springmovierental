package repository;

import common.domain.BaseEntity;
import common.domain.exceptions.MovieRentalException;
import common.domain.validators.Validator;


import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FileRepo<ID, T extends BaseEntity<ID>> extends InMemoryRepo<ID, T> {

    private Function< String, T> factory;
    private String fileName;
    private Function<T, String> stringFunction;

    public FileRepo(Validator<T> validator, String fileName, Function<String, T> factory, Function <T, String> stringFunction){
        super(validator);
        this.fileName = fileName;
        this.factory = factory;
        this.stringFunction = stringFunction;
        readFile();
    }

    private void readFile() {

        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach( line -> {
                T entity = factory.apply(line);
                Optional<T> opt = Optional.ofNullable(entity);
                opt.ifPresent(e -> entities.put(entity.getId(), entity));
            });
        }
        catch(IOException ex){
            throw new MovieRentalException("Cannot read from file");
        }
    }
    private void saveFile(){
        Path path = Paths.get(fileName);
        StringBuilder stringBuilder = new StringBuilder();
        try{
            entities.forEach( (id,entity) -> {
                String repr = stringFunction.apply(entity) + "\n";
                stringBuilder.append(repr);
            });
            Files.write(path, stringBuilder.toString().getBytes(), StandardOpenOption.WRITE);
        }
        catch (IOException ex){
            throw new MovieRentalException("Cannot write to file");
        }
    }

    @Override
    public Optional<T> save(T entity){
        Optional<T> superValue = super.save(entity);
        saveFile();
        return superValue;
    }

    @Override
    public Optional<T> delete(ID id){
        Optional<T> superValue = super.delete(id);
        saveFile();
        return superValue;
    }
    @Override
    public Optional<T> update(T entity){
        Optional<T> superValue = super.update(entity);
        saveFile();
        return superValue;
    }
}
