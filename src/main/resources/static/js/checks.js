// const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
// const csrfToken = document.querySelector("meta[name='_csrf_token']").content;
// const headers = {};
// headers[csrfHeader] = csrfToken;

const statuses = {
    NEW: "NEW",
    IN_PROGRESS: "IN_PROGRESS",
    DONE: "DONE",
    DELETED: "DELETED"
};

async function getChecks(status) {
    try {
        let url;
        if (status === statuses.DELETED) {
            url = `/api/checks/deleted`
        } else {
            url = `/api/experts/checks?status=${status}`;
        }
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
        // let url = (c.status === statuses.IN_PROGRESS)
        //     ? `/checks/${c.uuid}/fill`
        //     : `/checks/${c.uuid}`;

        let url = `/checks/${c.uuid}`;

        htmlContent += `
           <a href=${url} class="text-decoration-none d-block col">
                <div class="card border-0 rounded-4 bg-primary-subtle">
                    <div class="card-body">
                        <div class="row row-cols-4">
                            <div class="col-3 d-flex align-items-center">
                                <span>${c.pizzeria}</span>
                            </div>
                            <div class="col-4 d-flex align-items-center">
                                <span>${c.manager.name} ${c.manager.surname}</span>
                            </div>
                            <div class="col-5 d-flex align-items-center column-gap-1">
                                <span>${c.managerWorkStartTime}</span>-<span>${c.managerWorkEndTime}</span>
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
document.getElementById('deletedChecks').onclick = () => getChecks(statuses.DELETED)
window.onload = () => document.getElementById('inProgressChecks').click();