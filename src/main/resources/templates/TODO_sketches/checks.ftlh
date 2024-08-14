<#import "../layout.ftlh" as main>

<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 shadow-sm border-0">
                <#assign userRole = authorizedUser.authorities[0].authority>
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3">
                        <div class="col">
                            <div class="row <#if userRole == "ROLE_EXPERT">rol-cols-4<#else>row-cols-3</#if> row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <#if userRole == "ROLE_EXPERT">
                                        <a href="/expert/opportunities" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-calendar2-week fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline d-lg-none">Возможности</span>
                                            <span class="d-none d-lg-inline">Карта возможностей</span>
                                        </a>
                                    <#else>
                                        <a href="/supervisor/weekly" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-calendar2-range fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline">График</span>
                                        </a>
                                    </#if>
                                </div>
                                <#if userRole == "ROLE_SUPERVISOR" || userRole == "ROLE_ADMIN">
                                    <div class="col">
                                        <a href="/supervisor/opportunities"
                                           class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                            <i class="bi bi-calendar2-week fs-5 d-md-none"></i>
                                            <span class="d-none d-md-inline">Возможности</span>
                                        </a>
                                    </div>
                                </#if>
                                <div class="col button-wrapper">
                                    <a href="#" class="btn disabled rounded-4 w-100 h-100 btn-dodo-light">
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
        <div class="col-md-8 col-xl-9">


            <div class="card rounded-4 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3">
                        <div class="col">
                            <div class="row row-gap-2">
                                <div class="col-auto ps-3 pe-1">
                                    <button id="newChecks" type="button" class="btn btn-light-green rounded-4">Новые</button>
                                </div>
                                <div class="col-auto px-1">
                                    <button id="inProgressChecks" type="button" class="btn btn-light-green rounded-4">Активные</button>
                                </div>
                                <div class="col-auto px-1">
                                    <button id="doneChecks"       type="button" class="btn btn-light-green rounded-4">Завершенные</button>
                                </div>
                                <#if userRole != "ROLE_EXPERT">
                                    <div class="col-auto px-1">
                                        <button id="deletedChecks" type="button" class="btn btn-light-green rounded-4">Удаленные</button>
                                    </div>
                                </#if>
                            </div>
                        </div>

                        <div class="col">
                            <div id="checks-container" class="row row-cols-1 row-gap-2 mt-2 overflow-y-auto align-content-start" style="height: 58vh;">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <style>
        /*.active {*/
        /*    !*background-col: #2857a7; !* Change this to the color you want *!*!*/
        /*    !*color: #ffffff;*!*/
        /*    !*background-color: #28A745;*!*/
        /*}*/
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://moment.github.io/luxon/global/luxon.min.js"></script>
    <script src="/js/clock.js"></script>
    <script src="../js/checks.js"></script>
    <script src="../js/appealsCount.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            document.querySelectorAll('.btn').forEach(button => {
                button.addEventListener('click', function() {
                    document.querySelectorAll('.btn').forEach(btn => {
                        btn.style.backgroundColor = (btn === this) ? '#28A745' : '';
                        btn.style.color = (btn === this) ? '#fff' : '';
                    });
                });
            });
        });
    </script>
</@main.layout>