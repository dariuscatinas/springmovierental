package common.domain;

public class Client extends BaseEntity<Long> {

    private String name;
    private String email;
    private int age;

    public Client(Long cnp, String name, String email, int age){
        setId(cnp);
        this.name=name;
        this.email=email;
        this.age=age;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public int getAge(){
        return age;
    }

    public void setName(String name){
        this.name=name;
    }

    public Client() {
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setAge(int age){
        this.age=age;
    }

    /**
     * Provides the string represation of a "Client"
     * @return a String, which is the description of a "Client"
     */
    @Override
    public String toString(){
        return "Client CNP:"+getId()+"\nClient name:"+name+"\nClient email:"+email+"\nClient age:"+age;
    }

    /**
     * Identifies if two clients are equal
     * @param obj an {@code Object} which should represent a movie
     * @return a boolean, true if obj is a Client and it's id, name, age and email are equal
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Client){
            Client temp=(Client)obj;
            if(temp.getId()==this.getId() && temp.getAge()==this.getAge() &&
                    temp.getEmail()==this.getEmail() && temp.getName()==this.getName())
                return true;
        }
        return false;
    }
}
