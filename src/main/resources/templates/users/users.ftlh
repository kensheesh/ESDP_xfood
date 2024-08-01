<#import "../layout.ftlh" as main>
<@main.layout>

    <style>

        .user-info {
            flex-grow: 1;
        }

        .card-title {
            font-size: 1.25rem;
            font-weight: 600;
        }

        .card-text {
            margin-bottom: 5px;
        }

        .card-container {
            margin-bottom: 20px;
        }
    </style>

    <div class="container">
        <div class="row">
            <div class="card-container col-lg-8">
                <#list users as user>
                    <div class="card mb-2">
                        <div class="card-body d-flex">
                            <div class="user-info">
                                <h5 class="card-title">ФИО: ${user.getName()} ${user.getSurname()}</h5>
                                <p class="card-text">Роль: ${user.getRole()}</p>
                                <p class="card-text">Почта: ${user.getEmail()}</p>
                                <p class="card-text">Телефон: ${user.getPhoneNumber()}</p>
                                <p class="card-text">Enabled: <#if user.getEnabled() == true>Да<#else>Нет</#if></p>
                            </div>
                            <div class="card-icons">
                                <i class="fas fa-edit"></i>
                                <i class="fas fa-trash-alt"></i>
                            </div>
                        </div>
                    </div>
                </#list>

                <nav aria-label="Page navigation example">
                    <ul class="pagination">
                        <li class="page-item">
                            <a class="page-link" href="/admin/users?role=${currentRole}&page=${currentPage-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>
                        <li class="page-item"><a class="page-link" href="/admin/users?role=${currentRole}&page=0">1</a></li>
                        <li class="page-item"><a class="page-link" href="/admin/users?role=${currentRole}&page=1">2</a></li>
                        <li class="page-item"><a class="page-link" href="/admin/users?role=${currentRole}&page=2">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="/admin/users?role=${currentRole}&page=${currentPage+1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Next</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>


            <div class="col-lg-4">
                <div class="card">
                    <div class="card-body">
                        <h6>Фильтры</h6>
                        <form action="/admin/users" method="GET">
                            <div class="mb-3">
                                <label for="role-select" class="form-label">Тип пользователя</label>
                                <select class="form-select" id="role-select" name="role">
                                    <option value="default">Все</option>
                                </select>
                            </div>
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" placeholder="Поиск" aria-label="Recipient's username" aria-describedby="basic-addon2">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-secondary" type="button">Найти</button>
                                </div>
                            </div>
                            <button class="btn btn-outline-success w-100" id="buttonFilter" type="submit">Применить</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>

        document.addEventListener('DOMContentLoaded', async () => {
            const roleSelect = document.getElementById('role-select')
            async function getRoles() {
                const response = await fetch('/api/role');
                const data = await response.json();
                console.log(data);
                data.forEach(function (role) {
                    let option = document.createElement('option');
                    option.value = role;
                    option.textContent = role;

                    console.log(role)
                    if ('${currentRole}' === role) {
                        option.selected = true;
                    }


                    roleSelect.appendChild(option);
                });
            }

            await getRoles();
        })

    </script>

</@main.layout>
