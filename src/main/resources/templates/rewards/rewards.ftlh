<#import "../layout.ftlh" as main>
<@main.layout>

    <div class="row">
        <div class="card col-lg-8">
            <div class="card-body">
                <h3>Вознаграждение</h3>
                <div class="d-flex flex-wrap mb-3">
                    <div class="p-2 text-white me-2" style="background-color: #20b2aa;">Количество
                        проверок:
                    </div>
                    <div class="p-2 text-white" style="background-color: #ff6347;">Сумма
                        вознаграждение: Р
                    </div>
                </div>

                <table class="table mt-3">
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
                                    <option value="default">Пиццерия</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="manager-select" class="form-label">Эксперт</label>
                                <select class="form-select" id="manager-select" name="manager">
                                    <option value="default">Эксперт</option>
                                </select>
                            </div>
                        </#if>

                        <div class="mb-3">
                            <label for="date-from" class="form-label">От</label>
                            <input type="date" class="form-control" id="date-from" name="startDate">
                        </div>
                        <div class="mb-3">
                            <label for="date-to" class="form-label">До</label>
                            <input type="date" class="form-control" id="date-to" name="endDate">
                        </div>
                        <button class="btn btn-outline-success w-100" type="submit">Применить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</@main.layout>
