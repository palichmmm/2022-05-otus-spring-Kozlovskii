package ru.otus.spring.service;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class AclPermissionService {

    private final MutableAclService mutableAclService;

    public AclPermissionService(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }

    public void savePermission(Class<?> javaType, Serializable identifier, Permission permission) {

        MutableAcl acl;

        // Подготовить информацию, которую хотим добавить в систему управления доступом (ACE).
        ObjectIdentity oi = new ObjectIdentityImpl(javaType, identifier);
        Sid sid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication().getName());

        // Создать или обновите соответствующий ACL
        try {
            acl = (MutableAcl) mutableAclService.readAclById(oi);
        } catch (NotFoundException err) {
            acl = mutableAclService.createAcl(oi);
        }

        // Теперь предоставьте некоторые разрешения через запись управления доступом (ACE).
        acl.insertAce(acl.getEntries().size(), permission, sid, true);
        mutableAclService.updateAcl(acl);
    }
}
