<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <style>
        /*body {*/
        /*    font-family: 'Arial', sans-serif;*/
        /*    background-color: #f8f9fa;*/
        /*}*/

        /*.table-responsive {*/
        /*    background: white;*/
        /*    border-radius: 8px;*/
        /*    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);*/
        /*}*/

        .table th, .table td {
            vertical-align: middle;
        }

        .table thead th {
            /*background-color: #ffc107;*/
            color: #343a40;
            font-weight: bold;
        }

        .table tbody tr:nth-child(odd) {
            background-color: #f8f9fa;
        }

        .bg-danger, .bg-success {
            color: white;
            font-weight: bold;
            padding: 0.75rem;
        }

        /*.bg-danger {*/
        /*    background-color: #dc3545;*/
        /*}*/

        /*.bg-success {*/
        /*    background-color: #28a745;*/
        /*}*/

        .form-control {
            width: auto;
            display: inline-block;
            margin-right: 0.5rem;
        }

        .appeal-button {
            display: inline-block;
            margin-top: 0.5rem;
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
        }

        .appeal-submitted {
            float: right;
            margin-left: 5px;
            color: #28a745;
            font-weight: bold;
            font-size: 0.875rem;
        }

        .notification {
            background-color: #f1f1f1;
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            text-align: center;
            font-size: 1rem;
        }
    </style>

    <a href="/checks" class="btn btn-light btn-sm my-2 px-3"><i class="bi bi-arrow-90deg-left"></i></a>

    <#assign countWowFactors = 0>
    <#assign countCriticalFactors = 0>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>
        <#assign name = user.getUsername()>
        <#assign authorities = SPRING_SECURITY_CONTEXT.authentication.authorities>
    </#if>

    <#if guest?? && checkList.status == "IN_PROGRESS" || guess?? && checkList.status == "NEW">
        <h1>Данная проверка еще не опубликована</h1>
    <#else>
        <div class="notification">
            ! - Данный знак рядом с критерием означает, что на данный критерий подана апелляция, которую еще не
            рассмотрели.
        </div>

        <div class="table-responsive rounded p-3 rounded-4 shadow-sm">
            <table id="mainTable" class="table rounded-3">
                <thead class="table-warning">
                <tr>
                    <th colspan="1" class="text-center">${checkList.pizzeria.name}</th>
                    <th colspan="2" class="text-center">${checkList.manager.name} ${checkList.manager.surname}</th>
                    <th colspan="2" class="text-center">
                        ${checkList.managerWorkDate} ${checkList.managerWorkStartTime}
                        - ${checkList.managerWorkEndTime}
                    </th>
                </tr>
                <tr>
                    <th class="text-center">Раздел</th>
                    <th class="text-center">Что проверить</th>
                    <th class="text-center">Оценка</th>
                    <th class="text-center">Действия</th>
                    <th></th>
                </tr>
                </thead>
                <tbody id="mainTableBody">
                <#list checkList.criteria as criteria>

                    <#list authorities as authority>
                        <#if authority.authority == 'ROLE_SUPERVISOR'>
                            <button class="appeals" data-criteria-id="${criteria.id}" hidden="hidden"></button>
                        </#if>
                    </#list>

                    <tr class="">
                        <#if criteria.maxValue?? && criteria.maxValue gt 0>
                            <td class="text-center">${criteria.zone}</td>
                            <td id="criteria-description-${criteria.id}">
                                ${criteria.description}
                            </td>
                            <td class="text-center border">${criteria.maxValue}</td>
                            <td class="text-center">
                                <input aria-label="" type="number" disabled
                                       class="text-center form-control score-input"
                                       min="0"
                                       max="${criteria.maxValue}" value="${criteria.value}" id="${criteria.id}"
                                       aria-valuemax="${criteria.maxValue}">
                            </td>
                            <td class="text-center">
                                <#if guest??>
                                    <div class="d-flex justify-content-end align-items-center">
                                        <#if isRecent == true>
                                            <button class="btn btn-warning btn-sm appeal-button"
                                                    data-criteria-id="${criteria.id}">Подать апелляцию
                                            </button>
                                        </#if>
                                        <#if criteria.isAccepted?? && true>
                                            <span class="appeal-submitted">!</span>
                                        </#if>
                                    </div>
                                </#if>
                            </td>
                        <#else>
                            <#if criteria.section == 'Критический фактор'>
                                <td class="text-center table-danger">${criteria.section}</td>
                                <td class="table-danger" id="criteria-description-${criteria.id}">
                                    ${criteria.description}
                                </td>
                                <td class="table-danger"></td>
                                <td class="text-center table-danger">
                                    <input aria-label="" type="number" disabled
                                           class="text-center form-control score-input"
                                           value="${criteria.value}" id="${criteria.id}"/>
                                </td>
                                <td class="text-center table-danger">
                                    <#if guest??>
                                        <div class="d-flex justify-content-end align-items-center">
                                            <#if isRecent == true>
                                                <button class="btn btn-warning btn-sm appeal-button"
                                                        data-criteria-id="${criteria.id}">Подать апелляцию
                                                </button>
                                                <button class="btn btn-info btn-sm comments-button"
                                                        data-criteria-id="${criteria.id}">Комментарии
                                                </button>
                                            </#if>
                                            <#if criteria.isAccepted?? && true>
                                                <span class="appeal-submitted">!</span>
                                            </#if>
                                        </div>
                                    </#if>
                                </td>
                            <#else>
                                <#if criteria.section == 'Критический фактор'>
                                    <td class="text-center bg-danger">${criteria.section}</td>
                                    <td class="bg-danger" id="criteria-description-${criteria.id}">
                                        ${criteria.description}
                                    </td>
                                    <td class="bg-danger"></td>
                                    <td class="text-center bg-danger">
                                        <input aria-label="" type="number" disabled
                                               class="text-center form-control score-input"
                                               value="${criteria.value}" id="${criteria.id}"/>
                                    </td>
                                    <td class="text-center bg-danger">
                                        <#if guest?? && checkList.status == "DONE">
                                            <#if isRecent == true>
                                                <div class="d-flex justify-content-end align-items-center">
                                                   <#if criteria.hasComments == false>
                                                       <button class="btn btn-warning btn-sm appeal-button"
                                                               data-criteria-id="${criteria.id}">Подать апелляцию
                                                       </button>

                                                       <#if criteria.isAccepted?? && true>
                                                           <span class="appeal-submitted">!</span>
                                                       </#if>
                                                       <#else >
                                                           <button class="btn btn-info btn-sm comments-button"
                                                                   data-criteria-id="${criteria.id}">Комментарии
                                                           </button>
                                                   </#if>

                                                </div>
                                            </#if>
                                        </#if>
                                    </td>
                                <#elseif criteria.section == 'WOW фактор'>
                                    <td class="text-center bg-success">${criteria.section}</td>
                                    <td class="bg-success">${criteria.description}</td>
                                    <td class="bg-success"></td>
                                    <td class="text-center bg-success">
                                        <input aria-label="" type="number" disabled
                                               class="text-center form-control score-wow-input"
                                               min="0"
                                               value="${criteria.value}" id="${criteria.id}"/>
                                    </td>
                                    <td class="text-center bg-success">
                                    </td>
                                </#if>
                            </#if>
                        </tr>
                        </#if>
                    </#list>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="2" class="text-center"><strong>Максимальное количество баллов: </strong></td>
                        <td colspan="2" class="text-center"><strong id="maxPoint"></strong></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="text-center"><strong>(количество набранных баллов / макс. количество
                                баллов)
                                x 100%</strong></td>
                        <td colspan="2" class="text-center" id="percentageResult"></td>
                    </tr>
                    </tfoot>
                </table>
            </div>
            <div class="text-end mb-5 mt-2">
                <#if authorities??>
                    <#list authorities as authority>
                        <#if authority.authority == "ROLE_ADMIN" || name?? && name == checkList.getExpertEmail()>
                            <#if checkList.status == "IN_PROGRESS">
                                <button id="reopenCheck" class="btn btn-success">Возобновить</button>
                            <#elseif checkList.status == "NEW">
                                <button id="startCheck" class="btn btn-success">Выполнить проверку</button>
                            </#if>
                        </#if>
                    </#list>
                </#if>
            </div>
        <div class="modal fade" id="commentsModal" tabindex="-1" aria-labelledby="commentsModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="commentsModalLabel">Комментарии</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="commentsModalBody">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="checkId" value="${checkList.id}">

        <#if known>
            <#assign userRole = authorities[0].authority>

            <#if checkList.isDeleted>
                <#if userRole == "ROLE_ADMIN">
                    <div class="text-end mb-5 mt-2">
                        <button class="btn btn-primary col col-md-auto" data-bs-toggle="modal"
                                data-bs-target="#restoreModal">
                            Восстановить проверку
                        </button>
                    </div>

                    <div class="modal fade" id="restoreModal" tabindex="-1" aria-labelledby="restoreModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="restoreModalLabel">Восстановление проверки</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    Вы уверены, что хотите восстановить проверку?
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-link text-decoration-none link-secondary"
                                            data-bs-dismiss="modal">Отмена
                                    </button>
                                    <form class="d-inline-block" action="/checks/${checkList.uuidLink}/restore"
                                          method="post">
                                        <button class="btn btn-primary">Восстановить</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </#if>
            <#else>
                <div class="justify-content-center mx-0 mx-sm-4 mx-md-0 justify-content-md-end mb-5 mt-3 row row-gap-2">
                    <#if checkList.status == "DONE">
                        <#if userRole == 'ROLE_ADMIN'>
                            <div class="col-md-auto px-1">
                                <button class="btn btn-outline-danger w-100" data-bs-toggle="modal"
                                        data-bs-target="#deleteModal">
                                    Удалить проверку
                                </button>
                            </div>

                            <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel"
                                 aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="deleteModalLabel">Удаление проверки</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            Вы уверены, что хотите удалить проверку?
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button"
                                                    class="btn btn-link text-decoration-none link-secondary"
                                                    data-bs-dismiss="modal">Отмена
                                            </button>
                                            <form class="d-inline-block" action="/checks/${checkList.uuidLink}/delete"
                                                  method="post">
                                                <button class="btn btn-danger">Удалить</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#if>
                    <#else>
                        <#if userRole == 'ROLE_SUPERVISOR' || userRole == 'ROLE_ADMIN'>
                            <div class="col-md-auto px-1">
                                <button class="btn btn-outline-danger w-100" data-bs-toggle="modal"
                                        data-bs-target="#deleteModal">
                                    Удалить проверку
                                </button>
                            </div>

                            <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel"
                                 aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="deleteModalLabel">Удаление проверки</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            Вы уверены, что хотите удалить проверку?
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button"
                                                    class="btn btn-link text-decoration-none link-secondary"
                                                    data-bs-dismiss="modal">Отмена
                                            </button>
                                            <form class="d-inline-block" action="/checks/${checkList.uuidLink}/delete"
                                                  method="post">
                                                <button class="btn btn-danger">Удалить</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#if>
                    </#if>

                    <#if userRole == 'ROLE_SUPERVISOR'>
                        <div class="col-md-auto px-1">
                            <a href="/checks/${checkList.uuidLink}/edit" class="btn btn-light-green w-100"
                               role="link">
                                Редактировать
                            </a>
                        </div>
                    </#if>

                    <#if checkList.status == 'DONE'>
                        <div class="col-md-auto px-1">
                            <button id="getTemplate" class="btn btn-dodo w-100">Получить шаблон сообщение</button>
                        </div>
                    </#if>

                    <#if name?? && name == checkList.getExpertEmail()>
                        <#if checkList.status == "IN_PROGRESS">
                            <div class="col-md-auto px-1">
                                <button id="reopenCheck" class="btn btn-dodo-light">Возобновить</button>
                            </div>
                        <#elseif checkList.status == "NEW">
                            <div class="col-md-auto px-1">
                                <button id="startCheck" class="btn btn-dodo-light">Выполнить проверку</button>
                            </div>
                        </#if>
                    </#if>
                </div>
            </#if>
        </#if>
    </#if>
    <script>
        document.addEventListener('DOMContentLoaded', async () => {
            const maxPoint = document.getElementById('maxPoint')
            const percentageDisplay = document.getElementById('percentageResult');
            const btnStartCheck = document.getElementById('startCheck');
            const btnReopenCheck = document.getElementById('reopenCheck');

            async function showResultPercentage() {
                let response = await fetch('/api/checklist/criteria/percentage/${checkList.id}')
                let data = await response.json()
                percentageDisplay.textContent = data + '%';
                console.log('Сделан запрос' + data)
                return data;
            }

            <#if checkList.status == "NEW">
            btnStartCheck.addEventListener('click', () => startCheck());
            <#elseif checkList.status = "IN_PROGRESS">
            btnReopenCheck.addEventListener('click', () => {
                window.location.href = '/checks/${checkList.uuidLink}/fill'
            });
            </#if>


            async function startCheck() {
                await fetch('/api/checks/change/status/' + ${checkList.id}, {
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                });
                window.location.href = '/checks/${checkList.uuidLink}/fill'
            }

            async function showMaxPoint() {
                let response = await fetch('/api/checks/points/${checkList.id}')
                let data = await response.json()
                console.log(data)
                maxPoint.innerText = data
            }

            await showResultPercentage()
            await showMaxPoint()

            const appealButtons = document.querySelectorAll('.appeal-button');

            appealButtons.forEach(button => {
                button.addEventListener('click', async () => {
                    const criteria = button.getAttribute('data-criteria-id');
                    await fetchToAppeal(criteria)
                });
            });

            const historyAppeals = document.querySelectorAll('.appeals');
            historyAppeals.forEach(button => {
                const criteria = button.getAttribute('data-criteria-id');
                if (criteria !== null) {
                    addDynamicContent(criteria);
                }
            });

            async function fetchToAppeal(criteria) {
                let object = buildData(criteria);
                console.log(object)
                let response = await fetch('/api/appeal', {
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(object)
                })

                let data = await response.json()
                window.location.href = '/appeals/' + data
            }

            function buildData(criteria) {
                return {
                    criteriaId: criteria,
                    checkListId: ${checkList.id}
                }
            }

            async function getAppeals(criteriaId) {
                if (criteriaId !== null) {
                    let response = await fetch('/api/appeal/accepted/${checkList.id}/' + criteriaId)
                    return await response.json();
                }
                return []
            }

            async function addDynamicContent(criteriaId) {
                let appeals = await getAppeals(criteriaId)
                console.log(appeals);
                const description = document.getElementById(`criteria-description-` + criteriaId);
                console.log(description)
                if (appeals.length > 0) {
                    description.innerHTML = `
                        <div class="accordion" id="accordion-` + criteriaId + `">
                                        <div class="accordion-item">
                                            <h2 class="accordion-header" id="heading-` + criteriaId + `">
                                                <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                                        data-bs-target="#collapse-` + criteriaId + `" aria-expanded="true"
                                                        aria-controls="#collapse-` + criteriaId + `">` + appeals[0].criteriaDescription + `</button>
                                            </h2>
                                            <div id="collapse-` + criteriaId + `" class="accordion-collapse collapse"
                                                 aria-labelledby="heading-` + criteriaId + `"
                                                 data-bs-parent="#accordion-` + criteriaId + `">
                                                <div class="accordion-body-` + criteriaId + `">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                        `

                    let accordionBody = document.querySelector('.accordion-body-' + criteriaId)
                    let ol = document.createElement('ol')
                    for (let i = 0; i < appeals.length; i++) {
                        let li = document.createElement('li')
                        li.className = 'm-1'
                        li.innerHTML = `<a class="link-offset-2 link-underline link-underline-opacity-0" href="/appeals/` + appeals[i].id + `/approve">Аппеляция</a>`
                        ol.appendChild(li)
                    }
                    accordionBody.appendChild(ol)
                }
            }

            const commentsButtons = document.querySelectorAll('.comments-button');

            commentsButtons.forEach(button => {
                button.addEventListener('click', async () => {
                    const criteriaId = button.getAttribute('data-criteria-id');
                    await fetchAndDisplayComments(criteriaId);
                });
            });

            async function fetchAndDisplayComments(criteriaId) {
                let checkId = document.getElementById('checkId').value;
                let response = await fetch('/api/comments/' + checkId + '/' + criteriaId);
                let data = await response.json();
                displayComments(data, checkId, criteriaId);
            }


            function displayComments(comments, checkId, criteriaId) {
                const commentsModalBody = document.getElementById('commentsModalBody');
                commentsModalBody.innerHTML = '';

                comments.forEach(comment => {
                    const commentElement = document.createElement('div');
                    commentElement.classList.add('list-group', 'list-group-flush')
                    console.log("appealed "+comment.appealed)
                    if (comment.appealed === true){
                        commentElement.innerHTML =
                            '<li class="list-group-item my-2 d-flex justify-content-between align-items-center">'+comment.comment+'' +
                            '<button  type="button" class="btn btn-danger">'+
                            '<i class="bi bi-exclamation-square-fill"></i></button> '+
                            '</li>';
                    }else if (comment.appealed ===false) {
                        commentElement.innerHTML =
                            '<li class="list-group-item my-2 d-flex justify-content-between align-items-center">'+comment.comment+
                            '<button id="appealButton-'+comment.commentId+'" type="button" class="btn btn-warning" data-bs-toggle="tooltip" data-bs-placement="right" data-bs-title="Подать апелляцию">'+
                            '<i class="bi bi-hammer"></i></button> </li>';
                    }
                    commentsModalBody.appendChild(commentElement);
                    console.log("comment id: " + comment.commentId)
                    let appealButton = document.getElementById('appealButton-'+comment.commentId);
                    if (appealButton){
                        appealButton.addEventListener('click', (ev)=>{
                            ev.preventDefault();
                            appealByComment(comment.commentId, criteriaId, checkId);
                        })
                    }

                });
                $('#commentsModal').modal('show');
                const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
                const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
            }

            async function appealByComment(commentId, criteriaId, checkId){
                let object = {
                    commentId: commentId,
                    criteriaId: criteriaId,
                    checkListId: checkId,
                }
                console.log("object "+object)
                let response = await fetch('/api/appeal/comment', {
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(object)
                })

                let data = await response.json()
                window.location.href = '/appeals/' + data

            }

            let a = document.createElement('a');
            a.href = '/checks/${checkList.uuidLink}';

            document.getElementById('getTemplate').addEventListener('click', async () => {
                const message = `Дата: ${checkList.managerWorkDate}\n` +
                    `Пиццерия: ${checkList.pizzeria.name}\n` +
                    `Менеджер: ${checkList.manager.name} ${checkList.manager.surname}\n` +
                    `Результат проверки в %: ` + await updatePercentage() + `` +
                    // `\nПоложительная обратная связь: ` +
                    `\nКритический фактор: ` + ${countCriticalFactors} +
                    `\nWOW фактор: ` + ${countWowFactors} +
                    `\nПоделитесь результатами проверки с командой - отправьте результаты в общий чат команды\n\n\n` +
                    a.href;

                await navigator.clipboard.writeText(message);

                alert("Скопировано в буфер обмена!");
            });

            async function updatePercentage() {
                let response = await fetch('/api/checklist/criteria/percentage/${checkList.id}')
                return await response.json()
            }
        });
    </script>

</@main.layout>