package org.modeldd.flowableboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;


@SpringBootApplication
public class App {
	
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    
    @Bean
    public CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService,
                                  final HistoryService historyService) {

        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                System.out.println("Number of process definitions : "
                	+ repositoryService.createProcessDefinitionQuery().count());
                
    			String employee = "ACV";
    			Integer nrOfHolidays = 364;
    			String description = "To learn";
    			System.out.println("Who are you? > " + employee);
    	
    			System.out.println("How many holidays do you want to request? > " + nrOfHolidays);
    	
    			System.out.println("Why do you need them? >" + description);
    		
    	
    			Map<String, Object> variables = new HashMap<String, Object>();
    			variables.put("employee", employee);
    			variables.put("nrOfHolidays", nrOfHolidays);
    			variables.put("description", description);
    			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("flowableplay01", variables);
    			if( processInstance != null){}/*CQT*/
    			
    			// From FlowablePlay01QueryTasks01.java
    
    			List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
    			System.out.println("You have " + tasks.size() + " tasks:");
    			for (int i=0; i<tasks.size(); i++) {
    			  System.out.println((i+1) + ") " + tasks.get(i).getName());
    			}
    			
    			System.out.println("You have " + tasks.size() + " tasks (detailed):");
    			for (int i=0; i<tasks.size(); i++) {
    			  Task task = tasks.get(i);
    			  Map<String, Object> processVariables = taskService.getVariables(task.getId());
    			  System.out.println( (i+1) + ") " + task.getName() + ": " + processVariables.get("employee") + " wants " +
    			      processVariables.get("nrOfHolidays") + " days of holidays.");
    			}
    			
    			
    			// From FlowablePlay01CompleteTask01.java			
    			int taskIndex = 1;		
    			System.out.println("Which task would you like to complete? > " + taskIndex);
    			
    			Task taskToComplete = tasks.get(taskIndex - 1);
    			Map<String, Object> processVariables = taskService.getVariables(taskToComplete.getId());
    			System.out.println(processVariables.get("employee") + " wants " +
    			    processVariables.get("nrOfHolidays") + " of holidays. Do you approve this? > y");
    			
    			boolean approved = "y".toLowerCase().equals("y");
    			variables = new HashMap<String, Object>();
    			variables.put("approved", approved);
    			taskService.complete(taskToComplete.getId(), variables);
    			
    			
    			// Dump tasks available as above from FlowablePlay01QueryTasks01.java
    			System.out.println("List tasks for managers in instance(s) of process definition");
    			List<Task> tasks2 = taskService.createTaskQuery().taskCandidateGroup("managers").list();
    			System.out.println("You have " + tasks2.size() + " tasks (detailed):");
    			for (int i=0; i<tasks2.size(); i++) {
    			  Task task2 = tasks2.get(i);
    			  Map<String, Object> processVariables2 = taskService.getVariables(task2.getId());
    			  System.out.println( (i+1) + ") (After) " + task2.getName() + ": " + processVariables2.get("employee") + " wants " +
    			      processVariables.get("nrOfHolidays") + " days of holidays.");
    			}
    			
    			
    			// From FlowablePlay01TasksHistory01.java
    			List<HistoricActivityInstance> activities =
    			  historyService.createHistoricActivityInstanceQuery()
    			   .processInstanceId(processInstance.getId())
    			   .finished()
    			   .orderByHistoricActivityInstanceEndTime().asc()
    			   .list();

    			for (HistoricActivityInstance activity : activities) {
    			  System.out.println(activity.getActivityId() + " took "
    			    + activity.getDurationInMillis() + " milliseconds");
    			}
    			

    			/* Dig deeper in details
    			 * as i.e. https://github.com/flowable/flowable-engine/blob/master/modules/flowable-engine/src/test/java/org/flowable/standalone/history/FullHistoryTest.java
    			 * https://www.flowable.org/docs/javadocs/org/flowable/engine/history/HistoricDetail.html
    			 */
    	        System.out.println( "\n\norg.modeldd.flowableplay01.FlowablePlay01TasksHistoryDeeper01 each HistoricActivityInstance");

    	        HistoricActivityInstance historicStartEvent = historyService.createHistoricActivityInstanceQuery()
    	        		.processInstanceId(processInstance.getId()).activityId("startEvent").singleResult();
    	        System.out.println( "\n\n" + 
    	        		"processDefinitionId=" + historicStartEvent.getProcessDefinitionId() + " " + 
            			"processInstanceId=" + historicStartEvent.getProcessInstanceId() + " " + 
            			"activityId=" + historicStartEvent.getActivityId() + " " + 
    	        		"activityType=" + historicStartEvent.getActivityType() + " " + 
    	        		"historicActivityInstance_id=" + historicStartEvent.getId() + " " + 
    	        		"executionId=" + historicStartEvent.getExecutionId() + " " + 
            			"durationInMillis=" + historicStartEvent.getDurationInMillis() + "\n\n");
    	        
    	        HistoricActivityInstance historicApproveTask = historyService.createHistoricActivityInstanceQuery()
    	        		.processInstanceId(processInstance.getId()).activityId("approveTask").singleResult();
    	        System.out.println( "\n\n" + 
    	        		"processDefinitionId=" + historicApproveTask.getProcessDefinitionId() + " " + 
            			"processInstanceId=" + historicApproveTask.getProcessInstanceId() + " " + 
            			"activityId=" + historicApproveTask.getActivityId() + " " + 
    	        		"activityType=" + historicApproveTask.getActivityType() + " " + 
    	        		"historicActivityInstance_id=" + historicApproveTask.getId() + " " + 
    	        		"executionId=" + historicApproveTask.getExecutionId() + " " + 
            			"durationInMillis=" + historicApproveTask.getDurationInMillis() + "\n\n");
    	        
    	        HistoricActivityInstance historicDecision = historyService.createHistoricActivityInstanceQuery()
    	        		.processInstanceId(processInstance.getId()).activityId("decision").singleResult();
    	        System.out.println( "\n\n" + 
    	        		"processDefinitionId=" + historicDecision.getProcessDefinitionId() + " " + 
            			"processInstanceId=" + historicDecision.getProcessInstanceId() + " " + 
            			"activityId=" + historicDecision.getActivityId() + " " + 
    	        		"activityType=" + historicDecision.getActivityType() + " " + 
    	        		"historicActivityInstance_id=" + historicDecision.getId() + " " + 
    	        		"executionId=" + historicDecision.getExecutionId() + " " + 
            			"durationInMillis=" + historicDecision.getDurationInMillis() + "\n\n");

    	        HistoricActivityInstance historicExternalSystemCall = historyService.createHistoricActivityInstanceQuery()
    	        		.processInstanceId(processInstance.getId()).activityId("externalSystemCall").singleResult();
    	        System.out.println( "\n\n" + 
    	        		"processDefinitionId=" + historicExternalSystemCall.getProcessDefinitionId() + " " + 
            			"processInstanceId=" + historicExternalSystemCall.getProcessInstanceId() + " " + 
            			"activityId=" + historicExternalSystemCall.getActivityId() + " " + 
    	        		"activityType=" + historicExternalSystemCall.getActivityType() + " " + 
    	        		"historicActivityInstance_id=" + historicExternalSystemCall.getId() + " " + 
    	        		"executionId=" + historicExternalSystemCall.getExecutionId() + " " + 
            			"durationInMillis=" + historicExternalSystemCall.getDurationInMillis() + "\n\n");

    	        HistoricActivityInstance historicHolidayApprovedTask = historyService.createHistoricActivityInstanceQuery()
    	        		.processInstanceId(processInstance.getId()).activityId("holidayApprovedTask").singleResult();
    	        System.out.println( "\n\n" + 
    	        		"processDefinitionId=" + historicHolidayApprovedTask.getProcessDefinitionId() + " " + 
            			"processInstanceId=" + historicHolidayApprovedTask.getProcessInstanceId() + " " + 
            			"activityId=" + historicHolidayApprovedTask.getActivityId() + " " + 
    	        		"activityType=" + historicHolidayApprovedTask.getActivityType() + " " + 
    	        		"historicActivityInstance_id=" + historicHolidayApprovedTask.getId() + " " + 
    	        		"executionId=" + historicHolidayApprovedTask.getExecutionId() + " " + 
            			"durationInMillis=" + historicHolidayApprovedTask.getDurationInMillis() + "\n\n");
    	        
    	        
    	        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
    	        		.orderByVariableName().asc().list();

    	        System.out.println( "\n\n" + "org.modeldd.flowableplay01.FlowablePlay01TasksHistoryDeeper01 " +
    	        		"num historicVariableInstances=" + historicVariableInstances.size());
    	        
    	        for (HistoricVariableInstance aHistoricVariableInstance : historicVariableInstances) {
            		String aValueStr = "";
            		Object aValue = aHistoricVariableInstance.getValue();
            		switch( aHistoricVariableInstance.getVariableTypeName()) {
            			case "boolean":
            				aValueStr = aValue.toString();
            				break;
            				
            			case "integer":
            				aValueStr = aValue.toString();
            				break;
            				
            			case "string":
            				aValueStr = aValue.toString();
            				break;
            				
        				default:
        					aValueStr = aValue.toString();
            				break;
            		}
            			
    	        	System.out.println( "\n\n" + 
    	 	        		"id=" + aHistoricVariableInstance.getId() + " " + 
    	         			"processInstanceId=" + aHistoricVariableInstance.getProcessInstanceId() + " " + 
    	         			"taskId=" + aHistoricVariableInstance.getTaskId() + " " + 
    	 	        		"variable name=" + aHistoricVariableInstance.getVariableName() + " " + 
    	 	        		"variable type name=" + aHistoricVariableInstance.getVariableTypeName() + " " + 
    	 	        		"value=" + aValueStr + "\n\n");
    			 }	        
    	        System.out.println( "\n\n");
              
            }
            
            
            
        };
    }
    
}

