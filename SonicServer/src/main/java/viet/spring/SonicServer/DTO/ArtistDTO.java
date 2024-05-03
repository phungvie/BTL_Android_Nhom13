package viet.spring.SonicServer.DTO;

import java.sql.Date;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import viet.spring.SonicServer.entity.Artist;
@Data
@AllArgsConstructor
@Builder
@ToString

public class ArtistDTO {

	private Integer artistID;
	private String name;
	private Date dateOfBirth;
	private String country;
	private String image;
	public ArtistDTO(Artist viet) {
		artistID=viet.getArtistID();
		name=viet.getName();
		dateOfBirth=viet.getDateOfBirth();
		country=viet.getCountry();
		image=viet.getImage();
	}
	
}
