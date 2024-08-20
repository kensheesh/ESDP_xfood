<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <style>
        /*body {*/
        /*    font-family: 'Arial', sans-serif;*/
        /*    background-color: #f8f9fa;*/
        /*}*/

        .table-responsive {
            /*background: white;*/
            /*border-radius: 8px;*/
            /*box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);*/
        }

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

        .bg-danger {
            background-color: #dc3545;
        }

        .bg-success {
            background-color: #28a745;
        }

        .delete-factor {
            background-color: transparent;
            border: none;
            color: black;
            cursor: pointer;
            font-size: 1.2rem;
            margin-left: 0.5rem;
        }

        .delete-factor:hover {
            text-decoration: underline;
        }

        .form-control {
            width: auto;
            display: inline-block;
            /*margin-right: 0.5rem;*/
        }

        .error-message {
            color: white;
            background-color: #dc3545;
            padding: 10px;
            border-radius: 5px;
            font-weight: bold;
            text-align: center;
        }

        .select2-container {
            z-index: 2000;
        }
    </style>

    <a href="/checks" class="btn btn-light btn-sm my-2 px-3"><i class="bi bi-arrow-90deg-left"></i></a>

    <#if error??>
        <div class="error-message">${error}</div>
    <#else>
        <div class="table-responsive p-3 rounded-4 shadow-sm">
            <table id="mainTable" class="table">
                <thead class="table-warning">
                <tr>
                    <th colspan="3" class="text-center">${checkList.pizzeria.name}</th>
                    <th colspan="1" class="text-center">${checkList.manager.name} ${checkList.manager.surname}</th>
                </tr>
                <tr>
                    <th class="text-center">Раздел</th>
                    <th class="text-center">Что проверить</th>
                    <th class="text-center">Оценка</th>
                    <th class="text-center"><#if checkList.managerWorkDate??>${checkList.managerWorkDate}</#if> ${checkList.managerWorkStartTime}
                        -${checkList.managerWorkEndTime}</th>
                </tr>
                </thead>
                <tbody id="mainTableBody">
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="2" class="text-center"><strong>Максимальное количество баллов: </strong></td>
                    <td colspan="2" class="text-center" id="maxPoint"><strong></strong></td>
                </tr>
                <tr>
                    <td colspan="2" class="text-center"><strong>(количество набранных баллов / макс. количество баллов)
                            x 100%</strong></td>
                    <td colspan="2" class="text-center" id="percentageResult"></td>
                </tr>
                </tfoot>
            </table>
        </div>

        <#assign known = SPRING_SECURITY_CONTEXT??>
        <#if known>

            <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>
            <#assign name = user.getUsername()>
            <#assign userRole = SPRING_SECURITY_CONTEXT.authentication.authorities[0].authority>

            <#if checkList.isDeleted == false>
                <div class="justify-content-center mx-0 mx-sm-4 mx-md-0 justify-content-md-end mb-5 mt-3 row row-gap-2">

                    <#if checkList.status == "IN_PROGRESS" || checkList.status == "NEW">
                        <div class="col-md-auto">
                            <div class="row row cols-1 row-cols-md-2 row-gap-2">
                                <div class="col-md-auto px-1">
                                    <button class="btn btn-green w-100" data-bs-toggle="modal"
                                            data-bs-target="#wowFactorModal">Добавить
                                        wow-фактор
                                    </button>
                                </div>
                                <div class="col-md-auto px-1">
                                    <button class="btn btn-danger w-100" data-bs-toggle="modal"
                                            data-bs-target="#critFactorModal">
                                        Добавить критический фактор
                                    </button>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="wowFactorModal" tabindex="-1" aria-labelledby="wowFactorModalLabel"
                             aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content rounded-4">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="wowFactorModalLabel">Добавить WOW-фактор</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form id="wowFactorForm">
                                            <div class="mb-3">
                                                <label for="wowFactorDescription" class="form-label">Описание</label>
                                                <select class="form-select form-select-sm" id="wowFactorDescription"
                                                        aria-label=".form-select-sm example">

                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Добавить</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="critFactorModal" tabindex="-1"
                             aria-labelledby="critwowFactorModalLabel"
                             aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content rounded-4">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="wowFactorModalLabel">Добавить критический
                                            фактор</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form id="critFactorForm">
                                            <div class="mb-3">
                                                <label for="critFactorDescription" class="form-label">Описание</label>
                                                <select class="form-select form-select-sm" id="critFactorDescription"
                                                        aria-label=".form-select-sm example">
                                                </select>
                                            </div>
                                            <div class="mb-3" id="otherDescriptionDiv" style="display: none;">
                                                <label for="otherDescription" class="form-label">Введите
                                                    описание</label>
                                                <input type="text" class="form-control" id="otherDescription"
                                                       name="otherDescription"/>
                                            </div>
                                            <button type="submit" id="addCritFactor" data-bs-dismiss="modal"
                                                    class="btn btn-primary">
                                                Добавить
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#if>

                    <#if checkList.status == 'IN_PROGRESS'>
                        <div class="col-md-auto px-1">
                            <button type="button" class="btn btn-dodo-light w-100" data-bs-toggle="modal"
                                    data-bs-target="#exampleModal">
                                Опубликовать
                            </button>
                        </div>

                        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                             aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content rounded-4">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Вы уверены, что хотите
                                            опубликовать?</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Закрыть"></button>
                                    </div>
                                    <div class="modal-body">
                                        <button type="button" class="btn btn-link text-decoration-none link-secondary" data-bs-dismiss="modal">Не хочу
                                            публиковать
                                        </button>
                                        <button type="button" id="post" class="btn btn-dodo-light">Уверен!</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#if>
                </div>
            </#if>
        </#if>
    </#if>

    <input type="hidden" value="${checkList.uuidLink}" id="uuid">
    <input type="hidden" value="${checkList.id}" id="checkId">

    <script>
        document.addEventListener('DOMContentLoaded', async (node, child) => {

            const mainTableBody = document.getElementById('mainTableBody');
            const percentageDisplay = document.getElementById('percentageResult');
            const criticalSelect = document.getElementById('critFactorDescription');
            const wowSelect = document.getElementById('wowFactorDescription');
            const maxPoint = document.getElementById('maxPoint')
            let initialInputValue;
            let countWowFactors = 0;
            let countCriticalFactors = 0;

            await updatePercentage()
            await showCriticalFactors()
            await showWowFactors()
            await showFactors()
            await showMaxPoint()
            inputUpdate()

            function inputUpdate() {
                document.querySelectorAll('.score-input').forEach(input => {
                    input.addEventListener('input', handleInput);
                    input.addEventListener('focus', handleFocus);
                    input.addEventListener('blur', handleBlur);
                });
            }

            async function showFactors() {
                let maxSum = 0;
                mainTableBody.innerHTML = '';

                let response = await fetch('/api/checklist/criteria/${checkList.uuidLink}')
                let data = await response.json()
                console.log(data)
                for (const criteria of data) {
                    const row = document.createElement('tr');
                    console.log("maxValue = " + criteria.maxValue)
                    if (criteria.maxValue && criteria.maxValue > 0) {
                        const zoneCell = createTableCell('td', 'text-center', criteria.zone);
                        const descriptionCell = createTableCell('td', '', criteria.description);
                        const maxValueCell = createTableCell('td', 'text-center border', criteria.maxValue);

                        const inputCell = document.createElement('td');
                        inputCell.classList.add('text-center');

                        const scoreInput = document.createElement('input');
                        scoreInput.type = 'number';
                        scoreInput.step = criteria.maxValue;
                        scoreInput.classList.add('text-center', 'form-control', 'score-input');
                        scoreInput.max = criteria.maxValue;
                        scoreInput.value = criteria.value;
                        scoreInput.id = criteria.id;
                        scoreInput.setAttribute('aria-valuemax', criteria.maxValue);

                        scoreInput.addEventListener('input', updatePercentage);
                        scoreInput.addEventListener('focus', handleFocus);
                        scoreInput.addEventListener('blur', handleBlur);

                        inputCell.appendChild(scoreInput);

                        row.appendChild(zoneCell);
                        row.appendChild(descriptionCell);
                        row.appendChild(maxValueCell);
                        row.appendChild(inputCell);

                        maxSum += criteria.maxValue;
                    } else {
                        let sectionCell, descriptionCell, inputCell, maxValueCell;
                        console.log("else")
                        if (criteria.section === 'Критический фактор') {
                            console.log("Крит")
                            sectionCell = createTableCell('td', 'text-center table-danger', criteria.section);
                            descriptionCell = createTableCell('td', 'table-danger', criteria.description);
                            maxValueCell = createTableCell('td', 'table-danger', '')

                            row.id = "crit-"+criteria.id;

                            inputCell = document.createElement('td');
                            inputCell.classList.add('text-center', 'table-danger');

                            const inputGroup = await createInputGroup('8', criteria.value, criteria.id, false);
                            inputCell.appendChild(inputGroup);
                            countCriticalFactors += 1;
                        } else if (criteria.section === 'WOW фактор') {
                            console.log("wow")
                            sectionCell = createTableCell('td', 'text-center table-success', criteria.section);
                            descriptionCell = createTableCell('td', 'table-success', criteria.description);
                            maxValueCell = createTableCell('td', 'table-success', '')

                            inputCell = document.createElement('td');
                            inputCell.classList.add('text-center', 'table-success');

                            const inputGroup = await createInputGroup(0, criteria.value, criteria.id, true);
                            inputCell.appendChild(inputGroup);
                            countWowFactors += 1;
                        }

                        row.appendChild(sectionCell);
                        row.appendChild(descriptionCell);
                        row.appendChild(maxValueCell);
                        row.appendChild(inputCell);
                    }

                    mainTableBody.appendChild(row);
                }
                await updatePercentage()
            }

            function createTableCell(tagName, className, textContent) {
                const cell = document.createElement(tagName);
                cell.className = className;
                cell.textContent = textContent || '';
                return cell;
            }

            async function createInputGroup(step, value, id, isWowFact) {
                let checkId = document.getElementById('checkId').value;
                let commentByCriteria = await getCommentsByCriteria(checkId, id);
                let commentsButton = document.createElement('button');
                let commentsList = '';
                if(!isWowFact){
                    let shouldShowButton = false;
                    function updateCommentsButtonVisibility() {
                        let commentQuantity = Math.abs(value / step);
                        let commentsRealQuantity = commentByCriteria.length;
                        console.log("comment should be : " + commentQuantity);
                        console.log("comments are : " + commentsRealQuantity);

                        shouldShowButton = commentsRealQuantity < commentQuantity;
                        if (shouldShowButton) {
                            commentButton.style.display = 'inline-block';
                        } else {
                            commentButton.style.display = 'none';
                        }
                    }

                    if (Array.isArray(commentByCriteria) && commentByCriteria.length > 0) {
                        commentsList = commentByCriteria.map(comment =>
                            '<ul class="list-group">' +
                            '<li class="list-group-item my-2 d-flex justify-content-between align-items-center" id="list'+comment.checkCritCommId+'">' +
                            '<span>' + '-' + step + ' ' + comment.comment + '</span>' +
                            '<div>' +
                            '<button type="button" class="btn btn-danger me-2" id="del'+comment.checkCritCommId+'" ' +
                            '-bs-toggle="tooltip" data-bs-placement="right" ' +
                            'data-bs-title="Удалить комментарий" ' +
                            'data-comment-id="' + comment.checkCritCommId + '" >' +
                            '<i class="bi bi-trash"></i>' +
                            '</button>' +
                            '</div>' +
                            '</li>' +
                            '</ul>'
                        ).join('');

                    } else {
                        commentsList = '<p>Комментариев нет</p>';
                    }
                    let commentsModal = document.createElement('div') ;
                    commentsModal.classList.add('modal', 'fade');
                    commentsModal.id = 'commentsModal-' + id;
                    commentsModal.setAttribute('tabindex', '-1');
                    commentsModal.setAttribute('aria-labelledby', 'commentsModal-' + id);
                    commentsModal.setAttribute('aria-hidden', 'true');
                    commentsModal.innerHTML =
                        '<div class="modal-dialog">'+
                        '<div class="modal-content">'+
                        '<div class="modal-header">'+
                        '<h1 class="modal-title fs-5" id="exampleModalLabel">Комментарии</h1>'+
                        '</div>'+
                        '<div class="modal-body">'+
                        commentsList +
                        '</div>'+
                        '<div class="modal-footer">'+
                        '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>'+
                        '</div>'+
                        '</div>'+
                        '</div>';

                    document.body.appendChild(commentsModal);
                    if (commentByCriteria.length >0){
                        addListenersToDeleteButtons(commentByCriteria);
                    }
                    const inputGroup = document.createElement('div');
                    inputGroup.classList.add('input-group');
                    let uuid = document.getElementById('uuid').value;
                    let max = -step;
                    const scoreInput = document.createElement('input');
                    scoreInput.type = 'number';
                    scoreInput.step = step;
                    scoreInput.classList.add('text-center', 'form-control', 'score-input');
                    scoreInput.value = value;
                    scoreInput.id = id;
                    scoreInput.max = max;

                    const inputGroupAppend = document.createElement('div');
                    inputGroupAppend.classList.add('input-group-append');

                    commentsButton.classList.add('btn', 'btn-success');
                    commentsButton.setAttribute('data-bs-toggle', 'modal');
                    commentsButton.setAttribute('data-bs-target', '#commentsModal-' + id);
                    commentsButton.innerHTML = `<i class="bi bi-card-list"></i>`;

                    const deleteButton = document.createElement('button');
                    deleteButton.classList.add('delete-factor', 'btn', 'btn-outline-danger');
                    deleteButton.dataset.id = id;
                    deleteButton.textContent = '×';
                    deleteButton.addEventListener('click', deleteFactor);

                    let comments = await getComments();
                    console.log(comments);

                    const commentButton = document.createElement('button');
                    commentButton.classList.add('btn', 'btn-success');
                    commentButton.id = "leaveComment-"+id;
                    commentButton.setAttribute('data-bs-toggle', 'modal');
                    commentButton.setAttribute('data-bs-target', '#commentModal-' + id);
                    commentButton.innerHTML = `<i class="bi bi-pencil-square"></i>`;

                    let commentsHtml = '';
                    if (Array.isArray(comments) && comments.length > 0) {
                        commentsHtml =
                            '<select class="form-select select2" placeholder="Выберите комментарий" name="commentId" aria-label="Выберите комментарий" id="select-' + id + '">' +
                            comments.map(comment =>
                                '<option value="' + comment.commentId + '">' + comment.comment + '</option>'
                            ).join('') +
                            '</select>';
                    } else {
                        commentsHtml = '<p>  </p>';
                    }
                    console.log(commentsHtml);
                    const modal = document.createElement('div');
                    modal.classList.add('modal', 'fade');
                    modal.id = 'commentModal-' + id;
                    modal.setAttribute('tabindex', '-1');
                    modal.setAttribute('aria-labelledby', 'commentModal-' + id);
                    modal.setAttribute('aria-hidden', 'true');
                    modal.innerHTML =
                        '<div class="modal-dialog">' +
                        '<div class="modal-content">' +
                        '<div class="modal-header">' +
                        '<h1 class="modal-title fs-5" id="exampleModalLabel">Добавьте пояснение за снятый балл</h1>' +
                        '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>' +
                        '</div>' +
                        '<form action="/checks/' + uuid + '/' + id + '" method="post">' +
                        '<div class="modal-body" id="modalBody' + id + '">' +
                        commentsHtml +
                        '<button type="button" class="btn btn-white border shadow-sm my-2" id="writeButton' + id + '">' +
                        'Написать другой комментарий' +
                        '</button>' +
                        '</div>' +
                        '<div class="modal-footer">' +
                        '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>' +
                        '<button type="submit" class="btn btn-dodo">Сохранить</button>' +
                        '</div>' +
                        '</form>' +
                        '</div>' +
                        '</div>';

                    document.body.appendChild(modal);
                } else {
                    commentsList = '<p>Комментариев нет</p>';
                }
                let commentsModal = document.createElement('div');
                commentsModal.classList.add('modal', 'fade');
                commentsModal.id = 'commentsModal-' + id;
                commentsModal.setAttribute('tabindex', '-1');
                commentsModal.setAttribute('aria-labelledby', 'commentsModal-' + id);
                commentsModal.setAttribute('aria-hidden', 'true');
                commentsModal.innerHTML =
                    '<div class="modal-dialog">' +
                    '<div class="modal-content">' +
                    '<div class="modal-header">' +
                    '<h1 class="modal-title fs-5" id="exampleModalLabel">Комментарии</h1>' +
                    '</div>' +
                    '<div class="modal-body">' +
                    commentsList +
                    '</div>' +
                    '<div class="modal-footer">' +
                    '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>' +
                    '</div>' +
                    '</div>' +
                    '</div>';

                document.body.appendChild(commentsModal);
                if (commentByCriteria.length > 0) {
                    addListenersToDeleteButtons(commentByCriteria);
                }
                const inputGroup = document.createElement('div');
                inputGroup.classList.add('input-group');
                let uuid = document.getElementById('uuid').value;
                let max = -step;
                const scoreInput = document.createElement('input');
                scoreInput.type = 'number';
                scoreInput.step = step;
                scoreInput.classList.add('text-center', 'form-control', 'score-input');
                scoreInput.value = value;
                scoreInput.id = id;
                if(step !== 0) {
                    scoreInput.max = max;
                } else {
                    scoreInput.min = 0;
                }

                const inputGroupAppend = document.createElement('div');
                inputGroupAppend.classList.add('input-group-append');

                const deleteButton = document.createElement('button');
                deleteButton.classList.add('delete-factor', 'btn', 'btn-outline-danger');
                deleteButton.dataset.id = id;
                deleteButton.textContent = '×';
                deleteButton.addEventListener('click', deleteFactor);

                inputGroupAppend.appendChild(deleteButton);
                inputGroup.appendChild(scoreInput);
                inputGroup.appendChild(inputGroupAppend);

                let comments = await getComments();
                console.log(comments);

                const commentButton = document.createElement('button');
                commentButton.classList.add('btn', 'btn-success');
                commentButton.setAttribute('data-bs-toggle', 'modal');
                commentButton.setAttribute('data-bs-target', '#commentModal-' + id);
                commentButton.innerHTML = `<i class="bi bi-pencil-square"></i>`;

                let commentsHtml = '';
                if (Array.isArray(comments) && comments.length > 0) {
                    commentsHtml =
                        '<select class="form-select select2" placeholder="Выберите комментарий" name="commentId" aria-label="Выберите комментарий" id="select-' + id + '">' +
                        comments.map(comment =>
                            '<option value="' + comment.commentId + '">' + comment.comment + '</option>'
                        ).join('') +
                        '</select>';
                } else {
                    commentsHtml = '<p>  </p>';
                }
                console.log(commentsHtml);
                const modal = document.createElement('div');
                modal.classList.add('modal', 'fade');
                modal.id = 'commentModal-' + id;
                modal.setAttribute('tabindex', '-1');
                modal.setAttribute('aria-labelledby', 'commentModal-' + id);
                modal.setAttribute('aria-hidden', 'true');
                modal.innerHTML =
                    '<div class="modal-dialog">' +
                    '<div class="modal-content">' +
                    '<div class="modal-header">' +
                    '<h1 class="modal-title fs-5" id="exampleModalLabel">Добавьте пояснение за снятый балл</h1>' +
                    '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>' +
                    '</div>' +
                    '<form action="/checks/' + uuid + '/' + id + '" method="post">' +
                    '<div class="modal-body" id="modalBody' + id + '">' +
                    commentsHtml +
                    '<button type="button" class="btn btn-white border shadow-sm my-2" id="writeButton' + id + '">' +
                    'Написать другой комментарий' +
                    '</button>' +
                    '</div>' +
                    '<div class="modal-footer">' +
                    '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>' +
                    '<button type="submit" class="btn btn-dodo">Сохранить</button>' +
                    '</div>' +
                    '</form>' +
                    '</div>' +
                    '</div>';

                document.body.appendChild(modal);

                $('#select-' + id).select2({
                    placeholder: 'Выберите комментарий',
                    width: '100%',
                    language: 'ru',
                    dropdownParent: $('#commentModal-' + id)
                });

                document.getElementById('commentModal-' + id).addEventListener('shown.bs.modal', function (event) {
                    let select = document.getElementById('select-' + id);
                    let textarea = document.getElementById('textarea' + id);
                    if (!select) {
                        const form = this.querySelector('form');
                        writeButton.style.display = "block";

                        if (textarea) {
                            textarea.remove();
                        }
                        form.insertAdjacentHTML('afterbegin', commentsHtml);
                        $('#select-' + id).select2({
                            placeholder: 'Выберите комментарий',
                            width: '100%',
                            language: 'ru',
                            dropdownParent: $('#commentModal-' + id)
                        });
                    }
                });

                let writeButton = document.getElementById('writeButton' + id);
                console.log(writeButton);
                writeButton.addEventListener('click', () => {
                    let select = document.getElementById('select-' + id);
                    console.log(select);
                    if (select) {

                        $('#select-' + id).select2('destroy');
                        select.remove();
                        writeButton.style.display = "none";
                        let modalBody = document.getElementById('modalBody' + id);
                        let inputBox = document.createElement('div');
                        inputBox.id = 'inputBox' + id;
                        inputBox.classList.add('input-group', 'mb-3');
                        let input = document.createElement('textarea');
                        input.name = 'comment';
                        input.id = 'textarea' + id;
                        input.rows = 3;
                        input.classList.add('form-control');
                        input.required = true;
                        inputBox.appendChild(input);
                        modalBody.appendChild(inputBox);
                    }
                });

                scoreInput.addEventListener('input', async () => {
                    value = scoreInput.value;
                    await getCommentsByCriteria(checkId, id);
                    updateCommentsButtonVisibility();
                });

                // inputGroupAppend.appendChild(commentButton);
                //
                // inputGroupAppend.appendChild(commentsButton)
                // inputGroupAppend.appendChild(deleteButton);
                inputGroup.appendChild(scoreInput);

                if (!isWowFact) {
                    inputGroup.appendChild(commentButton)
                    inputGroup.appendChild(commentsButton)
                }

                inputGroup.appendChild(deleteButton)
                // inputGroup.appendChild(inputGroupAppend);
                const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
                const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));

                return inputGroup;
            }

            //     const inputGroup = document.createElement('div');
            //     inputGroup.classList.add('input-group');
            //     let uuid = document.getElementById('uuid').value;
            //     let max = -step;
            //     const scoreInput = document.createElement('input');
            //     scoreInput.type = 'number';
            //     scoreInput.step = step;
            //     scoreInput.classList.add('text-center', 'form-control', 'score-input');
            //     scoreInput.value = value;
            //     scoreInput.id = id;
            //     scoreInput.max = max;
            //
            //     const inputGroupAppend = document.createElement('div');
            //     inputGroupAppend.classList.add('input-group-append');
            //
            //     const deleteButton = document.createElement('button');
            //     deleteButton.classList.add('delete-factor', 'btn', 'btn-outline-danger');
            //     deleteButton.dataset.id = id;
            //     deleteButton.textContent = '×';
            //     deleteButton.addEventListener('click', deleteFactor);
            //
            //     inputGroupAppend.appendChild(deleteButton);
            //     inputGroup.appendChild(scoreInput);
            //     inputGroup.appendChild(inputGroupAppend);
            //
            //     return inputGroup;
            // });


            async function showCriticalFactors() {
                try {
                    let response = await fetch('/api/criteria/critical');
                    if (!response.ok) {
                        throw new Error('Network response was not ok ' + response.statusText);
                    }
                    let data = await response.json();

                    data.forEach((criteria) => {
                        let option = document.createElement('option')
                        option.value = criteria.id
                        option.innerText = criteria.description
                        criticalSelect.appendChild(option)
                    })

                    let otherOption = document.createElement('option');
                    otherOption.id = 'other';
                    otherOption.value = 'other';
                    otherOption.innerText = 'Другой';
                    criticalSelect.appendChild(otherOption);
                } catch (error) {
                    console.error('There was a problem with the fetch operation:', error);
                }
            }

            async function showWowFactors() {
                try {
                    let response = await fetch('/api/criteria/wow');
                    if (!response.ok) {
                        throw new Error('Network response was not ok ' + response.statusText);
                    }
                    let data = await response.json();

                    data.forEach((criteria) => {
                        let option = document.createElement('option')
                        option.value = criteria.id
                        option.innerText = criteria.description
                        wowSelect.appendChild(option)
                    })

                } catch (error) {
                    console.error('There was a problem with the fetch operation:', error);
                }
            }

            async function checkCritComments() {
                let elements = document.querySelectorAll('[id^="crit-"]');
                let criteriaIds = [];
                elements.forEach(element => {
                    let id = element.id;
                    let criteriaId = id.substring(5);
                    console.log(criteriaId);
                    criteriaIds.push(criteriaId);
                });
                for (let i = 0; i <criteriaIds.length ; i++) {
                    let criteria = await getCriteriaById(criteriaIds[i]);
                    console.log("id "+criteria.id)
                    let value = criteria.value;
                    console.log("value "+value)
                    let comments = await getCommentsByCriteria(${checkList.id}, criteria.id);
                    let mSize = Math.abs(value/criteria.coefficient);
                    console.log("mSize "+mSize);
                    console.log("length "+comments.length)
                    let writeButton = document.getElementById('leaveComment-'+criteria.id);
                    if (mSize <= comments.length){
                        if (writeButton){
                            writeButton.style.display = "none";
                        }
                    }
                }

            }

            checkCritComments();

            async function getCriteriaById(id) {
                let response = await fetch("/api/checklist/criteria/"+${checkList.id}+"/" + id);
                if (response.ok) {
                    return await response.json();
                } else {
                    console.log(response);
                    alert("Ошибка HTTP: " + response.status);
                }
            }

            async function updatePercentage() {
                let response = await fetch('/api/checklist/criteria/percentage/${checkList.id}')
                let data = await response.json()
                percentageDisplay.textContent = data + '%';
                console.log('Сделан запрос' + data)
                return data;
            }

            function handleFocus(event) {
                initialInputValue = event.target.value;
            }

            async function handleBlur(event) {
                if (event.target.value !== initialInputValue) {
                    let score = {
                        criteriaId: event.target.id,
                        value: event.target.value,
                        checkListId: ${checkList.id},
                        maxValue: event.target.getAttribute('aria-valuemax')
                    };
                    console.log(score)

                    await fetch('/api/checklist/criteria/save', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify([score])
                    });


                    await updatePercentage();
                }
            }

            function handleInput(event) {
                const input = event.target;
                const maxValue = parseFloat(input.getAttribute('max'));
                const value = parseFloat(input.value);

                if (value > maxValue) {
                    input.value = maxValue;
                }

                updatePercentage();
            }

            async function fetchToCreate(url, object) {
                const response = await fetch(url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(object)
                });

                return response.json();
            }

            async function deleteFactor(event) {
                const button = event.target;
                const row = button.closest('tr');
                const criteriaId = button.getAttribute('data-id');

                await fetch(`/api/checklist/criteria/delete/` + criteriaId + `?checkListId=${checkList.id}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(response => {
                    if (response.ok) {
                        row.remove();
                        updatePercentage();
                    } else {
                        console.error('Ошибка при удалении фактора');
                    }
                });
                await showFactors()
                await updatePercentage()
            }

            document.getElementById('wowFactorForm').addEventListener('submit', async (event) => {
                event.preventDefault();
                const descriptionId = document.getElementById('wowFactorDescription').value;

                const object = {
                    criteriaId: descriptionId,
                    value: 0,
                    checkListId: ${checkList.id},
                    maxValue: 0
                };

                await fetchToCreate('/api/checklist/criteria/create/wow', object);
                $('#wowFactorModal').modal('hide')
                alert('Добавлен WOW-фактор')
                await showFactors()
                inputUpdate()
            });

            document.getElementById('critFactorDescription').addEventListener('change', function () {
                let selectedValue = this.value;
                let otherDescriptionDiv = document.getElementById('otherDescriptionDiv');
                if (selectedValue === 'other') {
                    otherDescriptionDiv.style.display = 'block';
                } else {
                    otherDescriptionDiv.style.display = 'none';
                }
            });

            document.getElementById('critFactorForm').addEventListener('submit', async (event) => {
                event.preventDefault();
                const selectedValue = document.getElementById('critFactorDescription').value;
                let description;

                if (selectedValue === 'other') {
                    description = document.getElementById('otherDescription').value;
                } else {
                    description = selectedValue;
                }

                const object = {
                    value: 0,
                    checkListId: ${checkList.id},
                    maxValue: 0
                };

                await fetchToCreate(`/api/checklist/criteria/create/crit?description=` + description, object);
                $('#critFactorModal').modal('hide')
                alert('Критический фактор добавлен');
                await showFactors()
                inputUpdate()
            });

            document.querySelectorAll('.delete-factor').forEach(button => {
                button.addEventListener('click', deleteFactor);
            });

            document.getElementById('post').addEventListener('click', fetchToResult);

            async function fetchToResult() {

                try {
                    let response = await fetch(`/api/checks/post/${checkList.uuidLink}`, {method: 'POST'});
                    if (!response.ok) {
                        let errorMessage = await response.text();
                        throw new Error(errorMessage);
                    }
                    await navigator.clipboard.writeText('/checks/${checkList.uuidLink}');
                    console.log("Скопировано в буфер обмена!");
                    window.location.href = '/analytics';
                } catch (error) {
                    alert(error.message);
                }
            }

            async function showMaxPoint() {
                let response = await fetch('/api/checks/points/${checkList.id}')
                let data = await response.json()
                console.log(data)
                maxPoint.innerText = data
            }

            async function getComments() {
                let response = await fetch('/api/comments');
                let data = await response.json();
                console.log(data.type)
                return data;
            }


            async function getCommentsByCriteria(checkId, criteriaId) {
                let response = await fetch('/api/comments/' + checkId + '/' + criteriaId);
                let data = await response.json();
                console.log(data.type)
                return data;
            }


            async function addListenersToDeleteButtons(comments) {
                comments.forEach(comment => {
                    const deleteButton = document.getElementById('del' + comment.checkCritCommId);
                    if (deleteButton) {
                        deleteButton.addEventListener('click', async function () {
                            const commentId = deleteButton.getAttribute('data-comment-id');

                            try {
                                const response = await fetch('/api/comments/' + commentId + '/delete', {
                                    method: 'DELETE',
                                });

                                if (response.ok) {

                                    const listItem = document.getElementById('list' + commentId);
                                    if (listItem) {
                                        listItem.remove();
                                        window.location.reload();
                                    }
                                } else {
                                    alert('Ошибка при удалении комментария. Статус: ' + response.status);
                                }
                            } catch (error) {
                                console.error('Ошибка при удалении комментария:', error);
                                alert('Произошла ошибка при удалении комментария.');
                            }
                        });

                    }
                });
            }



        });


    </script>


</@main.layout>