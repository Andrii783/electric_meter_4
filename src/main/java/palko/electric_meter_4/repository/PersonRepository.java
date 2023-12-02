package palko.electric_meter_4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import palko.electric_meter_4.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
}
