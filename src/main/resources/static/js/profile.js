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


all_timezones = [
    "Asia/Aden",
    "Asia/Almaty",
    "Asia/Amman",
    "Asia/Anadyr",
    "Asia/Aqtau",
    "Asia/Aqtobe",
    "Asia/Ashgabat",
    "Asia/Ashkhabad",
    "Asia/Atyrau",
    "Asia/Baghdad",
    "Asia/Bahrain",
    "Asia/Baku",
    "Asia/Bangkok",
    "Asia/Barnaul",
    "Asia/Beirut",
    "Asia/Bishkek",
    "Asia/Brunei",
    "Asia/Calcutta",
    "Asia/Chita",
    "Asia/Choibalsan",
    "Asia/Chongqing",
    "Asia/Chungking",
    "Asia/Colombo",
    "Asia/Dacca",
    "Asia/Damascus",
    "Asia/Dhaka",
    "Asia/Dili",
    "Asia/Dubai",
    "Asia/Dushanbe",
    "Asia/Famagusta",
    "Asia/Gaza",
    "Asia/Harbin",
    "Asia/Hebron",
    "Asia/Ho_Chi_Minh",
    "Asia/Hong_Kong",
    "Asia/Hovd",
    "Asia/Irkutsk",
    "Asia/Istanbul",
    "Asia/Jakarta",
    "Asia/Jayapura",
    "Asia/Jerusalem",
    "Asia/Kabul",
    "Asia/Kamchatka",
    "Asia/Karachi",
    "Asia/Kashgar",
    "Asia/Kathmandu",
    "Asia/Katmandu",
    "Asia/Khandyga",
    "Asia/Kolkata",
    "Asia/Krasnoyarsk",
    "Asia/Kuala_Lumpur",
    "Asia/Kuching",
    "Asia/Kuwait",
    "Asia/Macao",
    "Asia/Macau",
    "Asia/Magadan",
    "Asia/Makassar",
    "Asia/Manila",
    "Asia/Muscat",
    "Asia/Nicosia",
    "Asia/Novokuznetsk",
    "Asia/Novosibirsk",
    "Asia/Omsk",
    "Asia/Oral",
    "Asia/Phnom_Penh",
    "Asia/Pontianak",
    "Asia/Pyongyang",
    "Asia/Qatar",
    "Asia/Qostanay",
    "Asia/Qyzylorda",
    "Asia/Rangoon",
    "Asia/Riyadh",
    "Asia/Saigon",
    "Asia/Sakhalin",
    "Asia/Samarkand",
    "Asia/Seoul",
    "Asia/Shanghai",
    "Asia/Singapore",
    "Asia/Srednekolymsk",
    "Asia/Taipei",
    "Asia/Tashkent",
    "Asia/Tbilisi",
    "Asia/Tehran",
    "Asia/Tel_Aviv",
    "Asia/Thimbu",
    "Asia/Thimphu",
    "Asia/Tokyo",
    "Asia/Tomsk",
    "Asia/Ujung_Pandang",
    "Asia/Ulaanbaatar",
    "Asia/Ulan_Bator",
    "Asia/Urumqi",
    "Asia/Ust-Nera",
    "Asia/Vientiane",
    "Asia/Vladivostok",
    "Asia/Yakutsk",
    "Asia/Yangon",
    "Asia/Yekaterinburg",
    "Asia/Yerevan",
    "Europe/Amsterdam",
    "Europe/Andorra",
    "Europe/Astrakhan",
    "Europe/Athens",
    "Europe/Belfast",
    "Europe/Belgrade",
    "Europe/Berlin",
    "Europe/Bratislava",
    "Europe/Brussels",
    "Europe/Bucharest",
    "Europe/Budapest",
    "Europe/Busingen",
    "Europe/Chisinau",
    "Europe/Copenhagen",
    "Europe/Dublin",
    "Europe/Gibraltar",
    "Europe/Guernsey",
    "Europe/Helsinki",
    "Europe/Isle_of_Man",
    "Europe/Istanbul",
    "Europe/Jersey",
    "Europe/Kaliningrad",
    "Europe/Kiev",
    "Europe/Kirov",
    "Europe/Lisbon",
    "Europe/Ljubljana",
    "Europe/London",
    "Europe/Luxembourg",
    "Europe/Madrid",
    "Europe/Malta",
    "Europe/Mariehamn",
    "Europe/Minsk",
    "Europe/Monaco",
    "Europe/Moscow",
    "Europe/Nicosia",
    "Europe/Oslo",
    "Europe/Paris",
    "Europe/Podgorica",
    "Europe/Prague",
    "Europe/Riga",
    "Europe/Rome",
    "Europe/Samara",
    "Europe/San_Marino",
    "Europe/Sarajevo",
    "Europe/Saratov",
    "Europe/Simferopol",
    "Europe/Skopje",
    "Europe/Sofia",
    "Europe/Stockholm",
    "Europe/Tallinn",
    "Europe/Tirane",
    "Europe/Tiraspol",
    "Europe/Ulyanovsk",
    "Europe/Uzhgorod",
    "Europe/Vaduz",
    "Europe/Vatican",
    "Europe/Vienna",
    "Europe/Vilnius",
    "Europe/Volgograd",
    "Europe/Warsaw",
    "Europe/Zagreb",
    "Europe/Zaporozhye",
    "Europe/Zurich",
]

