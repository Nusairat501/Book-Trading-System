
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>



<div class="bg-light flex-fill">
    <div class="p-2 d-md-none d-flex text-white bg-dark">
        <a href="#" class="text-white" 
           data-bs-toggle="offcanvas"
           data-bs-target="#bdSidebar">
            <i class="fa-solid fa-bars"></i>
        </a>
        <span class="ms-3">${sessionScope.username}</span>
    </div>
    <div class="p-4">
        <nav style="--bs-breadcrumb-divider:'>';font-size:14px">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <i class="fa-solid fa-house"></i>
                </li>
                <li class="breadcrumb-item">Profile </li>
                <li class="breadcrumb-item active">Info & Edit</li>
            </ol>
        </nav>
        <hr>
        <div class="row mb-4">
            <div class="col d-flex justify-content-between align-items-center">
                <h3 class="mb-0">Edit Info</h3>

            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="card shadow-sm">

                    <div class="card-body">
                        <form class="row" action="users" method="post">
                            <input type="hidden" name="action" value="edit-profile">
                            <input type="hidden" name="userId" value="${user.userId}">

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Name</label>
                                <input type="text"
                                       class="form-control"
                                       name="name"
                                       value="${user.name}"
                                       required>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Email</label>
                                <input type="email"
                                       class="form-control"
                                       name="email"
                                       value="${user.email}"
                                       required>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">New Password</label>
                                <input type="password"
                                       class="form-control"
                                       name="password"
                                       placeholder="Leave empty to keep current password">
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Academic Year</label>
                                <input type="number"
                                       class="form-control"
                                       name="academicYear"
                                       value="${user.academicYear}">
                            </div>

                            <div class="d-flex justify-content-end">
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </div>
                        </form>


                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<%@include file="footer.jsp" %>