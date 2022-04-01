<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <%@ taglib prefix="hac" uri="/WEB-INF/custom.tld"%> --%>
<spring:htmlEscape defaultHtmlEscape="true" />

<template:page pageTitle="${pageTitle}">


	<cms:pageSlot position="ceeaParagraph" var="feature" element="div"
		class="ceeaBannerParagraph">
		Hi, 
			This is test page
		
		<form action="upload.jsp" method="post" enctype="multipart/form-data">  
		Select File:<input type="file" name="fname"/><br/>  
		</form>  
		
	</cms:pageSlot>
</template:page>





