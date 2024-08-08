<#import "../layout.ftlh" as main>
<link rel="stylesheet" href="/css/appeal.css">
<@main.layout>
    <div class="container">
        <div class="row">
            <div class="col-md-10 offset-md-1">
                <div class="card">
                    <div class="card-header text-white">
                        Подать апелляцию
                    </div>
                    <div class="card-body">
                        <form action="/api/appeal/${appeal.id}" id="form" method="post" enctype="multipart/form-data">
                            <div class="mb-3">
                                <label for="name" class="form-label">Менеджер</label>
                                <input type="text" class="form-control" disabled id="name" value="${appeal.managerName} ${appeal.managerSurname}" required>
                            </div>
                            <div class="mb-3">
                                <label for="pizzeria" class="form-label">Пиццерия</label>
                                <input type="text" class="form-control" disabled id="pizzeria" value="${appeal.pizzeriaName}" required>
                            </div>
                            <div class="mb-3">
                                <label for="period" class="form-label">Дата</label>
                                <input type="text" class="form-control" disabled id="period" value="${appeal.date}" required>
                            </div>
                            <div class="mb-3">
                                <label for="criterion" class="form-label">Критерий</label>
                                <textarea class="form-control textarea-criteria" name="text" id="text" rows="3" disabled required>${appeal.criteriaDescription}</textarea>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" name="email" id="email" required>
                                <div class="error-message" id="email-error"></div>
                            </div>
                            <div class="mb-3">
                                <label for="fullName" class="form-label">Имя и фамилия</label>
                                <input type="text" class="form-control" name="fullName" id="fullName" required>
                                <div class="error-message" id="fullName-error"></div>
                            </div>
                            <div class="mb-3">
                                <label for="tgLinkMessage" class="form-label">Ссылка на сообщение в тг</label>
                                <input type="url" class="form-control" name="tgLinkMessage" id="tgLinkMessage" required>
                                <div class="error-message" id="tgLinkMessage-error"></div>
                            </div>
                            <div class="mb-3">
                                <label for="linkToExternalSrc" class="form-label">Ссылка на чек-лист</label>
                                <input type="url" class="form-control" name="linkToExternalSrc" id="linkToExternalSrc">
                                <div class="error-message" id="linkToExternalSrc-error"></div>
                            </div>
                            <div class="mb-3">
                                <label for="comment" class="form-label">Комментарий</label>
                                <textarea class="form-control" name="comment" id="comment" rows="3" required></textarea>
                                <div class="error-message" id="comment-error"></div>
                            </div>

                            <div class="mb-3">
                                <div id="file-upload-container">
                                </div>
                                <button class="mt-2 btn btn-green" type="button" onclick="addFileInput()">Добавить файл</button>
                                <button class="mt-2 btn btn-secondary" type="button" onclick="toggleEditFiles()">Редактировать файлы</button>
                                <div class="mt-2">
                                    <p style="margin-top: 2px">Фото - не более 5 шт, каждый файл не более 3 мб.<br>
                                    Видео - не более 4 шт, каждый файл не более 25 мб.</p>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-warning text-white">Отправить</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="successModalLabel">Успех</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Апелляция успешно отправлена.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>

    <script src="../js/appeal.js"></script>
</@main.layout>