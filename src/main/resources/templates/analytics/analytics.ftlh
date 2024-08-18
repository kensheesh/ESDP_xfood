<#import "../layout.ftlh" as main>
<@main.layout; spring>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <link rel="stylesheet" href="/css/analytics.css">

    <div class="row row-cols-md-2 row-gap-3 mt-4">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 shadow-sm border-0">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3">
                        <#assign userRole = authorizedUser.authorities[0].authority>
                        <div class="col">
                            <div class="row <#if userRole == "ROLE_EXPERT">rol-cols-4<#else>row-cols-3</#if> row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <#if userRole == "ROLE_EXPERT">
                                        <a href="/my-opportunities" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-calendar2-week fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline d-lg-none">Возможности</span>
                                            <span class="d-none d-lg-inline">Карта возможностей</span>
                                        </a>
                                    <#else>
                                        <a href="/weekly" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-calendar2-range fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline">График</span>
                                        </a>
                                    </#if>
                                </div>
                                <#if userRole == "ROLE_SUPERVISOR" || userRole == "ROLE_ADMIN">
                                    <div class="col">
                                        <a href="/opportunities"
                                           class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-calendar2-week fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline">Возможности</span>
                                        </a>
                                    </div>
                                </#if>
                                <div class="col button-wrapper">
                                    <a href="/checks" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <span class="d-none d-md-inline">Проверки</span>
                                        <i class="bi bi-clipboard-check fs-5 d-md-none"></i>
                                    </a>
                                    <#if (checksCount?has_content && checksCount > 0)>
                                        <div class="badge-notification-icon">
                                            <div class="badge-notification-num">${checksCount}</div>
                                        </div>
                                    <#elseif (checksCount?has_content && checksCount > 99)>
                                        <div class="badge-notification-icon">
                                            <div class="badge-notification-num">99</div>
                                        </div>
                                    </#if>
                                </div>
                                <div class="col">
                                    <a href="#" class="btn rounded-4 w-100 h-100 disabled btn-dodo-light">
                                        <i class="bi bi-bar-chart-fill fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">Аналитика</span>
                                    </a>
                                </div>
                                <#if userRole == "ROLE_SUPERVISOR" || userRole == "ROLE_ADMIN">
                                    <div class="col button-wrapper">
                                        <a href="/appeals" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-flag fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline">Апелляции</span>
                                            <span class="badge-notification-icon">
                                                <span class="badge-notification-num" id="counter">

                                                </span>
                                            </span>
                                        </a>
                                    </div>
                                </#if>
                                <div class="col">
                                    <div class="dropdown d-md-none h-100">
                                        <a class="btn btn-dodo-light rounded-4 dropdown-toggle w-100 h-100"
                                           type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            <i class="bi bi-clock fs-5"></i>
                                        </a>
                                        <div class="dropdown-menu border-0 rounded-4 shadow">
                                            <section class="times_second d-flex flex-column row-gap-2 p-3"></section>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col align-self-end d-none d-md-block">
                            <div class="card rounded-4 border-0 shadow-sm">
                                <div class="card-body">
                                    <section class="d-flex flex-column row-gap-2 text-center times">
                                    </section>
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
                    <div class="table-responsive" style="max-height: 58vh; overflow-y: auto;">
                        <table class="table">
                            <thead style="position: sticky; top: 0; background-color: white; z-index: 1;">
                            <tr>
                                <th scope="col">Пиццерия</th>
                                <th scope="col">Менеджер</th>
                                <#if user.role != "EXPERT">
                                    <th scope="col">Эксперт</th>
                                </#if>
                                <th scope="col">Дата</th>
                                <th scope="col">Результат</th>
                            </tr>
                            </thead>
                            <tbody id="checks-container">
                            <#list checklists as checklist>
                                <tr class="clickable-row" data-href="/checks/${checklist.uuid}" style="cursor: pointer;">
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
                    <div class="d-flex flex-row gap-2 mt-3 justify-content-center d-lg-none">
                        <div class="dropdown">
                            <button class="btn btn btn-light rounded-4 dropdown-toggle" type="button"
                                    data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="bi bi-funnel"></i> Фильтр
                            </button>
                            <div class="dropdown-menu">
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
                                                   value="<#if currentStartDate??>${currentStartDate}</#if>"
                                                   style="width: 5.06rem">
                                            <label for="date-to" class="input-group-text">До</label>
                                            <input type="date" name="endDate" id="date-to" class="form-control"
                                                   value="<#if currentEndDate??>${currentEndDate}</#if>">
                                        </div>
                                        <button class="btn btn-outline-success rounded-4" type="submit">Применить
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <a href="/export/excel?pizzeria=${currentPizzeria}&manager=${currentManager}&expert=${currentExpert}&startDate=<#if currentStartDate??>${currentStartDate}</#if>&endDate=<#if currentEndDate??>${currentEndDate}</#if>"
                           class="btn btn-green rounded-4">Скачать <i class="bi bi-file-arrow-down"></i></a>
                        <a href="/statistics"
                           class="btn btn-green rounded-4">Статистика</a>
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
                                <button class="btn btn-green rounded-4" type="submit">Применить</button>
                            </div>
                        </div>
                    </form>
                    <a href="/export/excel?pizzeria=${currentPizzeria}&manager=${currentManager}&expert=${currentExpert}&startDate=<#if currentStartDate??>${currentStartDate}</#if>&endDate=<#if currentEndDate??>${currentEndDate}</#if>"
                       class="btn btn-green mt-3 rounded-4">Скачать Excel</a>

                    <a href="/statistics"
                       class="btn btn-green mt-3 rounded-4">Статистика</a>
                </div>
            </div>
        </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://moment.github.io/luxon/global/luxon.min.js"></script>
    <script src="/js/clock.js"></script>
    <script src="../js/appealsCount.js"></script>


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

        jQuery(document).ready(function ($) {
            $(".clickable-row").click(function () {
                window.location = $(this).data("href");
            });
        });
    </script>

</@main.layout>