package ObjectRepository;

import CommonClassReusables.AGlobalComponents;

public interface ReconObjects {

		/** Recon **/
		String reconTabLnk ="//span[contains(@class,'x-btn-inner-aeTopMenuButton-small') and contains(text(),'Recon')]";
		
		String reconSetUpLnk = "//span[contains(text(),'Recon setup')]";
		
		String reconMonitorLnk = "//span[contains(text(),'Recon Monitor')]";

		String reconRemediationLnk = "//span[contains(text(),'Recon Remediation')]";
		
		String trialButtonLnk = "//span[contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-trial ')]";
		
		String endDateForTrialJob = "//input[contains(@id,'baseDateTime')]";
		                             
		String addReconRowLnk = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[1]";
		
		String filterIconLnk = "//span[contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-filter ')]";
		
		String searchBarInRecon = "//input[contains(@class,'x-ibeGridSearch-field x-form-empty-field x-form-empty-field-default')]";
		
		String ListValueAfterSearch = "//li[@class='x-boundlist-item' and text()='"+AGlobalComponents.jobName+"']";
		
		String checkboxLnkOfReconJob = "//div[text()='"+AGlobalComponents.jobName+"']/parent::td/preceding-sibling::td[contains(@class,' x-selmodel-checkbox') and contains(@aria-describedby,'cell-description-selected')]";
		
		String enterFieldName1ToFilter = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default')]";
		
		String addIconToAddFilter = "//span[contains(@class,'x-btn-icon-el-aebtnSecondary-medium aegrid-rowAdd ')]";
		
		String clickFieldValue1 = "//input[contains(@class,'x-form-field x-form-text x-form-text-default  x-form-empty-field')]";
		
		String enterFieldValue1 = "//label[contains(@class,'x-placeholder-label x-placeholder-label-default x-form-empty-field x-form-empty-field-default')]";
		                                                  		
		String clickFieldValue2 = "//input[contains(@class,'x-form-field x-form-text x-form-text-default  x-form-empty-field')]";
		
		String enterFieldValue2 = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
		
		String descriptionLnk = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[5]";
		
		String entityLnk = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[6]";
		
		String connectorLnk = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[7]";
		
		String schedulerLnk = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[13]//div[@class='aegrid-schedule']";
		
		String timezoneLnk = "//input[@placeholder='Select Timezone']";
		
		String scheduleTypeLnk="//input[@placeholder='Select Schedule Type']";
		
		String confirmButtonLnk="//span[contains(text(),'Confirm') and contains(@class,'x-btn-inner x-btn-inner-aebtnPrimary-medium')]";
		
		String submitButtonLnk = "//span[contains(text(),'Submit')]";
		
		String confirmPopUpLnk = "//span[contains(text(),'Confirm')]" ;
		
		String checkboxLnk = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[contains(@class,'checkbox') and contains(@aria-describedby,'cell-description-selected')]";
		
		String elementConnector = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[7]";
		
		String elementDesc = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[5]";	
		
		String elementEntity = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[6]";
		
		String elementScheduler = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[13]//div[@class='aegrid-schedule']";
		
		String confirmButton = "//span[contains(text(),'Confirm') and contains(@class,'x-btn-inner x-btn-inner-aebtnPrimary-medium')]";
		
		String reconJobStatus = "(//tr//td[contains(@class,'x-grid-cell x-grid-td x-grid-cell-gridcolumn')])[3]";
		
		String activeRecords = "(//tr//td[contains(@class,'x-grid-cell x-grid-td x-grid-cell-gridcolumn')])[8]//a";
		
		String errorRecords = "(//tr//td[contains(@class,'x-grid-cell x-grid-td x-grid-cell-gridcolumn')])[9]" ; 
		
		String errorMessage = "(//tr//td[contains(@class,'x-grid-cell x-grid-td x-grid-cell-gridcolumn')])[7]";
		
		String settingsIcon = "//span[contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-setting ')]";
		
		String selectViewLnk = "//span[contains(@class,' x-menu-item-indent-no-separator x-menu-item-indent-right-arrow') and text()='Select View']";
		
		String roleReconViewLnk =  "//span[contains(@class,'x-menu-item-indent-no-separator') and text()='Role Recon View']";
		
		String ReconViewLnk =  "//span[contains(@class,'x-menu-item-indent-no-separator') and text()='Recon View']";
		
		String refreshIconLnk = "//a[contains(@data-qtip,'Reload')]//span[contains(@class,'x-btn-icon-el x-btn-icon-el-aetextlink-medium  ')]";
		
		String deleteIconReconSetup = "//div[text()='"+AGlobalComponents.jobName+"']/parent::td/preceding-sibling::td//div[contains(@class,'aegrid-rowMinus')]";
}
