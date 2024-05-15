package viet.spring.SonicServer.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import viet.spring.SonicServer.DTO.ArtistDTO;
import viet.spring.SonicServer.DTO.PlaylistDTO;
import viet.spring.SonicServer.DTO.SongDTO;
import viet.spring.SonicServer.entity.Artist;
import viet.spring.SonicServer.entity.Playlist;
import viet.spring.SonicServer.entity.Role;
import viet.spring.SonicServer.entity.Song;
import viet.spring.SonicServer.entity.User;
import viet.spring.SonicServer.DTO.Find;
import viet.spring.SonicServer.DTO.VietMessage;
import viet.spring.SonicServer.repository.ArtistRepository;
import viet.spring.SonicServer.repository.PlaylistRepository;
import viet.spring.SonicServer.repository.RoleRepository;
import viet.spring.SonicServer.repository.SongRepository;
import viet.spring.SonicServer.repository.UserRepository;
import viet.spring.SonicServer.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/sonic")
public class sonicController {
	UserService userService;
	UserRepository userR;
	PlaylistRepository playlistR;
	ArtistRepository artistR;
	SongRepository songR;
	RoleRepository roleRepository;

	// lấy các nghệ sĩ từ thư viện của người dùng
	@GetMapping("/lib/artists")
	public ResponseEntity<List<ArtistDTO>> getLibArtist(Principal vietdz) {
		User viet = userService.findByUsername(vietdz.getName()).orElse(null);
		if (viet != null) {
			// Kiểm tra nếu LibraryArtist không null trước khi trả về
			if (viet.getLibraryArtist() != null) {

				return ResponseEntity.ok().body(viet.getLibraryArtist().stream().map(t -> new ArtistDTO(t)).toList());

			} else {
				// Trả về 404 Not Found nếu không tìm thấy LibraryArtist
				return ResponseEntity.notFound().build();
			}
		} else {
			// Trả về 404 Not Found nếu không tìm thấy người dùng
			return ResponseEntity.notFound().build();
		}
	}

	// lấy các danh sách phát từ thư viện của người dùng
	@GetMapping("/lib/playlists")
	public ResponseEntity<List<PlaylistDTO>> getLibPlaylist(Principal vietdz) {
		User viet = userService.findByUsername(vietdz.getName()).orElse(null);
		if (viet != null) {
			// Kiểm tra nếu LibraryArtist không null trước khi trả về
			if (viet.getLibraryPlaylist() != null) {
				return ResponseEntity.ok()
						.body(viet.getLibraryPlaylist().stream().map(t -> new PlaylistDTO(t)).toList());

			} else {
				// Trả về 404 Not Found nếu không tìm thấy LibraryArtist
				return ResponseEntity.notFound().build();
			}
		} else {
			// Trả về 404 Not Found nếu không tìm thấy người dùng
			return ResponseEntity.notFound().build();
		}
	}

	// thêm nghệ sĩ vào thư viện của người dùng
	@PostMapping("/lib/addArtist/{id}")
	public ResponseEntity<VietMessage> addArtistLib(@PathVariable(name = "id") Integer id, Principal vietdz) {
		User viet = userService.findByUsername(vietdz.getName()).orElse(null);
		if (viet != null) {
			Artist artist = artistR.findById(id).orElse(null);

			if (artist != null) {
				viet.getLibraryArtist().add(artist);
				userR.save(viet);
				return ResponseEntity.ok().body(new VietMessage(200, "Đã thêm nghệ sĩ vào thư viện"));
			} else {
				// Trả về 404 Not Found nếu không tìm thấy artist
				return ResponseEntity.notFound().build();
			}

		} else {
			// Trả về 404 Not Found nếu không tìm thấy người dùng
			return ResponseEntity.notFound().build();
		}
	}

