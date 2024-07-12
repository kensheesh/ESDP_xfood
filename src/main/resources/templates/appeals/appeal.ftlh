<#import "../layout.ftlh" as main>
<@main.layout>

    <style>
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
                                <input type="text" class="form-control" disabled id="name" value="${appeal.checkListsCriteria.checklist.workSchedule.manager.getName()} ${appeal.checkListsCriteria.checklist.workSchedule.manager.getSurname()}" required>
                            </div>
                            <div class="mb-3">
                                <label for="pizzeria" class="form-label">Пиццерия</label>
                                <input type="text" class="form-control" disabled id="pizzeria" value="${appeal.checkListsCriteria.checklist.workSchedule.pizzeria.getName()}" required>
                            </div>
                            <div class="mb-3">
                                <label for="period" class="form-label">Дата</label>
                                <input type="text" class="form-control" disabled id="period" value="${appeal.checkListsCriteria.checklist.opportunity.date}" required>
                            </div>
                            <div class="mb-3">
                                <label for="criterion" class="form-label">Критерий</label>
                                <textarea class="form-control textarea-criteria" name="text" id="text" rows="3" disabled required>${appeal.checkListsCriteria.criteria.description}</textarea>
                            </div>
                            <div class="mb-3">
                                <label for="photo-report" class="form-label">Email</label>
                                <input type="email" class="form-control" name="email" id="photo-report" required>
                            </div>
                            <div class="mb-3">
                                <label for="photo-report" class="form-label">Имя и фамилия</label>
                                <input type="text" class="form-control" name="fullName" id="photo-report" required>
                            </div>
                            <div class="mb-3">
                                <label for="violation-link" class="form-label">Ссылка на сообщение в тг</label>
                                <input type="url" class="form-control" name="tgLinkMessage" id="violation-link" required>
                            </div>
                            <div class="mb-3">
                                <label for="comment" class="form-label">Комментарий</label>
                                <textarea class="form-control" name="comment" id="comment" rows="3" required></textarea>
                            </div>

                            <div class="mb-3">
                                <div id="file-upload-container">
                                </div>
                                <button class="mt-2 btn btn-green" type="button" onclick="addFileInput()">Добавить файл</button>
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
        function addFileInput() {
            const container = document.getElementById('file-upload-container');
            const newInput = document.createElement('div');
            newInput.className = 'file-upload';
            newInput.innerHTML = '<input type="file" class="form-control fileUploader mt-3" name="files[]" multiple><div class="error-message"></div>';
            container.appendChild(newInput);
            initializeFileUploaders();
        }

        function initializeFileUploaders() {
            const fileUploaders = document.querySelectorAll('.fileUploader');

            fileUploaders.forEach(fileUploader => {
                fileUploader.addEventListener('change', (event) => {
                    const file = event.target.files[0];
                    const errorMessageElement = event.target.nextElementSibling;
                    if (file) {
                        validateFileType(file, event.target, errorMessageElement);
                        console.log(file);
                    }
                });
            });
        }

        function validateFileType(file, input, errorMessageElement) {
            const acceptedTypes = ['png', 'jpg', 'jpeg', 'mp4', 'mov'];
            const acceptedTypesLong = ['image/png', 'image/jpg', 'image/jpeg', 'video/mp4', 'video/quicktime'];

            const nameExtension = file.name.split('.').pop().toLowerCase();

            if (!acceptedTypes.includes(nameExtension)) {
                input.value = null;
                errorMessageElement.textContent = "Загрузите фотографию или видео в формате png, jpg, jpeg, mp4 или mov";
                errorMessageElement.style.display = "block";
            } else if (!acceptedTypesLong.includes(file.type)) {
                input.value = null;
                errorMessageElement.textContent = "Загрузите фотографию или видео в формате png, jpg, jpeg, mp4 или mov";
                errorMessageElement.style.display = "block";
            } else {
                errorMessageElement.textContent = "";
                errorMessageElement.style.display = "none";
            }

            return true;
        }

        document.getElementById('form').addEventListener('submit', function(event) {
            event.preventDefault();

            const form = event.target;
            const formData = new FormData(form);

            fetch(form.action, {
                method: form.method,
                body: formData
            }).then(response => {
                if (response.ok) {
                    $('#successModal').modal('show');
                    setTimeout(function() {
                        window.location.href = '/checks/${appeal.checkListsCriteria.checklist.uuidLink}/result';
                    }, 3000);
                } else {
                    alert('Ошибка при отправке формы. Попробуйте еще раз.');
                }
            }).catch(error => {
                console.error('Error:', error);
                alert('Ошибка при отправке формы. Попробуйте еще раз.');
            });
        });

        initializeFileUploaders();

    </script>

</@main.layout>
