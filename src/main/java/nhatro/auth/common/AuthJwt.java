package nhatro.auth.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import nhatro.core.common.ErrorCodeDefs;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

@Slf4j
public class AuthJwt {
    private final long JWT_EXPIRATION = 604800000L;

    public String createToken(JwtData jwtData) {
        if (jwtData != null) {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
            // Tạo chuỗi json web token từ id của user.
            return Jwts.builder()
                    .setSubject(jwtData.toString())
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, ErrorCodeDefs.SECRET)
                    .compact();
        }
        return null;
    }

    public JwtData checkToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(ErrorCodeDefs.SECRET)
                .parseClaimsJws(token)
                .getBody();
        String data = claims.getSubject();
        ObjectMapper mapper = new ObjectMapper();
        JwtData jwtData = null;
        try {
            jwtData = mapper.readValue(data, JwtData.class);
        } catch (JsonProcessingException e) {
            log.error("token error ! token {}", data);
            return null;
        }
        return jwtData;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(ErrorCodeDefs.SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public static String get_SHA_256_SecurePassword(String passwordToHash) {
        try {
            byte[] salt = getSalt();
            String securePassword = get_SHA_256_SecurePassword(passwordToHash);
            log.info(securePassword);
            return securePassword;
        } catch (Exception e) {
            log.error("error {}", e);
            return null;
        }
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