const timezoneItems = document.getElementById('timezone-items');

function populateTimezones() {
    timezoneItems.innerHTML = "";
    all_timezones.forEach(timezone => {
        const now = luxon.DateTime.now().setZone(timezone);

        const offsetInMinutes = now.offset;
        const hours = Math.floor(Math.abs(offsetInMinutes) / 60);
        // const minutes = Math.abs(offsetInMinutes) % 60;
        const sign = offsetInMinutes >= 0 ? '+' : '-';

        const doneTime = `(` + `${sign}${hours}` + `)`

        const li = document.createElement('li');
        li.className = 'item';
        li.innerHTML = `
                    <span class="checkbox">
                        <i class="fa-solid fa-check check-icon"></i>
                    </span>
                    <span class="item-text">${timezone}&nbsp;${doneTime}</span>
                `;
        timezoneItems.appendChild(li);
    });

    const items = document.querySelectorAll(".item");
    items.forEach(item => {
        item.addEventListener("click", () => {
            let checked = document.querySelectorAll(".checked");
            if (!item.classList.contains("checked") && checked.length >= 3) {
                alert("Вы не можете выбрать больше 3 часовых поясов");
                return;
            }
            item.classList.toggle("checked");

            checked = document.querySelectorAll(".checked");
            let btnText = document.querySelector(".btn-text");

            if (checked && checked.length > 0) {
                btnText.innerText = checked.length + '\u00A0' + "выбрано";
            } else {
                btnText.innerText = "Выберите часовой пояс";
            }
        });
    });
}

populateTimezones();

const selectBtn = document.querySelector(".select-btn"),
    saveBtn = document.getElementById("saveBtn"),
    searchInput = document.getElementById("timezone-search");

selectBtn.addEventListener("click", () => {
    selectBtn.classList.toggle("open");
});

saveBtn.addEventListener("click", () => {

    let selectedItems = [];
    document.querySelectorAll(".checked .item-text").forEach(item => {
        selectedItems.push(item.innerText);
    });

    if (selectedItems.length !== 3) {
        alert("Пожалуйста, выберите 3 часовых пояса, ни меньше и не больше");
        return;
    }

    localStorage.setItem("selectedLanguages", JSON.stringify(selectedItems));
    alert("Выбранные часовые пояса сохранены!");
    location.reload();
});

window.addEventListener("load", () => {
    let savedItems = JSON.parse(localStorage.getItem("selectedLanguages"));
    if (savedItems) {
        const items = document.querySelectorAll(".item");
        items.forEach(item => {
            if (savedItems.includes(item.querySelector(".item-text").innerText)) {
                item.classList.add("checked");
            }
        });
        let btnText = document.querySelector(".btn-text");
        btnText.innerText = savedItems.length + '\u00A0' + "выбрано";
    }
});

searchInput.addEventListener("input", function () {
    const filter = searchInput.value.toLowerCase();
    const items = document.querySelectorAll(".item");
    items.forEach(item => {
        const text = item.querySelector(".item-text").innerText.toLowerCase();
        item.style.display = text.includes(filter) ? "" : "none";
    });
});

const resetBtn = document.querySelector(".btn-reset");

resetBtn.addEventListener("click", () => {
    const items = document.querySelectorAll(".item.checked");
    items.forEach(item => {
        item.classList.remove("checked");
    });
    document.querySelector(".btn-text").innerText = "Выберите часовой пояс";
});