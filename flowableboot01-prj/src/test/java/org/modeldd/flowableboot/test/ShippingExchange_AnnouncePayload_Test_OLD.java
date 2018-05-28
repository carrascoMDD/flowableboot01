/*
 * org.modeldd.flowableboot.test.ShippingExchange_AnnouncePayload_Test.java
 *
 * Created @author Antonio Carrasco Valero 201805260100
 *
 *
 ***************************************************************************

 Copyright 2018 Antonio Carrasco Valero
 Setup a Flowable ( https://github.com/flowable/flowable-engine) playground as standalone Java application.
 
Licensed under the EUPL, Version 1.1 only (the "Licence");
You may not use this work except in compliance with the
Licence.
You may obtain a copy of the Licence at:
https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
See the Licence for the specific language governing
permissions and limitations under the Licence.
 {{License2}}

 {{Licensed1}}
 {{Licensed2}}

 ***************************************************************************
 *
 */

package org.modeldd.flowableboot.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.rest.service.api.repository.ProcessDefinitionResponse;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.flowable.rest.service.api.runtime.task.TaskQueryRequest;
import org.flowable.rest.service.api.runtime.task.TaskResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modeldd.flowableboot.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest( classes = App.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ShippingExchange_AnnouncePayload_Test_OLD {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	public static final String JSON_ProcessInstanceCreateRequest = "{ \"processDefinitionKey\":\"proc_ShippingExchange_AnnouncePayload\", \"variables\": [ { \"name\":\"var_PayloadKind\", \"value\": \"SteelRoll\" }, { \"name\":\"var_PayloadWeight\", \"value\": 12000 }, { \"name\":\"var_PayloadWeightUnit\", \"value\": \"Kg\" }, { \"name\":\"var_PayloadVolume\", \"value\": 2 }, { \"name\":\"var_PayloadWeightUnit\", \"value\": \"M3\" }]}";
	
    @Autowired
    private TestRestTemplate template_ProcessDefinitionResponse;
    
    @Autowired
    private TestRestTemplate template_ProcessInstanceResponse;

    
    
    private JacksonTester<DataResponse<ProcessDefinitionResponse>> json_ProcessDefinitionResponse;
    private JacksonTester<ProcessInstanceResponse>  			   json_ProcessInstanceResponse;
    private JacksonTester<TaskQueryRequest>  			           json_TaskQueryRequest;
    private JacksonTester<DataResponse<TaskResponse>> 		       json_TaskResponse;

    
    
    
    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper(); 
        // Possibly configure the mapper
        JacksonTester.initFields(this, objectMapper);
    }



	@Test
    public void contextLoads() throws Exception {
    }
	
	@Test
	public void repositoryService_createProcessDefinitionQuery_count() throws Exception {
		
		ResponseEntity<String> response = template_ProcessDefinitionResponse.getForEntity("/process-api/repository/process-definitions", String.class);     
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        
        String aResponseBody = response.getBody();
        System.out.println( "\n\nGET /process-api/repository/process-definitions responseBody=" + aResponseBody);

        DataResponse<ProcessDefinitionResponse> aDataProcessDefinitionResponse = this.json_ProcessDefinitionResponse.parse( aResponseBody).getObject();
        
        System.out.println( "GET /process-api/repository/process-definitions response=\n" + this.json_ProcessDefinitionResponse.write( aDataProcessDefinitionResponse).getJson() + "\n\n");
        
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
		
		if( true) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType( APPLICATION_JSON_UTF8);

			HttpEntity<String> entity = new HttpEntity<String>( JSON_ProcessInstanceCreateRequest,headers);
			
			ResponseEntity<String> response = template_ProcessInstanceResponse.postForEntity("/process-api/runtime/process-instances", entity, String.class);     
	        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
	        
	        String aResponseBody = response.getBody();
	        System.out.println( "\n\nPOST /process-api/runtime/process-instances responseBody=" + aResponseBody);
	        
	        ProcessInstanceResponse aDataProcessInstanceResponse = this.json_ProcessInstanceResponse.parse( aResponseBody).getObject();
	        
	        System.out.println( "POST /process-api/runtime/process-instances response=\n" + this.json_ProcessInstanceResponse.write( aDataProcessInstanceResponse).getJson() + "\n\n");
	        
	        assertThat(aDataProcessInstanceResponse.getId(), notNullValue());
	        assertThat(aDataProcessInstanceResponse.getUrl(), notNullValue());
	        assertThat(aDataProcessInstanceResponse.getProcessDefinitionId(), notNullValue());
	        assertThat(aDataProcessInstanceResponse.getProcessDefinitionUrl(), notNullValue());
	        
		}
		
        
		if( true) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType( APPLICATION_JSON_UTF8);

			TaskQueryRequest aTaskQueryRequest = new TaskQueryRequest();
	        aTaskQueryRequest.setCandidateGroup( "managers");	        
	        String aJSON_TaskQueryRequest = this.json_TaskQueryRequest.write( aTaskQueryRequest).getJson();
	        
			HttpEntity<String> entity = new HttpEntity<String>( aJSON_TaskQueryRequest,headers);
			
			ResponseEntity<String> response = template_ProcessInstanceResponse.postForEntity("/process-api/query/tasks", entity, String.class);     
	        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	        
	        String aResponseBody = response.getBody();
	        System.out.println( "\n\nPOST /process-api/query/tasks responseBody=" + aResponseBody);
	        
	        DataResponse<TaskResponse> aDataTaskResponse = this.json_TaskResponse.parse( aResponseBody).getObject();
	        
	        System.out.println( "POST /process-api/query/tasks response=\n" + this.json_TaskResponse.write( aDataTaskResponse).getJson() + "\n\n");
	        
	        assertThat( aDataTaskResponse, notNullValue());
	        List<TaskResponse> someTaskResponses = aDataTaskResponse.getData();
	        assertThat(someTaskResponses.size(), is( 1));
	        
	        TaskResponse aTaskResponse = someTaskResponses.get( 0);       
	        assertThat(aTaskResponse.getId(), notNullValue());
	        assertThat(aTaskResponse.getUrl(), notNullValue());
	        assertThat(aTaskResponse.getProcessDefinitionId(), notNullValue());
	        assertThat(aTaskResponse.getProcessDefinitionUrl(), notNullValue());
	        
		}
		
        
        
        
        
	}
	
}

