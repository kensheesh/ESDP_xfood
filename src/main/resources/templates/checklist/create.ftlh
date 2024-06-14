<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <style>
        .input-group:hover,
        .input-group:focus {
            border-radius: 10px;
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
            outline: none;
        }
    </style>
    <form action="/checks/create" method="post" id="create-form">
    <div class="container mt-3">
        <input type="hidden" name="date" value="${date}">
        <input type="hidden" name="managerId" value="${managerId}">
        <input type="hidden" name="expertId" value="${expertId}">

        <div class="row bg-white rounded shadow-sm align-items-end">
            <div class="col-md-3">
                <div class="my-2">
                    <select id="checklistType" class="form-select" aria-label="Default select example" onchange="getCriterion(this.value, ${workSchedule.pizzeriaId})" name="checkTypeId">
                        <option selected disabled>выберите тип чек листа</option>
                        <#list types as t>
                            <option value="${t.id}">${t.name}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group my-2">
                    <label for="startTime" class="form-label">Время начала смены:</label>
                    <input type="time" id="startTime" class="form-control" name="startTime" value="${workSchedule.startTime}" required>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group my-2">
                    <label for="endTime" class="form-label">Время окончания смены:</label>
                    <input type="time" id="endTime" class="form-control" value="${workSchedule.endTime}" name="endTime" required>
                </div>
            </div>
            <div class="col-md-3">
                <button id="checklistSubmitButton" type="submit" class="btn btn-success my-2 w-100 disabled" >Создать чек-лист</button>
            </div>
        </div>
    </div>
<div class="mt-4 shadow-sm p-3 bg-white">
    <div>
            <div class="table-responsive" style="max-height: 400px;">
                <table class="table table-striped-columns">
                    <thead class="bg-white" style="position: sticky; top: 0; z-index: 1;">
                        <tr>
                            <th scope="col">Раздел</th>
                            <th scope="col">Зона</th>
                            <th scope="col">Что проверить</th>
                            <th scope="col">Оценка</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody class="criterion-list">
                    </tbody>
                </table>
            </div>
    </div>
</div>
    </form>
    <div class="row">
        <div class="col">
            <div class="input-group my-4">
                <button type="button" class="search_btn btn btn-white shadow-sm border w-100"  data-bs-toggle="modal" data-bs-target="#searchModal">
                        <span class="d-flex justify-content-start align-items-center">
                            <span class="me-auto">Найти критерию...</span>
                                <i class="bi bi-search"></i>
                        </span>
                </button>
            </div>
        </div>
        <div class="col">
            <button type="button" class="btn btn-success rounded-2 my-4" data-bs-toggle="modal" data-bs-target="#create">создать новый критерий</button>
        </div>
    </div>

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
                            <input type="number" class="form-control" id="coefficientInput" name="coefficient" required>
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
<script src="/js/create.js">
</script>
</@main.layout>