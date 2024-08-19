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
                                                <a href="/users" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                                    <span class="d-none d-md-inline">
                                                        Пользователи
                                                    </span>
                                                    <i class="bi bi-people d-md-none fs-5"></i>
                                                </a>
                                            </div>
                                            <div class="col">
                                                <a href="#" class="btn disabled rounded-4 w-100 h-100 btn-dodo-light">
                                                    <span class="d-none d-md-inline">
                                                        Пицерии
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
                                        <select class="form-select" id="editLocation" name="location_id">
                                            <option value="-1">Все</option>
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
                <div class="d-flex flex-row gap-2 mb-3 flex-wrap">
                    <button class="btn btn-dodo rounded-4" data-bs-toggle="modal" data-bs-target="#selectLocationModal">
                        <i class="bi bi-file-earmark-plus-fill"></i> Создать <span
                                class="d-none d-sm-inline">пиццерию</span>
                    </button>
                </div>

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
                                    <button class="btn-edit" data-pizzeria-id="${pizzeria.getId()}"><i
                                                class="fas fa-edit"></i></button>

                                    <button class="btn-delete" data-pizzeria-id="${pizzeria.getId()}"><i
                                                class="fas fa-trash-alt"></i>
                                    </button>
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

    <div class="modal fade" id="editPizzeria" tabindex="-1" aria-labelledby="editPizzeriaLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editPizzeriaLabel">Редактировать пиццерии</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editLocationForm">
                        <input type="text" hidden="hidden" id="pizzId" >
                        <div class="mb-3">
                            <label for="editName" class="form-label">Названике</label>
                            <input type="text" class="form-control" id="editName" name="name">
                        </div>
                        <div class="mb-3">
                            <label for="editUUID" class="form-label">UUID</label>
                            <input type="text" class="form-control" id="editUUID" name="uuid">
                        </div>
                        <div class="mb-3">
                            <label for="editLocation-2" class="form-label">Регион</label>
                            <select class="form-select" id="editLocation-2" name="location_id">

                            </select>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                            <button type="submit" class="btn btn-primary" id="saveChanges">Сохранить изменения</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for Location Selection -->
    <div class="modal fade" id="selectLocationModal" tabindex="-1" aria-labelledby="selectLocationLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="selectLocationLabel">Выберите локацию</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="locations"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="continue-to-pizzerias">Продолжить</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for Pizzeria Selection -->
    <div class="modal fade modal-xl" id="selectPizzeriaModal" tabindex="-1" aria-labelledby="selectPizzeriaLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="selectPizzeriaLabel">Выберите пиццерию</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row mb-4">
                            <div class="col-md-9">
                                <input class="form-control" type="search" placeholder="Введите ключивые слова"
                                       aria-label="searchPizzeria" id="pizzeria-search-input">
                            </div>
                            <div class="col-md-3">
                                <button class="btn btn-outline-success" id="find-pizzeria" type="button">Найти</button>
                            </div>
                        </div>
                        <div id="search-results"></div>
                        <nav aria-label="Page navigation">
                            <ul id="pagination" class="pagination"></ul>
                        </nav>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline-success w-100" id="load-pizzerias" type="button">Перезагрузить
                        пиццерии из "ДодоИС"
                    </button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>

    <script src="/js/pizzeriaManagement.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', async () => {
            const locationSelect = document.getElementById('editLocation');
            const editPizzeria = new bootstrap.Modal(document.getElementById('editPizzeria'));

            async function getLocations(select, currentLocation) {
                const response = await fetch('/api/admin/locations');
                const data = await response.json();
                data.forEach(function (l) {
                    let option = document.createElement('option');
                    option.value = l.id;
                    option.textContent = l.name;
                    if (currentLocation === l.id) {
                        option.selected = true;
                    }
                    select.appendChild(option);
                });
            }

            await getLocations(locationSelect, '${currentLocation}');

            document.addEventListener('click', async (event) => {
                if (event.target.closest('.btn-delete')) {
                    const btnDelete = event.target.closest('.btn-delete');
                    const pId = btnDelete.getAttribute('data-pizzeria-id');
                    // await deletePizzeria(userId);
                }

                if (event.target.closest('.btn-edit')) {
                    editPizzeria.show()
                    const btnEdit = event.target.closest('.btn-edit');
                    const pId = btnEdit.getAttribute('data-pizzeria-id');
                    await edit(pId);
                }
            });

            async function edit(pId) {
                const response = await fetch('/api/admin/pizzerias/' + pId);
                const p = await response.json();

                document.getElementById('pizzId').value = p.id;
                document.getElementById('editName').value = p.name;
                document.getElementById('editUUID').value = p.uuid;
                await getLocations(document.getElementById('editLocation-2'), p.location.id);
                editPizzeria.show();
            }

            document.getElementById('editLocationForm').addEventListener('submit', async (event) => {
                event.preventDefault();
                const pId = document.getElementById('pizzId').value;
                const PizzeriaDto = {
                    id: pId,
                    name: document.getElementById('editName').value,
                    uuid: document.getElementById('editUUID').value,
                    locationId: document.getElementById('editLocation-2').value,
                };

                await fetch('/api/admin/pizzerias/edit', {
                    method: 'put',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(PizzeriaDto)
                });
                editPizzeria.hide();
                location.reload();
            });

        });


    </script>

</@main.layout>