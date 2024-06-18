<#import "../layout.ftlh" as main>
<@main.layout>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }
    </style>

    <main class="container mt-4">
        <div class="table-responsive">
            <table id="mainTable" class="table table-bordered border border-3 rounded-3">
                <thead>
                <tr>
                    <th colspan="3" class="bg-warning section-header text-center">${checkList.pizzeria.name}</th>
                    <th colspan="1" class="text-center bg-warning section-header">${checkList.manager.name} ${checkList.manager.surname}</th>
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
                            <td>${criteria.zone}</td>
                            <td>${criteria.description}</td>
                            <td class="text-center">${criteria.maxValue}</td>
                            <td class="text-center rounded-3">
                                <input type="number" class="form-control score-input" min="0" max="${criteria.maxValue}" value="${criteria.value}" id="${criteria.id}" aria-valuemax="${criteria.maxValue}">
                            </td>
                            <#assign maxSum = maxSum + criteria.maxValue>
                        <#else>
                            <#if criteria.section == 'Критический фактор'>
                                <td class="text-center bg-danger">${criteria.section}</td>
                                <td class="bg-danger">${criteria.description}</td>
                                <td class="bg-danger"></td>
                                <td class="text-center bg-danger rounded-3">
                                    <input type="number" class="form-control score-input" value="${criteria.value}" id="${criteria.id}">
                                </td>
                            <#elseif criteria.section == 'WOW фактор'>
                                <td class="text-center bg-success">${criteria.section}</td>
                                <td class="bg-success">${criteria.description}</td>
                                <td class="bg-success"></td>
                                <td class="text-center bg-success rounded-3">
                                    <input type="number" class="form-control score-input" value="${criteria.value}" id="${criteria.id}">
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
        <div class="text-end mb-5">
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
                                    <option value="${wow.description}">${wow.description}</option>
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
            const mainTableBody = document.getElementById('mainTableBody');
            let criteria = [];

            function updatePercentage() {
                let totalScore = 0;
                inputs.forEach(input => {
                    totalScore += parseFloat(input.value) || 0;
                });
                const percentage = (totalScore / maxSum) * 100;
                percentageDisplay.textContent = Math.round(percentage) + '%';
            }

            function saveScores() {
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

                fetch('/criteria/save', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(criteria)
                });
            }

            inputs.forEach(input => {
                input.addEventListener('input', updatePercentage);
            });

            calculateButton.addEventListener('click', saveScores);

            updatePercentage();

            document.getElementById('wowFactorForm').addEventListener('submit', (event) => {
                event.preventDefault();
                const description = document.getElementById('wowFactorDescription').value;

                const newRow = document.createElement('tr');
                newRow.innerHTML = `
                    <td class="text-center bg-success">WOW фактор</td>
                    <td class="bg-success">`+ description + `</td>
                    <td class="bg-success"></td>
                    <td class="text-center bg-success rounded-3">
                        <input type="number" class="form-control score-input" value="0" min="0" id="wow-">
                    </td>
                `;
                mainTableBody.appendChild(newRow);

                const wowFactorModal = new bootstrap.Modal(document.getElementById('wowFactorModal'));
                wowFactorModal.hide();

                const newInput = newRow.querySelector('.score-input');
                newInput.addEventListener('input', updatePercentage);
            });
        });
    </script>
</@main.layout>
