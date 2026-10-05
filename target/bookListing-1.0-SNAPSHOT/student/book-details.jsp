<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

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
        <nav style="--bs-breadcrumb-divider:'>'; font-size:14px">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><i class="fa-solid fa-house"></i></li>
                <li class="breadcrumb-item">Listings</li>
                <li class="breadcrumb-item active">Details</li>
            </ol>
        </nav>
        <hr>

        <!-- Listing Details -->
        <div class="row">
            <div class="col-lg-6">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">Listing Details</h4>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">${listing.title}</h5>

                        <p><strong>Author:</strong> ${listing.author}</p>
                        <p><strong>Edition:</strong> ${listing.edition}</p>
                        <p><strong>Category:</strong> ${listing.categoryName}</p>
                        <p><strong>Course:</strong> ${listing.courseCode}</p>
                        <p><strong>Condition:</strong> ${listing.condition}</p>

                        <p><strong>Type:</strong>
                            <span class="badge bg-info">${listing.listingType}</span>
                        </p>

                        <c:if test="${listing.listingType eq 'SALE'}">
                            <p><strong>Price:</strong> $${listing.price}</p>
                        </c:if>

                        <p><strong>Status:</strong>
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
                        </p>

                        <p class="text-muted">
                            Posted on ${listing.createdAt}
                        </p>
                    </div>
                </div>
            </div>

            <div class="col-lg-6 mt-4 mt-lg-0">
                <div class="card shadow-sm">
                    <div class="card-header bg-success text-white">
                        <h4 class="mb-0">Actions</h4>
                    </div>
                    <div class="card-body">

                        <c:if test="${listing.listingType eq 'SALE' && listing.status eq 'AVAILABLE'}">
                            <form action="transactions" method="post">
                                <input type="hidden" name="action" value="reserve">
                                <input type="hidden" name="listingId" value="${listing.listingId}">
                                <button class="btn btn-warning w-100 mb-2">
                                    <i class="fas fa-bookmark"></i> Reserve
                                </button>
                            </form>
                        </c:if>

                        <c:if test="${listing.listingType eq 'SALE' && listing.status eq 'RESERVED'}">
                            <form action="transactions" method="post">
                                <input type="hidden" name="action" value="buy">
                                <input type="hidden" name="listingId" value="${listing.listingId}">
                                <button class="btn btn-success w-100 mb-2">
                                    <i class="fas fa-shopping-cart"></i> Buy Now
                                </button>
                            </form>
                        </c:if>

                        <c:if test="${listing.listingType eq 'EXCHANGE' && listing.status eq 'AVAILABLE'}">
                            <a href="exchanges?action=create&listingId=${listing.listingId}"
                               class="btn btn-primary w-100">
                                <i class="fas fa-exchange-alt"></i> Propose Exchange
                            </a>
                        </c:if>

                        <c:if test="${listing.status ne 'AVAILABLE' && listing.status ne 'RESERVED'}">
                            <button class="btn btn-secondary w-100" disabled>
                                No actions available
                            </button>
                        </c:if>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
