package ObjectRepository;

import CommonClassReusables.ByAttribute;

public interface HomeObjects {

	/** Home **/
	String homeTabLnk =".//*[@id='tabmenu']//span[contains(text(),'Home')]";
	
	String homeWorkItemsForApprovalTbl = ".//table[@class='ui-iggrid-headertable']";
	
	String homeApplicantPreEnrollmentApproveBtn = ".//button[@id='message' and @class='approveAlert mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange ng-binding']";

	String homeGoBackToInboxBtn = ".//button[text()='Go Back To Inbox']";
	
}












