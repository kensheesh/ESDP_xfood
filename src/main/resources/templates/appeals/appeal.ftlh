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
                                <label for="name" class="form-label">Имя и фамилия</label>
                                <input type="text" class="form-control" id="name" value="Улыбин Дмитрий" required>
                            </div>
                            <div class="mb-3">
                                <label for="pizzeria" class="form-label">Пиццерия</label>
                                <input type="text" class="form-control" id="pizzeria" value="Кольчугино-1" required>
                            </div>
                            <div class="mb-3">
                                <label for="period" class="form-label">Период</label>
                                <input type="text" class="form-control" id="period" value="04.15 - 04.21" required>
                            </div>
                            <div class="mb-3">
                                <label for="criterion" class="form-label">Критерий</label>
                                <input type="text" class="form-control" id="criterion" value="Все нарушения по продукту" required>
                            </div>
                            <div class="mb-3">
                                <label for="violation" class="form-label">Нарушение</label>
                                <input type="text" class="form-control" id="violation" value="Дно плохо прорезано" required>
                            </div>
                            <div class="mb-3">
                                <label for="photo-report" class="form-label">Фотоотчет</label>
                                <input type="url" class="form-control" id="photo-report" value="https://control.dodois.io/backoffice/checkups/11EEFC845CD3517682991EAC2A3758C0" required>
                            </div>
                            <div class="mb-3">
                                <label for="violation-link" class="form-label">Ссылка на нарушение в БЗ</label>
                                <input type="url" class="form-control" id="violation-link" value="https://dodopizza.info/rating/#D639A0C7B06924911EEC1CFE11984C3/1?ratingPeriodId=11EEF8A5E09C9B0A" required>
                            </div>
                            <div class="mb-3">
                                <label for="appeal-link" class="form-label">Укажите ссылку на апеллируемый тикет из таблицы рейтинга</label>
                                <input type="url" class="form-control" id="appeal-link">
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