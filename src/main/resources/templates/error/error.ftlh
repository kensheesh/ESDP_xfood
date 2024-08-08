<#assign known = SPRING_SECURITY_CONTEXT??>
<#if known>
<#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
</#if>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
    <link rel="stylesheet" href="/css/layout.css">
    <link rel="stylesheet" href="/css/error.css">

    <title>Document</title>
</head>
<body>
<header>
    <nav class="navbar navbar-expand fw-medium bg-white border-bottom " id="navbar">
        <div class="container">
            <a class="navbar-brand fw-semibold " href="#">XFOOD</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ms-auto">
                    <#if authorizedUser??>
                        <li class="nav-item dropdown" href="#">
                            <a
                                    class="nav-link dropdown-toggle d-flex flex-row flex-nowrap column-gap-2 align-items-center py-1"
                                    href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="bi bi-person-circle fs-5 m-0 p-0 "></i>
                                <span>
                                    ${authorizedUser.name}
                                </span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li><a class="dropdown-item" href="/profile">Мой профиль</a></li>
                                <li><a class="dropdown-item" href="#">Карта возможностей</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="/logout">Выход</a></li>
                            </ul>
                        </li>
                    </#if>
                </ul>
            </div>
        </div>
    </nav>
</header>



<div class="fullscreen-gif">
    <img src="/files/error.gif" alt="Fullscreen GIF">
    <div class="overlay-text">
        <h1>${status}</h1>
        <h2><span data-l10n>UH OH</span>! <span data-l10n>${reason}</span></h2>
        <#if details??>
            <ul class="details">
                <#if details.serverName??>
                    <li><span><span data-l10n>Host</span>:</span> <code>${details.serverName}</code></li>
                </#if>
                <#if details.requestURL??>
                    <li><span><span data-l10n>Original URI</span>:</span> <code>${details.requestURL}</code>
                    </li>
                </#if>
            </ul>
        </#if>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
        integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="/DataTables/datatables.min.js"></script>
</body>
</html>
