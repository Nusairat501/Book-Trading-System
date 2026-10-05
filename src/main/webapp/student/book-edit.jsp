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
                <li class="breadcrumb-item active">Edit</li>
            </ol>
        </nav>
        <hr>

        <div class="row mb-4">
            <div class="col">
                <h3>Edit Listing</h3>
            </div>
        </div>

        <!-- Block editing if not AVAILABLE -->
        <c:if test="${listing.status ne 'AVAILABLE'}">
            <div class="alert alert-warning">
                This listing cannot be edited because its status is
                <strong>${listing.status}</strong>.
            </div>
        </c:if>

        <div class="row">
            <div class="col">
                <div class="card shadow-sm">
                    <div class="card-body">

                        <form class="row"
                              action="listings"
                              method="post"
                              enctype="multipart/form-data">

                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="listingId" value="${listing.listingId}">

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Title</label>
                                <input type="text"
                                       class="form-control"
                                       name="title"
                                       value="${listing.title}"
                                       required
                                       ${listing.status ne 'AVAILABLE' ? 'disabled' : ''}>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Author</label>
                                <input type="text"
                                       class="form-control"
                                       name="author"
                                       value="${listing.author}"
                                       required
                                       ${listing.status ne 'AVAILABLE' ? 'disabled' : ''}>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Edition</label>
                                <input type="text"
                                       class="form-control"
                                       name="edition"
                                       value="${listing.edition}"
                                       ${listing.status ne 'AVAILABLE' ? 'disabled' : ''}>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Listing Type</label>
                                <select class="form-select"
                                        id="listingType"
                                        name="listingType"
                                        ${listing.status ne 'AVAILABLE' ? 'disabled' : ''}>
                                    <option value="SALE"
                                            ${listing.listingType eq 'SELL' ? 'selected' : ''}>
                                        Sell
                                    </option>
                                    <option value="EXCHANGE"
                                            ${listing.listingType eq 'EXCHANGE' ? 'selected' : ''}>
                                        Exchange
                                    </option>
                                </select>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Category</label>
                                <select class="form-select"
                                        name="categoryId"
                                        ${listing.status ne 'AVAILABLE' ? 'disabled' : ''}>
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat.categoryId}"
                                                ${cat.categoryId == listing.categoryId ? 'selected' : ''}>
                                            ${cat.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Course</label>
                                <select class="form-select"
                                        name="courseId"
                                        ${listing.status ne 'AVAILABLE' ? 'disabled' : ''}>
                                    <c:forEach var="course" items="${courses}">
                                        <option value="${course.courseId}"
                                                ${course.courseId == listing.courseId ? 'selected' : ''}>
                                            ${course.code}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3 col-lg-6">
                                <label class="form-label">Condition</label>
                                <select class="form-select"
                                        name="condition"
                                        ${listing.status ne 'AVAILABLE' ? 'disabled' : ''}>
                                    <option value="NEW" ${listing.condition.name() eq 'NEW' ? 'selected' : ''}>New</option>
                                    <option value="LIKE_NEW" ${listing.condition.name() eq 'LIKE_NEW' ? 'selected' : ''}>Like New</option>
                                    <option value="USED" ${listing.condition.name() eq 'USED' ? 'selected' : ''}>USED</option>
                                    <option value="DAMAGED" ${listing.condition.name() eq 'DAMAGED' ? 'selected' : ''}>Damaged</option>
                                </select>
                            </div>


                            <div class="mb-3 col-lg-6" id="priceField">
                                <label class="form-label">Price ($)</label>
                                <input type="number"
                                       class="form-control"
                                       name="price"
                                       value="${listing.price}"
                                       step="0.01"
                                       min="0"
                                       ${listing.status ne 'AVAILABLE' ? 'disabled' : ''}>
                            </div>

                            <div class="mb-3 col-lg-12">
                                <label class="form-label">Update Image</label>
                                <input type="file"
                                       class="form-control"
                                       name="image"
                                       ${listing.status ne 'AVAILABLE' ? 'disabled' : ''}>
                            </div>

                            <c:if test="${listing.status eq 'AVAILABLE'}">
                                <div class="d-flex justify-content-end">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-save"></i> Save Changes
                                    </button>
                                </div>
                            </c:if>

                        </form>

                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script>

</script>

<%@include file="footer.jsp"%>
