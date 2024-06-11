<#import "layout.ftlh" as main>
<@main.layout>

    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <div class="col">
                            <div class="py-3 px-3 text-center">
                                <h6>Фильтры</h6>
                                <select class="form-select mb-2" onchange="handleSelectChange(this.value)">
                                    <option selected disabled>Выберите территорию</option>
                                    <#if locations?has_content>
                                        <#list locations as location>
                                            <option value="${location.id}">${location.name}</option>
                                        </#list>
                                    <#else>
                                        <option disabled>Нет доступных территорий</option>
                                    </#if>
                                </select>
                            </div>
                            <div class="px-3" id="pizzeria-list">
                                <p><a href="#">Борисов-1</a></p>
                                <p><a href="#">Полоцк-1</a></p>
                                <p><a href="#">Нововолоцк-1</a></p>
                                <p><a href="#">Орша-1</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-8 col-xl-9">
            <div class="card border-0 shadow-sm rounded-4">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-borderless">
                            <tbody>
                            <tr>
                                <td class="align-middle">
                                    <span>Менеджер<br>Менеджеровский</span>
                                </td>
                                <td>
                                    <div class="row row-cols-1">
                                        <div class="col">
                                            <div class="text-body-secondary fw-medium calendar-date">
                                                31.01.2024
                                            </div>
                                        </div>
                                        <div class="col">
                                            <button class="btn btn-sm btn-light rounded-4 calendar-button"
                                                    data-bs-toggle="modal" data-bs-target="#workShiftModal">
                                                +
                                            </button>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="row row-cols-1">
                                        <div class="col">
                                            <div class="text-body-secondary fw-medium calendar-date">
                                                31.01.2024
                                            </div>
                                        </div>
                                        <div class="col">
                                            <button class="btn btn-sm btn-light rounded-4 calendar-button"
                                                    data-bs-toggle="modal" data-bs-target="#workShiftModal">
                                                +
                                            </button>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="row row-cols-1">
                                        <div class="col">
                                            <div class="text-body-secondary fw-medium calendar-date">31.01.2024
                                            </div>
                                        </div>
                                        <div class="col">
                                            <button class="btn btn-sm btn-danger rounded-4 calendar-button"
                                                    data-bs-toggle="modal" data-bs-target="#workShiftModal">
                                                Выходной
                                            </button>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="row row-cols-1">
                                        <div class="col">
                                            <div class="text-body-secondary fw-medium calendar-date">
                                                31.01.2024
                                            </div>
                                        </div>
                                        <div class="col">
                                            <button class="btn btn-sm btn-success rounded-4 calendar-button"
                                                    style="font-size: 11px;" data-bs-toggle="modal"
                                                    data-bs-target="#workShiftModal">
                                                <div>12:00 - 19:00</div>
                                                <div>12:00 - 19:00</div>
                                                <div>12:00 - 19:00</div>
                                            </button>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="row row-cols-1">
                                        <div class="col">
                                            <div class="text-body-secondary fw-medium calendar-date">
                                                31.01.2024
                                            </div>
                                        </div>
                                        <div class="col">
                                            <button class="btn btn-sm btn-light rounded-4 calendar-button"
                                                    data-bs-toggle="modal" data-bs-target="#workShiftModal">
                                                +
                                            </button>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="row row-cols-1">
                                        <div class="col">
                                            <div class="text-body-secondary fw-medium calendar-date">
                                                31.01.2024
                                            </div>
                                        </div>
                                        <div class="col">
                                            <button class="btn btn-sm btn-light rounded-4 calendar-button"
                                                    data-bs-toggle="modal" data-bs-target="#workShiftModal">
                                                +
                                            </button>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="row row-cols-1">
                                        <div class="col">
                                            <div class="text-body-secondary fw-medium calendar-date">
                                                31.01.2024
                                            </div>
                                        </div>
                                        <div class="col">
                                            <button class="btn btn-sm btn-light rounded-4 calendar-button"
                                                    data-bs-toggle="modal" data-bs-target="#workShiftModal">
                                                +
                                            </button>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <style>
        body {
            min-height: 100vh;
        }

        @media screen and (max-width: 575px) {
            .sidebar {
                position: fixed;
                bottom: 0px;
                left: 0;
                z-index: 2;
            }
        }

        .calendar-button {
            height: 60px;
            width: 100px;
        }

        .calendar-date {
            font-size: 11px;
            text-align: right;
        }
    </style>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">
    </script>

    <script>
        async function handleSelectChange(selectedValue) {
            let pizzeriaList = document.getElementById('pizzeria-list');
            pizzeriaList.innerHTML = '';
            fetch('/api/pizzerias/location/' + selectedValue)
                .then(response => response.json())
                .then(data => {
                    data.forEach(item => {
                        const pizzeria = document.createElement('p');
                        pizzeria.innerHTML =
                            `
                                <a href="` + "#" + `">` + item.name + `</a>
                            `;

                        pizzeriaList.insertAdjacentElement('beforeend', pizzeria);
                    });
                })
                .catch(error => console.error('Error fetching new messages:', error));
        }

    </script>

</@main.layout>