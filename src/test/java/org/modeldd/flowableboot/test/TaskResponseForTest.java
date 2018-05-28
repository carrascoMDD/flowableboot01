/*
 * org.modeldd.flowableboot.test.TaskResponseForTest.java
 *
 * Created @author Antonio Carrasco Valero 201805272010
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.flowable.common.rest.util.DateToStringSerializer;
import org.flowable.rest.service.api.engine.variable.RestVariable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author acv
 *
 */
public class TaskResponseForTest  {

	protected String id;
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDelegationState() {
		return delegationState;
	}

	public void setDelegationState(String delegationState) {
		this.delegationState = delegationState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getParentTaskUrl() {
		return parentTaskUrl;
	}

	public void setParentTaskUrl(String parentTaskUrl) {
		this.parentTaskUrl = parentTaskUrl;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getExecutionUrl() {
		return executionUrl;
	}

	public void setExecutionUrl(String executionUrl) {
		this.executionUrl = executionUrl;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessInstanceUrl() {
		return processInstanceUrl;
	}

	public void setProcessInstanceUrl(String processInstanceUrl) {
		this.processInstanceUrl = processInstanceUrl;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionUrl() {
		return processDefinitionUrl;
	}

	public void setProcessDefinitionUrl(String processDefinitionUrl) {
		this.processDefinitionUrl = processDefinitionUrl;
	}

	public List<RestVariable> getVariables() {
		return variables;
	}

	public void setVariables(List<RestVariable> variables) {
		this.variables = variables;
	}

	protected String url;
    protected String owner;
    protected String assignee;
    protected String delegationState;
    protected String name;
    protected String description;
    @JsonSerialize(using = DateToStringSerializer.class, as = Date.class)
    protected Date createTime;
    @JsonSerialize(using = DateToStringSerializer.class, as = Date.class)
    protected Date dueDate;
    protected int priority;
    protected boolean suspended;
    protected String taskDefinitionKey;
    protected String tenantId;
    protected String category;
    protected String formKey;

    // References to other resources
    protected String parentTaskId;
    protected String parentTaskUrl;
    protected String executionId;
    protected String executionUrl;
    protected String processInstanceId;
    protected String processInstanceUrl;
    protected String processDefinitionId;
    protected String processDefinitionUrl;

    protected List<RestVariable> variables = new ArrayList<>();

}
