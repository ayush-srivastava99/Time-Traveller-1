/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package railway;

import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author abhi
 */
public class BookTicket {
    /*
        this class is basically collecting necessary information for booking ticket for a user
        also this is the class used for invoking method which will update the database if the 
        ticket booking is successful
    */
    
//    public static boolean bookingStatus;
    private boolean bookingStatus;
    private int trainNo;
    private CachedRowSet crs;
    //variables for booking
    private String dateForBooking,classOfBooking,prefferedBerth,fromStation,toStation;
    
    java.time.LocalDate textFieldAsDate;
    
    //variables for inserting into db if booking is successful
    
    private ArrayList<String> passengerRecord = new ArrayList<String>();
            
            
    BookTicket(){
        trainNo = Integer.parseInt(Railway.clientData.get(0));
        dateForBooking = Railway.clientData.get(1);
        classOfBooking = Railway.clientData.get(2);
        prefferedBerth = Railway.clientData.get(3);
        fromStation = Railway.clientData.get(8);
        toStation = Railway.clientData.get(9);
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        textFieldAsDate = java.time.LocalDate.parse(dateForBooking, formatter);

        
    }
    public boolean bookTicketCaller(){
        BookingTicket bookingTicket = new BookingTicket(trainNo,textFieldAsDate,classOfBooking,prefferedBerth,fromStation,toStation);
        int age = Integer.valueOf(Railway.clientData.get(7));
        String gender = Railway.clientData.get(6);
        bookingStatus = bookingTicket.bookingTicketMethod(fromStation, toStation,age,gender);
        System.out.println("Booking done "+bookingStatus);
//        bookingStatus = BookingTicket.bookingStatus;
        
        if(bookingStatus){
            for(String str:Railway.clientData){
                passengerRecord.add(str);
            }
            BookingTicket.insertPassengerRecordInDB(passengerRecord,bookingTicket.seatNumber);
            crs = bookingTicket.fetchTicket(bookingTicket.seatNumber,trainNo,dateForBooking);
        }
        return bookingStatus;
    }
    public CachedRowSet printTicket(){
        return crs;
    }
}
