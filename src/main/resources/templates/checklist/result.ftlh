<#import "../layout.ftlh" as main>
<@main.layout>

    <main>
        <table class="table table-bordered border border-3 rounded-3">
            <thead>
            <tr>
                <th colspan="3" class="bg-warning section-header text-center">${checkList.workSchedule.pizzeria.name}</th>
                <th colspan="1" class="text-center bg-warning" class="section-header">
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
                        <td class="text-center rounded-3">
                            <input type="number" disabled class="score-input" min="0" max="${criteria.maxValue}"
                                   value="${criteria.value}" id="${criteria.id}" aria-valuemax="${criteria.maxValue}">
                        </td>
                        <#assign maxSum = maxSum + criteria.maxValue>
                    <#else>
                        <#if criteria.section == 'Критический фактор'>
                            <td class="text-center bg-danger">${criteria.section}</td>
                            <td class="bg-danger">${criteria.description}</td>
                            <td class="bg-danger"></td>
                            <td class="text-center bg-danger rounded-3">
                                <input type="number" disabled class="score-input" value="${criteria.value}" id="${criteria.id}">
                            </td>
                            <#assign critFact = critFact + 1>
                        <#elseif criteria.section == 'WOW фактор'>
                            <td class="text-center bg-success">${criteria.section}</td>
                            <td class="bg-success">${criteria.description}</td>
                            <td class="bg-success"></td>
                            <td class="text-center bg-success rounded-3">
                                <input type="number" disabled class="score-input" value="${criteria.value}" id="${criteria.id}">
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
                    <button id="calculateButton" class="btn btn-warning">Подать апелляцию</button>
                    <#if all??>
                    <#else>
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
                                    <h5 class="modal-title" id="exampleModalLabel">Вы уверены что хотите опубликовать?</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                                </div>
                                <div class="modal-body">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Не хочу публиковать</button>
                                    <button type="button" id="post" class="btn btn-primary">Уверен!</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </main>

    <script>
        let url = 'http://localhost:3445/checks/all/'
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
            a.href = url + await getUrl() + '/result'

            document.getElementById('getTemplate').addEventListener('click', () => {
                const percentage = document.getElementById('percentageResult').textContent;
                const message = `Дата: ${checkList.workSchedule.date}\n` +
                    `Пиццерия: ${checkList.workSchedule.pizzeria.name}\n` +
                    `Менеджер: ${checkList.workSchedule.manager.name}\n` +
                    `Результат проверки в %: ` + percentage +
                    `\nПоложительная обратная связь: ${checkList.feedback}\n` +
                    `Критический фактор: ${critFact}\n` +
                    `WOW фактор: ${wowFact}\n` +
                    `Поделитесь результатами проверки с командой - отправьте результаты в общий чат команды\n\n\n` +
                    a +
                    `\nПодать апелляцию`;

                navigator.clipboard.writeText(message)
                    .then(() => alert("Скопировано в буфер обмена!"))
                    .catch(err => console.error(err));
            });
        });

        document.getElementById('post').addEventListener('click', fetchToResult);

        async function getUrl() {
            try {
                let id = ${checkList.id};
                let response = await fetch(`/api/checks/` + id);
                let uuidLink = await response.text();
                return uuidLink;
            } catch (error) {
                console.error('There was a problem with the fetch operation:', error);
            }
        }

        async function fetchToResult() {
            navigator.clipboard.writeText(url + await getUrl() + '/result')
                .then(() => console.log("Скопировано в буфер обмена!"))
                .catch(err => console.error(err));
        }
    </script>

</@main.layout>
