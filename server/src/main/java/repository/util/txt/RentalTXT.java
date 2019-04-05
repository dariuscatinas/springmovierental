package repository.util.txt;

import common.domain.Rental;

import java.time.LocalDate;

public class RentalTXT {

    private static String fromLocalDate(LocalDate l){
        return l.getYear() + "," + l.getMonthValue() + "," + l.getDayOfMonth();
    }

    private static LocalDate parseLocalDate(String yearS, String monthS, String dayS){
        int year = -1, month = -1, day = -1;
        try{
            year = Integer.parseInt(yearS);
            month = Integer.parseInt(monthS);
            day = Integer.parseInt(dayS);
        }
        catch(NumberFormatException nex){
            return null;
        }
        return LocalDate.of(year, month, day);
    }

    public static String fromRental(Rental r){
        return r.getId() + "," + r.getClientId() + "," + r.getMovieId() + "," + fromLocalDate(r.getStartDate()) + "," + fromLocalDate(r.getEndDate());
    }
    public static Rental rentalFromLine(String line){
        String[] tokens = line.split(",");
        int ID ;
        long cId;
        int mId ;
        LocalDate startDate;
        LocalDate endDate;
        try{
            ID = Integer.parseInt(tokens[0]);
            cId = Integer.parseInt(tokens[1]);
            mId = Integer.parseInt(tokens[2]);
            startDate = parseLocalDate(tokens[3], tokens[4], tokens[5]);
            endDate = parseLocalDate(tokens[6], tokens[7], tokens[8]);
        }
        catch(NumberFormatException nex){
            return null;
        }
        return new Rental(ID, cId, mId, startDate, endDate);
    }
}
