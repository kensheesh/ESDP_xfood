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
    </style>

    <main class="container mt-4">
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
                <#assign maxSum = 0>
                <#list checkList.criteria as criteria>
                    <tr>
                        <#if criteria.maxValue?? && criteria.maxValue gt 0>
                            <td class="text-center">${criteria.zone}</td>
                            <td>${criteria.description}</td>
                            <td class="text-center border">${criteria.maxValue}</td>
                            <td class="text-center">
                                <input aria-label="" type="number" disabled class="text-center form-control score-input"
                                       min="0"
                                       max="${criteria.maxValue}" value="${criteria.value}" id="${criteria.id}"
                                       aria-valuemax="${criteria.maxValue}">
                            </td>
                            <td class="text-center">
                                <button class="btn btn-warning btn-sm appeal-button"
                                        data-criteria-id="${criteria.id}">Подать апелляцию
                                </button>
                            </td>
                            <#assign maxSum = maxSum + criteria.maxValue>
                        <#else>
                            <#if criteria.section == 'Критический фактор'>
                                <td class="text-center bg-danger">${criteria.section}</td>
                                <td class="bg-danger">${criteria.description}</td>
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
    </main>

    <script>
        document.addEventListener('DOMContentLoaded', async () => {
            const maxSum = ${maxSum};
            const percentageDisplay = document.getElementById('percentageResult');

            function updatePercentage() {
                let totalScore = 0;
                document.querySelectorAll('.score-input').forEach(input => {
                    totalScore += parseFloat(input.value) || 0;
                });
                let percentage = (totalScore / maxSum) * 100;

                if (percentage < 100) {
                    document.querySelectorAll('.score-wow-input').forEach(input => {
                        totalScore += parseFloat(input.value) || 0;
                    })
                    percentage = Math.min((totalScore / maxSum) * 100, 100)
                }

                percentageDisplay.textContent = Math.round(percentage) + '%';
            }

            updatePercentage()

            console.log("Сборка кнопок")
            const appealButtons = document.querySelectorAll('.appeal-button');
            appealButtons.forEach(button => {
                button.addEventListener('click', async () => {
                    const criteria = button.getAttribute('data-criteria-id');
                    fetchToAppeal(criteria)
                });
            });

            function fetchToAppeal(criteria) {
                window.location.href = "/appeal?criteriaId=" + criteria + "&checkListId=${checkList.uuidLink}"
            }

        })
    </script>

</@main.layout>
