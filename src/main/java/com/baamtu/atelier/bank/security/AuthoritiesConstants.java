package com.baamtu.atelier.bank.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String ROLE_ADVISOR = "ROLE_ADVISOR";

    public static final String ROLE_MANAGER = "ROLE_MANAGER";

    private AuthoritiesConstants() {}
}
