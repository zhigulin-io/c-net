package ru.drifles.app.cleaning.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Iterable<Address> getAllAddresses() {
        return repository.findAll();
    }

    public void deleteAddressById(Long id) {
        repository.deleteById(id);
    }

    public Address createAddress(String address) {
        return repository.save(Address.builder().setAddress(address).build());
    }

    public Address updateAddress(Long id, String address) {
        var entity = repository.findById(id).orElseThrow();
        entity.setAddress(address);
        return repository.save(entity);
    }

    public Address getAddressById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
