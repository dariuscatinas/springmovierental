package repository;

import common.domain.BaseEntity;
import common.domain.Client;
import common.domain.exceptions.ValidatorException;
import common.domain.validators.ClientValidator;
import common.domain.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import pagination.Page;
import pagination.PageGenerator;
import repository.PagingRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DBSpring<ID,T extends BaseEntity<ID>> implements PagingRepository<ID, T> {
    @Autowired
    private JdbcOperations jdbcOperations;


    private Function<JdbcOperations,Iterable<T>> findAll;
    private BiFunction<JdbcOperations,ID,Optional<T>> findOne;
    private BiFunction<JdbcOperations,T,Optional<T>> save;
    private BiFunction<JdbcOperations,ID,Optional<T>> delete;
    private BiFunction<JdbcOperations,T,Optional<T>> update;
    private Validator<T> validator;


    public DBSpring(Function<JdbcOperations, Iterable<T>> findAll,
                    BiFunction<JdbcOperations, ID, Optional<T>> findOne,
                    BiFunction<JdbcOperations, T, Optional<T>> save,
                    BiFunction<JdbcOperations, ID, Optional<T>> delete,
                    BiFunction<JdbcOperations, T, Optional<T>> update,
                    Validator<T> validator) {
        this.findAll = findAll;
        this.findOne = findOne;
        this.save = save;
        this.delete = delete;
        this.update = update;
        this.validator = validator;
    }

    @Override
    public Page<T> findAll(PageGenerator pageGenerator) {
        int baseL = pageGenerator.getCurrentPage()* pageGenerator.getPageSize();
        PageGenerator next = new PageGenerator(pageGenerator.getCurrentPage() + 1, pageGenerator.getPageSize());
        List<T> entities = StreamSupport.stream(findAll().spliterator(), false).skip(baseL).limit(pageGenerator.getPageSize()).collect(Collectors.toList());
        return new Page<>(entities, next);
    }

    @Override
    public Optional<T> findOne(ID aLong) {
        return findOne.apply(jdbcOperations,aLong);
    }


    @Override
    public Iterable<T> findAll() {
        return findAll.apply(jdbcOperations);
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        validator.validate(entity);
        return save.apply(jdbcOperations,entity);
    }

    @Override
    public Optional<T> delete(ID aLong) {
        return delete.apply(jdbcOperations,aLong);
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        validator.validate(entity);
        return update.apply(jdbcOperations,entity);
    }
}
