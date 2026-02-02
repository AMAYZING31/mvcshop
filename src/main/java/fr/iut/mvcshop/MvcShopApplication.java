package fr.iut.mvcshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.iut.mvcshop.model.Produit;
import fr.iut.mvcshop.repository.ProduitRepository;

@SpringBootApplication
public class MvcShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcShopApplication.class, args);
	}


	@Bean
	CommandLineRunner initBDD(ProduitRepository repo) {
		return args -> {
			// repo.deleteAll();
			// repo.save( new Produit("Trippi Troppi",20,10));
			// repo.save( new Produit("Pipi Kiwi",10,100));
			// repo.save( new Produit("Bombardino Crocodilo",100,10));
			// repo.save( new Produit("Brr Brr Patapim",150,5));
			// repo.save( new Produit("Pipi Potato",20,10));
			// repo.save( new Produit("Trippi Troppi Troppa Trippa",1000,0));

			repo.findAll().forEach(System.out::println );


			System.out.println("Pipi Kiwi, Pipi Kiwi, Ist'une creatura che smec che abite dans'elva magical wairaks de kiwi.");
		};
	}

}

