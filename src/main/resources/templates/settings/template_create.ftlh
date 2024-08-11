<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container my-5">
        <div class="border rounded shadow-sm p-4">
            <h2 class="mb-4">Создать шаблон</h2>
            <form>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="templateName" class="form-label">Название шаблона:</label>
                            <input type="text" class="form-control" id="templateName" name="templateName" minlength="2" required maxlength="200" placeholder="Введите название шаблона">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="templatePrice" class="form-label">Вознаграждение:</label>
                            <input type="number" class="form-control" id="templatePrice" name="templatePrice" required min="1" placeholder="Введите вознаграждение">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</@main.layout>
