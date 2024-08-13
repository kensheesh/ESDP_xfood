<#import "../layout.ftlh"as main>
<@main.layout>
    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#selectLocationModal">Добавить
        пиццерию
    </button>

    <!-- Modal for Location Selection -->
    <div class="modal fade" id="selectLocationModal" tabindex="-1" aria-labelledby="selectLocationLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="selectLocationLabel">Выберите локацию</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="locations"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="continue-to-pizzerias">Продолжить</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for Pizzeria Selection -->
    <div class="modal fade modal-xl" id="selectPizzeriaModal" tabindex="-1" aria-labelledby="selectPizzeriaLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="selectPizzeriaLabel">Выберите пиццерию</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row mb-4">
                            <div class="col-md-9">
                                <input class="form-control" type="search" placeholder="Введите ключивые слова"
                                       aria-label="searchPizzeria" id="pizzeria-search-input">
                            </div>
                            <div class="col-md-3">
                                <button class="btn btn-outline-success" id="find-pizzeria" type="button">Найти</button>
                            </div>
                        </div>
                        <div id="search-results"></div>
                        <nav aria-label="Page navigation">
                            <ul id="pagination" class="pagination"></ul>
                        </nav>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline-success w-100" id="load-pizzerias" type="button">Перезагрузить
                        пиццерии из "ДодоИС"
                    </button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>

    <script src="/js/pizzeriaManagement.js"></script>


</@main.layout>