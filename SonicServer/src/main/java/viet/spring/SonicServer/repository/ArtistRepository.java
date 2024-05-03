package viet.spring.SonicServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;
import viet.spring.SonicServer.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer>{
	@Query("FROM Artist AS a WHERE a.name LIKE %:keyword%")
	List<Artist> findByKeyword(@Param("keyword") String keyword);
}
