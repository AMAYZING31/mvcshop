package fr.iut.mvcshop.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.iut.mvcshop.model.Produit;
import fr.iut.mvcshop.repository.ProduitRepository;

@Controller
public class ProduitController {

    private ProduitRepository repo;

    public ProduitController(ProduitRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/produits")
    public String listerProduits(
            @RequestParam(value ="p", defaultValue = "0") int page,
            @RequestParam(value ="s", defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Produit> pageProduits = this.repo.findAll(pageable);
        
    
        model.addAttribute("produits", pageProduits.getContent());
        model.addAttribute("page", pageProduits);
        return "produits";
    }
}
