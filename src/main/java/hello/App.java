package hello;

import hello.pojo.Artist;
import hello.pojo.Plyta;
import hello.repositories.PlytaRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ComponentScan
@SpringBootApplication
public class App implements CommandLineRunner{
    private static final Logger log = LoggerFactory.getLogger(App.class);
    public static void main (String[] args){
        SpringApplication.run(App.class,args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception{
        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE artists IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE artists(" +
                "id SERIAL, firstName VARCHAR(255), lastName VARCHAR(255), category VARCHAR(255))");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("Slash XXX Metal",
                "Metallica XXX Metal",
                "John Lenon Pop",
                "Justin Bieber Pop").stream()
                .map(artist -> artist.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(artist -> log.info(String.format("Inserting artist record for %s %s", artist[0], artist[1])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO artists(firstName, lastName, category) VALUES (?,?,?)", splitUpNames);

        log.info("Querying for customer records where first_name = 'Slash':");
        jdbcTemplate.query(
                "SELECT id, firstName, lastName, category FROM artists WHERE firstName = ?", new Object[] { "Slash" },
                (rs, rowNum) -> new Artist(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("category"))
        ).forEach(artist -> log.info(artist.toString()));

    }


    @Bean
    public CommandLineRunner demo(PlytaRepo plytaRepo){
        return args
                ->{
            plytaRepo.save(new Plyta("Pierwsza"));
            plytaRepo.save(new Plyta("Druga"));
            plytaRepo.save(new Plyta("Trzecia"));
            plytaRepo.save(new Plyta("Czwarta"));



            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for(Plyta plyta : plytaRepo.findAll()){
                log.info(plyta.toString());
            }

        };
    }
}
