<#import "../layout.ftlh" as main>
<@main.layout>
    <#assign countChecks = 0>
    <#assign sumRewards = 0>
    <#if expert??>
        <#assign countChecks = expert.countChecks>
        <#assign sumRewards = expert.getSumRewards()>
    </#if>

    <#if experts??>
        <#list experts as expert>
            <#assign countChecks += expert.countChecks>
            <#assign sumRewards += expert.getSumRewards()>
        </#list>
    </#if>

    <div class="row">
        <div class="card col-lg-8">
            <div class="card-body">
                <h3>Вознаграждение</h3>
                <div class="d-flex flex-wrap mb-3">
                    <div class="p-2 text-white me-2 reward-info">Количество проверок: ${countChecks}</div>
                    <div class="p-2 text-white reward-info">Сумма вознаграждения: ${sumRewards} Р</div>
                </div>

                <table class="table mt-3 table-striped table-hover">
                    <thead>
                    <tr>
                        <th>Дата</th>
                        <th>Пиццерия</th>
                        <#if experts??>
                            <th>Эксперт</th>
                        </#if>
                        <th>Вознаграждение</th>
                        <th>Чек лист</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if expert??>
                        <#list expert.getCheckListRewards() as reward>
                            <tr>
                                <td>${reward.getEndDate()}</td>
                                <td>${reward.getPizzeriaName()}</td>
                                <td>${reward.getSumRewards()} Р</td>
                                <td><a href="/checks/${reward.checklistUUID}/result">Перейти</a></td>
                            </tr>
                        </#list>
                    </#if>
                    <#if experts??>
                        <#list experts as expert>
                            <#list expert.getCheckListRewards() as reward>
                                <tr>
                                    <td>${reward.getEndDate()}</td>
                                    <td>${reward.getPizzeriaName()}</td>
                                    <td>${reward.getExpertName()}</td>
                                    <td>${reward.getSumRewards()} Р</td>
                                    <td><a href="/checks/${reward.checklistUUID}/result">Перейти</a></td>
                                </tr>
                            </#list>
                        </#list>
                    </#if>
                    </tbody>
                </table>

                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center mt-4">
                        <li class="page-item">
                            <a class="page-link" href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="card">
                <div class="card-body">
                    <h6>Фильтры</h6>
                    <form action="/reward" method="GET">
                        <#if experts??>
                            <div class="mb-3">
                                <label for="pizzeria-select" class="form-label">Пиццерия</label>
                                <select class="form-select" id="pizzeria-select" name="pizzeria">
                                    <option value="default">Все</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="manager-select" class="form-label">Эксперт</label>
                                <select class="form-select" id="expert-select" name="expert">
                                    <option value="default">Все</option>
                                </select>
                            </div>
                        </#if>

                        <div class="mb-3">
                            <label for="date-from" class="form-label">От</label>
                            <input type="date" value="<#if currentStartDate??>${currentStartDate}</#if>" class="form-control" id="date-from" name="startDate">
                        </div>
                        <div class="mb-3">
                            <label for="date-to" class="form-label">До</label>
                            <input type="date" value="<#if currentEndDate??>${currentEndDate}</#if>" class="form-control" id="date-to" name="endDate">
                        </div>
                        <button class="btn btn-outline-success w-100" type="submit">Применить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <style>
        .reward-info {
            background-color: #20b2aa;
            margin-right: 10px;
            padding: 10px;
            border-radius: 5px;
        }

        .table thead {
            background-color: #f8f9fa;
        }

        .table-hover tbody tr:hover {
            background-color: #f1f1f1;
        }

        .pagination .page-link {
            color: #007bff;
        }

        .pagination .page-item.active .page-link {
            background-color: #007bff;
            border-color: #007bff;
        }
    </style>

    <script>
        document.addEventListener('DOMContentLoaded', async function () {
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
                        <#if currentPizzeria != "default">
                        if (${currentPizzeria} === pizzeria.id) {
                            option.selected = true;
                        }
                        </#if>

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
                        <#if currentExpert != "default">
                        if (${currentExpert} === expert.id) {
                            option.selected = true;
                        }
                        </#if>
                        expertSelect.appendChild(option);
                    });
                } catch (error) {
                    console.error('Ошибка при загрузке данных:', error);
                }
            }
        });
    </script>
</@main.layout>
