    <#import "../layout.ftlh" as main>
    <@main.layout ; spring>
        <form action="/statistics" method="get" id="daterange">
            <input type="hidden" name="from" id="from" value="">
            <input type="hidden" name="to" id="to" value="">
            <div id="reportrange" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%">
                <i class="fa fa-calendar"></i>&nbsp;
                <span></span> <i class="fa fa-caret-down"></i>
            </div>
            <button type="submit" id="date-range-button">применить</button>
        </form>
        <#if from?? && to??>
            <#assign size = (statistics.days?size) + 3>
            <#assign size1 = (statistics.days?size) + 4>

            <table id="myTable" class="display cell-border">
                <thead class="fs-6">
                <tr>
                    <th>Менеджер</th>
                    <th>Эксперт</th>
                    <th>Пиццерия</th>
                    <th>Тип</th>
                    <#if statistics.days??>
                        <#list statistics.days as d>
                            <th colspan="fs-6">${d.day}</th>
                        </#list>
                    </#if>
                    <th>Среднее за менеджера и эксперта</th>
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
                            <td>${row.type}</td>
                            <#list statistics.days as d>
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
                            <td class="average-by-pizzeria">${table.averageByTable}</td>
                        </tr>
                    </#list>
                </#list>
                </tbody>

                <tfoot>
                <tr>
                    <th colspan="${size}" style="text-align:right">Общий средний балл по всем пиццериям:</th>
                    <th style="text-align:right" colspan="${size1}">${statistics.average}</th>
                </tr>
                </tfoot>
            </table>
        <#else>
            Выберите период для отображения статистики
        </#if>
    </@main.layout>
