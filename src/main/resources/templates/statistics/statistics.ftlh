    <#import "../layout.ftlh" as main>
    <@main.layout ; spring>
        
        <#if from?? && to??>
            <#assign size = (days?size) + 3>
            <#assign size1 = (days?size) + 4>
            <table id="myTable" class="display">
                <thead>
                <tr>
                    <th>Менеджер</th>
                    <th>Эксперт</th>
                    <th>Пиццерия</th>
                    <#list days as d>
                        <th>${d.day}</th>
                    </#list>
                    <th>Среднее</th>
                    <th>Среднее за пиццерию</th>
                </tr>
                </thead>
                <tbody>
                <#list statistics.tables as table>
                    <#list table.rows as row>
                        <tr>
                            <td>${row.manager.name} ${row.manager.surname}</td>
                            <td>${row.expert.name} ${row.expert.surname}</td>
                            <td>${row.pizzeria.name}</td>
                            <#list days as d>
                                <#assign cellFound = false>
                                <#list row.cells as cell>
                                    <#if d.day == cell.date>
                                        <td>${cell.points}</td>
                                        <#assign cellFound = true>
                                        <#break >
                                    </#if>
                                </#list>
                                <#if !cellFound>
                                    <td> </td>
                                </#if>
                            </#list>
                            <td>${row.averageByRow}</td>
                            <td>${table.averageByTable}</td>
                        </tr>
                    </#list>
                </#list>
                </tbody>

                <tfoot>
                <tr>
                    <th colspan="${size}" style="text-align:right">Среднее:</th>
                    <th style="text-align:right" colspan="${size1}">${statistics.average}</th>
                </tr>
                </tfoot>
            </table>
            <#else>
            Выберите период для отображения статистики
        </#if>

    </@main.layout>
