<#import "../layout.ftlh" as main>
<@main.layout>

    <style>
        .file-item {
            display: flex;
            align-items: center;
            margin-bottom: 5px;
        }
        .file-item span {
            flex-grow: 1;
        }
        .remove-file {
            cursor: pointer;
        }
    </style>

    <div class="container mt-5">
        <div class="row">
            <div class="col-md-10 offset-md-1">
                <div class="card">
                    <div class="card-header bg-warning text-white">
                        Подать апелляцию
                    </div>
                    <div class="card-body">
                        <form action="/api/appeal" method="post" enctype="multipart/form-data">
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
                                <input type="text" class="form-control" disabled id="criterion" value="${appeal.checkListsCriteria.criteria.description}" required>
                            </div>
                            <div class="mb-3">
                                <label for="photo-report" class="form-label">Email</label>
                                <input type="email" class="form-control" id="photo-report" required>
                            </div>
                            <div class="mb-3">
                                <label for="photo-report" class="form-label">Имя и фамилия</label>
                                <input type="text" class="form-control" id="photo-report" required>
                            </div>
                            <div class="mb-3">
                                <label for="violation-link" class="form-label">Ссылка на сообщение в тг</label>
                                <input type="url" class="form-control" id="violation-link" required>
                            </div>
                            <div class="mb-3">
                                <label for="comment" class="form-label">Комментарий</label>
                                <textarea class="form-control" id="comment" rows="3" required></textarea>
                            </div>
                            <div class="mb-3">
                                <div id="file-upload-container">
                                    <div class="file-upload">
                                        <input type="file" class="form-control" name="files[]" multiple>
                                    </div>
                                </div>
                                <button onclick="addFileInput()">Добавить файл</button>
                            </div>

                            <button type="submit" class="btn btn-warning text-white">Отправить</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function addFileInput() {
            const container = document.getElementById('file-upload-container');
            const newInput = document.createElement('div');
            newInput.className = 'file-upload';
            newInput.innerHTML = '<input type="file" class="form-control" name="files[]" multiple>';
            container.appendChild(newInput);
        }
    </script>

</@main.layout>