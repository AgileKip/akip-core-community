package org.akip.security;

import org.akip.domain.ProcessMember;
import org.akip.domain.ProcessRole;
import org.akip.domain.TenantMember;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {}

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public static List<String> getAuthorities() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public static List<String> getFullAuthorities(List<ProcessMember> processMembers, List<TenantMember> tenantMembers) {
        List<String> authorities = new ArrayList<>();

        SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .forEach(grantedAuthority -> {
                    authorities.add(grantedAuthority.getAuthority());
                });

        for (ProcessMember processMember : processMembers){
            List<ProcessRole> processRoles = processMember.getProcessRoles();
            processRoles
                    .stream()
                    .forEach(processRole -> {
                        authorities.add(processMember.getProcessDefinition().getBpmnProcessDefinitionId() + "." + processRole.getName());
                    });
        }

//        for (TenantMember tenantMember : tenantMembers){
//            tenantMember.getTenantRoles()
//                    .stream()
//                    .forEach(tenantRole -> {
//                        authorities.add(tenantRole.getTenant().getIdentifier() + "." + tenantRole.getName());
//                    });
//        }

        return authorities;
    }

}
