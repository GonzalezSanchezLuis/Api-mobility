package com.transportapi.services;

import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class BlacklistService {
    private Set<String> blacklistedTokens = new HashSet<>();

    public void blacklistToken(String token){
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlackListed(String token){
        return  blacklistedTokens.contains(token);
    }
}
