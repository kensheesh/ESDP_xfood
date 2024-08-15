<#import "../layout.ftlh" as main>
<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <div class="row row-cols-md-2 row-gap-3">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 shadow-sm border-0">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <div class="col">
                            <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <a href="/deadlines" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-hourglass-split fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">
                                                        Дедлайны
                                                    </span>
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="#" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-key fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">
                                                        Токены
                                                    </span>
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="#" class="btn disabled rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-clipboard-plus fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">
                                                        Шаблоны
                                                    </span>
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/users" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                                    <span class="d-none d-md-inline">
                                                        Пользователи
                                                    </span>
                                        <i class="bi bi-people d-md-none fs-5"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-8 col-xl-9">
            <div class="text-end">
                <a href="/templates/create" class="btn btn-secondary">+ Добавить шаблон</a>
            </div>
            <#list templates as template>
                <div class="card my-2 text-decoration-none border border-secondary">
                    <div class="card-body py-1 mb-0">
                        <span><b>${template.name?capitalize}</b></span>
                        <div class="row">
                            <span class="col-3">Кол. критериев: <b>${template.numsOfCriteria}</b></span>
                            <span class="col-3">Макс. балл: <b>${template.totalValue}</b></span>
                            <span class="col-3">Вознаграждение: <b>${template.fee}₽</b></span>
                            <div class="btn btn-secondary col-1 ms-auto" data-id="${template.id}" onclick="handleEditClick(this)"><i class="bi bi-pencil-fill"></i></div>
                            <div class="btn btn-warning col-1 ms-auto text-white" id="delete_btn" data-id="${template.id}" onclick="handleDeleteClick(this)"><i class="bi bi-trash-fill"></i></div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>

    <script>
        function handleEditClick(element) {
            const selectedValue = element.getAttribute('data-id');
            window.location.href = '/templates/' + selectedValue;
        }

        function handleDeleteClick(element) {
            const selectedValue = element.getAttribute('data-id');
            window.location.href = '#' + selectedValue;
        }
    </script>

</@main.layout>