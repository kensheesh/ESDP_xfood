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
                                            <option value="${location.id}">${location.name}</option>
                                        </#list>
                                    <#else>
                                        <option disabled>Нет доступных территорий</option>
                                    </#if>
                                </select>
                            </div>
                            <div class="px-3" id="pizzeria-list">
                                <span class="text-secondary">Список пуст</span>
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
                    <button type="button" class="btn btn-primary rounded-4">Создать</button>
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

        async function handleSelectChange(selectedValue) {
            let pizzeriaList = document.getElementById('pizzeria-list');
            pizzeriaList.innerHTML = '';
            fetch('/api/pizzerias/location/' + selectedValue)
                .then(response => response.json())
                .then(data => {
                    data.forEach(item => {
                        const pizzeria = document.createElement('p');
                        const pizzeriaLink = document.createElement('a');
                        pizzeriaLink.href = "/api/managers-work-schedule/pizzeria/" + item.id;
                        pizzeriaLink.textContent = item.name;
                        pizzeriaLink.addEventListener('click', function() {
                            handlePizzeriaClick(this.href);
                        });
                        pizzeria.appendChild(pizzeriaLink);

                        pizzeriaList.insertAdjacentElement('beforeend', pizzeria);
                    });
                })
                .catch(error => console.error('Error fetching pizzerias:', error));
        }

        async function handlePizzeriaClick(href) {
            event.preventDefault();
            let scheduleList = document.getElementById('schedule-list');
            scheduleList.innerHTML = '';

            fetch(href)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    if (data.length === 0) {
                        let loss = document.createElement('span');
                        loss.className = 'text-center fs-4 p-4';
                        loss.innerHTML = 'Нет результатов';
                        scheduleList.insertAdjacentElement('beforeend', loss);
                    } else {
                        data.forEach(item => {
                            let row = document.createElement('tr');
                            let manager = document.createElement('td');
                            manager.className = 'align-middle';
                            manager.innerHTML =
                                `
                                <span>` + item.manager.surname + `<br>` + item.manager.name + `</span>
                            `;

                            row.insertAdjacentElement('beforeend', manager);

                            item.weeklySchedule.forEach(schedule => {
                                let day = document.createElement('td');
                                if(schedule.workDay) {
                                    day.innerHTML =
                                        `
                                        <div class="row row-cols-1">
                                            <div class="col">
                                                <div class="text-body-secondary fw-medium calendar-date">
                                                    ` + schedule.date + `
                                                </div>
                                            </div>
                                            <div class="col">
                                                <button class="btn btn-sm btn-success rounded-4 calendar-button"
                                                        style="font-size: 11px;" id="" value="` + schedule.date + `" data-bs-toggle="modal"
                                                        data-bs-target="#workShiftModal" onclick="handleWorkDayClick(` + item.manager.id + `, this.value)">
                                                    <div>` + schedule.startTime + ` - ` + schedule.endTime + `</div>
                                                </button>
                                            </div>
                                        </div>
                                        `;
                                } else {
                                    day.innerHTML =
                                        `
                                    <div class="row row-cols-1">
                                        <div class="col">
                                            <div class="text-body-secondary fw-medium calendar-date">
                                                ` + schedule.date + `
                                            </div>
                                        </div>
                                        <div class="col">
                                            <button class="btn btn-sm btn-light rounded-4 calendar-button" disabled>
                                                <i class="bi bi-ban"></i>
                                            </button>
                                        </div>
                                    </div>
                                    `;
                                }
                                row.insertAdjacentElement('beforeend', day);
                            })
                        scheduleList.insertAdjacentElement('beforeend', row);
                        });
                    }
                })
                .catch(error => console.error('Error fetching work schedules:', error));
        }

        function handleWorkDayClick(id, date) {
            currentDate = date;
            managerId = id;

            let shiftsList = document.getElementById('work-shifts');

            fetch('/api/opportunities/bydate/' + date)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
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
                            row.innerHTML = shift;
                            cardBody.insertAdjacentElement('beforeend', row)
                        })

                        opportunity.insertAdjacentElement('beforeend', card);
                        card.insertAdjacentElement('beforeend', cardBody);
                        shiftsList.insertAdjacentElement('beforeend', opportunity);
                    });
                })
                .catch(error => console.error('Error fetching pizzerias:', error));

        }

    </script>

</@main.layout>