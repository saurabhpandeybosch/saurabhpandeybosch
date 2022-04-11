<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <%@ taglib prefix="hac" uri="/WEB-INF/custom.tld"%> --%>
<spring:htmlEscape defaultHtmlEscape="true" />


<c:url value="import/csv/uploadThreeDFile" var="submitURL"/>

<template:page pageTitle="${pageTitle}">


	<cms:pageSlot position="ceeaParagraph" var="feature" element="div"
		class="ceeaBannerParagraph">
		Hi, 
			This is test page
		<form:form method="post" action="${submitURL}" modelAttribute="uploadForm" enctype="multipart/form-data">
		Product Code:<br>
    <input type="text" name="code"><br>
  Annotation:<br>
    <input type="text" name="annotation">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="file" name="image"/>
		<input type="submit" value="Upload"/>
		 </form:form>
		
	</cms:pageSlot>
</template:page>

