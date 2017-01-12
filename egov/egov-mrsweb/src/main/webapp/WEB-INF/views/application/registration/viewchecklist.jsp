<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<div class="panel-heading">
		<div class="panel-title">
				<spring:message code="lbl.common.docs"/>
		</div>
	</div>
	
	<div class="form-group">
		<label class="col-sm-offset-6 view-content">
			<spring:message code="lbl.submitted.by.couple"/>
		</label>
	</div>
	
<c:forEach var="doc" items="${generalDocuments}" varStatus="status">	
	<div class="form-group">	
		<div class="col-sm-2"></div>
		<div class="col-sm-4 add-margin text-right">
			<c:out value="${doc.name}"></c:out>
		</div>
		<div class="col-sm-2 add-margin text-center">
			<c:set value="false" var="isDocFound"></c:set>
			<c:forEach items="${registration.registrationDocuments}" var="regdoc" varStatus="loopStatus">
				<c:if test="${regdoc.document.id == doc.id}">
					<c:set value="true" var="isDocFound"></c:set>
					<input type="hidden" id="registrationfile${status.index}" value="${regdoc.fileStoreMapper.fileName}|${regdoc.fileStoreMapper.contentType}|${regdoc.base64EncodedFile}">
					<a id="regdoc${status.index}">${regdoc.fileStoreMapper.fileName}</a>
				</c:if>
			</c:forEach>
			<c:if test="${!isDocFound}">
				<div class="view-content">N/A</div>
			</c:if>
		</div>
	</div>

</c:forEach>


	<div class="panel-heading">
		<div class="panel-title">
				<spring:message code="lbl.indi.docs"/>
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-sm-offset-7 col-sm-1 view-content">
			<spring:message code="lbl.husband"/>
		</div>
		<div class="col-sm-2 text-center view-content">
			<spring:message code="lbl.wife"/>
		</div>
	</div>
	
<c:forEach var="doc" items="${individualDocuments}" varStatus="status">	
	
	<div class="form-group">	
		<div class="col-sm-2"></div>
		<div class="col-sm-4 add-margin text-right">
			<c:out value="${doc.name}"></c:out>
		</div>
		<div class="col-sm-2 add-margin text-center">
			<c:set value="false" var="isDocFound"></c:set>
			<c:forEach items="${registration.husband.applicantDocuments}" var="appdoc" varStatus="loopStatus">
				<c:if test="${appdoc.document.id == doc.id}">
					<c:set value="true" var="isDocFound"></c:set>
					<%-- <img src="data:image/jpeg;base64,${appdoc.base64EncodedFile}" width="250px" height="250px"> --%>
					<input type="hidden" id="husbandfile${status.index}" value="${appdoc.fileStoreMapper.fileName}|${appdoc.fileStoreMapper.contentType}|${appdoc.base64EncodedFile}">
					<a id="husbanddoc${status.index}">${appdoc.fileStoreMapper.fileName}</a>
				</c:if>
			</c:forEach>
			<c:if test="${!isDocFound}">
				<div class="view-content">N/A</div>
			</c:if>
		</div>
		<div class="col-sm-2 add-margin text-center">
			<c:set value="false" var="isDocFound"></c:set>
			<c:forEach items="${registration.wife.applicantDocuments}" var="appdoc" varStatus="loopStatus">
				<c:if test="${appdoc.document.id == doc.id}">
					<c:set value="true" var="isDocFound"></c:set>
					<input type="hidden" id="wifefile${status.index}" value="${appdoc.fileStoreMapper.fileName}|${appdoc.fileStoreMapper.contentType}|${appdoc.base64EncodedFile}">
					<a id="wifedoc${status.index}">${appdoc.fileStoreMapper.fileName}</a>
				</c:if>
			</c:forEach>
			<c:if test="${!isDocFound}">
				<div class="view-content">N/A</div>
			</c:if>
		</div>
		<div class="col-sm-1"></div>
	</div>
</c:forEach> 

<script src="<cdn:url value='/resources/js/app/viewdocumentsupload.js?rnd=${app_release_no}'/>"></script>