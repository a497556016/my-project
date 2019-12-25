package com.heshaowei.myproj.flowable.display.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletResponse;

import com.heshaowei.myproj.flowable.display.domain.ServerConfig;
import com.heshaowei.myproj.flowable.display.service.exception.FlowableServiceException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FlowableClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlowableClientService.class);
    protected static final String[] PAGING_AND_SORTING_PARAMETER_NAMES = new String[]{"sort", "order", "size"};
    public static final String DEFAULT_FLOWABLE_CONTEXT_ROOT = "flowable-rest";
    public static final String DEFAULT_FLOWABLE_REST_ROOT = "service";

    @Autowired
    protected ObjectMapper objectMapper;
    @Value("${flowable.admin.app.security.preemptive-basic-authentication:false}")
    boolean preemptiveBasicAuthentication;

    public FlowableClientService() {
    }

    public CloseableHttpClient getHttpClient(){
        return getHttpClient(null, null);
    }

    public CloseableHttpClient getHttpClient(String userName, String password) {
        SSLConnectionSocketFactory sslsf = null;

        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial((KeyStore)null, new TrustSelfSignedStrategy());
            sslsf = new SSLConnectionSocketFactory(builder.build(), new HostnameVerifier() {
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        } catch (Exception var7) {
            LOGGER.warn("Could not configure HTTP client to use SSL", var7);
        }

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        if(null != userName && null != password) {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

            if (this.preemptiveBasicAuthentication) {
                String auth = userName + ":" + password;
                httpClientBuilder.setDefaultHeaders(Collections.singletonList(new BasicHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8)))));
            }
        }

        if (sslsf != null) {
            httpClientBuilder.setSSLSocketFactory(sslsf);
        }

        return httpClientBuilder.build();
    }

    public JsonNode executeRequest(HttpUriRequest request) {
        return this.executeRequest(request, null, null, 200);
    }

    public JsonNode executeRequest(HttpUriRequest request, String userName, String password) {
        return this.executeRequest(request, userName, password, 200);
    }

    public JsonNode executeRequest(HttpUriRequest request, String userName, String password, int expectedStatusCode) {
        FlowableServiceException exception = null;
        CloseableHttpClient client = this.getHttpClient(userName, password);

        try {
            try {
                CloseableHttpResponse response = client.execute(request);
                Throwable var8 = null;

                try {
                    InputStream responseContent = response.getEntity().getContent();
                    String strResponse = IOUtils.toString(responseContent, "utf-8");
                    boolean success = response.getStatusLine() != null && response.getStatusLine().getStatusCode() == expectedStatusCode;
                    JsonNode bodyNode;
                    Object e;
                    if (success) {
                        bodyNode = this.objectMapper.readTree(strResponse);
                        e = bodyNode;
                        return (JsonNode)e;
                    }

                    bodyNode = null;

                    try {
                        bodyNode = this.objectMapper.readTree(strResponse);
                    } catch (Exception var44) {
                        e = var44;
                        LOGGER.debug("Error parsing error message", var44);
                    }

                    exception = new FlowableServiceException(this.extractError(bodyNode, "An error occurred while calling Flowable: " + response.getStatusLine()));
                } catch (Throwable var45) {
                    var8 = var45;
                    throw var45;
                } finally {
                    if (response != null) {
                        if (var8 != null) {
                            try {
                                response.close();
                            } catch (Throwable var43) {
                                var8.addSuppressed(var43);
                            }
                        } else {
                            response.close();
                        }
                    }

                }
            } catch (Exception var47) {
                LOGGER.warn("Error consuming response from uri {}", request.getURI(), var47);
                exception = this.wrapException(var47, request);
            }
        } catch (Exception var48) {
            LOGGER.error("Error executing request to uri {}", request.getURI(), var48);
            exception = this.wrapException(var48, request);
        } finally {
            try {
                client.close();
            } catch (Exception var42) {
                LOGGER.warn("Error closing http client instance", var42);
            }

        }

        if (exception != null) {
            throw exception;
        } else {
            return null;
        }
    }

    public JsonNode executeDownloadRequest(HttpUriRequest request, HttpServletResponse httpResponse, String userName, String password, int expectedStatusCode) {
        FlowableServiceException exception = null;
        CloseableHttpClient client = this.getHttpClient(userName, password);

        label296: {
            JsonNode bodyNode;
            try {
                try {
                    CloseableHttpResponse response = client.execute(request);
                    Throwable var9 = null;

                    try {
                        boolean success = response.getStatusLine() != null && response.getStatusLine().getStatusCode() == expectedStatusCode;
                        if (!success) {
                            bodyNode = null;
                            String strResponse = IOUtils.toString(response.getEntity().getContent(), "utf-8");

                            try {
                                bodyNode = this.objectMapper.readTree(strResponse);
                            } catch (Exception var43) {
                                LOGGER.debug("Error parsing error message", var43);
                            }

                            exception = new FlowableServiceException(this.extractError(bodyNode, "An error occurred while calling Flowable: " + response.getStatusLine()));
                            break label296;
                        }

                        httpResponse.setHeader("Content-Disposition", response.getHeaders("Content-Disposition")[0].getValue());
                        response.getEntity().writeTo(httpResponse.getOutputStream());
                        bodyNode = null;
                    } catch (Throwable var44) {
                        var9 = var44;
                        throw var44;
                    } finally {
                        if (response != null) {
                            if (var9 != null) {
                                try {
                                    response.close();
                                } catch (Throwable var42) {
                                    var9.addSuppressed(var42);
                                }
                            } else {
                                response.close();
                            }
                        }

                    }
                } catch (Exception var46) {
                    LOGGER.warn("Error consuming response from uri {}", request.getURI(), var46);
                    exception = this.wrapException(var46, request);
                    break label296;
                }
            } catch (Exception var47) {
                LOGGER.error("Error executing request to uri {}", request.getURI(), var47);
                exception = this.wrapException(var47, request);
                break label296;
            } finally {
                try {
                    client.close();
                } catch (Exception var41) {
                    LOGGER.warn("Error closing http client instance", var41);
                }

            }

            return bodyNode;
        }

        if (exception != null) {
            throw exception;
        } else {
            return null;
        }
    }

    public AttachmentResponseInfo executeDownloadRequest(HttpUriRequest request, String userName, String password) {
        return this.executeDownloadRequest(request, userName, password, 200);
    }

    public AttachmentResponseInfo executeDownloadRequest(HttpUriRequest request, String userName, String password, Integer... expectedStatusCodes) {
        FlowableServiceException exception = null;
        CloseableHttpClient client = this.getHttpClient(userName, password);

        label328: {
            Object contentDispositionFileName;
            try {
                try {
                    CloseableHttpResponse response = client.execute(request);
                    Throwable var8 = null;

                    try {
                        int statusCode = -1;
                        if (response.getStatusLine() != null) {
                            statusCode = response.getStatusLine().getStatusCode();
                        }

                        boolean success = Arrays.asList(expectedStatusCodes).contains(statusCode);
                        if (!success) {
                            exception = new FlowableServiceException(this.extractError(this.readJsonContent(response.getEntity().getContent()), "An error occurred while calling Flowable: " + response.getStatusLine()));
                            break label328;
                        }

                        if (response.getStatusLine().getStatusCode() == 200) {
                            contentDispositionFileName = response.getHeaders("Content-Disposition")[0].getValue().split("=");
                            Object fileName = ((Object[])contentDispositionFileName)[((Object[])contentDispositionFileName).length - 1];
                            AttachmentResponseInfo var13 = new AttachmentResponseInfo((String)fileName, IOUtils.toByteArray(response.getEntity().getContent()));
                            return var13;
                        }

                        contentDispositionFileName = new AttachmentResponseInfo(statusCode, this.readJsonContent(response.getEntity().getContent()));
                    } catch (Throwable var45) {
                        var8 = var45;
                        throw var45;
                    } finally {
                        if (response != null) {
                            if (var8 != null) {
                                try {
                                    response.close();
                                } catch (Throwable var44) {
                                    var8.addSuppressed(var44);
                                }
                            } else {
                                response.close();
                            }
                        }

                    }
                } catch (Exception var47) {
                    LOGGER.warn("Error consuming response from uri {}", request.getURI(), var47);
                    exception = this.wrapException(var47, request);
                    break label328;
                }
            } catch (Exception var48) {
                LOGGER.error("Error executing request to uri {}", request.getURI(), var48);
                exception = this.wrapException(var48, request);
                break label328;
            } finally {
                try {
                    client.close();
                } catch (Exception var43) {
                    LOGGER.warn("Error closing http client instance", var43);
                }

            }

            return (AttachmentResponseInfo)contentDispositionFileName;
        }

        if (exception != null) {
            throw exception;
        } else {
            return null;
        }
    }

    public ResponseInfo execute(HttpUriRequest request, String userName, String password, int... expectedStatusCodes) {
        FlowableServiceException exception = null;
        CloseableHttpClient client = this.getHttpClient(userName, password);

        try {
            try {
                CloseableHttpResponse response = client.execute(request);
                Throwable var8 = null;

                try {
                    JsonNode bodyNode = this.readJsonContent(response.getEntity().getContent());
                    int statusCode = -1;
                    if (response.getStatusLine() != null) {
                        statusCode = response.getStatusLine().getStatusCode();
                    }

                    boolean success = Arrays.asList(expectedStatusCodes).contains(statusCode);
                    if (success) {
                        ResponseInfo var12 = new ResponseInfo(statusCode, bodyNode);
                        return var12;
                    }

                    exception = new FlowableServiceException(this.extractError(this.readJsonContent(response.getEntity().getContent()), "An error occurred while calling Flowable: " + response.getStatusLine()));
                } catch (Throwable var41) {
                    var8 = var41;
                    throw var41;
                } finally {
                    if (response != null) {
                        if (var8 != null) {
                            try {
                                response.close();
                            } catch (Throwable var40) {
                                var8.addSuppressed(var40);
                            }
                        } else {
                            response.close();
                        }
                    }

                }
            } catch (Exception var43) {
                LOGGER.warn("Error consuming response from uri {}", request.getURI(), var43);
                exception = this.wrapException(var43, request);
            }
        } catch (Exception var44) {
            LOGGER.error("Error executing request to uri {}", request.getURI(), var44);
            exception = this.wrapException(var44, request);
        } finally {
            try {
                client.close();
            } catch (Exception var39) {
                LOGGER.warn("Error closing http client instance", var39);
            }

        }

        if (exception != null) {
            throw exception;
        } else {
            return null;
        }
    }

    public void execute(HttpUriRequest request, HttpServletResponse httpResponse, String userName, String password) {
        FlowableServiceException exception = null;
        CloseableHttpClient client = this.getHttpClient(userName, password);

        try {
            try {
                CloseableHttpResponse response = client.execute(request);
                Throwable var8 = null;

                try {
                    if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() != 401) {
                        httpResponse.setStatus(response.getStatusLine().getStatusCode());
                        if (response.getEntity() != null && response.getEntity().getContentType() != null) {
                            httpResponse.setContentType(response.getEntity().getContentType().getValue());
                            response.getEntity().writeTo(httpResponse.getOutputStream());
                        }
                    } else {
                        exception = new FlowableServiceException(this.extractError(this.readJsonContent(response.getEntity().getContent()), "An error occurred while calling Flowable: " + response.getStatusLine()));
                    }
                } catch (Throwable var34) {
                    var8 = var34;
                    throw var34;
                } finally {
                    if (response != null) {
                        if (var8 != null) {
                            try {
                                response.close();
                            } catch (Throwable var33) {
                                var8.addSuppressed(var33);
                            }
                        } else {
                            response.close();
                        }
                    }

                }
            } catch (Exception var36) {
                LOGGER.warn("Error consuming response from uri {}", request.getURI(), var36);
                exception = this.wrapException(var36, request);
            }
        } catch (Exception var37) {
            LOGGER.error("Error executing request to uri {}", request.getURI(), var37);
            exception = this.wrapException(var37, request);
        } finally {
            try {
                client.close();
            } catch (Exception var32) {
                LOGGER.warn("Error closing http client instance", var32);
            }

        }

        if (exception != null) {
            throw exception;
        }
    }

    public String executeRequestAsString(HttpUriRequest request, String userName, String password, int expectedStatusCode) {
        FlowableServiceException exception = null;
        String result = null;
        CloseableHttpClient client = this.getHttpClient(userName, password);

        try {
            CloseableHttpResponse response = client.execute(request);
            boolean success = response.getStatusLine() != null && response.getStatusLine().getStatusCode() == expectedStatusCode;
            if (success) {
                result = IOUtils.toString(response.getEntity().getContent(), "utf-8");
            } else {
                String errorMessage = null;

                try {
                    if (response.getEntity().getContentLength() != 0L) {
                        InputStream responseContent = response.getEntity().getContent();
                        JsonNode errorBody = this.objectMapper.readTree(responseContent);
                        errorMessage = this.extractError(errorBody, "An error occurred while calling Flowable: " + response.getStatusLine());
                    } else {
                        errorMessage = "An error was returned when calling the Flowable server";
                    }
                } catch (Exception var28) {
                    LOGGER.warn("Error consuming response from uri {}", request.getURI(), var28);
                    this.wrapException(var28, request);
                } finally {
                    response.close();
                }

                exception = new FlowableServiceException(errorMessage);
            }
        } catch (Exception var30) {
            LOGGER.error("Error executing request to uri {}", request.getURI(), var30);
            exception = this.wrapException(var30, request);
        } finally {
            try {
                client.close();
            } catch (Exception var27) {
                LOGGER.warn("Error closing http client instance", var27);
            }

        }

        if (exception != null) {
            throw exception;
        } else {
            return result;
        }
    }

    public FlowableServiceException wrapException(Exception e, HttpUriRequest request) {
        if (e instanceof HttpHostConnectException) {
            return new FlowableServiceException("Unable to connect to the Flowable server.");
        } else {
            return e instanceof ConnectTimeoutException ? new FlowableServiceException("Connection to the Flowable server timed out.") : new FlowableServiceException(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void executeRequestNoResponseBody(HttpUriRequest request, String userName, String password, int expectedStatusCode) {
        FlowableServiceException exception = null;
        CloseableHttpClient client = this.getHttpClient(userName, password);

        try {
            CloseableHttpResponse response = client.execute(request);
            boolean success = response.getStatusLine() != null && response.getStatusLine().getStatusCode() == expectedStatusCode;
            if (!success) {
                String errorMessage = null;

                try {
                    if (response.getEntity() != null && response.getEntity().getContentLength() != 0L) {
                        InputStream responseContent = response.getEntity().getContent();
                        JsonNode errorBody = this.objectMapper.readTree(responseContent);
                        errorMessage = this.extractError(errorBody, "An error occurred while calling Flowable: " + response.getStatusLine());
                    } else {
                        errorMessage = "An error was returned when calling the Flowable server";
                    }
                } catch (Exception var27) {
                    LOGGER.warn("Error consuming response from uri {}", request.getURI(), var27);
                    this.wrapException(var27, request);
                } finally {
                    response.close();
                }

                exception = new FlowableServiceException(errorMessage);
            }
        } catch (Exception var29) {
            LOGGER.error("Error executing request to uri {}", request.getURI(), var29);
            exception = this.wrapException(var29, request);
        } finally {
            try {
                client.close();
            } catch (Exception var26) {
                LOGGER.warn("Error closing http client instance", var26);
            }

        }

        if (exception != null) {
            throw exception;
        }
    }

    public String extractError(JsonNode errorBody, String defaultValue) {
        return errorBody != null && errorBody.isObject() && errorBody.has("exception") ? errorBody.get("exception").asText() : defaultValue;
    }

    public HttpPost createPost(String uri, ServerConfig serverConfig) {
        HttpPost post = new HttpPost(this.getServerUrl(serverConfig, uri));
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        return post;
    }

    public HttpPost createPost(URIBuilder builder, ServerConfig serverConfig) {
        HttpPost post = new HttpPost(this.getServerUrl(serverConfig, builder));
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        return post;
    }

    public HttpPut createPut(String uri, ServerConfig serverConfig) {
        HttpPut put = new HttpPut(this.getServerUrl(serverConfig, uri));
        put.setHeader("Content-Type", "application/json");
        put.setHeader("Accept", "application/json");
        return put;
    }

    public HttpPut createPut(URIBuilder builder, ServerConfig serverConfig) {
        HttpPut put = new HttpPut(this.getServerUrl(serverConfig, builder));
        put.setHeader("Content-Type", "application/json");
        put.setHeader("Accept", "application/json");
        return put;
    }

    public HttpDelete createDelete(URIBuilder builder, ServerConfig serverConfig) {
        HttpDelete delete = new HttpDelete(this.getServerUrl(serverConfig, builder));
        delete.setHeader("Content-Type", "application/json");
        delete.setHeader("Accept", "application/json");
        return delete;
    }

    public StringEntity createStringEntity(JsonNode json) {
        try {
            return new StringEntity(json.toString());
        } catch (Exception var3) {
            LOGGER.warn("Error translation json to http client entity {}", json, var3);
            return null;
        }
    }

    public StringEntity createStringEntity(String json) {
        try {
            return new StringEntity(json);
        } catch (Exception var3) {
            LOGGER.warn("Error translation json to http client entity {}", json, var3);
            return null;
        }
    }

    public String getServerUrl(ServerConfig serverConfig, String uri) {
        return this.getServerUrl(serverConfig.getContextRoot(), serverConfig.getRestRoot(), serverConfig.getServerAddress(), serverConfig.getPort(), uri);
    }

    public String getServerUrl(String contextRoot, String restRoot, String serverAddress, Integer port, String uri) {
        String actualContextRoot = null;
        if (contextRoot != null) {
            actualContextRoot = this.stripSlashes(contextRoot);
        } else {
            actualContextRoot = "flowable-rest";
        }

        String actualRestRoot = null;
        if (restRoot != null) {
            actualRestRoot = this.stripSlashes(restRoot);
        } else {
            actualRestRoot = "service";
        }

        String finalUrl = serverAddress + ":" + port;
        if (StringUtils.isNotEmpty(actualContextRoot)) {
            finalUrl = finalUrl + "/" + actualContextRoot;
        }

        if (StringUtils.isNotEmpty(actualRestRoot)) {
            finalUrl = finalUrl + "/" + actualRestRoot;
        }

        if (StringUtils.isNotEmpty(uri) && !uri.startsWith("/")) {
            uri = "/" + uri;
        }

        URIBuilder builder = this.createUriBuilder(finalUrl + uri);
        return builder.toString();
    }

    public String getAppServerUrl(ServerConfig serverConfig, String uri) {
        String contextRoot = null;
        if (StringUtils.isNotEmpty(serverConfig.getContextRoot())) {
            contextRoot = this.stripSlashes(serverConfig.getContextRoot());
        } else {
            contextRoot = "flowable-rest";
        }

        return "http://" + serverConfig.getServerAddress() + ":" + serverConfig.getPort() + "/" + contextRoot + "/" + uri;
    }

    public URIBuilder createUriBuilder(String url) {
        try {
            return new URIBuilder(url);
        } catch (URISyntaxException var3) {
            throw new FlowableServiceException("Error while creating Flowable endpoint URL: " + var3.getMessage());
        }
    }

    public String getServerUrl(ServerConfig serverConfig, URIBuilder builder) {
        try {
            return this.getServerUrl(serverConfig, builder.build().toString());
        } catch (URISyntaxException var4) {
            throw new FlowableServiceException("Error while creating Flowable endpoint URL: " + var4.getMessage());
        }
    }

    public String getUriWithPagingAndOrderParameters(URIBuilder builder, JsonNode bodyNode) throws URISyntaxException {
        this.addParameterToBuilder("size", bodyNode, builder);
        this.addParameterToBuilder("sort", bodyNode, builder);
        this.addParameterToBuilder("order", bodyNode, builder);
        return builder.build().toString();
    }

    public void addParameterToBuilder(String name, JsonNode bodyNode, URIBuilder builder) {
        JsonNode nameNode = bodyNode.get(name);
        if (nameNode != null && !nameNode.isNull()) {
            builder.addParameter(name, nameNode.asText());
            ((ObjectNode)bodyNode).remove(name);
        }

    }

    protected String stripSlashes(String url) {
        if (url.startsWith("/")) {
            url = url.substring(1);
        }

        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }

    protected JsonNode readJsonContent(InputStream requestContent) {
        try {
            return this.objectMapper.readTree(IOUtils.toString(requestContent, "utf-8"));
        } catch (Exception var3) {
            LOGGER.debug("Error parsing error message", var3);
            return null;
        }
    }
}
