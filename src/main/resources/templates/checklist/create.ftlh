<#import "../layout.ftlh" as main>
<@main.layout>
    <style>
        .input-group:hover,
        .input-group:focus {
            border-radius: 10px;
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
            outline: none;
        }
    </style>
    <div class="row row-cols-md-3 row-cols-1 bg-white rounded shadow-sm">
        <div class="col">
            <div class="my-4">
                <div class="dropdown">
                    <button class="btn btn-white shadow-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        тип чек листа
                    </button>
                    <ul class="dropdown-menu" style="z-index: 2;">
                        <#list types as type>
                            <li><a class="dropdown-item" href="/checks/create?type=${type.name}">${type.name}</a></li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
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
            <button type="button" class="btn btn-success rounded-2 my-4">создать новый критерий</button>
        </div>
    </div>
<div class="mt-4 shadow-sm p-3 bg-white">
    <div>
        <#if criterion?size == 0>
            <p>Выберите тип чек-листа для отображения критериев</p>
        <#else>
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
                        <#list criterion as c>
                            <tr id="criteria-wrap-${c.id}" class="<#if c.section == "WOW фактор">table-success<#elseif c.section == 'Критический фактор'>table-danger</#if>">
                                <th scope="row">${c.section}</th>
                                <td>${c.zone}</td>
                                <td>${c.description}</td>
                                <td>${c.coefficient}</td>
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
        </#if>
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
                            <span class="input-group-text" id="basic-addon1"><i class="bi bi-search"></i></span>
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
<script>
    let criteriaWraps = document.querySelectorAll('[id^="criteria-wrap-"]');
    let criterias = document.querySelectorAll('[id^="deleteCriteria"]');
    for (let i = 0 ; i < criterias.length; i++){
        criterias[i].addEventListener('click', ()=>{
            criteriaWraps[i].remove();
        });
    }

    let search = document.getElementById('searchbar');
    search.addEventListener('input', onInput);

    function onInput(event) {
        event.preventDefault();
        let input = document.getElementById('searchbar');
        let description = input.value;
        let result = "/api/criteria/search?description=" + description;
        getResults(result);
    }

    async function getResults(result) {
        let response = await fetch(result);
        if (response.ok){
            let json = await response.json();
            printResults(json);
        } else {
            console.log(response);
            alert("Ошибка HTTP: " + response.status);
        }
    }

    function printResults(json) {
        let objectSize = Object.keys(json).length;
        let searchResultsWrapper = document.querySelector('.found-criterion');
        searchResultsWrapper.innerHTML = "";
        if (objectSize > 0){
            for (let i = 0; i < objectSize; i++) {
                let element = document.createElement('div');
                element.classList.add('card');
                element.id = 'del-'+json[i].id;
                element.innerHTML =
                    '<div class="card-body">'+
                    '<h5 class="card-title">'+json[i].section+'</h5>'+
                    '<h6 class="card-subtitle mb-2 text-muted">'+json[i].zone+'</h6>'+
                    '<p class="card-text">'+json[i].description+'</p>'+
                    '<button class="btn btn-link" type="button" id="add-criteria-'+json[i].id+'" data-id="'+json[i].id+'">'+
                    '<i class="bi bi-plus-circle-fill text-success fs-3"></i>'+
                    '</button>'+
                    '</div>';
                searchResultsWrapper.appendChild(element);
            }

            let addCriteriaButtons = document.querySelectorAll('[id^="add-criteria-"]');
            for (let btn of addCriteriaButtons) {
                btn.addEventListener('click', function() {
                    let id = this.getAttribute('data-id');
                    addCriteriaToList(id);
                });
            }
        } else {
            let noResultsSection = document.createElement('div');
            noResultsSection.innerText = 'No criteria found';
            searchResultsWrapper.appendChild(noResultsSection);
        }
    }

    async function addCriteriaToList(id) {
        let del = document.getElementById('del-'+id);
        del.remove();
        let criteriaList = document.querySelector('.criterion-list');
        let criteriaWrap = document.getElementById('criteria-wrap-' + id);
        if (!criteriaWrap) {
            let criteria = await getCriteriaById(id);
            let newCriteria = document.createElement('tr');
            newCriteria.setAttribute('id', 'criteria-wrap-' + id);
            newCriteria.innerHTML =
                '<th scope="row">' + criteria.section + '</th>' +
                '<td>' + criteria.zone + '</td>' +
                '<td>' + criteria.description + '</td>' +
                '<td>' + criteria.coefficient + '</td>' +
                '<td>' +
                '<button class="btn btn-link bg-white shadow-sm rounded-4 p-2" type="button" id="deleteCriteria-' + criteria.id + '">' +
                '<i class="bi bi-trash text-secondary fs-4"></i>' +
                '</button>' +
                '</td>';
            criteriaList.appendChild(newCriteria);

            let deleteButton = document.getElementById('deleteCriteria-' + criteria.id);
            deleteButton.addEventListener('click', function() {
                document.getElementById('criteria-wrap-' + criteria.id).remove();
            });
        }
    }

    async function getCriteriaById(id){
        let response = await fetch("/api/criteria/" + id);
        if (response.ok){
            return await response.json();
        } else {
            console.log(response);
            alert("Ошибка HTTP: " + response.status);
        }
    }


</script>
</@main.layout>