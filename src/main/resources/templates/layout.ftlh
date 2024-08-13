<#import "spring.ftl" as spring>

<#macro layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    <#else>
    </#if>

    <!doctype html>
    <html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Expert checks</title>

        <link href="/DataTables/datatables.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <!-- Fontawesome CDN Link -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="/css/layout.css">
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
                                    <#if authorizedUser.authorities[0].authority??>
                                        <#if authorizedUser.authorities[0].authority == "ROLE_ADMIN" ||
                                        authorizedUser.authorities[0].authority == "ROLE_SUPERVISOR">
                                            <li>
                                                <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                                        data-bs-target="#addUser">
                                                    Создать пользователя
                                                </button>
                                            </li>
                                            <li>
                                                <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                                        data-bs-target="#tokenModal">
                                                    Привязать токен
                                                </button>
                                            </li>
                                        <#else>
                                        </#if>
                                    </#if>
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

    <main class="container my-4">
        <#nested spring>
    </main>

    <div class="modal fade" id="tokenModal" tabindex="-1" aria-labelledby="tokenModal"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="tokenModal">
                        Привязать токен доступа
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>
                <form id="bearerForm">
                    <div class="modal-body">
                        <label for="bearer" class="form-label">Токен</label>
                        <input type="text" id="bearer" name="bearer" class="form-control" required>
                    </div>

                    <div class="modal-footer">
                        <button type="submit" id="pushToken" class="btn btn-primary" data-bs-dismiss="modal"
                                data-bs-target="#tokenModal">
                            Сохранить токен
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </div>

    <div class="modal fade modal-lg" id="addUser" tabindex="-1" aria-labelledby="addUserLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <h5 class="modal-title" id="addUserLabel">Введите данные нового сотдрудника</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="container mt-4">
                    <form id="addUserForm" class="needs-validation" novalidate>
                        <div class="mb-4">
                            <label for="name" class="form-label">Имя</label>
                            <input type="text" id="name" name="name" class="form-control" required>
                        </div>

                        <div class="mb-4">
                            <label for="surname" class="form-label">Фамилия</label>
                            <input type="text" id="surname" name="surname" class="form-control" required>
                        </div>

                        <div class="mb-4">
                            <label class="form-label" for="tgLink">Ccылка на телеграм</label>
                            <input type="text" id="tgLink" name="tgLink" class="form-control" required
                                   pattern="^t\.me/.*"
                                   placeholder="Пожалуйста, введите ссылку на телеграм, начиная с t.me/."/>
                        </div>

                        <div class="mb-4">
                            <label for="email" class="form-label">Адрес эл. почты</label>
                            <input type="email" id="email" name="email" class="form-control" required>
                            <div id="emailError" class="invalid-feedback"></div>
                        </div>

                        <div class="mb-4">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password" id="password" name="password" class="form-control" required
                                   pattern="^(?=.*\d)(?=.*[a-zA-Z]).{8,}$"
                                   placeholder="Пароль должен быть не менее 8 символов, содержать цифры и буквы."/>
                        </div>

                        <#if authorizedUser??>
                            <#if authorizedUser.authorities[0].authority??>
                                <#if authorizedUser.authorities[0].authority == "ROLE_ADMIN" >
                                    <div class="mb-4">
                                        <div class="d-flex ">
                                            <p class="me-3">Выберите роль:</p>
                                            <div class="form-check me-3">
                                                <input type="radio" id="expert" name="role" value="expert"
                                                       class="form-check-input"
                                                       checked required>
                                                <label class="form-check-label" for="expert">Эксперт</label>
                                            </div>
                                            <div class="form-check">
                                                <input type="radio" id="supervisor" name="role" value="supervisor"
                                                       class="form-check-input" required>
                                                <label class="form-check-label" for="supervisor">Руководитель</label>
                                            </div>
                                        </div>
                                    </div>
                                <#else>
                                    <div class="mb-4">
                                        <div class="d-flex ">
                                            <p class="me-3">Выберите роль:</p>
                                            <div class="form-check me-3">
                                                <input type="radio" id="expert" name="role" value="expert"
                                                       class="form-check-input"
                                                       checked required>
                                                <label class="form-check-label" for="expert">Эксперт</label>
                                            </div>
                                        </div>
                                    </div>
                                </#if>
                            <#else>
                            </#if>
                        <#else>
                        </#if>

                        <div class="d-flex justify-content-end">
                            <button type="submit" class="btn btn-primary mb-2">Создать</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="/js/validation.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/i18n/ru.min.js"></script>
    <script>
        document.getElementById('pushToken').addEventListener('click', async function() {
            const form = document.getElementById('bearerForm');
            const formData = new FormData(form);

            try {
                const response = await fetch('/api/bearer', {
                    method: 'POST',
                    body: formData
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                // Optionally, handle the response here
                const result = await response.json();
                console.log(result);

                // Close the modal if needed
                const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
                modal.hide();
            } catch (error) {
                console.error('There was a problem with the fetch operation:', error);
            }
        });
    </script>
    </script>
    </body>
    </html>
</#macro>