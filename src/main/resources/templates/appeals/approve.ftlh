<#import "../layout.ftlh" as main>
<@main.layout>
<div class="container">
    <div class="card" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">${appeal.checkListsCriteria.pizzeria}</h5>
            <h6 class="card-subtitle mb-2 text-muted">Период: ${appeal.checkListsCriteria.localDate}</h6>
            <p class="card-text">Email: ${appeal.email}</p>
            <p class="card-text">ФИО: ${appeal.fullName}</p>
            <p class="card-text">Комментарий: </p>
            <div class="alert alert-warning" role="alert">
                ${appeal.comment}
            </div>
        </div>
    </div>
</div>
</@main.layout>