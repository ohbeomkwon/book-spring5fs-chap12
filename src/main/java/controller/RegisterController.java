package controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String handlerStep3(@ModelAttribute("registerRequest") @Valid RegisterRequest regReq, Errors errors) {	//커맨드객체(regReq)와 errors객체를 연결할 수 있다.
		// @Valid 어노테이션을 통해서 global validator를 적용시킬 수 있다.
		/*
		new RegisterRequestValidator().validate(regReq, errors);		// local validator로 지정하는 방법
//		위 코드를 통해서 RegisterRequest 커맨드 객체의 값이 올바른지 검사하고 그 결과를 Errors객체에 담는다.
		 */
		
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
	/*
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new RegisterRequestValidator());
//		어떤 Validator가 커맨드 객체를 검증할지를 정한다. 지금은 한개의 Valiidator가 있지만 여러개가 존재하게되면 
	}
	*/
	
}
