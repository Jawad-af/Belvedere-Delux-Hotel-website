package com.belvederedeluxhotelapplication.service;

import com.belvederedeluxhotelapplication.model.Booking;

import java.util.List;

public interface BookedRoomService {

    List<Booking> getAllBookingsByRoomId(Long roomId);
    List<Booking> getAllBookings();
}
