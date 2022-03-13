package com.example.demo.utils;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class JwtService {

    /*
    JWT 생성
    @param userIdx
    @return String
     */
    public String createJwt(int userIdx){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userIdx",userIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)))
//                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public int getUserIdx() throws BaseException{
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) { //여기서 만료시간체크 다 되는것같다.
            throw new BaseException(INVALID_JWT);
        }

        // 3. userIdx 추출
//        System.out.println(!claims.getBody().getExpiration().before(new Date())); //굳이 이렇게 만료시간 체크 안해도되는듯
//        if(!claims.getBody().getExpiration().before(new Date())){
//            return claims.getBody().get("userIdx",Integer.class);
//        }else{
//            System.out.println("만료");
//            throw new BaseException(EXPIRED_JWT);
//        }
        return claims.getBody().get("userIdx",Integer.class);
    }

    public boolean validateToken() throws BaseException {
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(Secret.JWT_SECRET_KEY).parseClaimsJws(accessToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
