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
	
/** NEW BADGE REQUEST OBJECTS**/
	
	String selfServiceSearchUserTxt = ".//label[@data-ref='placeholderLabel' and text()='Search Identity or User']//preceding-sibling::div//input";
	String selfServiceCardStatusTxt = ".//input[@placeholder='Select Status']";
	String selfServiceCardInactiveDateTxt = ".//input[contains(@id,'baseDateTime') and @placeholder='Enter inactive date']";
	String selfServiceCardRequestReasonTxt = ".//input[@data-ref='inputEl' and @placeholder='Enter card request reason']";
	
	/** NEW BADGE APPROVAL OBJECTS**/
	String selfServiceSelectBadgeDdn = ".//td/div[contains(@class,'x-grid-cell-inner') and text()='Select Badge']";
	String selfServiceSaveBtn = ".//*[@class='x-btn-icon-el x-btn-icon-el-aebtnSmallPrimary-small aegrid-save ']//ancestor::a[@role='button']";
	String selfServicePrintBtn = ".//div[@role='button' and @data-qtip='Print']";
	String selfServiceBadgeTemplateTxt = ".//input[@role='combobox' and @name='template']";
	String selfServiceBadgeConfirmBtn = ".//*[@data-ref='btnInnerEl' and text()='Confirm']";
	String selfServiceBadgeCancelBtn = ".//*[@data-ref='btnInnerEl' and text()='Confirm']//ancestor::div[@class='x-box-target']//span[@data-ref='btnInnerEl' and text()='Cancel']";
	
	
	String selfServiceSelectRequestTypeTxt = ".//input[contains(@id,'baseComboBoxRemote') and @placeholder='Select Request Type']";
	
	String selfServiceSelectCardDdn = ".//input[@placeholder='Select Asset to replace']//following::div[contains(@id,'baseComboBoxRemote') and contains(@class,'x-form-trigger x-form-trigger-default x-form-arrow-trigger x-form-arrow-trigger-default')]";
	String selfServiceCardValueLbl = "(//span//following::b[text()='Code']//following::div//following::span[1])[1]";
	String selfServiceReasonForReplaceTxt = "//input[@placeholder='Enter Reason']";
	String selfServiceSelectStatusDdn = "//input[@placeholder= 'Select Status']//following::div[1]";
	String selfServiceSelectStatusTxt = ".//input[contains(@id,'baseComboBox') and @placeholder='Select Status']";

	
	
}