package ObjectRepository;

public interface MyRequestObjects {
	
	String myRequestsTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='My Requests']";
	String createBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Create']";
	String wellnessCheckLnk = "//div[@class='x-component location-chooser-view x-box-item x-component-default x-scroller']//p[text()='Wellness Check']";
	String acknowledgementCheckbox = "(//input[@type='checkbox'])[1]";
	String submitBtn = "//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Submit']";
	String questionOneNo = "//*[@class='x-form-item-label-text' and contains(text(),'tested positive')]//ancestor::label//following-sibling::div//label[text()='No']//preceding-sibling::span//input";
	String questionTwoNo = "//*[@class='x-form-item-label-text' and contains(text(),'COVID-19 symptoms')]//ancestor::label//following-sibling::div//label[text()='No']//preceding-sibling::span//input";
	String questionThreeNo = "//*[@class='x-form-item-label-text' and contains(text(),'temperature')]//ancestor::label//following-sibling::div//label[text()='No']//preceding-sibling::span//input";
	String questionOneYes = "//*[@class='x-form-item-label-text' and contains(text(),'tested positive')]//ancestor::label//following-sibling::div//label[text()='Yes']//preceding-sibling::span//input";
	String userRequestHeader = "//div[@class='x-box-target']//label[text()='User Request']";
	String reloadOptionMenu = "//*[@class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu ']";
	String reloadOption = "//*[contains(@id,'menuitem') and text()='Reload']";
	String requestNoLnk = "(//tr[@class='  x-grid-row'])[1]//*[contains(text(),'ACR-')]";
	String otherRequestsLnk = "//div[@class='x-component location-chooser-view x-box-item x-component-default x-scroller']//p[text()='Other Requests']";
	String selectRequestType = "//*[contains(@id,'baseComboBoxRemote') and @placeholder ='Select Request Type']";
	String uploadImgBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnSecondary-medium' and text()='Upload']";
	String cropAndSaveBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Crop & Save']";
	String employeeType = "//input[contains(@id,'baseBusObjType') and @placeholder='Select Employee Type']";
	String deleteAttachmentBtnLnk = "//*[contains(@id,'button') and @data-qtip='Delete Image']";
}
