<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<div class="container py-4">
    <div class="mb-4">
        <h3>Propose Exchange for: <span class="text-primary">"${targetListing.title}"</span></h3>
        <p><strong>Author:</strong> ${targetListing.author}</p>
        <p><strong>Edition:</strong> ${targetListing.edition}</p>
    </div>

    <form action="transactions" method="post">
        <input type="hidden" name="action" value="exchange-proposal">
        <input type="hidden" name="targetListingId" value="${targetListing.listingId}">

        <h5 class="mb-3">Your Available Exchange Listings:</h5>

        <c:if test="${not empty myExchangeListings}">
            <div class="row row-cols-1 row-cols-md-2 g-3">
                <c:forEach var="myListing" items="${myExchangeListings}">
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" 
                                           name="offeredListingId"
                                           value="${myListing.listingId}" 
                                           id="myListing${myListing.listingId}">
                                    <label class="form-check-label" for="myListing${myListing.listingId}">
                                        <h6 class="card-title mb-1">${myListing.title}</h6>
                                        <p class="mb-0"><strong>Condition:</strong> ${myListing.condition}</p>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <button  type="submit" class="btn btn-primary mt-3">Propose Exchange </button>
        </c:if>

        <c:if test="${empty myExchangeListings}">
            <p class="text-muted mt-2">You have no available listings for exchange. 
                <a href="listings?action=my-books" class="text-decoration-none">Add a book</a>
            </p>
        </c:if>
    </form>
</div>

<%@ include file="footer.jsp" %>
