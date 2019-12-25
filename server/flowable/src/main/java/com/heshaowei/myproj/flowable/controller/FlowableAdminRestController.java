package com.heshaowei.myproj.flowable.controller;

import com.google.common.collect.Maps;
import com.heshaowei.myproj.flowable.bean.ChangeStateParams;
import com.heshaowei.myproj.flowable.service.BaseFlowService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/app/rest/admin")
@RestController
public class FlowableAdminRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

//    private final static String bpmApiProtocol = "http://";
//    private final static String bpmApiHost = "localhost";
//    private final static String bpmApiPort = "8080";
//    private final static String bpmApiIdmAuthPath = "flowable-idm/app/authentication";
//    private final static String bpmApiProcessPath = "flowable-admin/app/rest/admin/";
//    private final static String bpmApiIdmUser = "admin";
//    private final static String bpmApiIdmPassword = "test";

    @Autowired
    private BaseFlowService flowService;

    /*@GetMapping("/process-instances")
    @ResponseBody
    public String processInstances(String historyInstanceId, String instanceId, String definitionId, HttpServletResponse response){

        URL url = null;
        try {
            // 请求路径根路径
            // http://服务器ip:端口/flowable-admin/app/rest/admin/process-instances/流程实例id/model-json?processDefinitionId=流程定义id
            String modelUrl = getModelUrl(bpmApiProcessPath);

            if (null != historyInstanceId) {
                url = new URL(modelUrl + "process-instances/" + historyInstanceId + "/history-model-json?processDefinitionId=" + definitionId + "&nocaching=" + new Date().getTime());
            } else if (null != instanceId) {
                url = new URL(modelUrl + "process-instances/" + instanceId + "/model-json?processDefinitionId=" + definitionId + "&nocaching=" + new Date().getTime());
            } else {
                url = new URL(modelUrl + "process-definitions/" + definitionId + "/model-json?nocaching=" + new Date().getTime());
            }

            return connection(url, "GET", null);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return null;
    }*/

    @PostMapping("/process-instances/{instanceId}/change-state")
    public void changeState(@PathVariable("instanceId") String instanceId, @RequestBody ChangeStateParams changeStateParams) {
        if (changeStateParams.isNotEmpty()) {
            this.flowService.changeState(instanceId, changeStateParams.getCancelActivityIds().get(0), changeStateParams.getStartActivityIds().get(0));
        }
    }

    /*private String getModelUrl(String path){
        String modelUrl = bpmApiProtocol + bpmApiHost + ":" + bpmApiPort + "/" + path;
        return modelUrl;
    }

    private String connection(URL url, String method, Map<String, Object> params) {
        try {

            // 获取响应的cookie 值
            String responseCookie = getAuthenticationSession();

            HttpURLConnection conn = null;


            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Accept", "application/json");
            // 设置为之前登录获取的cookie
            conn.setRequestProperty("Cookie", responseCookie);
            // 设置请求参数
            if(null != params) {
                conn.setDoOutput(true);
                PrintStream ps = new PrintStream(conn.getOutputStream());
                ps.print(params);
                ps.close();
            }
            conn.connect();

            StringBuilder builder = new StringBuilder();
            // 将数据读入StringBuilder；
            int successCode = 200;
            if (conn.getResponseCode() == successCode) {
                InputStream inputStream = conn.getInputStream();
                byte[] data = new byte[1024];
                StringBuffer sb1 = new StringBuffer();
                int length = 0;
                while ((length = inputStream.read(data)) != -1) {
                    String s = new String(data, 0, length);
                    sb1.append(s);
                }
                builder.append(sb1.toString());
                inputStream.close();
            }

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return null;
    }*/

    /**
     * flowable-admin登录认证
     *
     * @return cookie
     * @throws IOException
     */
    /*public String getAuthenticationSession() {
        // http协议头+服务器ip+端口 + flowable-idm/app/authentication（admin的登陆请求）
        String authenticationUrl = getModelUrl(bpmApiIdmAuthPath);

        URL url = null;
        String responseCookie= "";
        try {
            url = new URL(authenticationUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 允许连接提交信息
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            // 请求参数
            String content = "j_username=" + bpmApiIdmUser + "&j_password=" + bpmApiIdmPassword + "&_spring_security_remember_me=true&submit=Login";

            // 提交请求数据
            OutputStream os = connection.getOutputStream();
            os.write(content.getBytes("utf8"));
            os.close();

            // 取到所用的Cookie， 认证
            responseCookie = connection.getHeaderField("Set-Cookie");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCookie;
    }*/
}
