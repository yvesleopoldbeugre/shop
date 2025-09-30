package com.asking.api_produit.controller.interfaces.security;

import com.asking.api_produit.dto.security.AddRight;
import com.asking.api_produit.dto.security.UpdateRight;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface RightsController {
    @PostMapping("/add-right")
    @Operation(summary = "Ajouter un droit")
    ResponseEntity<?> addRight(@RequestBody AddRight addRights);

    @PutMapping("/update-right/{rightId}")
    @Operation(summary = "Mise à jour d'un droit")
    ResponseEntity<?> updateRight(@RequestBody UpdateRight updateRights, @PathVariable Long rightId);

    @GetMapping("/all-rights")
    @Operation(summary = "Récupérer tous les droits")
    ResponseEntity<?> getAllRights();

    @GetMapping("/info-right/{rightId}")
    @Operation(summary = "Récupérer un droit")
    ResponseEntity<?> getOneRightById(@PathVariable Long rightId);

    @DeleteMapping("/delete-right")
    @Operation(summary = "Supprimer un droit")
    ResponseEntity<?> deleteRightById(@RequestParam Long id);
}
