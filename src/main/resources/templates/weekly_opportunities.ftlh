<#import "layout.ftlh" as main>
<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

<#--    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">-->

    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <#assign userRole = authorizedUser.authorities[0].authority>
                        <div class="col">
                            <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <a href="/supervisor/weekly" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        График
                                    </a>
                                </div>
                                <#if userRole == "ROLE_SUPERVISOR" || userRole == "ROLE_ADMIN">
                                    <div class="col">
                                        <a href="/supervisor/opportunities" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            Возможности
                                        </a>
                                    </div>
                                </#if>
                                <div class="col">
                                    <a href="/expert/checks" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        Проверки
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/analytics" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        Аналитика
                                    </a>
                                </div>
                            </div>
                            <div class="col align-self-end d-none d-md-block">
                                <div class="col">
                                    <div class="dropdown d-md-none h-100">
                                        <button class="btn btn-light rounded-4 dropdown-toggle w-100 h-100"
                                                type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            <i class="bi bi-clock"></i>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <section class="times_second"></section>
                                        </ul>
                                    </div>
                                </div>
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
        </div>
        <div class="col-md-8 col-xl-9">
            <nav aria-label="navigation">
                <ul class="pagination justify-content-center my-3">
                    <li class="page-item"><a class="page-link text-secondary" href="/supervisor/opportunities?week=${week.weekOrder - 1}">Previous</a></li>
                    <span class="text-secondary pt-2 mx-3">${week.monday} - ${week.sunday}</span>
                    <li class="page-item"><a class="page-link text-secondary" href="/supervisor/opportunities?week=${week.weekOrder + 1}">Next</a></li>
                </ul>
            </nav>
            <div class="card border-0 shadow-sm rounded-4">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-borderless">
                            <tbody id="schedule-list">
                            <#if opportunities?has_content>
                                <#list opportunities as opportunity>
                                    <tr>
                                    <td class="align-middle">
                                        <span>${opportunity.expert.name}<br>${opportunity.expert.surname}</span>
                                    </td>
                                    <#list opportunity.weeklyOpportunity as day>
                                        <td>
                                            <#if !day.empty>
                                                <#if day.dayOff>
                                                    <div class="row row-cols-1">
                                                        <div class="col">
                                                            <div class="text-body-secondary fw-medium calendar-date">${day.date}</div>
                                                        </div>
                                                        <div class="col">
                                                            <button class="btn btn-sm btn-danger rounded-4 calendar-button"
                                                                    style="font-size: 11px;" id="" value="${day.date}" data-bs-toggle="modal"
                                                                    data-bs-target="#workShiftModal" onclick="handleWorkDayClick(${opportunity.expert.id}, this.value)">
                                                                <i class="bi bi-ban"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                <#else>
                                                    <div class="row row-cols-1">
                                                        <div class="col">
                                                            <div class="text-body-secondary fw-medium calendar-date">${day.date}</div>
                                                        </div>
                                                        <div class="col">
                                                            <button class="btn btn-sm btn-success rounded-4 calendar-button"
                                                                    style="font-size: 11px;" id="" value="${day.date}" data-bs-toggle="modal"
                                                                    data-bs-target="#workShiftModal" onclick="handleWorkDayClick(${opportunity.expert.id}, this.value)">
                                                                <#list day.shifts as shift>>
                                                                    <p>${shift.startTime} - ${shift.endTime}</p>
                                                                </#list>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </#if>
                                            <#else>
                                                <div class="row row-cols-1">
                                                    <div class="col">
                                                        <div class="text-body-secondary fw-medium calendar-date">
                                                            ${day.date}
                                                        </div>
                                                    </div>
                                                    <div class="col">
                                                        <button class="btn btn-sm btn-light rounded-4 calendar-button"
                                                                style="font-size: 11px;" id="" value="${day.date}" data-bs-toggle="modal"
                                                                data-bs-target="#workShiftModal" onclick="handleWorkDayClick(${opportunity.expert.id}, this.value)">
                                                            <i class="bi bi-plus-circle"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </#if>
                                        </td>
                                    </#list>
                                </#list>
                                </tr>
                            <#else>
                                <span class="text-center fs-4 p-4">Нет результатов</span>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="workShiftModal" tabindex="-1" aria-labelledby="workShiftModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content rounded-4">
                <div class="modal-header">
                    <h1 class="modal-title fs-6" id="workShiftModalLabel">Выберите данные для назначения проверки</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form class="my-1 mx-2 p-1" method="POST" action="/supervisor/opportunities">
                    <div class="mb-3">
                        <label for="category" class="form-label ps-1 mt-2">Пиццерия</label>
