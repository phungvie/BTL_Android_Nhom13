package viet.spring.SonicServer.DTO;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import viet.spring.SonicServer.entity.Artist;
import viet.spring.SonicServer.entity.Song;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class SongDTO {
	private Integer songID;
	private String name;
	private String image;
	private String sound;
	private Timestamp releaseTime;
	private String artistName;
	public SongDTO(Song viet) {
		this.songID = viet.getSongID();
		this.name = viet.getName();
		this.image = viet.getImage();
		this.sound = viet.getSound();
		this.releaseTime = viet.getReleaseTime();
		artistName=viet.getArtist().getName();
	}
	
	
}

