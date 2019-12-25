package com.heshaowei.myproj.flowable.display.service;

import com.heshaowei.myproj.flowable.display.domain.ServerConfig;
import com.heshaowei.myproj.flowable.display.service.exception.FlowableServiceException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class ProcessDefinitionService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private FlowableClientService clientUtil;

    @Autowired
    private ServerConfig serverConfig;

    public BpmnModel getProcessDefinitionModel(String processDefinitionId) {
        HttpGet get = new HttpGet(this.clientUtil.getServerUrl(serverConfig, "repository/process-definitions/" + processDefinitionId + "/resourcedata"));
        return executeRequestForXML(get, 200);
    }

    protected BpmnModel executeRequestForXML(HttpUriRequest request, int expectedStatusCode) {
        FlowableServiceException exception = null;
        CloseableHttpClient client = this.clientUtil.getHttpClient();

        try {
            try {
                CloseableHttpResponse response = client.execute(request);
                Throwable var7 = null;

                try {
                    InputStream responseContent = response.getEntity().getContent();
                    XMLInputFactory xif = XMLInputFactory.newInstance();
                    InputStreamReader in = new InputStreamReader(responseContent, "UTF-8");
                    XMLStreamReader xtr = xif.createXMLStreamReader(in);
                    BpmnModel bpmnModel = (new BpmnXMLConverter()).convertToBpmnModel(xtr);
                    boolean success = response.getStatusLine() != null && response.getStatusLine().getStatusCode() == expectedStatusCode;
                    if (success) {
                        BpmnModel var14 = bpmnModel;
                        return var14;
                    }

                    exception = new FlowableServiceException("An error occurred while calling Flowable: " + response.getStatusLine());
                } catch (Throwable var43) {
                    var7 = var43;
                    throw var43;
                } finally {
                    if (response != null) {
                        if (var7 != null) {
                            try {
                                response.close();
                            } catch (Throwable var42) {
                                var7.addSuppressed(var42);
                            }
                        } else {
                            response.close();
                        }
                    }

                }
            } catch (Exception var45) {
                LOGGER.warn("Error consuming response from uri {}", request.getURI(), var45);
                exception = this.clientUtil.wrapException(var45, request);
            }
        } catch (Exception var46) {
            LOGGER.error("Error executing request to uri {}", request.getURI(), var46);
            exception = this.clientUtil.wrapException(var46, request);
        } finally {
            try {
                client.close();
            } catch (Exception var41) {
                LOGGER.warn("Error closing http client instance", var41);
            }

        }

        if (exception != null) {
            throw exception;
        } else {
            return null;
        }
    }
}