<#--                        //ToDo Доделать живой поиск-->
<#--                        <input type="text" id="search" class="form-control" placeholder="Введите название пиццерии">-->
<#--                        <div class="dropdown-menu" id="dropdown">-->
<#--                            <option selected>Выберите пиццерию</option>-->
<#--                            <#list pizzerias as pizzeria>-->
<#--                                <option value="${pizzeria.id}">${pizzeria.name?cap_first}</option>-->
<#--                            </#list>-->
<#--                        </div>-->
                        <select class="form-select" id="category" name="pizzeriaId" required>
                            <option selected>Выберите пиццерию</option>
                            <#list pizzerias as pizzeria>
                                <option value="${pizzeria.id}">${pizzeria.name?cap_first}</option>
                            </#list>
                        </select>
                        <label for="category" class="form-label ps-1 mt-2">Менеджер</label>
                        <select class="form-select" id="category" name="managerId" required>
                            <option selected>Выберите менеджера смены</option>
                            <#list managers as manager>
                                <option value="${manager.id}">${manager.surname?cap_first} ${manager.name?cap_first}</option>
                            </#list>
                        </select>
                        <div class="col-auto">
                            <div class="input-group mt-3">
                                <input class="form-control rounded-start-4 w-0 " style="max-width: 75px;"
                                       type="number" min="0" max="23" name="startTimeHour"
                                       oninput="validate(event)" required>
                                <div class="input-group-text">
                                    :
                                </div>
                                <input class="form-control" type="number" min="0" max="59" name="startTimeMinute"
                                       oninput="validate(event)" required>
                                <div class="col-auto input-group-text">
                                    —
                                </div>
                                <input class="form-control" type="number" min="0" max="23" name="endTimeHour"
                                       oninput="validate(event)" required>
                                <div class="input-group-text">
                                    :
                                </div>
                                <input class="form-control rounded-end-4" type="number" min="0" max="59" name="endTimeMinute"
                                       oninput="validate(event)" required>
                                <input type="hidden" name="currentDate" id="currentDate" value="">
                                <input type="hidden" name="expertId" id="expertId" value="">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <a type="button" class="btn btn-link text-decoration-none link-secondary" data-bs-dismiss="modal">Отмена</a>
                        <button id="saveOpportunity" type="submit" class="btn btn-dodo rounded-4">Сохранить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <style>
        #dropdown {
            display: none;
            max-height: 200px;
            overflow-y: auto;
        }
        #dropdown.show {
            display: block;
        }

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

        .calendar-button {
            height: 60px;
            width: 100px;
        }

        .calendar-date {
            font-size: 11px;
            text-align: right;
        }
    </style>



    <script>
        let currentDate;
        let expertId;
        document.addEventListener('DOMContentLoaded', inputFunction)

        async function handleWorkDayClick(id, date) {
            currentDate = date;
            expertId = id;

            document.getElementById('currentDate').value = currentDate;
            document.getElementById('expertId').value = expertId;
        }

        function validate(event) {
            let inputName = event.target.name;
            let indexStr = inputName.match(/\[(.*?)\]/)[1];
            let index = Number(indexStr);

            let startTimeHour = document.getElementsByName('startTimeHour');
            let startTimeMinute = document.getElementsByName('startTimeMinute');
            let endTimeHour = document.getElementsByName('endTimeHour');
            let endTimeMinute = document.getElementsByName('endTimeMinute');

            let startHour = parseInt(startTimeHour.value);
            let startMinute = parseInt(startTimeMinute.value);

            startTimeHour.setAttribute('min', '0');
            startTimeHour.setAttribute('max', '23');

            if (startHour === 23) {
                startTimeMinute.setAttribute('max', '58');
            } else {
                startTimeMinute.setAttribute('max', '59');
            }
            startTimeMinute.setAttribute('min', '0');

            if (!isNaN(startHour) && !isNaN(startMinute)) {
                if (startMinute === 59) {
                    endTimeHour.setAttribute('min', startHour + 1);
                    endTimeMinute.setAttribute('min', '0');
                } else {
                    endTimeHour.setAttribute('min', startHour);
                    endTimeMinute.setAttribute('min', startMinute + 1);
                }

                endTimeHour.setAttribute('max', '23');
                endTimeMinute.setAttribute('max', '59');
            }

            // Проверка, чтобы min не превышал max
            let endMinHour = parseInt(endTimeHour.getAttribute('min'));
            let endMaxHour = parseInt(endTimeHour.getAttribute('max'));
            if (endMinHour > endMaxHour) {
                endTimeHour.setAttribute('min', '0');
                endTimeHour.setAttribute('max', '23');
            }

            let endMinMinute = parseInt(endTimeMinute.getAttribute('min'));
            let endMaxMinute = parseInt(endTimeMinute.getAttribute('max'));
            if (endMinMinute > endMaxMinute) {
                endTimeMinute.setAttribute('min', '0');
                endTimeMinute.setAttribute('max', '59');
            }
        }

        async function inputFunction() {
            const searchInput = document.getElementById('search');
            const dropdown = document.getElementById('dropdown');

            searchInput.addEventListener('focus', toggleDropdown);
            searchInput.addEventListener('keyup', liveSearch);
            document.addEventListener('click', function(event) {
                if (!dropdown.contains(event.target) && !searchInput.contains(event.target)) {
                    dropdown.classList.remove('show');
                }
            });

            function toggleDropdown() {
                dropdown.classList.toggle('show');
            }

            function liveSearch() {
                const searchQuery = searchInput.value;
                const xhr = new XMLHttpRequest();
                xhr.open('GET', `/api/pizzerias/search?query=` + searchQuery, true);
                xhr.onload = function () {
                    if (xhr.status === 200) {
                        const pizzerias = JSON.parse(xhr.responseText);
                        dropdown.innerHTML = '';
                        pizzerias.forEach(pizzeria => {
                            const item = document.createElement('a');
                            item.className = 'dropdown-item';
                            item.textContent = pizzeria.name;
                            item.addEventListener('click', function () {
                                searchInput.value = pizzeria.name;
                                dropdown.classList.remove('show');
                            });
                            dropdown.appendChild(item);
                        });
                        dropdown.classList.add('show');
                    }
                };
                xhr.send();
            }
            async function liveSearch() {
                const searchQuery = document.getElementById('search').value;
                const dropdown = document.getElementById('dropdown');

                try {
                    const response = await fetch(`/api/pizzerias/search?query=` + searchQuery);
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    const pizzerias = await response.json();

                    dropdown.innerHTML = '';

                    pizzerias.forEach(pizzeria => {
                        const item = document.createElement('a');
                        item.className = 'dropdown-item';
                        item.href = '#';
                        item.textContent = pizzeria.name;
                        item.addEventListener('click', function() {
                            document.getElementById('search').value = pizzeria.name;
                            dropdown.classList.remove('show');
                        });
                        dropdown.appendChild(item);
                    });

                    dropdown.classList.add('show');
                } catch (error) {
                    console.error('Error fetching pizzerias:', error);
                }
            }
        }

    </script>

</@main.layout>