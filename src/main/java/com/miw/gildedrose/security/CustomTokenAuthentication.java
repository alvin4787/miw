package com.miw.gildedrose.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class CustomTokenAuthentication extends AbstractAuthenticationToken {

    private Object principal;
    private String name;

    public CustomTokenAuthentication(String name) {
        super(Collections.emptyList());
        this.name = name;
    }

    public CustomTokenAuthentication(Object principal, String name, Object details, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.name = name;
        setDetails(details);

    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
