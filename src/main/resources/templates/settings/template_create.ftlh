<#import "../layout.ftlh" as main>
<@main.layout>
    <form action="/templates/create" method="post">
    <div class="container my-5">
        <div class="border rounded shadow-sm p-4 my-3">
            <h2 class="mb-4">Создать шаблон</h2>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="templateName" class="form-label">Название шаблона:</label>
                            <input type="text" class="form-control" id="templateName" name="templateName" minlength="2" required maxlength="200" placeholder="Введите название шаблона">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="templatePrice" class="form-label">Вознаграждение:</label>
                            <input type="number" class="form-control" id="templatePrice" name="templatePrice" required min="1" placeholder="Введите вознаграждение">
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
                <div class="table-responsive" style="max-height: 400px;">
                    <table class="table table-striped-columns">
                        <thead class="bg-white" style="position: sticky; top: 0; z-index: 1;">
                        <tr>
                            <th scope="col">Раздел</th>
                            <th scope="col">Зона</th>
                            <th scope="col">Что проверить</th>
                            <th scope="col">Оценка</th>
                            <th scope="col" id="totalSum"></th>
                        </tr>
                        </thead>
                        <tbody class="criterion-list">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="row bg-white rounded shadow-sm d-flex align-items-end">
            <div class="col-md-3">
                <button id="checklistSubmitButton" type="submit" class="btn btn-success my-2 w-100">Создать шаблон</button>
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

    <script src="/js/create_template.js">
    </script>
</@main.layout>
