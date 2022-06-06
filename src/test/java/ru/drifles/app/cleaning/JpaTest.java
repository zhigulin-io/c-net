package ru.drifles.app.cleaning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.drifles.app.cleaning.address.Address;
import ru.drifles.app.cleaning.address.AddressRepository;
import ru.drifles.app.cleaning.room.Room;
import ru.drifles.app.cleaning.room.RoomRepository;

@SpringBootTest
public class JpaTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void shouldAddRoomToExistingAddress() {
        var address = new Address("qwer");
        var room = new Room("asdf");
        address.addRoom(room);
        addressRepository.save(address);

        var newRoom = new Room("zxcv");
        address.addRoom(newRoom);
        addressRepository.save(address);
    }

    @Test
    public void test() {
        var addr = new Address(null);
        addr.setId(1L);

        var room = new Room("qqqqwww");
        room.setAddress(addr);

        roomRepository.save(room);
    }

}
