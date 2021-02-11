package ObjectRepository;

public interface AssetObjects {
	
	String assetTypeTxt ="//*[contains(@id,'baseBusObjType') and @placeholder='Select Type']";
	String assetSubTypeTxt = "//*[contains(@id,'baseComboBox') and @placeholder='Select SubType']";
	String assetSerialNumberTxt = "//*[contains(@id,'baseText') and @placeholder='Enter Serial Number']";
	String assetBadgeIdTxt = "//*[contains(@id,'baseText') and @placeholder='Enter Access ID']";
	String assetBadgeNameTxt = "//*[contains(@id,'baseText') and @placeholder='Enter Name']";
	String assetSourceIdTxt = "//*[contains(@id,'baseText') and @placeholder='Enter Source ID']";
	String assetSystemTxt = "//*[contains(@id,'baseComboBoxRemote') and @placeholder='Select System']";
	String assetCreateAssetBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Create']";
	String assetManageAssetLnk = "//*[contains(@id,'button') and text()='Manage Asset']";
	String assetTabBtn = ".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Asset']";
	String assetSaveBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Save']";
	String cardHoldersAndAssetsTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Cardholders & Assets']";
	String manageAssetsLnk = ".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Manage Assets']";
}
