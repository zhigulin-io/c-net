package ru.drifles.app.cleaning.address;

import javax.persistence.*;

@Entity
@Table(name = "address", schema = "public")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false, unique = true, length = 30)
    private String address;

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        private String address;

        private Builder() {}

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Address build() {
            var address = new Address();
            address.id = this.id;
            address.address = this.address;
            return address;
        }
    }
}
