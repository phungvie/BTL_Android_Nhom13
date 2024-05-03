package viet.spring.SonicServer.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import viet.spring.SonicServer.entity.Playlist;

@Data
@AllArgsConstructor
@Builder
@ToString
public class PlaylistDTO {

	private Integer playlistID;
	private String name;
	private String image;
    private String nameUserCreate;
    private Integer IdUserCreate;
	
	public  PlaylistDTO(Playlist viet) {
		playlistID=viet.getPlaylistID();
		name=viet.getName();
		image=viet.getImage();
		nameUserCreate=viet.getUser().getName();
		IdUserCreate=viet.getUser().getUserID();
	}
	
	
}
