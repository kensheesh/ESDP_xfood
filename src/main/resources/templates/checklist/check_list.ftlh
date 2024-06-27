<#import "../layout.ftlh" as main>
<@main.layout ; spting>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }

        .table-responsive {
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .table th, .table td {
            vertical-align: middle;
        }

        .table thead th {
            background-color: #ffc107;
            color: #343a40;
            font-weight: bold;
        }

        .table tbody tr:nth-child(odd) {
            background-color: #f8f9fa;
        }

        .bg-danger, .bg-success {
            color: white;
            font-weight: bold;
            padding: 0.75rem;
        }

        .bg-danger {
            background-color: #dc3545;
        }

        .bg-success {
            background-color: #28a745;
        }

        .delete-factor {
            background-color: transparent;
            border: none;
            color: black;
            cursor: pointer;
            font-size: 1.2rem;
            margin-left: 0.5rem;
        }

        .delete-factor:hover {
            text-decoration: underline;
        }

        .form-control {
            width: auto;
            display: inline-block;
            margin-right: 0.5rem;
        }
    </style>

    <main class="container mt-4">
        <div class="table-responsive rounded p-3">
            <table id="mainTable" class="table rounded-3">
                <thead>
                <tr>
                    <th colspan="3" class="text-center">${checkList.pizzeria.name}</th>
                    <th colspan="1" class="text-center">${checkList.manager.name} ${checkList.manager.surname}</th>
                </tr>
                <tr>
                    <th class="text-center">Раздел</th>
                    <th class="text-center">Что проверить</th>
                    <th class="text-center">Оценка</th>
                    <th class="text-center">${checkList.managerWorkStartDate}</th>
                </tr>
                </thead>
                <tbody id="mainTableBody">
                <#assign maxSum = 0>
                <#list checkList.criteria as criteria>
                    <tr>
                        <#if criteria.maxValue?? && criteria.maxValue gt 0>
                            <td class="text-center">${criteria.zone}</td>
                            <td>${criteria.description}</td>
                            <td class="text-center border">${criteria.maxValue}</td>
                            <td class="text-center">
                                <input type="number" step="${criteria.maxValue}" class="text-center form-control score-input"
                                       max="${criteria.maxValue}" value="${criteria.value}" id="${criteria.id}"
                                       aria-valuemax="${criteria.maxValue}">
                            </td>
                            <#assign maxSum = maxSum + criteria.maxValue>
                        <#else>
                            <#if criteria.section == 'Критический фактор'>
                                <td class="text-center bg-danger">${criteria.section}</td>
                                <td class="bg-danger">${criteria.description}</td>
                                <td class="bg-danger"></td>
                                <td class="text-center bg-danger">
                                    <div class="input-group">
                                        <input type="number" class="text-center form-control score-input"
                                               value="${criteria.value}" id="${criteria.id}">
                                        <div class="input-group-append">
                                            <button class="delete-factor btn btn-outline-danger"
                                                    data-id="${criteria.id}">&times;
                                            </button>
                                        </div>
                                    </div>
                                </td>
                            <#elseif criteria.section == 'WOW фактор'>
                                <td class="text-center bg-success">${criteria.section}</td>
                                <td class="bg-success">${criteria.description}</td>
                                <td class="bg-success"></td>
                                <td class="text-center bg-success">
                                    <div class="input-group">
                                        <input type="number"  class="text-center form-control score-wow-input" min="0"
                                               value="${criteria.value}" id="${criteria.id}">
                                        <div class="input-group-append">
                                            <button class="delete-factor" data-id="${criteria.id}">&times;</button>
                                        </div>
                                    </div>
                                </td>
                            </#if>
                        </#if>
                    </tr>
                </#list>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="2" class="text-center"><strong>Максимальное количество баллов: </strong></td>
                    <td colspan="2" class="text-center"><strong>${maxSum}</strong></td>
                </tr>
                <tr>
                    <td colspan="2" class="text-center"><strong>(количество набранных баллов / макс. количество баллов)
                            x 100%</strong></td>
                    <td colspan="2" class="text-center" id="percentageResult"></td>
                </tr>
                </tfoot>
            </table>
        </div>
        <div class="form-group m-3 shadow-sm">
            <label for="expertStartTime" class="form-label">Общее время, потрачееное на проверку:</label><br>
            <input type="time" id="duration" class="form-control" name="duration"  required>
        </div>
        <div class="text-end mb-5 mt-2">
            <#assign
            known = SPRING_SECURITY_CONTEXT??
            >
            <#if known>
                <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>
                <#assign name = user.getUsername()>
                <#assign authorities = SPRING_SECURITY_CONTEXT.authentication.authorities>

                <#list authorities as authority>
                    <#if authority.authority == 'ROLE_SUPERVISOR'>
                            <a href="/checks/${checkList.uuidLink}/update" class="btn btn-success"  role="link" >Редактировать </a>
                    </#if>
                </#list>
            </#if>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#wowFactorModal">Добавить
                wow-фактор
            </button>
            <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#critFactorModal">Добавить критический
                фактор
            </button>
            <button id="calculateButton" class="btn btn-warning">Сохранить</button>

            <#if checkList.status == 'IN_PROGRESS'>
                <button id="getTemplate" class="btn btn-warning">Получить шаблон сообщение</button>
                <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#exampleModal">
                    Опубликовать
                </button>
            </#if>

            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Вы уверены, что хотите опубликовать?</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Закрыть"></button>
                        </div>
                        <div class="modal-body">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Не хочу
                                публиковать
                            </button>
                            <button type="button" id="post" class="btn btn-primary">Уверен!</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <div class="modal fade" id="wowFactorModal" tabindex="-1" aria-labelledby="wowFactorModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="wowFactorModalLabel">Добавить WOW-фактор</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="wowFactorForm">
                        <div class="mb-3">
                            <label for="wowFactorDescription" class="form-label">Описание</label>
                            <select class="form-select form-select-sm" id="wowFactorDescription"
                                    aria-label=".form-select-sm example">
                                <#list wowCriteria as wow>
                                    <option value="${wow.id}">${wow.description}</option>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Добавить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="critFactorModal" tabindex="-1" aria-labelledby="critwowFactorModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="wowFactorModalLabel">Добавить критический фактор</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="critFactorForm">
                        <div class="mb-3">
                            <label for="critFactorDescription" class="form-label">Описание</label>
                            <select class="form-select form-select-sm" id="critFactorDescription"
                                    aria-label=".form-select-sm example">
                                <#list critCriteria as crit>
                                    <option value="${crit.id}">${crit.description}</option>
                                </#list>
                                <option value="other">Другой</option>
                            </select>
                        </div>
                        <div class="mb-3" id="otherDescriptionDiv" style="display: none;">
                            <label for="otherDescription" class="form-label">Введите описание</label>
                            <input type="text" class="form-control" id="otherDescription" name="otherDescription"/>
                        </div>
                        <button type="submit" id="addCritFactor" class="btn btn-primary">Добавить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script>
        document.addEventListener('DOMContentLoaded', async () => {
            const maxSum = ${maxSum};
            const percentageDisplay = document.getElementById('percentageResult');
            const calculateButton = document.getElementById('calculateButton');
            let criteria = [];

            async function updatePercentage() {
                let totalScore = 0;
                document.querySelectorAll('.score-input').forEach(input => {
                    totalScore += parseFloat(input.value) || 0;
                });

                await fetch('/api/checks/change/status/' + ${checkList.id}, {
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                })

                let percentage = (totalScore / maxSum) * 100;

                if (percentage < 100) {
                    document.querySelectorAll('.score-wow-input').forEach(input => {
                        totalScore += parseFloat(input.value) || 0;
                    })
                    percentage = Math.min((totalScore / maxSum) * 100, 100)
                }

                percentageDisplay.textContent = Math.round(percentage) + '%';
            }

            await updatePercentage()

            async function saveScores() {
                criteria = [];
                document.querySelectorAll('.score-input').forEach(input => {
                    let scores = {
                        criteriaId: input.id,
                        value: input.value,
                        checkListId: ${checkList.id},
                        maxValue: input.getAttribute('aria-valuemax')
                    };
                    criteria.push(scores);
                });

                await fetch('/criteria/save', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(criteria)
                });
            }

            async function fetchToCreate(url, object) {
                const response = await fetch(url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(object)
                });

                return response;
            }

            async function createNewElement(factor, typeClass, newCriteria) {
                if (newCriteria.criteria !== undefined) {
                    const newRow = document.createElement('tr');
                    newRow.innerHTML = `
                <td class="text-center bg-` + typeClass + `">factor фактор</td>
                <td class="bg-` + typeClass + `">` + newCriteria.criteria + `</td>
                <td class="bg-` + typeClass + `"></td>
                <td class="text-center bg-` + typeClass + ` d-flex">
                    <input type="number" class="text-center form-control score-input" min="0" value="0" id="` + newCriteria.id + `">
                    <button class="delete-factor" data-id=<div class="input-group">
                                        <input type="number" class="text-center form-control score-input" min="0" value="0" id="` + newCriteria.id + `">
                                        <div class="input-group-append">
                                            <button class="delete-factor" data-id="` + newCriteria.id + `">&times;</button>
                                        </div>
                                    </div>
                </td>
            `;

                    document.getElementById('mainTableBody').appendChild(newRow);

                    const newInput = newRow.querySelector('.score-input');
                    newInput.addEventListener('input', updatePercentage);

                    const newDeleteButton = newRow.querySelector('.delete-factor');
                    newDeleteButton.addEventListener('click', await deleteFactor);

                    await updatePercentage();
                }
            }

            async function deleteFactor(event) {
                const button = event.target;
                const row = button.closest('tr');
                const criteriaId = button.getAttribute('data-id');

                await fetch(`/api/checklist/criteria/delete/` + criteriaId + `?checkListId=` + ${checkList.id}, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(response => {
                    if (response.ok) {
                        row.remove();
                        updatePercentage();
                    } else {
                        console.error('Ошибка при удалении фактора');
                    }
                });
            }

            document.querySelectorAll('.score-input').forEach(input => {
                input.addEventListener('input', updatePercentage);
            });

            calculateButton.addEventListener('click', saveScores);

            document.getElementById('wowFactorForm').addEventListener('submit', async (event) => {
                event.preventDefault();
                const descriptionId = document.getElementById('wowFactorDescription').value;

                const object = {
                    criteriaId: descriptionId,
                    value: 0,
                    checkListId: ${checkList.id},
                    maxValue: 0
                };

                let response = await fetchToCreate('/api/checklist/criteria/create/wow', object)
                const newCriteria = await response.json();
                await createNewElement('WOW', 'success', newCriteria)

            });

            document.getElementById('critFactorDescription').addEventListener('change', function () {
                let selectedValue = this.value;
                let otherDescriptionDiv = document.getElementById('otherDescriptionDiv');
                if (selectedValue === 'other') {
                    otherDescriptionDiv.style.display = 'block';
                } else {
                    otherDescriptionDiv.style.display = 'none';
                }
            });

            document.getElementById('critFactorForm').addEventListener('submit', async (event) => {
                event.preventDefault();
                const selectedValue = document.getElementById('critFactorDescription').value;
                let description;

                if (selectedValue === 'other') {
                    description = document.getElementById('otherDescription').value;
                } else {
                    description = selectedValue;
                }

                const object = {
                    value: 0,
                    checkListId: ${checkList.id},
                    maxValue: 0
                };

                let response = fetchToCreate('/api/checklist/criteria/create/crit?description=' + description, object)
                const newCriteria = await response.json();
                await createNewElement('Критический', 'danger', newCriteria)
            });

            document.querySelectorAll('.delete-factor').forEach(button => {
                button.addEventListener('click', deleteFactor);
            });


            let uuidLink
            let url = 'http://localhost:2345/checks/'

            async function getUrl() {
                try {
                    let response = await fetch(`/api/checks/${checkList.id}`);
                    uuidLink = await response.text();
                } catch (error) {
                    console.error('There was a problem with the fetch operation:', error);
                }
            }

            await updatePercentage();
            let a = document.createElement('a')
            await getUrl()
            a.href = url + uuidLink + '/result'

            document.getElementById('getTemplate').addEventListener('click', () => {
                const percentage = document.getElementById('percentageResult').textContent;
                const message = `Дата: ${checkList.managerWorkStartDate}\n` +
                    `Пиццерия: ${checkList.pizzeria.name}\n` +
                    `Менеджер: ${checkList.manager.name} ${checkList.manager.surname}\n` +
                    `Результат проверки в %: ` + percentage +
                    `\nПоложительная обратная связь: \n` +
                    `Критический фактор: \n` +
                    `WOW фактор: \n` +
                    `Поделитесь результатами проверки с командой - отправьте результаты в общий чат команды\n\n\n` +
                    a;

                navigator.clipboard.writeText(message)
                    .then(() => alert("Скопировано в буфер обмена!"))
                    .catch(err => console.error(err));
            });

            document.getElementById('post').addEventListener('click', fetchToResult);

            async function fetchToResult() {
                let duration = document.getElementById('duration').value
                let uuid = `${checkList.uuidLink}`
                if (!duration){
                    console.log("dasda")
                }
                console.log(duration)
                try {
                    let response = await fetch('/api/checks/post/'+uuid+'/'+duration, {method: 'POST'});
                    if (!response.ok) {
                        let errorMessage = await response.text();
                        throw new Error(errorMessage);
                    }
                    console.log(done)
                    await getUrl();
                    navigator.clipboard.writeText(url + uuidLink + '/result')
                        .then(() => console.log("Скопировано в буфер обмена!"))
                        .catch(err => console.error(err));
                } catch (error) {
                    alert(error.message)
                }
            }
        });
    </script>

</@main.layout>