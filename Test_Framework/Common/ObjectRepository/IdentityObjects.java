package ObjectRepository;

import CommonClassReusables.ByAttribute;

public interface IdentityObjects {
	
	
	//String idmTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Cardholders & Assets']";
	String idmTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='IDM']";
	String cardHoldersAndAssetsTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Cardholders & Assets']";
	String idmManageIdentityAssetsAddAssetBtn =".//span[contains(@id,'gridcolumn') and text()='Asset Access ID']//ancestor::div[@role='rowgroup']//following-sibling::div[@data-ref='body']//div[@role='button' and @data-qtip='Add Row']";
	String idmManageIdentityAssetsSelectAssetTxt =".//span[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Confirm']//ancestor::tr//preceding-sibling::tr//input[@placeholder='Select Asset']";
	String manageAssetsLnk = ".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Manage Assets']";
	String idmManageIdentitiesLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Manage Identities']";
	String idmManageIdentityLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Manage Identity']";
	String idmManageIdentitySearchFieldTxt =".//input[@name='searchField']";
	String idmManageIdentityIsUnlockPermBadgeChk =".//div[contains(@id,'header-title-textEl') and text()='Do you want to return badge?']//ancestor::div[@class='x-window x-layer x-window-default x-closable x-window-closable x-window-default-closable x-border-box']//input[contains(@id,'inputEl') and @type='checkbox']";
	String idmManageIdentityAccessesTabBtn =".//*[@data-ref='btnInnerEl' and text()='Accesses']";
	String idmManageIdentityAssetsAddAssetConfirmBtn =".//span[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Confirm']";
	String idmManageIdentityAssetsAddAssetCloseWindowBtn="//div[@class='x-tool-tool-el x-tool-img x-tool-close ']";
	String createIdentityHeader = "//div[contains(@class,'x-box-target')]//label[text()='Create Identity Management']";
	String identityManagementHeader = "//div[contains(@class,'x-box-target')]//label[text()='Identity Management']";
	String idmManageIdentityCancelBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnSecondary-medium' and text()='Cancel']";
	String filterIconLnk = "//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-filter')]";
	String idmManageIdentityProfileTabPositionTxt = "//input[contains(@id,'baseComboBoxRemote') and @placeholder='Select Position']";
	String addFilterLnk = "//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aebtnSecondary-medium aegrid-rowAdd')]";
	String enterFieldName1ToFilter = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default')]";
	String enterFieldName2ToFilter = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
	String clickFieldValue1 = "//input[contains(@class,'x-form-field x-form-text x-form-text-default  x-form-empty-field')]";
	String enterFieldValue1 = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
	String clickFieldValue2 = "//input[contains(@class,'x-form-field x-form-text x-form-text-default  x-form-empty-field')]";
	String enterFieldValue2 = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
	String createBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Create']";
	String idmManageIdentityClickToAddLnk = "//a[normalize-space(text())='Click here to Add']";
	String idmManageIdentityAssetTabAssignAsetWindow = "//div[contains(@id,'ext-comp') and @class='x-window-bodyWrap']";
	String idmManageIdentityaddRowLnk = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[1]";
	String idmManageIdentityDeleteRowLnk = "(//div[contains(@class,'x-action-col-1  aegrid-rowMinus')])[1]";
	String idmManageIdentityProfileTabLnk = ".//*[@class='x-tab-inner x-tab-inner-default' and text()='Profile Info']";
	String idmManageIdentityAccessTabBtn = ".//*[@class='x-tab-inner x-tab-inner-default' and text()='Accesses']";
	String idmManageIdentitySystemsTabBtn = ".//*[@class='x-tab-inner x-tab-inner-default' and text()='Systems']";
	String idmManageIdentityAssetsTabBtn =".//*[@data-ref='btnInnerEl' and text()='Assets']";
	String prerequisitesTabLnk = ".//*[@class='x-tab-inner x-tab-inner-default' and text()='Prerequisites']";
	String idmManageIdentityProfileInfoFirstNameTxt = "//*[contains(@id,'baseText') and @placeholder='Enter First Name']";
	String idmManageIdentityProfileInfoLastNameTxt = "//*[contains(@id,'baseText') and @placeholder='Enter Last Name']";	
	String idmManageIdentityProfileInfoPhoneNoTxt = "//*[contains(@id,'baseText') and @placeholder='Enter Phone Number']";
	String collapseBasicInfoSection = "//fieldset[@aria-label='Basic Information field set']//div[contains(@class,'x-tool-img x-tool-toggle ')]";
	String emailIdLnk = "//*[contains(@id,'baseText') and @placeholder='Enter Email ID']";
	String phoneNumberLnk = "//*[contains(@id,'baseText') and @placeholder='Enter Phone Number']";
	String workLocationLnk="//*[contains(@id,'baseText') and @placeholder='Enter Work Location']";
	String cityLnk="//input[@placeholder='Enter City']";
	String managerSourceIdLnk="//input[@placeholder='Enter manager source id']";
	String collapseContactInfoSection = "//fieldset[@aria-label='Contact Information field set']//div[contains(@class,'x-tool-img x-tool-toggle ')]";
	String collapseOrganisationInfoSection = "//fieldset[@aria-label='Organisation Information field set']//div[contains(@class,'x-tool-img x-tool-toggle ')]";
	String employeeTypeLnk = "//*[contains(@id,'baseBusObjType') and @placeholder='Select Employee Type']";
	String idmProfileUserIdTxt = "//*[contains(@id,'baseText') and @placeholder='Enter User ID']";
	String jobTitleLnk = "//*[contains(@id,'baseText') and @placeholder='Enter Job Title']";
	String validFromLnk = "//*[contains(@id,'baseDateTime') and @placeholder='Select Valid From']";
	String validToLnk = "//*[contains(@id,'baseDateTime') and @placeholder='Select Valid To']";
	String SaveBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Save']";
	String menuItemsIcon = "//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-menu')]";
	String deletedItemsLnk = "//*[contains(@class,'x-menu-item-text-default x-menu-item-indent-no-separator') and text()='Open Deleted Items']";
	String restoreButtonInDeletedItemsWindow = "//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Restore']";
	String cancelButtonInDeletedItemsWindow = "//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Restore']/ancestor::a/preceding-sibling::a//span[contains(@id,'button') and text()='Cancel']";
	String closeButtonInDeletedItemsWindow = "//div[contains(@class,'x-tool-tool-el x-tool-img x-tool-close ')]";
	String deletedIdentityDocumentsHeader = "//div[contains(@id,'MainPanelTrashCanPopup') and text()='Deleted identity Documents']";
	String deletedSinceDateValue = "//div[contains(@id,'baseDateTime')]//input[contains(@class,'x-form-text-default  x-form-empty-field x-form-empty-field-default')]";
	String emptyGrid = ".//div[@class='emptyGridMsg']";
	String confirmButton = "//*[contains(@id,'button') and text()='Confirm']";
	String settingsIconLnk = "//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-setting ')]";
	String widgetMenuLnk = "//div[contains(@class,'x-menu-item x-menu-item-default x-box-item')]//span[ text()='Show/hide Filter Widget']";
	String errorMessageForFirstName = "//div[contains(@class,'x-form-error-msg')]//li[text()='First Name is required ']";
	String deleteIdentityIconLnk = "//*[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-delete ')]";
	String noButtonInDeleteIdentitiesPopUp = "//*[contains(@id,'button') and contains(@class,'x-btn-inner-aebtnSmallSecondary-small') and text()='No']";
	String yesButtonInDeleteIdentitiesPopUp = "//*[contains(@id,'button') and contains(@class,'x-btn-inner-aebtnSmallPrimary-small') and text()='Yes']";
	String cancelAssetButtonLnk = "//div[contains(@class,'x-container x-container-default x-box-layout-ct')]//span[text()='Cancel']";
	String resetButtonAssetScreen = "//div[contains(@class,'x-container x-container-default x-box-layout-ct')]//span[text()='Reset']";
	String imageLnk = "//div[@class='x-autocontainer-innerCt']//img[@class='x-img idmphoto x-img-default']";
	String addImageLnk = ".//div[@class='x-container imageoverlay x-container-default']";
	String reloadOptionMenu = "//*[@class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu ']";
	String reloadOption = "//*[contains(@id,'menuitem') and text()='Reload']";
	String leftPaneExpansionLnk = "//*[@class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-hamburgerexpand ']";
	String leftPaneContractionLnk = "//*[@class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-hamburger ']";
	String idmAddAssetSelectDdn = "(//input[contains(@id,'baseComboBoxRemote') and @placeholder='Select Asset'])[4]";
	String idmAddAssetStatusDdn = ".//span[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Confirm']//ancestor::tr//preceding-sibling::tr//*[contains(@id,'baseComboBox') and @placeholder='Select Status']";
	String idmAddAssetSaveBtn = "//*[text()='Reset']//ancestor::a//following-sibling::a//*[contains(@class,'x-btn-inner x-btn-inner-aebtnPrimary-medium') and text()='Save']";
	String actionBtn = "(//div[contains(@class,'x-action-col-icon x-action-col') and @role='button'])[4]";
	String identityCommentsBtn = "//div[@data-qtip='Add Comments' and @class='x-action-col-icon x-action-col-0  aegrid-select']";
	String manageEntities = ".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Manage Entities']";
	String cardHolders = ".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Cardholders']";
	String idmMAnageIdentityExpandLeftViewLnk = ".//span[contains(@class,'hamburgerexpand')]";
	

	String identityRowAdderLnk = "//div[contains(@class,'x-action-col-icon x-action-col-0  aegrid-rowAdd')]";
	String identityAssignAssetLbl = "(//div[text()='Assign Badges'])[2]";
	String identitySelectAssetDnd = "(//input[@placeholder= 'Select Asset'])[4]//parent::div//following-sibling::div";
	String identitySelectAssetValueTxt = "(//span//following::b[text()='Code']//following::div//following::span[1])[1]";
	String identityConfirmAssetBtn = "//span[text()='Confirm' and contains(@class,'x-btn-inner x-btn-inner-aebtnPrimary-medium')]";
	String identityResetPinBtn = "(//div[@data-qtip='Reset Pin' and @class = 'x-action-col-icon x-action-col-0  aegrid-passwordUpdate'])[1]";
	String identityResetPinYesBtn = "//span[contains(text(),'Yes') and contains(@class,'x-btn-inner x-btn-inner-aebtnSmallPrimary-small')]";
	String identitypopUpToast = "//div[contains(@id,'toast') and @class='x-autocontainer-innerCt']";
	String identityReplaceBadgeBtn = "(//label[text()='Active'])[1]//parent::div//parent::div//parent::td//following-sibling::td[4]//div[@class='x-action-col-icon x-action-col-0  aegrid-replaceasset']";
	String identityReplaceBadgeLbl = "//div[text()='Replace Badge']";
	String identityReplacementReason = "//input[@placeholder = 'Enter Replacement Reason']";
	String identitySelectStatusDnd = "(//input[@placeholder= 'Select Asset Status'])[2]//parent::div//following-sibling::div";
	String identityDamagedStatusTxt = "//li[text()='DAMAGED']";
	String identitySelectBadgetDnd = "(//input[@placeholder= 'Select Asset'])[5]//parent::div//following-sibling::div";
	String identityReplaceSaveBtn = "(.//span[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Save'])[2]";
	String identityValidTo = "(//input[@placeholder='Select Valid To' and @class = 'x-form-field x-form-text x-form-text-default '])[4]";
	String identityAdvancedSearchBtn = "//span[text()='Advanced Search']";
	String activateActionBtn = "//div[@class='x-action-col-icon x-action-col-0  aegrid-active3']";
	String deactivateActionBtn = "//div[@class='x-action-col-icon x-action-col-0  aegrid-inactive2']";
	String badgeTabLnk = ".//*[@class='x-tab-inner x-tab-inner-default' and text()='Badges']";
	String identityAssignAssetValidToDate = "//span[@class='x-btn-inner x-btn-inner-aebtnSecondary-medium' and text()='Reset']//ancestor::tr//preceding-sibling::tr//*[contains(@id,'baseDateTime') and @placeholder='Select Valid To']";
	
	
	
	String idmCardholdersAssetsTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Cardholders & Assets']";
	
	String idmManageIdentitySaveBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Save']";
	
	String idmManageIdentityMenuBtn =".//*[@class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu ']";
	
	String idmManageIdentityReloadBtn = ".//*[contains(@id,'menuitem') and text()='Reload']";
	
	String cancelButtonLnk = "(//a[@role='button']//span[contains(@class,'x-btn-inner') and text()='Cancel'])[1]";
	
	String createIdentityLnk = "//span[normalize-space(text())='Create']";
	
	String profileTabLnk = "//span[text()='Profile Info']";
	
	String accessTabLnk = "//span[text()='Accesses']";
	
	String systemsTabLnk = "//span[text()='Systems']";
	
	String assetsTabLnk = "//span[text()='Assets']";
	
	String firstNameLnk = "//input[@placeholder='Enter First Name']";
	
	String lastNameLnk = "//input[@placeholder='Enter Last Name']";	
	
	String saveIconLnk = "//span[contains(@class,'x-btn-inner-aebtnPrimary-medium') and text()='Save']";
	
	String deletedItemsIcon = "//span[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-menu')]";
	
	String restoreButtonInDeletedItemsLnk = "//span[contains(@id,'button') and text()='Restore']";
	
	String cancelButtonInDeletedItemsLnk = "//span[contains(@id,'button') and text()='Restore']/ancestor::a/preceding-sibling::a//span[contains(@id,'button') and text()='Cancel']";
	
	String closeButtonInDeletedItemsLnk = "//div[contains(@class,'x-tool-tool-el x-tool-img x-tool-close ')]";
	
	String addRecordsIconPrerequisiteTab = "//a[normalize-space(text())='Click here to Add']";
	
	String addRecordsIconSystemsTab = "//a[normalize-space(text())='Click here to Add']";
	
	String addRecordsIconAccessTab = "//a[normalize-space(text())='Click here to Add']";
	
	String confirmButtonValidFrom = "(//span[contains(@id,'ButtonbaseDatePicker') and text()='Confirm'])[1]";
	
	String confirmButtonValidTo ="(//span[contains(@id,'ButtonbaseDatePicker') and text()='Confirm'])[2]";
	
	String addCommentsButtonLnk = "//span[contains(@class,'x-btn-inner x-btn-inner-aebtnSmallPrimary-small') and text()='Add Comment']";
	
	String closeNotesWindowLnk = "//div[text()='Add Comment']/parent::div//following-sibling::div//div[contains(@class,'x-tool-img x-tool-close ')]";
	
	String uploadAttachmentLnk = "//span[contains(@class,'x-fa fa-paperclip ')]";
	
	String downloadAttachmentLnk = "//span[contains(@class,' x-fa fa-download ')]";
	
	String deleteAttachmentLnk = "//span[contains(@class,'x-fa fa-trash ')]";
	
	String yesButtonToDeleteIdentities = "//span[contains(@id,'button') and contains(@class,'x-btn-inner-aebtnSmallPrimary-small') and text()='Yes']";
	
	
}
