package viet.spring.SonicServer.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name= "persistent_logins")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity

public class PersistentLogins {
	@Id
	private String series;
	private String username;
	private String token;
	private String last_used;
	
}
