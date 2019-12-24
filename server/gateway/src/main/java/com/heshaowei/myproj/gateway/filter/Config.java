package com.heshaowei.myproj.gateway.filter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.heshaowei.myproj.gateway.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Log4j2
@Component
public class Config {

    @Autowired
    private AccountService accountService;

    @Autowired
    private FilterProps filterProps;

    @Bean
    public GlobalFilter authFilter() {
        return (exchange, chain) -> {
            log.info("before权限验证拦截器...");
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (!this.needFilter(request.getPath().pathWithinApplication().value())) {
                return chain.filter(exchange);
            }

            List<String> accessTokens = request.getHeaders().get("Authorization");
            if (null == accessTokens || accessTokens.isEmpty()) {
                return response401(response, HttpStatus.NON_AUTHORITATIVE_INFORMATION, "没有访问权限！");
            } else {
                try {
                    String accessToken = accessTokens.get(0);

                    boolean isAuthed = this.accountService.verify(accessToken, request.getPath().pathWithinApplication().value());
                    if (isAuthed) {
                        /*String json = JWTUtil.getUserinfo(accessToken);
                        UserInfo userInfo = new Gson().fromJson(json, UserInfo.class);
                        //向headers中放文件，记得build
                        ServerHttpRequest host = exchange.getRequest().mutate().header("username", userInfo.getUsername()).build();
                        //将现在的request 变成 change对象
                        exchange = exchange.mutate().request(host).build();*/
                    } else {
                        throw new Exception("没有访问权限！");
                    }

                    /*String username = JWTUtil.getUsername(accessToken);

                    String userJson = this.accountService.findByUsername(username);
                    JsonObject result = new JsonParser().parse(userJson).getAsJsonObject();
                    if (result.get("code").getAsInt() == 1) {
                        JsonObject user = result.get("data").getAsJsonObject();

                        JWTUtil.verify(accessToken, username, user.get("password").getAsString());

                        //向headers中放文件，记得build
                        ServerHttpRequest host = exchange.getRequest().mutate().header("username", username).build();
                        //将现在的request 变成 change对象
                        exchange = exchange.mutate().request(host).build();
                    }else {
                        //清除缓存
                        this.accountService.removeByUsername(username);
                        throw new Exception("查询用户信息异常！");
                    }*/
                } catch (Exception e) {
                    log.error(e);
                    return response401(response, HttpStatus.UNAUTHORIZED, "权限已经失效，请重新登录！");
                }
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("拦截之后处理");

            }));
        };
    }

    private boolean needFilter(String path) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String ignoreUrl : filterProps.getAuth().getIgnoreUrls()) {
            if (pathMatcher.match(ignoreUrl, path)) {
                return false;
            }
        }
        return true;
    }

    private Mono<Void> response401(ServerHttpResponse response, HttpStatus status, String msg) {
        response.setStatusCode(status);
        JsonObject message = new JsonObject();
        message.addProperty("code", 0);
        message.addProperty("msg", msg);
        DataBuffer buffer = response.bufferFactory().wrap(message.toString().getBytes(StandardCharsets.UTF_8));

        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

}
