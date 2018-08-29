package com.jiujiu;
import com.jiujiu.filter.AccessFilter;
import com.jiujiu.filter.ResponseFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableSwagger2
public class JJZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(JJZuulApplication.class, args);
	}


   //设置路由
   @Bean
   public AccessFilter accessFilter(){
		return new AccessFilter();
   }

    //设置路由
    @Bean
    public ResponseFilter responseFilter(){
        return new ResponseFilter();
    }
}
