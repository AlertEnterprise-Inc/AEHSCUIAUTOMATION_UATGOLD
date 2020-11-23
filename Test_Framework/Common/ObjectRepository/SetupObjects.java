package ObjectRepository;

public interface SetupObjects
{	
	String setupTabLnk = ".//*[@id='tabmenu']//span[contains(text(),'Setup')]";
	
	/** SETUP TAB OBJECTS**/
	
	String setupLeftMenuManualConfigurationLnk = ".//a[@target='_self' and text()='Manual Configuration']";
	String setupLeftMenuOrganizationDataLnk = ".//a[@target='_self' and text()='Organization Data']";
	String setupLeftMenuJobSchedulerLnk = ".//a[@target='_self' and text()='Job Scheduler']";
	String setupLeftMenuUserManagementLnk = ".//a[@target='_self' and text()='User Management']";
	String setupLeftMenuLocationLnk = ".//a[@target='_self' and text()='Location']";
	String setupLeftMenuSchedulerLnk = ".//a[@target='_self' and text()='Scheduler']";
	String setupLeftMenuRolesLnk = ".//a[@target='_self' and text()='Roles']";
	String setupSchedulerCreateNewJobBtn = ".//button[@ng-click='schedulerHomePageVm.createNewJob()']";
	String setupSchedulerJobTypeDdn = ".//md-select[@name='jobType']";
	String setupSchedulerJobNameTxt = ".//input[@name='jobName']";
	String setupSchedulerJobParametersMenuBtn = ".//div[@class='title ng-binding' and @ng-click='toggleCollapsibleItem()' and text()='Job Parameters']";
	String setupSchedulerReconciliationForDdn = ".//md-select[@aria-label='Reconciliation For']";
	String setupSchedulerReconciliationTypeDdn = ".//md-select[@aria-label='Reconciliation Type']";
	String setupSchedulerReconciliationTriggersDdn = ".//md-select[@aria-label='Reconciliation Triggers']";
	String setupSchedulerSaveJobBtn = ".//button[@ng-click='newSchedulerVm.saveJob()']";
	String setupSchedulerJobScheduledLbl = ".//div[contains(text(),'Job Scheduled with Id')]";
	String setupLocationPodTitleLbl = ".//div[@class='fab-pod-title' and text()='Locations']";
	String setupMiddleMenuAirportsExpanderBtn = ".//a[@class='ui-state-active' and text()='AIRPORTS']/parent::li/span[@data-role='expander']";
	String setupMiddleMenuLAXLbl = ".//a[@href='#' and text()='LAX']";
	String setupLocationNameLAXLbl = ".//span[@id='name' and text()='LAX']";
	String setupLocationModifyBtn = ".//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect updateButton' and text()='Modify']";
	String setupDefaultFromParentChk = ".//input[@id='defaultFromParent' and @type='checkbox']";
	String setupNoMapWithJobRoleChk = ".//input[@id='NoMapWithJobRole' and @type='checkbox']";
	String setupModifyLocationSaveBtn = ".//button[@class='mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange saveButton']";
	String setupSuccessfullyUpdatedLocationLbl = ".//div[@class='fab-message-info' and contains(text(),'Successfully updated the location')]";
	String setupConfirmPopupOKBtn = ".//div[@class='confirm_buttons fabric-confirmpopup-buttons']/button[text()='OK']";
	String setupRolesPermissionsSaveBtn = ".//*[@type='submit' and @value='Save']";
	String setupRolesPermissionsRoleNameTxt = ".//input[@class='textinput' and @name='roleName']";
	String setupRolesPermissionsSearchBtn = ".//input[@type='submit' and @name='search']";
	String setupRolesPermissionsRoleChk = ".//td[1]/input[@type='checkbox' and @name='sortableGridView:closeContainer:minMaxContainer:scrollid:gridGroup']";
	String setupRolesPermissionsModifyBtn = ".//*[@class='tblbtn' and @value='Modify']";
	String setupRolesPermissionsRoleModifiedSuccessfullyMsgLbl = ".//span[@class='feedbackPanelINFO' and text()='Role modified successfully']";
	
	
	
	
}