package palko.electric_meter_4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import palko.electric_meter_4.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
