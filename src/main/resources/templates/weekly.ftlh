<#import "layout.ftlh" as main>
<@main.layout>

    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <div class="col">
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
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-8 col-xl-9">
            <div class="card border-0 shadow-sm rounded-4">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-borderless">
                            <tbody id="schedule-list">
                                <tr>
                                    <#if schedules?has_content>
                                        <#list schedules as schedule>
                                            <td class="align-middle">
                                                <span>${schedule.manager.name}<br>${schedule.manager.surname}</span>
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
                                    <#else>
                                        <span class="text-center fs-4 p-4">Нет результатов</span>
                                    </#if>
                                </tr>
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
                    <div id="work-shifts" class="row row-cols-1 row-gap-3">
                        <div class="row">
                        </div>
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
    </style>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">
    </script>

    <script>
        let currentDate;
        let managerId;
        let expertId;
        let locationId;

        async function handleSelectChange(selectedValue) {
            locationId = selectedValue;
            localStorage.setItem('locationId', locationId);
            window.location.href = '/supervisor/weekly?locId=' + locationId
        }

        async function handlePizzeriaClick(pizzeriaId) {
            locationId = localStorage.getItem('locationId');
            window.location.href = '/supervisor/weekly?locId=' + locationId + '&pizzId=' + pizzeriaId;
        }

        /*function handleWorkDayClick(id, date) {
            currentDate = date;
            managerId = id;

            let shiftsList = document.getElementById('work-shifts');
            shiftsList.innerHTML = '';

            fetch('/api/opportunities/bydate/' + date)
                .then(response => response.json())
                .then(data => {
                    data.forEach(item => {
                        let opportunity = document.createElement('div');
                        opportunity.className = 'row';
                        let card = document.createElement('a');
                        card.className = 'card';
                        card.href = '/checks/create?date=' + currentDate + '&managerId=' + managerId + '&expertId=' + item.user.id;
                        let cardBody = document.createElement('div');
                        cardBody.className = 'card-body';
                        cardBody.innerHTML = `
                                <h5 class="card-title">` + item.user.surname + ` ` + item.user.name + `</h5>
                                             `;
                        item.shifts.forEach(shift => {
                            let row = document.createElement('span');
                            row.innerHTML = `
                                ` + shift.startTime + `-` + shift.endTime + `
                            `;
                            cardBody.insertAdjacentElement('beforeend', row)
                        })

                        opportunity.insertAdjacentElement('beforeend', card);
                        card.insertAdjacentElement('beforeend', cardBody);
                        shiftsList.insertAdjacentElement('beforeend', opportunity);
                    });
                })
                .catch(error => console.error('Error fetching pizzerias:', error));

        }*/


        function handleWorkDayClick(id, date) {
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
                            cardBody.innerHTML = `
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

        function fetchAllExperts() {
            fetch('/api/experts')
                .then(response => response.json())
                .then(data => {
                    data.forEach(expert => {
                        if (!document.querySelector(`[href*="&expertId=` + expert.id + `"]`)) {
                            let expertElement = document.createElement('div');
                            expertElement.className = 'row';
                            let card = document.createElement('a');
                            card.className = 'card mb-2';
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