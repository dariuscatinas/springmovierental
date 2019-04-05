package common.domain.validators;

import common.domain.exceptions.ValidatorException;

/**
 * @author rauldarius
 */

public interface Validator<T> {

    public void validate(T entity) throws ValidatorException;
}
