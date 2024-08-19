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
                                    <button class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-key fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline" data-bs-toggle="modal" data-bs-target="#tokenModal">
                                                        Токены
                                                    </span>
                                    </button>
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
            <div class="overflow-x-auto">
                <#list templates as template>
                    <div class="card my-2 text-decoration-none border-0 shadow-sm">
                        <div class="card-body py-1 mb-0" >
                            <span><b>${template.name?capitalize}</b></span>
                            <div class="row" style="min-width: 500px;">
                                <span class="col-3">Кол. критериев: <b>${template.numsOfCriteria}</b></span>
                                <span class="col-3">Макс. балл: <b>${template.totalValue}</b></span>
                                <span class="col-3">Вознаграждение: <b>${template.fee}₽</b></span>
                                <div class="btn btn-secondary col-1 ms-auto d-flex align-items-center justify-content-center" data-id="${template.id}" onclick="handleEditClick(this)"><i class="bi bi-pencil-fill"></i></div>
                                <div class="btn btn-warning col-1 ms-auto text-white d-flex align-items-center justify-content-center" id="delete_btn" data-id="${template.id}" onclick="handleDeleteClick(this)"><i class="bi bi-trash-fill"></i></div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <div class="modal fade" id="tokenModal" tabindex="-1" aria-labelledby="tokenModal"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content rounded-4">
                <div class="modal-header">
                    <h5 class="modal-title" id="tokenModal">
                        Привязать токен доступа
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>
                <form id="bearerForm">
                    <div class="modal-body">
                        <label for="bearer" class="form-label">Токен</label>
                        <input type="text" id="bearer" name="bearer" class="form-control" required>
                    </div>

                    <div class="modal-footer border-0">
                        <button type="submit" id="pushToken" class="btn btn-dodo rounded-4" data-bs-dismiss="modal"
                                data-bs-target="#tokenModal">
                            Сохранить токен
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </div>

    <script>
        document.getElementById('pushToken').addEventListener('click', async function () {
            const form = document.getElementById('bearerForm');
            const formData = new FormData(form);

            try {
                const response = await fetch('/api/bearer', {
                    method: 'POST',
                    body: formData
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                // Optionally, handle the response here
                const result = await response.json();
                console.log(result);

                // Close the modal if needed
                const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
                modal.hide();
            } catch (error) {
                console.error('There was a problem with the fetch operation:', error);
            }
        });
    </script>

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