package org.example.rest;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.example.service.RequestOutInformationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableProcessApplication
@RestController
@RequestMapping("1/camunda")
public class CamundaRestController {
    private RequestOutInformationService requestOutInformationService;

    public CamundaRestController(RequestOutInformationService requestOutInformationService) {
        this.requestOutInformationService = requestOutInformationService;
    }

    @PostMapping("/deployTask")
    public void deployTask(){
        requestOutInformationService.deployTask();
    }

    @PostMapping("/fillResponse")
    public void fillTask(){
        requestOutInformationService.fillTask();
    }

    @PostMapping("/task/complete/send-request-out")
    public void completeSendRequest(){
        requestOutInformationService.completeSendRequest();
    }

    @PostMapping("/task/complete/chase-supplier")
    public void completeChaseSupplier(){
        requestOutInformationService.completeChaseSupplier();
    }

    @PostMapping("/task/complete/adjust-request")
    public void completeRequestSupplier(){
        requestOutInformationService.completeRequestSupplier();
    }
}
