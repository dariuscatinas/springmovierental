package common.domain.validators;
import common.domain.Client;
import common.domain.exceptions.ValidatorException;

import java.util.Optional;
import java.util.stream.Stream;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidatorException {
         Boolean b = (entity.getId()== null || entity.getId()<0 || entity.getName()==null || !(entity.getName().length()>2) ||
              entity.getEmail()==null  || !entity.getEmail().contains("@") || entity.getAge()<=0);
         Optional<Boolean> booleanOptional= Stream.of(b).filter(v -> !v).findAny();
         booleanOptional.orElseThrow(() -> new ValidatorException("Client details not valid!"));

    }
}
