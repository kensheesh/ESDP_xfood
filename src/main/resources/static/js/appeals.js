// const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
// const csrfToken = document.querySelector("meta[name='_csrf_token']").content;
// const headers = {};
// headers[csrfHeader] = csrfToken;

const statuses = {
    NEW: "",
    ACCEPTED: true,
    REJECTED: false
};

let currentStatus = statuses.NEW;
let currentPage = 1;

async function getChecks(status, page = 1) {
    try {
        const url = `/api/appeal?s=${status}&p=${page}`;
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Error fetching data: ${response.statusText}`);
        }
        const json = await response.json();
        displayChecks(json.content);
        currentStatus = status;
        currentPage = page;
        displayPagination(json);
    } catch (error) {
        console.error('Failed to fetch checks:', error);
    }
}

function displayChecks(appeals) {
    const container = document.getElementById('appeals');
    let htmlContent = '';

    if (appeals.length !== 0) {
        appeals.forEach(appeal => {
            console.log(appeal)

            htmlContent += `
        <a href="/appeals/${appeal.id}" class="col text-decoration-none d-block">
            <div class="card border-0 rounded-4 bg-primary-subtle">
                <div class="card-body">
                    <div class="row row-cols-3">
                        <div class="col-4 d-flex align-items-center">
                            <span>${appeal.fullName}</span>
                        </div>
                        <div class="col-4 d-flex align-items-center">
                            <span>${appeal.pizzeriaName} (${appeal.locationName})</span>
                        </div>
                        <div class="col-4 d-flex align-items-center">
                            <span>Эксперт: ${appeal.expertFullName}</span>
                        </div>
                    </div>
                </div>
            </div>
        </a>
        `;}
        );
    }
    container.innerHTML = htmlContent;
}

function displayPagination (json) {
    let lowerPagination = document.getElementById('lowerPagination')

    let htmlContent = '';

     htmlContent += `
        <nav>
            <ul class="pagination m-0">
                <li class="page-item">
     `

    if (json.first) {
        htmlContent += `
            <button id="prevPage" class="page-link link-success disabled" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </button>
        </li>
        <li class="page-item">
        `
    } else {
        htmlContent += `
            <button id="prevPage" class="page-link link-success" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </button>
        </li>
        <li class="page-item">
        `
    }

    if (json.last) {
        htmlContent += `
            <button id="nextPage" class="page-link link-success disabled" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </button>
        </li>
    </ul>
</nav>
        `
    } else {
        htmlContent += `
            <button id="nextPage" class="page-link link-success" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </button>
        </li>
    </ul>
</nav>
`
    }

    lowerPagination.innerHTML = htmlContent;

    document.getElementById("nextPage").onclick = () => getChecks(currentStatus, currentPage + 1)
    document.getElementById("prevPage").onclick = () => getChecks(currentStatus, currentPage - 1)
}



document.getElementById("new").onclick = () => getChecks(statuses.NEW);
document.getElementById("accepted").onclick = () => getChecks(statuses.ACCEPTED);
document.getElementById("rejected").onclick = () => getChecks(statuses.REJECTED);
document.getElementById("nextPage").onclick = () => getChecks(currentStatus, currentPage + 1)
document.getElementById("prevPage").onclick = () => getChecks(currentStatus, currentPage - 1)
