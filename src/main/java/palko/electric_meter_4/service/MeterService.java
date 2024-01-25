package palko.electric_meter_4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import palko.electric_meter_4.model.Meter;
import palko.electric_meter_4.repository.MeterRepository;

import java.util.List;

@Service
public class  MeterService {
    private final MeterRepository meterRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MeterService(MeterRepository meterRepository, JdbcTemplate jdbcTemplate) {
        this.meterRepository = meterRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Meter> getAll() {
        return meterRepository.findAll();
    }
    public Meter getById (int id){
        return meterRepository.findById(id).stream().findAny().orElse(null);
    }

    public void save (Meter meter){
        meterRepository.save(meter);
    }
    public void edit (Meter meter, int id){
        meter.setId(id);
        meterRepository.save(meter);
    }
    public void delete (int id){
        meterRepository.deleteById(id);
    }
    public List<Meter> getAllMeterByAddressId(int address_id){
        return jdbcTemplate.query("SELECT * FROM meter WHERE address_id=?", new Object[]{address_id},
                new BeanPropertyRowMapper<>(Meter.class));
    }

}
