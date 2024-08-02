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
            <div class="card-container col-lg-8" id="user-list">
                <#list users as user>
                    <div class="card mb-2 user-card">
                        <div class="card-body d-flex">
                            <div class="user-info">
                                <h5 class="card-title">ФИО: ${user.getName()} ${user.getSurname()}</h5>
                                <p class="card-text">Роль: ${user.getRole()}</p>
                                <p class="card-text">Почта: ${user.getEmail()}</p>
                                <p class="card-text">Телефон: ${user.getPhoneNumber()}</p>
                                <p class="card-text">Enabled: <#if user.getEnabled() == true>Да<#else>Нет</#if></p>
                            </div>
                            <div class="card-icons">
                                <button class="btn-edit" data-user-id="${user.getId()}"><i class="fas fa-edit"></i></button>
                                <button class="btn-delete" data-user-id="${user.getId()}"><i class="fas fa-trash-alt"></i></button>
                            </div>
                        </div>
                    </div>
                </#list>

                <nav aria-label="Page navigation example">
                    <ul class="pagination">
                        <#if currentPage gt 0>
                            <li class="page-item">
                                <a class="page-link" href="/admin/users?role=${currentRole}&page=${currentPage-1}&size=${currentSize}&search=${searchWord}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                    <span class="sr-only">Previous</span>
                                </a>
                            </li>
                        <#else>
                            <li class="page-item disabled">
                                <a class="page-link" href="#" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                    <span class="sr-only">Previous</span>
                                </a>
                            </li>
                        </#if>

                        <#list 0..(totalPages-1) as page>
                            <li class="page-item <#if page == currentPage>active</#if>">
                                <a class="page-link" href="/admin/users?role=${currentRole}&page=${page}&size=${currentSize}&search=${searchWord}">${page + 1}</a>
                            </li>
                        </#list>

                        <#if currentPage lt totalPages - 1>
                            <li class="page-item">
                                <a class="page-link" href="/admin/users?role=${currentRole}&page=${currentPage+1}&size=${currentSize}&search=${searchWord}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </li>
                        <#else>
                            <li class="page-item disabled">
                                <a class="page-link" href="#" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </li>
                        </#if>
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
                                <input type="text" name="search" value="<#if searchWord??>${searchWord}</#if>" class="form-control" id="search-input" placeholder="Поиск" aria-label="Recipient's username" aria-describedby="basic-addon2">
                            </div>
                            <button class="btn btn-outline-success w-100" id="buttonFilter" type="submit">Применить</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit User Modal -->
    <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editUserModalLabel">Редактировать пользователя</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editUserForm">
                        <div class="mb-3">
                            <label for="editName" class="form-label">Имя</label>
                            <input type="text" class="form-control" id="editName" name="name">
                        </div>
                        <div class="mb-3">
                            <label for="editSurname" class="form-label">Фамилия</label>
                            <input type="text" class="form-control" id="editSurname" name="surname">
                        </div>
                        <div class="mb-3">
                            <label for="editRole" class="form-label">Роль</label>
                            <select class="form-select" id="editRole" name="role">

                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="editEmail" class="form-label">Почта</label>
                            <input type="email" disabled class="form-control" id="editEmail" name="email">
                        </div>
                        <div class="mb-3">
                            <label for="editPhoneNumber" class="form-label">Телефон</label>
                            <input type="text" disabled class="form-control" id="editPhoneNumber" name="phoneNumber">
                        </div>
                        <div class="mb-3">
                            <label for="editEnabled" class="form-label">Enabled</label>
                            <select class="form-select" id="editEnabled" name="enabled">
                                <option value="true">Да</option>
                                <option value="false">Нет</option>
                            </select>
                        </div>
                        <input type="hidden" id="editUserId">
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                    <button type="button" class="btn btn-primary" id="saveChanges">Сохранить изменения</button>
                </div>
            </div>
        </div>
    </div>


    <script>
        document.addEventListener('DOMContentLoaded', async () => {
            const roleSelect = document.getElementById('role-select');
            const searchInput = document.getElementById('search-input');
            const userCards = document.querySelectorAll('.user-card');
            const btnEdit = document.getElementById('btn-edit');
            const btnDelete = document.getElementById('btn-delete');
            const editUserModal = new bootstrap.Modal(document.getElementById('editUserModal'));

            async function getRoles(select, currentRole) {
                const response = await fetch('/api/role');
                const data = await response.json();
                data.forEach(function(role) {
                    let option = document.createElement('option');
                    option.value = role;
                    option.textContent = role;
                    if (currentRole === role) {
                        option.selected = true;
                    }
                    select.appendChild(option);
                });
            }

            document.addEventListener('click', async (event) => {
                if (event.target.closest('.btn-delete')) {
                    const btnDelete = event.target.closest('.btn-delete');
                    const userId = btnDelete.getAttribute('data-user-id');
                    await deleteUser(userId);
                }

                if (event.target.closest('.btn-edit')) {
                    editUserModal.show()
                    const btnEdit = event.target.closest('.btn-edit');
                    const userId = btnEdit.getAttribute('data-user-id');
                    editUser(userId);
                }
            });
            async function deleteUser(userId){
                await fetch('/api/admin/user/'+userId, {
                    method: 'DELETE'
                })
            }

            async function editUser(userId) {
                const response = await fetch('/api/admin/user/' + userId);
                const user = await response.json();
                document.getElementById('editUserId').value = userId;
                document.getElementById('editName').value = user.name;
                document.getElementById('editSurname').value = user.surname;
                await getRoles(document.getElementById('editRole'), user.role);
                document.getElementById('editEmail').value = user.email;
                document.getElementById('editPhoneNumber').value = user.phoneNumber;
                document.getElementById('editEnabled').value = user.enabled;
                editUserModal.show();
            }

            function filterUsers(query) {
                userCards.forEach(card => {
                    const userInfo = card.querySelector('.user-info').textContent.toLowerCase();
                    if (userInfo.includes(query.toLowerCase())) {
                        card.style.display = '';
                    } else {
                        card.style.display = 'none';
                    }
                });
            }

            searchInput.addEventListener('input', () => {
                const query = searchInput.value;
                filterUsers(query);
            });

            await getRoles(roleSelect, '${currentRole}');
        });
    </script>

</@main.layout>
