package com.intuit.chirp.authserver;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AddNewLdapUser {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("sreespassword"));
    }
}
