package com.belvederedeluxhotelapplication.service;

import com.belvederedeluxhotelapplication.model.Room;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Service
public interface RoomService {

    Room addNewRoom(String roomType, BigDecimal roomPrice, MultipartFile photo) throws IOException, SQLException;
    List<String> getRoomTypes();

    List<Room> getAllRooms();
}
