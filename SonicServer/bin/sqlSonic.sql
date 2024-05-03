-- --------------------------------------------------------
-- Máy chủ:                      127.0.0.1
-- Server version:               10.4.28-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Phiên bản:           12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for sonic
CREATE DATABASE IF NOT EXISTS `sonic` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `sonic`;

-- Dumping structure for table sonic.album
CREATE TABLE IF NOT EXISTS `album` (
  `album_id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `release_time` datetime(6) DEFAULT NULL,
  `artist_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`album_id`),
  KEY `FKmwc4fyyxb6tfi0qba26gcf8s1` (`artist_id`),
  CONSTRAINT `FKmwc4fyyxb6tfi0qba26gcf8s1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.album: ~0 rows (approximately)

-- Dumping structure for table sonic.artist
CREATE TABLE IF NOT EXISTS `artist` (
  `artist_id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`artist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.artist: ~4 rows (approximately)
INSERT INTO `artist` (`artist_id`, `country`, `date_of_birth`, `image`, `name`) VALUES
	(1, NULL, NULL, '/data/img/son_tung_6-160862605540810660.jpg', 'Sơn Tùng M-TP'),
	(2, NULL, NULL, '/data/img/222214_phuonglytrolaigoicamtruongthanhvoiamnhac1206.jpeg', 'Phương Ly'),
	(3, NULL, NULL, '/data/img/bich_phuong.7ab4f334-5994-461d-9e58-4dbfb763c619.png', 'Bích Phương'),
	(4, NULL, NULL, '/data/img/JustaTee.1453715057717.jpg', 'JustaTee');

-- Dumping structure for table sonic.comment
CREATE TABLE IF NOT EXISTS `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_time` datetime(6) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `reply_id` int(11) DEFAULT NULL,
  `song_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `UK_5qo83tosgx0p9ayujl1y87502` (`reply_id`),
  KEY `FKbkwibkxkhbevo3yg3aoxh3vmy` (`song_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKaux3hl25n3q64ww0612uk786n` FOREIGN KEY (`reply_id`) REFERENCES `comment` (`comment_id`),
  CONSTRAINT `FKbkwibkxkhbevo3yg3aoxh3vmy` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.comment: ~0 rows (approximately)

-- Dumping structure for table sonic.lib_artist
CREATE TABLE IF NOT EXISTS `lib_artist` (
  `user_id` int(11) NOT NULL,
  `artist_id` int(11) NOT NULL,
  UNIQUE KEY `UK91h2k51vts43hjsv8qt2flhc3` (`user_id`,`artist_id`),
  KEY `FK4ugey7vjark1ceyr9iyupbnyx` (`artist_id`),
  CONSTRAINT `FK4ugey7vjark1ceyr9iyupbnyx` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`),
  CONSTRAINT `FKikfiydutejqdc9uuk2n6mpyo2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.lib_artist: ~2 rows (approximately)
INSERT INTO `lib_artist` (`user_id`, `artist_id`) VALUES
	(1, 1),
	(1, 2);

-- Dumping structure for table sonic.lib_playlist
CREATE TABLE IF NOT EXISTS `lib_playlist` (
  `user_id` int(11) NOT NULL,
  `playlist_id` int(11) NOT NULL,
  UNIQUE KEY `UK62j7jpfbr7vvijqnfsp7uif5f` (`user_id`,`playlist_id`),
  KEY `FK43sx6s26donm2cr5rdaa9tjrb` (`playlist_id`),
  CONSTRAINT `FK43sx6s26donm2cr5rdaa9tjrb` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`playlist_id`),
  CONSTRAINT `FKc4axyf067di775n56uvxjd937` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.lib_playlist: ~1 rows (approximately)
INSERT INTO `lib_playlist` (`user_id`, `playlist_id`) VALUES
	(1, 1);

-- Dumping structure for table sonic.like_sonic
CREATE TABLE IF NOT EXISTS `like_sonic` (
  `song_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  UNIQUE KEY `UK356kgefmwxuuhc2tfugepa5wx` (`song_id`,`user_id`),
  KEY `FKc5vcbtd24nsqow8r162fmfnue` (`user_id`),
  CONSTRAINT `FKc5vcbtd24nsqow8r162fmfnue` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKo41m3mbmixpc0l0p2cygik3eb` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.like_sonic: ~0 rows (approximately)

-- Dumping structure for table sonic.persistent_logins
CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `series` varchar(255) NOT NULL,
  `last_used` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.persistent_logins: ~0 rows (approximately)

-- Dumping structure for table sonic.playlist
CREATE TABLE IF NOT EXISTS `playlist` (
  `playlist_id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`playlist_id`),
  KEY `FKlbi6wsq41356go2ki0yirfixo` (`user_id`),
  CONSTRAINT `FKlbi6wsq41356go2ki0yirfixo` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.playlist: ~2 rows (approximately)
INSERT INTO `playlist` (`playlist_id`, `image`, `name`, `user_id`) VALUES
	(1, '/data/img/son-tung-mmw-2824-1683249980.jpg', 'Tập tất cả bài hát', 1),
	(2, '/data/img/bich_phuong.7ab4f334-5994-461d-9e58-4dbfb763c619.png', 'Tập các bài hát của Bích Phương', 1);

-- Dumping structure for table sonic.playlist_song
CREATE TABLE IF NOT EXISTS `playlist_song` (
  `playlist_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  UNIQUE KEY `UKd13qlyisrgsa4l4e5aip5uu0e` (`song_id`,`playlist_id`),
  KEY `FKji5gt6i2hcwyt9x1fcfndclva` (`playlist_id`),
  CONSTRAINT `FK8l4jevlmxwsdm3ppymxm56gh2` FOREIGN KEY (`song_id`) REFERENCES `song` (`song_id`),
  CONSTRAINT `FKji5gt6i2hcwyt9x1fcfndclva` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`playlist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.playlist_song: ~10 rows (approximately)
INSERT INTO `playlist_song` (`playlist_id`, `song_id`) VALUES
	(1, 1),
	(2, 1),
	(1, 2),
	(1, 3),
	(1, 4),
	(1, 5),
	(1, 6),
	(2, 6),
	(1, 7),
	(1, 8);

-- Dumping structure for table sonic.role
CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.role: ~3 rows (approximately)
INSERT INTO `role` (`role_id`, `name`) VALUES
	(1, 'USER'),
	(2, 'ADMIN'),
	(3, 'AUTHENTICATED');

-- Dumping structure for table sonic.song
CREATE TABLE IF NOT EXISTS `song` (
  `song_id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `release_time` datetime(6) DEFAULT NULL,
  `sound` varchar(255) DEFAULT NULL,
  `album_id` int(11) DEFAULT NULL,
  `artist_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`song_id`),
  KEY `FKrcjmk41yqj3pl3iyii40niab0` (`album_id`),
  KEY `FKa21ft97nj7thwrp5d31xdaxr` (`artist_id`),
  CONSTRAINT `FKa21ft97nj7thwrp5d31xdaxr` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`),
  CONSTRAINT `FKrcjmk41yqj3pl3iyii40niab0` FOREIGN KEY (`album_id`) REFERENCES `album` (`album_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.song: ~8 rows (approximately)
INSERT INTO `song` (`song_id`, `image`, `name`, `release_time`, `sound`, `album_id`, `artist_id`) VALUES
	(1, '/data/img/anh1.jpg', 'Bao Giờ Lấy Chồng', NULL, '/data/stream/BaoGioLayChongMRKVT2018Remix-DJ-5385923.mp3', NULL, 3),
	(2, '/data/img/anh1.jpg', 'Hãy Trao Cho Anh', NULL, '/data/stream/HayTraoChoAnh-SonTungMTPSnoopDogg-6010660.mp3', NULL, 1),
	(3, '/data/img/anh1.jpg', 'Mặt Trời Của Em', NULL, '/data/stream/MatTroiCuaEmKynbbRemix-JustaTeePhuongLy-5290457.mp3', NULL, 2),
	(4, '/data/img/anh1.jpg', 'Một Năm Mới Bình An', NULL, '/data/stream/MotNamMoiBinhAn-SonTungMTP-4315569.mp3', NULL, 1),
	(5, '/data/img/anh1.jpg', 'Nơi Này Có Anh', NULL, '/data/stream/NoiNayCoAnhTropicalHouseRemix-SonTungMTP-4773696.mp3', NULL, 1),
	(6, '/data/img/anh1.jpg', 'Chuyện Cũ Bỏ Qua', NULL, '/data/stream/ChuyenCuBoQua_pyelss0ioh.mp3', NULL, 3),
	(7, '/data/img/anh1.jpg', 'Từ Chối Nhẹ Nhàng Thôi', NULL, '/data/stream/TuChoiNheNhangThoi-BichPhuongPhucDu-6281296.mp3', NULL, 3),
	(8, '/data/img/anh1.jpg', 'Thằng Điên', NULL, '/data/stream/ThangDienLive-JustaTeePhuongLy-6066987.mp3', NULL, 4);

-- Dumping structure for table sonic.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `sign_up_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.user: ~1 rows (approximately)
INSERT INTO `user` (`user_id`, `country`, `date_of_birth`, `followers`, `following`, `img`, `mail`, `name`, `otp`, `password`, `phone`, `sign_up_date`) VALUES
	(1, NULL, NULL, NULL, NULL, NULL, 'sonic', 'Sonic Admin', NULL, '$2a$10$yYFLfyG4KUvjS59Bx7KWkuJZh0M08WLHgejROupvmn9zyXsi3s32K', NULL, NULL);

-- Dumping structure for table sonic.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  UNIQUE KEY `UK872xec3woupu3gw59b04pj3sa` (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table sonic.user_role: ~3 rows (approximately)
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
	(1, 1),
	(1, 2),
	(1, 3);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
