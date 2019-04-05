package repository;

import common.domain.BaseEntity;
import pagination.Page;
import pagination.PageGenerator;

public interface PagingRepository<ID, T extends BaseEntity<ID>> extends Repository<ID, T>{

        Page<T> findAll(PageGenerator pageGenerator);
}
