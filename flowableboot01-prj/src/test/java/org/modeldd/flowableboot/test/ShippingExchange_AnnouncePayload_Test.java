package org.modeldd.flowableboot.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.notNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.rest.service.api.repository.ProcessDefinitionCollectionResource;
import org.flowable.rest.service.api.repository.ProcessDefinitionResource;
import org.flowable.rest.service.api.repository.ProcessDefinitionResponse;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceCollectionResource;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modeldd.flowableboot.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


//@ComponentScan( { "org.flowable.rest"})
//@WebMvcTest(ProcessDefinitionCollectionResource.class)


@RunWith(SpringRunner.class)
@SpringBootTest( classes = App.class, webEnvironment = WebEnvironment.RANDOM_PORT)
// @AutoConfigureMockMvc
public class ShippingExchange_AnnouncePayload_Test {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	public static final String JSON_ProcessInstanceCreateRequest = "{ \"processDefinitionKey\":\"proc_ShippingExchange_AnnouncePayload\", \"variables\": [ { \"name\":\"var_PayloadKind\", \"value\": \"SteelRoll\" }, { \"name\":\"var_PayloadWeight\", \"value\": 12000 }, { \"name\":\"var_PayloadWeightUnit\", \"value\": \"Kg\" }, { \"name\":\"var_PayloadVolume\", \"value\": 2 }, { \"name\":\"var_PayloadWeightUnit\", \"value\": \"M3\" }]}";
	
    @Autowired
    private TestRestTemplate template_ProcessDefinitionResponse;
    
    @Autowired
    private TestRestTemplate template_ProcessInstanceResponse;

    
    
    private JacksonTester<DataResponse<ProcessDefinitionResponse>> json_ProcessDefinitionResponse;
    private JacksonTester<ProcessInstanceResponse>  			   json_ProcessInstanceResponse;

    
    
    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper(); 
        // Possibly configure the mapper
        JacksonTester.initFields(this, objectMapper);
    }

    
    // @Autowired
	// private MockMvc mvc;

 // @MockBean
 // private RepositoryService repositoryService;

	// @MockBean
	// private RuntimeService runtimeService;

	// @MockBean
	// private TaskService taskService;

	// @MockBean
	// private HistoryService historyService;
	
    
	// @MockBean
	// private ProcessDefinitionCollectionResource aProcessDefinitionCollectionResource;
	
	// @MockBean
	// private ProcessInstanceCollectionResource aProcessInstanceCollectionResource;
    
    // @Autowired
 // private ProcessDefinitionCollectionResource aProcessDefinitionCollectionResource;
	
    // @Autowired
 // private ProcessInstanceCollectionResource aProcessInstanceCollectionResource;


	// @Test
    // public void contextLoads() throws Exception {
    // }
	
	@Test
	public void repositoryService_createProcessDefinitionQuery_count() throws Exception {
		/* https://www.flowable.org/docs/userguide/index.html#_process_definitions
		 https://www.flowable.org/docs/userguide/index.html#_list_of_process_definitions
		 15.3.1. List of process definitions
		 GET repository/process-definitions
		 
		 org.flowable.rest.service.api.repository.ProcessDefinitionCollectionResource

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
		
		ResponseEntity<String> response = template_ProcessDefinitionResponse.getForEntity("/process-api/repository/process-definitions", String.class);     
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        
        String aResponseBody = response.getBody();
        System.out.println( "\n\nGET /process-api/repository/process-definitions responseBody=" + aResponseBody);

        DataResponse<ProcessDefinitionResponse> aDataProcessDefinitionResponse = this.json_ProcessDefinitionResponse.parse( aResponseBody).getObject();
        
        System.out.println( "GET /process-api/repository/process-definitions response=\n" + this.json_ProcessDefinitionResponse.write( aDataProcessDefinitionResponse) + "\n\n");
        
        List<ProcessDefinitionResponse> someProcessDefinitionResponses = aDataProcessDefinitionResponse.getData();
        assertThat( someProcessDefinitionResponses.size(), is( 2));

        ProcessDefinitionResponse aProcessDefinitionResponse_flowableplay01 = someProcessDefinitionResponses.get( 0);       
        assertThat( aProcessDefinitionResponse_flowableplay01, notNullValue());
        assertThat( aProcessDefinitionResponse_flowableplay01.getId(), notNullValue());
        assertThat( aProcessDefinitionResponse_flowableplay01.getUrl(), notNullValue());
        assertThat( aProcessDefinitionResponse_flowableplay01.getKey(), is( "flowableplay01"));
        assertThat( aProcessDefinitionResponse_flowableplay01.getName(), is( "FlowablePlay01 BPMN2"));
        assertThat( aProcessDefinitionResponse_flowableplay01.getDeploymentId(), notNullValue());
        assertThat( aProcessDefinitionResponse_flowableplay01.getDeploymentUrl(), notNullValue());


        ProcessDefinitionResponse aProcessDefinitionResponse_ShippingExchange = someProcessDefinitionResponses.get( 1);       
        assertThat( aProcessDefinitionResponse_ShippingExchange, notNullValue());
        assertThat( aProcessDefinitionResponse_ShippingExchange.getId(), notNullValue());
        assertThat( aProcessDefinitionResponse_ShippingExchange.getUrl(), notNullValue());
        assertThat( aProcessDefinitionResponse_ShippingExchange.getKey(), is( "proc_ShippingExchange_AnnouncePayload"));
        assertThat( aProcessDefinitionResponse_ShippingExchange.getName(), is( "Shipping Exchange Announce Payload"));
        assertThat( aProcessDefinitionResponse_ShippingExchange.getDeploymentId(), notNullValue());
        assertThat( aProcessDefinitionResponse_ShippingExchange.getDeploymentUrl(), notNullValue());

	}


	@Test
	public void runtimeService_startProcessInstanceByKey() throws Exception {		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( APPLICATION_JSON_UTF8);

		HttpEntity<String> entity = new HttpEntity<String>( JSON_ProcessInstanceCreateRequest,headers);
		
		ResponseEntity<String> response = template_ProcessInstanceResponse.postForEntity("/process-api/runtime/process-instances", entity, String.class);     
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        
        String aResponseBody = response.getBody();
        System.out.println( "\n\nPOST /process-api/runtime/process-instances responseBody=" + aResponseBody);
        

        ProcessInstanceResponse aDataProcessInstanceResponse = this.json_ProcessInstanceResponse.parse( aResponseBody).getObject();
        
        System.out.println( "POST /process-api/runtime/process-instances response=\n" + this.json_ProcessInstanceResponse.write( aDataProcessInstanceResponse) + "\n\n");
        
        assertThat(aDataProcessInstanceResponse.getId(), notNullValue());
        assertThat(aDataProcessInstanceResponse.getUrl(), notNullValue());
        assertThat(aDataProcessInstanceResponse.getProcessDefinitionId(), notNullValue());
        assertThat(aDataProcessInstanceResponse.getProcessDefinitionUrl(), notNullValue());
	}
	
}