	// thêm danh sách phát vào thư viện người dùng
	@PostMapping("/lib/addPlaylist/{id}")
	public ResponseEntity<VietMessage> addPlaylistLib(@PathVariable(name = "id") Integer id, Principal vietdz) {
		User viet = userService.findByUsername(vietdz.getName()).orElse(null);
		if (viet != null) {
			Playlist playlist = playlistR.findById(id).orElse(null);
			if (playlist != null) {
				viet.getLibraryPlaylist().add(playlist);
				userR.save(viet);
				return ResponseEntity.ok().body(new VietMessage(200, "Đã thêm playlist vào thư viện"));

			} else {
				// Trả về 404 Not Found nếu không tìm thấy playlist
				return ResponseEntity.notFound().build();
			}

		} else {
			// Trả về 404 Not Found nếu không tìm thấy người dùng
			return ResponseEntity.notFound().build();
		}
	}
	// xóa danh sách phát từ thư viện người dùng
	@DeleteMapping("/lib/deletePlaylist/{id}")
	public ResponseEntity<VietMessage> deletePlaylistLib(@PathVariable(name = "id") Integer id, Principal vietdz) {
		User viet = userService.findByUsername(vietdz.getName()).orElse(null);
		if (viet != null) {
			Playlist playlist = playlistR.findById(id).orElse(null);
			if (playlist != null) {
				viet.getLibraryPlaylist().remove(playlist);
				userR.save(viet);
				return ResponseEntity.ok().body(new VietMessage(200, "Đã xóa xóa playlist"));

			} else {
				// Trả về 404 Not Found nếu không tìm thấy playlist
				return ResponseEntity.notFound().build();
			}

		} else {
			// Trả về 404 Not Found nếu không tìm thấy người dùng
			return ResponseEntity.notFound().build();
		}
	}

	// xóa các nghệ sĩ từ thư viện người dùng
	@DeleteMapping("/lib/deleteArtist/{id}")
	public ResponseEntity<VietMessage> deleteArtistLib(@PathVariable(name = "id") Integer id, Principal vietdz) {
		User viet = userService.findByUsername(vietdz.getName()).orElse(null);
		if (viet != null) {
			Artist artist = artistR.findById(id).orElse(null);
			if (artist != null) {
				viet.getLibraryArtist().remove(artist);
				userR.save(viet);
				return ResponseEntity.ok().body(new VietMessage(200, "Đã xóa nghệ sĩ"));

			} else {
				// Trả về 404 Not Found nếu không tìm thấy playlist
				return ResponseEntity.notFound().build();
			}

		} else {
			// Trả về 404 Not Found nếu không tìm thấy người dùng
			return ResponseEntity.notFound().build();
		}
	}

