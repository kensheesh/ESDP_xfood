<#import "../layout.ftlh" as main>
<@main.layout>

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

        .form-control {
            width: auto;
            display: inline-block;
            margin-right: 0.5rem;
        }

        .appeal-button {
            display: none;
            margin-top: 0.5rem;
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
        }

        .table tbody tr:hover .appeal-button {
            display: inline-block;
        }

        .error-message {
            color: white;
            background-color: #dc3545;
            padding: 10px;
            border-radius: 5px;
            font-weight: bold;
            text-align: center;
        }

        .appeal-submitted {
            float: right;
            margin-left: 5px;
            color: #28a745;
            font-weight: bold;
            font-size: 0.875rem;
        }

        .notification {
            background-color: #f1f1f1;
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            text-align: center;
            font-size: 1rem;
        }
    </style>

    <main class="container mt-4">
        <#if error??>
            <div class="error-message">${error}</div>
        <#else>
            <div class="notification">
                ✔ - Данная галочка рядом с критерием означает, что на данный критерий подана аппеляция, которую еще не
                рассмотрели
            </div>
            <div class="table-responsive rounded p-3">
                <table id="mainTable" class="table rounded-3">
                    <thead>
                    <tr>
                        <th colspan="3" class="text-center">${checkList.pizzeria.name}</th>
                        <th colspan="2" class="text-center">${checkList.manager.name} ${checkList.manager.surname}</th>
                    </tr>
                    <tr>
                        <th class="text-center">Раздел</th>
                        <th class="text-center">Что проверить</th>
                        <th class="text-center">Оценка</th>
                        <th class="text-center">${checkList.managerWorkStartDate}</th>
                        <th class="text-center">Действия</th>
                    </tr>
                    </thead>
                    <tbody id="mainTableBody">
                    <#list checkList.criteria as criteria>
                        <tr>
                            <#if criteria.maxValue?? && criteria.maxValue gt 0>
                                <td class="text-center">${criteria.zone}</td>
                                <td class="criteria-description-${criteria.id}">
                                    ${criteria.description}
                                </td>
                                <td class="text-center border">${criteria.maxValue}</td>
                                <td class="text-center">
                                    <input aria-label="" type="number" disabled
                                           class="text-center form-control score-input"
                                           min="0"
                                           max="${criteria.maxValue}" value="${criteria.value}" id="${criteria.id}"
                                           aria-valuemax="${criteria.maxValue}">
                                </td>
                                <td class="text-center">
                                    <button class="btn btn-warning btn-sm appeal-button"
                                            data-criteria-id="${criteria.id}">Подать апелляцию
                                    </button>
                                    <#if criteria.isAccepted?? && true>
                                        <span class="appeal-submitted">✔</span>
                                    </#if>
                                </td>
                            <#else>
                                <#if criteria.section == 'Критический фактор'>
                                    <td class="text-center bg-danger">${criteria.section}</td>
                                    <td class="bg-danger">
                                        <div class="accordion" id="accordion-${criteria.id}">
                                            <div class="accordion-item">
                                                <h2 class="accordion-header" id="heading-${criteria.id}">
                                                    <button class="accordion-button" type="button"
                                                            data-bs-toggle="collapse"
                                                            data-bs-target="#collapse-${criteria.id}"
                                                            aria-expanded="true"
                                                            aria-controls="#collapse-${criteria.id}">
                                                        ${criteria.description}
                                                    </button>
                                                </h2>
                                                <div id="collapse-${criteria.id}"
                                                     class="accordion-collapse collapse show"
                                                     aria-labelledby="heading-${criteria.id}"
                                                     data-bs-parent="#accordion-${criteria.id}">
                                                    <div class="accordion-body">
                                                        <strong>${criteria.description}.</strong>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="bg-danger"></td>
                                    <td class="text-center bg-danger">
                                        <input aria-label="" type="number" disabled
                                               class="text-center form-control score-input"
                                               value="${criteria.value}" id="${criteria.id}"/>
                                    </td>
                                    <td class="text-center">
                                        <button class="btn btn-warning btn-sm appeal-button"
                                                data-criteria-id="${criteria.id}">Подать апелляцию
                                        </button>
                                        <#if criteria.isAccepted?? && true>
                                            <span class="appeal-submitted">✔</span>
                                        </#if>
                                    </td>
                                <#elseif criteria.section == 'WOW фактор'>
                                    <td class="text-center bg-success">${criteria.section}</td>
                                    <td class="bg-success">${criteria.description}</td>
                                    <td class="bg-success"></td>
                                    <td class="text-center bg-success">
                                        <input aria-label="" type="number" disabled
                                               class="text-center form-control score-wow-input"
                                               min="0"
                                               value="${criteria.value}" id="${criteria.id}"/>
                                    </td>
                                    <td class="text-center">
                                        <#if criteria.isAccepted?? && true>
                                            <span class="appeal-submitted">✔</span>
                                        </#if>
                                    </td>
                                </#if>
                            </#if>
                        </tr>
                    </#list>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="2" class="text-center"><strong>Максимальное количество баллов: </strong></td>
                        <td colspan="2" class="text-center"><strong id="maxPoint"></strong></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="text-center"><strong>(количество набранных баллов / макс. количество
                                баллов)
                                x 100%</strong></td>
                        <td colspan="2" class="text-center" id="percentageResult"></td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </#if>
    </main>

    <script>
        document.addEventListener('DOMContentLoaded', async () => {
            const maxPoint = document.getElementById('maxPoint')
            const percentageDisplay = document.getElementById('percentageResult');

            async function showResultPercentage() {
                let response = await fetch('/api/checklist/criteria/percentage/${checkList.id}')
                let data = await response.json()
                percentageDisplay.textContent = data + '%';
                console.log('Сделан запрос' + data)
                return data;
            }

            async function showMaxPoint() {
                let response = await fetch('/api/checks/${checkList.id}/points')
                let data = await response.json()
                console.log(data)
                maxPoint.innerText = data
            }

            await showResultPercentage()
            await showMaxPoint()

            const appealButtons = document.querySelectorAll('.appeal-button');

            appealButtons.forEach(button => {
                button.addEventListener('click', async () => {
                    const criteria = button.getAttribute('data-criteria-id');
                    await fetchToAppeal(criteria)
                });
            });

            appealButtons.forEach(button => {
                const criteria = button.getAttribute('data-criteria-id');
                addDynamicContent(criteria);
            });

            async function fetchToAppeal(criteria) {
                let object = buildData(criteria);
                console.log(object)
                let response = await fetch('/api/appeal', {
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(object)
                })

                let data = await response.json()
                window.location.href = '/appeals/' + data
            }

            function buildData(criteria) {
                return {
                    criteriaId: criteria,
                    checkListId: ${checkList.id}
                }
            }

            async function getAppeals(criteriaId){
                if(criteriaId !== null) {
                    let response = await fetch('/api/appeal/accepted/${checkList.id}/'+criteriaId)
                    return await response.json();
                }
                return []
            }
            async function addDynamicContent(criteriaId) {
                let appeals = await getAppeals(criteriaId)
                console.log(appeals);
                const description = document.getElementById(`criteria-description-` + criteriaId);
                if(appeals.length === 0) {
                    description.innerText = `
                   Нет принятых аппеляций
                `;
                } else {
                    for (let i = 0; i < appeals.length; i++) {
                        let id = appeals[i].id
                        description.innerHTML = `
                        <div class="accordion" id="accordion-`+ criteriaId +`">
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="heading-`+ criteriaId +`">
                                                <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                                        data-bs-target="#collapse-`+ criteriaId +`" aria-expanded="true"
                                                        aria-controls="#collapse-`+ criteriaId +`">`+ appeals[i].criteriaDescription +`</button>
                                            </h2>
                                            <div id="collapse-`+ criteriaId +`" class="accordion-collapse collapse"
                                                 aria-labelledby="heading-`+ criteriaId +`"
                                                <div class="accordion-body">
                                                    <a href="/appeals/`+ appeals[i].id +`/approve">Аппеляция</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                        `
                    }
                }
            }

        })
    </script>

</@main.layout>