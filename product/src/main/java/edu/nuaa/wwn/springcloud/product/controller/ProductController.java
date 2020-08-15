package edu.nuaa.wwn.springcloud.product.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import edu.nuaa.wwn.springcloud.product.pojo.UserPo;
import edu.nuaa.wwn.springcloud.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/ribbon")
    public UserPo testRibbon() {
        UserPo user = null;
        for (int i = 0; i < 10; i++) {
            user = restTemplate.getForObject("http://USER/user/" + (i + 1), UserPo.class);
        }
        return user;
    }

    @GetMapping("/feign")
    public UserPo feign() {
        UserPo user = null;
        for (int i = 0; i < 10; i++) {
            user = userService.getUser((long) (i + 1));
        }
        return user;
    }

    @GetMapping("/circuitBreaker1")
    @HystrixCommand(fallbackMethod = "error",
            commandProperties = {
                    @HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "3000"
                    )
            })
    public String circuitBreaker1() {
        return restTemplate.getForObject("http://USER/timeout", String.class);
    }

    @GetMapping("/circuitBreaker2")
    @HystrixCommand(fallbackMethod = "error")
    public String circuitBreaker2() {
        return userService.timeout();
    }

    public String error() {
        return "超时出错";
    }
}