	// check xem danh sách phát đã có trong thư viện hay chưa
	@GetMapping("/lib/checkPlaylist/{id}")
	public ResponseEntity<Boolean> checkPlaylistLib(@PathVariable Integer id, Principal vietdz) {
		User viet = userService.findByUsername(vietdz.getName()).orElse(null);
		Playlist playlist=playlistR.findById(id).orElse(null);
		if(playlist!=null) {
			return ResponseEntity.ok().body(viet.getLibraryPlaylist().contains(playlist));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// check xem nghệ sĩ đã có trong thư viện hay chưa
	@GetMapping("/lib/checkArtist/{id}")
	public ResponseEntity<Boolean> checkArtistLib(@PathVariable Integer id, Principal vietdz) {
		User viet = userService.findByUsername(vietdz.getName()).orElse(null);
		Artist artist=artistR.findById(id).orElse(null);
		if(artist!=null) {
			return ResponseEntity.ok().body(viet.getLibraryArtist().contains(artist));
		}else {
			return ResponseEntity.notFound().build();
		}
	}

	// lấy danh sách bài hát từ một danh sách phát cụ thể {id}
	@GetMapping("/playlist/{id}/songs")
	public ResponseEntity<List<SongDTO>> getSongsInPlaylist(@PathVariable(name = "id") Integer id) {
		Playlist playlist = playlistR.findById(id).orElse(null);
		if (playlist != null) {
			List<Song> songs = playlist.getSongs();
			List<SongDTO> songsDTO = songs.stream().map(t -> new SongDTO(t)).toList();
			return ResponseEntity.ok().body(songsDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// lấy danh sách bài hát từ một nghệ sĩ cụ thể {id}
	@GetMapping("/artist/{id}/songs")
	public ResponseEntity<List<SongDTO>> getSongsOfArtist(@PathVariable(name = "id") Integer id) {
		Artist artist = artistR.findById(id).orElse(null);
		if (artist != null) {
			List<Song> songs = artist.getSongs();
			List<SongDTO> songsDTO = songs.stream().map(t -> new SongDTO(t)).toList();
			return ResponseEntity.ok().body(songsDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	// Người dùng đang đăng nhập tạo một danh sách phát mới
	@PostMapping("/user/addPlaylist")
	public ResponseEntity<VietMessage> addPlaylists(@RequestBody PlaylistDTO playlistDTO, Principal vietdz) {
		if (playlistDTO == null) {
			return ResponseEntity.badRequest().body(new VietMessage(400, "Đối tượng playlistDTO là rỗng"));
		} else {
			Playlist playlist = new Playlist(playlistDTO);
			User user = userService.findByUsername(vietdz.getName()).orElse(null);
			if (user == null) {
				return ResponseEntity.badRequest().body(new VietMessage(400, "Không tìm thấy người dùng khởi tạo playlistDTO"));
			} else {
				playlist.setUser(user);
				playlistR.save(playlist);
				return ResponseEntity.ok().body(new VietMessage(200, "Thêm songDTO thành công"));
			}

		}
	}
	
	@GetMapping("/checkPremium")
	public ResponseEntity<Boolean> checkPremium(Principal vietdz) {
		Role role= roleRepository.findById(4).orElse(null);
		User viet=userService.findByUsername(vietdz.getName()).orElse(null);
		if(role!=null&&viet!=null) {
			if(viet.getRoles().contains(role)) {
				return ResponseEntity.ok().body(true);
			}else {
				return ResponseEntity.ok().body(false);
			}
		}
		return ResponseEntity.ok().body(false);
	}
	
	
	//lấy tất cả danh sách các danh sách phát
	@GetMapping("/user/playlists")
	public ResponseEntity<List<PlaylistDTO>> getAllPlaylists() {
		List<PlaylistDTO> list = playlistR.findAll().stream().map(t -> new PlaylistDTO(t)).toList();
		return ResponseEntity.ok().body(list);
	}

	//lấy tất cả danh sách các nghệ sĩ
	@GetMapping("/user/artists")
	public ResponseEntity<List<ArtistDTO>> getAllArtists() {
		
		List<ArtistDTO> list = artistR.findAll().stream().map(t -> new ArtistDTO(t)).toList();
		return ResponseEntity.ok().body(list);
	}


	// thêm một nghệ sĩ
	@PostMapping("/amdin/addArtist")
	public ResponseEntity<VietMessage> addArtist(@RequestBody ArtistDTO artistDTO) {
		if (artistDTO == null) {
			return ResponseEntity.badRequest().body(new VietMessage(400, "Đối tượng artistDTO là rỗng"));
		} else {
			Artist artist = new Artist(artistDTO);
			artistR.save(artist);
			return ResponseEntity.ok().body(new VietMessage(200, "Thêm artistDTO thành công"));
		}

	}

	// thêm một bài hát với id của nghệ sĩ sáng tác bài hát đó
	@PostMapping("/admin/addSong/{id}")
	public ResponseEntity<VietMessage> addSong(@RequestBody SongDTO songDTO, @PathVariable(name = "id") Integer id) {
		if (songDTO == null) {
			return ResponseEntity.badRequest().body(new VietMessage(400, "Đối tượng songDTO là rỗng"));
		} else {
			Song song = new Song(songDTO);
			Artist artist = artistR.findById(id).orElse(null);
			if (artist == null) {
				return ResponseEntity.badRequest()
						.body(new VietMessage(400, "Không tìm thấy nghệ sĩ. Nếu chưa thêm nghệ sĩ hãy thêm nghệ sĩ trước"));
			} else {
				song.setArtist(artist);
				songR.save(song);
				return ResponseEntity.ok().body(new VietMessage(200, "Thêm songDTO thành công"));
			}

		}

	}

@GetMapping("/findByKeyword")
public ResponseEntity<List<Find>> findByKeyword(@RequestParam String keyword) {
//	List<Find>findList1= artistR.findByKeyword(keyword).stream().limit(2).map(t -> new ArtistDTO(t)).map(t->new Find(t)).toList();
//	List<Find>findList2=songR.findByKeyword(keyword).stream().limit(2).map(t -> new SongDTO(t)).map(t -> new Find(t)).toList();
//	List<Find>findList3=playlistR.findByKeyword(keyword).stream().limit(2).map(t -> new PlaylistDTO(t)).map(t -> new Find(t)).toList();
	List<Find>findList1= artistR.findByKeyword(keyword).stream().map(t -> new ArtistDTO(t)).map(t->new Find(t)).toList();
	List<Find>findList2=songR.findByKeyword(keyword).stream().map(t -> new SongDTO(t)).map(t -> new Find(t)).toList();
	List<Find>findList3=playlistR.findByKeyword(keyword).stream().map(t -> new PlaylistDTO(t)).map(t -> new Find(t)).toList();
	
	List<Find>vietdz=new ArrayList<>();
	vietdz.addAll(findList1);
	vietdz.addAll(findList2);
	vietdz.addAll(findList3);
	
    return ResponseEntity.ok().body(vietdz);
}


}
