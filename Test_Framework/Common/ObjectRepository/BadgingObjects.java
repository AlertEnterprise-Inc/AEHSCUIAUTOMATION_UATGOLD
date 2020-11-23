package ObjectRepository;

public interface BadgingObjects
{	
	String badgingTabLnk = ".//*[@id='tabmenu']//span[contains(text(),'Badging')]";
	
	/** BADGING TAB OBJECTS**/
	
	String badgingManageApplicationsLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Manage Applications']";
	String badgingBadgeAdminLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Badge Admin']";
	String badgingAreaAdminLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Area Admin']";
	String badgingViewRequestStatusLnk = ".//*[@class='podTitle ng-binding' and @tooltip='View Request Status']";
	String badgingBulkIdentityImportLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Bulk Identity Import']";
	String badgingCreateNewApplicationsLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Create New Applications']";
	String badgingManageAccessLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Manage Access']";
	String badgingKeyManagementLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Key Management']";
	String badgingAccessLevelBulkImportExportLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Access Level Bulk Import Export']";
	String badgingViewAllRequestStatusLnk = ".//*[@class='podTitle ng-binding' and @tooltip='View All Request Status']";
	String badgingGlobalIdentitySearchLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Global Identity Search']";
	String badgingAccessReviewsLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Access Reviews']";
	String badgingRoleAccessMaintenanceLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Role/Access Maintenance']";
	String badgingIssueTicketASOLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Issue Ticket ( ASO )']";
	String badgingManageCardholdersLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Manage Cardholders']";
	String badgingMaintainAuthorizedSignatoryLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Maintain Authorized Signatory']";
	String badgingTrainingCertificationsLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Training & Certifications']";
	String badgingDACSStatusLnk = ".//*[@class='podTitle ng-binding' and @tooltip='DACS Status']";
	String badgingBadgeDesignerLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Badge Designer']";
	String badgingApplicantInvitationLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Applicant Invitation']";
	String badgingWatchlistManagementLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Watchlist Management']";
	String badgingBillingManagementLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Billing Management']";
	String badgingBadgeAuditLnk = ".//*[@class='podTitle ng-binding' and @tooltip='BadgeAudit']";
	String badgingTrainingStatusOverrideLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Training Status Override']";
	String badgingTenantManagementLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Tenant Management']";
	String badgingDocumentManagementLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Document Management']";
	String badgingContactUsLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Contact Us']";
	String badgingAppointmentSchedulerLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Appointment Scheduler']";
	String badgingLogExceptionsLnk = ".//*[@class='podTitle ng-binding' and @tooltip='Log Exceptions']";
	String badgingCreateCustomCategoryLnk = ".//*[@class='podTitle ng-binding' and contains(text(),'Create Custom Category')]";
	
	String badgingPreEnrollmentLnk = ".//*[@id='linksList']//a[text()='PreEnrollment']";
	String badgingBasicInformationLbl = ".//div[@class='profile_popup_form_window']//span[text()='Basic Information']";
	
	String badgingCalendarConfigurationsLnk = ".//*[@id='linksList']//a[text()='Calendar Configurations']";
	String badgingAppointmentTypeTabLnk = ".//*[@id='calConfViews']//md-tab-item/span[text()='Appointment Type']";
	String badgingAppointmentTypeCreateBtn = ".//*[@id='appTypeBtn']";
	String badgingAppointmentTypeDetailsLbl = ".//*[@id='newAppointmentType']//label[contains(text(),'Appointment type details')]";
	
	String badgingAppointmentTypeTxt = ".//*[@id='appointmentType']";
	String badgingAppointmentSlotTxt = ".//*[@id='appointmentSlot']";
	String badgingAppointmentTypeDescriptionTxt = ".//*[@id='description']";
	String badgingAppointmentTypeLabelTxt = ".//*[@id='appointmentTypeLabel']";
	String badgingAppointmentTypeAdvDaysTxt = ".//*[@id='advDays']";
	String badgingAppointmentTypeIdentityActionTxt = ".//*[contains(@title,'Enter search criteria')]";
	String badgingAppointmentTypeSaveBtn = ".//*[@id='newTimeOffSaveBtn']";
	String badgingAppointmentTypeAddedSuccessfullyLbl = ".//h2[text()='Appointment Type added successfully']";
	String badgingAppointmentTypeOKBtn = ".//md-dialog/md-dialog-actions/button/span[text()='OK']";
	String badgingAppointmentNameFilterTxt = ".//*[@id='comp_15_headers']//tr[@data-role='filterrow']/td[1]/span/input";
	
