package com.synpulse8.pulse8.core.yournamebackend.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class P8CAuthenticationContextImpl extends P8CAuthenticationContext {

    @Override
    public P8CAuthenticationAttributesImpl getAttributes() {
        return (P8CAuthenticationAttributesImpl) this.attributes;
    }

    public P8CAuthenticationContextImpl(P8CAuthenticationAttributesImpl attributes) {
        super(attributes);
    }

    public static DefaultOidcUser getOidcUser() {
        return (DefaultOidcUser) getUser();
    }

    @Override
    public String getUsername() {
        return getAttributes().getUsername();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        if (getUser() instanceof DefaultOidcUser) {
            return (Set<GrantedAuthority>) getOidcUser().getAuthorities();
        }
        return new HashSet<>();
    }

    public String getCurrentClientId() {
        return getAttributes().getCurrentClient();
    }

    public List<String> getUserHierarchies() {
        List<String> groups = getAttribute(P8CAuthAttribute.GROUPS);

        if (groups != null) {
            return groups;
        }

        return new ArrayList<>();
    }

    public List<String> getUserGroups() {
        List<String> hierarchies = getUserHierarchies();
        List<String> groups = new ArrayList<>();

        for (String hierarchy : hierarchies) {
            if (hierarchy != null) {
                String[] groupSplit = hierarchy.split("/");
                groups.add(groupSplit[groupSplit.length - 1]);
            }
        }

        return groups;
    }

    public Map<String, Map<String, List<String>>> getRoleMap() {
        return getAttribute(P8CAuthAttribute.RESOURCE_ACCESS);
    }

    public List<String> getRolesByClientId(String clientId) {
        if (clientId == null) return new ArrayList<>();

        Map<String, Map<String, List<String>>> roleMap = getRoleMap();

        if (roleMap != null && roleMap.containsKey(clientId)) {
            List<String> roles = roleMap.get(clientId).get(P8CAuthAttribute.ROLES);
            if (roles != null) {
                return roles;
            }
        }

        return new ArrayList<>();
    }

    public List<String> getRoles() {
        return getRolesByClientId(getCurrentClientId());
    }
}
