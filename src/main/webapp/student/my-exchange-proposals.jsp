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
        <nav style="--bs-breadcrumb-divider:'>';font-size:14px">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><i class="fa-solid fa-house"></i></li>
                <li class="breadcrumb-item">My Exchange Proposals</li>
            </ol>
        </nav>
        <hr>

        <div class="row mb-4">
            <div class="col">
                <h3 class="mb-0">My Exchange Proposals</h3>
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
                                        <th>Proposer</th>
                                        <th>Offered Book</th>
                                        <th>Offered Status</th>
                                        <th>Target Book</th>
                                        <th>Target Owner</th>
                                        <th>Target Status</th>
                                        <th>Proposal Status</th>
                                        <th>Proposed At</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="proposal" items="${exchangeProposals}" varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${proposal.requester.userId eq sessionScope.userId}">Me</c:when>
                                                    <c:otherwise>${proposal.requester.name}</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${proposal.offeredListing.title}</td>
                                            <td>${proposal.offeredListing.status}</td>
                                            <td>${proposal.targetListing.title}</td>
                                            <td>${proposal.targetListing.user.name}</td>
                                            <td>${proposal.targetListing.status}</td>
                                            <td>${proposal.status}</td>
                                            <td>${proposal.createdAt}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${proposal.status eq 'PENDING' && proposal.targetListing.user.userId eq sessionScope.userId}">
                                                        <form action="transactions" method="post" class="d-inline">
                                                            <input type="hidden" name="action" value="accept-exchange">
                                                            <input type="hidden" name="proposalId" value="${proposal.proposalId}">
                                                            <button class="btn btn-sm btn-success">Accept</button>
                                                        </form>
                                                        <form action="transactions" method="post" class="d-inline">
                                                            <input type="hidden" name="action" value="reject-exchange">
                                                            <input type="hidden" name="proposalId" value="${proposal.proposalId}">
                                                            <button class="btn btn-sm btn-danger">Reject</button>
                                                        </form>
                                                        <form action="conversations" method="post" class="d-inline">
                                                            <input type="hidden" name="action" value="start">
                                                            <input type="hidden" name="listingId" value="${proposal.targetListing.listingId}">
                                                            <input type="hidden" name="proposerId" value="${proposal.requester.userId}">
                                                            <button class="btn btn-sm btn-primary">
                                                                <i class="fa fa-comment"></i> Message
                                                            </button>
                                                        </form>

                                                    </c:when>
                                                </c:choose>
                                                <c:if test="${proposal.status eq 'ACCEPTED' and 
                                                              (
                                                              (proposal.targetListing.user.userId eq sessionScope.userId and proposal.targetListing.status eq 'RESERVED') or
                                                              (proposal.requester.userId eq sessionScope.userId and proposal.offeredListing.status eq 'RESERVED')
                                                              )
                                                      }">
                                                    <form action="transactions" method="post" class="d-inline">
                                                        <input type="hidden" name="action" value="complete-exchange">
                                                        <input type="hidden" name="proposalId" value="${proposal.proposalId}">
                                                        <button class="btn btn-sm btn-success">Complete Exchange</button>
                                                    </form>
                                                    <form action="transactions" method="post" class="d-inline">
                                                        <input type="hidden" name="action" value="cancel-exchange">
                                                        <input type="hidden" name="proposalId" value="${proposal.proposalId}">
                                                        <button class="btn btn-sm btn-danger">Cancel Exchange</button>
                                                    </form>
                                                </c:if>

                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty exchangeProposals}">
                                        <tr>
                                            <td colspan="10" class="text-center">No exchange proposals found.</td>
                                        </tr>
                                    </c:if>
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

<%@ include file="footer.jsp" %>
