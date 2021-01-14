package ObjectRepository;

import CommonClassReusables.ByAttribute;

public interface HomeObjects {

	/** Home **/
	String homeTabLnk =".//*[@id='tabmenu']//span[contains(text(),'Home')]";
	
	String homeWorkItemsForApprovalTbl = ".//table[@class='ui-iggrid-headertable']";
	
	String homeApplicantPreEnrollmentApproveBtn = ".//button[@id='message' and @class='approveAlert mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-orange ng-binding']";

	String homeGoBackToInboxBtn = ".//button[text()='Go Back To Inbox']";
	
	//*********** NEW PLATFORM ***********
	
		String homeTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Home']";
		String myRequestsTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='My Requests']";
		String inboxTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Inbox']";
		String homeAccessRequestLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Access Request']";
		String homeInboxLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Inbox']";
		
		String homeAccessRequestPageLbl =".//*[contains(@id,'label') and text()='Access Request']";
		String homeAccessRequestCreateBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Create']";
		String homeAccessRequestSelfRdb =".//span[@data-ref='labelTextEl' and text()='Request For:']//ancestor::div[@class='x-container x-form-checkboxgroup x-form-item x-form-item-default x-box-item x-container-default x-vbox-form-item']//label[@data-ref='boxLabelEl' and text()='Self']";
		String homeAccessRequestOthersRdb =".//span[@data-ref='labelTextEl' and text()='Request For:']//ancestor::div[@class='x-container x-form-checkboxgroup x-form-item x-form-item-default x-box-item x-container-default x-vbox-form-item']//label[@data-ref='boxLabelEl' and text()='Others']";
		String homeAccessRequestSearchLocationTxt =".//input[@data-ref='inputEl' and @placeholder='Search or Filter by Location']";
		String homeAccessRequestSearchAccessTxt =".//span[@data-ref='btnInnerEl' and text()='Review']//ancestor::div[@class='x-tab-bar x-docked x-tab-bar-default x-horizontal x-tab-bar-horizontal x-tab-bar-default-horizontal x-top x-tab-bar-top x-tab-bar-default-top x-docked-top x-tab-bar-docked-top x-tab-bar-default-docked-top x-noborder-trl']//parent::div/div[@class='x-panel-body x-panel-body-default x-panel-body-default x-noborder-trbl']//input[@data-ref='inputEl' and @placeholder='Search or Filter by Location']";
		String homeAccessRequestCreateNextBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnSmallPrimary-small' and contains(text(),'Next')]";
		String homeAccessRequestCreatePreviousBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnSmallPrimary-small' and contains(text(),'Previous')]";
		String homeAccessRequestAddLocationBtn =".//*[contains(@id,'baseAccessLocationBox')]//div[@class='selecteditem btnadd' and text()='Add']";
		String homeAccessRequestReviewTab =".//*[@data-ref='btnInnerEl' and text()='Review']";
		String homeAccessRequestCommentsBtn =".//*[@data-ref='btnInnerEl' and text()='Comments']";
		String homeAccessRequestAttachmentsBtn =".//*[@data-ref='btnInnerEl' and text()='Attachments']";
		String homeAccessRequestUploadAttachmentBtn =".//*[@class='x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-upload ']";
		String homeAccessRequestAddCommentBtn =".//*[@data-ref='btnInnerEl' and text()='Add Comment']";
		String homeAccessRequestCloseDialogBtn =".//div[@data-qtip='Close Dialog']/div";
		String homeAccessRequestAddLocationSubmitBtn =".//*[@class='x-btn-inner x-btn-inner-aebtnPrimary-medium' and text()='Submit']";
		String homeAccessRequestAddLocationCancelBtn =".//a[@data-qtip='Cancel' and @aria-hidden='false']";
		String homeAccessRequestAddLocationChangesWillBeLostMsgLbl =".//*[contains(@id,'messagebox') and text()='You request changes will lost, do you want to proceed?']";
		
		String homeAccessRequestAddLocationReasonTxt =".//*[contains(@id,'baseComboBox') and @placeholder='Select Reason for request']";
		String homeAccessRequestAddLocationBusinessJustificationTxt =".//textarea[contains(@id,'baseTextArea')]";
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
		String homeInboxRequestInboxCompletedBtn = ".//span[@class='x-tree-node-text ' and text()='Completed']";
		String homeGenericRemoveRowBtn = ".//*[@data-ref='btnIconEl' and @class='x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-rowMinus ']";
		String homeGenericAddRowBtn = ".//*[@data-ref='btnIconEl' and @class='x-btn-icon-el x-btn-icon-el-aebtnSecondary-medium aegrid-rowAdd ']";
		
	
}












