package ru.drifles.app.cleaning.room;

import javax.persistence.*;

@Entity
@Table(name = "room", schema = "public")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room", nullable = false, unique = true, length = 30)
    private String room;

    @Column(name = "address_id", nullable = false)
    private Long addressId;

    @Transient
    private String address;

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String room() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Long addressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String address() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String room;
        private Long addressId;
        private String address;

        private Builder() {}

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setRoom(String room) {
            this.room = room;
            return this;
        }

        public Builder setAddressId(Long addressId) {
            this.addressId = addressId;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Room build() {
            var entity = new Room();
            entity.id = this.id;
            entity.room = this.room;
            entity.addressId = this.addressId;
            return entity;
        }
    }
}
