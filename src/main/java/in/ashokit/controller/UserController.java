package in.ashokit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import in.ashokit.dto.QuoteResponseDTO;
import in.ashokit.dto.ResetPwdDTO;
import in.ashokit.dto.UserDTO;
import in.ashokit.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index(Model model) {

		UserDTO userDto = new UserDTO();
		model.addAttribute("user", userDto);

		return "index";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("user") UserDTO user, Model model) {

		UserDTO login = userService.login(user.getEmail(), user.getPwd());

		if (login == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			return "index";
		}

		if (login.getPwdUpdated().equals("YES")) {
			// display dashboard page

			QuoteResponseDTO quotation = userService.getQuotation();
			model.addAttribute("quote", quotation);
			return "dashboard";

		} else {
			// display reset pwd page

			ResetPwdDTO resetPwd = new ResetPwdDTO();
			resetPwd.setEmail(login.getEmail());

			model.addAttribute("resetPwd", resetPwd);
			return "resetPwd";

		}
	}

	@GetMapping("/register")
	public String register(Model model) {

		UserDTO userDto = new UserDTO();
		model.addAttribute("user", userDto);

		Map<Integer, String> countriesMap = userService.getCountries();
		model.addAttribute("countries", countriesMap);

		return "register";
	}

	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable Integer countryId) {
		return userService.getStates(countryId);
	}

	@GetMapping("/cities/{stateId}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable Integer stateId) {
		return userService.getCities(stateId);
	}

}







