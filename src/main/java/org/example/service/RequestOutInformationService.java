package org.example.service;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestOutInformationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestOutInformationService.class);

    private static final String BPMN = "request-confirm.bpmn";
    private static final String SEND_REQUEST_OUT_USER_TASK= "Send request out";
    private static final String CHASE_SUPPLIER_USER_TASK = "Chase supplier";
    private static final String ADJUST_REQUEST_USER_TASK = "Adjust request with supplier";

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private ProcessInstance processInstance;
    private RepositoryService repositoryService;

    public RequestOutInformationService(RuntimeService runtimeService, TaskService taskService, RepositoryService repositoryService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.repositoryService = repositoryService;
    }

    public void deployTask() {
        repositoryService.createDeployment()
                .addClasspathResource(BPMN)
                .deploy();
        LOGGER.info("Task deployed");
    }

    public void fillTask() {
        boolean isSupplierConfirmed = true;
        boolean isMatchExpectation = false;

        processInstance = runtimeService.startProcessInstanceByKey("request-confirm");
        runtimeService.setVariable(processInstance.getId(),"isSupplierConfirmed", isSupplierConfirmed);
        runtimeService.setVariable(processInstance.getId(),"isMatchExpectation", isMatchExpectation);
        LOGGER.info("Variable filled");
    }

    public void completeSendRequest() {
        Task taskSendRequestOut = getTask(SEND_REQUEST_OUT_USER_TASK);
        taskService.complete(taskSendRequestOut.getId());
        LOGGER.info("Send request out complete!");
    }

    public void completeChaseSupplier() {
        Task taskCompleteChaseSupplier = getTask(CHASE_SUPPLIER_USER_TASK);
        taskService.complete(taskCompleteChaseSupplier.getId());
        LOGGER.info("Chase supplier complete!");
    }

    public void completeRequestSupplier() {
        Task taskCompleteRequestSupplier = getTask(ADJUST_REQUEST_USER_TASK);
        taskService.complete(taskCompleteRequestSupplier.getId());
        LOGGER.info("Adjust supplier complete!");
    }

    private Task getTask(String taskName) {
        Task taskSendRequestOut = null;
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        for (Task task : list) {
            if (task.getName().equals(taskName)) {
                taskSendRequestOut = task;
            }
        }
        return taskSendRequestOut;
    }
}
