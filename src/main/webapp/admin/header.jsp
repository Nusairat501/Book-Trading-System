<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width,
              initial-scale=1.0">
        <title>User</title>
        <link href=
              "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" 
              rel="stylesheet"
              integrity=
              "sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" 
              crossorigin="anonymous">
        <style>

            html, body {

                font-family: 'Ubuntu', sans-serif;
            }

            .gfg {
                height: 50px;
                width: 50px;

            }

            .mynav {
                color: #fff;
            }

            .mynav li a {
                color: #fff;
                text-decoration: none;
                width: 100%;
                display: block;
                border-radius: 5px;
                padding: 8px 5px;
            }

            .mynav li a.active {
                background: rgba(255, 255, 255, 0.2);
            }

            .mynav li a:hover {
                background: rgba(255, 255, 255, 0.2);
            }

            .mynav li a i {
                width: 25px;
                text-align: center;
            }

            .notification-badge {
                background-color: rgba(255, 255, 255, 0.7);
                float: right;
                color: #222;
                font-size: 14px;
                padding: 0px 8px;
                border-radius: 2px;
            }
        </style>
        <link rel="stylesheet" href=
              "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
              integrity=
              "sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
              crossorigin="anonymous" 
              referrerpolicy="no-referrer" />
    </head>
    <body>
        <div class="container-fluid p-0 d-flex h-100">
            <div id="bdSidebar" 
                 class="d-flex flex-column flex-shrink-0 p-3 bg-dark text-white offcanvas-md offcanvas-start" 
                 style="height: 100vh; position: sticky; top: 0;">
                <a  href="users?action=users-list" 
                    class="navbar-brand">
                    Admin Dash
                </a><hr>
                <ul class="mynav nav nav-pills flex-column mb-auto">

                    <li class="nav-item mb-1">
                        <a href="users?action=users-list"
                           class="<%= request.getParameter("action") != null && request.getParameter("action").equals("users-list") ? "active" : ""%>">
                            <i class="fa-regular fa-user"></i>
                            Users
                        </a>
                    </li>
                    <li class="nav-item mb-1">
                        <a href="categories?action=categories-list"
                           class="<%= request.getParameter("action") != null && request.getParameter("action").equals("categories-list") ? "active" : ""%>">
                            <i class="fa-regular fa-newspaper"></i>
                            Categories
                        </a>
                    </li>
                    <li class="nav-item mb-1">
                        <a href="courses?action=courses-list"
                           class="<%= request.getParameter("action") != null && request.getParameter("action").equals("courses-list") ? "active" : ""%>">
                            <i class="fa-solid fa-newspaper"></i>
                            Courses
                        </a>
                    </li>
                    <li class="nav-item mb-1">
                        <a href="listings?action=admin-books-list"
                           class="<%= request.getParameter("action") != null && request.getParameter("action").equals("admin-books-list") ? "active" : ""%>">
                            <i class="fa-regular fa-newspaper"></i>
                            Manage Book Listings
                        </a>
                    </li>
                    <li class="sidebar-item  nav-item mb-1">
                        <a href="#" 
                           class="sidebar-link collapsed" 
                           data-bs-toggle="collapse"
                           data-bs-target="#settings"
                           aria-expanded="false"
                           aria-controls="settings">
                            <i class="fas fa-cog pe-2"></i>
                            <span class="topic">Settings </span>
                        </a>
                        <ul id="settings" 
                            class="sidebar-dropdown list-unstyled collapse" 
                            data-bs-parent="#sidebar">


                            <li class="sidebar-item">
                                <a href="auth?action=logout" class="sidebar-link">
                                    <i class="fas fa-sign-out-alt pe-2"></i>
                                    <span class="topic">Log Out</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <hr>
                <div class="d-flex">

                    <i class="fa-solid fa-book  me-2"></i>
                    <span>
                        <h6 class="mt-1 mb-0">
                            <c:out value="${sessionScope.username}" />
                        </h6>
                    </span>
                </div>
            </div>
