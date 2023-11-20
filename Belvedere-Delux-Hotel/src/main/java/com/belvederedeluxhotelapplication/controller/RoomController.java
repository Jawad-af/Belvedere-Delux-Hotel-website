package com.belvederedeluxhotelapplication.controller;

import com.belvederedeluxhotelapplication.dto.RoomDto;
import com.belvederedeluxhotelapplication.model.Room;
import com.belvederedeluxhotelapplication.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
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
    public List<String> goToRoomTypes(){
         return roomService.getRoomTypes();
    }
}