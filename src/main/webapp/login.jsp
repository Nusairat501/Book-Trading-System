<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

        <style>
            body {
                background: linear-gradient(to right, #e0eafc, #cfdef3);
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 100vh;
                margin: 0;
            }

            .login-card {
                max-width: 420px;
                width: 100%;
                border-radius: 1rem;
                box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            }

            .login-header {
                background-color: #343a40;
                color: white;
                padding: 1rem 1.5rem;
                border-top-left-radius: 1rem;
                border-top-right-radius: 1rem;
            }

            .form-label i {
                margin-right: 6px;
                color: #495057;
            }

            .btn-dark {
                background-color: #343a40;
            }

            .login-footer {
                font-size: 0.9rem;
            }
        </style>
    </head>
    <body>

        <div class="login-card bg-white">
            <div class="login-header text-center">
                <h4>
                    <i class="fa-solid fa-right-to-bracket me-2"></i>Login
                </h4>
            </div>

            <div class="card-body p-4">
                <form action="/bookListing/auth" method="post">
                    <input type="hidden" name="action" value="login">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger text-center">
                            ${error}
                        </div>
                    </c:if>

                    <div class="mb-3">
                        <label for="email" class="form-label">
                            <i class="fa-solid fa-envelope"></i>Email
                        </label>
                        <input type="email" name="email" id="email"
                               class="form-control"
                               placeholder="Enter your email"
                               required>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">
                            <i class="fa-solid fa-lock"></i>Password
                        </label>
                        <input type="password" name="password" id="password"
                               class="form-control"
                               placeholder="Enter your password"
                               required>
                    </div>

                    <button type="submit" class="btn btn-dark w-100 mt-3">
                        <i class="fa-solid fa-arrow-right-to-bracket me-2"></i>Login
                    </button>
                </form>

                <div class="text-center mt-4 login-footer">
                    Don't have an account?
                    <a href="/bookListing/student/register.jsp"
                       class="text-decoration-none ms-1 text-primary">
                        Register here
                    </a>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
