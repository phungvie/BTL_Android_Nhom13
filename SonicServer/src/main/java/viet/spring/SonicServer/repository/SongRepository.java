package viet.spring.SonicServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;
import viet.spring.SonicServer.entity.Song;

public interface SongRepository extends JpaRepository<Song, Integer>{
	@Query("FROM Song AS s WHERE s.name LIKE %:keyword%")
	List<Song> findByKeyword(@Param("keyword") String keyword);

}
