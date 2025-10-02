package com.asking.api_produit.view.impl.product;

import com.asking.api_produit.modele.product.Produit;
import com.asking.api_produit.service.interfaces.ProduitService;
import com.asking.api_produit.view.interfaces.product.ProductView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProductViewImpl implements ProductView {
    private final ProduitService produitService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/all-product")
    public String allProduct(){
        return "allProduct";
    }

    @GetMapping("/product-by-category/{slug}")
    public String productByCategory(@PathVariable String slug){
        return "allProduct";
    }

    @GetMapping("/info-product")
    public String infoProduct(){
        return "infoProduct";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart(){
        return "shoppingCart";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }
}

/*
 Le contrôleur ProduitController gère les requêtes liées aux produits.

 Il est annoté avec @Controller pour indiquer qu'il est un composant du contrôleur dans Spring MVC.
 Il est également annoté avec @RequestMapping("/ProduitApp") pour spécifier le chemin de base pour toutes les requêtes liées aux produits.

 Le constructeur de la classe prend en paramètre un objet ProduitService, qui est utilisé pour effectuer les opérations CRUD sur les produits.

 Le contrôleur gère les pages en retournant les noms des fichiers de modèle correspondants.
 Par exemple, la méthode liste() retourne "list" pour afficher la page list.html.
 Cette méthode récupère la liste des produits à partir de ProduitService et l'ajoute au modèle avant de la renvoyer à la vue.
 Les autres méthodes gèrent les actions liées aux produits.

 Par exemple, la méthode creer() est annotée avec @PostMapping("create") pour spécifier qu'elle gère les requêtes POST vers "/ProduitApp/create".
 Elle prend un objet Produit en tant que paramètre, qui est lié aux données envoyées dans la requête.
 Cette méthode utilise ProduitService pour créer le produit et redirige ensuite vers la page list.html.

 La méthode read() est annotée avec @GetMapping("/read") pour spécifier qu'elle gère les requêtes GET vers "/ProduitApp/read".
 Elle utilise ProduitService pour récupérer la liste des produits et la renvoie en tant que réponse.

 Les autres méthodes gèrent les mises à jour et les suppressions de produits.
 Par exemple, la méthode update() est annotée avec @PostMapping("/update/{id}") pour spécifier qu'elle gère les requêtes POST vers "/ProduitApp/update/{id}".
 Elle prend un objet Produit en tant que paramètre, qui est lié aux données envoyées dans la requête.
 Cette méthode utilise ProduitService pour mettre à jour le produit avec l'ID spécifié et redirige ensuite vers la page list.html.

 La méthode delete() est annotée avec @DeleteMapping() pour spécifier qu'elle gère les requêtes DELETE vers "/ProduitApp/{id}".
 Elle utilise ProduitService pour supprimer le produit avec l'ID spécifié et renvoie true si la suppression a réussi.

 La méthode suppression() est annotée avec @RequestMapping("/delete/{id}") pour spécifier qu'elle gère les requêtes GET et POST vers "/ProduitApp/delete/{id}".
 Elle utilise la méthode delete() pour supprimer le produit avec l'ID spécifié et redirige ensuite vers la page list.html.

 En résumé, le contrôleur ProduitController gère les requêtes liées aux produits, y compris la création, la lecture, la mise à jour et la suppression des produits. Il utilise ProduitService pour effectuer les opérations CRUD sur les produits et renvoie les réponses appropriées en fonction des actions effectuées.
 */
