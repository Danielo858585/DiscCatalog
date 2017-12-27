package hello.repositories;

import hello.pojo.Plyta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlytaRepo extends CrudRepository<Plyta,Long>{
    List<Plyta> findAllByOrderByIdDesc(String nazwa);
}
