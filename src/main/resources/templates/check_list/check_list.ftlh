<#import "../layout.ftlh" as main>
<@main.layout>
    <main>
        <table class="table table-bordered border border-3 rounded-3">
            <thead>
            <tr>
                <th colspan="3" class="bg-warning section-header text-center">${checkList.workSchedule.pizzeria.name}</th>
                <th colspan="1" class="text-center bg-warning" class="section-header">${checkList.workSchedule.manager.name} ${checkList.workSchedule.manager.surname}</th>
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
            <#list checkList.checkListsCriteriaList as list>
                <tr>
                    <#if list.criteria.zone.name??>
                        <td class="text-center">${list.criteria.zone.name}</td>
                    <#else>
                        <td></td>
                    </#if>
                    <td>${list.criteria.description}</td>
                    <#if list.criteria.maxValue??>
                        <td class="text-center">${list.criteria.maxValue}</td>
                        <td class="text-center rounded-3">
                            <input type="number" class="score-input" min="0" max="${list.criteria.maxValue}"
                                   value="${list.criteria.maxValue}" id="${list.criteria.id}">
                        </td>
                        <#assign maxSum = maxSum + list.criteria.maxValue>
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
                <td colspan="2" class="text-center"><p><strong>(количество набранных баллов / макс. количество баллов) x 100%</strong></p>
                </td>
                <td colspan="2" class="text-center" id="percentageResult"></td>
            </tr>
            </tbody>
            <tr>
                <td colspan="4" class="text-end"><button id="calculateButton" class="btn btn-warning">Сохранить</button></td>
            </tr>
        </table>
    </main>

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

            function saveScores() {
                arr = arr.splice(0, arr.length)
                inputs.forEach(input => {
                    let scores = {
                        criteriaId: input.id,
                        value: input.value,
                        checkListId: ${checkList.id}
                    }
                    criteria.push(scores)
                });

                fetch('/criteria/save', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(criteria)
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            alert('Сохранено успешно!');
                        } else {
                            alert('Ошибка при сохранении.');
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Ошибка при сохранении.');
                    });
            }

            inputs.forEach(input => {
                input.addEventListener('input', updatePercentage);
            });

            calculateButton.addEventListener('click', saveScores);

            updatePercentage();
        });
    </script>
</@main.layout>
