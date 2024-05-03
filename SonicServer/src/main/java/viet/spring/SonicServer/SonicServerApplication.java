package viet.spring.SonicServer;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import viet.spring.SonicServer.config.Config;
import viet.spring.SonicServer.entity.Artist;
import viet.spring.SonicServer.entity.Playlist;
import viet.spring.SonicServer.entity.Role;
import viet.spring.SonicServer.entity.Song;
import viet.spring.SonicServer.entity.User;
import viet.spring.SonicServer.repository.ArtistRepository;
import viet.spring.SonicServer.repository.PlaylistRepository;
import viet.spring.SonicServer.repository.RoleRepository;
import viet.spring.SonicServer.repository.SongRepository;
import viet.spring.SonicServer.repository.UserRepository;

@SpringBootApplication
@AllArgsConstructor
public class SonicServerApplication implements CommandLineRunner {
	UserRepository userR;
	RoleRepository roleR;
	BCryptPasswordEncoder encoder;
	ArtistRepository artistR;
	SongRepository songR;
	PlaylistRepository playlistR;

	public static void main(String[] args) {
		SpringApplication.run(SonicServerApplication.class, args);
//		System.out.println(Config.getRandomNumber(8));

	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {


////Các quyền
//		Role role0 = Role.builder().name("USER").build();
//		Role role1 = Role.builder().name("ADMIN").build();
//		Role role2 = Role.builder().name("AUTHENTICATED").build();
//		Role role3 = Role.builder().name("PREMIUM").build();
//		roleR.saveAll(() -> Lists.newArrayList(role0, role1, role2,role3).iterator());
////
//		
////Người dùng
//		User sonic = User.builder().name("Sonic Admin").password(encoder.encode("123")).mail("sonic1").build();
//		sonic.setRoles(Lists.newArrayList(role0, role1, role2,role3));
//		userR.save(sonic);
//		
//		User sonic2 = User.builder().name("Sonic User").password(encoder.encode("123")).mail("sonic2").build();
//		sonic2.setRoles(Lists.newArrayList(role0, role2));
//		userR.save(sonic2);
//		
//		User sonic3 = User.builder().name("Sonic User Premium").password(encoder.encode("123")).mail("sonic3").build();
//		sonic3.setRoles(Lists.newArrayList(role0, role2,role3));
//		userR.save(sonic3);
////
//
////Nhạc sĩ		
//		Artist artistSonTung = Artist.builder().name("Sơn Tùng M-TP").image("/data/img/son_tung_6-160862605540810660.jpg").build();
//		Artist artistPhuongLy = Artist.builder().name("Phương Ly").image("/data/img/222214_phuonglytrolaigoicamtruongthanhvoiamnhac1206.jpeg").build();
//		Artist artistBichPhuong = Artist.builder().name("Bích Phương").image("/data/img/bich_phuong.7ab4f334-5994-461d-9e58-4dbfb763c619.png").build();
//		Artist artistJustaTee = Artist.builder().name("JustaTee").image("/data/img/JustaTee.1453715057717.jpg").build();
//		Artist artistSiro = Artist.builder().name("Mr.Siro").image("/data/img/Mr.Siro.1505103180911.jpg").build();
//		artistR.saveAll(
//				() -> Lists.newArrayList(artistSonTung, artistPhuongLy, artistBichPhuong, artistJustaTee,artistSiro).iterator());
////
//		
////Bài hát
//		Song songBGLC = Song.builder().name("Bao Giờ Lấy Chồng").build();// Bích Phương
//		songBGLC.setSound("/data/stream/BaoGioLayChongMRKVT2018Remix-DJ-5385923.mp3");
//		songBGLC.setImage("/data/img/anh1.jpg");
//		songBGLC.setArtist(artistBichPhuong);
//
//		Song songHTCA = Song.builder().name("Hãy Trao Cho Anh").build();
//		songHTCA.setSound("/data/stream/HayTraoChoAnh-SonTungMTPSnoopDogg-6010660.mp3");
//		songHTCA.setImage("/data/img/anh1.jpg");
//		songHTCA.setArtist(artistSonTung);
//
//		Song songMTCE = Song.builder().name("Mặt Trời Của Em").build();// Phương Ly
//		songMTCE.setSound("/data/stream/MatTroiCuaEmKynbbRemix-JustaTeePhuongLy-5290457.mp3");
//		songMTCE.setImage("/data/img/anh1.jpg");
//		songMTCE.setArtist(artistPhuongLy);
//
//		Song songMNMBA = Song.builder().name("Một Năm Mới Bình An").build();
//		songMNMBA.setSound("/data/stream/MotNamMoiBinhAn-SonTungMTP-4315569.mp3");
//		songMNMBA.setImage("/data/img/anh1.jpg");
//		songMNMBA.setArtist(artistSonTung);
//
//		Song songNNCA = Song.builder().name("Nơi Này Có Anh").build();
//		songNNCA.setSound("/data/stream/NoiNayCoAnhTropicalHouseRemix-SonTungMTP-4773696.mp3");
//		songNNCA.setImage("/data/img/anh1.jpg");
//		songNNCA.setArtist(artistSonTung);
//
//		Song songCCBQ = Song.builder().name("Chuyện Cũ Bỏ Qua").build();// Bích Phương
//		songCCBQ.setSound("/data/stream/ChuyenCuBoQua_pyelss0ioh.mp3");
//		songCCBQ.setImage("/data/img/anh1.jpg");
//		songCCBQ.setArtist(artistBichPhuong);
//
//		Song songTCNNT = Song.builder().name("Từ Chối Nhẹ Nhàng Thôi").build();// Bích Phương
//		songTCNNT.setSound("/data/stream/TuChoiNheNhangThoi-BichPhuongPhucDu-6281296.mp3");
//		songTCNNT.setImage("/data/img/anh1.jpg");
//		songTCNNT.setArtist(artistBichPhuong);
//
//		Song songTD = Song.builder().name("Thằng Điên").build();// JustaTee và Phương Ly
//		songTD.setSound("/data/stream/ThangDienLive-JustaTeePhuongLy-6066987.mp3");
//		songTD.setImage("/data/img/anh1.jpg");
//		songTD.setArtist(artistJustaTee);
//		
//		Song songADSVAT = Song.builder().name("Anh Đã Sai Vì Anh Tin").build();// Mr.siro
//		songADSVAT.setSound("/data/stream/AnhDaSaiViAnhTin-MrSiro-4694386.mp3");
//		songADSVAT.setImage("/data/img/anh1.jpg");
//		songADSVAT.setArtist(artistSiro);
//		
//		Song songYNKTY = Song.builder().name("Yêu Người Không Thể Yêu").build();// Mr.siro
//		songYNKTY.setSound("/data/stream/YeuNguoiKhongTheYeuCover-MrSiro-5049892.mp3");
//		songYNKTY.setImage("/data/img/anh1.jpg");
//		songYNKTY.setArtist(artistSiro);
//		
//		Song songTYCV = Song.builder().name("Tình Yêu Chắp Vá").build();// Mr.siro
//		songTYCV.setSound("/data/stream/TinhYeuChapVa-MrSiro-4350275.mp3");
//		songTYCV.setImage("/data/img/anh1.jpg");
//		songTYCV.setArtist(artistSiro);
//		
//		Song songCE = Song.builder().name("Cho Em").build();// Mr.siro
//		songCE.setSound("/data/stream/ChoEm-MrSiro-3254801.mp3");
//		songCE.setImage("/data/img/anh1.jpg");
//		songCE.setArtist(artistSiro);
//		
//
//		songR.saveAllAndFlush(
//				() -> Lists.newArrayList(
//						songBGLC, songHTCA, songMTCE, 
//						songMNMBA, songNNCA, songCCBQ, 
//						songTCNNT, songTD,songADSVAT,
//						songYNKTY,songTYCV,songCE)
//						.iterator());
//		
////		
//		
////Playlist
//		Playlist playlist1 = Playlist.builder().name("50 bài hát Việt Nam").image("/data/img/432994336_4401343820091471_2698424304376113830_n.jpg")
//				.build();
//		playlist1.setUser(sonic);
//		playlist1.setSongs(Lists.newArrayList(songHTCA, songCCBQ, songNNCA));
//		playlistR.saveAndFlush(playlist1);
//
//		
//		Playlist playlist2 = Playlist.builder().name("Đề xuất cho bạn").image("/data/img/432994336_4401343820091471_2698424304376113830_n.jpg")
//				.build();
//		playlist2.setUser(sonic);
//		playlist2.setSongs(Lists.newArrayList(songHTCA,songBGLC, songCCBQ));
//		playlistR.saveAndFlush(playlist2);
//		
//		Playlist playlist3 = Playlist.builder().name("Tập tất cả các bài hát").image("/data/img/432994336_4401343820091471_2698424304376113830_n.jpg")
//				.build();
//		playlist3.setUser(sonic);
//		playlist3.setSongs(Lists.newArrayList(songBGLC, songHTCA, songMTCE, songMNMBA, songNNCA, songCCBQ, songTCNNT, songTD));
//		playlistR.saveAndFlush(playlist3);
//		
//		Playlist playlist4 = Playlist.builder().name("Chủ đề mùa xuân").image("/data/img/432994336_4401343820091471_2698424304376113830_n.jpg")
//				.build();
//		playlist4.setUser(sonic);
//		playlist4.setSongs(Lists.newArrayList(songBGLC, songMNMBA, songCCBQ));
//		playlistR.saveAndFlush(playlist4);
//		
//		Playlist playlist5 = Playlist.builder().name("Chủ đề suy").image("/data/img/432994336_4401343820091471_2698424304376113830_n.jpg")
//				.build();
//		playlist5.setUser(sonic);
//		playlist5.setSongs(Lists.newArrayList( 
//				songTCNNT,songADSVAT,songYNKTY,
//				songTYCV,songCE));
//		playlistR.saveAndFlush(playlist5);
////
//		
////Thêm trong thư viện
//		sonic.setLibraryArtist(Lists.newArrayList(artistSonTung, artistPhuongLy));
//		sonic.setLibraryPlaylist(Lists.newArrayList(playlist1,playlist3));
//		userR.save(sonic);
		
		

		
	}

}
