package ObjectRepository;

public interface SelfServiceObjects
{	
	
	/** HOME PAGE OBJECTS**/
	
	String selfServiceCreateApplicantInviteByECMULnk = ".//mat-panel-title[text()=' Applicant Invitation Group ']/ancestor::mat-expansion-panel//a[text()='Create Applicant Invite ']";
	String selfServiceApplicantInvitationByASPanelLnk = ".//mat-panel-title[text()=' Applicant Invitation by AS ']";
	String selfServiceCreateApplicantInviteByASLnk = ".//mat-panel-title[text()=' Applicant Invitation by AS ']/ancestor::mat-expansion-panel//a[text()='Initiate New Badge Application for Applicant ']";
	
	
	/** APPLICANT INVITE PAGE OBJECTS**/
	
	String selfServiceDuplicateCheckLbl = ".//h3[text()='Create Applicant Invite - Duplicate Check ']";
	String selfServiceDupDOBTxt = ".//h3[text()='Create Applicant Invite - Duplicate Check ']/following-sibling::mat-dialog-content//input[@id='DateOfBirth']";
	String selfServiceDupSSNTxt = ".//h3[text()='Create Applicant Invite - Duplicate Check ']/following-sibling::mat-dialog-content//input[@id='SSN_by_AS']";
	String selfServiceDupContinueBtn = ".//span[text()='Continue']/parent::button";
	String selfServiceDupCancelBtn = ".//span[text()='Cancel']/parent::button";
	String selfServiceCreateApplicantInvitationEmailTxt = ".//input[@id='Email']";
	String selfServiceCreateApplicantInvitationCitizenshipTypeDdn = ".//*[@id='Citizenship_Type']";
	String selfServiceCreateApplicantInvitationFirstNameTxt = ".//input[@id='FirstName']";
	String selfServiceCreateApplicantInvitationLastNameTxt = ".//input[@id='LastName']";
	String selfServiceCreateApplicantInvitationContactPhoneTxt = ".//input[@id='Contact Phone']";
	String selfServiceCreateApplicantInvitationSSNTxt = ".//input[@id='SSN']";
	String selfServiceCreateApplicantInvitationSSNConfirmationTxt = ".//input[@id='SSN Confirmation']";
	String selfServiceCreateApplicantInvitationAirportDdn = ".//*[@id='Airport']";
	String selfServiceCreateApplicantInvitationOrganizationTxt = ".//input[@id='Organization']";
	String selfServiceCreateApplicantInvitationBadgeValidToTxt = ".//input[@id='Badge_Valid_To' and @ng-reflect-disabled='false']";
	String selfServiceCreateApplicantInvitationIsAuthSignatoryDdn = ".//*[@id='ISAuthorizedSigner']";
	String selfServiceCreateApplicantInvitationJobRoleTxt = ".//input[@id='jobRole']";
	String selfServiceCreateApplicantInvitationBadgeTypeDdn = ".//mat-select[@id='RequestBadgeType']";
	String selfServiceCreateApplicantInvitationSubmitBtn = ".//span[@class='mat-button-wrapper' and text()=' Submit ']/parent::button";
	String selfServiceCreateApplicantInvitationConfirmationMessageLbl = ".//mat-dialog-content[contains(text(),'Application Submitted Successfully with request number:')]";
	String selfServiceCreateApplicantInvitationOKBtn = ".//span[@class='mat-button-wrapper' and text()='Ok']/parent::button";
	
	String selfServiceModifyApplicantSubmitBtn = ".//span[@class='mat-button-wrapper' and text()=' Submit ']/parent::button";
	String selfServiceModifyApplicantSubmitOKBtn = ".//span[@class='mat-button-wrapper' and text()='Submit']/parent::button";
	
	
}