	String badgingApplicantInvitationByASBtn = ".//*[@class='mdl-inbox-radiotext ng-binding' and text()='Applicant Invitation by AS']";
	String badgingCreateApplicantInvitationLnk = ".//*[@id='linksList']//a[text()='CreateApplicantInvitation']";
	String badgingCreateApplicantInvitationByECMULnk = ".//*[@id='linksList']//a[text()='Create Application Invite by ECMU']";
	String badgingInitiateNewBadgeApplicationLnk = ".//*[@id='linksList']/li/a[text()='Initiate New Badge Application for Applicant']";
	String badgingDuplicateCheckLbl = ".//span[contains(text(),'Duplicate Check')]";
	String badgingDupFirstNameTxt = ".//div[@class='profile_popup_form fabric_comp_form']//input[@id='FirstName' and @type='text']";
	String badgingDupLastNameTxt = ".//div[@class='profile_popup_form fabric_comp_form']//input[@id='LastName' and @type='text']";
	String badgingDupDOBTxt = ".//div[@class='profile_popup_form fabric_comp_form']//*[@id='divDateOfBirth']/span/input";
	String badgingDupSSNTxt = ".//div[@class='profile_popup_form fabric_comp_form']//input[contains(@id,'SSN')]";
	String badgingDupContinueBtn = ".//div[@class='profile_popup_form_buttons fabric_comp_buttonbar']//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange']";
	String badgingDupCancelBtn = ".//div[@class='profile_popup_form_buttons fabric_comp_buttonbar']//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect']";

	String badgingCreateApplicantInvitationCitizenshipTypeDdn = ".//*[@id='Citizenship_Type']";
	String badgingCreateApplicantInvitationFirstNameTxt = ".//*[@id='FirstName']";
	String badgingCreateApplicantInvitationLastNameTxt = ".//*[@id='LastName']";
	String badgingCreateApplicantInvitationContactPhoneTxt = ".//*[@id='Contact Phone']";
	String badgingCreateApplicantInvitationEmailTxt = ".//input[@id='Email']";
	String badgingCreateApplicantInvitationSSNTxt = ".//*[@id='SSN']";
	String badgingCreateApplicantInvitationSSNConfirmationTxt = ".//*[@id='SSN Confirmation']";
	String badgingCreateApplicantInvitationAirportDdn = ".//select[@id='Airport']";
	String badgingCreateApplicantInvitationOrganizationTxt = ".//input[@id='Organization']";
	String badgingCreateApplicantInvitationBadgeValidToTxt = ".//*[@name='Badge_Valid_To']";
	String badgingCreateApplicantInvitationBadgeTypeDdn = ".//select[@id='BadgeType']";
	String badgingCreateApplicantInvitationBadgeIconsTxt = ".//input[@title='Enter search criteria...']";
	String badgingCreateApplicantInvitationJobTitleTxt = ".//input[@id='JobTitle']";
	String badgingCreateApplicantInvitationJobRoleTxt = ".//input[@id='jobRole']";
	String badgingCreateApplicantInvitationIsAuthSignatoryDdn = ".//select[@id='ISAuthorizedSigner']";
	String badgingCreateApplicantInvitationReqAuthSignatoryChk = ".//*[@id='IsAuthorizedSigner_by_ECMU']";
	String badgingCreateApplicantInvitationSubmitBtn = ".//div[@class='fab-pod-main-content']//button[contains(@class,'Submit')]";
	String badgingCreateApplicantInvitationConfirmationMessageLbl = ".//div[@class='confirm-message-text fabric-confirmpopup-message-success' and contains(text(),'Request number is')]";
	String badgingCreateApplicantInvitationOKBtn = ".//div[@class='confirm_buttons fabric-confirmpopup-buttons']/button[text()='OK']";
	
