package viet.spring.SonicServer.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import viet.spring.SonicServer.DTO.UserDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userID;
	private String name;
	private String phone;
	private String mail;
	private String password;
	private Date dateOfBirth;
	private String otp;
	private Timestamp signUpDate;
	private String country;
	private String img;
	
	private Integer followers;
	private Integer following;
	
	
	public User(UserDTO viet) {
		this.userID = viet.getUserID();
		this.name = viet.getName();
		this.phone = viet.getPhone();
		this.mail = viet.getMail();
		this.dateOfBirth = viet.getDateOfBirth();
		this.country = viet.getCountry();
		this.img = viet.getImg();
		this.followers = viet.getFollowers();
		this.following = viet.getFollowers();
	}
	
	
	
	
//    @ElementCollection
//    @CollectionTable(name = "librar_bottom")
//    @Column(name = "object")
//    @JoinTable(name = "list", joinColumns = @JoinColumn(name = "user_id"))
//    private List<String> objects = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "lib_artist",
	joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="artist_id"),
	uniqueConstraints = {
			@UniqueConstraint(columnNames = { "user_id", "artist_id" })
			}
	)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
	private List<Artist> libraryArtist;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "lib_playlist",
	joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="playlist_id"),
	uniqueConstraints = {
			@UniqueConstraint(columnNames = { "user_id", "playlist_id" })
			}
	)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
	private List<Playlist> libraryPlaylist;
	
	@ManyToMany(mappedBy = "users")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Song> songs;



	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Playlist> playlists;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Comment> comments;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="role_id"),
	uniqueConstraints = {
			@UniqueConstraint(columnNames = { "user_id", "role_id" })
			}
	)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
	private List<Role> roles;


}
