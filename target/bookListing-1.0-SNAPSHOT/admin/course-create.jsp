<%@ include file="header.jsp" %>

<div class="bg-light flex-fill p-4">
    <nav style="--bs-breadcrumb-divider:'>';font-size:14px">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><i class="fa-solid fa-house"></i></li>
            <li class="breadcrumb-item">Course Management</li>
            <li class="breadcrumb-item active">Create Course</li>
        </ol>
    </nav>

    <div class="card shadow-sm p-4">
        <h4>Create New Course</h4>
        <form action="courses" method="post">
            <input type="hidden" name="action" value="create"/>
            <div class="mb-3">
                <label for="code" class="form-label">Course Code</label>
                <input type="text" class="form-control" id="code" name="code" required/>
            </div>
            <div class="mb-3">
                <label for="departmentId" class="form-label">Department</label>
                <select class="form-select" id="departmentId" name="departmentId" required>
                    <option value="">-- Select Department --</option>
                    <option value="1" ${course.departmentId == 1 ? 'selected' : ''}>Computer Science</option>
                    <option value="2">Electrical Engineering</option>
                    <option value="3">Mechanical Engineering</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary"><i class="fas fa-plus me-2"></i>Create Course</button>
            <a href="courses" class="btn btn-secondary ms-2">Cancel</a>
        </form>
    </div>
</div>

<%@ include file="footer.jsp" %>
