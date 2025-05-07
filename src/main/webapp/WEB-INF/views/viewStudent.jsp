<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Student Details" scope="request" />
<%@ include file="common/header.jsp" %>

<div class="card mb-4">
    <div class="card-header bg-primary text-white">
        <h5 class="card-title mb-0">Student Information</h5>
    </div>
    <div class="card-body">
        <div class="row mb-3">
            <div class="col-md-2 fw-bold">ID:</div>
            <div class="col-md-10">${student.id}</div>
        </div>
        <div class="row mb-3">
            <div class="col-md-2 fw-bold">Name:</div>
            <div class="col-md-10">${student.name}</div>
        </div>
        <div class="row mb-3">
            <div class="col-md-2 fw-bold">Email:</div>
            <div class="col-md-10">${student.email}</div>
        </div>
        
        <div class="d-flex mt-4">
            <a href="/students" class="btn btn-secondary me-2">Back to List</a>
            <a href="/students/update/${student.id}" class="btn btn-warning me-2">Edit</a>
            <a href="/students/delete/${student.id}" class="btn btn-danger" 
               onclick="return confirm('Are you sure you want to delete this student?')">Delete</a>
        </div>
    </div>
</div>

<div class="card">
    <div class="card-header bg-success text-white">
        <h5 class="card-title mb-0">Enrolled Courses</h5>
    </div>
    <div class="card-body">
        <c:if test="${empty student.courses}">
            <div class="alert alert-info">This student is not enrolled in any courses.</div>
        </c:if>
        <c:if test="${not empty student.courses}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${student.courses}" var="course">
                            <tr>
                                <td>${course.id}</td>
                                <td>${course.title}</td>
                                <td>${course.description}</td>
                                <td>
                                    <a href="/courses/view/${course.id}" class="btn btn-sm btn-info">View Course</a>
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
