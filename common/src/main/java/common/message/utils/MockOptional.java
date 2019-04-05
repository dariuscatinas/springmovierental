package common.message.utils;

import javax.swing.text.html.Option;
import java.io.Serializable;
import java.util.Optional;

public class MockOptional< T  extends Serializable> implements Serializable {
    private T entity = null;
    private boolean exists = false;

    public MockOptional(Optional<T> opt){
        if(opt.isPresent()){
            exists = true;
            entity = opt.get();
        }
    }
    public Optional<T> toOptional(){
        return Optional.ofNullable(entity);
    }

}
