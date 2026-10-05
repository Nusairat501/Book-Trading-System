<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Register Page</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

  <style>
    body {
      background: linear-gradient(to right, #f8f9fa, #e0eaff);
      height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      margin: 0;
    }

    .register-card {
      max-width: 450px;
      width: 100%;
      border-radius: 1rem;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    }

    .card-header {
      background-color: #343a40;
      color: #fff;
      text-align: center;
      padding: 1.2rem 1.5rem;
      border-top-left-radius: 1rem;
      border-top-right-radius: 1rem;
    }

    .form-label i {
      margin-right: 6px;
    }

    .btn-dark {
      background-color: #343a40;
    }

    a {
      color: #0d6efd;
      text-decoration: none;
    }

    a:hover {
      text-decoration: underline;
    }
  </style>
</head>

<body>
  <div class="card register-card">
    <div class="card-header">
      <h4><i class="fa-solid fa-user-plus me-2"></i>Register</h4>
    </div>
    <div class="card-body p-4">
      <form action="/bookListing/auth" method="post">
        <input type="hidden" name="action" value="register">
        <c:if test="${param.error == 'exists'}">
          <div class="alert alert-danger text-center">Email already exists</div>
        </c:if>

        <div class="mb-3">
          <label for="name" class="form-label"><i class="fa-solid fa-user"></i>Name</label>
          <input type="text" name="name" id="name" class="form-control" placeholder="Enter your name" required>
        </div>

        <div class="mb-3">
          <label for="email" class="form-label"><i class="fa-solid fa-envelope"></i>Email</label>
          <input type="email" name="email" id="email" class="form-control" placeholder="Enter your email" required>
        </div>

        <div class="mb-3">
          <label for="password" class="form-label"><i class="fa-solid fa-lock"></i>Password</label>
          <input type="password" name="password" id="password" class="form-control" placeholder="Create a password" required>
        </div>

        <div class="mb-3">
          <label for="academicYear" class="form-label"><i class="fa-solid fa-calendar"></i>Academic Year</label>
          <input type="number" name="academicYear" id="academicYear" class="form-control" placeholder="Enter your academic year" min="1" max="8">
        </div>

        <button type="submit" class="btn btn-dark w-100 mt-3">
          <i class="fa-solid fa-user-check me-2"></i>Register
        </button>
      </form>

      <div class="text-center mt-3">
        Already have an account? <br>
        <a href="/bookListing/login.jsp">Login here</a>
      </div>
    </div>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
