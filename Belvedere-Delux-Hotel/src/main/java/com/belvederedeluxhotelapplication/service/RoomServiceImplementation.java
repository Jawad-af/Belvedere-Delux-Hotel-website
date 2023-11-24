package com.belvederedeluxhotelapplication.service;

import com.belvederedeluxhotelapplication.exceptions.ErrorFetchingTheSource;
import com.belvederedeluxhotelapplication.model.Room;
import com.belvederedeluxhotelapplication.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new ErrorFetchingTheSource("Image not found");
        }
        Blob photoBlob = room.get().getPhoto();
        if (photoBlob != null) {
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }

        return null;
    }

}
