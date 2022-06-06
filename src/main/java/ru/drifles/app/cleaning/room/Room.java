package ru.drifles.app.cleaning.room;

import ru.drifles.app.cleaning.address.Address;
import ru.drifles.app.cleaning.task.Task;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms", schema = "public")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "room", nullable = false, unique = true, length = 30)
    private String room;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    public Room() {
    }

    public Room(String room) {
        this.room = room;
    }

    public void addTask(Task task) {
        if (tasks == null)
            tasks = new HashSet<>();
        tasks.add(task);
        task.setRoom(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
