package ObjectRepository;

public interface IdentityObjects {
	
	String idmTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='IDM']";
	
	String idmManageIdentityLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Manage Identity']";	
	
	String idmManageIdentitySearchFieldTxt =".//input[@name='searchField']";
	
	String idmManageIdentityAccessesTabBtn =".//*[@data-ref='btnInnerEl' and text()='Accesses']";
	
	String createIdentityHeader = "//div[contains(@class,'x-box-target')]//label[text()='Create Identity Management']";
	
	String identityManagementHeader = "//div[contains(@class,'x-box-target')]//label[text()='Identity Management']";
	
	String cancelButtonLnk = ".//*[@class='x-btn-inner x-btn-inner-aebtnSecondary-medium' and text()='Cancel']";
	
	String filterIconLnk = "//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-filter')]";

	String addFilterLnk = "//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aebtnSecondary-medium aegrid-rowAdd')]";
	
	String enterFieldName1ToFilter = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default')]";
	
	String enterFieldName2ToFilter = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
	
	String clickFieldValue1 = "//input[contains(@class,'x-form-field x-form-text x-form-text-default  x-form-empty-field')]";
	
	String enterFieldValue1 = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
	
	String clickFieldValue2 = "//input[contains(@class,'x-form-field x-form-text x-form-text-default  x-form-empty-field')]";
	
	String enterFieldValue2 = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
	
	String createIdentityBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Create']";
	
	String profileTabLnk = ".//*[@class='x-tab-inner x-tab-inner-default' and text()='Profile Info']";
	
	String accessTabLnk = ".//*[@class='x-tab-inner x-tab-inner-default' and text()='Accesses']";
	
	String systemsTabLnk = ".//*[@class='x-tab-inner x-tab-inner-default' and text()='Systems']";
	
	String assetsTabLnk = ".//*[@class='x-tab-inner x-tab-inner-default' and text()='Assets']";
	
	String prerequisitesTabLnk = ".//*[@class='x-tab-inner x-tab-inner-default' and text()='Prerequisites']";
	
	String firstNameLnk = "//*[contains(@id,'baseText') and @placeholder='Enter First Name']";
	
	String lastNameLnk = "//*[contains(@id,'baseText') and @placeholder='Enter Last Name']";	
	
	String collapseBasicInfoSection = "//fieldset[@aria-label='Basic Information field set']//div[contains(@class,'x-tool-img x-tool-toggle ')]";
	
	String emailIdLnk = "//*[contains(@id,'baseText') and @placeholder='Enter Email ID']";
	
	String phoneNumberLnk = "//*[contains(@id,'baseText') and @placeholder='Enter Phone Number']";
	
	String workLocationLnk="//*[contains(@id,'baseText') and @placeholder='Enter Work Location']";
	
	String cityLnk="//input[@placeholder='Enter City']";
	
	String managerSourceIdLnk="//input[@placeholder='Enter manager source id']";
	
	String collapseContactInfoSection = "//fieldset[@aria-label='Contact Information field set']//div[contains(@class,'x-tool-img x-tool-toggle ')]";
	
	String collapseOrganisationInfoSection = "//fieldset[@aria-label='Organisation Information field set']//div[contains(@class,'x-tool-img x-tool-toggle ')]";
	
	String employeeTypeLnk = "//*[contains(@id,'baseBusObjType') and @placeholder='Select Employee Type']";
	
	String jobTitleLnk = "//*[contains(@id,'baseText') and @placeholder='Enter Job Title']";
	
	String validFromLnk = "//*[contains(@id,'baseDateTime') and @placeholder='Select Valid From']";
	
	String validToLnk = "//*[contains(@id,'baseDateTime') and @placeholder='Select Valid To']";

	String idmSaveBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Save']";
	
	String menuItemsIcon = "//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-menu')]";
	
	String deletedItemsLnk = "//*[contains(@class,'x-menu-item-text-default x-menu-item-indent-no-separator') and text()='Open Deleted Items']";
	
	String restoreButtonInDeletedItemsWindow = "//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Restore']";
	
	String cancelButtonInDeletedItemsWindow = "//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Restore']/ancestor::a/preceding-sibling::a//span[contains(@id,'button') and text()='Cancel']";
	
	String closeButtonInDeletedItemsWindow = "//div[contains(@class,'x-tool-tool-el x-tool-img x-tool-close ')]";
		
	String deletedIdentityDocumentsHeader = "//div[contains(@id,'MainPanelTrashCanPopup') and text()='Deleted identity Documents']";
	
	String deletedSinceDateValue = "//div[contains(@id,'baseDateTime')]//input[contains(@class,'x-form-text-default  x-form-empty-field x-form-empty-field-default')]";
	
	String addRecordsIconPrerequisiteTab = "//a[normalize-space(text())='Click here to Add']";
	
	String addRecordsIconSystemsTab = "//a[normalize-space(text())='Click here to Add']";
	
	String addRecordsIconAccessTab = "//a[normalize-space(text())='Click here to Add']";
	
	String emptyGrid = ".//div[@class='emptyGridMsg']";
	
//	String confirmButtonValidFrom = "(//span[contains(@id,'ButtonbaseDatePicker') and text()='Confirm'])[1]";
	
//	String confirmButtonValidTo ="(//span[contains(@id,'ButtonbaseDatePicker') and text()='Confirm'])[2]";
	
	String confirmButton = "//*[contains(@id,'button') and text()='Confirm']";
	
//	String addCommentsButtonLnk = "//span[contains(@class,'x-btn-inner x-btn-inner-aebtnSmallPrimary-small') and text()='Add Comment']";
	
//	String closeNotesWindowLnk = "//div[text()='Add Comment']/parent::div//following-sibling::div//div[contains(@class,'x-tool-img x-tool-close ')]";
	
	String settingsIconLnk = "//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-setting ')]";
	
	
	String widgetMenuLnk = "//div[contains(@class,'x-menu-item x-menu-item-default x-box-item')]//span[ text()='Show/hide Filter Widget']";
	
	String errorMessageForFirstName = "//div[contains(@class,'x-form-error-msg')]//li[text()='First Name is required ']";
	
//	String uploadAttachmentLnk = "//span[contains(@class,'x-fa fa-paperclip ')]";
	
//	String downloadAttachmentLnk = "//span[contains(@class,' x-fa fa-download ')]";
	
//	String deleteAttachmentLnk = "//span[contains(@class,'x-fa fa-trash ')]";
	
	String deleteIdentityIconLnk = "//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-delete ')]";
	
	String noButtonInDeleteIdentitiesPopUp = "//*[contains(@id,'button') and contains(@class,'x-btn-inner-aebtnSmallSecondary-small') and text()='No']";
	
	String yesButtonInDeleteIdentitiesPopUp = "//*[contains(@id,'button') and contains(@class,'x-btn-inner-aebtnSmallPrimary-small') and text()='Yes']";
	
	String cancelAssetButtonLnk = "//div[contains(@class,'x-container x-container-default x-box-layout-ct')]//span[text()='Cancel']";
	
	String resetButtonAssetScreen = "//div[contains(@class,'x-container x-container-default x-box-layout-ct')]//span[text()='Reset']";
	
	String imageLnk = "//div[@class='x-autocontainer-innerCt']//img[@class='x-img idmphoto x-img-default']";
	
	String addImageLnk = ".//div[@class='x-container imageoverlay x-container-default']";
}
