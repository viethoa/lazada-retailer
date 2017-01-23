package com.viethoa.services;

import com.viethoa.models.Authentication;
import com.viethoa.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * Created by VietHoa on 19/01/2017.
 */
public class AuthenticationService {

    public static final String ISSUER = "http://lazada.vn";

    public synchronized String createJsonWebToken(String userID, String username, String password, long ttlMillis) throws Exception {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(getSignatureKey());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(userID)
                .setIssuedAt(now)
                .setIssuer(ISSUER)
                .setSubject(String.format("%s/%s", username, password))
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public synchronized Authentication parseJWT(String jwt) {
        try {
            //This line will throw an exception if it is not a signed JWS (as expected)
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(getSignatureKey()))
                    .parseClaimsJws(jwt).getBody();

            User user = new User();
            String userId = claims.getId();
            if (!StringUtils.isEmpty(userId)) {
                user.setId(Long.parseLong(userId));
            }
            String subject = claims.getSubject();
            if (!StringUtils.isEmpty(subject)) {
                String[] subjects = subject.split("/");
                user.setEmail(subjects[0]);
                user.setPassword(subjects[1]);
            }

            return new Authentication(
                    claims.getIssuer(),
                    claims.getExpiration(),
                    user
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private synchronized String getSignatureKey() throws Exception {
        return "Rpe3YoxSGL63yHsyqwnqbNazi8DGU0SRbPduUIjy";
    }

    public synchronized boolean isExpired(Authentication authentication) {
        if (authentication == null || authentication.getExpireTime() == null) {
            return false;
        }

        long date = authentication.getExpireTime().getTime();
        long now = System.currentTimeMillis();
        return (now - date > 0);
    }
}
