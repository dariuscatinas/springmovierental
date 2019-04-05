package common.domain.validators;

import common.domain.Rental;
import common.domain.exceptions.ValidatorException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

public class RentalValidator implements Validator<Rental> {

    public void validate(Rental rental) throws ValidatorException {

        LocalDate start = rental.getStartDate();
        LocalDate end = rental.getEndDate();
        Boolean b = start.isAfter(end);
        Optional<Boolean> booleanOptional= Stream.of(b).filter(v -> !v).findAny();
        booleanOptional.orElseThrow(() -> new ValidatorException("Rental details not valid!"));

    }
}
