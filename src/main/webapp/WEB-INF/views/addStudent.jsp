<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="title" value="Add New Student" scope="request" />
<%@ include file="common/header.jsp" %>

<div class="card">
    <div class="card-body">
        <form:form action="/students/add" method="post" modelAttribute="student">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <form:input path="name" class="form-control" required="true" />
                <form:errors path="name" cssClass="text-danger" />
            </div>
            
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <form:input type="email" path="email" class="form-control" required="true" />
                <form:errors path="email" cssClass="text-danger" />
            </div>
            
            <div class="mb-3">
                <label class="form-label">Select Courses</label>
                <div class="row">
                    <c:forEach items="${courses}" var="course">
                        <div class="col-md-4 mb-2">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="courseIds" value="${course.id}" id="course${course.id}">
                                <label class="form-check-label" for="course${course.id}">
                                    ${course.title}
                                </label>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            
            <div class="d-flex justify-content-between">
                <a href="/students" class="btn btn-secondary">Cancel</a>
                <button type="submit" class="btn btn-primary">Save Student</button>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="common/footer.jsp" %>
