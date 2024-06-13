<#import "../layout.ftlh" as main>
<@main.layout>

    <main class="container mt-4">
        <div class="row row-cols-md-2 row-gap-3">
            <div class="col-md-4 col-xl-3 bg-body sidebar">
                <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                    <div class="card-body">
                        <div class="row row-cols-1 row-gap-3 h-100">
                            <div class="col">
                                <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                    <div class="col">
                                        <a href="/abilities.html" class="btn btn-light rounded-4 w-100 h-100 ">
                                            График
                                        </a>
                                    </div>
                                    <div class="col">
                                        <a href="/expert/checks" class="btn btn-light rounded-4 w-100 h-100">
                                            Проверки
                                        </a>
                                    </div>
                                    <div class="col">
                                        <a href="/analytics" class="btn btn-light rounded-4 w-100 h-100 active">
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
                <div class="row row-cols-lg-2 row-cols-1">
                    <div class="col-lg-8">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">Пиццерия</th>
                                    <th scope="col">Менеджер</th>
                                    <#if user.role != "EXPERT">
                                        <th scope="col">Эксперт</th>
                                    </#if>
                                    <th scope="col">Дата</th>
                                    <th scope="col">Результат %</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <#list checklists as checklist>
                                <tr>
                                    <td>${checklist.pizzeria.name}</td>
                                    <td>${checklist.manager.name} ${checklist.manager.surname}</td>
                                    <#if user.role != "EXPERT">
                                        <td>${checklist.expert.name} ${checklist.expert.surname}</td>
                                    </#if>
                                    <td>${checklist.date}</td>
                                    <td>${checklist.result} %</td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                        <div class="d-flex flex-row gap-2 justify-content-center d-lg-none">
                            <div class="dropdown">
                                <button class="btn btn btn-light rounded-4 dropdown-toggle" type="button"
                                        data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="bi bi-funnel"></i> Фильтр
                                </button>
                                <ul class="dropdown-menu">
                                    <div class="py-3 px-3 text-center">
                                        <form action="/analytics" method="GET">
                                            <h6>Фильтры</h6>
                                            <div class="filter-width">
                                                <label>
                                                    <select class="form-select mb-2 pizzeria-select" name="pizzeria">
                                                        <option value="default">Пиццерия</option>
                                                    </select>
                                                </label>
                                                <label>
                                                    <select class="form-select mb-2 manager-select" name="manager">
                                                        <option value="default">Менеджер</option>
                                                    </select>
                                                </label>
                                                <#if user.role != "EXPERT">
                                                    <label>
                                                        <select class="form-select mb-2 expert-select" name="expert">
                                                            <option value="default">Эксперт</option>
                                                        </select>
                                                    </label>
                                                </#if>
                                            </div>
                                            <div class="input-group">
                                                <label for="date-from" class="input-group-text">От</label>
                                                <input type="date" name="startDate" id="date-from" class="form-control"
                                                       value="<#if currentStartDate??>${currentStartDate}</#if>" style="width: 5.06rem">
                                                <label for="date-to" class="input-group-text">До</label>
                                                <input type="date" name="endDate" id="date-to" class="form-control"
                                                       value="<#if currentEndDate??>${currentEndDate}</#if>">
                                            </div>
                                            <button class="btn btn-outline-success rounded-4" type="submit">Применить
                                            </button>
                                        </form>
                                    </div>
                                </ul>
                            </div>
                            <a href="/export/excel?pizzeria=${currentPizzeria}&manager=${currentManager}&expert=${currentExpert}&startDate=<#if currentStartDate??>${currentStartDate}</#if>&endDate=<#if currentEndDate??>${currentEndDate}</#if>"
                               class="btn btn-success rounded-4">Скачать <i class="bi bi-file-arrow-down"></i></a>
                        </div>
                    </div>
                    <div class="col-lg-4 d-none d-lg-inline text-center">
                        <form action="/analytics" method="GET">
                            <div class="card rounded-4 border-0 shadow-sm">
                                <div class="card-body">
                                    <h6 class="card-title">Фильтры</h6>
                                    <div class="filter-width">
                                        <label>
                                            <select class="form-select mb-2 pizzeria-select" name="pizzeria">
                                                <option value="default">Пиццерия</option>
                                            </select>
                                        </label>
                                        <label>
                                            <select class="form-select mb-2 manager-select" name="manager">
                                                <option value="default">Менеджер</option>
                                            </select>
                                        </label>
                                        <#if user.role != "EXPERT">
                                            <label>
                                                <select class="form-select mb-2 expert-select" name="expert">
                                                    <option value="default">Эксперт</option>
                                                </select>
                                            </label>
                                        </#if>
                                    </div>
                                    <div class="input-group">
                                        <label for="date-from" class="input-group-text">От</label>
                                        <input type="date" name="startDate" id="date-from" class="form-control"
                                               value="<#if currentStartDate??>${currentStartDate}</#if>">
                                        <label for="date-to" class="input-group-text">До</label>
                                        <input type="date" name="endDate" id="date-to" class="form-control"
                                               value="<#if currentEndDate??>${currentEndDate}</#if>">
                                    </div>
                                </div>
                                <div class="card-footer bg-body border-0 rounded-4 pb-3">
                                    <button class="btn btn-outline-success rounded-4" type="submit">Применить</button>
                                </div>
                            </div>
                        </form>
                        <a href="/export/excel?pizzeria=${currentPizzeria}&manager=${currentManager}&expert=${currentExpert}&startDate=<#if currentStartDate??>${currentStartDate}</#if>&endDate=<#if currentEndDate??>${currentEndDate}</#if>"
                           class="btn btn-outline-success mt-3 rounded-4">Скачать Excel</a>
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
                bottom: 0;
                left: 0;
                z-index: 2;
            }
        }

        .filter-width label {
            width: 100%;
        }
    </style>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            let pizzeriaSelects = document.querySelectorAll('.pizzeria-select');

            pizzeriaSelects.forEach(function (selectElement) {
                fetch('/api/pizzerias')
                    .then(function (response) {
                        return response.json();
                    })
                    .then(function (data) {
                        data.forEach(function (pizzeria) {
                            var option = document.createElement('option');
                            option.value = pizzeria.id;
                            option.textContent = pizzeria.name;
                            selectElement.appendChild(option);
                        });

                        selectElement.value = "${currentPizzeria}";
                    })
                    .catch(function (error) {
                        console.error('Ошибка при загрузке данных:', error);
                    });
            });


            let expertSelects = document.querySelectorAll('.expert-select');

            expertSelects.forEach(function (selectElement) {
                fetch('/api/experts')
                    .then(function (response) {
                        return response.json();
                    })
                    .then(function (data) {
                        data.forEach(function (expert) {
                            let option = document.createElement('option');
                            option.value = expert.id;
                            option.textContent = expert.name + " " + expert.surname;
                            selectElement.appendChild(option);
                        });

                        selectElement.value = "${currentExpert}";
                    })
                    .catch(function (error) {
                        console.error('Ошибка при загрузке данных:', error);
                    });
            });


            let managerSelects = document.querySelectorAll('.manager-select');

            managerSelects.forEach(function (selectElement) {
                fetch('/api/managers')
                    .then(function (response) {
                        return response.json();
                    })
                    .then(function (data) {
                        data.forEach(function (manager) {
                            let option = document.createElement('option');
                            option.value = manager.id;
                            option.textContent = manager.name + " " + manager.surname;
                            selectElement.appendChild(option);
                        });

                        selectElement.value = "${currentManager}";
                    })
                    .catch(function (error) {
                        console.error('Ошибка при загрузке данных:', error);
                    });
            });
        });
    </script>

</@main.layout>