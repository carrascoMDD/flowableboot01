/*
 * org.modeldd.flowableboot.test.ShippingExchange01AnnouncePayloadTest.java
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

package org.modeldd.flowableboot.test.helpers.restapi;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;


import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.modeldd.flowableboot.test.TestTaskResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.rest.service.api.engine.variable.RestVariable;
import org.flowable.rest.service.api.repository.ProcessDefinitionResponse;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.flowable.rest.service.api.runtime.task.TaskActionRequest;
import org.flowable.rest.service.api.runtime.task.TaskQueryRequest;
import org.flowable.rest.service.api.runtime.task.TaskResponse;


import org.modeldd.flowableboot.App;
import org.modeldd.flowableboot.test.TestTaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;



public class FlowableRESTAPIhelper {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	public static final String URL_PROCESSDEFINITIONS 	= "/process-api/repository/process-definitions";
	public static final String URL_PROCESSINSTANCES   	= "/process-api/runtime/process-instances";
	public static final String URL_QUERYTASKS 			= "/process-api/query/tasks";
	public static final String URL_TASKVARIABLESALL 	= "/process-api/runtime/tasks/{taskId}/variables";
	public static final String URL_TASKVARIABLESLOCAL 	= "/process-api/runtime/tasks/{taskId}/variables?scope=local";
    public static final String URL_TASKVARIABLESGLOBAL 	= "/process-api/runtime/tasks/{taskId}/variables?scope=global";
    public static final String URL_EXECUTETASKACTION    = "/process-api/runtime/tasks/{taskId}";

	public static final String INDENT 	= "    ";





	private TestRestTemplate _v_TestRestTemplate;
	private Boolean _v_AssertEssential = false;
	private Boolean _v_AssertNonEssential = false;
	private Boolean _v_DumpUrl = false;
	private Boolean _v_DumpRequestParms = false;
	private Boolean _v_DumpRequestBody = false;
	private Boolean _v_DumpRawResponse = false;
	private Boolean _v_DumpParsedResponse  = false;

    private JacksonTester<DataResponse<ProcessDefinitionResponse>>  _v_Json_ProcessDefinitionResponse;
    private JacksonTester<ProcessInstanceCreateRequest>             _v_Json_ProcessInstanceCreateRequest;
    private JacksonTester<ProcessInstanceResponse>                  _v_Json_ProcessInstanceResponse;
    private JacksonTester<TaskQueryRequest>                         _v_Json_TaskQueryRequest;
    private JacksonTester<DataResponse<TestTaskResponse>>        _v_Json_TestTaskResponse;
    private JacksonTester<List<RestVariable>>                       _v_Json_ListRestVariable;
    private JacksonTester<TaskActionRequest>                        _v_Json_TaskActionRequest;
    
    
	public FlowableRESTAPIhelper(
            TestRestTemplate theTestRestTemplate,
			Boolean theAssertEssential, 
			Boolean theAssertNonEssential,
			Boolean theDumpUrl,
			Boolean theDumpRequestParms,
			Boolean theDumpRequestBody,
			Boolean theDumpRawResponse,
			Boolean theDumpParsedResponse,
            JacksonTester<DataResponse<ProcessDefinitionResponse>> theJson_ProcessDefinitionResponse,
            JacksonTester<ProcessInstanceCreateRequest> theJson_ProcessInstanceCreateRequest,
            JacksonTester<ProcessInstanceResponse> theJson_ProcessInstanceResponse,
            JacksonTester<TaskQueryRequest> theJson_TaskQueryRequest,
            JacksonTester<DataResponse<TestTaskResponse>> theJson_TestTaskResponse,
            JacksonTester<List<RestVariable>> theJson_ListRestVariable,
            JacksonTester<TaskActionRequest> theJson_TaskActionRequest) {
        this._v_TestRestTemplate   = theTestRestTemplate;
		this._v_AssertEssential    = theAssertEssential;
		this._v_AssertNonEssential = theAssertNonEssential;
		this._v_DumpUrl            = theDumpUrl;
		this._v_DumpRequestParms   = theDumpRequestParms;
		this._v_DumpRequestBody    = theDumpRequestBody;
		this._v_DumpRawResponse    = theDumpRawResponse;
		this._v_DumpParsedResponse = theDumpParsedResponse;

        this._v_Json_ProcessDefinitionResponse   = theJson_ProcessDefinitionResponse;    
        this._v_Json_ProcessInstanceCreateRequest= theJson_ProcessInstanceCreateRequest; 
        this._v_Json_ProcessInstanceResponse     = theJson_ProcessInstanceResponse;      
        this._v_Json_TaskQueryRequest            = theJson_TaskQueryRequest;             
        this._v_Json_TestTaskResponse         = theJson_TestTaskResponse;          
        this._v_Json_ListRestVariable            = theJson_ListRestVariable;             
        this._v_Json_TaskActionRequest           = theJson_TaskActionRequest;            
    }
	    
	public Boolean fAnyDump() {
		return this._v_DumpUrl &&
			this._v_AssertEssential    &&
			this._v_AssertNonEssential &&
			this._v_DumpRequestParms   &&
			this._v_DumpRequestBody    &&
			this._v_DumpRawResponse    &&
			this._v_DumpParsedResponse;
	}

	public List<ProcessDefinitionResponse> repositoryService_createProcessDefinitionQuery() throws Exception {
		String aMethodName = "repositoryService_createProcessDefinitionQuery";
		if( fAnyDump()) System.out.println( "\n\n" + aMethodName + " BEGIN");

		// Flowable service access here
		if( this._v_DumpUrl) System.out.println( INDENT + "Url=" + URL_PROCESSDEFINITIONS);
		ResponseEntity<String> aResponseEntity = this._v_TestRestTemplate.getForEntity( URL_PROCESSDEFINITIONS, String.class);
		if( this._v_AssertEssential) assertThat( aResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        String aResponseBody = aResponseEntity.getBody();
        if( this._v_DumpRawResponse) System.out.println( INDENT + "responseBody=\n" + aResponseBody);
        DataResponse<ProcessDefinitionResponse> aDataProcessDefinitionResponse = this._v_Json_ProcessDefinitionResponse.parse( aResponseBody).getObject();
		if( this._v_AssertEssential) assertThat( aDataProcessDefinitionResponse, notNullValue());
		if( this._v_DumpParsedResponse) System.out.println( INDENT + "response=\n" + this._v_Json_ProcessDefinitionResponse.write( aDataProcessDefinitionResponse).getJson() + "\n\n");
        
        List<ProcessDefinitionResponse> someProcessDefinitionResponses = aDataProcessDefinitionResponse.getData();
		if( this._v_AssertEssential) assertThat( someProcessDefinitionResponses, notNullValue());
		if( fAnyDump()) System.out.println( aMethodName + " END\n\n");

		return someProcessDefinitionResponses;
	}


	public ProcessInstanceResponse runtimeService_startProcessInstanceByKey( String theProcessDefinitionKey, Map<String, Object> theVariables) throws Exception {
		String aMethodName = "runtimeService_startProcessInstanceByKey";
		if( fAnyDump()) System.out.println( "\n\n" + aMethodName + " BEGIN");

		ProcessInstanceCreateRequest aProcessInstanceCreateRequest = new ProcessInstanceCreateRequest();
		aProcessInstanceCreateRequest.setProcessDefinitionKey( theProcessDefinitionKey);
		List<RestVariable> someVariables = this.restVariablesFromMap( theVariables);
		aProcessInstanceCreateRequest.setVariables( someVariables);
		String aJSON_ProcessInstanceCreateRequest = this._v_Json_ProcessInstanceCreateRequest.write( aProcessInstanceCreateRequest).getJson();


		// Flowable service access here
		if( this._v_DumpUrl) System.out.println( INDENT + "Url=" + URL_PROCESSINSTANCES);
        if( this._v_DumpRequestBody) System.out.println( INDENT + "requestBody=" + aJSON_ProcessInstanceCreateRequest);
        HttpHeaders someHttpHeaders = new HttpHeaders();
        someHttpHeaders.setContentType( APPLICATION_JSON_UTF8);
        HttpEntity<String> anHttpEntity = new HttpEntity<String>( aJSON_ProcessInstanceCreateRequest, someHttpHeaders);
        ResponseEntity<String> aResponseEntity = this._v_TestRestTemplate.postForEntity( URL_PROCESSINSTANCES, anHttpEntity, String.class);
		if( this._v_AssertEssential) assertThat(aResponseEntity.getStatusCode(), equalTo(HttpStatus.CREATED));

		String aResponseBody = aResponseEntity.getBody();
		if( this._v_DumpRawResponse) System.out.println( INDENT + "responseBody=\n" + aResponseBody);
		ProcessInstanceResponse aProcessInstanceResponse = this._v_Json_ProcessInstanceResponse.parse( aResponseBody).getObject();
		if( this._v_AssertEssential) assertThat( aProcessInstanceResponse, notNullValue());
		if( this._v_DumpParsedResponse) System.out.println( INDENT + "response=\n" + this._v_Json_ProcessInstanceResponse.write( aProcessInstanceResponse).getJson() + "\n\n");

		if( this._v_AssertNonEssential) {
			assertThat(aProcessInstanceResponse.getId(), notNullValue());
			assertThat(aProcessInstanceResponse.getUrl(), notNullValue());
			assertThat(aProcessInstanceResponse.getProcessDefinitionId(), notNullValue());
			assertThat(aProcessInstanceResponse.getProcessDefinitionUrl(), notNullValue());
		}
		if( fAnyDump()) System.out.println( aMethodName + " END\n\n");

		return aProcessInstanceResponse;
	}





    public List<TestTaskResponse> taskService_createTaskQuery_taskCandidateGroup( String theCandidateGroupName) throws Exception {
        String aMethodName = "taskService_createTaskQuery_taskCandidateGroup";
        if( fAnyDump()) System.out.println( "\n\n" + aMethodName + " BEGIN");

		TaskQueryRequest aTaskQueryRequest = new TaskQueryRequest();
		aTaskQueryRequest.setCandidateGroup( theCandidateGroupName);
		String aJSON_TaskQueryRequest = this._v_Json_TaskQueryRequest.write( aTaskQueryRequest).getJson();


		// Flowable service access here
        if( this._v_DumpUrl) System.out.println( INDENT + "Url=" + URL_QUERYTASKS);
        HttpHeaders someHttpHeaders = new HttpHeaders();
        someHttpHeaders.setContentType( APPLICATION_JSON_UTF8);
        HttpEntity<String> anHttpEntity = new HttpEntity<String>( aJSON_TaskQueryRequest, someHttpHeaders);
        ResponseEntity<String> aResponseEntity = this._v_TestRestTemplate.postForEntity( URL_QUERYTASKS, anHttpEntity, String.class);
		if( this._v_AssertEssential) assertThat( aResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));

		String aResponseBody = aResponseEntity.getBody();
		if( this._v_DumpRawResponse) System.out.println( INDENT + "responseBody=\n" + aResponseBody);
		DataResponse<TestTaskResponse> aDataTestTaskResponse = this._v_Json_TestTaskResponse.parse( aResponseBody).getObject();
		if( this._v_AssertEssential) assertThat( aDataTestTaskResponse, notNullValue());
		if( this._v_DumpParsedResponse) System.out.println( INDENT + "response=\n" + this._v_Json_TestTaskResponse.write( aDataTestTaskResponse).getJson() + "\n\n");

        List<TestTaskResponse> someTestTaskResponse = aDataTestTaskResponse.getData();
		if( this._v_AssertEssential) assertThat( someTestTaskResponse, notNullValue());
        if( fAnyDump()) System.out.println( aMethodName + " END\n\n");

		return someTestTaskResponse;
	}





    public List<RestVariable> taskService_getVariables( String theTaskId, String theScope) throws Exception {
        String aMethodName = "taskService_getVariables";
        if( fAnyDump()) System.out.println( "\n\n" + aMethodName + " BEGIN");

        // Flowable service access here
        String aURL = ( theScope == null ? URL_TASKVARIABLESALL : ( theScope.equals( "local") ? URL_TASKVARIABLESLOCAL : (  theScope.equals( "global") ? URL_TASKVARIABLESGLOBAL : URL_TASKVARIABLESALL)));
        aURL = aURL.replace("{taskId}", theTaskId);
        if( this._v_DumpUrl) System.out.println( INDENT + "Url=" + aURL);
        ResponseEntity<String> response = this._v_TestRestTemplate.getForEntity(aURL, String.class);
        if( this._v_AssertEssential) assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        String aResponseBody = response.getBody();
        if( this._v_DumpRawResponse) System.out.println( INDENT + "responseBody=\n" + aResponseBody);
        List<RestVariable> someRestVariables = this._v_Json_ListRestVariable.parse(aResponseBody).getObject();
        if( this._v_AssertEssential) assertThat( someRestVariables, notNullValue());
        if( this._v_DumpParsedResponse) System.out.println( INDENT + "response=\n" + this._v_Json_ListRestVariable.write( someRestVariables).getJson() + "\n\n");
        if( fAnyDump()) System.out.println( aMethodName + " END\n\n");

        return someRestVariables;
    }






    public Boolean taskService_complete( String theTaskId, Map<String, Object> theVariables) throws Exception {
        String aMethodName = "taskService_complete";
        if( fAnyDump()) System.out.println( "\n\n" + aMethodName + " BEGIN");

        TaskActionRequest aTaskActionRequest = new TaskActionRequest();
        aTaskActionRequest.setAction( TaskActionRequest.ACTION_COMPLETE);
        List<RestVariable> someVariables = this.restVariablesFromMap( theVariables);
        aTaskActionRequest.setVariables( someVariables);
        String aJSON_TaskActionRequest = this._v_Json_TaskActionRequest.write( aTaskActionRequest).getJson();


        // Flowable service access here
        String aURL = URL_EXECUTETASKACTION.replace("{taskId}", theTaskId);
        if( this._v_DumpUrl) System.out.println( INDENT + "Url=" + aURL);
        if( this._v_DumpRequestBody) System.out.println( INDENT + "requestBody=" + aJSON_TaskActionRequest);
        HttpHeaders someHttpHeaders = new HttpHeaders();
        someHttpHeaders.setContentType( APPLICATION_JSON_UTF8);
        HttpEntity<String> anHttpEntity = new HttpEntity<String>( aJSON_TaskActionRequest, someHttpHeaders);
        ResponseEntity<String> aResponseEntity = this._v_TestRestTemplate.postForEntity( aURL, anHttpEntity, String.class);
        if( this._v_AssertEssential) assertThat(aResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));

        Boolean aCompleted = aResponseEntity.getStatusCode().equals( HttpStatus.OK);
        if( aCompleted){}/*CQT*/
        if( fAnyDump()) System.out.println( aMethodName + " END\n\n");
        return aCompleted;
    }




    public List<RestVariable> restVariablesFromMap( Map<String, Object> theVariables) throws Exception {
        List<RestVariable> someVariables = new ArrayList<RestVariable>();
        for ( Map.Entry<String, Object> aVariableNameAndValue : theVariables.entrySet()) {
            RestVariable aRestVariable = new RestVariable();
            aRestVariable.setName( aVariableNameAndValue.getKey());
            aRestVariable.setValue( aVariableNameAndValue.getValue());
            someVariables.add( aRestVariable);
        }
        return someVariables;
    }



    public void dumpTaskVariables( List<RestVariable> theRestVariables) throws Exception {
        String aMethodName = "dumpTaskVariables";
        if( fAnyDump()) System.out.println( "\n\n" + aMethodName + " BEGIN");

        Integer aNumRestVariables = theRestVariables.size();

        System.out.println( INDENT + "aNumRestVariables = " + aNumRestVariables);

        for (Integer aRestVariableIdx = 0; aRestVariableIdx < aNumRestVariables; aRestVariableIdx++) {
            RestVariable aRestVariable = theRestVariables.get( aRestVariableIdx);
            String aValueStr = "";
            Object aValue = aRestVariable.getValue();
            switch( aRestVariable.getType()) {
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
            }
            System.out.println( INDENT +
                    "variable name=" + aRestVariable.getName() + " " +
                    "variable type=" + aRestVariable.getType() + " " +
                    "value=" + aValueStr + " " +
                    "valueUrl=" + aRestVariable.getValueUrl());

            assertThat(aRestVariable.getName(), notNullValue());
            assertThat(aRestVariable.getType(), notNullValue());
        }
        if( fAnyDump()) System.out.println( aMethodName + " END\n\n");
    }



}

        /*
		if( true) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType( APPLICATION_JSON_UTF8);

			TaskQueryRequest aTaskQueryRequest = new TaskQueryRequest();
	        aTaskQueryRequest.setCandidateGroup( "managers");
	        String aJSON_TaskQueryRequest = this._v_Json_TaskQueryRequest.write( aTaskQueryRequest).getJson();

			HttpEntity<String> entity = new HttpEntity<String>( aJSON_TaskQueryRequest,headers);

			ResponseEntity<String> response = template_ProcessInstanceResponse.postForEntity("/process-api/query/tasks", entity, String.class);
	        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

	        String aResponseBody = response.getBody();
	        System.out.println( "\n\nPOST /process-api/query/tasks responseBody=" + aResponseBody);

	        DataResponse<TaskResponse> aDataTaskResponse = this._v_Json_TaskResponse.parse( aResponseBody).getObject();

	        System.out.println( "POST /process-api/query/tasks response=\n" + this._v_Json_TaskResponse.write( aDataTaskResponse).getJson() + "\n\n");

	        assertThat( aDataTaskResponse, notNullValue());
	        List<TaskResponse> someTaskResponses = aDataTaskResponse.getData();
	        assertThat(someTaskResponses.size(), is( 1));

	        TaskResponse aTaskResponse = someTaskResponses.get( 0);
	        assertThat(aTaskResponse.getId(), notNullValue());
	        assertThat(aTaskResponse.getUrl(), notNullValue());
	        assertThat(aTaskResponse.getProcessDefinitionId(), notNullValue());
	        assertThat(aTaskResponse.getProcessDefinitionUrl(), notNullValue());

		}

        */
