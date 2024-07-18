<#import "../layout.ftlh" as main>
<@main.layout>
    <form action="/checks/${checklist.id}/update" method="post">
        <div class="container mt-4">
            <div class="row mb-4">
                <div class="col text-center">
                    <p class="">${checklist.workSchedule.pizzeria.name}</p>
                    <p class="">${checklist.workSchedule.pizzeria.location.name}</p>
                    <input type="hidden" name="uuid" value="${checklist.id}">
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
                                                <option value="${m.uuid}">${m.firstName} ${m.patronymicName}</option>
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
                            <h4>Выбрать эксперта: </h4>
                         <div class="row">
                                <div class="col"  id="opportunity">
                                    <select class="form-select" aria-label="Default select example" name="opportunity.user.id">
                                        <option selected value="${checklist.expert.id}">${checklist.expert.name} ${checklist.expert.surname}</option>
                                        <#list experts as e>
                                            <#if e.id != checklist.expert.id>
                                                <option value="${e.id}">${e.name} ${e.surname}</option>
                                            </#if>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
               <#-- <div class="col-md-3 mt-2">
                    <div class="form-group">
                        <label for="totalValue" class="form-label">Введите максимальное кол-во баллов:</label>
                        <input type="number" id="totalValue" class="form-control" name="totalSum" min="1"  required >
                    </div>
                </div>-->
                <div class="row">
                    <div class="col">
                        <div class="input-group my-4">
                            <button type="button" class="search_btn btn btn-white shadow-sm border w-100" id="findCriteria"  data-bs-toggle="modal" data-bs-target="#searchModal">
                        <span class="d-flex justify-content-start align-items-center">
                            <span class="me-auto">Найти критерий...</span>
                                <i class="bi bi-search"></i>
                        </span>
                            </button>
                        </div>
                    </div>
                    <div class="col">
                        <button type="button" class="btn btn-success rounded-2 my-4 " data-bs-toggle="modal" data-bs-target="#create" id="createCriteria">создать новый критерий</button>
                    </div>
                </div>
            </div>
            <div class="table-responsive" style="max-height: 350px;">
                <table class="table">
                    <thead class="bg-white" style="position: sticky; top: 0; z-index: 1;">
                    <tr>
                        <th scope="col">Раздел</th>
                        <th scope="col">Зона</th>
                        <th scope="col">Что проверить</th>
                        <th scope="col" >Максимальный балл</th>
                        <th scope="col" id="totalSum"></th>
                    </tr>
                    </thead>
                    <tbody  class="criterion-list">
                    <#list checklist.criterion as c>
                        <tr id="criteria-wrap-${c.id}">
                            <th scope="row">${c.section}</th>
                            <td>${c.zone}</td>
                            <td>${c.description}</td>
                            <input type="hidden" name="criterion[${c?index}].id" value="${c.id}">
                             <td><input type="number" class="form-control" name="criterion[${c?index}].maxValue" id="value-${c.id}" required min="1" value="${c.maxValue}"></td>
                            <td>
                                <button class="btn btn-link bg-white shadow-sm rounded-4 p-2" type="button" id="deleteCriteria-${c.id}">
                                    <i class="bi bi-trash text-secondary fs-4"></i>
                                </button>
                            </td>`
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


    <!-- Modal -->
    <div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="searchModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="search-bar">
                        <div class="input-group my-4">
                            <input type="text" class="form-control" placeholder="Описание критерии..." aria-describedby="button-addon2" id="searchbar">
                            <span class="input-group-text" id=basic-addon1"><i class="bi bi-search"></i></span>
                        </div>
                    </div>
                    <div class="found-criterion h-100">

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="create" tabindex="-1" aria-labelledby="create" aria-hidden="true">
        <div class="modal-dialog">
            <form action="/criterion/create" method="post"  id="form">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Создание критерия</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="category">Раздел:</label>
                            <div id="section-err" class="text-danger"></div>
                            <select class="form-select" aria-label="Default select example" name="section" id="sectionSelect" >
                                <#list sections as s>
                                    <option value="${s.name}">${s.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="form-group">
                            <div id="zone-err" class="text-danger"></div>
                            <label for="category" id="zoneLabel">Зона:</label>
                            <select class="form-select" aria-label="Default select example" name="zone" id="zoneSelect">
                                <#list zones as z>
                                    <#if z.name == ''>
                                        <option value="${z.name}" selected>${z.name}</option>
                                    <#else >
                                        <option value="${z.name}">${z.name}</option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="exampleFormControlInput1" class="form-label" >Описание: </label>
                            <input type="text" class="form-control" id="descriptionInput" name="description">
                            <div id="descr-err" class="text-danger"></div>
                        </div>
                        <div class="form-group">
                            <label for="coefficient" id="coef-label">Коэффициент: </label>
                            <input type="number" class="form-control" id="coefficientInput" name="coefficient" value="1">
                            <div id="coef-err" class="text-danger"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <script src="/js/edit.js">
    </script>
</@main.layout>
