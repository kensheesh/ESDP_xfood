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
        <div class="row row-cols-md-2 row-gap-3">
            <div class="col-md-4 col-xl-3 bg-body sidebar">
                <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                    <div class="card-body">
                        <div class="row row-cols-1 row-gap-3 h-100">
                            <div class="col">
                                <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                    <div class="col">
                                        <a href="/abilities" class="btn btn-light rounded-4 w-100 h-100 ">
                                            График
                                        </a>
                                    </div>
                                    <div class="col">
                                        <a href="/expert/checks" class="btn btn-light rounded-4 w-100 h-100 active">
                                            Проверки
                                        </a>
                                    </div>
                                    <div class="col">
                                        <a href="/analytics" class="btn btn-light rounded-4 w-100 h-100 ">
                                            Аналитика
                                        </a>
                                    </div>
                                    <div class="col">
                                        <div class="dropdown d-md-none h-100">
                                            <button class="btn btn-light rounded-4 dropdown-toggle w-100 h-100"
                                                type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                <i class="bi bi-clock"></i>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li class="dropdown-item">МСК 14:45</li>
                                                <li class="dropdown-item">ЕКБ 16:45</li>
                                                <li class="dropdown-item">БИШ 17:45</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col align-self-end d-none d-md-block">
                                <div class="card rounded-4 border-0 shadow-sm">
                                    <div class="card-body">
                                        <div class="d-flex flex-column row-gap-2 text-center">
                                            <span>МСК 14:45</span>
                                            <span>ЕКБ 16:45</span>
                                            <span>БИШ 17:45</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-8 col-xl-9">


                <div class="card rounded-4 border-0 shadow-sm">
                    <div class="card-body">
                        <div class="row row-cols-1 row-gap-3">
                            <div class="col">
                                <button id="newChecks"        type="button" class="btn btn-success rounded-4">Новые</button>
                                <button id="inProgressChecks" type="button" class="btn btn-success rounded-4">Активные</button>
                                <button id="doneChecks"       type="button" class="btn btn-success rounded-4">Завершенные</button>
                            </div>

                            <div class="col">
                                <div class="row row-cols-1 row-gap-2 mt-2 overflow-y-auto" style="height: 58vh;">
                                    <div id="checks-container"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <style>
        body {
            min-height: 100vh;
        }
        
        @media screen and (max-width: 575px) {
            .sidebar {
                position: fixed;
                bottom: 0px;
                left: 0;
                z-index: 2;
            }
        }

        .active {
            background-color: #2857a7; /* Change this to the color you want */
        }
    </style>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">
    </script>
    <script src="../js/checks.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            document.querySelectorAll('.btn').forEach(button => {
                button.addEventListener('click', function() {
                    document.querySelectorAll('.btn').forEach(btn => {
                        btn.style.backgroundColor = (btn === this) ? '#2848a7' : '';
                        btn.style.color = (btn === this) ? '#fff' : '';
                    });
                });
            });
        });
    </script>


</body>

</html>