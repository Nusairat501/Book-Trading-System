<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.time.LocalDate" %>

<c:choose>
    <c:when test="${sessionScope.role == 'ADMIN'}">
        <%@include file="header.jsp" %>
    </c:when>
</c:choose>

<div class="bg-light flex-fill">
    <div class="p-2 d-md-none d-flex text-white bg-dark">
        <a href="#" class="text-white" 
           data-bs-toggle="offcanvas"
           data-bs-target="#bdSidebar">
            <i class="fa-solid fa-bars"></i>
        </a>
        <span class="ms-3">${sessionScope.name}</span>
    </div>
    <div class="p-4">
        <nav style="--bs-breadcrumb-divider:'>';font-size:14px">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <i class="fa-solid fa-house"></i>
                </li>
                <li class="breadcrumb-item">Users Management</li>
                <li class="breadcrumb-item active">Users List</li>
            </ol>
        </nav>
        <hr>
        <div class="row mb-4">
            <div class="col d-flex justify-content-between align-items-center">
                <h3 class="mb-0">Users List</h3>
               
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="card shadow-sm p-3">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Academic Year</th>
                                        <th>Role</th>
                                        <th>Blocked</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="user" items="${users}">
                                        <tr>
                                            <td>${user.userId}</td>
                                            <td>${user.name}</td>
                                            <td>${user.email}</td>
                                            <td><c:out value="${user.academicYear != null ? user.academicYear : '-'}"/></td>
                                            <td>${user.role.name()}</td>
                                            <td><c:choose>
                                                    <c:when test="${user.blocked}">Yes</c:when>
                                                    <c:otherwise>No</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <form action="users" method="post" class="d-inline">
                                                    <input type="hidden" name="action" value="delete-user">
                                                    <input type="hidden" name="userId" value="${user.userId}">
                                                    <button class="btn btn-sm btn-danger" 
                                                            onclick="return confirm('Are you sure you want to delete this user?');" 
                                                            title="Delete User">
                                                        <i class="fas fa-trash-alt"></i> Delete
                                                    </button>
                                                </form>

                                                <c:choose>
                                                    <c:when test="${user.blocked}">
                                                        <form action="users" method="post" class="d-inline">
                                                            <input type="hidden" name="action" value="unblock-user">
                                                            <input type="hidden" name="userId" value="${user.userId}">
                                                            <button class="btn btn-sm btn-success"
                                                                    onclick="return confirm('Unblock this user?');">
                                                                <i class="fas fa-unlock"></i> Unblock
                                                            </button>
                                                        </form>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <form action="users" method="post" class="d-inline">
                                                            <input type="hidden" name="action" value="block-user">
                                                            <input type="hidden" name="userId" value="${user.userId}">
                                                            <button class="btn btn-sm btn-warning"
                                                                    onclick="return confirm('Block this user?');">
                                                                <i class="fas fa-ban"></i> Block
                                                            </button>
                                                        </form>
                                                    </c:otherwise>
                                                </c:choose>

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
    </div>

    <!-- Include Font Awesome for Icons -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/js/all.min.js"></script>
</div>

<%@include file="footer.jsp" %>
