package com.asking.api_produit.controller.impl.security;

import com.asking.api_produit.controller.interfaces.security.ProfileController;
import com.asking.api_produit.dto.security.AddGroup;
import com.asking.api_produit.dto.security.UpdateGroup;
import com.asking.api_produit.service.impl.GroupServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/gestion-produit/security/profiles")
@RequiredArgsConstructor
@Tag(name = "Profiles", description = "Gestion des profiles")
public class ProfilesImpl implements ProfileController {
    private final GroupServiceImpl groupServiceImpl;

    @Override
    public ResponseEntity<?> addGroup(AddGroup addGroup) {
        log.info("groups: add-group, body: {}", addGroup);
        return ResponseEntity.status(201).body(groupServiceImpl.addGroup(addGroup));
    }

    @Override
    public ResponseEntity<?> updateGroup(UpdateGroup updateGroup, Long groupId) {
        log.info("groups: update-groups, body: {}", updateGroup);
        return ResponseEntity.status(200).body(groupServiceImpl.updateGroup(updateGroup,groupId));
    }

    @Override
    public ResponseEntity<?> getAllGroups() {
        log.info("groups: get-all-groups");
        return ResponseEntity.status(200).body(groupServiceImpl.getAllGroups());
    }

    @Override
    public ResponseEntity<?> getOneGroupById(Long groupId) {
        log.info("groups: get-one-group, groupId: {}", groupId);
        return ResponseEntity.status(200).body(groupServiceImpl.getGroupById(groupId));
    }

    @Override
    public ResponseEntity<?> deleteGroupById(Long id) {
        log.info("groups: delete-group, groupId: {}", id);
        groupServiceImpl.deleteGroup(id);
        return ResponseEntity.status(200).body(null);
    }
}
