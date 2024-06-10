<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Expert checks</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
<header>
    <nav class="navbar navbar-expand fw-medium bg-white border-bottom ">
        <div class="container">
            <a class="navbar-brand" href="#">XFOOD</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown" href="#">
                        <a a class="nav-link dropdown-toggle d-flex flex-row flex-nowrap column-gap-2 align-items-center py-1"
                           href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-person-circle fs-5 m-0 p-0 "></i>
                            <span>Иван Иванов</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="#">Мой профиль</a></li>
                            <li><a class="dropdown-item" href="#">Карта возможностей</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">Выход</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<main class="container mt-4">
    <div class="row row-cols-md-2 row-cols-1 bg-white rounded shadow-sm">
        <div class="col">
            <div class="my-4">
                <select class="form-select" id="floatingSelect" aria-label="Floating label select example">
                    <option disabled selected>Выберите тип чек листа</option>
                    <#list types as type>
                        <option value="${type.id}">${type.name}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="col d-flex">
            <button type="button" class="btn btn-success my-4 ms-auto w-50 h-50 rounded-4">Создать</button>
        </div>
    </div>
    <div class="mt-4 shadow-sm p-3 bg-white overflow-y-auto" style="height: 58vh;">
        <div class="row g-3 mb-3">
            <div class="col-12">
                <#list criterias as c>
                    <div id="criteria-wrap-${c?index}" class=" <#if c?index%2==0> bg-warning <#else> bg-primary-subtle</#if> bg-opacity-25 p-3 rounded-4 shadow-sm d-flex align-items-center mb-2">
                        <div class="row w-100 row-cols-1 row-cols-sm-2">
                            <div class="col-sm-9 col-md-10 d-flex align-items-center">
                                <p class="mb-0">${c.description}</p>
                            </div>
                            <div class="col-sm-3 col-md-2 d-flex align-items-center  justify-content-md-end">
                                <div class="row row-cols-2">
                                    <div class="col">
                                        <div class="shadow-sm bg-white rounded-4 text-center p-3 me-2">
                                            <#if c.maxValue??>${c.maxValue}<#else >${c.coefficient}</#if>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <button class="btn btn-link bg-white shadow-sm rounded-4 p-2" type="button" id="deleteCriteria-${c?index}">
                                            <i class="bi bi-trash text-secondary fs-4"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</main>

<script>
    let criteriaWraps = document.querySelectorAll('[id^="criteria-wrap-"]');
    let criterias = document.querySelectorAll('[id^="deleteCriteria"]');
    console.log(criterias);
    console.log(criteriaWraps)
    for (let i = 0 ; i<criterias.length; i++){
        criterias[i].addEventListener('click', ()=>{
            criteriaWraps[i].remove();
        })
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">
</script>
</body>