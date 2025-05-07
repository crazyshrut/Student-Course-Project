<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Courses List" scope="request" />
<%@ include file="common/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <a href="/courses/add" class="btn btn-success">
            <i class="bi bi-plus"></i> Add New Course
        </a>
    </div>
</div>

<div class="card">
    <div class="card-body">
        <c:if test="${empty courses}">
            <div class="alert alert-info">No courses found.</div>
        </c:if>
        <c:if test="${not empty courses}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Enrolled Students</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${courses}" var="course">
                            <tr>
                                <td>${course.id}</td>
                                <td>${course.title}</td>
                                <td>${course.description.length() > 50 ? course.description.substring(0, 50).concat('...') : course.description}</td>
                                <td>${course.students.size()}</td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="/courses/view/${course.id}" class="btn btn-sm btn-info">View</a>
                                        <a href="/courses/update/${course.id}" class="btn btn-sm btn-warning">Edit</a>
                                        <a href="/courses/delete/${course.id}" class="btn btn-sm btn-danger" 
                                           onclick="return confirm('Are you sure you want to delete this course?')">Delete</a>
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
