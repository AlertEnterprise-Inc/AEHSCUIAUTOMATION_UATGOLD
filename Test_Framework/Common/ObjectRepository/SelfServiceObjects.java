package ObjectRepository;

public interface SelfServiceObjects
{	
	
	
	
/** NEW BADGE REQUEST OBJECTS**/
	
	String selfServiceSearchUserTxt = ".//label[@data-ref='placeholderLabel' and text()='Search Identity or User']//preceding-sibling::div//input";
	String selfServiceCardStatusTxt = ".//input[@placeholder='Select Status']";
	String selfServiceCardInactiveDateTxt = ".//input[contains(@id,'baseDateTime') and @placeholder='Enter inactive date']";
	String selfServiceCardActiveDateTxt = ".//input[contains(@id,'baseDateTime') and @placeholder='Enter active date']";
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
	String selfServiceEnterAssetNameTxt = ".//input[@name='assetId' and @placeholder='Select Badge']";

	
	
}