package com.belvederedeluxhotelapplication.service;


import com.belvederedeluxhotelapplication.model.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> findByRoomId(Long roomId);

}
