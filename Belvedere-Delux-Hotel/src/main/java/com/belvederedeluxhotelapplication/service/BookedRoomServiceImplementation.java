package com.belvederedeluxhotelapplication.service;

import com.belvederedeluxhotelapplication.model.Booking;
import com.belvederedeluxhotelapplication.repository.BookingRepository;

import java.util.List;

public class BookedRoomServiceImplementation implements BookedRoomService {

    private BookingRepository bookingRepository;

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    @Override
    public List<Booking> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }
}
