/*
 * org.modeldd.flowableboot.test.FlowablePlay01_Test01.java
 *
 * Created @author Antonio Carrasco Valero 201805262149
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

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.rest.service.api.repository.ProcessDefinitionResponse;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.flowable.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.flowable.rest.service.api.runtime.task.TaskActionRequest;
import org.flowable.rest.service.api.runtime.task.TaskQueryRequest;
import org.flowable.rest.service.api.engine.variable.RestVariable;
import org.modeldd.flowableboot.App;
import org.modeldd.flowableboot.test.helpers.restapi.FlowableRESTAPIhelper;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class FlowablePlay01_Test01 {

	public static final String PROCESSDEFINITIONKEY  = "flowableplay01";
	public static final String PROCESSDEFINITIONNAME = "FlowablePlay01 BPMN2";

	@Before
	public void setup() {
		ObjectMapper objectMapper = new ObjectMapper();
		// Possibly configure the mapper
		JacksonTester.initFields(this, objectMapper);
	}

	@Test
	public void contextLoads() throws Exception {
	}

	@Autowired
	TestRestTemplate testRestTemplate;


	public JacksonTester<DataResponse<ProcessDefinitionResponse>> 	json_ProcessDefinitionResponse;
	public JacksonTester<ProcessInstanceCreateRequest> 				json_ProcessInstanceCreateRequest;
	public JacksonTester<ProcessInstanceResponse> 					json_ProcessInstanceResponse;
	public JacksonTester<TaskQueryRequest>  						json_TaskQueryRequest;
	public JacksonTester<DataResponse<TaskResponseForTest>> 		json_TaskResponseForTest;
	public JacksonTester<List<RestVariable>> 						json_ListRestVariable;
	public JacksonTester<TaskActionRequest> 						json_TaskActionRequest;


	@Test
	public void repositoryService_createProcessDefinitionQuery() throws Exception {


		FlowableRESTAPIhelper aHelper = new FlowableRESTAPIhelper(
				testRestTemplate,
				true /* theAssertEssential */,
				true /* theAssertNonEssential */,
				true /* theDumpUrl */,
				true /* theDumpRequestParms */,
				true /* theDumpRequestBody */,
				true /* theDumpRawResponse */,
				true /* theDumpParsedRespons */,
				json_ProcessDefinitionResponse,
				json_ProcessInstanceCreateRequest,
				json_ProcessInstanceResponse,
				json_TaskQueryRequest,
				json_TaskResponseForTest,
				json_ListRestVariable,
				json_TaskActionRequest
				);

		List<ProcessDefinitionResponse> someProcessDefinitionResponses = aHelper.repositoryService_createProcessDefinitionQuery();

		Integer aNumProcessDefinitionResponses = someProcessDefinitionResponses.size();
		assertThat( aNumProcessDefinitionResponses > 0, is(true));
		Integer aFoundProcessDefinitionResponse = -1;
		for( Integer aProcessDefinitionResponseIdx=0; aProcessDefinitionResponseIdx < aNumProcessDefinitionResponses; aProcessDefinitionResponseIdx++) {
			ProcessDefinitionResponse aProcessDefinitionResponse = someProcessDefinitionResponses.get( aProcessDefinitionResponseIdx);
			assertThat(aProcessDefinitionResponse, notNullValue());

			String aKey = aProcessDefinitionResponse.getKey();
			String aName = aProcessDefinitionResponse.getKey();
			assertThat(aKey, notNullValue());
			assertThat(aName, notNullValue());
			if( aKey.equals( PROCESSDEFINITIONKEY) || aName.equals( PROCESSDEFINITIONNAME)) {
				aFoundProcessDefinitionResponse = aProcessDefinitionResponseIdx;
			}
			assertThat(aProcessDefinitionResponse.getId(), notNullValue());
			assertThat(aProcessDefinitionResponse.getUrl(), notNullValue());
			assertThat(aProcessDefinitionResponse.getDeploymentId(), notNullValue());
			assertThat(aProcessDefinitionResponse.getDeploymentUrl(), notNullValue());
		}
		assertThat( aFoundProcessDefinitionResponse >= 0, is(true));
	}



	@Test
	public void startProcessInstanceByKey_createTaskQuery_getVariables_complete_createHistoricActivityInstanceQuery_createHistoricVariableInstanceQuery()
			throws Exception {

		FlowableRESTAPIhelper aHelper = new FlowableRESTAPIhelper(
				testRestTemplate,
				true /* theAssertEssential */,
				true /* theAssertNonEssential */,
				true /* theDumpUrl */,
				true /* theDumpRequestParms */,
				true /* theDumpRequestBody */,
				true /* theDumpRawResponse */,
				true /* theDumpParsedRespons */,
				json_ProcessDefinitionResponse,
				json_ProcessInstanceCreateRequest,
				json_ProcessInstanceResponse,
				json_TaskQueryRequest,
				json_TaskResponseForTest,
				json_ListRestVariable,
				json_TaskActionRequest);


		this.runtimeService_startProcessInstanceByKey( aHelper,
				"ACV" /* theEmployee */,
				10 /* theNrOfHolidays */,
				"Family reunion" /* theDescription */);

		TaskResponseForTest aTaskResponseForTest = taskService_createTaskQuery_returnFirst( aHelper, "managers");

		List<RestVariable> someTaskVariables = taskService_getVariables( aHelper, aTaskResponseForTest);
		Boolean aCompleted = taskService_complete( aHelper, aTaskResponseForTest, true);
	}




	private void runtimeService_startProcessInstanceByKey( FlowableRESTAPIhelper theHelper,
														   String theEmployee,
														   Integer theNrOfHolidays,
														   String theDescription) throws Exception {

		Map<String, Object> someVariables = new HashMap<String, Object>();
		someVariables.put( "employee", theEmployee);
		someVariables.put( "nrOfHolidays", theNrOfHolidays);
		someVariables.put( "description", theDescription);

		ProcessInstanceResponse aProcessInstanceResponse = theHelper.runtimeService_startProcessInstanceByKey(
				PROCESSDEFINITIONKEY, someVariables);

		assertThat( aProcessInstanceResponse, notNullValue());
		assertThat( aProcessInstanceResponse.getId(), notNullValue());
		assertThat( aProcessInstanceResponse.getUrl(), notNullValue());
		assertThat( aProcessInstanceResponse.getProcessDefinitionId(), notNullValue());
		assertThat( aProcessInstanceResponse.getProcessDefinitionUrl(), notNullValue());
	}



	private TaskResponseForTest taskService_createTaskQuery_returnFirst( FlowableRESTAPIhelper theHelper,
															 			 String theCandidateGroupName) throws Exception {

		List<TaskResponseForTest> someTaskResponseForTest = theHelper.taskService_createTaskQuery_taskCandidateGroup( theCandidateGroupName);
		assertThat( someTaskResponseForTest, notNullValue());
		Integer aNumTaskResponseForTest = someTaskResponseForTest.size();
		assertThat( aNumTaskResponseForTest > 0, is( true));

		TaskResponseForTest aTaskResponseForTest = someTaskResponseForTest.get( 0);
		assertThat( aTaskResponseForTest, notNullValue());

		return aTaskResponseForTest;
	}



	private List<RestVariable> taskService_getVariables( FlowableRESTAPIhelper theHelper,
														 TaskResponseForTest theTaskResponse) throws Exception {

		List<RestVariable> someRestVariables = theHelper.taskService_getVariables( theTaskResponse.getId(), null /* theScope */);
		theHelper.dumpTaskVariables( someRestVariables);
		return someRestVariables;
	}




	
	private Boolean taskService_complete( FlowableRESTAPIhelper theHelper,
									   TaskResponseForTest theTaskResponse,
									   Boolean theApproved) throws Exception {

		Map<String,Object> someVariables = new HashMap<String,Object>();
		someVariables.put( "approved", theApproved);
		Boolean aCompleted = theHelper.taskService_complete( theTaskResponse.getId(), someVariables);
		assertThat( aCompleted, is( true));
		return aCompleted;
	}


}
