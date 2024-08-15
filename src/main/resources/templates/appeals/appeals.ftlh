<#import "../layout.ftlh" as main>

<@main.layout>
    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3">
                        <div class="col">
                            <div class="row row-cols-3 row-cols-md-1 row-gap-2">
                                <div class="col">
                                        <a href="/weekly" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-calendar2-range fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline">График</span>
                                        </a>
                                </div>
                                <div class="col">
                                    <a href="/opportunities"
                                       class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-calendar2-week fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">Возможности</span>
                                    </a>
                                </div>
                                <div class="col button-wrapper">
                                    <a href="/checks" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <span class="d-none d-md-inline">Проверки</span>
                                        <i class="bi bi-clipboard-check fs-5 d-md-none"></i>
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/analytics" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-bar-chart-fill fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">Аналитика</span>
                                    </a>
                                </div>
                                <div class="col button-wrapper">
                                    <a href="/appeals" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-flag fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">Апелляции</span>
                                        <span class="badge-notification-icon">
                                            <span class="badge-notification-num" id="counter">

                                            </span>
                                        </span>
                                    </a>
                                </div>
                                <div class="col">
                                    <div class="dropdown d-md-none h-100">
                                        <a class="btn btn-dodo-light rounded-4 dropdown-toggle w-100 h-100"
                                           type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            <i class="bi bi-clock fs-5"></i>
                                        </a>
                                        <div class="dropdown-menu border-0 rounded-4 shadow">
                                            <section class="times_second d-flex flex-column row-gap-2 p-3"></section>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col align-self-end d-none d-md-block">
                            <div class="card rounded-4 border-0 shadow-sm">
                                <div class="card-body">
                                    <section class="d-flex flex-column row-gap-2 text-center times">
                                    </section>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-8 col-xl-9">
            <div class="card rounded-4 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3">

                        <div class="col">
                            <form action="/appeals" id="filterForm" class="mb-3">
                                <div class="row g-3">
                                    <div class="col-md">
                                        <label for="statusFilter" class="form-label">Статус:</label>
                                        <select id="statusFilter" name="status" class="form-select"
                                                aria-label="Фильтр по статусу">
                                            <#if currentStatus??>
                                                <#if currentStatus == true>
                                                    <option value="">Новые</option>
                                                    <option selected value="true">Принятые</option>
                                                    <option value="false">Отклоненные</option>
                                                <#elseif currentStatus == false>
                                                    <option value="">Новые</option>
                                                    <option value="true">Принятые</option>
                                                    <option selected value="false">Отклоненные</option>
                                                </#if>
                                            <#else>
                                                <option selected value="">Новые</option>
                                                <option value="true">Принятые</option>
                                                <option value="false">Отклоненные</option>
                                            </#if>

                                        </select>
                                    </div>
                                    <div class="col-md">
                                        <label for="pizzeria-select" class="form-label">Пиццерия:</label>
                                        <select id="pizzeria-select" name="pizzeriaId" class="form-select">
                                            <option value="0">Все</option>
                                        </select>
                                    </div>
                                    <div class="col-md">
                                        <label for="expert-select" class="form-label">Эксперт:</label>
                                        <select id="expert-select" name="expertId" class="form-select">
                                            <option value="0">Все</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="text-center mt-3">
                                    <button type="submit" class="btn btn-primary">Применить фильтр</button>
                                </div>
                            </form>
                        </div>

                        <div class="col">
                            <div class="row row-cols-1 row-gap-2 mt-2 align-items-start overflow-y-scroll"
                                 style="height: 55vh">
                                <div id="appeals" class="row row-cols-1 row-gap-2">
                                    <#list appeals.content as appeal>
                                        <a href="/appeals/${appeal.id}/approve"
                                           class="col text-decoration-none d-block">
                                            <div class="card border-0 rounded-4 bg-primary-subtle">
                                                <div class="card-body">
                                                    <div class="row row-cols-3">
                                                        <div class="col-4 d-flex align-items-center">
                                                            <span>${appeal.fullName}</span>
                                                        </div>
                                                        <div class="col-4 d-flex align-items-center">
                                                            <span>${appeal.pizzeriaName} (${appeal.locationName})</span>
                                                        </div>
                                                        <div class="col-4 d-flex align-items-center">
                                                            <span>Эксперт: ${appeal.expertFullName}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                    </#list>
                                </div>
                            </div>
                        </div>

                        <div class="col" id="lowerPagination">
                            <nav>
                                <#assign page = appeals.number + 1>
                                <ul class="pagination m-0">
                                    <li class="page-item">
                                        <#if appeals.first>
                                            <button id="prevPage" class="page-link link-success disabled"
                                                    aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </button>
                                        <#else>
                                            <button id="prevPage" class="page-link link-success" aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </button>
                                        </#if>
                                    </li>
                                    <li class="page-item">
                                        <#if appeals.last>
                                            <button id="nextPage" class="page-link link-success disabled"
                                                    aria-label="Next">
                                                <span aria-hidden="true">&raquo;</span>
                                            </button>
                                        <#else>
                                            <button id="nextPage" class="page-link link-success" aria-label="Next">
                                                <span aria-hidden="true">&raquo;</span>
                                            </button>
                                        </#if>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://moment.github.io/luxon/global/luxon.min.js"></script>
    <script src="/js/clock.js"></script>
    <script src="../js/appeals.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', async () => {
            // document.querySelectorAll('.btn').forEach(button => {
            //     button.addEventListener('click', function () {
            //         document.querySelectorAll('.btn').forEach(btn => {
            //             btn.style.backgroundColor = (btn === this) ? '#28A745' : '';
            //             btn.style.color = (btn === this) ? '#fff' : '';
            //         });
            //     });
            // });

            let pizzeriaSelect = document.getElementById('pizzeria-select');
            let expertSelect = document.getElementById('expert-select');

            await getPizzeria();
            await getExperts();

            async function getPizzeria() {
                try {
                    const response = await fetch('/api/pizzerias');
                    const data = await response.json();
                    data.forEach(function (pizzeria) {
                        let option = document.createElement('option');
                        option.value = pizzeria.id;
                        option.textContent = pizzeria.name;

                        if (${currentPizzeria} === pizzeria.id) {
                            option.selected = true;
                        }


                        pizzeriaSelect.appendChild(option);
                    });
                } catch (error) {
                    console.error('Ошибка при загрузке данных:', error);
                }
            }

            async function getExperts() {
                try {
                    const response = await fetch('/api/experts');
                    const data = await response.json();
                    data.forEach(function (expert) {
                        let option = document.createElement('option');
                        option.value = expert.id;
                        option.textContent = expert.name + " " + expert.surname;
                        console.log(${currentExpert})
                        if (${currentExpert} === expert.id) {
                            option.selected = true;
                        }

                        expertSelect.appendChild(option);
                    });
                } catch (error) {
                    console.error('Ошибка при загрузке данных:', error);
                }
            }
        });
    </script>
    <script src="../js/appealsCount.js">
    </script>
</@main.layout>