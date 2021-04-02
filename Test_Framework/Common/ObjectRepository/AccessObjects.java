package ObjectRepository;

public interface AccessObjects {
	
	String accessTabLnk ="//span[contains(@class,'x-btn-inner-aeTopMenuButton-small') and contains(text(),'Access')]";
	
	String manageAccessLnk = "//span[text()='Manage Access']";
	
	String filterIconLnk = "//span[contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-filter ')]";
	
	String enterFieldNameToFilter = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default')]";
	
	String clickFieldValue1 = "//input[contains(@class,'x-form-field x-form-text x-form-text-default  x-form-empty-field')]";
	
	String enterFieldValue1 = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
	
	String addFilterLnk = "//span[contains(@class,'x-btn-icon-el-aebtnSecondary-medium aegrid-rowAdd')]";

	String sourceIdLnk = "(//td[contains(@class,'x-grid-td x-grid-cell-gridcolumn')])[9]";
	
	String saveBtn = "//span[text()='Save']";

	String IdentityNewRadioButton = "//*[text()='New']";

	String clickToEnterIdentityName = "//*[@placeholder='Search or Filter by Identity Users']";
	
	String SaveBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Save']";

	String IdentityExistingRadioButton = "//*[text()='IdentityExistinng']";

	String removeAccessLnk = "//*[contains(@class,'aegrid-noaccess ')]";
	
}
