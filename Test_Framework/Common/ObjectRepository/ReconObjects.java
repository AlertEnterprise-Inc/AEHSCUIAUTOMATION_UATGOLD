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
		
		String MinusIconToRemoveExistingFilter = "//span[contains(@class,'aegrid-rowMinus ')]" ; 
		
		String searchBarInRecon = "//input[contains(@class,'x-ibeGridSearch-field')]";
		
		String ListValueAfterSearch = "//li[@class='x-boundlist-item' and text()='"+AGlobalComponents.jobName+"']";
		
		String checkboxLnkOfReconJob = "//div[text()='"+AGlobalComponents.jobName+"']/parent::td/preceding-sibling::td[contains(@class,' x-selmodel-checkbox') and contains(@aria-describedby,'cell-description-selected')]";
		
		String enterFieldName1ToFilter = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default')]";
		
		String addIconToAddFilter = "//span[contains(@class,'x-btn-icon-el-aebtnSecondary-medium aegrid-rowAdd ')]";
		
		String clickFieldValue1 = "//input[contains(@class,'x-form-field x-form-text x-form-text-default  x-form-empty-field')]";
		
		String enterFieldValue1 = "//label[contains(@class,'x-placeholder-label x-placeholder-label-default x-form-empty-field x-form-empty-field-default')]";
		                                                  		
		String clickFieldValue2 = "//input[contains(@class,'x-tagfield-input-field x-form-empty-field x-form-empty-field-default')]";
		
		String enterFieldValue2 = "//input[contains(@class,'x-form-field x-form-empty-field x-form-empty-field-default x-form-text x-form-text-default ')]";
		
		String FilterValueLnk = "//label[contains(@class,'x-placeholder-label x-placeholder-label-default x-form-empty-field x-form-empty-field-default')]";
		
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
		
		String preFeedRule = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[9]";
		
		String createRequest="(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[10]";
		
		String fetchEntity="(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[11]";
		
		String elementScheduler = "(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[12]";
		
		String sequence="(//div[contains(@class,'x-action-col-0  aegrid-rowAdd')])[2]//ancestor::tr//td[4]";
		
		String confirmButton = "//span[contains(text(),'Confirm') and contains(@class,'x-btn-inner x-btn-inner-aebtnPrimary-medium')]";
		
//		String reconJobStatus = ".//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.statusIndex+"]";
		
//		String activeRecords = "(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.activeIndex+"]";
		
//		String errorRecords = "(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.errorIndex+"]" ; 
		
//		String errorMessage = "(.//div[@class='x-grid-cell-inner ' and text()='"+AGlobalComponents.jobName+"']//ancestor::tr//td["+AGlobalComponents.messageIndex+"]";
		
		String settingsIcon = "//span[contains(@class,'x-btn-icon-el-aetextlink-medium aegrid-setting ')]";
		
		String selectViewLnk = "//span[contains(@class,' x-menu-item-indent-no-separator x-menu-item-indent-right-arrow') and text()='Select View']";
		
		String roleReconViewLnk =  "//span[contains(@class,'x-menu-item-indent-no-separator') and text()='Role Recon View']";
		
		String roleMonitorViewLnk = "//span[contains(@class,'x-menu-item-indent-no-separator') and text()='Role Monitor View']";
		
		String userReconViewLnk =  "//span[contains(@class,'x-menu-item-indent-no-separator') and text()='User Recon View']";
		
		String ReconViewLnk =  "//span[contains(@class,'x-menu-item-indent-no-separator') and text()='Recon View']";
		
		String refreshIconLnk = "//a[contains(@data-qtip,'Reload')]//span[contains(@class,'x-btn-icon-el x-btn-icon-el-aetextlink-medium  ')]";
		
		String downloadLink = "//span[contains(@class,' x-btn-icon-el-aetextlink-medium aegrid-download')]";
		
		String reconMonitorElements = "//label[contains(@class,'x-component x-box-item x-toolbar')]";
		
		String cancelInSettingsView = "(//span[contains(@id,'button') and text()='Cancel'])[2]";
		
		String closeInSettingsView = "//div[contains(@class,'x-tool-tool-el x-tool-img x-tool-close')]";
		
		String sharedLabelInSettingsVew = "//label[contains(text(),'Shared')]";
		
		String preferredLableInSettingsView =  "//label[contains(text(),'Preferred')]";
		
		String nameInSettingsView = "//span[contains(text(),'Name')]";
		
		String createNewView = "//span[text()='Create New View']";
		
		String saveLayout = "//div[contains(text(),'Save Current Layout')]";
		
		String nameInput = "(//input[contains(@id,'input') and contains(@class,'x-form-field x-form-required-field')])[2]";
		
		String confirmInSettingsView = "(//span[contains(text(),'Confirm') and contains(@class,'x-btn-inner x-btn-inner-aebtnPrimary-medium')])[2]";
		
		String deleteIconReconSetup = "//div[text()='"+AGlobalComponents.jobName+"']/parent::td/preceding-sibling::td//div[contains(@class,'aegrid-rowMinus')]";
		
		String searchInReconMonitor = "//input[contains(@class,'x-form-empty-field-default x-form-text x-form-text-default')]";
		
		String searchInReconRemediation = "//input[contains(@class,'x-form-empty-field-default x-form-text x-form-text-default')]";
		
		String searchInReconSetup = "//input[contains(@class,'x-form-empty-field-default x-form-text x-form-text-default')]";
		
		String filterExpand = "//span[contains(@class,'x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-rowMinus')]";

		String trialJobRadioButton = "//label[text()='Trial']/parent::div//input[@type='radio']";

		String emptyReconSetUpGrid = "//div[contains(@class,'emptyGridMsg')]";
		
		String addRecordsLnk = "//a[normalize-space(text())='Click here to Add']";

		String rerunIconReconSetup = "//span[contains(@class,' x-btn-icon-el-aetextlink-medium aegrid-rerun ')]";

		String endDateToRerunTheJob = "//input[contains(@id,'baseDateTime')]";

		String reconMonitorViewLnk = "//span[contains(@class,'x-menu-item-indent-no-separator') and text()='Recon Monitor View']";
		
		String reconRemediationViewLnk = "//span[contains(@class,'x-menu-item-indent-no-separator') and text()='Recon Remediation View']";

		String jobNameLocator = "//div[text()='"+AGlobalComponents.jobName+"']";
		
}
