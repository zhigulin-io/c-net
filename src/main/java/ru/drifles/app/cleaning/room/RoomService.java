package ru.drifles.app.cleaning.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private final RoomRepository repository;

    @Autowired
    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Room createRoom(String room, Long addressId) {
        var entity = Room.builder()
                .setRoom(room)
                .setAddressId(addressId)
                .build();

        return repository.save(entity);
    }

    public void deleteRoom(Long id) {
        var entity = repository.findById(id).orElseThrow();
        repository.delete(entity);
    }

    public Room updateRoom(Long id, String room, Long addressId) {
        var entity = repository.findById(id).orElseThrow();
        entity.setRoom(room);
        entity.setAddressId(addressId);
        return repository.save(entity);
    }

    public Room getRoomById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Iterable<Room> getAllRooms() {
        return repository.findAll();
    }
}
