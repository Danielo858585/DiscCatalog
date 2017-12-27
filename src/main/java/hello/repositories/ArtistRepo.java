package hello.repositories;

import hello.pojo.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepo extends CrudRepository <Artist, Long> {

}
