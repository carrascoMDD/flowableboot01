package org.modeldd.flowableboot.test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.rest.service.api.repository.ProcessDefinitionCollectionResource;
import org.flowable.rest.service.api.repository.ProcessDefinitionResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
// @WebMvcTest(ProcessDefinitionCollectionResource.class)
@AutoConfigureMockMvc
public class ShippingExchange_AnnouncePayload_Test {



    @Autowired
	private MockMvc mvc;

	@MockBean
	private RepositoryService repositoryService;

	@MockBean
	private RuntimeService runtimeService;

	@MockBean
	private TaskService taskService;

	@MockBean
	private HistoryService historyService;
	
    
	@MockBean
	private ProcessDefinitionCollectionResource aProcessDefinitionCollectionResource;

	@Test
    public void contextLoads() throws Exception {
    }
	
	@Test
	public void processDefinitionsCount() throws Exception {
		/* https://www.flowable.org/docs/userguide/index.html#_process_definitions
		 https://www.flowable.org/docs/userguide/index.html#_list_of_process_definitions
		 15.3.1. List of process definitions
		 GET repository/process-definitions
		 
		
		 Response code	Description
			200			Indicates request was successful and the process-definitions are returned
			400			Indicates a parameter was passed in the wrong format or that latest is used with other parameters other than key and keyLike. The status-message contains additional information.
	
			{
			  "data": [
			    {
			      "id" : "oneTaskProcess:1:4",
			      "url" : "http://localhost:8182/repository/process-definitions/oneTaskProcess%3A1%3A4",
			      "version" : 1,
			      "key" : "oneTaskProcess",
			      "category" : "Examples",
			      "suspended" : false,
			      "name" : "The One Task Process",
			      "description" : "This is a process for testing purposes",
			      "deploymentId" : "2",
			      "deploymentUrl" : "http://localhost:8081/repository/deployments/2",
			      "graphicalNotationDefined" : true,
			      "resource" : "http://localhost:8182/repository/deployments/2/resources/testProcess.xml",
			      "diagramResource" : "http://localhost:8182/repository/deployments/2/resources/testProcess.png",
			      "startFormDefined" : false
			    }
			  ],
			  "total": 1,
			  "start": 0,
			  "sort": "name",
			  "order": "asc",
			  "size": 1
			}
				 */
		mvc.perform(get("/repository/process-definitions").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[ 0].key", is("proc_ShippingExchange_AnnouncePayload")))
				.andExpect(jsonPath("$.data[ 0].name", is("Shipping Exchange Announce Payload")));
	}

	
}

