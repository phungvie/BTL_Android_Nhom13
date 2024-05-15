package viet.spring.SonicServer.DTO;

import viet.spring.SonicServer.DTO.ArtistDTO;
import viet.spring.SonicServer.DTO.PlaylistDTO;
import viet.spring.SonicServer.DTO.SongDTO;

public class Find {
	private final SongDTO songDTO;
	private final PlaylistDTO playlistDTO;
	private final ArtistDTO artistDTO;
	
	public Find(SongDTO songDTO) {
		this.songDTO = songDTO;
		this.playlistDTO = null;
		this.artistDTO = null;
	}
	public Find(PlaylistDTO playlistDTO) {

		this.songDTO = null;
		this.playlistDTO = playlistDTO;
		this.artistDTO = null;
	}
	public Find(ArtistDTO artistDTO) {

		this.songDTO = null;
		this.playlistDTO = null;
		this.artistDTO = artistDTO;
	}
	public SongDTO getSongDTO() {
		return songDTO;
	}
	public PlaylistDTO getPlaylistDTO() {
		return playlistDTO;
	}
	public ArtistDTO getArtistDTO() {
		return artistDTO;
	}
	@Override
	public String toString() {
		return "Find [songDTO=" + songDTO + ", playlistDTO=" + playlistDTO + ", artistDTO=" + artistDTO + "]";
	}
	
	
	
	
}
