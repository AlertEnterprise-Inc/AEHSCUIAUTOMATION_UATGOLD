package ObjectRepository;

public interface AdminObjects {
	
	String adminTabBtn =".//*[@class='x-btn-inner x-btn-inner-aeTopMenuButton-small' and text()='Admin']";
	
	String adminProvMonitorLnk =".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Provisioning Monitor']";
	
	String adminProvMonitorFilterBtn =".//*[@data-ref='btnIconEl' and @class='x-btn-icon-el x-btn-icon-el-aetextlink-medium aegrid-filter ']";
	
	String adminProvMonitorChooseFieldTxt =".//input[@data-ref='inputEl' and @placeholder='Choose Field']";
	
	String adminProvMonitorReloadBtn =".//*[@data-ref='btnIconEl' and contains(@style,'reload')]";
	
	String adminProvManageJobsLnk = ".//*[@class='x-btn-inner x-btn-inner-aeThirdMenuButton-small' and text()='Manage Jobs']";

}
