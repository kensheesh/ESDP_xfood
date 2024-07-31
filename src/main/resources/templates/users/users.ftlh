<#import "../layout.ftlh" as main>
<@main.layout>

    <style>
        .card-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
            margin-top: 20px;
        }

        .card {
            position: relative;
            flex: 1 1 calc(33.333% - 20px);
            margin: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .card-body {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 100%;
        }


        .card-icons {
            position: absolute;
            top: 10px;
            right: 10px;
        }

        .card-icons .fas {
            color: #6c757d;
            margin-left: 10px;
            cursor: pointer;
        }

        .card-icons .fas:hover {
            color: #000;
        }

        @media (max-width: 768px) {
            .card {
                flex: 1 1 calc(50% - 20px);
            }
        }

        @media (max-width: 576px) {
            .card {
                flex: 1 1 100%;
            }
        }
    </style>

    <div class="card-container">
        <#list users as user>
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">ФИО: ${user.getName()} ${user.getSurname()}</h5>
                    <p class="card-text">Роль: ${user.getRole()}.</p>
                    <div class="card-text">Почта: ${user.getEmail()}</div>
                    <div class="card-text">Телефон: ${user.getPhoneNumber()}</div>
                    <div class="card-text">Enabled: <#if user.getEnabled() == true>Да<#else>Нет</#if></div>
                </div>
                <div class="card-icons">
                    <i class="fas fa-edit"></i>
                    <i class="fas fa-trash-alt"></i>
                </div>
            </div>
        </#list>
    </div>

</@main.layout>