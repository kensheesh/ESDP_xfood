<#import "../layout.ftlh" as main>
<@main.layout>
    <form action="/checks/${checklist.id}/update" method="post" id="edit">
        <div class="container mt-4">
            <div class="alert alert-danger" role="alert" style="display: none;" id="error">
                Сумма баллов не соответствует требумой сумме!
            </div>
            <div class="card mb-3 shadow-sm border-light rounded">
                <div class="card-header bg-dodo text-white">
                    <h5 class="card-title mb-0">${checklist.workSchedule.pizzeria.name}</h5>
                </div>
                <div class="card-body">
                    <p class="card-subtitle mb-2 text-secondary">
                        <strong>Локация:</strong> ${checklist.workSchedule.pizzeria.location.name}
                    </p>
                    <p class="card-subtitle text-secondary"><strong>Тип проверки: </strong></p>
                    <div class="my-3">
                        <select class="form-select w-50" aria-label="Default select example">
                            <#list types as t>
                                <#if t.name == checklist.checkType>
                                    <option selected ${t.id}>${checklist.checkType}</option>
                                <#else>
                                    <option value="${t.id}">${t.name}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 mb-3 mb-sm-0">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">${checklist.workSchedule.manager.name} ${checklist.workSchedule.manager.surname}  </h5>
                                    <p class="card-text">${checklist.workSchedule.startTime} - ${checklist.workSchedule.endTime}</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Эксперт : </h5>
                                    <p class="card-text">${checklist.expert.name} ${checklist.expert.surname} </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="uuid" value="${checklist.id}">
                </div>
            </div>
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
                        <th scope="col" id="sum"></th>
                    </tr>
                    </thead>
                    <tbody  class="criterion-list">
                    <#list checklist.criterion as c>
                        <tr id="criteria-wrap-${c.id}">
                            <th scope="row">${c.section}</th>
                            <td>${c.zone}</td>
                            <td>${c.description}</td>
                            <input type="hidden" name="criterion[${c?index}].id" value="${c.id}">
                             <td><input type="number" class="form-control" name="criterion[${c?index}].maxValue" id="value-${c.id}" required min="1" <#if c.section == "Критический фактор">readonly value="${c.coefficient}" <#else> value="${c.maxValue}"</#if> ></td>
                            <td>
                                <button class="btn btn-link bg-white shadow-sm rounded-4 p-2" type="button" id="deleteCriteria-${c.id}">
                                    <i class="bi bi-trash text-secondary fs-4"></i>
                                </button>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <div class="d-grid gap-2 d-md-block text-end">
                <a class="btn btn-danger" href="/checks/${checklist.id}/check" role="button">Отменить</a>
                <button class="btn btn-success" type="submit">Сохранить</button>
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
    <input type="hidden" value="${checklist.totalValue}" id="totalSum" >
    <script src="/js/edit.js">
    </script>
</@main.layout>
