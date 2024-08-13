let sLocationId = null;

document.addEventListener('DOMContentLoaded', function () {
    fetch('/api/admin/locations')
        .then(response => response.json())
        .then(data => {
            const locationsDiv = document.getElementById('locations');

            if (locationsDiv) {
                locationsDiv.innerHTML = '';
                data.forEach(location => {
                    const radio = document.createElement('input');
                    radio.type = 'radio';
                    radio.name = 'location';
                    radio.value = location.id;
                    radio.id = `location-${location.id}`;

                    const label = document.createElement('label');
                    label.htmlFor = `location-${location.id}`;
                    label.textContent = location.name;

                    const div = document.createElement('div');
                    div.appendChild(radio);
                    div.appendChild(label);

                    locationsDiv.appendChild(div);
                });
            }
        })
        .catch(error => {
            console.error('Error fetching locations:', error);
        });
});

document.getElementById('continue-to-pizzerias')
    .addEventListener('click', function () {
        const selectedRadio = document.querySelector('input[name="location"]:checked');
        if (selectedRadio) {
            sLocationId = selectedRadio.value;

            document.getElementById('selectLocationModal').classList.remove('show');
            new bootstrap.Modal(document.getElementById('selectPizzeriaModal')).show();
        } else {
            alert('Пожалуйста, выберите локацию.');
        }
    });

document.getElementById('load-pizzerias')
    .addEventListener('click', function () {
        fetch('/api/admin/load-pizzerias', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },

        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                // console.log('Success:', data);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    });

document.getElementById('find-pizzeria')
    .addEventListener('click', function () {
        const query = document.getElementById('pizzeria-search-input').value.trim();

        fetch(`/api/admin/redis-pizzerias?name=${encodeURIComponent(query)}`)
            .then(response => response.json())
            .then(data => {
                displayResults(data);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
                document.getElementById('search-results').innerHTML = 'Ошибка при поиске. Попробуйте снова.';
            });
    });

const resultsPerPage = 21;
let currentPage = 1;

function displayResults(data) {
    const resultsContainer = document.getElementById('search-results');
    const paginationContainer = document.getElementById('pagination');

    resultsContainer.innerHTML = '';
    paginationContainer.innerHTML = '';

    if (data.length === 0) {
        resultsContainer.innerHTML = 'Не удалось найти совпадения';
        return;
    }

    const totalPages = Math.ceil(data.length / resultsPerPage);
    const resultsToDisplay = data.slice((currentPage - 1) * resultsPerPage, currentPage * resultsPerPage);

    resultsToDisplay.forEach((result, index) => {
        if (index % 3 === 0) {
            resultsContainer.innerHTML += '<div class="row mb-3"></div>';
        }

        const cardHtml = `
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <b>${result.Name}</b>
                        <button type="button" class="btn btn-primary" data-bs-toggle="collapse" data-bs-target="#collapseInfo-${result.UUId}">Больше</button>
                    </div>
                    <div id="collapseInfo-${result.UUId}" class="collapse">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><b>Код страны:</b> ${result.countryCode}</li>
                            <li class="list-group-item"><b>Название организации:</b> ${result.OrganizationName}</li>
                            <li class="list-group-item"><b>UUID:</b> ${result.UUId}</li>
                            <li class="list-group-item"><b>Псевдоним:</b> ${result.Alias}</li>
                            <li class="list-group-item"><b>Трансрипт псевдонима:</b> ${result.TranslitAlias}</li>
                            <li class="list-group-item"><b>Адресс:</b> ${result.Address}</li>
                            <li class="list-group-item"><b>Полный адресс:</b> ${result.AddressText}</li>
                            <li class="list-group-item"><b>Ориентир:</b> ${result.Orientation}</li>
                        </ul>
                        <div class="card-header d-flex justify-content-end">
                            <button type="button" class="btn btn-primary select-btn" data-uuid="${result.UUId}" data-name="${result.Name}">Выбрать</button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        document.querySelector('.row.mb-3:last-child').innerHTML += cardHtml;
    });

    const createPageItem = (page) => `
        <li class="page-item ${page === currentPage ? 'active' : ''}">
            <a class="page-link" href="#">${page}</a>
        </li>
    `;


    for (let i = Math.max(1, currentPage - 3); i <= Math.min(totalPages, currentPage + 3); i++) {
        paginationContainer.innerHTML += createPageItem(i);
    }

    paginationContainer.querySelectorAll('.page-link').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            currentPage = +link.textContent;
            displayResults(data);
        });
    });

    document.querySelectorAll('.select-btn').forEach(button => {
        button.addEventListener('click', async function () {
            const uuid = this.getAttribute('data-uuid');
            const name = this.getAttribute('data-name');
            // console.log('UUID:', uuid, 'Name:', name, 'locationId:', sLocationId);
            if (confirm(`Вы уверены, что хотите сохранить пиццерию ${name} - ${uuid}?`)) {
                const response = await fetch('/api/admin/pizzerias', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(
                        {
                            uuid: uuid,
                            name: name,
                            locationId: sLocationId
                        })
                });
                if (response.ok) {
                    alert('Пиццерия сохранена успешно!');
                    const modal = document.getElementById('selectPizzeriaModal');
                    const modal2 = document.getElementById('selectLocationModal');
                    if (modal2) {
                        const bsModal = bootstrap.Modal.getInstance(modal);
                        if (bsModal) bsModal.hide();
                    }
                    if (modal) {
                        const bsModal = bootstrap.Modal.getInstance(modal);
                        if (bsModal) bsModal.hide();
                    }
                } else {
                    alert('Произошла ошибка при сохранении.');
                }
            }
        });
    });

}
