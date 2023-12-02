package palko.electric_meter_4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import palko.electric_meter_4.model.Index;
import palko.electric_meter_4.repository.IndexRepository;

import java.util.List;

@Service
public class IndexService {
    private final IndexRepository indexRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IndexService(IndexRepository indexRepository, JdbcTemplate jdbcTemplate) {
        this.indexRepository = indexRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Index> getAll() {
        return indexRepository.findAll();
    }
    public Index getById (int id){
        return indexRepository.findById(id).stream().findAny().orElse(null);
    }
    public void save (Index index){
        indexRepository.save(index);
    }
    public void edit (Index index, int id){
        index.setId(id);
        indexRepository.save(index);
    }
    public void delete (int id){
        indexRepository.deleteById(id);
    }
    public List<Index> getAllIndexByMeterId(int id){
        return jdbcTemplate.query("SELECT * FROM index WHERE meter_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Index.class));
    }
}
