package common.domain;


import java.time.LocalDate;
import java.time.Period;

public class Rental extends BaseEntity<Integer> {

    private long clientId;
    private int movieId;
    private LocalDate startDate;
    private LocalDate endDate;
    private static final int BASEPRICE=10;

    public Rental(int ID,long cId,int mId,LocalDate startDate, LocalDate endDate){
        setId(ID);
        clientId=cId;
        movieId=mId;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public Rental(long clientId, int movieId, LocalDate startDate, LocalDate endDate) {
        this.clientId = clientId;
        this.movieId = movieId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Rental() {
    }

    public long getClientId() {
        return clientId;
    }

    public int getMovieId() {
        return movieId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getPrice(){
        return BASEPRICE* Period.between(startDate,endDate).getDays();
    }

    public int getDuration(){
        return Period.between(startDate,endDate).getDays();
    }

    @Override
    public String toString(){
        return "Rental:"+getId()+" of client:"+clientId+" for movie:"+movieId+'\n'
                +"From:"+startDate+" to:"+endDate;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Rental)
        {
            Rental temp=(Rental)obj;
            if(temp.getClientId()==clientId && temp.getMovieId()==movieId &&
            temp.getStartDate()==startDate && temp.getEndDate()==endDate)
                return true;
        }
        return false;
    }
}
