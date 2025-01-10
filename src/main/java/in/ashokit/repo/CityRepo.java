package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.CityEntity;

public interface CityRepo extends JpaRepository<CityEntity, Integer>{
	
	public List<CityEntity> findByStateStateId(Integer stateId);

}
