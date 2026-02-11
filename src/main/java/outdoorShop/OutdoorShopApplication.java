package outdoorShop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"outdoorShop", "jpaoutdoor"})
public class OutdoorShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutdoorShopApplication.class, args);
    }

}
