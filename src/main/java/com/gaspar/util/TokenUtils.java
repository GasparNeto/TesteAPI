/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaspar.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *
 * @author Neto-PC
 */
public class TokenUtils {

    public static final String CHAVE_PRIVADA = "ac794fd6e34ad821462201dfbd8044fc"; //md-5 = gaspar

    public static String geraToken(String payload) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, CHAVE_PRIVADA)
                .setPayload(payload)
                .compact();
    }

    public static Boolean validaToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(CHAVE_PRIVADA).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
