package com.asking.api_produit.service.impl;

import com.asking.api_produit.dto.security.AddRight;
import com.asking.api_produit.dto.security.UpdateRight;
import com.asking.api_produit.exceptions.NotFoundException;
import com.asking.api_produit.modele.security.Rights;
import com.asking.api_produit.repository.security.RightsRepository;
import com.asking.api_produit.service.interfaces.RightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RightServiceImpl implements RightService {
    private final RightsRepository rightsRepository;

    @Override
    public Object addRight(AddRight addRight) {
        return rightsRepository.save(addRight.toRight());
    }

    @Override
    public Object updateRight(UpdateRight updateRight, Long rightId) {
        Optional<Rights> optionalRights = rightsRepository.findById(rightId);
        if (optionalRights.isEmpty()) {
            log.error("Ce droit: {} n'existe pas", rightId);
            throw new NotFoundException("Ce droit n'existe pas");
        }
        return rightsRepository.save(updateRight.toUpdateRight(optionalRights.get()));
    }

    @Override
    public Object getAllRights() {
        return rightsRepository.findAll();
    }

    @Override
    public Object getRightById(Long id) {
        Optional<Rights> optionalRights = rightsRepository.findById(id);
        if (optionalRights.isEmpty()) {
            log.error("Updating rights with id: {}", id);
            throw new NotFoundException("Ce droit n'existe pas");
        }
        return optionalRights.get();
    }

    @Override
    public void deleteRight(Long id) {
        Optional<Rights> optionalRights = rightsRepository.findById(id);
        if (optionalRights.isEmpty()) {
            log.error("Updating rights with id: {}", id);
            throw new NotFoundException("Ce droit n'existe pas");
        }
        rightsRepository.delete(optionalRights.get());
    }
}
