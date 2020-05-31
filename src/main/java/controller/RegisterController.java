package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@Controller
public class RegisterController {
	
	private MemberRegisterService memberRegisterService;
	
	public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	}

	@RequestMapping("/register/step1")
	public String handlerStep1() {
		return "register/step1";
	}

	@PostMapping("/register/step2")
	public String handlerStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree, Model model) {
		if (!agree) {
			return "register/step1";
		}
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}
	
	@GetMapping("/register/step2")
	public String handlerStep2Get() {
		return "redirect:/register/step1";
	}
	
	@PostMapping("/register/step3")
	public String handlerStep3(RegisterRequest regReq, Errors errors) {	//커맨드객체(regReq)와 errors객체를 연결할 수 있다.
//		@ModelAttribute("formData") 어노테이션을 통해서 RegisterRequest 객체가 받는 model의 이름을 변경할 수 있다.
		new RegisterRequestValidator().validate(regReq, errors);
//		위 코드를 통해서 RegisterRequest 커맨드 객체의 값이 올바른지 검사하고 그 결과를 Errors객체에 담는다.
		if(errors.hasErrors()) {
			return "register/step2";
		}
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (DuplicateMemberException e) {
			errors.rejectValue("email", "duplicate");
			return "register/step2";
		}
	}
	
}
