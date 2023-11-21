package com.belvederedeluxhotelapplication.controller;

import com.belvederedeluxhotelapplication.dto.RoomDto;
import com.belvederedeluxhotelapplication.exceptions.ErrorFetchingTheImageSource;
import com.belvederedeluxhotelapplication.model.Room;
import com.belvederedeluxhotelapplication.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;


    @PostMapping("/add/new-room")
    public ResponseEntity<RoomDto> addNewRoom(
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice,
            @RequestParam("photo") MultipartFile photo
    ) throws SQLException, IOException {

        Room savedRoom = roomService.addNewRoom(roomType, roomPrice, photo);
        RoomDto response =
                new RoomDto(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/room-types")
    public List<String> goToRoomTypes() {
        return roomService.getRoomTypes();
    }

    @GetMapping("/all-rooms")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomDto> roomResponses = new ArrayList<>();
        for (Room room : rooms) {
            try {
                byte[] photoBytes = room.getPhoto().getBytes(1, (int) room.getPhoto().length());
                String image = new String(photoBytes);

                RoomDto roomDto = new RoomDto(room.getId(), room.getRoomType(), room.getRoomPrice(),
                        room.isBooked(), photoBytes, room.getBookings());
            } catch (Exception exception) {
                throw new ErrorFetchingTheImageSource("Unable to fetch the image source from the database");
            }
        }
        return ResponseEntity.ok(roomResponses);
    }
}