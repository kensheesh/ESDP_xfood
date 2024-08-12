let periodForm = document.getElementById('period');
console.log(periodForm.action);

let fromInput = document.getElementById('from');
let toInput = document.getElementById('to');

toInput.addEventListener('input', ()=>{
    let toValue = toInput.value;
    let fromValue = fromInput.value;
    periodForm.action = periodForm.action+"?from="+fromValue+"&to="+toValue;
    console.log(periodForm.action)
    periodForm.submit();
})