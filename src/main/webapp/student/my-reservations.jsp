<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                <li class="breadcrumb-item">My Reservations</li>
            </ol>
        </nav>
        <hr>

        <div class="row mb-4">
            <div class="col">
                <h3 class="mb-0">My Reservations</h3>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="card shadow-sm p-3">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover align-middle">
                                <thead class="table-dark">
                                <tr>
                                    <th>#</th>
                                    <th>Book</th>
                                    <th>Seller</th>
                                    <th>Buyer</th>
                                    <th>Price</th>
                                    <th>Listing Status</th>
                                    <th>Reservation Status</th>
                                    <th>Reserved At</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="reservation" items="${reservations}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${reservation.listing.title}</td>
                                    <td>${reservation.listing.user.name}</td>
                                    <td>${reservation.buyer.name}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${reservation.listing.listingType eq 'SELL'}">
                                                $${reservation.listing.price}
                                            </c:when>
                                            <c:otherwise>--</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${reservation.listing.status}</td>
                                    <td>${reservation.reservationStatus}</td>
                                    <td>${reservation.reservedAt}</td>
                                    <td>
                                        <c:if test="${reservation.reservationStatus eq 'RESERVED' && reservation.listing.user.userId eq sessionScope.userId}">
                                            <form action="transactions" method="post" class="d-inline ms-1">
                                                <input type="hidden" name="action" value="mark-sold">
                                                <input type="hidden" name="listingId" value="${reservation.listing.listingId}">
                                                <input type="hidden" name="reservationId" value="${reservation.reservationId}">
                                                <button class="btn btn-sm btn-success" onclick="return confirm('Mark this listing as sold?');">
                                                    <i class="fas fa-check"></i> Sold
                                                </button>
                                            </form>
                                            <form action="transactions" method="post" class="d-inline">
                                                <input type="hidden" name="action" value="cancel-reservation">
                                                <input type="hidden" name="listingId" value="${reservation.listing.listingId}">
                                                <input type="hidden" name="reservationId" value="${reservation.reservationId}">
                                                <button class="btn btn-sm btn-danger" onclick="return confirm('Cancel reservation?');">
                                                    <i class="fas fa-times"></i> Cancel
                                                </button>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/js/all.min.js"></script>

<%@ include file="footer.jsp" %>
