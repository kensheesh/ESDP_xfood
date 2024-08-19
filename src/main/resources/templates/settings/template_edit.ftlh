<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <form action="/templates/${id}" method="post" id="updateForm">
        <input type="hidden" value="${template.templateName}" id="oldName">
        <div class="container my-5">
            <div class="border rounded shadow-sm p-4 my-3">
                <h2 class="mb-4">Редактировать шаблон</h2>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="templateName" class="form-label">Название шаблона:</label>
                            <input type="text" name="templateName"  value="${template.templateName}" class="form-control" minlength="2" required maxlength="200" id="templateName">
                            <p class="text-danger" id="nameError"></p>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="templatePrice" class="form-label">Вознаграждение:</label>
                            <input type="number" name="templatePrice" class="form-control" required min="1"  id="templatePrice" value="${template.templatePrice}">
                        </div>
                    </div>
                </div>
            </div>
            <div class="border rounded shadow-sm p-4">
                <div class="d-flex justify-content-between">
                    <div class="me-2">
                        <div class="input-group">
                            <button type="button" class="search_btn btn btn-white shadow-sm border w-100" id="findCriteria" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <span class="d-flex justify-content-start align-items-center">
                            <span class="me-auto">Найти критерий...</span>
                            <i class="bi bi-search"></i>
                        </span>
                            </button>
                        </div>
                    </div>
                    <div>
                        <button type="button" class="btn btn-success rounded-2" data-bs-toggle="modal" data-bs-target="#create" id="createCriteria">Создать новый критерий</button>
                    </div>
                </div>
            </div>
            <div class="mt-4 shadow-sm p-3 bg-white">
                <div>
                    <div class="alert alert-warning mt-3" style="display: none;" id="alert">
                            Пожалуйста, добавьте критерии.
                    </div>
                    <div class="table-responsive" style="max-height: 400px;">
                        <table class="table">
                            <thead class="bg-white" style="position: sticky; top: 0; z-index: 1;">
                            <tr>
                                <th scope="col">Раздел</th>
                                <th scope="col">Зона</th>
                                <th scope="col">Что проверить</th>
                                <th scope="col">Оценка</th>
                                <th scope="col" id="totalSum"></th>
                            </tr>
                            </thead>
                            <tbody class="criterion-list" id="criterion-list" >
                            <#list template.criteriaMaxValueDtoList as c>
                                <tr id="criteria-wrap-${c.criteriaId}">
                                    <th scope="row">${c.section}</th>
                                    <td>${c.zone}</td>
                                    <td>${c.description}</td>
                                    <input type="hidden" name="criteriaMaxValueDtoList[${c.criteriaId}].criteriaId" value="${c.criteriaId}">
                                    <td><input type="number" class="form-control" name="criteriaMaxValueDtoList[${c.criteriaId}].maxValue" id="maxValueInput-${c.criteriaId}" required min="1" value="${c.maxValue}"></td>
                                    <td>
                                        <button class="btn btn-link bg-white shadow-sm rounded-4 p-2" type="button" id="deleteCriteria-${c.criteriaId}">
                                            <i class="bi bi-trash text-secondary fs-4"></i>
                                        </button>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="row bg-white rounded shadow-sm d-flex align-items-end">
                <div class="col-md-3">
                    <button id="checklistSubmitButton" type="submit" class="btn btn-success my-2 w-100">Сохранить изменения</button>
                </div>
                <div class="col-md-3">
                    <a class="btn btn-danger my-2" href="/templates" role="button">Отменить</a>
                </div>
            </div>
        </div>
    </form>

    <!-- Modal -->
    <div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="searchModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content rounded-4">
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
                <div class="modal-content rounded-4">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Создание критерия</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="category">Раздел:</label>
                            <div id="section-err" class="text-danger"></div>
                            <select class="form-select" aria-label="Default select example" disabled name="section" id="sectionSelect" >
                                <#list sections as s>
                                    <option value="${s.name}">Обычный</option>
                                </#list>
                            </select>
                        </div>
                        <div class="form-group">
                            <div id="zone-err" class="text-danger"></div>
                            <label for="category" id="zoneLabel">Зона:</label>
                            <select class="form-select" aria-label="Default select example" name="zone" id="zoneSelect">
                                <#list zones as z>
                                    <#if z.name == ''>
                                        <option value="${z.name}" selected>Обычная</option>
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
    <script src="/js/edit_template.js">
    </script>
</@main.layout>
