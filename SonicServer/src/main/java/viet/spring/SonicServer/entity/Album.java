package viet.spring.SonicServer.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity

public class Album {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "album_id")
	private Integer albumID;
	private String name;
	private Timestamp releaseTime;
	private String image;
	
	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL) 
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
	public List<Song> songs;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "artist_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
	public Artist artist;
}
