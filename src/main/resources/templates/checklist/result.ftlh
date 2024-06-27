<#import "../layout.ftlh" as main>
<@main.layout>

    <div class="card border-0 shadow-sm rounded-4">
        <div class="card-body">
            <table class="table rounded-3">
                <thead>
                <tr>
                    <th colspan="3" class="section-header text-center fs-5">${checkList.workSchedule.pizzeria.name}</th>
                    <th colspan="1" class="text-center" class="section-header">
                        ${checkList.workSchedule.manager.name} ${checkList.workSchedule.manager.surname}
                    </th>
                </tr>
                <#assign maxSum = 0>
                <tr>
                    <th class="text-center">Раздел</th>
                    <th class="text-center">Что проверить</th>
                    <th class="text-center">Оценка</th>
                    <th class="text-center">${checkList.workSchedule.date}</th>
                </tr>
                </thead>
                <tbody>
                <#assign critFact = 0>
                <#assign wowFact = 0>
                <#list checkList.criteria as criteria>
                    <tr>
                        <#if criteria.maxValue?? && criteria.maxValue gt 0>
                            <td>${criteria.zone}</td>
                            <td>${criteria.description}</td>
                            <td class="text-center">${criteria.maxValue}</td>
                            <td class="text-center">
                                <input type="number" disabled class="score-input form-control" min="0" max="${criteria.maxValue}"
                                       value="${criteria.value}" id="${criteria.id}" aria-valuemax="${criteria.maxValue}">
                            </td>
                            <#assign maxSum = maxSum + criteria.maxValue>
                        <#else>
                            <#if criteria.section == 'Критический фактор'>
                                <td class="text-center table-danger">${criteria.section}</td>
                                <td class="table-danger">${criteria.description}</td>
                                <td class="table-danger"></td>
                                <td class="text-center table-danger">
                                    <input type="number" disabled class="score-input form-control" value="${criteria.value}" id="${criteria.id}">
                                </td>
                                <#assign critFact = critFact + 1>
                            <#elseif criteria.section == 'WOW фактор'>
                                <td class="text-center table-success">${criteria.section}</td>
                                <td class="table-success">${criteria.description}</td>
                                <td class="table-success"></td>
                                <td class="text-center table-success">
                                    <input type="number" disabled class="score-input form-control" value="${criteria.value}" id="${criteria.id}">
                                </td>
                                <#assign wowFact = wowFact + 1>
                            </#if>
                        </#if>
                    </tr>
                </#list>
                <tr>
                    <td colspan="2" class="text-center"><p><strong>Максимальное количество баллов: </strong></p></td>
                    <td colspan="2" class="text-center"><p><strong>${maxSum}</strong></p></td>
                </tr>
                <tr>
                    <td colspan="2" class="text-center"><p><strong>(количество набранных баллов / макс. количество баллов) x
                                100%</strong></p></td>
                    <td colspan="2" class="text-center" id="percentageResult"></td>
                </tr>
                </tbody>

                <tr>
                    <td colspan="4" class="text-end">
                        <button id="calculateButton" class="btn btn-dodo rounded-4">Подать апелляцию</button>
                        <#if all??>
                        <#else>
                            <button id="getTemplate" class="btn btn-dodo rounded-4">Получить шаблон сообщение</button>
                            <button type="button" class="btn btn-dodo rounded-4" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                Опубликовать
                            </button>
                        </#if>

                        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content rounded-4">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Вы уверены, что хотите опубликовать?</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                                    </div>
                                    <div class="modal-body">
                                        <button type="button" class="btn btn-link rounded-4 text-decoration-none link-secondary" data-bs-dismiss="modal">Не хочу публиковать</button>
                                        <button type="button" id="post" class="btn btn-dodo rounded-4">Уверен!</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <script>
        let uuidLink;
        let url = 'http://localhost:2345/checks/all/'
        document.addEventListener('DOMContentLoaded', async () => {
            const inputs = document.querySelectorAll('.score-input');
            const maxSum = ${maxSum};
            const percentageDisplay = document.getElementById('percentageResult');

            function updatePercentage() {
                let totalScore = 0;
                inputs.forEach(input => {
                    totalScore += parseFloat(input.value) || 0;
                });
                const percentage = (totalScore / maxSum) * 100;
                percentageDisplay.textContent = Math.round(percentage) + '%';
            }

            updatePercentage();
            let a = document.createElement('a');
            await getUrl()
            a.href = url + uuidLink + '/result'

            document.getElementById('getTemplate').addEventListener('click', () => {
                const percentage = document.getElementById('percentageResult').textContent;
                const message = `Дата: ${checkList.workSchedule.date}\n` +
                    `Пиццерия: ${checkList.workSchedule.pizzeria.name}\n` +
                    `Менеджер: ${checkList.workSchedule.manager.name} ${checkList.workSchedule.manager.surname}\n` +
                    `Результат проверки в %: ` + percentage +
                    `\nПоложительная обратная связь: \n` +
                    `Критический фактор: ${critFact}\n` +
                    `WOW фактор: ${wowFact}\n` +
                    `Поделитесь результатами проверки с командой - отправьте результаты в общий чат команды\n\n\n` +
                    a;

                navigator.clipboard.writeText(message)
                    .then(() => alert("Скопировано в буфер обмена!"))
                    .catch(err => console.error(err));
            });
        });

        document.getElementById('post').addEventListener('click', fetchToResult);

        async function getUrl() {
            try {
                let response = await fetch(`/api/checks/${checkList.id}`);
                uuidLink = await response.text();
            } catch (error) {
                console.error('There was a problem with the fetch operation:', error);
            }
        }

        document.getElementById('post').addEventListener('click', fetchToResult);

        async function fetchToResult() {
            try {
                let response = await fetch(`/api/checks/post/${checkList.id}`, { method: 'POST' });
                if (!response.ok) {
                    let errorMessage = await response.text();
                    throw new Error(errorMessage);
                }
                await getUrl();
                navigator.clipboard.writeText(url + uuidLink + '/result')
                    .then(() => console.log("Скопировано в буфер обмена!"))
                    .catch(err => console.error(err));
            } catch (error) {
                alert(error.message)
            }
        }


    </script>

</@main.layout>
