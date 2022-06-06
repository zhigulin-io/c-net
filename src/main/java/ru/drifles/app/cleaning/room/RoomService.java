package ru.drifles.app.cleaning.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.drifles.app.cleaning.address.Address;

@Service
public class RoomService {
    private final RoomRepository repository;

    @Autowired
    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Room createRoom(String room, Long addressId) {
        var address = new Address(null);
        address.setId(addressId);

        var entity = new Room(room);
        entity.setAddress(address);

        return repository.save(entity);
    }

    public void deleteRoom(Long id) {
        repository.deleteById(id);
    }

    public Room updateRoom(Long id, String room) {
        var entity = repository.findById(id).orElseThrow();
        entity.setRoom(room);
        return repository.save(entity);
    }

    public Room getRoomById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public Room getRoomWithTasks(Long id) {
        var room = repository.findById(id).orElseThrow();
        room.getTasks().size();
        return room;
    }

    public Iterable<Room> getAllRooms() {
        return null;
    }
}
