package com.asking.api_produit.controller.impl.product;


import com.asking.api_produit.controller.interfaces.product.ProduitController;
import com.asking.api_produit.modele.product.Produit;
import com.asking.api_produit.service.interfaces.ProduitService;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/gestion-produit/produit")
@RequiredArgsConstructor
@Tag(name = "Produits", description = "Gestion des produits")
public class ProduitControllerImpl implements ProduitController {

}