<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Home" scope="request" />
<%@ include file="common/header.jsp" %>

<div class="row">
    <div class="col-md-6">
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <h5 class="card-title mb-0">Students</h5>
            </div>
            <div class="card-body">
                <h3 class="card-title">${studentCount}</h3>
                <p class="card-text">Total students registered in the system</p>
                <a href="/students" class="btn btn-primary">Manage Students</a>
                <a href="/students/add" class="btn btn-outline-primary">Add New Student</a>
            </div>
        </div>
    </div>
    
    <div class="col-md-6">
        <div class="card mb-4">
            <div class="card-header bg-success text-white">
                <h5 class="card-title mb-0">Courses</h5>
            </div>
            <div class="card-body">
                <h3 class="card-title">${courseCount}</h3>
                <p class="card-text">Total courses available in the system</p>
                <a href="/courses" class="btn btn-success">Manage Courses</a>
                <a href="/courses/add" class="btn btn-outline-success">Add New Course</a>
            </div>
        </div>
    </div>
</div>

<div class="card">
    <div class="card-header bg-info text-white">
        <h5 class="card-title mb-0">Student-Course Enrollments</h5>
    </div>
    <div class="card-body">
        <c:if test="${empty studentCourseData}">
            <div class="alert alert-info">No course enrollments found.</div>
        </c:if>
        <c:if test="${not empty studentCourseData}">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>Student Name</th>
                        <th>Course</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${studentCourseData}" var="data">
                        <tr>
                            <td>${data.studentName}</td>
                            <td>${data.courseTitle}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>

<%@ include file="common/footer.jsp" %>
