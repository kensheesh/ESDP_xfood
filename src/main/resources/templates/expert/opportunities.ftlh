<#import "../layout.ftlh" as main>

<@main.layout>
    <link rel="stylesheet" href="/css/opportunities.css">

    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <div class="col">
                            <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <a href="#" class="btn rounded-4 w-100 h-100 disabled btn-dodo-light">
                                        <i class="bi bi-calendar2-range fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">График</span>
                                    </a>
                                </div>
                                <div class="col button-wrapper">
                                    <a href="/expert/checks" class="btn rounded-4 w-100 h-100 btn-dodo-light">
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
                                    <a href="/analytics" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-bar-chart-fill fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">Аналитика</span>
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
            <div class="card border-0 rounded-4 shadow-sm">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table text-center table-bordered align-middle">
                            <thead>
                            <tr>
                                <th scope="col">
                                    <div class="text-body-secondary" style="font-size: 12px;">${monday}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal" id="${monday}">
                                        Пн
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary"
                                         style="font-size: 12px;">${monday.plusDays(1)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal"
                                            id="${monday.plusDays(1)}">
                                        Вт
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary"
                                         style="font-size: 12px;">${monday.plusDays(2)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal"
                                            id="${monday.plusDays(2)}">
                                        Ср
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary"
                                         style="font-size: 12px;">${monday.plusDays(3)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal"
                                            id="${monday.plusDays(3)}">
                                        Чт
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary" style="font-size: 12px">${monday.plusDays(4)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal"
                                            id="${monday.plusDays(4)}">
                                        Пт
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary"
                                         style="font-size: 12px;">${monday.plusDays(5)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal"
                                            id="${monday.plusDays(5)}">
                                        Сб
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary"
                                         style="font-size: 12px;">${monday.plusDays(6)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal"
                                            id="${monday.plusDays(6)}">
                                        Вс
                                    </button>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <#assign firstDay = opportunities[monday]!"">
                            <#assign secondDay = opportunities[monday.plusDays(1)]!"">
                            <#assign thirdDay = opportunities[monday.plusDays(2)]!"">
                            <#assign fourthDay = opportunities[monday.plusDays(3)]!"">
                            <#assign fifthDay = opportunities[monday.plusDays(4)]!"">
                            <#assign sixthDay = opportunities[monday.plusDays(5)]!"">
                            <#assign seventhDay = opportunities[monday.plusDays(6)]!"">
                            <tr>
                                <td>
                                    <#if firstDay != "">
                                        <#if !firstDay.isDayOff>
                                            <#if firstDay.shifts[0]??>
                                                ${firstDay.shifts[0].startTime}-${firstDay.shifts[0].endTime}
                                            </#if>
                                        <#else>
                                            Выходной
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if secondDay != "">
                                        <#if !secondDay.isDayOff>
                                            <#if secondDay.shifts[0]??>
                                                ${secondDay.shifts[0].startTime}-${secondDay.shifts[0].endTime}
                                            </#if>
                                        <#else>
                                            Выходной
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if thirdDay != "">
                                        <#if !thirdDay.isDayOff>
                                            <#if thirdDay.shifts[0]??>
                                                ${thirdDay.shifts[0].startTime}-${thirdDay.shifts[0].endTime}
                                            </#if>
                                        <#else>
                                            Выходной
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if fourthDay != "">
                                        <#if !fourthDay.isDayOff>
                                            <#if fourthDay.shifts[0]??>
                                                ${fourthDay.shifts[0].startTime}-${fourthDay.shifts[0].endTime}
                                            </#if>
                                        <#else>
                                            Выходной
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if fifthDay != "">
                                        <#if !fifthDay.isDayOff>
                                            <#if fifthDay.shifts[0]??>
                                                ${fifthDay.shifts[0].startTime}-${fifthDay.shifts[0].endTime}
                                            </#if>
                                        <#else>
                                            Выходной
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if sixthDay != "">
                                        <#if !sixthDay.isDayOff>
                                            <#if sixthDay.shifts[0]??>
                                                ${sixthDay.shifts[0].startTime}-${sixthDay.shifts[0].endTime}
                                            </#if>
                                        <#else>
                                            Выходной
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if seventhDay != "">
                                        <#if !seventhDay.isDayOff>
                                            <#if seventhDay.shifts[0]??>
                                                ${seventhDay.shifts[0].startTime}-${seventhDay.shifts[0].endTime}
                                            </#if>
                                        <#else>
                                            Выходной
                                        </#if>
                                    </#if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <#if firstDay != "">
                                        <#if firstDay.shifts[1]??>
                                            ${firstDay.shifts[1].startTime}-${firstDay.shifts[1].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if secondDay != "">
                                        <#if secondDay.shifts[1]??>
                                            ${secondDay.shifts[1].startTime}-${secondDay.shifts[1].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if thirdDay != "">
                                        <#if thirdDay.shifts[1]??>
                                            ${thirdDay.shifts[1].startTime}-${thirdDay.shifts[1].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if fourthDay != "">
                                        <#if fourthDay.shifts[1]??>
                                            ${fourthDay.shifts[1].startTime}-${fourthDay.shifts[1].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if fifthDay != "">
                                        <#if fifthDay.shifts[1]??>
                                            ${fifthDay.shifts[1].startTime}-${fifthDay.shifts[1].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if sixthDay != "">
                                        <#if sixthDay.shifts[1]??>
                                            ${sixthDay.shifts[1].startTime}-${sixthDay.shifts[1].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if seventhDay != "">
                                        <#if seventhDay.shifts[1]??>
                                            ${seventhDay.shifts[1].startTime}-${seventhDay.shifts[1].endTime}
                                        </#if>
                                    </#if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <#if firstDay != "">
                                        <#if firstDay.shifts[2]??>
                                            ${firstDay.shifts[2].startTime}-${firstDay.shifts[2].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if secondDay != "">
                                        <#if secondDay.shifts[2]??>
                                            ${secondDay.shifts[2].startTime}-${secondDay.shifts[2].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if thirdDay != "">
                                        <#if thirdDay.shifts[2]??>
                                            ${thirdDay.shifts[2].startTime}-${thirdDay.shifts[2].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if fourthDay != "">
                                        <#if fourthDay.shifts[2]??>
                                            ${fourthDay.shifts[2].startTime}-${fourthDay.shifts[2].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if fifthDay != "">
                                        <#if fifthDay.shifts[2]??>
                                            ${fifthDay.shifts[2].startTime}-${fifthDay.shifts[2].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if sixthDay != "">
                                        <#if sixthDay.shifts[2]??>
                                            ${sixthDay.shifts[2].startTime}-${sixthDay.shifts[2].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if seventhDay != "">
                                        <#if seventhDay.shifts[2]??>
                                            ${seventhDay.shifts[2].startTime}-${seventhDay.shifts[2].endTime}
                                        </#if>
                                    </#if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <#if firstDay != "">
                                        <#if firstDay.shifts[3]??>
                                            ${firstDay.shifts[3].startTime}-${firstDay.shifts[3].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if secondDay != "">
                                        <#if secondDay.shifts[3]??>
                                            ${secondDay.shifts[3].startTime}-${secondDay.shifts[3].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if thirdDay != "">
                                        <#if thirdDay.shifts[3]??>
                                            ${thirdDay.shifts[3].startTime}-${thirdDay.shifts[3].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if fourthDay != "">
                                        <#if fourthDay.shifts[3]??>
                                            ${fourthDay.shifts[3].startTime}-${fourthDay.shifts[3].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if fifthDay != "">
                                        <#if fifthDay.shifts[3]??>
                                            ${fifthDay.shifts[3].startTime}-${fifthDay.shifts[3].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if sixthDay != "">
                                        <#if sixthDay.shifts[3]??>
                                            ${sixthDay.shifts[3].startTime}-${sixthDay.shifts[3].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if seventhDay != "">
                                        <#if seventhDay.shifts[3]??>
                                            ${seventhDay.shifts[3].startTime}-${seventhDay.shifts[3].endTime}
                                        </#if>
                                    </#if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <#if firstDay != "">
                                        <#if firstDay.shifts[4]??>
                                            ${firstDay.shifts[4].startTime}-${firstDay.shifts[4].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if secondDay != "">
                                        <#if secondDay.shifts[4]??>
                                            ${secondDay.shifts[4].startTime}-${secondDay.shifts[4].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if thirdDay != "">
                                        <#if thirdDay.shifts[4]??>
                                            ${thirdDay.shifts[4].startTime}-${thirdDay.shifts[4].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if fourthDay != "">
                                        <#if fourthDay.shifts[4]??>
                                            ${fourthDay.shifts[4].startTime}-${fourthDay.shifts[4].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if fifthDay != "">
                                        <#if fifthDay.shifts[4]??>
                                            ${fifthDay.shifts[4].startTime}-${fifthDay.shifts[4].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if sixthDay != "">
                                        <#if sixthDay.shifts[4]??>
                                            ${sixthDay.shifts[4].startTime}-${sixthDay.shifts[4].endTime}
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if seventhDay != "">
                                        <#if seventhDay.shifts[4]??>
                                            ${seventhDay.shifts[4].startTime}-${seventhDay.shifts[4].endTime}
                                        </#if>
                                    </#if>
                                </td>
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
            <form class="modal-content rounded-4" method="POST" action="/expert/opportunities/change">
                <div class="modal-header">
                    <h1 class="modal-title fs-6" id="workShiftModalLabel"></h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="alert rounded-4 alert-danger alert-dismissible fade show" role="alert">
                        Смены не могут пересекаться
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>

                    <div id="workShifts" class="row row-cols-1 row-gap-3">

                    </div>
                    <div class="form-check ms-1 mt-3">
                        <input class="form-check-input" type="checkbox" value="true" name="isDayOff" id="day-off">
                        <label class="form-check-label" for="day-off">
                            Выходной
                        </label>
                    </div>
                </div>
                <div class="modal-footer">
                    <a type="button" class="btn btn-link text-decoration-none link-secondary" data-bs-dismiss="modal">Отмена</a>
                    <button id="saveOpportunity" type="submit" class="btn btn-dodo rounded-4">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://moment.github.io/luxon/global/luxon.min.js"></script>
    <script src="/js/clock.js"></script>

    <script>
        window.lastInput = -1;
        let modal = document.getElementById('workShiftModal')
        let dayOffBtn = document.getElementById('day-off')
        let inputs = document.getElementById('workShifts')
        dayOffBtn.addEventListener('change', onChecked)
        modal.addEventListener('show.bs.modal', onShowModal)

        async function onShowModal(event) {
            let dateString = event.relatedTarget.id;
            inputs.innerHTML = `
                <div class="col">
                    <button type="button" id="add-shift-btn" class="btn btn-light rounded-4" onclick="addInput()">Добавить период</button>
                </div>
            `;

            let opportunityId = document.getElementById('opportunity-id')
            let opportunityDate = document.getElementById('opportunity-date')

            if (opportunityDate) {
                opportunityDate.remove()
            }

            if (opportunityId) {
                opportunityId.remove()
            }

            let dateInputHtml = document.createElement('input')
            dateInputHtml.type = 'hidden'
            dateInputHtml.name = 'date'
            dateInputHtml.id = 'opportunity-date'
            dateInputHtml.value = dateString;

            inputs.parentElement.appendChild(dateInputHtml);

            let workShiftsModalLabel = document.getElementById('workShiftModalLabel')
            workShiftsModalLabel.innerText = 'Выберите время, когда Вы можете работать ' + dateString

            let addShiftBtn = document.getElementById('add-shift-btn')
            // addShiftBtn.addEventListener('click', addInput)

            try {
                let request = await fetch('/api/opportunities?d=' + event.relatedTarget.id)
                if (request.ok) {
                    let opportunity = await request.json();
                    console.log(opportunity)

                    let opportunityIdHtml = document.createElement('input')
                    opportunityIdHtml.type = 'hidden';
                    opportunityIdHtml.name = 'id';
                    opportunityIdHtml.value = opportunity.id;
                    opportunityIdHtml.id = 'opportunity-id';
                    inputs.parentElement.appendChild(opportunityIdHtml)

                    if (opportunity.isDayOff === false || opportunity.isDayOff == null) {
                        for (let i = 0; i < opportunity.shifts.length; i++) {
                            let startTimeParts = opportunity.shifts[i].startTime.split(":")
                            let endTimeParts = opportunity.shifts[i].endTime.split(":")

                            let input = document.createElement('div')
                            input.classList.add('row', 'row-cols-2', 'col')

                            input.innerHTML = `<div class="col-auto">
                            <input type="hidden" name="shifts[` + i + `].id" value="` + opportunity.shifts[i].id + `">
                            <div class="input-group">
                                <input class="form-control rounded-start-4 w-0 " style="max-width: 65px;"
                                    type="number" min="0" max="23" value="` + startTimeParts[0] + `"
                                    name="shifts[` + i + `].startTimeHour" oninput="validate(event)" required>
                                <div class="input-group-text">
                                    :
                                </div>
                                <input class="form-control" type="number" min="0" max="59" value="` + startTimeParts[1] + `"
                                    name="shifts[` + i + `].startTimeMinute" oninput="validate(event)" required>
                                <div class="col-auto input-group-text">
                                    —
                                </div>
                                <input class="form-control" type="number" min="0" max="23" value="` + endTimeParts[0] + `"
                                    name="shifts[` + i + `].endTimeHour" oninput="validate(event)" required>
                                <div class="input-group-text">
                                    :
                                </div>
                                <input class="form-control rounded-end-4" type="number" min="0" max="59" value="` + endTimeParts[1] + `"
                                    name="shifts[` + i + `].endTimeMinute" oninput="validate(event)" required>
                            </div>
                        </div>
                        <div class="col-auto px-0">
                            <button type="button" class="btn btn-dodo-light rounded-4" onclick="deleteInput(event)"><i class="bi bi-trash3-fill"></i></button>
                        </div>`;

                            let addShiftBtn = document.getElementById('add-shift-btn')
                            inputs.insertBefore(input, addShiftBtn.parentElement)

                            window.lastInput = i;
                        }
                    } else {
                        dayOffBtn.checked = true;
                        onChecked();
                    }

                } else {
                    throw new Error();
                }
            } catch (e) {
                console.log(e);
            }
        }

        function onChecked() {
            if (dayOffBtn.checked == true) {
                inputs.innerHTML = ''
            }

            if (dayOffBtn.checked == false) {
                let addShiftBtn = document.createElement('div')
                addShiftBtn.classList.add('col')
                addShiftBtn.innerHTML = '<button class="btn btn-light rounded-4" id="add-shift-btn">Добавить период</button>'

                inputs.appendChild(addShiftBtn)
                addShiftBtn.addEventListener('click', addInput)
            }

        }

        function addInput() {

            if (inputs.childElementCount < 7) {
                let input = document.createElement('div')
                input.classList.add('row', 'row-cols-2')
                window.lastInput++;
                input.innerHTML = `<div class="col-auto">
                            <div class="input-group">
                                <input class="form-control rounded-start-4 w-0 " style="max-width: 65px;"
                                    type="number" min="0" max="23" name="shifts[` + window.lastInput + `].startTimeHour"
                                     oninput="validate(event)" required>
                                <div class="input-group-text">
                                    :
                                </div>
                                <input class="form-control" type="number" min="0" max="59" name="shifts[` + window.lastInput + `].startTimeMinute"
                                     oninput="validate(event)" required>
                                <div class="col-auto input-group-text">
                                    —
                                </div>
                                <input class="form-control" type="number" min="0" max="23" name="shifts[` + window.lastInput + `].endTimeHour"
                                     oninput="validate(event)" required>
                                <div class="input-group-text">
                                    :
                                </div>
                                <input class="form-control rounded-end-4" type="number" min="0" max="59" name="shifts[` + window.lastInput + `].endTimeMinute"
                                     oninput="validate(event)" required>
                            </div>
                        </div>
                        <div class="col-auto px-0">
                            <button type="button" class="btn btn-dodo-light rounded-4" onclick="deleteInput(event)"><i class="bi bi-trash3-fill"></i></button>
                        </div>`;

                let addShiftBtn = document.getElementById('add-shift-btn')
                inputs.insertBefore(input, addShiftBtn.parentElement)
            }
        }

        function deleteInput(event) {
            let btn = event.currentTarget;
            btn.parentElement.parentElement.remove();
            window.lastInput--;
        }

        function validate(event) {
            let inputName = event.target.name;
            let indexStr = inputName.match(/\[(.*?)\]/)[1];
            let index = Number(indexStr);

            let startTimeHour = document.getElementsByName('shifts[' + index + '].startTimeHour')[0];
            let startTimeMinute = document.getElementsByName('shifts[' + index + '].startTimeMinute')[0];
            let endTimeHour = document.getElementsByName('shifts[' + index + '].endTimeHour')[0];
            let endTimeMinute = document.getElementsByName('shifts[' + index + '].endTimeMinute')[0];

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

    </script>
</@main.layout>