	String badgingModifyApplicantInviteLnk = ".//*[@id='linksList']//a[text()='Modify Applicant Invite']";
	String badgingModifyApplicantInvitedApplicantsTbl = ".//mat-table[@class='mat-table']";
	String badgingModifyApplicantContinueBtn = ".//span[@class='mat-button-wrapper' and contains(text(),'Continue')]";
	String badgingModifyApplicantPreEnrolmentContinueBtn = ".//button[@class='btn btn-sm ae-bold Continue btn-warning']";
	String badgingModifyApplicantLocationRequestLevelTbl = ".//div[@class='locationRequestLevel']";
	String badgingModifyApplicantContinueSubmitBtn = ".//button[@class='btn btn-sm ae-bold Continue:Submit btn-warning']";
	
	String badgingApplicationSearchLnk = ".//*[@id='linksList']//a[text()='Application Search']";
	String badgingAppointmentsLnk = ".//*[@id='linksList']//a[text()='Appointments']";
	
	/** COMPANY MANAGEMENT OBJECTS **/
	
	String companyManagementHeaderLbl = ".//h3[text()='Company Management']";
	
	String companyCompanyNameOrCompanyCodeTxt = ".//input[@placeholder='Company Name or Company Code']";
	String companySearchBtn = ".//span[@class='fab-comp-search-icon' and @title='Search']";
	
	String companyProfileHeaderLbl = ".//h3[text()='Company Profile']";
	String companyProfileBadgeIconsTxt = ".//*[contains(@id,'inputcomp') and contains(@placeholder,'Enter search criteria')]";
	String companyProfileBadgeTypeTxt = ".//*[@id='sub-content']//div[text()='Badge Type']//parent::div//input[@title='Enter search criteria...']";
	String companyProfileNameTxt = ".//*[@id='OrgDisplayName']";
	String companyProfileCompanyDBATxt = ".//*[@id='CompanyDBA']";
	String companyProfileCompanyTypeDdn = ".//*[@id='OrgType']";
	String companyProfileOrgShortDescriptionTxt = ".//*[@id='OrgShortDescription']";
	String companyProfileCompanyWebsiteTxt = ".//*[@id='companywebsite']";
	String companyProfileStartDateTxt = ".//input[@name='OrgValidFrom']";
	String companyProfileEndDateTxt = ".//input[@name='OrgValidTo']";
	String companyProfileAddressLine1Txt = ".//*[@id='AddressLine1']";
	String companyProfileCompanyCityTxt = ".//*[@id='CompanyCity']";
	String companyProfileCompanyCountryDdn = ".//*[@id='CompanyCountry']";
	String companyProfileCompanyStateDdn = ".//*[@id='CompanyState']";
	String companyProfileCompanyPostalCodeTxt = ".//*[@id='CompanyPostalCode']";
	String companyProfileCompanyPhoneTxt = ".//*[@id='CompanyMainPhone']";
	String companyProfileUploadLogoBtn = ".//*[@id='_lbl' and text()='Upload Logo']";
	String companyProfileClickToUploadBtn = ".//*[@id='file-upload-btn-comp_55_ibb' and @title='Upload File']";
	String companyProfileSaveBtn = ".//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange save']";
	String companyProfileNextBtn = ".//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect nextLink']";
	String companyProfileCloseBtn = ".//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect _closeToLaunch']";
	String companyProfileNewContactBtn = ".//*[@id='tenantContactControllerId']/md-card//button[@aria-label='New Contact']";
	String companyProfileNewContractBtn = ".//*[@id='tenantContractControllerId']/md-card//button[@aria-label='New Contracts']";
	String companyProfileRoleDdn = ".//*[@aria-label='Role']";
	String companyProfilePrimaryPOCLbl = ".//*[@value='Primary Point of Contact']";
	String companyProfilePOCFirstNameTxt = ".//*[@id='tenantContactVm.tenantContactForm_fabric_form_textfield_firstName_0']";
	String companyProfilePOCLastNameTxt = ".//*[@id='tenantContactVm.tenantContactForm_fabric_form_textfield_lastName_0']";
	String companyProfilePOCEmailTxt = ".//*[@id='tenantContactVm.tenantContactForm_fabric_form_textfield_email_0']";
	String companyProfilePOCTitleTxt = ".//*[@id='tenantContactVm.tenantContactForm_fabric_form_textfield_title_0']";
	String companyProfilePOCWorkPhoneTxt = ".//*[@id='tenantContactVm.tenantContactForm_fabric_form_textfield_businessPhone_0']";
	String companyProfilePOCMobilePhoneTxt = ".//*[@id='tenantContactVm.tenantContactForm_fabric_form_textfield_mobilePhone_0']";
	String companyProfilePOCPrimaryContactChk = ".//*[@id='tenantContactVm.tenantContactForm_fabric_form_checkbox_primaryContact_0']//span[text()='Primary Contact']";
	String companyProfilePOCSaveBtn = ".//*[@id='tenantContactControllerId']//md-icon[@class='ae-st-icon ae-st-saveForm ng-scope material-icons']";
	String companyProfileContactSavedLbl = ".//*[@id='tenantContactControllerId']/md-card//div[text()='Contact saved successfully']";
	
