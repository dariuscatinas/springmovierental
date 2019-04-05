package common.domain;

import java.io.Serializable;

/**
 * @author rauldarius
 * An abstract class representing the one of the bussiness entities of the common.domain.
 */

public abstract class BaseEntity<ID> implements Serializable {

    private ID id;

    public ID getId(){
        return id;
    }

    public void setId(ID newId){
        id=newId;
    }

}
