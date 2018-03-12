package com.cignex.rahul.springboothystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@RestController
@RequestMapping("/")
public class SpringBootHystrixApplication {

	@SuppressWarnings("rawtypes")
	@Bean
	public ServletRegistrationBean servletRegistration() {
		@SuppressWarnings("unchecked")
		ServletRegistrationBean registration = new ServletRegistrationBean(new HystrixMetricsStreamServlet(),
				"/hystrix.stream");
		return registration;
	}

	@GetMapping
	@HystrixCommand(fallbackMethod = "demoRest_Fallback")
	public String demoRest() {
		int a = 5 / 0;
		System.out.println(a);
		return "Demo";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHystrixApplication.class, args);
	}

	public String demoRest_Fallback() {
		return "From FallBakc";
	}
}
