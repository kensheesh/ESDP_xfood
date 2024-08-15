<#import '../layout.ftlh' as main>
<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <link rel="stylesheet" href="/css/profile.css">

    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                <#assign userRole = authorizedUser.authorities[0].authority>
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3">
                        <div class="col">
                            <div class="row <#if userRole == "ROLE_EXPERT">rol-cols-4<#else>row-cols-3</#if> row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <#if userRole == "ROLE_EXPERT">
                                        <a href="/my-opportunities" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-calendar2-week fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline d-lg-none">Возможности</span>
                                            <span class="d-none d-lg-inline">Карта возможностей</span>
                                        </a>
                                    <#else>
                                        <a href="/weekly" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-calendar2-range fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline">График</span>
                                        </a>
                                    </#if>
                                </div>
                                <#if userRole == "ROLE_SUPERVISOR" || userRole == "ROLE_ADMIN">
                                    <div class="col">
                                        <a href="/opportunities"
                                           class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-calendar2-week fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline">Возможности</span>
                                        </a>
                                    </div>
                                </#if>
                                <div class="col button-wrapper">
                                    <a href="/checks" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <span class="d-none d-md-inline">Проверки</span>
                                        <i class="bi bi-clipboard-check fs-5 d-md-none"></i>
                                    </a>
                                    <#if (checksCount?has_content && checksCount > 0)>
                                        <div class="badge-notification-icon">
                                            <div class="badge-notification-num">${checksCount}</div>
                                        </div>
                                    <#elseif (checksCount?has_content && checksCount > 99)>
                                        <div class="badge-notification-icon">
                                            <div class="badge-notification-num">99</div>
                                        </div>
                                    </#if>
                                </div>
                                <div class="col">
                                    <a href="/analytics" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-bar-chart-fill fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">Аналитика</span>
                                    </a>
                                </div>
                                <#if userRole == "ROLE_SUPERVISOR" || userRole == "ROLE_ADMIN">
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
                                </#if>
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
        <div class  ="col-md-8 col-xl-9">
            <h1 class="mb-4">Личный кабинет
                <#if user.role == "SUPERVISOR">
                    руководителя
                <#else>
                    эксперта
                </#if>
            </h1>
            <div class="row justify-content-center row-gap-3">
                <div class="col-md-6">
                    <div class="card h-100 border-0">
                        <div class="card-body p-3 d-flex flex-column justify-content-between shadow-sm rounded-4">
                            <h4 class="card-title">${user.name} ${user.surname}</h4>
                            <p class="card-text">Email: ${user.email}</p>
                            <div class="mt-3" role="group" aria-label="User actions">
                                <button type="button" class="btn btn-dodo rounded-4 w-100 px-5">
                                    История вознаграждений
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card h-100 border-0 shadow-sm rounded-4">
                        <div class="card-body d-flex flex-column justify-content-between">
                            <h3 class="fs-4 mb-4">Смена часовых поясов</h3>
                            <div class="row row-gap-3">
                                <div class="col-xl-8 px-2">
                                    <div class="dropdown w-100">
                                        <div class="select-btn rounded-4 row row-cols-2 align-items-center justify-content-between w-100 m-0">
                                            <div class="col-11 px-0">
                                                <span class="btn-text">Выбери часовые пояса</span>
                                            </div>
                                            <div class="col-1 px-0">
                                                <span class="arrow-dwn"><i class="fa-solid fa-chevron-down"></i></span>
                                            </div>
                                        </div>
                                        <ul class="list-items dropdown-menu w-100">
                                            <button type="button" class="btn btn-reset rounded-4 w-100 mb-3">Сбросить
                                                выборы
                                            </button>
                                            <input type="text" id="timezone-search" class="form-control rounded-4 mb-3"
                                                   placeholder="Поиск часовых поясов...">
                                            <div id="timezone-items">
                                            </div>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-xl-4 px-2">
                                    <button class="btn btn-green rounded-4 p-2 h-100 col-auto col-xl-12"
                                            type="submit" id="saveBtn">Сохранить
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://moment.github.io/luxon/global/luxon.min.js"></script>
    <script src="/js/profile.js"></script>
    <script src="../js/appealsCount.js">
</@main.layout>