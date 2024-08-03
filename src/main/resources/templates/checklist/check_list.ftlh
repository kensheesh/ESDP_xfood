<#import "../layout.ftlh" as main>
<@main.layout ; spting>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }

        .table-responsive {
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .table th, .table td {
            vertical-align: middle;
        }

        .table thead th {
            background-color: #ffc107;
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
            margin-right: 0.5rem;
        }

        .error-message {
            color: white;
            background-color: #dc3545;
            padding: 10px;
            border-radius: 5px;
            font-weight: bold;
            text-align: center;
        }
    </style>

    <#if error??>
        <div class="error-message">${error}</div>
     <#else>
         <main class="container mt-4">
             <div class="table-responsive rounded p-3">
                 <table id="mainTable" class="table rounded-3">
                     <thead>
                     <tr>
                         <th colspan="3" class="text-center">${checkList.pizzeria.name}</th>
                         <th colspan="1" class="text-center">${checkList.manager.name} ${checkList.manager.surname}</th>
                     </tr>
                     <tr>
                         <th class="text-center">Раздел</th>
                         <th class="text-center">Что проверить</th>
                         <th class="text-center">Оценка</th>
                         <th class="text-center">${checkList.managerWorkDate} ${checkList.managerWorkStartTime}-${checkList.managerWorkEndTime}</th>
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
             <#assign
             known = SPRING_SECURITY_CONTEXT??
             >
             <#if known>
                 <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>
                 <#assign name = user.getUsername()>
                 <#assign authorities = SPRING_SECURITY_CONTEXT.authentication.authorities>
                 <div class="text-end mb-5 mt-2">
                     <#list authorities as authority>
                         <#if authority.authority == 'ROLE_SUPERVISOR'>
                             <a href="/checks/${checkList.uuidLink}/update" class="btn btn-success"  role="link" >Редактировать </a>
                         </#if>
                     </#list>

                     <#if checkList.status == 'IN_PROGRESS'>
                         <button id="getTemplate" class="btn btn-warning">Получить шаблон сообщение</button>
                         <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#exampleModal">
                             Опубликовать
                         </button>
                     </#if>

                     <#if checkList.status == "IN_PROGRESS" || checkList.status == "NEW">
                         <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#wowFactorModal">Добавить
                             wow-фактор
                         </button>
                         <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#critFactorModal">Добавить критический
                             фактор
                         </button>
                     </#if>

                     <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                          aria-hidden="true">
                         <div class="modal-dialog">
                             <div class="modal-content">
                                 <div class="modal-header">
                                     <h5 class="modal-title" id="exampleModalLabel">Вы уверены, что хотите опубликовать?</h5>
                                     <button type="button" class="btn-close" data-bs-dismiss="modal"
                                             aria-label="Закрыть"></button>
                                 </div>
                                 <div class="modal-body">
                                     <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Не хочу
                                         публиковать
                                     </button>
                                     <button type="button" id="post" class="btn btn-primary">Уверен!</button>
                                 </div>
                             </div>
                         </div>
                     </div>
                 </div>
             </#if>
         </main>
    </#if>

    <div class="modal fade" id="wowFactorModal" tabindex="-1" aria-labelledby="wowFactorModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="wowFactorModalLabel">Добавить WOW-фактор</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
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


    <div class="modal fade" id="critFactorModal" tabindex="-1" aria-labelledby="critwowFactorModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="wowFactorModalLabel">Добавить критический фактор</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
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
                            <label for="otherDescription" class="form-label">Введите описание</label>
                            <input type="text" class="form-control" id="otherDescription" name="otherDescription"/>
                        </div>
                        <button type="submit" id="addCritFactor" data-bs-dismiss="modal" class="btn btn-primary">Добавить</button>
                    </form>
                </div>
            </div>
        </div>
        <input type="text" value="${checkList.uuidLink}" id="uuid">
    </div>
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
                for (const criteria of data) {
                    const row = document.createElement('tr');

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

                        if (criteria.section === 'Критический фактор') {
                            sectionCell = createTableCell('td', 'text-center bg-danger', criteria.section);
                            descriptionCell = createTableCell('td', 'bg-danger', criteria.description);
                            maxValueCell = createTableCell('td', 'bg-danger', '')

                            inputCell = document.createElement('td');
                            inputCell.classList.add('text-center', 'bg-danger');

                            const inputGroup = await createInputGroup('8', criteria.value, criteria.id);
                            inputCell.appendChild(inputGroup);
                            countCriticalFactors += 1;
                        } else if (criteria.section === 'WOW фактор') {
                            sectionCell = createTableCell('td', 'text-center bg-success', criteria.section);
                            descriptionCell = createTableCell('td', 'bg-success', criteria.description);
                            maxValueCell = createTableCell('td', 'bg-success', '')

                            inputCell = document.createElement('td');
                            inputCell.classList.add('text-center', 'bg-success');

                            const inputGroup = await createInputGroup('0', criteria.value, criteria.id);
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

             async function createInputGroup(step, value, id) {
                const inputGroup = document.createElement('div');
                inputGroup.classList.add('input-group');
                let uuid = document.getElementById('uuid').value;
                const scoreInput = document.createElement('input');
                scoreInput.type = 'number';
                scoreInput.step = step;
                scoreInput.classList.add('text-center', 'form-control', 'score-input');
                scoreInput.value = value;
                scoreInput.id = id;

                const inputGroupAppend = document.createElement('div');
                inputGroupAppend.classList.add('input-group-append');

                const deleteButton = document.createElement('button');
                deleteButton.classList.add('delete-factor', 'btn', 'btn-outline-danger');
                deleteButton.dataset.id = id;
                deleteButton.textContent = '×';
                deleteButton.addEventListener('click', deleteFactor);

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
                         '<select class="form-select" name="commentId" aria-label="Выберите комментарий" id="select-'+id+'">' +
                         '<option selected>Выбрать комментарий</option>' +
                         comments.map(comment =>
                             '<option value="' + comment.commentId + '">' + comment.comment + '</option>'
                         ).join('') +
                         '</select>';
                 } else {
                     commentsHtml = '<p>  </p>';
                 }
                console.log(commentsHtml)
                 const modal = document.createElement('div');
                    modal.classList.add('modal', 'fade');
                    modal.id = 'commentModal-' + id;
                    modal.setAttribute('tabindex', '-1');
                    modal.setAttribute('aria-labelledby', 'commentModal-' + id);
                    modal.setAttribute('aria-hidden', 'true');
                    modal.innerHTML =
                        '<div class="modal-dialog">'+
                        '<div class="modal-content">'+
                        '<div class="modal-header">'+
                        '<h1 class="modal-title fs-5" id="exampleModalLabel">Добавьте пояснение за снятый балл</h1>'+
                        '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>'+
                        '</div>'+
                        '<form action="/checks/'+uuid+'/'+id+'" method="post">'+
                        '<div class="modal-body" id="modalBody'+id+'">'+
                        commentsHtml+
                        '<button type="button" class="btn btn-white border shadow-sm my-2" id="writeButton'+id+'">'+
                        'Написать другой комментарий'+
                        '</button>'+
                        '</div>'+
                        '<div class="modal-footer">'+
                        '<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>'+
                        '<button type="submit" class="btn btn-dodo">Сохранить</button>'+
                        '</div>'+
                        '</form>'+
                        '</div>'+
                        '</div>';

                 document.body.appendChild(modal);
                 document.getElementById('commentModal-' + id).addEventListener('shown.bs.modal', function (event) {

                     let select = document.getElementById('select-' + id);
                     let textarea = document.getElementById('textarea'+id);
                     if (!select) {
                         const form = this.querySelector('form');
                         writeButton.style.display = "block";
                         if (textarea){
                             textarea.remove();
                         }
                         form.insertAdjacentHTML('afterbegin', commentsHtml);
                     }
                 });

                 let writeButton = document.getElementById('writeButton'+id);
                 console.log(writeButton)
                 writeButton.addEventListener('click', ()=>{
                     let select = document.getElementById('select-'+id);
                     console.log(select)
                     if (select) {
                         select.remove();
                         writeButton.style.display = "none"
                         let modalBody = document.getElementById('modalBody'+id);
                         let inputBox = document.createElement('div');
                         inputBox.id = 'inputBox'+id;
                         inputBox.classList.add('input-group', 'mb-3');
                         let input = document.createElement('textarea');
                         input.name = 'comment';
                         input.id = 'textarea'+id;
                         input.rows = 3;
                         input.classList.add('form-control')
                         input.required = true;
                         inputBox.appendChild(input)
                         modalBody.appendChild(inputBox);
                     }
                 });

                inputGroupAppend.appendChild(commentButton);
                inputGroupAppend.appendChild(deleteButton);
                inputGroup.appendChild(scoreInput);
                inputGroup.appendChild(inputGroupAppend);

                return inputGroup;


            }

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

            async function updatePercentage() {
                let response = await fetch('/api/checklist/criteria/percentage/${checkList.id}')
                let data = await response.json()
                await fetch('/api/checks/change/status/' + ${checkList.id}, {
                    method: 'post',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                });

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

                await fetch(`/api/checklist/criteria/delete/`+ criteriaId+ `?checkListId=${checkList.id}`, {
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

                await fetchToCreate(`/api/checklist/criteria/create/crit?description=`+ description, object);
                $('#critFactorModal').modal('hide')
                alert('Критический фактор добавлен');
                await showFactors()
                inputUpdate()
            });

            document.querySelectorAll('.delete-factor').forEach(button => {
                button.addEventListener('click', deleteFactor);
            });

            let uuidLink;
            const url = 'http://localhost:2345/checks/';

            async function getUrl() {
                try {
                    let response = await fetch(`/api/checks/${checkList.id}`);
                    uuidLink = await response.text();
                } catch (error) {
                    console.error('There was a проблема с fetch операцией:', error);
                }
            }

            let a = document.createElement('a');
            await getUrl();
            a.href = url + uuidLink + '/result';

            document.getElementById('getTemplate').addEventListener('click', async () => {
                const message = `Дата: ${checkList.managerWorkDate}\n` +
                    `Пиццерия: ${checkList.pizzeria.name}\n` +
                    `Менеджер: ${checkList.manager.name} ${checkList.manager.surname}\n` +
                    `Результат проверки в %: `+ await updatePercentage() +``+
                    `\nПоложительная обратная связь: \n` +
                    `Критический фактор: ` + countCriticalFactors +
                    `\nWOW фактор: ` + countWowFactors +
                    `\nПоделитесь результатами проверки с командой - отправьте результаты в общий чат команды\n\n\n` +
                    a.href;

                await navigator.clipboard.writeText(message);

                alert("Скопировано в буфер обмена!");
            });

            document.getElementById('post').addEventListener('click', fetchToResult);

            async function fetchToResult() {

                try {
                    let response = await fetch(`/api/checks/post/${checkList.uuidLink}`, {method: 'POST'});
                    if (!response.ok) {
                        let errorMessage = await response.text();
                        throw new Error(errorMessage);
                    }
                    await getUrl();
                    await navigator.clipboard.writeText(url + uuidLink + '/result');
                    console.log("Скопировано в буфер обмена!");
                    window.location.href = '/analytics';
                } catch (error) {
                    alert(error.message);
                }
            }

            async function showMaxPoint() {
                let response = await fetch('/api/checks/${checkList.id}/points')
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


        });


    </script>

</@main.layout>