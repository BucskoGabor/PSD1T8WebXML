// Local Storage kulcs az órarend számára
const TIMETABLE_KEY = 'orarend';

// Órarend betöltése Local Storage-ból
function loadTimetableFromStorage() {
    const data = localStorage.getItem(TIMETABLE_KEY);
    if (data) {
        const courses = JSON.parse(data);
        renderTable(courses);
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
            saveTimetableToStorage(data);
            renderTable(data);
            alert('JSON fájl sikeresen betöltve!');
        } catch (error) {
            alert('Hiba a JSON fájl betöltése során: ' + error.message);
        }
    };
    reader.readAsText(file);
}

// Táblázat renderelése
function renderTable(courses) {
    const tbody = document.getElementById('timetableBody');
    tbody.innerHTML = '';

    if (!courses || courses.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = '<td colspan="7" style="text-align: center;">Nincs megjeleníthető órarend</td>';
        tbody.appendChild(row);
        return;
    }

    courses.forEach(course => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${course.targy || '-'}</td>
            <td>${course.nap || '-'}</td>
            <td>${course.tol || '-'}</td>
            <td>${course.ig || '-'}</td>
            <td>${course.helyszin || '-'}</td>
            <td>${course.oktato || '-'}</td>
            <td>${course.szak || '-'}</td>
        `;
        tbody.appendChild(row);
    });
}

// Event listeners
document.getElementById('jsonFileTable').addEventListener('change', function (e) {
    if (e.target.files.length > 0) {
        loadJSONFile(e.target.files[0]);
    }
});

document.getElementById('loadStorageBtnTable').addEventListener('click', loadTimetableFromStorage);

// Kezdeti betöltés
loadTimetableFromStorage();
