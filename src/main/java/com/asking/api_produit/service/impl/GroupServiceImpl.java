package com.asking.api_produit.service.impl;

import com.asking.api_produit.dto.security.AddGroup;
import com.asking.api_produit.dto.security.UpdateGroup;
import com.asking.api_produit.exceptions.NotFoundException;
import com.asking.api_produit.modele.security.Profile;
import com.asking.api_produit.modele.security.Rights;
import com.asking.api_produit.repository.security.GroupsRepository;
import com.asking.api_produit.repository.security.RightsRepository;
import com.asking.api_produit.service.interfaces.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupsRepository groupsRepository;
    private final RightsRepository rightsRepository;

    @Override
    public Object addGroup(AddGroup addGroup) {
        List<Rights> rights = null;
        if (addGroup.getRightIds() != null)
            rights = rightsRepository.findAllById(addGroup.getRightIds());

        return groupsRepository.save(addGroup.toGroup(rights));
    }

    @Override
    public Object updateGroup(UpdateGroup updateGroup, Long groupId) {
        Optional<Profile> optionalGroup = groupsRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            log.error("Ce groupe {} n'existe pas", groupId);
            throw new NotFoundException("Ce groupe " + groupId + " n'existe pas");
        }
        List<Rights> rights = null;
        if (updateGroup.getRightIds() != null)
            rights = rightsRepository.findAllById(updateGroup.getRightIds());

        return groupsRepository.save(updateGroup.toUpdateGroup(optionalGroup.get(), rights));
    }

    @Override
    public Object getAllGroups() {
        return groupsRepository.findAll();
    }

    @Override
    public Object getGroupById(Long groupId) {
        Optional<Profile> optionalGroup = groupsRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            log.error("Ce groupe {} n'existe pas", groupId);
            throw new NotFoundException("Ce groupe " + groupId + " n'existe pas");
        }
        return optionalGroup.get();
    }

    @Override
    public void deleteGroup(Long groupId) {
        Optional<Profile> optionalGroup = groupsRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            log.error("Ce groupe {} n'existe pas", groupId);
            throw new NotFoundException("Ce groupe " + groupId + " n'existe pas");
        }
        groupsRepository.delete(optionalGroup.get());
    }
}
