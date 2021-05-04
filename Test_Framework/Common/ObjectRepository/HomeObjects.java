package ObjectRepository;

import CommonClassReusables.ByAttribute;

public interface HomeObjects {

	/** Home **/
	String homeTabLnk =".//*[@id='tabmenu']//span[contains(text(),'Home')]";
	
	String homeWorkItemsForApprovalTbl = ".//table[@class='ui-iggrid-headertable']";
	
	String homeApplicantPreEnrollmentApproveBtn = ".//button[@id='message' and @class='approveAlert mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange ng-binding']";

	String homeGoBackToInboxBtn = ".//button[text()='Go Back To Inbox']";
	
	//*********** NEW PLATFORM ***********
	
		String homeAnnouncementPopUpCloseLnk ="//div[text()='Announcement']//parent::div//following-sibling::div//div[@class='x-tool-tool-el x-tool-img x-tool-close ']";
		String homeTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Home']";
		String myRequestsTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='My Requests']";
		String inboxTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Inbox']";
		String homeAccessRequestLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Access Request']";
		String homeMyRequestsLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='My Requests']";
		String homeMyRequestLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and contains( text(),'My Requests')]";
		String homeInboxLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Inbox']";
		String homeDashboardLnk = ".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and contains( text(),'Dashboard')]";
		String homeMyDashboardBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='My Dashboard']";
		
		String homeAccessRequestPageLbl =".//*[contains(@id,'label') and text()='Access Request']";
		String homeAccessRequestCreateBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Create']";
		String homeAccessRequestSelfRdb =".//span[@data-ref='labelTextEl' and text()='Request For:']//ancestor::div[@class='x-container x-form-checkboxgroup x-form-item x-form-item-default x-box-item x-container-default x-vbox-form-item']//label[@data-ref='boxLabelEl' and text()='Self']";
		String homeAccessRequestOthersRdb =".//span[@data-ref='labelTextEl' and text()='Request For:']//ancestor::div[@class='x-container x-form-checkboxgroup x-form-item x-form-item-default x-box-item x-container-default x-vbox-form-item']//label[@data-ref='boxLabelEl' and text()='Others']";
		String homeAccessRequestSearchLocationTxt =".//input[@data-ref='inputEl' and @placeholder='Search or Filter by Location']";
		String homeAccessRequestSearchPositionTxt =".//input[@data-ref='inputEl' and @placeholder='Search or Filter by Position']";
		String homeAccessRequestSearchApplicationTxt =".//input[@data-ref='inputEl' and @placeholder='Search or Filter by Application']";
		String homeAccessRequestSearchITSystemTxt =".//input[@data-ref='inputEl' and @placeholder='Search or Filter by IT System']";
		String homeAccessRequestSearchAccessTxt =".//div[@class='tab-title' and text()='Review']//ancestor::div[@class='x-tab-bar x-docked x-tab-bar-default x-horizontal x-tab-bar-horizontal x-tab-bar-default-horizontal x-top x-tab-bar-top x-tab-bar-default-top x-docked-top x-tab-bar-docked-top x-tab-bar-default-docked-top x-noborder-trl']//parent::div/div[@class='x-panel-body x-panel-body-default x-panel-body-default x-noborder-trbl']//input[@data-ref='inputEl' and @placeholder='Search or Filter by Location']";
		String homeAccessRequestSearchPositionAccessTxt ="//div[@class='tab-title' and text()='Review']//ancestor::div[@class='x-tab-bar x-docked x-tab-bar-default x-horizontal x-tab-bar-horizontal x-tab-bar-default-horizontal x-top x-tab-bar-top x-tab-bar-default-top x-docked-top x-tab-bar-docked-top x-tab-bar-default-docked-top x-noborder-trl']//parent::div/div[@class='x-panel-body x-panel-body-default x-panel-body-default x-noborder-trbl']//input[@data-ref='inputEl' and @placeholder='Search or Filter by Position']";
		String homeAccessRequestSearchApplicationAccessTxt ="//div[@class='tab-title' and text()='Review']//ancestor::div[@class='x-tab-bar x-docked x-tab-bar-default x-horizontal x-tab-bar-horizontal x-tab-bar-default-horizontal x-top x-tab-bar-top x-tab-bar-default-top x-docked-top x-tab-bar-docked-top x-tab-bar-default-docked-top x-noborder-trl']//parent::div/div[@class='x-panel-body x-panel-body-default x-panel-body-default x-noborder-trbl']//input[@data-ref='inputEl' and @placeholder='Search or Filter by Application']";
		String homeAccessRequestSearchITAccessLbl =".//label[contains(@id,'placeholderLabel') and text()='Search or Filter by IT Access']";
		String homeAccessRequestCreateNextBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnSmallPrimary-small' and contains(text(),'Next')]";
		String homeAccessRequestCreateAddBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnSmallPrimary-small' and contains(text(),'Add')]";
		String homeAccessRequestCreatePreviousBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnSmallPrimary-small' and contains(text(),'Previous')]";
		String homeAccessRequestAddLocationBtn =".//*[contains(@id,'baseAccessLocationBox')]//div[@class='selecteditem btnadd' and text()='Add']";
		String homeAccessRequestReviewTab =".//div[@class='tab-title' and contains(text(),'Review')]";
		String homeAccessRequestCommentsBtn =".//*[@data-ref='btnInnerEl' and text()='Comments']";
		String homeAccessRequestAttachmentsBtn =".//*[@data-ref='btnInnerEl' and text()='Attachments']";
		String homeAccessRequestUploadAttachmentBtn =".//*[@class='x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-upload ']";
		String homeAccessRequestAddCommentBtn =".//*[@data-ref='btnInnerEl' and text()='Add Comment']";
		String homeAccessRequestCloseDialogBtn =".//div[@data-qtip='Close Dialog']/div";
		String homeAccessRequestAddLocationSubmitBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Submit']";
		String homeAccessRequestAddPositionSubmitBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Submit']";
		String homeAccessRequestAddApplicationSubmitBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Submit']";
		String homeAccessRequestAddLocationCancelBtn =".//a[@data-qtip='Cancel' and @aria-hidden='false']";
		String homeAccessRequestAddLocationChangesWillBeLostMsgLbl =".//*[contains(@id,'messagebox') and text()='You request changes will lost, do you want to proceed?']";
		String homeAccessRequestHistoryBtn = "//*[@class='x-btn-inner x-btn-inner-aebtnSmallSecondary-small' and text()='History']";
		String homeAccessRequestHistoryPopUpCloseIcn= "//div[text()='History']//parent::div//following-sibling::div//*[@class='x-tool-tool-el x-tool-img x-tool-close ']";
		
		String homeAccessRequestAddLocationReasonTxt =".//*[contains(@id,'baseComboBox') and @placeholder='Select Reason for request']";
		String homeAccessRequestAddLocationBusinessJustificationTxt =".//textarea[contains(@id,'baseTextArea')]";
		String homeAccessRequestAddLocationJustificationTxt =".//*[text()='Justification']//following::div[contains(@id,'baseTextArea') and @class='x-form-text-wrap x-form-text-wrap-default']//textarea";
		String homeAccessRequestAddLocationGroupNameTxt =".//*[contains(@id,'fieldset') and @data-ref='tbody']//input[contains(@id,'baseComboBox')]";
		String homeAccessRequestAddLocationConfirmBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Confirm']";
		
		String homeInboxRequestApproveBtn = ".//button[contains(@data-qtip,'Approve')]";
		String homeInboxRequestRejectBtn = ".//button[contains(@data-qtip,'Reject')]";
		String homeInboxCommentRequiredLbl = ".//*[@class='x-title-text x-title-text-default x-title-item' and text()='Comment Required']";
		String homeInboxCommentRequiredConfirmBtn = ".//*[@data-ref='btnInnerEl' and text()='Confirm']";
		String homeInboxRequestWorkflowApprovedLbl = ".//td[@data-columnid='workflowActionColumn']//div[text()='Approved']";
		String homeInboxRequestWorkflowViewStatusBtn = ".//td[@data-columnid='workflowActionColumn']//button[@data-qtip='View Status']";
		String homeInboxRequestWorkflowRejectedLbl = ".//td[@data-columnid='workflowActionColumn']//div[text()='Rejected']";
		String homeInboxRequestWorkflowViewStatusCancelBtn = ".//div[contains(@class,'window')]//span[@data-ref='btnInnerEl' and text()='Cancel']";
		String homeInboxRequestInboxExpandBtn = ".//span[contains(@class,'hamburgerexpand')]";
		String homeInboxRequestInboxCollapseBtn = ".//span[@class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-hamburger ']";
		String homeInboxRequestInboxCompletedBtn = ".//span[@class='x-tree-node-text ' and text()='Completed']";
		String homeGenericRemoveRowBtn = ".//*[@data-ref='btnIconEl' and @class='x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-rowMinus ']";
		String homeGenericAddRowBtn = ".//*[@data-ref='btnIconEl' and @class='x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-rowAdd ']";
		String homeInboxRequestMenuIconLnk = "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]";
		String homeInboxRequestHistoryOptionInMenuLnk = "(//*[text()='History'])[2]";
		String homeInboxRequestCloseHistoryPopUpWindowLnk = "//div[@class='x-tool-tool-el x-tool-img x-tool-close ']";
		
		String createBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Create']";
		String wellnessCheckLnk = "//div[@class='x-component location-chooser-view x-box-item x-component-default x-scroller']//p[text()='Wellness Check']";
		String physicalAccessLnk = "//div[@class='x-component location-chooser-view x-box-item x-component-default x-scroller']//p[text()='Physical Access']";
		String requestLocationAccessLnk = "//div[@class='x-component location-chooser-view x-box-item x-component-default x-scroller']//p[text()='Request Location Access']";
		String homeCreateRequestPositionAccessPod = "//div[@class='x-component location-chooser-view x-box-item x-component-default x-scroller']//p[text()='Position Access']";
		String homeCreateRequestApplicationAccessPod = "//div[@class='x-component location-chooser-view x-box-item x-component-default x-scroller']//p[text()='Application Access']";
		String homeCreateRequestITAccessPod = "//div[@class='x-component location-chooser-view x-box-item x-component-default x-scroller']//p[text()='IT Access']";
		String acknowledgementCheckbox = "(//input[@type='checkbox'])[1]";
		String submitBtn = "//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Submit']";
		String questionOneNo = "//*[@class='x-form-item-label-text' and contains(text(),'tested positive')]//ancestor::label//following-sibling::div//label[text()='No']//preceding-sibling::span//input";
		String questionTwoNo = "//*[@class='x-form-item-label-text' and contains(text(),'COVID-19 symptoms')]//ancestor::label//following-sibling::div//label[text()='No']//preceding-sibling::span//input";
		String questionThreeNo = "//*[@class='x-form-item-label-text' and contains(text(),'temperature')]//ancestor::label//following-sibling::div//label[text()='No']//preceding-sibling::span//input";
		String questionOneYes = "//*[@class='x-form-item-label-text' and contains(text(),'tested positive')]//ancestor::label//following-sibling::div//label[text()='Yes']//preceding-sibling::span//input";
		String userRequestHeader = "//div[@class='x-box-target']//label[text()='User Request']";
		String requestNoLnk = "(//tr[@class='  x-grid-row'])[1]//*[contains(text(),'ACR-')]";
		String homeAccessRequestOtherRequestsPod = "//div[@class='x-component location-chooser-view x-box-item x-component-default x-scroller']//p[text()='Other Requests']";
		String selectRequestType = "//*[contains(@id,'baseComboBoxRemote') and @placeholder ='Select Request Type']";
		String uploadImgBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnSecondary-medium' and text()='Upload']";
		String cropAndSaveBtn = ".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Crop & Save']";
		String homeMyRequestsActualPhotoLnk = "//div[@class='jcrop-tracker']";
		String employeeType = "//input[contains(@id,'baseBusObjType') and @placeholder='Select Employee Type']";
		String homeMyRequestsUploadedImageLnk = "//*[@class='x-img x-box-item x-img-default']";
		String homeMyRequestsUploadedImageSectionLnk = "//label[@class='x-component x-box-item x-component-activitySubtext' and text()='photo']";
		String homeAccessRequestFirstNameTxt = "//*[contains(@id,'baseText') and @placeholder='Enter First Name']";
		String homeAccessRequestLastNameTxt = "//*[contains(@id,'baseText') and @placeholder='Enter Last Name']";
		String homeAccessRequestEmpTypeTxt = "//*[contains(@id,'baseBusObjType') and @placeholder='Select Employee Type']";
		String homeAccessRequestCountryDdn = "//*[contains(@id,'baseComboBoxRemote') and @placeholder='Select Country']";
		String homeAccessRequestJobTitle = "//*[contains(@id,'baseText') and @placeholder='Enter Job Title']";
		String homeAccessRequestDepartmentDdn = "//*[contains(@id,'baseComboBoxRemote') and @placeholder='Select Department Name']";
		String homeAccessRequestLocationDdn = "//*[contains(@class,'x-placeholder-label') and text()='Select Location']";
		String homeAccessRequestAccessListGrid = "//*[@class='x-box-target']//label[text()='Access List']";
		String homeAccessRequestBadgeListGrid = "//*[@class='x-box-target']//label[text()='Badge List']";
		String homeAccessRequestSystemListGrid = "//*[@class='x-box-target']//label[text()='System List']";
		String homeAccessRequestPrerequisiteList = "//*[@class='x-box-target']//label[text()='Prerequisite List']";
		String homeAccessRequestApproveButton = "//*[text()='Approve']";
		String homeOpenRequestsLnk = ".//*[@class='activeReq' and contains( text(),'Open Requests')]";
		String homeAccessRequestSelectBadgeDDn = "//div[@class='x-grid-cell-inner ' and text()='Select Badge']";
		String homeAccessRequestYesButtonOnPopUpWindow = "//*[@class='x-btn-inner x-btn-inner-default-small' and text()='Yes']";
		String TempWorkerTab = ".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Temp Worker']";
		String TempWorkerOnboardingLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Temp Worker Onboarding']";
		String TempWorkerModificationLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Temp Worker Modification']";
		String TempWorkerOffboardingLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Temp Worker Offboarding']";
		String TempWorkerRehireLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Temp Worker Rehire']";
		String ComparisonButton = "//*[@class='x-btn-inner x-btn-inner-aebtnSmallSecondary-small' and text()='Comparison']";
		String homeMyRequestsHeader = "//*[@class='x-box-target']//label[text()='My Requests']";
		String homeAccessRequestWorkFlowBtn = "//td[contains(@class,' x-grid-cell-workflowActionColumn x-grid-cell-last')]//button[@class='aegrid-view']";
		String workflowStatusHeaderInWFPopupWindow = "//div[contains(@id,'header-title') and contains(text(),'Workflow Status')]";
		String closeIconInWFStatusWindow = "//div[@class='x-tool-tool-el x-tool-img x-tool-close ']";
		String homeAccessRequestSelectTerminationReasonTxt = "//*[contains(@id,'baseComboBox') and @ placeholder='Select Termination Reason']";
		String homeAccessRequestSelectRehireReasonTxt = "//input[contains(@id,'baseText') and @class='x-form-field x-form-text x-form-text-default  x-form-empty-field x-form-empty-field-default']";
		String homeAccessRequestValidToDate = "//*[contains(@id,'baseDateTime') and @placeholder='Select Valid To']";
		String homeRequestInboxCloseHistoryWindowIcn = "//div[@class='x-tool-tool-el x-tool-img x-tool-close ']";
		String homeRequestInboxMenuIcn = "(//*[contains(@id,'button') and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-menu '])[2]";
	    String homeRequestInboxHistoryMenuLnk = "(//*[text()='History'])[2]";
	    String homeRequestInboxUserIdTxt = "//input[contains(@id,'baseText') and @placeholder='Enter User ID']";
	    String homeMyRequestsActionMenuBtn = "//*[contains(@id,'button') and text()='Actions']";
	    String homeMyRequestsApprovalMenuItemLnk = "//*[contains(@id,'menuitem') and text()='Approve']";
	    String homeMyRequestsRequesterNameLnk = ".//span[@class='x-btn-wrap x-btn-wrap-aetextlink-medium x-btn-arrow x-btn-arrow-right' and @role='presentation']";
	    String homeMyRequestSearchUserTxt = ".//*[contains(@class,'x-placeholder-label') and text()='Search Identity or User']";

}












