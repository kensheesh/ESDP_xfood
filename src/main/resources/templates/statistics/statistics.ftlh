    <#import "../layout.ftlh" as main>
    <@main.layout ; spring>
        <#assign size = (days?size) + 3>
        <#assign size1 = (days?size) + 4>

        <div id="reportrange" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%">
            <i class="fa fa-calendar"></i>&nbsp;
            <span></span> <i class="fa fa-caret-down"></i>
        </div>


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

        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>

        <script type="text/javascript">
            $(function() {

                let start = moment().subtract(29, 'days');
                let end = moment();

                function cb(start, end) {
                    $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                }

                $('#reportrange').daterangepicker({
                    startDate: start,
                    endDate: end,
                    ranges: {
                        'Today': [moment(), moment()],
                        'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                        'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                        'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                        'This Month': [moment().startOf('month'), moment().endOf('month')],
                        'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                    }
                }, cb);

                cb(start, end);

            });
        </script>
    </@main.layout>
