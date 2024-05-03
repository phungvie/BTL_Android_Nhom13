package viet.spring.SonicServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.lettuce.core.dynamic.annotation.Param;
import viet.spring.SonicServer.entity.Playlist;
import viet.spring.SonicServer.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	@Query("SELECT p FROM Role p WHERE p.name LIKE %:keyword%")
	List<Role> findByKeyword(@Param("keyword") String keyword);
}
