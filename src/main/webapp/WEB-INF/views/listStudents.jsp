<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Students List" scope="request" />
<%@ include file="common/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <a href="/students/add" class="btn btn-primary">
            <i class="bi bi-plus"></i> Add New Student
        </a>
    </div>
</div>

<div class="card">
    <div class="card-body">
        <c:if test="${empty students}">
            <div class="alert alert-info">No students found.</div>
        </c:if>
        <c:if test="${not empty students}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Enrolled Courses</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${students}" var="student">
                            <tr>
                                <td>${student.id}</td>
                                <td>${student.name}</td>
                                <td>${student.email}</td>
                                <td>${student.courses.size()}</td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="/students/view/${student.id}" class="btn btn-sm btn-info">View</a>
                                        <a href="/students/update/${student.id}" class="btn btn-sm btn-warning">Edit</a>
                                        <a href="/students/delete/${student.id}" class="btn btn-sm btn-danger" 
                                           onclick="return confirm('Are you sure you want to delete this student?')">Delete</a>
                                    </div>
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
