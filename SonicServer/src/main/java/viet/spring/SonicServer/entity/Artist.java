package viet.spring.SonicServer.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import viet.spring.SonicServer.DTO.ArtistDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity

public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "artist_id")
	private Integer artistID;
	private String name;
	private Date dateOfBirth;
	private String country;
	private String image;
	
	

	public Artist(ArtistDTO artistDTO) {
		this.artistID = artistDTO.getArtistID();
		this.name = artistDTO.getName();
		this.dateOfBirth = artistDTO.getDateOfBirth();
		this.country = artistDTO.getCountry();
		this.image = artistDTO.getImage();

	}


	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
	@ToString.Exclude // Khoonhg sử dụng trong toString()
	private List<Song> songs;

	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
	@ToString.Exclude // Không sử dụng trong toString()
	private List<Album> albums;
	
	
	@ManyToMany(mappedBy = "libraryArtist")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<User> addedUsers;

}
