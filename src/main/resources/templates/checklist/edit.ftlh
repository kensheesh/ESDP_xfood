<#import "../layout.ftlh" as main>
<@main.layout>
    <form action="/checks/${checklist.id}/update" method="post">
        <div class="container mt-4">
            <div class="row mb-4">
                <div class="col text-center">
                    <p class="">${checklist.workSchedule.pizzeria.name}</p>
                    <p class="">${checklist.workSchedule.pizzeria.location.name}</p>

                    <input type="hidden" name="workSchedule.pizzeria" value="${checklist.workSchedule.pizzeria}">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-4">
                    <div class="card border-0  shadow-sm rounded">
                        <div class="card-body">
                            <h4>График менеджера: </h4>
                            <div class="row">
                                <div class="col">
                                    <select  name="workSchedule.manager.id" class="form-select" aria-label="Default select example">
                                        <option selected  value="${checklist.workSchedule.manager.id}">${checklist.workSchedule.manager.name} ${checklist.workSchedule.manager.surname}</option>
                                        <#list managers as m>
                                            <#if m.id != checklist.workSchedule.manager.id>
                                                <option value="${m.id}">${m.name} ${m.surname}</option>
                                            </#if>
                                        </#list>
                                    </select>
                                    <div class="form-group my-2">
                                        <label for="managerStartTime" class="form-label">Время начала смены:</label>
                                        <input type="datetime-local" id="managerStartTime" class="form-control" name="workSchedule.startTime" value="${checklist.workSchedule.startTime}">
                                    </div>
                                    <div class="form-group my-2">
                                        <label for="managerEndTime" class="form-label">Время конца смены:</label>
                                        <input type="datetime-local" id="managerEndTime" class="form-control" name="workSchedule.endTime" value="${checklist.workSchedule.endTime}">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 mb-4">
                    <div class="card border-0 shadow-sm rounded">
                        <div class="card-body">
                            <h4>График эксперта: </h4>
                            <div class="row">
                                <div class="col"  id="opportunity">
                                    <select class="form-select" aria-label="Default select example" name="opportunity.user.id">
                                        <option selected value="${checklist.opportunity.user.id}">${checklist.opportunity.user.name} ${checklist.opportunity.user.surname}</option>
                                        <#list experts as e>
                                            <#if e.id != checklist.opportunity.user.id>
                                                <option value="${e.id}">${e.name} ${e.surname}</option>
                                            </#if>
                                        </#list>
                                    </select>
                                    <h1 class="display-4"></h1>
                                    <div class="form-group my-2">
                                        <label for="expertStartTime" class="form-label">Дата:</label>
                                        <input type="date" id="expertStartTime" class="form-control" name="opportunity.date" value="${checklist.opportunity.date}" required>
                                    </div>
                                    <div class="form-group my-2">
                                        <label for="expertStartTime" class="form-label">Время начала смены:</label>
                                        <input type="time" id="expertStartTime" class="form-control" name="opportunity.startTime" value="${checklist.opportunity.startTime}" required>
                                    </div>
                                    <div class="form-group my-2">
                                        <label for="expertEndTime" class="form-label">Время конца смены:</label>
                                        <input type="time" id="expertEndTime" class="form-control" name="opportunity.endTime" value="${checklist.opportunity.endTime}" required>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="table-responsive" style="max-height: 350px;">
                <table class="table table-striped-columns">
                    <thead class="bg-white" style="position: sticky; top: 0; z-index: 1;">
                    <tr>
                        <th scope="col">Раздел</th>
                        <th scope="col">Зона</th>
                        <th scope="col">Что проверить</th>
                        <th scope="col" id="sum"></th>
                    </tr>
                    </thead>
                    <tbody >
                    <#list checklist.criterion as c>
                        <tr>
                            <th scope="row">${c.section}</th>
                            <td>${c.zone}</td>
                            <td>${c.description}</td>
                            <input type="hidden" name="criterion[${c?index}].id">
                             <td><input type="number" class="form-control" name="criterion[${c?index}].maxValue" id="value-${c.id}" required min="1" value="${c.maxValue}"></td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <div class="container text-end">
                <button type="submit" class="btn btn-success rounded my-3 w-75">
                    Изменить
                </button>
            </div>
        </div>
    </form>
    <script src="/js/edit.js">
    </script>
</@main.layout>
