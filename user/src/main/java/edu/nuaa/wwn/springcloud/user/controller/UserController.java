package edu.nuaa.wwn.springcloud.product.controller;

import edu.nuaa.wwn.springcloud.product.pojo.UserPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/user/{id}")
    public UserPo getUserPo(@PathVariable("id") Long id) {
        ServiceInstance serviceInstance = discoveryClient.getInstances("USER").get(0);
        log.info("[" + serviceInstance.getServiceId() + "] :" + serviceInstance.getHost() +
                ":" + serviceInstance.getPort());
        UserPo user = new UserPo();
        user.setId(id);
        user.setLevel((int)(id%3+1));
        user.setUserName("user_name_" + id);
        user.setNote("note_" + id);
        return user;
    }

    @GetMapping("/timeout")
    public String timeout() {
        long ms = (long) (3000L * Math.random());
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "熔断测试";
    }
}
