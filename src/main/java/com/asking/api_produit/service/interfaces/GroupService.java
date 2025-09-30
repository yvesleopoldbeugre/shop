package com.asking.api_produit.service.interfaces;


import com.asking.api_produit.dto.security.AddGroup;
import com.asking.api_produit.dto.security.UpdateGroup;

public interface GroupService {
    Object addGroup(AddGroup addGroup);
    Object updateGroup(UpdateGroup updateGroups, Long groupId);
    Object getAllGroups();
    Object getGroupById(Long groupId);
    void deleteGroup(Long groupId);
}
