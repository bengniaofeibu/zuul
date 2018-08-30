package com.jiujiu.config;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class ZuulSwaggerResourcesProvider implements SwaggerResourcesProvider{

    @Autowired
    private ZuulRoutesConfig zuulRoutesConfig;

    private final RouteLocator routeLocator;

    public ZuulSwaggerResourcesProvider(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }


    @Override
    public List<SwaggerResource> get() {

        List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        for (Route route:routes) {
            for (String value : zuulRoutesConfig.getRoutes().values()) {
                System.out.println("Value = " + value);
                String res[] = value.split("/");
                if(res[1].equals(route.getId()) || res[1].equals(route.getId())){
                    resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs")));
                }
            }
        }
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
