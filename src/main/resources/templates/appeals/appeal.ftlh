<#import "../layout.ftlh" as main>
<style>
    .file-upload {
        position: relative;
        margin-bottom: 10px;
    }

    .file-upload input[type="file"] {
        display: inline-block;
        width: calc(100% - 100px);
    }

    .delete-button {
        position: absolute;
        right: 0;
        top: 8px;
        height: 2.38rem;
        margin-left: 10px;
    }

    .file-item span {
        flex-grow: 1;
    }

    .error-message {
        color: red;
        font-size: 0.9em;
        margin-top: 5px;
        display: none;
    }

    .textarea-criteria {
        resize: none;
    }

    .card {
        border: none;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .card-header {
        background: linear-gradient(90deg, #ffcc00, #ff9900);
        padding: 15px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        font-size: 18px;
    }

    .btn-warning {
        background-color: #ff9900;
        border-color: #ff9900;
    }

    .btn-green {
        background-color: #28a745;
        border-color: #28a745;
        color: white;
    }

    .form-label {
        font-weight: bold;
    }

    .file-upload input {
        margin-top: 10px;
    }

    .modal-header, .modal-footer {
        border: none;
    }

</style>
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
                                <label for="text" class="form-label">Критерий</label>
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
                                <button class="mt-2 btn btn-green" type="button" id="btnAdd">Добавить файл</button>
                                <button class="mt-2 btn btn-secondary" type="button" id="btnEdit">Редактировать файлы</button>
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

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('btnAdd').addEventListener('click', addFileInput)
            document.getElementById('btnEdit').addEventListener('click', toggleEditFiles)

            function addFileInput() {
                const container = document.getElementById('file-upload-container');
                const newInput = document.createElement('div');
                newInput.className = 'file-upload';
                newInput.innerHTML = `
        <input type="file" class="form-control fileUploader mt-3" name="files[]" multiple>
        <div class="error-message"></div>
    `;
                container.appendChild(newInput);
                initializeFileUploader();
            }

            function initializeFileUploader() {
                const fileUploader = document.querySelectorAll('.fileUploader');

                fileUploader.forEach(fileUploader => {
                    fileUploader.addEventListener('change', (event) => {
                        const file = event.target.files[0];
                        const errorMessageElement = event.target.nextElementSibling;
                        if (file) {
                            validateFileType(file, event.target, errorMessageElement);
                        }
                    });
                });
            }

            function validateFileType(file, input, errorMessageElement) {
                const acceptedTypes = ['png', 'jpg', 'jpeg', 'mp4', 'mov'];
                const acceptedTypesLong = ['image/png', 'image/jpg', 'image/jpeg', 'video/mp4', 'video/quicktime'];

                const nameExtension = file.name.split('.').pop().toLowerCase();

                if (!acceptedTypes.includes(nameExtension) || !acceptedTypesLong.includes(file.type)) {
                    input.value = null;
                    errorMessageElement.textContent = "Загрузите фотографию или видео в формате png, jpg, jpeg, mp4 или mov";
                    errorMessageElement.style.display = "block";
                } else {
                    errorMessageElement.textContent = "";
                    errorMessageElement.style.display = "none";
                }
            }

            document.getElementById('form').addEventListener('submit', function (event) {
                event.preventDefault();

                const fileUploader = document.querySelectorAll('.fileUploader');
                let allFilesValid = true;

                fileUploader.forEach(fileUploader => {
                    const file = fileUploader.files[0];
                    const errorMessageElement = fileUploader.nextElementSibling;
                    if (!file) {
                        errorMessageElement.textContent = "Загрузите фотографию или видео в формате png, jpg, jpeg, mp4 или mov.";
                        errorMessageElement.style.display = "block";
                        allFilesValid = false;
                    } else {
                        errorMessageElement.textContent = "";
                        errorMessageElement.style.display = "none";
                    }
                });

                if (!allFilesValid) {
                    return;
                }

                const form = event.target;
                const formData = new FormData(form);

                fetch(form.action, {
                    method: form.method,
                    body: formData
                }).then(response => {
                    if (response.ok) {
                        $('#successModal').modal('show');
                        setTimeout(function () {
                            window.location.href = '/checks/${appeal.checklistUuid}';
                        }, 3000);
                    } else {
                        response.json().then(data => {
                            handleValidationErrors(data.errors);
                        }).catch(error => {
                            alert('Ошибка при загрузке файлов. Пожалуйста, убедитесь, что:\n' +
                                '- Вы загружаете не более 5 фотографий, каждая не превышает 3 МБ.\n' +
                                '- Вы загружаете не более 4 видео-файлов, каждый не превышает 25 МБ.');
                        });
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('Ошибка при загрузке файлов. Пожалуйста, убедитесь, что:\n' +
                        '- Вы загружаете не более 5 фотографий, каждая не превышает 3 МБ.\n' +
                        '- Вы загружаете не более 4 видео-файлов, каждый не превышает 25 МБ.');
                });
            });

            function handleValidationErrors(errors) {
                errors.forEach(error => {
                    const errorMessageElement = document.getElementById(error.field + `-error`);
                    if (errorMessageElement) {
                        errorMessageElement.textContent = error.defaultMessage;
                        errorMessageElement.style.display = "block";
                    }
                });
            }

            let editingFiles = false;

            function toggleEditFiles() {
                const fileUploader = document.querySelectorAll('.file-upload');
                fileUploader.forEach(fileUpload => {
                    let deleteButton = fileUpload.querySelector('.delete-button');
                    if (editingFiles) {
                        if (deleteButton) {
                            deleteButton.remove();
                        }
                    } else {
                        if (!deleteButton) {
                            const newDeleteButton = document.createElement('button');
                            newDeleteButton.className = 'btn btn-danger delete-button mt-2';
                            newDeleteButton.textContent = 'Удалить';
                            newDeleteButton.onclick = function () {
                                fileUpload.remove();
                            };
                            fileUpload.appendChild(newDeleteButton);
                        }
                    }
                });
                editingFiles = !editingFiles;
            }

            initializeFileUploader();
        })
    </script>
</@main.layout>