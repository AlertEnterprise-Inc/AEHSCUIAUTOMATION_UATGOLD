package ObjectRepository;import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.LogStatus;

import CommonClassReusables.ByAttribute;
import CommonClassReusables.Utility;

public interface IdentityObjects {
	
	String IdentityTabLnk ="//span[contains(@class,'x-btn-inner-aeTopMenuButton-small') and contains(text(),'IDM')]";
	
	String manageIdentityLnk = "//span[text()='Manage Identity']";
	
	String createIdentityHeader = "//div[contains(@class,'x-box-target')]//label[text()='Create Identity Management']";
	
	String identityManagementHeader = "//div[contains(@class,'x-box-target')]//label[text()='Identity Management']";
	
	String cancelButtonLnk = "(//a[@role='button']//span[contains(@class,'x-btn-inner') and text()='Cancel'])[1]";
	
	String filterIconLnk = "//span[contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-filter ')]";

	String addFilterLnk = "//span[contains(@class,'x-btn-icon-el-aebtnSecondary-medium aegrid-rowAdd')]";
	
	String enterFieldName1ToFilter = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default')]";
	
	String enterFieldName2ToFilter = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
	
	String clickFieldValue1 = "//input[contains(@class,'x-form-field x-form-text x-form-text-default  x-form-empty-field')]";
	
	String enterFieldValue1 = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
	
	String clickFieldValue2 = "//input[contains(@class,'x-form-field x-form-text x-form-text-default  x-form-empty-field')]";
	
	String enterFieldValue2 = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
	
	String createIdentityLnk = "//span[normalize-space(text())='Create']";
	
	String profileTabLnk = "//span[text()='Profile Info']";
	
	String accessTabLnk = "//span[text()='Accesses']";
	
	String systemsTabLnk = "//span[text()='Systems']";
	
	String assetsTabLnk = "//span[text()='Assets']";
	
	String prerequisitesTabLnk = "//span[text()='Prerequisites']";
	
	String firstNameLnk = "//input[@placeholder='Enter First Name']";
	
	String lastNameLnk = "//input[@placeholder='Enter Last Name']";	
	
	String collapseBasicInfoSection = "//fieldset[@aria-label='Basic Information field set']//div[contains(@class,'x-tool-img x-tool-toggle ')]";
	
	String emailIdLnk = "//input[@placeholder='Enter Email ID']";
	
	String phoneNumberLnk = "//input[@placeholder='Enter Phone Number']";
	
	String workLocationLnk="//input[@placeholder='Enter Work Location']";
	
	String cityLnk="//input[@placeholder='Enter City']";
	
	String managerSourceIdLnk="//input[@placeholder='Enter manager source id']";
	
	String collapseContactInfoSection = "//fieldset[@aria-label='Contact Information field set']//div[contains(@class,'x-tool-img x-tool-toggle ')]";
	
	String collapseOrganisationInfoSection = "//fieldset[@aria-label='Organisation Information field set']//div[contains(@class,'x-tool-img x-tool-toggle ')]";
	
	String employeeTypeLnk = "//input[@placeholder='Select Employee Type']";
	
	String jobTitleLnk = "//input[@placeholder='Enter Job Title']";
	
	String validFromLnk = "//input[@placeholder='Select Valid From']";
	
	String validToLnk = "//input[@placeholder='Select Valid To']";

	String saveIconLnk = "//span[contains(@class,'x-btn-inner-aebtnPrimary-medium') and text()='Save']";
	
	String deletedItemsIcon = "//span[contains(@id,'button') and contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-menu')]";
	
	String deletedItemsLnk = "//span[contains(@class,'x-menu-item-text-default x-menu-item-indent-no-separator') and text()='Open Deleted Items']";
	
	String restoreButtonInDeletedItemsLnk = "//span[contains(@id,'button') and text()='Restore']";
	
	String cancelButtonInDeletedItemsLnk = "//span[contains(@id,'button') and text()='Restore']/ancestor::a/preceding-sibling::a//span[contains(@id,'button') and text()='Cancel']";
	
	String closeButtonInDeletedItemsLnk = "//div[contains(@class,'x-tool-tool-el x-tool-img x-tool-close ')]";
	
	String deletedIdentityDocumentsHeader = "//div[contains(@id,'MainPanelTrashCanPopup') and text()='Deleted identity Documents']";
	
	String deletedSinceDateValue = "//div[contains(@id,'baseDateTime')]//input[contains(@class,'x-form-text-default  x-form-empty-field x-form-empty-field-default')]";
	
	String addRecordsIconPrerequisiteTab = "//a[normalize-space(text())='Click here to Add']";
	
	String addRecordsIconSystemsTab = "//a[normalize-space(text())='Click here to Add']";
	
	String addRecordsIconAccessTab = "//a[normalize-space(text())='Click here to Add']";
	
	String confirmButtonValidFrom = "(//span[contains(@id,'ButtonbaseDatePicker') and text()='Confirm'])[1]";
	
	String confirmButtonValidTo ="(//span[contains(@id,'ButtonbaseDatePicker') and text()='Confirm'])[2]";
	
	String confirmButton = "//span[contains(@id,'button') and text()='Confirm']";
	
	String addCommentsButtonLnk = "//span[contains(@class,'x-btn-inner x-btn-inner-aebtnSmallPrimary-small') and text()='Add Comment']";
	
	String closeNotesWindowLnk = "//div[text()='Add Comment']/parent::div//following-sibling::div//div[contains(@class,'x-tool-img x-tool-close ')]";
	
	String settingsIconLnk = "//span[contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-setting ')]";
	
	String widgetMenuLnk = "//span[contains(@class,'x-menu-item-text-default x-menu-item-indent-no-separator') and text()='Show/hide Filter Widget']";
	
	String errorMessageForFirstName = "//div[contains(@class,'x-form-error-msg')]//li[text()='First Name is required ']";
	
	String uploadAttachmentLnk = "//span[contains(@class,'x-fa fa-paperclip ')]";
	
	String downloadAttachmentLnk = "//span[contains(@class,' x-fa fa-download ')]";
	
	String deleteAttachmentLnk = "//span[contains(@class,'x-fa fa-trash ')]";
	
	String deleteIdentityIconLnk = "//span[contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-delete ')]";
	
	String yesButtonToDeleteIdentities = "//span[contains(@id,'button') and contains(@class,'x-btn-inner-aebtnSmallPrimary-small') and text()='Yes']";
	
	String cancelAssetButtonLnk = "//div[contains(@class,'x-container x-container-default x-box-layout-ct')]//span[text()='Cancel']";
	
	String resetButtonAssetScreen = "//div[contains(@class,'x-container x-container-default x-box-layout-ct')]//span[text()='Reset']";
}
