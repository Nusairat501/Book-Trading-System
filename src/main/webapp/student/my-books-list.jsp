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
            </ol>
        </nav>
        <hr>

        <div class="row mb-4">
            <div class="col d-flex justify-content-between align-items-center">
                <h3 class="mb-0">My Books</h3>
                <a class="btn btn-primary" href="listings?action=create">
                    <i class="fas fa-plus me-2"></i>Add New Book
                </a>
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
                                        <th>Title</th>
                                        <th>Type</th>
                                        <th>Category</th>
                                        <th>Course</th>
                                        <th>Price</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="listing" items="${listings}">
                                        <tr>
                                            <td>${listing.listingId}</td>
                                            <td>${listing.title}</td>
                                            <td>
                                                <span class="badge bg-info">
                                                    ${listing.listingType}
                                                </span>
                                            </td>
                                            <td>${listing.category.name}</td>
                                            <td>${listing.course.code}</td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${listing.listingType eq 'SELL'}">
                                                        $${listing.price}
                                                    </c:when>
                                                    <c:otherwise>
                                                        Not Found
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td>
                                                <span class="badge
                                                      <c:choose>
                                                          <c:when test="${listing.status eq 'AVAILABLE'}">bg-success</c:when>
                                                          <c:when test="${listing.status eq 'RESERVED'}">bg-warning</c:when>
                                                          <c:when test="${listing.status eq 'SOLD'}">bg-secondary</c:when>
                                                          <c:when test="${listing.status eq 'EXCHANGED'}">bg-primary</c:when>
                                                          <c:otherwise>bg-danger</c:otherwise>
                                                      </c:choose>">
                                                    ${listing.status}
                                                </span>
                                            </td>

                                            <td>
                                                <c:if test="${listing.status eq 'AVAILABLE'}">
                                                    <form action="listings" method="get" class="d-inline">
                                                        <input type="hidden" name="action" value="edit">
                                                        <input type="hidden" name="listingId" value="${listing.listingId}">
                                                        <button class="btn btn-sm btn-primary">
                                                            <i class="fas fa-edit"></i> Edit
                                                        </button>
                                                    </form>
                                                </c:if>

                                                <c:if test="${listing.status eq 'AVAILABLE'}">
                                                    <form action="listings" method="post" class="d-inline">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="listingId" value="${listing.listingId}">
                                                        <button class="btn btn-sm btn-danger"
                                                                onclick="return confirm('Delete this listing?');">
                                                            <i class="fas fa-trash"></i> Delete
                                                        </button>
                                                    </form>
                                                </c:if>

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
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/js/all.min.js"></script>

<%@include file="footer.jsp" %>
