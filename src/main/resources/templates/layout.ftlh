<#import "spring.ftl" as spring>
<#macro layout>

    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Job search</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
                integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
                crossorigin="anonymous"></script>
    </head>
    <body>

    <nav class="navbar navbar-expand-lg bg-body-tertiary mb-3" data-bs-theme="dark">
        <div class="container">
            <a class="navbar-brand" href="/">JobSearch</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <#--                <ul class="navbar-nav d-flex flex-fill">-->
                <#--                    <li class="nav-item">-->
                <#--                        <a class="nav-link active" aria-current="page" href="/messages/resumes/1/vacancy/2">Messages(Test)</a>-->
                <#--                    </li>-->
                <#--                </ul>-->
            </div>
            <#assign
            known = SPRING_SECURITY_CONTEXT??
            >
            <#if known>
                <#assign
                user = SPRING_SECURITY_CONTEXT.authentication.principal
                name = user.getUsername()
                >

                <#list SPRING_SECURITY_CONTEXT.authentication.getAuthorities() as authority>
                    <#if authority == 'EMPLOYER'>
                        <#assign auth = true>
                    </#if>
                </#list>

                <#if auth?? && auth>
                    <a href="/employer/resumes"
                       class="d-flex text-light text-decoration-none fs-5 m-1 m-sm-0">Резюме</a>
                <#else>
                    <a href="/employee/companies"
                       class="d-flex text-light text-decoration-none fs-5 m-1 m-sm-0">Компании</a>
                </#if>

                <a href="/users/profile"
                   class="d-flex text-secondary text-decoration-none fs-5 ps-3 m-1 m-sm-0">${name}</a>


                <a href="/logout" class="btn btn-outline-info ms-3 m-1"
                   id="logout">Logout</a>

            <#else>
                <a href="/auth/login"
                   class="btn btn-outline-info m-1">Login</a>
                <a href="/auth/register"
                   class="btn btn-outline-info m-1">Register</a>
            </#if>
        </div>
    </nav>

    <div class="container">
        <#nested >
    </div>
    </body>

</html>

</#macro>


