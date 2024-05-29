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
		Optional<User> phoneNumber = userR.findByPhoneNumber(userName);
		if (mail.isEmpty() && phoneNumber.isEmpty()) {
			return Optional.empty();
		} else {
			if (mail.isPresent()) {
				return mail;
			} else {
				return phoneNumber;
			}
		}
	}
}
