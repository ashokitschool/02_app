package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.StateEntity;

public interface StateRepo extends JpaRepository<StateEntity, Integer> {

	public List<StateEntity> findByCountryCountryId(Integer countryId);

}
