<#import "../layout.ftlh" as main>

<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 h-100 shadow-sm border-0">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <#assign userRole = authorizedUser.authorities[0].authority>
                        <div class="col">
                            <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <a href="
                                            <#if userRole == "ROLE_SUPERVISOR">
                                                /supervisor/weekly
                                            <#else>
                                                /expert/opportunities
                                            </#if>
                                        " class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        График
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="#" class="btn rounded-4 w-100 h-100 disabled btn-dodo-light">
                                        Проверки
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/analytics" class="btn rounded-4 w-100 h-100 btn-dodo-light">
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
                                            <li class="dropdown-item">МСК 14:45</li>
                                            <li class="dropdown-item">ЕКБ 16:45</li>
                                            <li class="dropdown-item">БИШ 17:45</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col align-self-end d-none d-md-block">
                            <div class="card rounded-4 border-0 shadow-sm">
                                <div class="card-body">
                                    <div class="d-flex flex-column row-gap-2 text-center">
                                        <span>МСК 14:45</span>
                                        <span>ЕКБ 16:45</span>
                                        <span>БИШ 17:45</span>
                                    </div>
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
                            <button id="newChecks"        type="button" class="btn btn-light-green rounded-4">Новые</button>
                            <button id="inProgressChecks" type="button" class="btn btn-light-green rounded-4">Активные</button>
                            <button id="doneChecks"       type="button" class="btn btn-light-green rounded-4">Завершенные</button>
                        </div>

                        <div class="col">
                            <div class="row row-cols-1 row-gap-2 mt-2 overflow-y-auto" style="height: 58vh;">
                                <div id="checks-container"></div>
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
    <script src="../js/checks.js"></script>
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