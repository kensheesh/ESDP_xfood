<#import "../layout.ftlh" as main>
<@main.layout>

    <main>
        <table class="table table-bordered border border-3">
            <thead>
            <tr>
                <th colspan="3" class="section-header">${checkList.workSchedule.pizzeria.name}</th>
                <th colspan="1" class="section-header">${checkList.workSchedule.manager.name}</th>
            </tr>
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
                    <#if list.criteria.zone??>
                        <td>${list.criteria.zone}</td>
                    <#else>
                        <td></td>
                    </#if>

                    <td>${list.criteria.description}</td>
                    <#if list.criteria.maxValue??>
                        <td>${list.criteria.maxValue}</td>
                        <#else>
                            <td></td>
                    </#if>

                    <td><input type="number" name=""></td>
                </tr>

            </#list>
            </tbody>
        </table>

        <p><strong>Максимальное количество баллов: 24</strong></p>
        <p><strong>(количество набранных баллов / макс. количество баллов) x 100%</strong></p>
    </main>


</@main.layout>