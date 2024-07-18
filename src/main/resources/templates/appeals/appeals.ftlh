<#import "../layout.ftlh" as main>

<@main.layout>
    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 h-100 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <div class="col">
                            <div class="row row-cols-5 row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <a href="/supervisor/weekly" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        График
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/expert/checks" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        Проверки
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/analytics" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        Аналитика
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="#"
                                       class="btn rounded-4 w-100 h-100 btn-dodo-light position-relative disabled">
                                        Апелляции
                                        <span class="position-absolute top-0
                                                 start-100 translate-middle badge rounded-pill bg-dodo">
                                            <span id="counter">

                                            </span>
                                            <span class="visually-hidden">Апелляции</span>
                                        </span>
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
            <div class="card rounded-4 border-0 shadow-sm">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3">

                        <div class="col">
                            <button id="new" type="button"
                                    class="btn btn-light-green rounded-4">Новые</button>
                            <button id="accepted" type="button"
                                    class="btn btn-light-green rounded-4">Принятые</button>
                            <button id="rejected" type="button"
                                    class="btn btn-light-green rounded-4">Отклоненные</button>
                        </div>

                        <div class="col">
                            <div class="row row-cols-1 row-gap-2 mt-2 align-items-start overflow-y-scroll"
                            style="height: 55vh">
                                <div id="appeals" class="row row-cols-1 row-gap-2">
                                    <#list appeals.content as appeal>
                                        <a href="/appeals/${appeal.id}" class="col text-decoration-none d-block">
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
                                            <button id="prevPage" class="page-link link-success disabled" aria-label="Previous">
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
                                            <button id="nextPage" class="page-link link-success disabled" aria-label="Next">
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
    <script src="../js/appealsCount.js">
    </script>
</@main.layout>