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

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") UserDTO user, Model model) {
		boolean emailUnique = userService.isEmailUnique(user.getEmail());
		if (emailUnique) {
			boolean register = userService.register(user);
			if (register) {
				
				
				
				model.addAttribute("smsg", "Registration Success");
			} else {
				model.addAttribute("emsg", "Registration Failed");
			}
		} else {
			model.addAttribute("emsg", "Duplicate Email Found");
		}

		Map<Integer, String> countriesMap = userService.getCountries();
		model.addAttribute("countries", countriesMap);

		return "register";
	}

	@PostMapping("/resetPwd")
	public String resetPwd(@ModelAttribute("resetPwd") ResetPwdDTO resetPwd, Model model) {

		UserDTO login = userService.login(resetPwd.getEmail(), resetPwd.getOldPwd());
		if (login == null) {
			model.addAttribute("emsg", "Old Pwd is incorrect");
			return "resetPwd";
		}

		if (resetPwd.getNewPwd().equals(resetPwd.getConfirmPwd())) {
			userService.resetPwd(resetPwd);
			QuoteResponseDTO quotation = userService.getQuotation();
			model.addAttribute("quote", quotation);
			return "dashboard";
		} else {
			model.addAttribute("emsg", "New Pwd and Confirm Pwd Not Matching");
			return "resetPwd";
		}
	}

	@GetMapping("/getQuote")
	public String getQuote(Model model) {
		QuoteResponseDTO quotation = userService.getQuotation();
		model.addAttribute("quote", quotation);
		return "dashboard";
	}

}
