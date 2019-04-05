package repository;

import common.domain.BaseEntity;
import common.domain.validators.Validator;
import common.domain.exceptions.ValidatorException;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepo<ID,T extends BaseEntity<ID>> implements Repository<ID,T> {


    protected Map<ID,T> entities;
    protected Validator<T> validator;

    public InMemoryRepo(Validator<T> validator){
        entities=new HashMap<ID,T>();
        this.validator=validator;
    }
    public Optional<T> save(T entity) throws ValidatorException{
        if(entity==null)
            throw new IllegalArgumentException("Argument cannot be null!");
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(),entity));
    }
    public Optional<T> findOne(ID id){
        if(id == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        return Optional.ofNullable(entities.get(id));
    }
    public Iterable<T> findAll(){
        return entities.values();
    }
    public Optional<T> delete(ID id){
        if(id == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }
    public Optional<T> update(T entity) throws ValidatorException{
        if(entity == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }


}
