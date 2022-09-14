package mx.com.metalsa.soap.web.services.integrator;

import mx.com.metalsa.soap.service.reports.wsdl.RunReportResponse;
import mx.com.metalsa.soap.web.services.integrator.db.entities.TblControl;
import mx.com.metalsa.soap.web.services.integrator.db.repository.TblControlRepository;
import mx.com.metalsa.soap.web.services.integrator.db.repository.TblCoreIntegratorControlRepository;
import mx.com.metalsa.soap.web.services.integrator.schemas.GetIntegratorRequest;
import mx.com.metalsa.soap.web.services.integrator.schemas.GetIntegratorResponse;
import mx.com.metalsa.soap.web.services.integrator.schemas.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Endpoint
public class IntegratorEndpoint {
    private static final String NAMESPACE_URI = "http://metalsa.com.mx/soap/web/services/integrator/schemas";

    private Logger log = LoggerFactory.getLogger(IntegratorEndpoint.class);

    private TblControlRepository tblControlRepository;
    private TblCoreIntegratorControlRepository tblCoreIntegratorControlRepository;
    private ReportClient reportClient;

    @Autowired
    public IntegratorEndpoint(TblControlRepository tblControlRepository,
                              TblCoreIntegratorControlRepository tblCoreIntegratorControlRepository,
                              ReportClient reportClient) {

        this.tblControlRepository = tblControlRepository;
        this.tblCoreIntegratorControlRepository = tblCoreIntegratorControlRepository;
        this.reportClient = reportClient;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getIntegratorRequest")
    @ResponsePayload
    public GetIntegratorResponse getIntegrator(@RequestPayload GetIntegratorRequest request) {
        GetIntegratorResponse response = new GetIntegratorResponse();
        Response rs = new Response();

        // query the data from TBL_CONTROL table
        Optional<TblControl> controlData = tblControlRepository.findOneByTableName(request.getObjectName());
        TblControl tblControl = controlData.get();

        // call the ws runReport from ReportService
        RunReportResponse runReportResponse = reportClient.runReport(tblControl.getReportAbsolutePath());
        //log.info(new String(runReportResponse.getRunReportReturn().getReportBytes(), StandardCharsets.UTF_8));

        // call the procedure from Oracle Database
        Map<String, Object> spResult = tblCoreIntegratorControlRepository.genericIntegrator(tblControl.getModule(),
                tblControl.getTableName(), new String(runReportResponse.getRunReportReturn().getReportBytes(), StandardCharsets.UTF_8));

        // get the result of the service Integrator
        String responseCode = spResult.get("O_RESPONSE_CODE").toString();
        log.info("responseCode = " + responseCode);
        String responseMessage = spResult.get("O_RESPONSE_MESSAGE").toString();
        log.info("responseMessage = " + responseMessage);

        rs.setCode(Integer.parseInt(spResult.get("O_RESPONSE_CODE").toString()));
        rs.setMessage(spResult.get("O_RESPONSE_MESSAGE").toString());
        response.setResponse(rs);

        return response;
    }
}