
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
    if (response.ok) {
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
    if (objectSize > 0) {
        for (let i = 0; i < objectSize; i++) {
            let element = document.createElement('div');
            element.classList.add('card');
            element.id = 'del-' + json[i].id;
            element.innerHTML =
                '<div class="card-body">' +
                '<h5 class="card-title">' + json[i].section + '</h5>' +
                '<h6 class="card-subtitle mb-2 text-muted">' + json[i].zone + '</h6>' +
                '<p class="card-text">' + json[i].description + '</p>' +
                '<button class="btn btn-link" type="button" id="add-criteria-' + json[i].id + '" data-id="' + json[i].id + '">' +
                '<i class="bi bi-plus-circle-fill text-success fs-3"></i>' +
                '</button>' +
                '</div>';
            searchResultsWrapper.appendChild(element);
        }

        let addCriteriaButtons = document.querySelectorAll('[id^="add-criteria-"]');
        for (let btn of addCriteriaButtons) {
            btn.addEventListener('click', function () {
                let id = this.getAttribute('data-id');
                addCriteriaToList(id);
            });
        }
    } else {
        let noResultsSection = document.createElement('div');
        noResultsSection.innerText = 'не найдено';
        searchResultsWrapper.appendChild(noResultsSection);
    }
}
async function addCriteriaToList(id) {
    let del = document.getElementById('del-' + id);
    if (del) del.remove();

    let criteriaList = document.querySelector('.criterion-list');
    let criteriaWrap = document.getElementById('criteria-wrap-' + id);
    if (!criteriaWrap) {
        let criteria = await getCriteriaById(id);
        let newCriteria = document.createElement('tr');
        newCriteria.setAttribute('id', 'criteria-wrap-' + id);
        newCriteria.innerHTML =
            `<th scope="row">${criteria.section}</th>
             <input type="hidden" value="${criteria.id}" name="criteriaMaxValueDtoList[${id}].criteriaId">
             <td>${criteria.zone}</td>
             <td>${criteria.description}</td>
             <td>
                <input type="number" name="criteriaMaxValueDtoList[${id}].maxValue"  class="form-control form-control-sm w-75" required ${criteria.section === 'Критический фактор' ? `value="${criteria.coefficient} readonly"` : ` min="1" value="1"` } id="maxValueInput-${id}"> 
             </td>
             <td>
                <button class="btn btn-link bg-white shadow-sm rounded-4 p-2" type="button" id="deleteCriteria-${id}">
                    <i class="bi bi-trash text-secondary fs-4"></i>
                </button>
             </td>`;
        criteriaList.appendChild(newCriteria);

        let deleteButton = document.getElementById('deleteCriteria-' + id);
        deleteButton.addEventListener('click', function () {
            document.getElementById('criteria-wrap-' + id).remove();
            updateTotalSum();
        });

        setupMaxValueInputs();
    }
}

async function getCriteriaById(id) {
    let response = await fetch("/api/criteria/get/" + id);
    if (response.ok) {
        return await response.json();
    } else {
        console.log(response);
        alert("Ошибка HTTP: " + response.status);
    }
}
