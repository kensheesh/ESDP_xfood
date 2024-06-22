<#import '../layout.ftlh' as main>
<@main.layout>

    <link rel="stylesheet" href="/css/profile.css">
    <main class="container mt-4">
        <div class="row row-cols-md-2 row-gap-3">
            <div class="col-md-4 col-xl-3 bg-body sidebar">
                <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                    <div class="card-body">
                        <div class="row row-cols-1 row-gap-3 h-100">
                            <div class="col">
                                <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                    <div class="col">
                                        <a href="/abilities" class="btn btn-light rounded-4 w-100 h-100 ">
                                            График
                                        </a>
                                    </div>
                                    <div class="col">
                                        <a href="/expert/checks" class="btn btn-light rounded-4 w-100 h-100">
                                            Проверки
                                        </a>
                                    </div>
                                    <div class="col">
                                        <a href="/analytics" class="btn btn-light rounded-4 w-100 h-100">
                                            Аналитика
                                        </a>
                                    </div>
                                    <div class="col">
                                        <div class="dropdown d-md-none h-100">
                                            <button class="btn btn-light rounded-4 dropdown-toggle w-100 h-100"
                                                    type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                <i class="bi bi-clock"></i>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <section class="times_second"></section>
                                            </ul>
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
                <h1 class="mb-4">Личный кабинет
                    <#if user.role == "SUPERVISOR">
                        руководителя
                    <#else>
                        эксперта
                    </#if>
                </h1>
                <div class="row justify-content-center">
                    <div class="col-md-6">
                        <div class="card border-secondary-subtle">
                            <div class="card-body p-3">
                                <h4 class="card-title">${user.name} ${user.surname}</h4>
                                <p class="card-text">Email: ${user.email}</p>
                                <div class="btn-group mt-3" role="group" aria-label="User actions">
                                    <button type="button" class="btn btn-outline-primary px-5">
                                        История вознаграждений
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="border rounded-1 p-3 border-1 border-secondary-subtle">
                            <h3 class="fs-4 mb-4">Смена часовых поясов</h3>
                            <div class="row" style="margin-bottom: 1.75rem;">
                                <div class="col-8">
                                    <div class="select-btn">
                                        <span class="btn-text">Select Language</span>
                                        <span class="arrow-dwn"><i class="fa-solid fa-chevron-down"></i></span>
                                    </div>
                                    <ul class="list-items" id="languages-list">

                                    </ul>
                                </div>
                                <div class="col-4">
                                    <button class="btn btn-outline-success rounded-2 p-2" style="margin-top: 2.5px"
                                            type="submit" id="saveBtn">Сохранить
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </main>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://moment.github.io/luxon/global/luxon.min.js"></script>
    <script src="/js/profile.js"></script>

</@main.layout>