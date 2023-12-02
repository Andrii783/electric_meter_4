package palko.electric_meter_4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import palko.electric_meter_4.model.Address;
import palko.electric_meter_4.model.Meter;
import palko.electric_meter_4.model.Person;
import palko.electric_meter_4.repository.PersonRepository;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonService(PersonRepository personRepository, JdbcTemplate jdbcTemplate) {
        this.personRepository = personRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person getById(int id) {
        return personRepository.findById(id).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        personRepository.save(person);
    }

    public void edit(Person person, int id) {
        person.setId(id);
        personRepository.save(person);
    }

    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public List<Meter> getAllMetersByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM meter WHERE address_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Meter.class));
    }

    public List<Address> getAllAddressesPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM address WHERE owner_id=?", new Object[]{id},
        new BeanPropertyRowMapper<>(Address.class));
    }
}
