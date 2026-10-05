<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<div class="bg-light flex-fill">
    <div class="p-4">

        <div class="row" style="height: 80vh">

            <div class="col-md-4 border-end bg-white overflow-auto">
                <h5 class="mb-3">Conversations</h5>

                <c:forEach var="conv" items="${conversations}">
                    <a href="conversations?conversationId=${conv.conversationId}"
                       class="list-group-item list-group-item-action
                       ${conv.conversationId == activeConversationId ? 'active' : ''}">

                        <strong> <c:choose>
                                <c:when test="${conv.proposer.userId eq sessionScope.userId}">
                                     ${conv.listing.user.name}
                                </c:when>
                                <c:otherwise>
                                    ${conv.proposer.name}
                                </c:otherwise>
                            </c:choose></strong>
                        <small class="text-muted">
                            <p>the owner of ${conv.listing.title} <strong>Book</strong></p><br>
                        
                        </small>

                    </a>
                </c:forEach>

                <c:if test="${empty conversations}">
                    <p class="text-muted mt-3">No conversations yet.</p>
                </c:if>
            </div>

            <div class="col-md-8 d-flex flex-column bg-light">

                <c:if test="${empty activeConversationId}">
                    <div class="d-flex h-100 justify-content-center align-items-center text-muted">
                        Select a conversation to start chatting
                    </div>
                </c:if>

                <c:if test="${not empty activeConversationId}">

                    <div class="flex-grow-1 overflow-auto p-3">

                        <c:forEach var="msg" items="${messages}">
                            <c:choose>

                                <c:when test="${msg.sender.userId eq sessionScope.userId}">
                                    <div class="d-flex justify-content-end mb-2">
                                        <div class="bg-primary text-white p-2 rounded" style="max-width: 70%">
                                                ${msg.content}
                                            <div class="text-end small opacity-75">
                                                    ${msg.sentAt.toString().replace('T', '  ')}
                                            </div>
                                        </div>
                                    </div>
                                </c:when>

                                <c:otherwise>
                                    <div class="d-flex justify-content-start mb-2">
                                        <div class="bg-white border p-2 rounded" style="max-width: 70%">
                                                ${msg.content}
                                            <div class="small text-muted">
                                                    ${msg.sender.name}  ${msg.sentAt.toString().replace('T', '  ')}
                                            </div>
                                        </div>
                                    </div>
                                </c:otherwise>

                            </c:choose>
                        </c:forEach>

                        <c:if test="${empty messages}">
                            <p class="text-muted">No messages yet.</p>
                        </c:if>
                    </div>

                    <div class="border-top bg-white p-3">
                        <form action="conversations" method="post" class="d-flex">
                            <input type="hidden" name="action" value="send">
                            <input type="hidden" name="conversationId" value="${activeConversationId}">

                            <c:forEach var="conv" items="${conversations}">
                                <c:if test="${conv.conversationId eq activeConversationId}">
                                    <input type="hidden" name="receiverId"
                                           value="${conv.proposer.userId eq sessionScope.userId
                                                    ? conv.listing.user.userId
                                                    : conv.proposer.userId}">
                                </c:if>
                            </c:forEach>

                            <input type="text" name="content"
                                   class="form-control me-2"
                                   placeholder="Type a message..."
                                   required>

                            <button class="btn btn-primary">
                                <i class="fa fa-paper-plane"></i>
                            </button>
                        </form>
                    </div>

                </c:if>
            </div>

        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
