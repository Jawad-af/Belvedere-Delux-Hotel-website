package com.belvederedeluxhotelapplication.service;

import com.belvederedeluxhotelapplication.model.Booking;
import com.belvederedeluxhotelapplication.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImplementation implements BookingService {

    BookingRepository bookingRepository;

    @Override
    public List<Booking> findByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }
}
