package com.belvederedeluxhotelapplication.service;

import com.belvederedeluxhotelapplication.dto.RoomDto;
import com.belvederedeluxhotelapplication.model.Room;
import com.belvederedeluxhotelapplication.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImplementation implements RoomService {

    @Autowired
    private final RoomRepository roomRepository;

    @Override
    public Room addNewRoom(
            String roomType,
            BigDecimal roomPrice,
            MultipartFile photo
    ) throws IOException, SQLException {

        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!photo.isEmpty()) {
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

}
