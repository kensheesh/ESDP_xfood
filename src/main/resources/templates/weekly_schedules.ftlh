<#import "layout.ftlh" as main>
<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <#assign userRole = authorizedUser.authorities[0].authority>
                        <div class="col">
                            <div class="row row-cols-3 row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <a href="#" class="btn rounded-4 w-100 h-100 disabled btn-dodo-light">
                                        <i class="bi bi-calendar2-range fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">График</span>
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/supervisor/opportunities"
                                       class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-calendar2-week fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">Возможности</span>
                                    </a>
                                </div>
                                <div class="col button-wrapper">
                                    <a href="/checks" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <span class="d-none d-md-inline">Проверки</span>
                                        <i class="bi bi-clipboard-check fs-5 d-md-none"></i>
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/analytics" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-bar-chart-fill fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">Аналитика</span>
                                    </a>
                                </div>
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
            <div class="py-3 px-3 text-center">
                <h6>Фильтры</h6>
                <select class="form-select mb-2" onchange="handleSelectChange(this.value)">
                    <option selected disabled>Выберите территорию</option>
                    <#if locations?has_content>
                        <#list locations as location>
                            <option value="${location.id}" <#if location.id == locationId>selected</#if>>${location.name}</option>
                        </#list>
                    <#else>
                        <option disabled>Нет доступных территорий</option>
                    </#if>
                </select>
            </div>
            <div class="px-3" id="pizzeria-list">
                <#if pizzerias?has_content>
                    <#list pizzerias as pizzeria>
                        <div class="btn border w-100 text-start p-2 my-1" id="${pizzeria.id}" onclick="handlePizzeriaClick(this.id)">${pizzeria.name}</div>
                    </#list>
                <#else>
                    <span class="text-secondary">Список пуст</span>
                </#if>
            </div>
            <nav aria-label="navigation">
                <ul class="pagination justify-content-center my-3">
                    <li class="page-item"><a class="page-link text-secondary" href="/supervisor/weekly?locId=${locationId}&pizzId=${pizzeriaId}&week=${week.weekOrder - 1}">Previous</a></li>
                    <span class="text-secondary pt-2 mx-3">${week.monday} - ${week.sunday}</span>
                    <li class="page-item"><a class="page-link text-secondary" href="/supervisor/weekly?locId=${locationId}&pizzId=${pizzeriaId}&week=${week.weekOrder + 1}">Next</a></li>
                </ul>
            </nav>
            <div class="card border-0 shadow-sm rounded-4">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-borderless">
                            <tbody id="schedule-list">
                                    <#if schedules?has_content>
                                    <#list schedules as schedule>
                                    <tr>
                                    <td class="align-middle">
                                            <span>${schedule.manager.surname}<br>${schedule.manager.name}</span>
                                            </td>
                                            <#list schedule.weeklySchedule as day>
                                                <td>
                                                    <#if day.workDay>
                                                        <div class="row row-cols-1">
                                                            <div class="col">
                                                                <div class="text-body-secondary fw-medium calendar-date">${day.date}</div>
                                                            </div>
                                                            <div class="col">
                                                                <button class="btn btn-sm btn-success rounded-4 calendar-button"
                                                                        style="font-size: 11px;" id="" value="${day.date}" data-bs-toggle="modal"
                                                                        data-bs-target="#workShiftModal" onclick="handleWorkDayClick(${schedule.manager.id}, this.value)">
                                                                    <div>${day.startTime} - ${day.endTime}</div>
                                                                </button>
                                                            </div>
                                                        </div>
                                                    <#else>
                                                        <div class="row row-cols-1">
                                                            <div class="col">
                                                                <div class="text-body-secondary fw-medium calendar-date">
                                                                    ${day.date}
                                                                </div>
                                                            </div>
                                                            <div class="col">
                                                                <button class="btn btn-sm btn-light rounded-4 calendar-button" disabled>
                                                                    <i class="bi bi-ban"></i>
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
                    <h1 class="modal-title fs-6" id="workShiftModalLabel">Выберите эксперта для назначения на проверку</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="work-shifts" class="row row-cols-1 row-gap-3 mx-1">
                    </div>
                </div>
                <div class="modal-footer">
                    <a type="button" class="btn btn-link text-decoration-none link-secondary" data-bs-dismiss="modal">Отмена</a>
                </div>
            </div>
        </div>
    </div>


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

        .calendar-button {
            height: 60px;
            width: 100px;
        }

        .calendar-date {
            font-size: 11px;
            text-align: right;
        }

        .badge-notification-icon {
            position: absolute;
            display:block;
            top: -8px;
            right: 0px;
            height: 27px;
            width: 27px;
            border-radius: 50%;
            background: red;
        }

        .badge-notification-num {
            text-align: center;
            vertical-align: middle;
            line-height: 27px;
            width: 27px;
            color: white;
        }

        .button-wrapper {
            position: relative;
            display: inline-block;
        }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://moment.github.io/luxon/global/luxon.min.js"></script>
    <script src="/js/clock.js"></script>
    <script src="../js/appealsCount.js"></script>

    <script>
        let currentDate;
        let managerId;
        let expertId;
        let locationId;

        function handleSelectChange(selectedValue) {
            locationId = selectedValue;
            localStorage.setItem('locationId', locationId);
            window.location.href = '/supervisor/weekly?locId=' + locationId
        }

        function handlePizzeriaClick(pizzeriaId) {
            locationId = localStorage.getItem('locationId');
            window.location.href = '/supervisor/weekly?locId=' + locationId + '&pizzId=' + pizzeriaId;
        }

        async function handleWorkDayClick(id, date) {
            currentDate = date;
            managerId = id;

            let shiftsList = document.getElementById('work-shifts');
            shiftsList.innerHTML = '';

            fetch('/api/opportunities/bydate/' + date)
                .then(response => response.json())
                .then(data => {
                    if (data.length > 0) {
                        data.forEach(item => {
                            let opportunity = document.createElement('div');
                            opportunity.className = 'row';
                            let card = document.createElement('a');
                            card.className = 'card text-decoration-none mb-2';
                            card.href = '/checks/create?date=' + currentDate + '&managerId=' + managerId + '&expertId=' + item.user.id;
                            let cardBody = document.createElement('div');
                            cardBody.className = 'card-body';
                            cardBody.innerHTML =
                                `
                                <h5 class="card-title">` + item.user.surname + ` ` + item.user.name + `</h5>
                                `;
                            item.shifts.forEach(shift => {
                                let row = document.createElement('p');
                                row.innerHTML = shift.startTime + ` - ` + shift.endTime;
                                cardBody.appendChild(row);
                            });
                            card.appendChild(cardBody);
                            opportunity.appendChild(card);
                            shiftsList.appendChild(opportunity);
                        });
                    } else {
                        let noOpportunities = document.createElement('div');
                        noOpportunities.className = 'text-center text-secondary';
                        noOpportunities.innerText = 'Нет доступных экспертов на выбранную дату';
                        shiftsList.appendChild(noOpportunities);
                    }
                    fetchAllExperts();
                })
                .catch(error => console.error('Error fetching opportunities:', error));
        }

        async function fetchAllExperts() {
            fetch('/api/experts')
                .then(response => response.json())
                .then(data => {
                    data.forEach(expert => {
                        if (!document.querySelector(`[href*="&expertId=` + expert.id + `"]`)) {
                            let expertElement = document.createElement('div');
                            expertElement.className = 'row';
                            let card = document.createElement('a');
                            card.className = 'card text-decoration-none mb-2';
                            card.href = '/checks/create?date=' + currentDate + '&managerId=' + managerId + '&expertId=' + expert.id;
                            let cardBody = document.createElement('div');
                            cardBody.className = 'card-body';
                            cardBody.innerHTML = `
                                    <h5 class="card-title">` + expert.surname + ` ` + expert.name + `</h5>
                                    <span class="text-secondary">Нет доступных смен</span>
                                `;
                            card.appendChild(cardBody);
                            expertElement.appendChild(card);
                            document.getElementById('work-shifts').appendChild(expertElement);
                        }
                    });
                })
                .catch(error => console.error('Error fetching experts:', error));
        }

        document.addEventListener('DOMContentLoaded', (event) => {
            locationId = localStorage.getItem('locationId');
        });
    </script>

</@main.layout>