<%@ include file="header.jsp" %>

<div class="bg-light flex-fill p-4">
    <nav style="--bs-breadcrumb-divider:'>';font-size:14px">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><i class="fa-solid fa-house"></i></li>
            <li class="breadcrumb-item">Category Management</li>
            <li class="breadcrumb-item active">Create Category</li>
        </ol>
    </nav>

    <div class="card shadow-sm p-4">
        <h4>Create New Category</h4>
        <form action="categories" method="post">
            <input type="hidden" name="action" value="create"/>
            <div class="mb-3">
                <label for="name" class="form-label">Category Name</label>
                <input type="text" class="form-control" id="name" name="name" required/>
            </div>
            <button type="submit" class="btn btn-primary"><i class="fas fa-plus me-2"></i>Create Category</button>
            <a href="categories" class="btn btn-secondary ms-2">Cancel</a>
        </form>
    </div>
</div>

<%@ include file="footer.jsp" %>
