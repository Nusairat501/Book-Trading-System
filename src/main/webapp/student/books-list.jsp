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
                <li class="breadcrumb-item">Books</li>
                <li class="breadcrumb-item active"> Dashboard Books</li>
            </ol>
        </nav>
        <hr>

        <div class="row mb-4">
            <div class="col d-flex justify-content-between align-items-center">
                <h3 class="mb-0">Available Books</h3>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col-md-6">
                <form action="listings" method="get" class="d-flex">
                    <input type="hidden" name="action" value="books-list">

                    <select class="form-select me-2" name="filter" required>
                        <option value="" disabled ${filter == null ? "selected" : ""}>Select Filter</option>
                        <option value="title" ${filter == 'title' ? 'selected' : ''}>Title</option>
                        <option value="course" ${filter == 'course' ? 'selected' : ''}>Course Code</option>
                        <option value="department" ${filter == 'department' ? 'selected' : ''}>Department</option>
                        <option value="condition" ${filter == 'condition' ? 'selected' : ''}>Book Condition</option>
                        <option value="type" ${filter == 'type' ? 'selected' : ''}>Listing Type</option>
                    </select>

                    <input type="text" class="form-control me-2" name="keyword"
                           placeholder="Enter keyword" value="${keyword != null ? keyword : ''}">

                    <button class="btn btn-primary">
                        <i class="fas fa-search"></i> Search
                    </button>
                </form>
            </div>

            <div class="col-md-6 text-end">
                <a href="listings?action=books-list" class="btn btn-secondary">
                    <i class="fas fa-undo"></i> Reset
                </a>
            </div>
        </div>

        <div class="row">
            <c:forEach var="listing" items="${listings}">
                <div class="col-md-4 mb-4">
                    <div class="card shadow-sm h-100">
                        <img src="${listing.imagePath}" class="card-img-top" 
                             style="height:220px;object-fit:contain;">

                        <div class="card-body">
                            <a href="listings?action=details&id=${listing.listingId}"
                               class="text-decoration-none text-dark">

                                <h5 class="card-title" style="text-align: center"> <strong>Title:</strong> ${listing.title}</h5>
                                <p class="card-text">
                                    <strong>Author:</strong> ${listing.author}
                                </p>
                                <p class="card-text">
                                    <strong>Edition:</strong> ${listing.edition}
                                </p>
                                <p class="card-text">
                                    <strong>Course code:</strong> ${listing.course.code}
                                </p>
                                <p class="card-text">
                                    <strong>Department name:</strong> ${listing.course.department.name}
                                </p>
                                <p class="card-text">
                                    <strong>Condition:</strong> ${listing.condition}
                                </p>
                                <p class="card-text">
                                    <strong>Type:</strong>
                                    <span class="badge bg-info">${listing.listingType}</span>
                                </p>
                                <p class="card-text">
                                    <strong>Status:</strong>
                                    <span class="badge bg-success">${listing.status}</span>
                                </p>
                                <c:if test="${listing.listingType == 'SELL'}">
                                    <p class="card-text">
                                        <strong>Price:</strong> $${listing.price}
                                    </p>
                                </c:if>
                            </a>

                            <div class="d-flex justify-content-between mt-3">
                                <c:if test="${listing.status == 'AVAILABLE' && listing.user.userId != sessionScope.userId}">

                                    <c:if test="${listing.listingType == 'SELL'}">
                                        <form action="transactions" method="post">
                                            <input type="hidden" name="action" value="reserve">
                                            <input type="hidden" name="listingId"
                                                   value="${listing.listingId}">
                                            <button class="btn btn-sm btn-warning">
                                                <i class="fas fa-bookmark"></i> Reserve
                                            </button>
                                        </form>
                                    </c:if>

                                    <c:if test="${listing.listingType == 'EXCHANGE'}">
                                        <a href="transactions?action=propose&listingId=${listing.listingId}"
                                           class="btn btn-sm btn-info">
                                            <i class="fas fa-exchange-alt"></i> Exchange
                                        </a>
                                    </c:if>

                                </c:if>
                            </div>

                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/js/all.min.js"></script>

<%@include file="footer.jsp" %>
