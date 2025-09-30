package com.asking.api_produit.controller.interfaces.security;

import com.asking.api_produit.dto.security.AddGroup;
import com.asking.api_produit.dto.security.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ProfileController {
    @PostMapping("/add-group")
    @Operation(summary = "Ajouter un groupe")
    ResponseEntity<?> addGroup(@RequestBody AddGroup addGroups);

    @PutMapping("/update-group/{groupId}")
    @Operation(summary = "Mise à jour d'un groupe")
    ResponseEntity<?> updateGroup(@RequestBody UpdateGroup updateGroups, @PathVariable Long groupId);

    @GetMapping("/all-groups")
    @Operation(summary = "Récupérer tous les groupes")
    ResponseEntity<?> getAllGroups();

    @GetMapping("/info-group/{groupId}")
    @Operation(summary = "Récupérer un groupe")
    ResponseEntity<?> getOneGroupById(@PathVariable Long groupId);

    @DeleteMapping("/delete-group")
    @Operation(summary = "Supprimer un groupe")
    ResponseEntity<?> deleteGroupById(@RequestParam Long id);
}
