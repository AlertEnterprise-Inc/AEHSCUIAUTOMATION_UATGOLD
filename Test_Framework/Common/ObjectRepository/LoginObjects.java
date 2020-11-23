package ObjectRepository;

public interface LoginObjects {

	// ******************************************************************//
		// **********************Login controls*********************//
	

	String loginUsernameTxt = ".//*[@name='signinpod:userName']";
	
	String loginPasswordTxt = ".//*[@name='signinpod:password']";
	
	String loginSigninBtn = ".//*[@name='signinpod:logIn']";
	
	String loginRemberMeChk = ".//*[@id='rememberMe']";
	
	String loginTimeZoneDdn = ".//*[@class='select-control' and @name='signinpod:tzDropdown']";
	
	
	// **********************Self Service Login controls*********************//
	
	String selfServiceSigninWithOKTABtn = ".//a/button/span[text()='SIGN IN WITH OKTA']";
	String selfServiceOKTAUsernameTxt = ".//input[@id='okta-signin-username']";
	String selfServiceOKTAPasswordTxt = ".//input[@id='okta-signin-password']";
	String selfServiceOKTASigninBtn = ".//input[@id='okta-signin-submit']";
	
	
}