	String companyProfileEditContactBtn = ".//button/md-icon[@class='md-accent ae-st-icon ae-st-edit']";
	String companyProfileECMUCoordinatorDdn = ".//*[@id='AirportCordianator']";
	
	String divisionManageDivisionHeaderLbl = ".//h3[contains(text(),'Manage Division')]";
	String divisionDivisionTypeDdn = ".//*[@id='DivisionType']";
	String divisionExemptSecurityCheckDdn = ".//*[@id='Exempt_security_check']";
	String divisionBadgeTypeDdn = ".//*[@id='BadgeType']";
	String divisionContactPodLnk = ".//*[@id='Division Contact']";
	String divisionContractPodLnk = ".//*[@id='Division Contract']";
	String divisionContractNoRecordsLbl = ".//*[@id='tenantContractControllerId']//md-sidenav//div[contains(text(),'No Record Found')]";
	String divisionContractCloseCardBtn = ".//*[@id='tenantContractControllerId']//div[@ng-click='tenantContractVm.closeRightContractBar()']";
	String divisionContractSaveBtn = ".//*[@id='tenantContractControllerId']//button/md-icon[@class='ae-st-icon ae-st-saveForm ng-scope material-icons']";
	
	String divisionJobRolesPodLnk = ".//*[@id='Division Job Positions and Locations']";
	String divisionNewJobRoleBtn = ".//*[@id='mdl-main']//button[@aria-label='Add Job Roles']";
	String divisionJobRoleSaveBtn = ".//*[@id='mdl-main']//md-sidenav[2]//div[@class='ae-st-icon ae-st-saveForm']";
	String divisionMappedLocationsNewBtn = ".//button[@ng-click='tenantJobPositionsLocationVm.changeIsNew()']";
	String divisionMappedLocationsExistingBtn = ".//button[@ng-click='tenantJobPositionsLocationVm.changeIsExisting()']";
	String divisionJobRoleMappedLocationLnk = ".//a[contains(text(),'Mapped Location')]";
	String divisionJobRoleLocationLevelsChk = ".//div[text()='AIRPORTS']//parent::div//div[@class='md-container md-ink-ripple']";
	String divisionJobRoleLocationsUpdatedSuccessfullyLbl = ".//div[text()='Locations Updated Successfully']";
	
