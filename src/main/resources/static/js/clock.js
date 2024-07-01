function createClock(time) {
    const selectedLanguages = JSON.parse(localStorage.getItem('selectedLanguages'));
    const timesSection = document.querySelector(time);

    selectedLanguages.forEach(timezone => {
        const div = document.createElement('div');

        function extractTimeZone(timeZoneWithOffset) {
            return timeZoneWithOffset.split(/\s|\(/)[0];
        }

        console.log(extractTimeZone(timezone))

        div.setAttribute('data-timezone', extractTimeZone(timezone));
        const span = document.createElement('span');
        span.classList.add('time_name');
        span.textContent = timezone.split('/')[1] + '\u00A0';

        const output = document.createElement('output');
        output.classList.add('time_clock');
        output.textContent = '\u00A000:00:00';

        div.appendChild(span);
        div.appendChild(output);

        timesSection.appendChild(div);
    });
}

function fillClock(time) {
    const locations = document.querySelectorAll(time);

    const updateTimes = function () {
        locations.forEach(location => {
            const output = location.querySelector("output");
            const timezone = location.getAttribute("data-timezone");
            const now = luxon.DateTime.now().setZone(timezone);
            output.innerHTML = now.toFormat("HH:mm:ss");
        });
    };
    updateTimes();
    setInterval(updateTimes, 1000);
}

const selectedLanguages = JSON.parse(localStorage.getItem('selectedLanguages'));
if (selectedLanguages !== null && selectedLanguages !== undefined) {
    createClock(".times");
    createClock(".times_second");
    fillClock("section.times div");
    fillClock("section.times_second div");
}