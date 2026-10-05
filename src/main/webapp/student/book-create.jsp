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
                <li class="breadcrumb-item"><i class="fa-solid fa-house"></i></li>
                <li class="breadcrumb-item">My Books</li>
                <li class="breadcrumb-item active">Add New Book</li>
            </ol>
        </nav>
        <hr>

        <div class="row mb-4">
            <div class="col">
                <h3>Add Book</h3>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <form class="row" action="listings?action=create" method="post" enctype="multipart/form-data">

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Title</label>
                                <input type="text" class="form-control"
                                       name="title" required>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Author</label>
                                <input type="text" class="form-control"
                                       name="author" required>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Edition</label>
                                <input type="text" class="form-control"
                                       name="edition">
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Listing Type</label>
                                <select class="form-select" name="listingType"
                                        id="listingType" required onchange="togglePrice()">
                                    <option value="SELL">Sell</option>
                                    <option value="EXCHANGE">Exchange</option>
                                </select>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Category</label>
                                <select class="form-select" name="categoryId" required>
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat.categoryId}">
                                            ${cat.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Course</label>
                                <select class="form-select" name="courseId" required>
                                    <c:forEach var="course" items="${courses}">
                                        <option value="${course.courseId}">
                                            ${course.code}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Condition</label>
                                <select class="form-select" name="condition" required>
                                    <option value="">Select Condition</option>
                                    <option value="NEW">New</option>
                                    <option value="LIKE_NEW">Like New</option>
                                    <option value="USED">Used</option>
                                    <option value="DAMAGED">Damaged</option>
                                </select>
                            </div>
                            <div class="mb-3 col-lg-6" id="priceField">
                                <label class="form-label">Price ($)</label>
                                <input type="number" class="form-control"
                                       name="price" min="0" step="0.01">
                            </div>

                            <div class="mb-3 col-lg-12">
                                <label class="form-label">Book Image</label>
                                <input type="file" class="form-control"
                                       name="image">
                            </div>

                            <div class="d-flex justify-content-end">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Add
                                </button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script>
    function togglePrice() {
        const type = document.getElementById("listingType").value;
        document.getElementById("priceField").style.display =
                type === 'SELL' ? 'block' : 'none';
    }
    togglePrice();
</script>

<%@include file="footer.jsp" %>
