<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <table id="myTable" class="display">
        <thead>
        <tr>
            <th>Name</th>
            <th>Surname</th>
        </tr>
        </thead>
        <tbody>
        <#list experts as e>
            <tr>
                <td> ${e.name}</td>
                <td> ${e.email}</td>
            </tr>
        </#list>
        </tbody>
    </table>

</@main.layout>
