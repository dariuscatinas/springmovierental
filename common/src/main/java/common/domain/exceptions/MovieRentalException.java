package common.domain.exceptions;

/**
 * @author rauldarius
 */

public class MovieRentalException extends RuntimeException {

    public MovieRentalException(String message) {
        super(message);
    }
}
