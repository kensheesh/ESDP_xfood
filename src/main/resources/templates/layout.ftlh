<#import "spring.ftl" as spring>

<#macro layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    <#else>
    </#if>

    <!doctype html>
    <html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Expert checks</title>

        <link href="/DataTables/datatables.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <!-- Fontawesome CDN Link -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
        <link rel="stylesheet" href="/css/layout.css">
    </head>

    <body>
    <header>
        <nav class="navbar navbar-expand fw-medium bg-white border-bottom " id="navbar">
            <div class="container">
                <a class="navbar-brand fw-semibold " href="#">XFOOD</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto">
                        <#if authorizedUser??>
                            <li class="nav-item dropdown" href="#">
                                <a
                                        class="nav-link dropdown-toggle d-flex flex-row flex-nowrap column-gap-2 align-items-center py-1"
                                        href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="bi bi-person-circle fs-5 m-0 p-0 "></i>
                                    <span>
                                    ${authorizedUser.name}
                                </span>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li><a class="dropdown-item" href="/profile">Мой профиль</a></li>
                                    <li><a class="dropdown-item" href="#">Карта возможностей</a></li>
                                    <#if authorizedUser.authorities[0].authority??>
                                        <#if authorizedUser.authorities[0].authority == "ROLE_ADMIN" ||
                                        authorizedUser.authorities[0].authority == "ROLE_SUPERVISOR">
                                            <li>
                                                <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                                        data-bs-target="#addUser">
                                                    Создать пользователя
                                                </button>
                                            </li>
                                        <#else>
                                        </#if>
                                    </#if>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li><a class="dropdown-item" href="/logout">Выход</a></li>
                                </ul>
                            </li>
                        </#if>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <main class="container my-4">
        <#nested spring>
    </main>

    <div class="modal fade modal-lg" id="addUser" tabindex="-1" aria-labelledby="addUserLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">

                <div class="modal-header">
                    <h5 class="modal-title" id="addUserLabel">Введите данные нового сотдрудника</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="container mt-4">
                    <form id="addUserForm" class="needs-validation" novalidate>
                        <div class="mb-4">
                            <label for="name" class="form-label">Имя</label>
                            <input type="text" id="name" name="name" class="form-control" required>
                        </div>

                        <div class="mb-4">
                            <label for="surname" class="form-label">Фамилия</label>
                            <input type="text" id="surname" name="surname" class="form-control" required>
                        </div>

                        <div class="mb-4">
                            <label class="form-label" for="tgLink">Ccылка на телеграм</label>
                            <input type="text" id="tgLink" name="tgLink" class="form-control" required
                                   pattern="^t\.me/.*"
                                   placeholder="Пожалуйста, введите ссылку на телеграм, начиная с t.me/."/>
                        </div>

                        <div class="mb-4">
                            <label for="email" class="form-label">Адрес эл. почты</label>
                            <input type="email" id="email" name="email" class="form-control" required>
                            <div id="emailError" class="invalid-feedback"></div>
                        </div>

                        <div class="mb-4">
                            <label for="password" class="form-label">Пароль</label>
                            <input type="password" id="password" name="password" class="form-control" required
                                   pattern="^(?=.*\d)(?=.*[a-zA-Z]).{8,}$"
                                   placeholder="Пароль должен быть не менее 8 символов, содержать цифры и буквы."/>
                        </div>

                        <#if authorizedUser??>
                            <#if authorizedUser.authorities[0].authority??>
                                <#if authorizedUser.authorities[0].authority == "ROLE_ADMIN" >
                                    <div class="mb-4">
                                        <div class="d-flex ">
                                            <p class="me-3">Выберите роль:</p>
                                            <div class="form-check me-3">
                                                <input type="radio" id="expert" name="role" value="expert"
                                                       class="form-check-input"
                                                       checked required>
                                                <label class="form-check-label" for="expert">Эксперт</label>
                                            </div>
                                            <div class="form-check">
                                                <input type="radio" id="supervisor" name="role" value="supervisor"
                                                       class="form-check-input" required>
                                                <label class="form-check-label" for="supervisor">Руководитель</label>
                                            </div>
                                        </div>
                                    </div>
                                <#else>
                                    <div class="mb-4">
                                        <div class="d-flex ">
                                            <p class="me-3">Выберите роль:</p>
                                            <div class="form-check me-3">
                                                <input type="radio" id="expert" name="role" value="expert"
                                                       class="form-check-input"
                                                       checked required>
                                                <label class="form-check-label" for="expert">Эксперт</label>
                                            </div>
                                        </div>
                                    </div>
                                </#if>
                            <#else>
                            </#if>
                        <#else>
                        </#if>

                        <div class="d-flex justify-content-end">
                            <button type="submit" class="btn btn-primary mb-2">Создать</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="/js/validation.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="/DataTables/datatables.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/i18n/ru.min.js"></script>

    <script>
        $(document).ready(function() {
           $('#myTable').DataTable({
                responsive: true,
                language: {
                    url: '/DataTables/ru.json',
                },
                rowGroup: {
                    dataSrc: 2,
                    endRender: function(rows) {
                        let totalColumns = rows.columns().header().length;
                        let lastColumnIndex = totalColumns - 2;

                        let averageColumnIndex = rows.columns().indexes().filter(index => {
                            return $(rows.column(index).header()).text().includes('Среднее за пиццерию');
                        })[0];


                        if (averageColumnIndex !== undefined) {
                            $('#myTable').DataTable().column(averageColumnIndex).visible(false);
                        }

                        let avg = rows
                            .data()
                            .pluck(lastColumnIndex)
                            .reduce(function(a, b) {
                                return a + parseFloat(b);
                            }, 0) / rows.count();
                        return $('<tr/>')
                            .append('<td colspan="3">Среднее за пиццерию</td>')
                            .append('<td colspan="' + (rows.columns().header().length - 4) + '">' + avg.toFixed(2) + '</td>');
                    }
                },
                layout: {
                    topStart: {
                        buttons: [
                            {
                                extend: 'excelHtml5',
                                autoFilter: true,
                                sheetName: 'Статистика',
                                filename: 'Статистика',
                                header: true,
                                footer: true,
                                exportOptions: {
                                    modifier: {
                                        search: 'applied',
                                    }}
                            }
                        ]
                    }
                }

        });

        })
    </script>
    <script src="https://cdn.jsdelivr.net/npm/moment/min/moment-with-locales.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    <script src="https://cdn.datatables.net/2.1.2/js/dataTables.bootstrap5.js"></script>
    <script src="https://cdn.datatables.net/buttons/3.1.0/js/buttons.html5.min.js"></script>
    <script>
        $(function() {
           moment.locale('ru');
            var start = moment().subtract(29, 'days');
            var end = moment();

            function cb(start, end) {
                $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                $('#from').val(start.format('YYYY-MM-DD'));
                $('#to').val(end.format('YYYY-MM-DD'));
            }

            $('#reportrange').daterangepicker({
                startDate: start,
                endDate: end,
                ranges: {
                    'Сегодня': [moment(), moment()],
                    'Вчера': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                    'Последние 7 дней': [moment().subtract(6, 'days'), moment()],
                    'Последние 30 дней': [moment().subtract(29, 'days'), moment()],
                    'Этот месяц': [moment().startOf('month'), moment().endOf('month')],
                    'Последний месяц': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                },
                locale: {
                    format: 'DD.MM.YYYY',
                    separator: ' - ',
                    applyLabel: 'Применить',
                    cancelLabel: 'Отменить',
                    fromLabel: 'С',
                    toLabel: 'По',
                    customRangeLabel: 'Выбрать диапазон',
                    daysOfWeek: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
                    monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
                    firstDay: 1
                }
            }, cb);

            cb(start, end);

            $('#reportrange').on('apply.daterangepicker', function(ev, picker) {
                start = picker.startDate;
                end = picker.endDate;
                $('#from').val(start.format('YYYY-MM-DD'));
                $('#to').val(end.format('YYYY-MM-DD'));
            });
        });
    </script>
    <script>
        $(document).ready(function () {
            $('#myTable').DataTable({
                responsive: true,
                language: {
                    url: '/DataTables/ru.json',
                },
                rowGroup: {
                    dataSrc: 2,
                    endRender: function (rows) {
                        let totalColumns = rows.columns().header().length;
                        let lastColumnIndex = totalColumns - 2;

                        let averageColumnIndex = rows.columns().indexes().filter(index => {
                            return $(rows.column(index).header()).text().includes('Среднее за пиццерию');
                        })[0];


                        if (averageColumnIndex !== undefined) {
                            $('#myTable').DataTable().column(averageColumnIndex).visible(false);
                        }

                        let avg = rows
                            .data()
                            .pluck(lastColumnIndex)
                            .reduce(function (a, b) {
                                return a + parseFloat(b);
                            }, 0) / rows.count();
                        return $('<tr/>')
                            .append('<td colspan="3">Среднее за пиццерию</td>')
                            .append('<td colspan="' + (rows.columns().header().length - 4) + '">' + avg.toFixed(2) + '</td>');
                    }
                },
                layout: {
                    topStart: {
                        buttons: [
                            {
                                extend: 'excelHtml5',
                                autoFilter: true,
                                sheetName: 'Статистика',
                                filename: 'Статистика',
                                header: true,
                                footer: true,
                                exportOptions: {
                                    modifier: {
                                        search: 'applied',
                                    }
                                }
                            }
                        ]
                    }
                }

            });

        })
    </script>
    </body>
    </html>
</#macro>