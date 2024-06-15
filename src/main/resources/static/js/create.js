let totalSum = 0;

let criteriaWraps = document.querySelectorAll('[id^="criteria-wrap-"]');
let criterias = document.querySelectorAll('[id^="deleteCriteria"]');
for (let i = 0; i < criterias.length; i++) {
    criterias[i].addEventListener('click', () => {
        criteriaWraps[i].remove();
        setupMaxValueInputs();
        updateTotalSum();
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
    del.remove();
    let criteriaList = document.querySelector('.criterion-list');
    let criteriaWrap = document.getElementById('criteria-wrap-' + id);
    if (!criteriaWrap) {
        let criteria = await getCriteriaById(id);
        console.log(criteria)
        let newCriteria = document.createElement('tr');
        newCriteria.setAttribute('id', 'criteria-wrap-' + id);
        newCriteria.innerHTML =
            '<th scope="row">' + criteria.section + '</th>' +
            '<input type="hidden" value="' + criteria.id + '" name="criteriaMaxValueDtoList[' + id + '].criteriaId">' +
            '<td>' + criteria.zone + '</td>' +
            '<td>' + criteria.description + '</td>' +
            '<td>' +
            (criteria.section === ''
                    ? '<input type="number" name="criteriaMaxValueDtoList[' + id + '].maxValue"  class="form-control form-control-sm w-75" required min="1" value="1" id="maxValueInput-' + id + '">'
                    : criteria.coefficient + '<input type="hidden" name="criteriaMaxValueDtoList[' + id + '].maxValue" value="'+criteria.coefficient+'" id="maxValueInput-' + id + '">'
            ) +
            '</td>' +
            '<td>' +
            '<button class="btn btn-link bg-white shadow-sm rounded-4 p-2" type="button" id="deleteCriteria-' + id + '">' +
            '<i class="bi bi-trash text-secondary fs-4"></i>' +
            '</button>' +
            '</td>';
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
    let response = await fetch("/api/criteria/" + id);
    if (response.ok) {
        return await response.json();
    } else {
        console.log(response);
        alert("Ошибка HTTP: " + response.status);
    }
}

let saveButton = document.getElementById('form');
saveButton.addEventListener('submit', (e) => {
    validate(e);
})

async function validate(event) {
    event.preventDefault();
    let list = document.getElementById('criterion-list');
    let zone = document.getElementById('zoneSelect').value;
    let section = document.getElementById('sectionSelect').value;
    let description = document.getElementById('descriptionInput').value;
    let coefficient = document.getElementById('coefficientInput').value;

    const data = {
        description: description,
        coefficient: coefficient,
        zone: zone,
        section: section
    }
    console.log(data)
    try {
        const resp = await fetch("/api/criteria/create", {
            method: "POST",
            mode: "cors",
            cache: "no-cache",
            credentials: "same-origin",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const respData = await resp.json();

        if (!resp.ok) {
            console.log(respData)
            handleErrors(respData);
        } else {
            const createdId = respData;
            console.log("new criteria id " + createdId);
            let criteriaList = document.querySelector('.criterion-list');
            let newCriteria = document.createElement('tr');
            newCriteria.setAttribute('id', 'criteria-wrap-' + createdId);
            newCriteria.innerHTML =
                '<th scope="row">' + data.section + '</th>' +
                '<input type="hidden" value="' + createdId + '" name="criteriaMaxValueDtoList[' + createdId + '].criteriaId">' +
                '<td>' + data.zone + '</td>' +
                '<td>' + data.description + '</td>' +
                '<td>' +
                (data.section === ''
                    ? '<input type="number" name="criteriaMaxValueDtoList[' + createdId + '].maxValue"  class="form-control form-control-sm w-75" required min="1" value="1" id="maxValueInput-' + createdId + '">'
                    : data.coefficient + '<input type="hidden" name="criteriaMaxValueDtoList[' + createdId + '].maxValue" value="'+data.coefficient+'" id="maxValueInput-' + createdId + '"/> ') +
                '</td>' +
                '<td>' +
                '<button class="btn btn-link bg-white shadow-sm rounded-4 p-2" type="button" id="deleteCriteria-' + createdId + '">' +
                '<i class="bi bi-trash text-secondary fs-4"></i>' +
                '</button>' +
                '</td>';
            criteriaList.appendChild(newCriteria);

            let deleteButton = document.getElementById('deleteCriteria-' + createdId);
            deleteButton.addEventListener('click', function () {
                document.getElementById('criteria-wrap-' + createdId).remove();
                updateTotalSum();
            });
            setupMaxValueInputs();
            let modal = document.getElementById('create');
            modal.classList.remove('show');
            modal.style.display = 'none';
            document.body.classList.remove('modal-open');
            let modalBackdrop = document.getElementsByClassName('modal-backdrop')[0];
            if (modalBackdrop) {
                modalBackdrop.parentNode.removeChild(modalBackdrop);
            }
        }
    } catch (error) {
        console.log("Error: ", error)
    }
}

function handleErrors(errorData) {
    let desc = document.getElementById('descr-err');
    let coeff = document.getElementById('coef-err');
    let zoneER = document.getElementById('zone-err');
    let sectionER = document.getElementById('section-err');
    desc.innerHTML = "";
    coeff.innerHTML = "";
    zoneER.innerHTML = "";
    sectionER.innerHTML = "";

    for (const [field, message] of Object.entries(errorData)) {
        const errorElement = document.createElement("p");
        if (field === 'description') {
            errorElement.textContent = message + '';
            desc.appendChild(errorElement)
        } else if (field === 'section') {
            sectionER.textContent = message + '';
            desc.appendChild(errorElement)
        } else {
            errorElement.textContent = message + '';
            coeff.appendChild(errorElement);
        }
    }
}
let sectionSelect = document.getElementById('sectionSelect');
let zoneSelect = document.getElementById('zoneSelect');
let coefficientInput = document.getElementById('coefficientInput');
let descriptionInput = document.getElementById('descriptionInput');
let coefLabel = document.getElementById('coef-label');
let zoneLabel = document.getElementById('zoneLabel')

sectionSelect.addEventListener('change', function () {
    toggleFields(sectionSelect.value);
});
function toggleFields(value) {
    if (value === '') {
        coefficientInput.style.display = 'none';
        coefLabel.style.display = 'none';
        zoneLabel.style.display = 'block';
        zoneSelect.style.display = 'block';
    } else {
        zoneLabel.style.display = 'none';
        zoneSelect.style.display = 'none';
        coefLabel.style.display = 'block';
        coefficientInput.style.display = 'block';
    }
}

document.getElementById('create').addEventListener('shown.bs.modal', function () {
    toggleFields(sectionSelect.value);
});
document.getElementById('create').addEventListener('hidden.bs.modal', function () {
    clearFields();
});
function clearFields() {
    descriptionInput.value = '';
    coefficientInput.value = '';
    toggleFields(sectionSelect.value);
}

async function getCriterion(value, pizzeriaId) {
    let criterion = await getCriterionByTypeAndPizzeriaId(value, pizzeriaId);
    console.log(criterion)
    let criteriaList = document.querySelector('.criterion-list');
    criteriaList.innerHTML = '';
    if (criterion && criterion.length > 0) {
        for (let i = 0; i < criterion.length; i++) {
            let newCriteria = document.createElement('tr');
            newCriteria.setAttribute('id', 'criteria-wrap-' + criterion[i].id);
            newCriteria.innerHTML =
                '<th scope="row">' + criterion[i].section + '</th>' +
                '<input type="hidden" name="criteriaMaxValueDtoList[' + i + '].criteriaId" value="' + criterion[i].id + '">' +
                '<td>' + criterion[i].zone + '</td>' +
                '<td>' + criterion[i].description + '</td>' +
                '<td>' +
                (criterion[i].section === ''
                    ? '<input type="number" name="criteriaMaxValueDtoList[' + i + '].maxValue" class="form-control form-control-sm w-75" required min="1" value="1" id="maxValueInput-' + criterion[i].id + '">'
                    : criterion[i].coefficient + '<input type="hidden" name="criteriaMaxValueDtoList[' + i + '].maxValue" value="'+criterion[i].coefficient+'" id="maxValueInput-' + criterion[i].id + '">') +
                '</td>' +
                '<td>' +
                '<button class="btn btn-link bg-white shadow-sm rounded-4 p-2" type="button" id="deleteCriteria-' + criterion[i].id + '" id="maxValueInput-' + criterion[i].id + '">' +
                '<i class="bi bi-trash text-secondary fs-4"></i>' +
                '</button>' +
                '</td>';
            criteriaList.appendChild(newCriteria);
            let deleteButton = document.getElementById('deleteCriteria-' + criterion[i].id);
            deleteButton.addEventListener('click', function () {
                document.getElementById('criteria-wrap-' + criterion[i].id).remove();
                updateTotalSum();
            });
        }
        setupMaxValueInputs();
    }
}

async function getCriterionByTypeAndPizzeriaId(value, pizzeriaId) {
    let response = await fetch("/api/criteria/" + value + "/" + pizzeriaId);
    if (response.ok) {
        return await response.json();
    } else {
        console.log(response);
        alert("Ошибка HTTP: " + response.status);
    }
}
const checklistSubmitButton = document.getElementById("checklistSubmitButton");
const checklistType = document.getElementById("checklistType")

checklistType.addEventListener('change', () => {
    checklistSubmitButton.classList.remove('disabled');
})

function setupMaxValueInputs() {
    let maxValueInputs = document.querySelectorAll('[id^="maxValueInput"]');
    if (maxValueInputs.length === 0) {
        console.log("не найдено")
    } else {
        for (let i = 0; i < maxValueInputs.length; i++) {
            maxValueInputs[i].addEventListener('input', () => {
                countTotalSum(maxValueInputs);
            });
        }
        countTotalSum(maxValueInputs);
    }
}

function countTotalSum(maxValueInputs) {
    totalSum = 0;
    for (let i = 0; i < maxValueInputs.length; i++) {
        totalSum += parseInt(maxValueInputs[i].value) || 0;
    }
    let sum = document.getElementById('totalSum');
    sum.innerHTML = totalSum;
    console.log(totalSum);
}

function updateTotalSum() {
    let maxValueInputs = document.querySelectorAll('[id^="maxValueInput"]');
    countTotalSum(maxValueInputs);
}

let createForm = document.getElementById('create-form');
createForm.addEventListener('submit', (event) => {
    event.preventDefault();
    putIndexesToListAndValidate();
});

function putIndexesToListAndValidate() {
    let criteriaList = document.querySelector('.criterion-list');
    let criteriaWraps = criteriaList.querySelectorAll('tr');
    for (let i = 0; i < criteriaWraps.length; i++) {
        let hiddenInput = criteriaWraps[i].querySelector('input[type="hidden"]');
        let maxValueInput = criteriaWraps[i].querySelector('input[type="number"]');

        if (hiddenInput) {
            hiddenInput.name = 'criteriaMaxValueDtoList[' + i + '].criteriaId';
        }
        if (maxValueInput) {
            maxValueInput.name = 'criteriaMaxValueDtoList[' + i + '].maxValue';
        }
    }
    createForm.submit();
}
