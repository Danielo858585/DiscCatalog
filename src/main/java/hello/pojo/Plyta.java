package hello.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Plyta {

    @Id
    @GeneratedValue
    private Long id;
    private String nazwa;
    //private Artist artist;

    public Plyta() {
    }

    public Plyta(String nazwa) {
        this.nazwa = nazwa;
    }
//
//    public Plyta(String nazwa, Artist artist) {
//        this.nazwa = nazwa;
//        this.artist = artist;
//    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
//
//    public Artist getArtist() {
//        return artist;
//    }
//
//    public void setArtist(Artist artist) {
//        this.artist = artist;
//    }
}
