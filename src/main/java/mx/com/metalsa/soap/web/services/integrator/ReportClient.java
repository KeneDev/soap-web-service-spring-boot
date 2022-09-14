package mx.com.metalsa.soap.web.services.integrator;

import mx.com.metalsa.soap.service.reports.wsdl.ReportRequest;
import mx.com.metalsa.soap.service.reports.wsdl.RunReport;
import mx.com.metalsa.soap.service.reports.wsdl.RunReportResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


public class ReportClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(ReportClient.class);

    private String userId;
    private String password;
    private static final String FORMAT = "xml";

    public RunReportResponse runReport(String absolutePath){
        // set credentials
        RunReport runReport = new RunReport();
        runReport.setUserID(userId);
        log.info("userId = " + userId);

        runReport.setPassword(password);
        log.info("password = " + password);

        ReportRequest request = new ReportRequest();
        request.setReportAbsolutePath(absolutePath);
        request.setAttributeFormat(FORMAT);
        request.setSizeOfDataChunkDownload(-1);
        runReport.setReportRequest(request);

        log.info("Requesting report for path: " + absolutePath);

        RunReportResponse response = (RunReportResponse)  getWebServiceTemplate()
                .marshalSendAndReceive("https://hcun-dev3.fa.us2.oraclecloud.com/xmlpserver/services/v2/ReportService", runReport);
        return response;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
