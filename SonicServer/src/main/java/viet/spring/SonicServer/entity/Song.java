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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import viet.spring.SonicServer.DTO.SongDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity

public class Song {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "song_id")
	private Integer songID;
	private String name;
	private String image;
	private String sound;
	private Timestamp releaseTime;
	
	
	
	

	public Song(SongDTO songDTO) {
		super();
		this.songID = songDTO.getSongID();
		this.name = songDTO.getName();
		this.image = songDTO.getImage();
		this.sound = songDTO.getSound();
		this.releaseTime = songDTO.getReleaseTime();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id") // thông qua khóa ngoại albumID
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Album album;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "artist_id") // thông qua khóa ngoại albumID
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Artist artist;

	@ManyToMany(mappedBy = "songs")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Playlist> playlists;

	@OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
	@ToString.Exclude // Không sử dụng trong toString()
	private List<Comment> comments;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
	@ToString.Exclude // Không sử dụng trong toString()

	@JoinTable(name = "like_sonic", joinColumns = @JoinColumn(name = "song_id"), inverseJoinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "song_id", "user_id" }) })
	private List<User> users;

}
