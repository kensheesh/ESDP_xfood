<#import "../layout.ftlh" as main>
<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>


    <style>
        .user-info {
            flex-grow: 1;
        }

        .card-title {
            font-size: 1.25rem;
            font-weight: 600;
        }

        .card-text {
            margin-bottom: 5px;
        }

        .card-container {
            margin-bottom: 20px;
        }

        .btn-edit, .btn-delete {
            border: none;
            background: none;
            color: #007bff;
            cursor: pointer;
            font-size: 1.25rem;
            padding: 0 5px;
        }

        .btn-edit:hover, .btn-delete:hover {
            color: #0056b3;
        }

        .pagination .page-item.active .page-link {
            background-color: #007bff;
            border-color: #007bff;
        }

        .modal-header, .modal-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
    </style>

    <div class="container">
        <div class="row row-cols-md-3 row-gap-3">
            <div class="col-md-4 col-xl-3 bg-body sidebar">
                <div class="row row-cols-1 row-gap-3">
                    <div class="col">
                        <div class="card rounded-4 mb-2 mb-sm-0 shadow-sm border-0">
                            <div class="card-body">
                                <div class="row row-cols-1 row-gap-3 h-100">
                                    <div class="col">
                                        <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                            <div class="col">
                                                <a href="/deadlines" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                                    <i class="bi bi-hourglass-split fs-5 d-md-none"></i>
                                                    <span class="d-none d-md-inline">
                                                        Дедлайны
                                                    </span>
                                                </a>
                                            </div>
                                            <div class="col">
                                                <a class="btn rounded-4 w-100 h-100 btn-dodo-light"
                                                   data-bs-toggle="modal" data-bs-target="#tokenModal">
                                                    <i class="bi bi-key fs-5 d-md-none"></i>
                                                    <span class="d-none d-md-inline">
                                                        Токены
                                                    </span>
                                                </a>
                                            </div>
                                            <div class="col">
                                                <a href="/templates" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                                    <i class="bi bi-clipboard-plus fs-5 d-md-none"></i>
                                                    <span class="d-none d-md-inline">
                                                        Шаблоны
                                                    </span>
                                                </a>
                                            </div>
                                            <div class="col">
                                                <a href="#" class="btn disabled rounded-4 w-100 h-100 btn-dodo-light">
                                                    <span class="d-none d-md-inline">
                                                        Пользователи
                                                    </span>
                                                    <i class="bi bi-people d-md-none fs-5"></i>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col">
                        <div class="card d-none d-md-block border-0 shadow-sm rounded-4 ">
                            <div class="card-body">
                                <h6>Фильтры</h6>
                                <form action="" method="GET">
                                    <div class="mb-3">
                                        <label for="location-select" class="form-label">Тип пользователя</label>
                                        <select class="form-select" id="location-select-1" name="role">
                                            <option value="default">Все</option>
                                        </select>
                                    </div>
                                    <div class="input-group mb-3">
                                        <input type="text" name="search" value="<#if searchWord??>${searchWord}</#if>"
                                               class="form-control" placeholder="Поиск">
                                    </div>
                                    <button class="btn btn-green rounded-4 w-100" type="submit">Применить
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card-container col-xl-9 col-md-8" id="pizzeria-list">
                <#if pizzerias?size == 0>
                    <div class="alert alert-warning" role="alert">
                        Ничего не найдено.
                    </div>
                <#else>
                    <#list pizzerias as pizzeria>
                        <div class="card border-0 shadow-sm rounded-4 mb-2 user-card">
                            <div class="card-body d-flex">
                                <div class="user-info">
                                    <h5 class="card-title">Название Пицерии: ${pizzeria.getName()}</h5>
                                    <p class="card-text">UUID: ${pizzeria.getUuid()}</p>
                                    <p class="card-text">Регион: ${pizzeria.getLocation().getName()}</p>
                                </div>

                                <div class="card-icons">
                                    <button class="btn-edit"><i class="fas fa-edit"></i></button>
                                    <button class="btn-delete"><i class="fas fa-trash-alt"></i></button>
                                </div>
                            </div>
                        </div>
                    </#list>
                </#if>

                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                        <#if totalPages gt 0>
                            <#if currentPage gt 0>
                                <li class="page-item">
                                    <a class="page-link"
                                       href="/pizzerias?location_id=${currentLocation}&page=${currentPage-1}&size=${currentSize}&search=${searchWord}"
                                       aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                </li>
                            <#else>
                                <li class="page-item disabled">
                                    <a class="page-link" href="#" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                </li>
                            </#if>

                            <#list 0..(totalPages-1) as page>
                                <li class="page-item <#if page == currentPage>active</#if>">
                                    <a class="page-link"
                                       href="/pizzerias?location_id=${currentLocation}&page=${page}&size=${currentSize}&search=${searchWord}">${page + 1}</a>
                                </li>
                            </#list>

                            <#if currentPage lt totalPages - 1>
                                <li class="page-item">
                                    <a class="page-link"
                                       href="/pizzerias?location_id=${currentLocation}&page=${currentPage+1}&size=${currentSize}&search=${searchWord}"
                                       aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </li>
                            <#else>
                                <li class="page-item disabled">
                                    <a class="page-link" href="#" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </li>
                            </#if>
                        </#if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>

    <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editUserModalLabel">Редактировать пользователя</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editUserForm">
                        <div class="mb-3">
                            <label for="editName" class="form-label">Имя</label>
                            <input type="text" class="form-control" id="editName" name="name">
                        </div>
                        <div class="mb-3">
                            <label for="editSurname" class="form-label">Фамилия</label>
                            <input type="text" class="form-control" id="editSurname" name="surname">
                        </div>
                        <div class="mb-3">
                            <label for="editRole" class="form-label">Роль</label>
                            <select class="form-select" id="editRole" name="role">

                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="editEmail" class="form-label">Почта</label>
                            <input type="email" disabled class="form-control" id="editEmail" name="email">
                        </div>
                        <div class="mb-3">
                            <label for="editPhoneNumber" class="form-label">Телеграм</label>
                            <input type="text" disabled class="form-control" id="editPhoneNumber" name="tgLink">
                        </div>
                        <div class="mb-3" id="edit-user-enabled">
                            <label for="editEnabled" class="form-label">Активный</label>
                            <select class="form-select" id="editEnabled" name="enabled">
                                <option value="true">Да</option>
                                <option value="false">Нет</option>
                            </select>
                        </div>
                        <input type="hidden" id="editUserId">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                            <button type="submit" class="btn btn-primary" id="saveChanges">Сохранить изменения</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>



</@main.layout>