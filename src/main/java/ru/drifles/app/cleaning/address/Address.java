package ru.drifles.app.cleaning.address;

import ru.drifles.app.cleaning.room.Room;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "addresses", schema = "public")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "address", nullable = false, unique = true, length = 30)
    private String address;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private Set<Room> rooms;

    public Address() {
    }

    public Address(String address) {
        this.address = address;
    }

    public void addRoom(Room room) {
        if (rooms == null)
            rooms = new HashSet<>();
        rooms.add(room);
        room.setAddress(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }
}
