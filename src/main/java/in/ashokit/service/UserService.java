package in.ashokit.service;

import java.util.Map;

import in.ashokit.dto.QuoteResponseDTO;
import in.ashokit.dto.ResetPwdDTO;
import in.ashokit.dto.UserDTO;

public interface UserService {

	public UserDTO login(String email, String pwd);

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public boolean isEmailUnique(String email);

	public boolean register(UserDTO userDto);

	public boolean resetPwd(ResetPwdDTO resetPwdDto);

	public QuoteResponseDTO getQuotation();

}
