<#import "../layout.ftlh" as main>
<@main.layout>

    <main>
        <table class="table table-bordered border border-3">
            <thead>
            <tr>
                <th colspan="3" class="section-header text-center">${checkList.workSchedule.pizzeria.name}</th>
                <th colspan="1" class="section-header">${checkList.workSchedule.manager.name} ${checkList.workSchedule.manager.surname}</th>
            </tr>
            <#assign maxSum = 0>
            <tr>
                <th>Раздел</th>
                <th>Что проверить</th>
                <th>Оценка</th>
                <th>05.06.2024</th>
            </tr>
            </thead>
            <tbody>
            <#list checkList.checkListsCriteriaList as list>

                <tr>
                    <#if list.criteria.zone.name??>
                        <td>${list.criteria.zone.name}</td>
                    <#else>
                        <td></td>
                    </#if>

                    <td>${list.criteria.description}</td>
                    <#if list.criteria.maxValue??>
                        <td>${list.criteria.maxValue}</td>
                        <#assign maxSum = maxSum + list.criteria.maxValue>
                        <#else>
                            <td></td>
                    </#if>

                    <td><input type="number" name=""></td>
                </tr>

            </#list>

            <tr>
                <td colspan="2"><p><strong>Максимальное количество баллов: </strong></p></td>
                <td colspan="1"><p><strong>${maxSum}</strong></p></td>
            </tr>
            <tr>
                <td colspan="2"><p><strong>(количество набранных баллов / макс. количество баллов) x 100%</strong></p>
                <td colspan="1"></td>
            </tr>
            </tbody>
        </table>
    </main>


</@main.layout>