package com.attendance.attendance.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtUtil {
    private Map<String,String> validTokens=new ConcurrentHashMap<>();
    private static final String SECRET_KEY = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf=";
    public String generateToken(String userName) {
        String jwt= Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        validTokens.put(userName,jwt);
        return jwt;
    }
    public String extractUserName(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
        catch (Exception e){
            return "invalid";
        }
    }
    public boolean validateToken(String token, String userName) {
        String extractedUserName = extractUserName(token);
        return validTokens.containsKey(extractedUserName) && extractedUserName.equals(userName) && validTokens.get(extractedUserName).equals(token);
    }
    public void removeToken(String userId){
        validTokens.remove(userId);
    }
    public String extractUserNameFromRequest(HttpServletRequest request) {
        String requestHeader = request.getHeader("Authorization");
        String token=requestHeader.substring(7);
        return extractUserName(token);
    }
}
