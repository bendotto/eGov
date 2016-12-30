/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.infra.workflow.multitenant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.admin.master.entity.User;
import org.egov.pims.commons.Designation;

public class WorkflowBean implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String workflowAction;
    protected String workflowComments;
    protected String workflowDepartment;
    protected String currentState;
    protected Long currentDesignationId;
    protected Long currentDepartmentId;
    protected Long currentPositionId;
    protected String businessKey;
    
    protected String workflowId;

    
    public String additionalRule;
    public BigDecimal amountRule;

    protected String pendingActions;
    protected String approverName;
    protected Long approverPositionId;
    protected Long approverDesignationId;
    protected Long approverDepartmentId;
    protected String approverGroupId;
    protected String nextAction;
    protected String nextState;

    protected List<String> validActions;
    protected List<Department> departmentList;
    protected List<Designation> designationList;
    protected List<User> userList;
    protected Map<String,List<Object>> additionalAttributes;
    private String approverDesignationName;
    
    //add attruibute List here to pass from processInstance
    
    public String getWorkflowAction() {
        return workflowAction;
    }
    public void setWorkflowAction(String workflowAction) {
        this.workflowAction = workflowAction;
    }
    public String getWorkflowComments() {
        return workflowComments;
    }
    public void setWorkflowComments(String workflowComments) {
        this.workflowComments = workflowComments;
    }
    public String getWorkflowDepartment() {
        return workflowDepartment;
    }
    public void setWorkflowDepartment(String workflowDepartment) {
        this.workflowDepartment = workflowDepartment;
    }
    public String getCurrentState() {
        return currentState;
    }
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
    public Long getCurrentDesignationId() {
        return currentDesignationId;
    }
    public void setCurrentDesignationId(Long currentDesignationId) {
        this.currentDesignationId = currentDesignationId;
    }
    public Long getCurrentDepartmentId() {
        return currentDepartmentId;
    }
    public void setCurrentDepartmentId(Long currentDepartmentId) {
        this.currentDepartmentId = currentDepartmentId;
    }
    public Long getCurrentPositionId() {
        return currentPositionId;
    }
    public void setCurrentPositionId(Long currentPositionId) {
        this.currentPositionId = currentPositionId;
    }
    public String getBusinessKey() {
        return businessKey;
    }
    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
    public String getAdditionalRule() {
        return additionalRule;
    }
    public void setAdditionalRule(String additionalRule) {
        this.additionalRule = additionalRule;
    }
    public BigDecimal getAmountRule() {
        return amountRule;
    }
    public void setAmountRule(BigDecimal amountRule) {
        this.amountRule = amountRule;
    }
    public String getPendingActions() {
        return pendingActions;
    }
    public void setPendingActions(String pendingActions) {
        this.pendingActions = pendingActions;
    }
    public String getApproverName() {
        return approverName;
    }
    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }
    public Long getApproverPositionId() {
        return approverPositionId;
    }
    public void setApproverPositionId(Long approverPositionId) {
        this.approverPositionId = approverPositionId;
    }
    public Long getApproverDesignationId() {
        return approverDesignationId;
    }
    public void setApproverDesignationId(Long approverDesignationId) {
        this.approverDesignationId = approverDesignationId;
    }
    public Long getApproverDepartmentId() {
        return approverDepartmentId;
    }
    public void setApproverDepartmentId(Long approverDepartmentId) {
        this.approverDepartmentId = approverDepartmentId;
    }
    public String getApproverGroupId() {
        return approverGroupId;
    }
    public void setApproverGroupId(String approverGroupId) {
        this.approverGroupId = approverGroupId;
    }
    public String getNextAction() {
        return nextAction;
    }
    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }
    public String getNextState() {
        return nextState;
    }
    public void setNextState(String nextState) {
        this.nextState = nextState;
    }
    public List<String> getValidActions() {
        return validActions;
    }
    public void setValidActions(List<String> validActions) {
        this.validActions = validActions;
    }
    public List<Department> getDepartmentList() {
        return departmentList;
    }
    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
    public List<Designation> getDesignationList() {
        return designationList;
    }
    public void setDesignationList(List<Designation> designationList) {
        this.designationList = designationList;
    }
    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getWorkflowId() {
        return workflowId;
    }
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
    public String getApproverDesignationName() {
       return this.approverDesignationName;
    }
    public void setApproverDesignationName(String approverDesignationName) {
        this.approverDesignationName = approverDesignationName;
    }

}