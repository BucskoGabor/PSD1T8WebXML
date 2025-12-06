// Local Storage kulcs az órarend számára
const TIMETABLE_KEY = 'orarend';
let currentCourses = [];
let currentIndex = 0;

// Órarend betöltése Local Storage-ból
function loadTimetableFromStorage() {
    const data = localStorage.getItem(TIMETABLE_KEY);
    if (data) {
        currentCourses = JSON.parse(data);
        currentIndex = 0;
        displayCourse();
        alert('Órarend betöltve a LocalStorage-ból!');
    } else {
        alert('Nincs mentett órarend a LocalStorage-ban!');
    }
}

// Órarend mentése Local Storage-ba
function saveTimetableToStorage(courses) {
    localStorage.setItem(TIMETABLE_KEY, JSON.stringify(courses));
}

// JSON fájl betöltése
function loadJSONFile(file) {
    const reader = new FileReader();
    reader.onload = function (e) {
        try {
            const data = JSON.parse(e.target.result);
            currentCourses = data;
            saveTimetableToStorage(data);
            currentIndex = 0;
            displayCourse();
            alert('JSON fájl sikeresen betöltve!');
        } catch (error) {
            alert('Hiba a JSON fájl betöltése során: ' + error.message);
        }
    };
    reader.readAsText(file);
}

// Aktuális kurzus megjelenítése
function displayCourse() {
    if (currentCourses.length === 0) {
        document.getElementById('detailNap').textContent = '-';
        document.getElementById('detailTol').textContent = '-';
        document.getElementById('detailIg').textContent = '-';
        document.getElementById('detailHelyszin').textContent = '-';
        document.getElementById('detailOktato').textContent = '-';
        document.getElementById('detailSzak').textContent = '-';
        return;
    }

    const course = currentCourses[currentIndex];
    document.getElementById('detailNap').textContent = course.nap || '-';
    document.getElementById('detailTol').textContent = course.tol || '-';
    document.getElementById('detailIg').textContent = course.ig || '-';
    document.getElementById('detailHelyszin').textContent = course.helyszin || '-';
    document.getElementById('detailOktato').textContent = course.oktato || '-';
    document.getElementById('detailSzak').textContent = course.szak || '-';

    // Frissítjük a címet a tárgy nevével
    const title = document.querySelector('.display-section h2');
    title.textContent = course.targy || 'Szoftvertesztelés';
}

// Keresés tárgy szerint
function searchCourses() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase().trim();

    if (!searchTerm) {
        // Ha üres a keresés, betöltjük az összes kurzust
        const allCourses = localStorage.getItem(TIMETABLE_KEY);
        if (allCourses) {
            currentCourses = JSON.parse(allCourses);
        }
    } else {
        // Szűrés
        const allCourses = localStorage.getItem(TIMETABLE_KEY);
        if (allCourses) {
            const courses = JSON.parse(allCourses);
            currentCourses = courses.filter(course =>
                course.targy && course.targy.toLowerCase().includes(searchTerm)
            );
        }
    }

    currentIndex = 0;
    displayCourse();
}

// Előző kurzus
function previousCourse() {
    if (currentCourses.length === 0) return;

    currentIndex--;
    if (currentIndex < 0) {
        currentIndex = currentCourses.length - 1;
    }
    displayCourse();
}

// Következő kurzus
function nextCourse() {
    if (currentCourses.length === 0) return;

    currentIndex++;
    if (currentIndex >= currentCourses.length) {
        currentIndex = 0;
    }
    displayCourse();
}

// Event listeners
document.getElementById('jsonFile').addEventListener('change', function (e) {
    if (e.target.files.length > 0) {
        loadJSONFile(e.target.files[0]);
    }
});

document.getElementById('loadStorageBtn').addEventListener('click', loadTimetableFromStorage);
document.getElementById('searchInput').addEventListener('input', searchCourses);
document.getElementById('prevBtn').addEventListener('click', previousCourse);
document.getElementById('nextBtn').addEventListener('click', nextCourse);

// Kezdeti betöltés
loadTimetableFromStorage();
