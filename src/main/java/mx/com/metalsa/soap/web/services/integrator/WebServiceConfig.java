package mx.com.metalsa.soap.web.services.integrator;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Value("${userId}")
    @NonNull
    private String userId;
    @Value("${password}")
    @NonNull
    private String password;

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "integrator")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema integratorSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("IntegratorPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://metalsa.com.mx/soap/web/services/integrator/schemas");
        wsdl11Definition.setSchema(integratorSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema integratorSchema() {
        return new SimpleXsdSchema(new ClassPathResource("integrator.xsd"));
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("mx.com.metalsa.soap.service.reports.wsdl");
        return marshaller;
    }

    @Bean
    public ReportClient reportClient(Jaxb2Marshaller marshaller) {
        ReportClient client = new ReportClient();
        client.setUserId(userId);
        client.setPassword(password);
        client.setDefaultUri("https://hcun-dev3.fa.us2.oraclecloud.com:443/xmlpserver/services/v2/ReportService");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}