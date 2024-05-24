package viet.spring.SonicServer.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import viet.spring.SonicServer.entity.User;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {
	
	


	private Integer userID;
	private String name;
	private String phoneNumber;
	private String mail;
	private Date dateOfBirth;
	private String country;
	private String img;
	
	private Integer followers;
	private Integer following;
	public UserDTO(User viet) {
		this.userID = viet.getUserID();
		this.name = viet.getName();
		this.phoneNumber = viet.getPhoneNumber();
		this.mail = viet.getMail();
		this.dateOfBirth = viet.getDateOfBirth();
		this.country = viet.getCountry();
		this.img = viet.getImg();
		this.followers = viet.getFollowers();
		this.following = viet.getFollowers();
	}

}
