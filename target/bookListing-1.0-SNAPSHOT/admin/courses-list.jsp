<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="header.jsp" %>

<div class="bg-light flex-fill">
    <div class="p-4">
        <nav style="--bs-breadcrumb-divider:'>';font-size:14px">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><i class="fa-solid fa-house"></i></li>
                <li class="breadcrumb-item">Course Management</li>
                <li class="breadcrumb-item active">Courses List</li>
            </ol>
        </nav>
        <hr>
        <div class="row mb-4">
            <div class="col d-flex justify-content-between align-items-center">
                <h3 class="mb-0">Courses List</h3>
                <a class="btn btn-primary" href="courses?action=create">
                    <i class="fas fa-plus me-2"></i>Create New Course
                </a>
            </div>
        </div>

        <div class="card shadow-sm p-3">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Code</th>
                                <th>Department ID</th>
                                <th>Created At</th>
                                <th>Updated At</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="course" items="${courses}">
                                <tr>
                                    <td>${course.courseId}</td>
                                    <td>${course.code}</td>
                                    <td><c:out value="${course.department != null ? course.department.name : '-'}"/></td>
                                    <td>${course.createdAt}</td>
                                    <td>${course.updatedAt}</td>
                                    <td>
                                        <form action="courses" method="get" class="d-inline">
                                            <input type="hidden" name="action" value="edit"/>
                                            <input type="hidden" name="courseId" value="${course.courseId}"/>
                                            <button class="btn btn-sm btn-primary"><i class="fas fa-edit"></i> Edit</button>
                                        </form>
                                        <form action="courses" method="post" class="d-inline">
                                            <input type="hidden" name="action" value="delete"/>
                                            <input type="hidden" name="courseId" value="${course.courseId}"/>
                                            <button class="btn btn-sm btn-danger" onclick="return confirm('Are you sure?');">
                                                <i class="fas fa-trash-alt"></i> Delete
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</div>

<%@ include file="footer.jsp" %>
