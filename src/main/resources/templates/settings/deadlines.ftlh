<#import "../layout.ftlh" as main>
<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <div class="row row-cols-md-2 row-gap-3 position-relative">
        <div class="col-md-4 col-xl-3 bg-body sidebar">
            <div class="card rounded-4 mb-2 mb-sm-0 shadow-sm border-0">
                <div class="card-body">
                    <div class="row row-cols-1 row-gap-3 h-100">
                        <div class="col">
                            <div class="row row-cols-4 row-cols-md-1 row-gap-2">
                                <div class="col">
                                    <a href="#" class="btn disabled rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-hourglass-split fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">
                                                        Дедлайны
                                                    </span>
                                    </a>
                                </div>
                                <div class="col">
                                    <button type="button" class="btn rounded-4 w-100 h-100 btn-dodo-light" data-bs-toggle="modal" data-bs-target="#tokenModal">
                                        <i class="bi bi-key fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">
                                                        Токены
                                                    </span>
                                    </button>
                                </div>
                                <div class="col">
                                    <a href="/templates" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                        <i class="bi bi-clipboard-plus fs-5 d-md-none"></i>
                                        <span class="d-none d-md-inline">
                                                        Шаблоны
                                                    </span>
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="/users" class="btn rounded-4 w-100 h-100 btn-dodo-light">
                                                    <span class="d-none d-md-inline">
                                                        Пользователи
                                                    </span>
                                        <i class="bi bi-people d-md-none fs-5"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-8 col-xl-9">

            <div id="notifications-container" class="position-absolute end-0">
            </div>

            <div>
                <div class="mx-3 my-5">
                    <h3>Период планирования карты возможностей</h3>
                    <p class="text-secondary">${oppDeadlineSetting.description}</p>
                    <div class="form-check form-check-inline ms-2">
                        <input class="form-check-input" type="radio" name="oppDeadlineRadio" id="oppDeadlineRadio0"
                               value="0" <#if oppDeadlineSetting.valueInt == 0>checked</#if>>
                        <label class="form-check-label" for="oppDeadlineRadio1">0</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="oppDeadlineRadio" id="oppDeadlineRadio1"
                               value="1" <#if oppDeadlineSetting.valueInt == 1>checked</#if>>
                        <label class="form-check-label" for="oppDeadlineRadio1">1</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="oppDeadlineRadio" id="oppDeadlineRadio2"
                               value="2" <#if oppDeadlineSetting.valueInt == 2>checked</#if>>
                        <label class="form-check-label" for="oppDeadlineRadio2">2</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="oppDeadlineRadio" id="oppDeadlineRadio3"
                               value="3" <#if oppDeadlineSetting.valueInt == 3>checked</#if>>
                        <label class="form-check-label" for="oppDeadlineRadio3">3</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="oppDeadlineRadio" id="oppDeadlineRadio4"
                               value="4" <#if oppDeadlineSetting.valueInt == 4>checked</#if>>
                        <label class="form-check-label" for="oppDeadlineRadio4">4</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="oppDeadlineRadio" id="oppDeadlineRadio5"
                               value="5" <#if oppDeadlineSetting.valueInt == 5>checked</#if>>
                        <label class="form-check-label" for="oppDeadlineRadio5">5</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="oppDeadlineRadio" id="oppDeadlineRadio6"
                               value="6" <#if oppDeadlineSetting.valueInt == 6>checked</#if>>
                        <label class="form-check-label" for="oppDeadlineRadio6">6</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="oppDeadlineRadio" id="oppDeadlineRadio7"
                               value="7" <#if oppDeadlineSetting.valueInt == 7>checked</#if>>
                        <label class="form-check-label" for="oppDeadlineRadio7">7</label>
                    </div>
                </div>
                <div class="mx-3 my-5">
                    <h3>Количество выходных в неделю</h3>
                    <p class="text-secondary">${dayOffSetting.description}</p>
                    <div class="form-check form-check-inline ms-2">
                        <input class="form-check-input" type="radio" name="dayOffRadio" id="dayOffRadio0" value="0"
                               <#if dayOffSetting.valueInt == 0>checked</#if>>
                        <label class="form-check-label" for="dayOffRadio0">0</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="dayOffRadio" id="dayOffRadio1" value="1"
                               <#if dayOffSetting.valueInt == 1>checked</#if>>
                        <label class="form-check-label" for="oppDeadlineRadio1">1</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="dayOffRadio" id="dayOffRadio2" value="2"
                               <#if dayOffSetting.valueInt == 2>checked</#if>>
                        <label class="form-check-label" for="dayOffRadio2">2</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="dayOffRadio" id="dayOffRadio3" value="3"
                               <#if dayOffSetting.valueInt == 3>checked</#if>>
                        <label class="form-check-label" for="dayOffRadio3">3</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="dayOffRadio" id="dayOffRadio4" value="4"
                               <#if dayOffSetting.valueInt == 4>checked</#if>>
                        <label class="form-check-label" for="dayOffRadio4">4</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="dayOffRadio" id="dayOffRadio5" value="5"
                               <#if dayOffSetting.valueInt == 5>checked</#if>>
                        <label class="form-check-label" for="dayOffRadio5">5</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="dayOffRadio" id="dayOffRadio6" value="6"
                               <#if dayOffSetting.valueInt == 6>checked</#if>>
                        <label class="form-check-label" for="dayOffRadio6">6</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="dayOffRadio" id="dayOffRadio7" value="7"
                               <#if dayOffSetting.valueInt == 7>checked</#if>>
                        <label class="form-check-label" for="dayOffRadio7">7</label>
                    </div>
                </div>
                <div class="mx-3 my-5">
                    <h3>Период подачи апелляции</h3>
                    <p class="text-secondary">${appealDeadlineSetting.description}</p>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="appealDeadlineRadio"
                               id="appealDeadlineRadio0" value="0"
                               <#if appealDeadlineSetting.valueInt == 0>checked</#if>>
                        <label class="form-check-label" for="appealDeadlineRadio0">0</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="appealDeadlineRadio"
                               id="appealDeadlineRadio1" value="1"
                               <#if appealDeadlineSetting.valueInt == 1>checked</#if>>
                        <label class="form-check-label" for="appealDeadlineRadio1">1</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="appealDeadlineRadio"
                               id="appealDeadlineRadio2" value="2"
                               <#if appealDeadlineSetting.valueInt == 2>checked</#if>>
                        <label class="form-check-label" for="appealDeadlineRadio2">2</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="appealDeadlineRadio"
                               id="appealDeadlineRadio3" value="3"
                               <#if appealDeadlineSetting.valueInt == 3>checked</#if>>
                        <label class="form-check-label" for="appealDeadlineRadio3">3</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="appealDeadlineRadio"
                               id="appealDeadlineRadio4" value="4"
                               <#if appealDeadlineSetting.valueInt == 4>checked</#if>>
                        <label class="form-check-label" for="appealDeadlineRadio4">4</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="appealDeadlineRadio"
                               id="appealDeadlineRadio5" value="5"
                               <#if appealDeadlineSetting.valueInt == 5>checked</#if>>
                        <label class="form-check-label" for="appealDeadlineRadio5">5</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="appealDeadlineRadio"
                               id="appealDeadlineRadio6" value="6"
                               <#if appealDeadlineSetting.valueInt == 6>checked</#if>>
                        <label class="form-check-label" for="appealDeadlineRadio6">6</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="appealDeadlineRadio"
                               id="appealDeadlineRadio7" value="7"
                               <#if appealDeadlineSetting.valueInt == 7>checked</#if>>
                        <label class="form-check-label" for="appealDeadlineRadio7">7</label>
                    </div>
                </div>
            </div>
            <div class="text-end">
                <button id="submitSettings" class="btn btn-secondary">Сохранить настройки</button>
            </div>
        </div>
    </div>

    <div class="modal fade" id="tokenModal" tabindex="-1" aria-labelledby="tokenModal"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="tokenModal">
                        Привязать токен доступа
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>
                <form id="bearerForm">
                    <div class="modal-body">
                        <label for="bearer" class="form-label">Токен</label>
                        <input type="text" id="bearer" name="bearer" class="form-control" required>
                    </div>

                    <div class="modal-footer">
                        <button type="submit" id="pushToken" class="btn btn-primary" data-bs-dismiss="modal"
                                data-bs-target="#tokenModal">
                            Сохранить токен
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </div>

    <script>
        document.getElementById('pushToken').addEventListener('click', async function () {
            const form = document.getElementById('bearerForm');
            const formData = new FormData(form);

            try {
                const response = await fetch('/api/bearer', {
                    method: 'POST',
                    body: formData
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                // Optionally, handle the response here
                const result = await response.json();
                console.log(result);

                // Close the modal if needed
                const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
                modal.hide();
            } catch (error) {
                console.error('There was a problem with the fetch operation:', error);
            }
        });
    </script>
    <script>
        document.getElementById('submitSettings').addEventListener('click', function () {
            const oppDeadlineValue = document.querySelector('input[name="oppDeadlineRadio"]:checked').value;
            const dayOffValue = document.querySelector('input[name="dayOffRadio"]:checked').value;
            const appealDeadlineValue = document.querySelector('input[name="appealDeadlineRadio"]:checked').value;

            console.log('Settings: ' + oppDeadlineValue + ', ' + dayOffValue + ', ' + appealDeadlineValue);

            const data = {
                oppDeadline: oppDeadlineValue,
                dayOff: dayOffValue,
                appealDeadline: appealDeadlineValue
            };

            fetch('/deadlines', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            })
                .catch((error) => {
                    console.error('Ошибка:', error);
                });

            const maxNotifications = 3;
            let notificationCount = 0;
            const notificationsContainer = document.getElementById('notifications-container');

            let alertDiv = document.createElement('div');
            alertDiv.classList.add('alert', 'alert-success', 'alert-dismissible');
            alertDiv.setAttribute('role', 'alert');
            alertDiv.innerText = 'Настройки успешно сохранены';

            let closeButton = document.createElement('button');
            closeButton.classList.add('btn-close');
            closeButton.setAttribute('type', 'button');
            closeButton.setAttribute('data-bs-dismiss', 'alert');
            closeButton.setAttribute('aria-label', 'close');

            closeButton.addEventListener('click', () => {
                alertDiv.remove();
                notificationCount--;
            });

            alertDiv.appendChild(closeButton);

            notificationsContainer.appendChild(alertDiv);

            notificationCount++;

            if (notificationCount > maxNotifications) {
                let notifications = notificationsContainer.querySelectorAll('.alert');
                notifications[0].remove();
                notificationCount--;
            }

            setTimeout(() => {
                alertDiv.remove();
                notificationCount--;
            }, 5000);
        });
    </script>
</@main.layout>