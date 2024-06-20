<#import "../layout.ftlh" as main>

<@main.layout>
    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <div class="col">
                            <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <a href="#" class="btn btn-light rounded-4 w-100 h-100 active">
                                        График
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/expert/checks" class="btn btn-light rounded-4 w-100 h-100">
                                        Проверки
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/analytics" class="btn btn-light rounded-4 w-100 h-100">
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
            <div class="card border-0 shadow-sm rounded-4">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table text-center table-borderless">
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
                                    <div class="text-body-secondary" style="font-size: 12px;">${monday.plusDays(1)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal" id="${monday.plusDays(1)}">
                                        Вт
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary" style="font-size: 12px;">${monday.plusDays(2)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal" id="${monday.plusDays(2)}">
                                        Ср
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary" style="font-size: 12px;">${monday.plusDays(3)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal" id="${monday.plusDays(3)}">
                                        Чт
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary" style="font-size: 12px">${monday.plusDays(4)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal" id="${monday.plusDays(4)}">
                                        Пт
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary" style="font-size: 12px;">${monday.plusDays(5)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal" id="${monday.plusDays(5)}">
                                        Сб
                                    </button>
                                </th>
                                <th scope="col">
                                    <div class="text-body-secondary" style="font-size: 12px;">${monday.plusDays(6)}</div>
                                    <button type="button" class="btn btn-danger btn-sm rounded-4 modal-btn"
                                            data-bs-toggle="modal" data-bs-target="#workShiftModal" id="${monday.plusDays(6)}">
                                        Вс
                                    </button>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                                <#assign firstDay = opportunities[monday]>
                                <#assign secondDay = opportunities[monday.plusDays(1)]>
                                <#assign thirdDay = opportunities[monday.plusDays(2)]>
                                <#assign fourthDay = opportunities[monday.plusDays(3)]>
                                <#assign fifthDay = opportunities[monday.plusDays(4)]>
                                <#assign sixthDay = opportunities[monday.plusDays(5)]>
                                <#assign seventhDay = opportunities[monday.plusDays(6)]>
                                <tr>
                                    <td>
                                        <#if firstDay[0]??>
                                            ${firstDay[0].startTime}-${firstDay[0].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if secondDay[0]??>
                                            ${secondDay[0].startTime}-${secondDay[0].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if thirdDay[0]??>
                                            ${thirdDay[0].startTime}-${thirdDay[0].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if fourthDay[0]??>
                                            ${fourthDay[0].startTime}-${fourthDay[0].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if fifthDay[0]??>
                                            ${fifthDay[0].startTime}-${fifthDay[0].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if sixthDay[0]??>
                                            ${sixthDay[0].startTime}-${sixthDay[0].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if seventhDay[0]??>
                                            ${seventhDay[0].startTime}-${seventhDay[0].endTime}
                                        </#if>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <#if firstDay[1]??>
                                            ${firstDay[1].startTime}-${firstDay[1].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if secondDay[1]??>
                                            ${secondDay[1].startTime}-${secondDay[1].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if thirdDay[1]??>
                                            ${thirdDay[1].startTime}-${thirdDay[1].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if fourthDay[1]??>
                                            ${fourthDay[1].startTime}-${fourthDay[1].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if fifthDay[1]??>
                                            ${fifthDay[1].startTime}-${fifthDay[1].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if sixthDay[1]??>
                                            ${sixthDay[1].startTime}-${sixthDay[1].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if seventhDay[1]??>
                                            ${seventhDay[1].startTime}-${seventhDay[1].endTime}
                                        </#if>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <#if firstDay[2]??>
                                            ${firstDay[2].startTime}-${firstDay[2].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if secondDay[2]??>
                                            ${secondDay[2].startTime}-${secondDay[2].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if thirdDay[2]??>
                                            ${thirdDay[2].startTime}-${thirdDay[2].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if fourthDay[2]??>
                                            ${fourthDay[2].startTime}-${fourthDay[2].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if fifthDay[2]??>
                                            ${fifthDay[2].startTime}-${fifthDay[2].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if sixthDay[2]??>
                                            ${sixthDay[2].startTime}-${sixthDay[2].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if seventhDay[2]??>
                                            ${seventhDay[2].startTime}-${seventhDay[2].endTime}
                                        </#if>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <#if firstDay[3]??>
                                            ${firstDay[3].startTime}-${firstDay[3].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if secondDay[3]??>
                                            ${secondDay[3].startTime}-${secondDay[3].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if thirdDay[3]??>
                                            ${thirdDay[3].startTime}-${thirdDay[3].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if fourthDay[3]??>
                                            ${fourthDay[3].startTime}-${fourthDay[3].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if fifthDay[3]??>
                                            ${fifthDay[3].startTime}-${fifthDay[3].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if sixthDay[3]??>
                                            ${sixthDay[3].startTime}-${sixthDay[3].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if seventhDay[3]??>
                                            ${seventhDay[3].startTime}-${seventhDay[3].endTime}
                                        </#if>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <#if firstDay[4]??>
                                            ${firstDay[4].startTime}-${firstDay[4].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if secondDay[4]??>
                                            ${secondDay[4].startTime}-${secondDay[4].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if thirdDay[4]??>
                                            ${thirdDay[4].startTime}-${thirdDay[4].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if fourthDay[4]??>
                                            ${fourthDay[4].startTime}-${fourthDay[4].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if fifthDay[4]??>
                                            ${fifthDay[4].startTime}-${fifthDay[4].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if sixthDay[4]??>
                                            ${sixthDay[4].startTime}-${sixthDay[4].endTime}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if seventhDay[4]??>
                                            ${seventhDay[4].startTime}-${seventhDay[4].endTime}
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
                        <div class="row row-cols-2">
                            <div class="col-auto">
                                <div class="input-group">
                                    <select class="form-select rounded-start-4">
                                        <option value="">
                                            12
                                        </option>
                                    </select>
                                    <div class="input-group-text">
                                        :
                                    </div>
                                    <select class="form-select">
                                        <option value="">
                                            00
                                        </option>
                                    </select>
                                    <div class="input-group-text">
                                        —
                                    </div>
                                    <select class="form-select">
                                        <option value="">
                                            12
                                        </option>
                                    </select>
                                    <div class="input-group-text">
                                        :
                                    </div>
                                    <select class="form-select rounded-end-4">
                                        <option value="">
                                            00
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-auto px-0">
                                <button class="btn btn-danger rounded-4" onclick="deleteInput(event)">
                                    <i class="bi bi-trash3-fill"></i>
                                </button>
                            </div>
                        </div>
                        <div class="row row-cols-2">
                            <div class="col-auto">
                                <div class="input-group">
                                    <select class="form-select rounded-start-4">
                                        <option value="">
                                            12
                                        </option>
                                    </select>
                                    <div class="input-group-text">
                                        :
                                    </div>
                                    <select class="form-select">
                                        <option value="">
                                            00
                                        </option>
                                    </select>
                                    <div class="input-group-text">
                                        —
                                    </div>
                                    <select class="form-select">
                                        <option value="">
                                            12
                                        </option>
                                    </select>
                                    <div class="input-group-text">
                                        :
                                    </div>
                                    <select class="form-select rounded-end-4">
                                        <option value="">
                                            00
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-auto px-0">
                                <button class="btn btn-danger rounded-4" onclick="deleteInput(event)">
                                    <i class="bi bi-trash3-fill"></i>
                                </button>
                            </div>
                        </div>
                        <div class="col">
                            <button id="add-shift-btn" class="btn btn-light rounded-4">Добавить период</button>
                        </div>
                    </div>
                    <div class="form-check ms-1 mt-3">
                        <input class="form-check-input" type="checkbox" value="" id="day-off">
                        <label class="form-check-label" for="day-off">
                            Выходной
                        </label>
                    </div>
                </div>
                <div class="modal-footer">
                    <a type="button" class="btn btn-link text-decoration-none link-secondary" data-bs-dismiss="modal">Отмена</a>
                    <button type="submit" class="btn btn-primary rounded-4">Сохранить</button>
                </div>
            </form>
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

        td {
            height: 40px;
        }

        .form-control {
            max-width: 65px;
        }

        .modal-btn {
            min-width: 80px;
        }

    </style>
    <script>
        window.lastInput = -1;
        let modal = document.getElementById('workShiftModal')
        let dayOffBtn = document.getElementById('day-off')
        let inputs = document.getElementById('workShifts')
        dayOffBtn.addEventListener('change', onChecked)
        modal.addEventListener('show.bs.modal', onShowModal)

        async function onShowModal (event) {
            let dateString = event.relatedTarget.id;
            inputs.innerHTML = `
                <div class="col">
                    <button type="button" id="add-shift-btn" class="btn btn-light rounded-4" onclick="addInput('` + dateString + `')">Добавить период</button>
                </div>
                <input type="hidden" name="date" value="` + dateString + `">
            `;

            let workShiftsModalLabel = document.getElementById('workShiftModalLabel')
            workShiftsModalLabel.innerText = 'Выберите время, когда Вы можете работать ' + dateString

            let addShiftBtn = document.getElementById ('add-shift-btn')
            addShiftBtn.addEventListener('click', addInput)

            try {
                let request = await fetch('/api/opportunities?d=' + event.relatedTarget.id)
                if (request.ok) {
                    let opportunities = await request.json();
                    for (let i = 0; i < opportunities.length; i++) {
                        let startTimeParts = opportunities[i].startTime.split(":")
                        let endTimeParts = opportunities[i].endTime.split(":")

                        let input = document.createElement ('div')
                        input.classList.add('row', 'row-cols-2' , 'col')

                        input.innerHTML=`<div class="col-auto">
                            <input type="hidden" name="opportunities[` + i + `].id" value="`+ opportunities[i].id +`">
                            <div class="input-group">
                                <input class="form-control rounded-start-4 w-0 " style="max-width: 65px;"
                                    type="number" min="0" max="23" value="` + startTimeParts[0] + `"
                                    name="opportunities[` + i + `].startTimeHour" required>
                                <div class="input-group-text">
                                    :
                                </div>
                                <input class="form-control" type="number" min="0" max="60" value="` + startTimeParts[1] + `"
                                    name="opportunities[` + i + `].startTimeMinute" required>
                                <div class="col-auto input-group-text">
                                    —
                                </div>
                                <input class="form-control" type="number" min="0" max="23" value="` + endTimeParts[0] + `"
                                    name="opportunities[` + i + `].endTimeHour" required>
                                <div class="input-group-text">
                                    :
                                </div>
                                <input class="form-control rounded-end-4" type="number" min="0" max="60" value="` + endTimeParts[1] + `"
                                    name="opportunities[` + i + `].endTimeMinute" required>
                            </div>
                        </div>
                        <div class="col-auto px-0">
                            <button type="button" class="btn btn-danger rounded-4" onclick="deleteInput(event)"><i class="bi bi-trash3-fill"></i></button>
                        </div>`;

                        let addShiftBtn = document.getElementById ('add-shift-btn')
                        inputs.insertBefore(input, addShiftBtn.parentElement)

                        window.lastInput = i;
                    }

                } else {
                    throw new Error();
                }
            } catch (e) {
                console.log(e);
            }
        }

        function onChecked () {
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

        function addInput (dateString) {
            event.preventDefault()
            let parts = dateString.toString().split('-');
            let day = Number(parts[2]);
            let month = parseInt(parts[1], 10) - 1;
            let year = parseInt(parts[0], 10);
            let date = new Date(Date.UTC(year, month, day, 0, 0, 0, 0)).toISOString()
            console.log(date)

            if (inputs.childElementCount < 4) {
                let input=document.createElement ('div')
                input.classList.add('row', 'row-cols-2' )
                window.lastInput++;
                input.innerHTML=`<div class="col-auto">
                            <div class="input-group">
                                <input class="form-control rounded-start-4 w-0 " style="max-width: 65px;"
                                    type="number" min="0" max="23" name="opportunities[` + window.lastInput + `].startTimeHour"
                                    required>
                                <div class="input-group-text">
                                    :
                                </div>
                                <input class="form-control" type="number" min="0" max="60" name="opportunities[` + window.lastInput + `].startTimeMinute"
                                    required>
                                <div class="col-auto input-group-text">
                                    —
                                </div>
                                <input class="form-control" type="number" min="0" max="23" name="opportunities[` + window.lastInput + `].endTimeHour"
                                    required>
                                <div class="input-group-text">
                                    :
                                </div>
                                <input class="form-control rounded-end-4" type="number" min="0" max="60" name="opportunities[` + window.lastInput + `].endTimeMinute"
                                    required>
                            </div>
                        </div>
                        <div class="col-auto px-0">
                            <button type="button" class="btn btn-danger rounded-4" onclick="deleteInput(event)"><i class="bi bi-trash3-fill"></i></button>
                        </div>`;

                let addShiftBtn = document.getElementById ('add-shift-btn')
                inputs.insertBefore(input, addShiftBtn.parentElement)
            }
        }

        function deleteInput (event) {
            let btn = event.currentTarget;
            btn.parentElement.parentElement.remove();
        }
    </script>
</@main.layout>