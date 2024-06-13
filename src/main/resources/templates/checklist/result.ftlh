<#import "../layout.ftlh" as main>
<@main.layout>

    <main>
        <table class="table table-bordered border border-3 rounded-3">
            <thead>
            <tr>
                <th colspan="3"
                    class="bg-warning section-header text-center">${checkList.workSchedule.pizzeria.name}</th>
                <th colspan="1" class="text-center bg-warning"
                    class="section-header">${checkList.workSchedule.manager.name} ${checkList.workSchedule.manager.surname}</th>
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
            <#list checkList.criteria as criterion>
                <tr>
                    <#if criterion.zone??>
                        <td>${criterion.zone}</td>
                    <#else>
                        <td></td>
                    </#if>
                    <td>${criterion.description}</td>
                    <#if criterion.maxValue??>
                        <td class="text-center">${criterion.maxValue}</td>
                        <td class="text-center rounded-3"><input type="number" class="score-input" min="0"
                                                                 max="${criterion.maxValue}"
                                                                 value="${criterion.value}" disabled></td>
                        <#assign maxSum = maxSum + criterion.maxValue>
                    <#else>
                        <td></td>
                    </#if>
                </tr>
            </#list>
            <tr>
                <td colspan="2" class="text-center"><p><strong>Максимальное количество баллов: </strong></p></td>
                <td colspan="2" class="text-center"><p><strong>${maxSum}</strong></p></td>
            </tr>
            <tr>
                <td colspan="2" class="text-center"><p><strong>(количество набранных баллов / макс. количество баллов) x
                            100%</strong></p>
                </td>
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
                                    <h5 class="modal-title" id="exampleModalLabel">Вы уверены что хотите
                                        опубликовать?</h5>
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
                </td>
            </tr>
        </table>
    </main>

    <script>

        document.addEventListener('DOMContentLoaded', () => {
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

            inputs.forEach(input => {
                input.addEventListener('input', updatePercentage);
            });

            updatePercentage();
        });

        let percentage = document.getElementById('percentageResult').textContent;
        console.log(percentage)
        let message = `Дата: ${checkList.workSchedule.date}\n` +
            `Пиццерия: ${checkList.workSchedule.pizzeria.name}\n` +
            `Менеджер: ${checkList.workSchedule.manager.name}\n` +
            `Результат проверки в %: ` + percentage +
            `\nПоложительная обратная связь: ` +
            `\nКритический фактор: ` +
            `\nWOW фактор: ` +
            `\nПоделитесь результатами проверки с командой - отправьте результаты в общий чат команды`

        document.getElementById('getTemplate').addEventListener('click', () => {
            navigator.clipboard.writeText(message)
                .then(() => console.log("Done!"))
                .catch(err => console.error(err))
        })

        document.getElementById('post').addEventListener('click', async () => {
            try {
                let id = ${checkList.id};

                let response = await fetch(`/api/checks/` + id);
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }

                let uuidLink = await response.text();
                console.log(uuidLink);


                    navigator.clipboard.writeText('http://localhost:3445/checks/all/' + uuidLink + '/result')
                        .then(() => console.log("Done!"))
                        .catch(err => console.error(err))


            } catch (error) {
                console.error('There was a problem with the fetch operation:', error);
            }
        });



    </script>

</@main.layout>