	String searchApplicationsContinueBtn = ".//div[@class='badge_search_buttons fabric_comp_buttonbar']//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange application-continue']";
	String applicationWizardCitizenshipStatusDdn = ".//*[@id='citizenshipStatus']";
	String applicationWizardListADdn = ".//*[@id='listA_combobox']";
	String applicationWizardDocumentNumberATxt = ".//*[@id='documentNumberA']";
	String applicationWizardIssuedByATxt = ".//*[@id='issuedByA']";
	String applicationWizardDateIssuedATxt = ".//input[@name='dateIssuedA']";
	String applicationWizardExpiryDateATxt = ".//input[@name='expiryDateA']";
	String applicationWizardNextBtn = ".//div[@class='badge_wizard_buttons fabric_comp_buttonbar']//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect nextLink']";
	String applicationWizardSaveBtn = ".//div[@class='badge_wizard_buttons fabric_comp_buttonbar']//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange save']";
	String applicationWizardSavedSuccessfullyMessageLbl = ".//*[@id='errorPanel']//div[@class='fab-message-info']";
	String applicationWizardInterferenceWithFlightCrewMembersNORdb  = ".//*[@id='No' and @name='Interference_with_flight_crew_members_or_attendants']";
	String applicationWizardIsNDAAttachedDdn = ".//*[@id='isNDAAttached']";
	String applicationWizardCertifyCriminalHistoryChk = ".//*[@id='certify_criminalHistory']";
	String applicationWizardVerifySSNChk = ".//*[@id='VerifySSN']";
	
	String applicationWizardBadgeInfoPodLnk = ".//*[@id='Badge Info']";
	String applicationWizardBioMetricsPodLnk = ".//*[@id='Bio Metrics']";
	String applicationWizardNewBadgeBtn = ".//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect' and text()='New Badge']";
	String applicationWizardBadgeTypeDdn = ".//*[@id='BadgeType']";
	String applicationWizardBadgeTypeLbl = ".//div[text()='Badge Type']";
	String applicationWizardBadgeValidFromLbl = ".//div[text()='Badge Valid From']";
	String applicationWizardBadgeValidToLbl = ".//div[text()='Badge Valid To']";
	String applicationWizardBadgeStatusLbl = ".//div[text()='Badge Status']";
	String applicationWizardPrintBadgeBtn = ".//button[text()='Print Badge']";
	String applicationWizardModifyExistingMediaBtn = ".//button[text()='Modify Existing Media']";
	String applicationWizardBadgeIdTxt = ".//input[@id='BadgeId']";
	String applicationWizardCaptureSignatureBtn = ".//button[text()='Capture Signature']";
	
	String applicationWizardTrainingScreeningPodLnk = ".//*[@id='Training & Screening']";
	String applicationWizardBadgeHistoryLnk = ".//*[@class='viewBadgeHistory' and text()=' Audit History']";
	String applicationWizardBadgeValidToTxt = ".//*[@id='divBadgeValidTo']//input[@class='ui-igedit-field fab-time-date fab-ui-textfield fab-ui-gradient-bg fab-ui-border']";
	String applicationWizardBadgeStatusDdn = ".//*[@id='BadgeStatus']";
	String applicationWizardBadgeReasonCodeDdn = ".//*[@id='BadgeReasonCode']";
	String applicationWizardBadgeConfiscateBtn = ".//button[@type='button' and text()='Confiscate a Badge']";
	String applicationWizardBadgeConfiscateBadgeLbl = ".//div[@class='fab-pod-title' and text()='Confiscate a Badge']";
	String applicationWizardBadgeSuspendBtn = ".//button[@type='button' and text()='Suspend a Badge']";
	String applicationWizardBadgeSuspendBadgeLbl = ".//div[@class='fab-pod-title' and text()='Suspend a Badge']";
	String applicationWizardBadgeCommentsTxt = ".//textarea[@id='BadgeComments']";
	String applicationWizardBadgeChangeStatusSaveBtn = ".//div[@class='badge_wizard_footer_buttons fabric_comp_buttonbar']//button[@type='button' and text()='Save']";
	String applicationWizardAuthorizationToWorkPodLnk = ".//*[@id='Establish Authorization']";
	
	String applicationWizardApplicantProfilePodLnk = ".//*[@id='Profile']";
	String applicationWizardPINTxt = ".//*[@id='PIN_modify']";
	String applicationWizardConfirmPINTxt = ".//*[@id='PIN_Confrm']";
	String applicationWizardCitizenshipTypeDdn = ".//*[@id='Citizenship_Type']";
	String applicationWizardCountryOfBirthDdn = ".//*[@id='Country_of_birth_telos']";
	String applicationWizardStreetAddressTxt = ".//*[@id='Street']";
	String applicationWizardCityTxt = ".//*[@id='City']";
	String applicationWizardZipCodeTxt = ".//*[@id='Zip']";
	String applicationWizardApplicantBadgeTypeDdn = ".//*[@id='ApplicantBadgeType']";
	String applicationWizardStateDdn = ".//*[@id='Address_State_telos']";
	String applicationWizardSubmitBtn = ".//div[@class='badge_wizard_footer_buttons fabric_comp_buttonbar']//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange save']";
	String applicationWizardPopupContinueBtn = ".//*[@id='Address_State_telos']";
	
