package com.belvederedeluxhotelapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private Long Id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumberOfGuests;
    private String bookingConfirmationCode;

    private RoomDto room;

    public BookingDto(Long id, LocalDate checkInDate,
                           LocalDate checkOutDate, String bookingConfirmationCode) {
        Id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
