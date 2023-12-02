package palko.electric_meter_4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import palko.electric_meter_4.model.Address;
import palko.electric_meter_4.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AddressService(AddressRepository addressRepository, JdbcTemplate jdbcTemplate) {
        this.addressRepository = addressRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Address> getAll(){
        return addressRepository.findAll();
    }
    public Address getById(int id){
        return addressRepository.findById(id).stream().findAny().orElse(null);
    }
    @Transactional
    public void edit (Address address, int id){
        address.setId(id);
        addressRepository.save(address);
    }
    public Address save (Address address){
        return addressRepository.save(address);
    }
    public void delete(int id){
        addressRepository.deleteById(id);
    }
    public Optional<Address> getByAddress (String city, String street, String house, String letter,
                                           String entrance, String apartment){
        return jdbcTemplate.query("SELECT * from address WHERE city=? and street=? and house=? and letter=? and " +
                        "entrance=? and apartment=?",
                new Object[]{city,street,house,letter,entrance,apartment},
                new BeanPropertyRowMapper<>(Address.class)).stream().findAny();
    }
    public List<Address> getAllByOwnerId(int owner_id){
        return jdbcTemplate.query("SELECT * from address WHERE owner_id=?",new Object[]{owner_id},new BeanPropertyRowMapper<>(Address.class));
    }
}
