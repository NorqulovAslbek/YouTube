package com.example.util;

import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JWTUtil {

    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final int emailTokenLiveTime = 1000 * 3600; // 1-hour
    private static final String secretKey = "mazgissddfskfekssssssssssssssssssssssssssssssssssssssssssssssssssssfkekekgekgkegkekerkgkegkrkgrkgkrgkrgkrkkrgkrkrkgnrgrgjrgkjrkjfdjekfekf";

    public static String encode(Integer profileId, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        jwtBuilder.signWith(secretKeySpec);
        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("role", role);
        jwtBuilder.expiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.issuer("Youtube");
        return jwtBuilder.compact();
    }


    public static JwtDTO decode(String token) {
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();

        Integer id = (Integer) claims.get("id");
        String role = (String) claims.get("role");
        if (role != null) {
            ProfileRole profileRole = ProfileRole.valueOf(role);
            return new JwtDTO(id, profileRole);
        }
        return new JwtDTO(id);
    }

    public static String encodeForEmail(Integer profileId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        jwtBuilder.signWith(secretKeySpec);
        jwtBuilder.claim("id", profileId);
        jwtBuilder.expiration(new Date(System.currentTimeMillis() + (emailTokenLiveTime)));
        jwtBuilder.issuer("Youtube");
        return jwtBuilder.compact();
    }


    public static JwtDTO decodeForSpringSecurity(String token) {
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();
        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();
        String email = (String) claims.get("email");
        String role = (String) claims.get("role");
        ProfileRole profileRole = ProfileRole.valueOf(role);
        return new JwtDTO(email, profileRole);
    }

    public static String encode(String email, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        jwtBuilder.signWith(secretKeySpec);
        jwtBuilder.claim("email", email);
        jwtBuilder.claim("role", role);
        jwtBuilder.expiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.issuer("Youtube");
        return jwtBuilder.compact();
    }

}
