package viet.spring.SonicServer.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import viet.spring.SonicServer.entity.User;
import viet.spring.SonicServer.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
	private UserRepository userR;

	public Optional<User> findByUsername(String userName) {
		Optional<User> mail = userR.findByMail(userName);
		Optional<User> phone = userR.findByPhone(userName);
		if (mail.isEmpty() && phone.isEmpty()) {
			return Optional.empty();
		} else {
			if (mail.isPresent()) {
				return mail;
			} else {
				return phone;
			}
		}
	}
}
