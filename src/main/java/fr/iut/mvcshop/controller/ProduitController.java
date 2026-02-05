package fr.iut.mvcshop.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.iut.mvcshop.model.Produit;
import fr.iut.mvcshop.repository.ProduitRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProduitController {

    private ProduitRepository repo;

    public ProduitController(ProduitRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/produits")
    public String listerProduits(
            @RequestParam(value = "p", defaultValue = "0") int page,
            @RequestParam(value = "s", defaultValue = "5") int size,
            @RequestParam(value = "mc", defaultValue = "") String motCle,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Produit> pageProduits;
        if (motCle.length() > 0) {
            pageProduits = this.repo.rechercher("%" + motCle + "%", pageable);
        } else {
            pageProduits = this.repo.findAll(pageable);
        }

        model.addAttribute("produits", pageProduits.getContent());
        model.addAttribute("page", pageProduits);
        model.addAttribute("motCle", motCle);
        return "produits";
    }

    @RequestMapping(value = "/produitDelete", method = RequestMethod.GET)
    public String supprimerProduit(Long id, int p, int s, String mc, RedirectAttributes redirectAttributes) {
        // TODO on pourrait verifier si le produit existe avant
        this.repo.deleteById(id);

        redirectAttributes.addAttribute("p", p);
        redirectAttributes.addAttribute("s", s);
        redirectAttributes.addAttribute("mc", mc);
        return "redirect:/produits";
    }

    @GetMapping("/produitform")
    public String editerProduits(
            @RequestParam(value = "id", defaultValue = "0") Long id,
            @RequestParam(value = "p", defaultValue = "0") int page,
            @RequestParam(value = "s", defaultValue = "5") int size,
            @RequestParam(value = "mc", defaultValue = "") String motCle,
            Model model) {

        if (id > 0) {
            Optional<Produit> optPro = this.repo.findById(id);
            if (optPro.isPresent()) {
                model.addAttribute("produit", optPro.get());
            } else {
                return "redirect:/";
            }
        } else {
            model.addAttribute("produit", new Produit());
        }
        model.addAttribute("p", page);
        model.addAttribute("s", size);
        model.addAttribute("mc", motCle);

        return "produitform";
    }

    @PostMapping("/produitSave")
    public String sauverProduit(@Valid Produit produit,
            BindingResult bindingResult, int p, int s, String mc,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("p", p);
            redirectAttributes.addAttribute("s", s);
            redirectAttributes.addAttribute("mc", mc);
            return "produitform";
        }

        this.repo.save(produit);

        redirectAttributes.addAttribute("p", p);
        redirectAttributes.addAttribute("s", s);
        redirectAttributes.addAttribute("mc", mc);
        return "redirect:/produits";
    }

}
