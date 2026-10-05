<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <div class="alert alert-danger" role="alert">
        <h4 class="alert-heading">Error!</h4>
        <p><strong>Something went wrong:</strong></p>
        <p>${param.message}</p> <!-- This will display the error message passed in the URL -->
        <hr>
        <p class="mb-0">
            <a href="index.jsp" class="btn btn-primary">Go Back</a> 
        </p>
    </div>
</div>

</body>
</html>
