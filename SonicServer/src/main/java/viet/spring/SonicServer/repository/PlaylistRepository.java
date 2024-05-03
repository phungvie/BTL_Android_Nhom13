package viet.spring.SonicServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;
import viet.spring.SonicServer.entity.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer>{
	@Query("FROM Playlist p WHERE p.name LIKE %:keyword%")
	List<Playlist> findByKeyword(@Param("keyword") String keyword);
}
