// const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
// const csrfToken = document.querySelector("meta[name='_csrf_token']").content;
// const headers = {};
// headers[csrfHeader] = csrfToken;

const statuses = {
    NEW: "NEW",
    IN_PROGRESS: "IN_PROGRESS",
    DONE: "DONE"
};

async function getChecks(status) {
    try {
        const url = `/api/experts/checks?status=${status}`;
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Error fetching data: ${response.statusText}`);
        }
        const json = await response.json();
        displayChecks(json, status);
    } catch (error) {
        console.error('Failed to fetch checks:', error);
    }
}

function displayChecks(checks, status) {
    const container = document.getElementById('checks-container');
    let htmlContent = '';

    checks.forEach(c => {
        console.log("uuid = "+c.uuid)
        let url = (status === statuses.NEW || status === statuses.IN_PROGRESS)
            ? `/checks/${c.uuid}/check`
            : `/checks/${c.uuid}/result`;

        htmlContent += `
           <a href=${url} class="text-decoration-none">
            <div class="col" >
                <div class="card border-0 rounded-4 bg-primary-subtle">
                    <div class="card-body">
                        <div class="row row-cols-4">
                            <div class="col-3 d-flex align-items-center">
                                <span>${c.pizzeria}</span>
                            </div>
                            <div class="col-4 d-flex align-items-center">
                                <span>${c.manager.name} ${c.manager.surname}</span>
                            </div>
                            <div class="col-3 d-flex align-items-center">
                                <span>${c.managerWorkDate}</span></span>
                            </div>
                            <div class="col-2 d-flex align-items-center">
                                <span>${c.managerWorkStartTime}</span>-<span>${c.managerWorkEndTime}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </a>
        `;
    });

    container.innerHTML = htmlContent;
}


document.getElementById("newChecks").onclick = () => getChecks(statuses.NEW);
document.getElementById("inProgressChecks").onclick = () => getChecks(statuses.IN_PROGRESS);
document.getElementById("doneChecks").onclick = () => getChecks(statuses.DONE);
window.onload = () => getChecks(statuses.IN_PROGRESS);