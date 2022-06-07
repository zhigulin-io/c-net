package ru.drifles.app.cleaning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.drifles.app.cleaning.address.Address;
import ru.drifles.app.cleaning.address.AddressRepository;
import ru.drifles.app.cleaning.room.Room;
import ru.drifles.app.cleaning.room.RoomRepository;
import ru.drifles.app.cleaning.task.TaskRepository;

@SpringBootTest
public class JpaTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TaskRepository taskRepository;

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

    @Test
    public void test1() {
        var tasks = taskRepository.findTasksByAddressIdOrderByPriority(3L);
        tasks.forEach(task -> {
            System.out.println(task.getCurrentPriority());
            System.out.println(task.getRoom().getId());
            System.out.println(task.getRoom().getAddress().getId());
        });
    }

}
