package spring.test.eureka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@SpringBootApplication
@EnableEurekaClient
@Controller
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Value("${server.port}")
	private String port;

	@Resource
	private DiscoveryClient discoveryClient;

	@ResponseBody
	@GetMapping("/hello")
	public String hello(){
		return "hello" + port;
	}


	@ResponseBody
	@GetMapping("/discovery")
	public String discovery(){
		return String.join(",", discoveryClient.getServices());
	}

}
