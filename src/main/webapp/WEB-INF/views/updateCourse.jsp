<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="title" value="Update Course" scope="request" />
<%@ include file="common/header.jsp" %>

<div class="card">
    <div class="card-body">
        <form:form action="/courses/update" method="post" modelAttribute="course">
            <form:hidden path="id" />
            
            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <form:input path="title" class="form-control" required="true" />
                <form:errors path="title" cssClass="text-danger" />
            </div>
            
            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <form:textarea path="description" class="form-control" rows="5" />
                <form:errors path="description" cssClass="text-danger" />
            </div>
            
            <div class="d-flex justify-content-between">
                <a href="/courses" class="btn btn-secondary">Cancel</a>
                <button type="submit" class="btn btn-success">Update Course</button>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="common/footer.jsp" %>
