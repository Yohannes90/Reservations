package com.example.reservations.buisness.service;

import com.example.reservations.buisness.domain.RoomReservation;
import com.example.reservations.entity.Guest;
import com.example.reservations.entity.Reservation;
import com.example.reservations.entity.Room;
import com.example.reservations.repository.GuestRepository;
import com.example.reservations.repository.ReservationRepository;
import com.example.reservations.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.util.*;

@Service
public class ReservationService {
    private GuestRepository guestRepository;
    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;

    @Autowired
    public ReservationService(GuestRepository guestRepository, ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public List<RoomReservation> getRoomReservationForDate(String dateString) {
        Date date = this.createDateFromString(dateString);
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room->{
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findByDate(new Date(date.getTime()));
        if (null != reservations) {
            reservations.forEach(reservation -> {
                Guest guest = this.guestRepository.findOne(reservation.getId());
                if (null != guest) {
                    RoomReservation roomReservation = roomReservationMap.get(reservation.getId());
                    roomReservation.setDate(date);
                    roomReservation.setFirstName(guest.getFirstName());
                    roomReservation.setLastName(guest.getLastName());
                    roomReservation.setGuestId(guest.getId());
                }
            });
        }
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long roomId: roomReservationMap.KeySet()) {
            roomReservations.add(roomReservationMap.get(roomId));
        }
        return roomReservations;
    }

    private Date createDateFromString(String dateString) {
        Date date = null;
        if (null != dateString) {
            try{
                date = DATE_FORMAT.parse(dateString);
            }catch(ParseException pe) {
                date = new Date();
            }
        } else {
            date = new Date();
        }
    }
}
