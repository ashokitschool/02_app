package in.ashokit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {

	private Integer userId;
	private String name;
	private String email;
	private String pwd;
	private String pwdUpdated;
	private Long phno;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;

}
