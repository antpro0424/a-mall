package com.chuwa.filters;

import com.chuwa.config.AuthProperties;
import com.chuwa.exception.UnauthorizedException;
import com.chuwa.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;
    private final JwtTool jwtTool;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1. 获取用户信息 (Request对象)
        ServerHttpRequest request = exchange.getRequest();

        // 2. 根据请求路径判断是否需要登录校验
        if (isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }

        // 3. 获取token
        String token = null;
        List<String> headers = request.getHeaders().get("Authorization");
        if (headers != null && !headers.isEmpty()) {
            token = headers.get(0);
        }

        // 4. 校验并解析token
        String username = null;
        try {
            username = jwtTool.parseToken(token);
        }
        catch (UnauthorizedException e) {
            // 拦截，设置响应状态码401
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // TODO: 5.传递用户信息
        System.out.println("Username" + username);

        // 6. 放行
        return chain.filter(exchange);
    }

    private boolean isExclude(String path) {
        for (String pathPatter : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(pathPatter, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
