package com.asking.api_produit.controller.impl.security;

import com.asking.api_produit.controller.interfaces.security.RightsController;

import com.asking.api_produit.dto.security.AddRight;
import com.asking.api_produit.dto.security.UpdateRight;
import com.asking.api_produit.service.impl.RightServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/gestion-produit/security/rights")
@RequiredArgsConstructor
@Tag(name = "Droits", description = "Gestion des droits")
public class RightsImpl implements RightsController {
    private final RightServiceImpl rightServiceImpl;

    @Override
    public ResponseEntity<?> addRight(AddRight addRight) {
        log.info("rights: add-right, body: {}", addRight);
        return ResponseEntity.status(201).body(rightServiceImpl.addRight(addRight));
    }

    @Override
    public ResponseEntity<?> updateRight(UpdateRight updateRight, Long rightId) {
        log.info("rights: update-right, body: {}, id: {}", updateRight, rightId);
        return ResponseEntity.status(200).body(rightServiceImpl.updateRight(updateRight, rightId));
    }

    @Override
    public ResponseEntity<?> getAllRights() {
        log.info("rights: get-all-rights");
        return ResponseEntity.status(200).body(rightServiceImpl.getAllRights());
    }

    @Override
    public ResponseEntity<?> getOneRightById(Long rightId) {
        log.info("rights: get-one-right-id, body: {}", rightId);
        return ResponseEntity.status(200).body(rightServiceImpl.getRightById(rightId));
    }

    @Override
    public ResponseEntity<?> deleteRightById(Long id) {
        log.info("rights: delete-right-id, body: {}", id);
        rightServiceImpl.deleteRight(id);
        return ResponseEntity.status(200).body(null);
    }
}