	String applicationWizardBackgroundCheckPodLnk = ".//*[@id='Background Check']";
	String applicationWizardUpdateBackgroundCheckSTABtn = ".//span[contains(text(),'STA CHECK')]/ancestor::tr//button[@aria-label='Update Background Check']";
	String applicationWizardUpdateBackgroundCheckCHRCBtn = ".//span[contains(text(),'CHRC CHECK')]/ancestor::tr//button[@aria-label='Update Background Check']";
	String applicationWizardUpdateManuallyBtn = ".//button[@ng-if='backgroundUpdateVm.manualSubmission']";
	String applicationWizardUpdateBackgroundCheckStatusDdn = ".//md-select[@name='statusId']";
	String applicationWizardUpdateBackgroundCheckSaveBtn = ".//md-dialog-actions/button[@ng-click='backgroundUpdateManVm.updateBgCheck()']";
	String applicationWizardUpdateBackgroundCheckRapBackInformationTab = ".//md-pagination-wrapper//span[text()='RapBack Information']";
	String applicationWizardRapbackInformationSubscribeBtn = ".//button[@ng-click='backgroundCheckVm.createNewSubscription()']";
	String applicationWizardUpdateBGConfirmAndSubmitBtn = ".//button[@ng-click='backgroundUpdateVm.confirmAndSubmit()']";
	
	
	/** INCIDENT MANAGEMENT OBJECTS **/
	
	String incidentManagementIncidentNameLnk = ".//a[@class='listTitle ng-binding' and text()='Incident Name']";
	String incidentManagementViolationNamesHeaderLbl = ".//h3[text()='Maintain the list of Violation names']";
	String incidentManagementNewBtn = ".//div[@class='js-grid_names-list-header-buttons fabric_comp_buttonbar']//button[text()='New']";
	String incidentManagementModifyBtn = ".//div[@class='js-grid_names-list-header-buttons fabric_comp_buttonbar']//button[text()='Modify']";
	String incidentManagementDeleteBtn = ".//div[@class='js-grid_names-list-header-buttons fabric_comp_buttonbar']//button[text()='Delete']";
	String incidentManagementCreateViolationHeaderLbl = ".//h3[text()='Create a new Violation Name']";
	String incidentManagementModifyViolationHeaderLbl = ".//h3[text()='Modify Violation Name']";
	String incidentManagementViolationNameTxt = ".//input[@id='incident_names_form_crud_incidentName']";
	String incidentManagementViolationTitleTxt = ".//textarea[@id='incident_names_form_crud_incidentDescription']";
	String incidentManagementViolationDescriptionTxt = ".//textarea[@id='incident_names_form_crud_longDescription']";
	String incidentManagementViolationIncidentTypeDdn = ".//select[@id='incident_names_form_crud_incidentTypes']";
	String incidentManagementViolationTagsTxt = ".//textarea[@id='incident_names_form_crud_tags']";
	String incidentManagementViolationDemeritPointsTxt = ".//input[@id='incident_names_form_crud_demerit_points']";
	String incidentManagementViolationTypeDdn = ".//select[@id='incident_names_form_crud_violation_type']";
	String incidentManagementViolationActiveChk = ".//input[@id='incident_names_form_crud_active' and @type='checkbox']";
	String incidentManagementViolationSaveBtn = ".//div[@class='js-crud-form-header-buttons fabric_comp_buttonbar']//button[text()='Save']";
	String incidentManagementViolationCancelBtn = ".//div[@class='js-crud-form-header-buttons fabric_comp_buttonbar']//button[text()='Cancel']";
	String incidentManagementDeleteIncidentOkBtn = ".//div[@class='confirm_buttons fabric-confirmpopup-buttons']/button[text()='OK']";
	
	
	
	
	
}