package com.expense.BudgetBuddy.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private String secret="";

    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokenUtil() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keyGenerator.generateKey();
       secret = Base64.getEncoder().encodeToString(sk.getEncoded());

    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims()
                .subject(userDetails.getUsername())
                .add(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*5))
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    public String getUserNameFromToken(String jwtToken){
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }


    private <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        return claimsResolver.apply(claims);
    }


    public boolean validateToken(String jwtToken, UserDetails userDetails) {
            final String userName= getUserNameFromToken(jwtToken);
           return userName.equals(userDetails.getUsername())  && !isTokenExpired(jwtToken);

    }

    private boolean isTokenExpired(String jwtToken) {
       final Date expirationDate = getExpirationDateFromToken(jwtToken);
       return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String jwtToken) {
      return getClaimFromToken(jwtToken,Claims::getExpiration);
    }


}
