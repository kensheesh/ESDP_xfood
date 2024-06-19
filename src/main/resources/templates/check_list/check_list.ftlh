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
        }

        .delete-factor {
            background-color: transparent;
            border: none;
            color: red;
            cursor: pointer;
        }

        .delete-factor:hover {
            text-decoration: underline;
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
                    <th class="text-center">${checkList.managerWorkDate}</th>
                </tr>
                </thead>
                <tbody id="mainTableBody">
                <#assign maxSum = 0>
                <#list checkList.criteria as criteria>
                    <tr>
                        <#if criteria.maxValue?? && criteria.maxValue gt 0>
                            <td class="text-center">${criteria.zone}</td>
                            <td>${criteria.description}</td>
                            <td class="text-center">${criteria.maxValue}</td>
                            <td class="text-center">
                                <input type="number" class="text-center form-control score-input" min="0" max="${criteria.maxValue}" value="${criteria.value}" id="${criteria.id}" aria-valuemax="${criteria.maxValue}">
                            </td>
                            <#assign maxSum = maxSum + criteria.maxValue>
                        <#else>
                            <#if criteria.section == 'Критический фактор'>
                                <td class="text-center bg-danger">${criteria.section}</td>
                                <td class="bg-danger">${criteria.description}</td>
                                <td class="bg-danger"></td>
                                <td class="text-center bg-danger d-flex">
                                    <input type="number" class="text-center form-control score-input" value="${criteria.value}" id="${criteria.id}">
                                    <button class="delete-factor" data-id="${criteria.id}">Delete</button>
                                </td>
                            <#elseif criteria.section == 'WOW фактор'>
                                <td class="text-center bg-success">${criteria.section}</td>
                                <td class="bg-success">${criteria.description}</td>
                                <td class="bg-success"></td>
                                <td class="text-center bg-success d-flex">
                                    <input type="number" class="text-center form-control score-input" min="0" value="${criteria.value}" id="${criteria.id}">
                                    <button class="delete-factor" data-id="${criteria.id}">Delete</button>
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
                    <td colspan="2" class="text-center"><strong>(количество набранных баллов / макс. количество баллов) x 100%</strong></td>
                    <td colspan="2" class="text-center" id="percentageResult"></td>
                </tr>
                </tfoot>
            </table>
        </div>
        <div class="text-end mb-5 mt-2">
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#wowFactorModal">Добавить wow-фактор</button>
            <button class="btn btn-danger">Добавить критический фактор</button>
            <button id="calculateButton" class="btn btn-warning">Сохранить</button>
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
                            <select class="form-select form-select-sm" id="wowFactorDescription" aria-label=".form-select-sm example">
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
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const inputs = document.querySelectorAll('.score-input');
            const maxSum = ${maxSum};
            const percentageDisplay = document.getElementById('percentageResult');
            const calculateButton = document.getElementById('calculateButton');
            let criteria = [];

            function updatePercentage() {
                let totalScore = 0;
                inputs.forEach(input => {
                    totalScore += parseFloat(input.value) || 0;
                });
                const percentage = (totalScore / maxSum) * 100;
                percentageDisplay.textContent = Math.round(percentage) + '%';
            }

            async function saveScores() {
                criteria = [];
                inputs.forEach(input => {
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

            async function deleteFactor(event) {
                const button = event.target;
                const row = button.closest('tr');
                const criteriaId = button.getAttribute('data-id');

                await fetch(`/api/checklist/criteria/wow/delete/` + criteriaId + `?checkListId=` + ${checkList.id}, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(response => {
                    if (response.ok) {
                        window.location.reload()
                    } else {
                        console.error('Ошибка при удалении фактора');
                    }
                });
            }

            inputs.forEach(input => {
                input.addEventListener('input', updatePercentage);
            });

            calculateButton.addEventListener('click', saveScores);

            updatePercentage();

            document.getElementById('wowFactorForm').addEventListener('submit', async (event) => {
                event.preventDefault();
                const descriptionId = document.getElementById('wowFactorDescription').value;

                const object = {
                    criteriaId: descriptionId,
                    value: 0,
                    checkListId: ${checkList.id},
                    maxValue: 0
                };

                const response = await fetch('/api/checklist/criteria/create/wow', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(object)
                });

                const newCriteria = await response.json();

                if(newCriteria.criteria !== undefined) {
                    await saveScores()
                    window.location.reload()
                    window.location.reload()
                }
            });

            document.querySelectorAll('.delete-factor').forEach(button => {
                button.addEventListener('click', deleteFactor);
            });
        });
    </script>

</@main.layout>