package com.belvederedeluxhotelapplication.controller;

import com.belvederedeluxhotelapplication.dto.BookingDto;
import com.belvederedeluxhotelapplication.dto.RoomDto;
import com.belvederedeluxhotelapplication.exceptions.PhotoRetrieverException;
import com.belvederedeluxhotelapplication.model.Booking;
import com.belvederedeluxhotelapplication.model.Room;
import com.belvederedeluxhotelapplication.service.BookingService;
import com.belvederedeluxhotelapplication.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private final RoomService roomService;
    @Autowired
    private final BookingService bookingService;

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
    public ResponseEntity<List<RoomDto>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomDto> roomResponses = new ArrayList<>();
        for (Room room : rooms) {
            byte[] photoOfbytes = roomService.getRoomPhotoByRoomId(room.getId());
            if (photoOfbytes != null && photoOfbytes.length > 0) {
                String base64Photo = Base64.encodeBase64String(photoOfbytes);
                RoomDto roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);
    }

    private RoomDto getRoomResponse(Room room) {
        List<Booking> bookings = getRoomBookings(room);
        List<BookingDto> bookingDto = bookings.stream().map(
                booking -> new BookingDto(booking.getBookingId(), booking.getCheckInDate(),
                        booking.getCheckOutDate(), booking.getBookingConfirmationCode())
        ).toList();

        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try{
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrieverException("Error retrieving the photo");
            }
        }
        return new RoomDto(room.getId(), room.getRoomType(),
                room.getRoomPrice(), room.isBooked(), photoBytes, bookingDto);
    }
    private List<Booking> getRoomBookings(Room room) {
        return bookingService.findByRoomId(room.getId());
    }
}