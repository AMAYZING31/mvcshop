package fr.iut.mvcshop.repository;

import fr.iut.mvcshop.model.Produit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query("SELECT p FROM Produit p WHERE p.nom LIKE :x")
    Page<Produit> rechercher(@Param("x") String mc,Pageable pageable);

    
    //Page<Produit>  findByNomContainingPage(String nom, Pageable pageable);

}
