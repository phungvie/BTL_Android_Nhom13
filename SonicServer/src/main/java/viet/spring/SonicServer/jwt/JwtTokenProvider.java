package viet.spring.SonicServer.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import viet.spring.SonicServer.config.UserImplUserDetails;

@Slf4j
@NoArgsConstructor
@Component
public class JwtTokenProvider {
	// Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
	private final String JWT_SECRET = "vietdz";

	// Thời gian có hiệu lực của chuỗi jwt
	private final long JWT_EXPIRATION = 604800000L;
//	private final long JWT_EXPIRATION = 60000L;
	// Tạo ra jwt từ thông tin user
	public String generateToken(UserImplUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		// Tạo chuỗi json web token từ id của user.
		return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
	}

	// Lấy thông tin user từ jwt
	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("vietError Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("vietError Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("vietError Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("vietError JWT claims string is empty");
		}
		return false;
	}
}