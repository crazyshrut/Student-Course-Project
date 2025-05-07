<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Course Details" scope="request" />
<%@ include file="common/header.jsp" %>

<div class="card mb-4">
    <div class="card-header bg-success text-white">
        <h5 class="card-title mb-0">Course Information</h5>
    </div>
    <div class="card-body">
        <div class="row mb-3">
            <div class="col-md-2 fw-bold">ID:</div>
            <div class="col-md-10">${course.id}</div>
        </div>
        <div class="row mb-3">
            <div class="col-md-2 fw-bold">Title:</div>
            <div class="col-md-10">${course.title}</div>
        </div>
        <div class="row mb-3">
            <div class="col-md-2 fw-bold">Description:</div>
            <div class="col-md-10">
                <p class="text-justify">${course.description}</p>
            </div>
        </div>
        
        <div class="d-flex mt-4">
            <a href="/courses" class="btn btn-secondary me-2">Back to List</a>
            <a href="/courses/update/${course.id}" class="btn btn-warning me-2">Edit</a>
            <a href="/courses/delete/${course.id}" class="btn btn-danger" 
               onclick="return confirm('Are you sure you want to delete this course?')">Delete</a>
        </div>
    </div>
</div>

<div class="card">
    <div class="card-header bg-primary text-white">
        <h5 class="card-title mb-0">Enrolled Students</h5>
    </div>
    <div class="card-body">
        <c:if test="${empty course.students}">
            <div class="alert alert-info">No students are enrolled in this course.</div>
        </c:if>
        <c:if test="${not empty course.students}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${course.students}" var="student">
                            <tr>
                                <td>${student.id}</td>
                                <td>${student.name}</td>
                                <td>${student.email}</td>
                                <td>
                                    <a href="/students/view/${student.id}" class="btn btn-sm btn-info">View Student</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>

<%@ include file="common/footer.jsp" %>
