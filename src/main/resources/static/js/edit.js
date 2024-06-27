document.addEventListener('DOMContentLoaded', () => {
    let values = document.querySelectorAll('[id^="value-"]');
    let sum = document.getElementById('sum');
    let totalSum = 0;

    values.forEach((element) => {
        totalSum += parseInt(element.value) || 0;
    });
    sum.innerHTML = `<p>${totalSum}</p>`;


    values.forEach((element) => {
        element.addEventListener('input', () => {
            updateTotalSum();
        });
    });


    function updateTotalSum() {
        totalSum = 0;
        values.forEach((element) => {
            totalSum += parseInt(element.value) || 0;
        });
        sum.innerHTML = `<p>${totalSum}</p>`;
    }
});
/*    let experts = document.querySelectorAll('[id^="expert-"]');
    experts.forEach((expertInfo)=>{
        expertInfo.addEventListener('click', ()=>{
            changeContent(expertInfo.id);
        })
    })
function changeContent (expertInfo){
    let id = expertInfo.slice(7);
    let expert = document.getElementById('expert-info-'+id).innerHTML;
    let startTime = document.getElementById('start-time-info-'+id).innerHTML;
    let endTime = document.getElementById('end-time-info-'+id).innerHTML;

    let opportunity = document.getElementById('opportunity');
    opportunity.innerHTML = '';
    let empty = document.createElement('div')
    empty.innerHTML =
        ' <h5 class="card-title">'+expert+'</h5>'+
        '<div class="form-group my-2">'+
        '<label for="expertStartTime" class="form-label">Время начала смены:</label>'+
        '<input type="time" id="expertStartTime" class="form-control" name="expertStartTime" value="'+startTime+'" required>'+
        '</div>'+
        '<div class="form-group my-2">'+
        '<label for="expertEndTime" class="form-label">Время конца смены:</label>'+
        '<input type="time" id="expertEndTime" class="form-control" name="expertEndTime" value="'+endTime+'" required>'+
        '</div>';
    opportunity.appendChild(empty);
    let modal = document.getElementById('opportunityModal');
    modal.classList.remove('show');
    modal.style.display = 'none';
    document.body.classList.remove('modal-open');
    let modalBackdrop = document.getElementsByClassName('modal-backdrop')[0];
    if (modalBackdrop) {
        modalBackdrop.parentNode.removeChild(modalBackdrop);
    }

}*/


