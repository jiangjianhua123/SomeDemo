package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
@SpringBootApplication
public class GatewayApplication {

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                  .route(r->r.path("/api-a").uri("http://localhost:8765"))
//                  .route(r->r.path("/api-b").uri("http://localhost:8766"))
//                .build();
////                .route("test")
////                .uri("http://httpbin.org:80")
////                .predicate(host("**.abc.org").and(path("/image/png")))
////                .addResponseHeader("X-TestHeader", "foobar")
////                .and()
////                .route("test2")
////                .uri("http://httpbin.org:80")
////                .predicate(path("/image/webp"))
////                .add(addResponseHeader("X-AnotherHeader", "baz"))
////                .and()
////                .build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
