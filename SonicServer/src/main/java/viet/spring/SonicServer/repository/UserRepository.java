package viet.spring.SonicServer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import viet.spring.SonicServer.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public abstract Optional<User> findByMail(String username);
	public abstract Optional<User> findByPhone(String username);
}
