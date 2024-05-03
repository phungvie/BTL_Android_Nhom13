package viet.spring.SonicServer.entity;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import viet.spring.SonicServer.DTO.PlaylistDTO;
import jakarta.persistence.UniqueConstraint;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity

public class Playlist {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "playlist_id")
	private Integer playlistID;
	private String name;
	private String image;
	
	
	
	public Playlist(PlaylistDTO playlistDTO) {
		this.playlistID = playlistDTO.getPlaylistID();
		this.name = playlistDTO.getName();
		this.image = playlistDTO.getImage();
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
	@ToString.Exclude // Không sử dụng trong toString()
	@JoinTable(
			name = "playlist_song", 
			joinColumns = @JoinColumn(name = "playlist_id"), 
			inverseJoinColumns = @JoinColumn(name = "song_id"), 
			uniqueConstraints = {
					@UniqueConstraint(columnNames = { "song_id", "playlist_id" }) 
				}
			)
	private List<Song> songs;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private User user;
	
	@ManyToMany(mappedBy = "libraryPlaylist")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<User> addedUsers;
}
