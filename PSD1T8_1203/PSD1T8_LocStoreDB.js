// Local Storage kulcs
const STORAGE_KEY = 'hallgatok';
let editingId = null;

// Hallgatók betöltése Local Storage-ból
function loadStudents() {
    const data = localStorage.getItem(STORAGE_KEY);
    return data ? JSON.parse(data) : [];
}

// Hallgatók mentése Local Storage-ba
function saveStudents(students) {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(students));
}

// Hallgató hozzáadása vagy módosítása
function saveStudent(student) {
    let students = loadStudents();

    if (editingId !== null) {
        // Módosítás
        const index = students.findIndex(s => s.id === editingId);
        if (index !== -1) {
            students[index] = { ...student, id: editingId };
        }
        editingId = null;
    } else {
        // Új hallgató
        student.id = Date.now();
        students.push(student);
    }

    saveStudents(students);
    renderTable();
}

// Hallgató törlése
function deleteStudent(id) {
    if (confirm('Biztosan törölni szeretnéd ezt a hallgatót?')) {
        let students = loadStudents();
        students = students.filter(s => s.id !== id);
        saveStudents(students);
        renderTable();
    }
}

// Hallgató szerkesztése
function editStudent(id) {
    const students = loadStudents();
    const student = students.find(s => s.id === id);

    if (student) {
        document.getElementById('nev').value = student.nev;
        document.getElementById('evfolyam').value = student.evfolyam;
        document.getElementById('szak').value = student.szak;
        document.getElementById('email').value = student.email || '';
        document.getElementById('neptun').value = student.neptun || '';
        editingId = id;

        // Scroll to form
        document.getElementById('studentForm').scrollIntoView({ behavior: 'smooth' });
    }
}

// Táblázat renderelése
function renderTable() {
    const students = loadStudents();
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();
    const sortBy = document.getElementById('sortSelect').value;

    // Szűrés
    let filtered = students.filter(student => {
        return Object.values(student).some(value =>
            String(value).toLowerCase().includes(searchTerm)
        );
    });

    // Rendezés
    if (sortBy) {
        filtered.sort((a, b) => {
            const aVal = a[sortBy];
            const bVal = b[sortBy];

            if (typeof aVal === 'number') {
                return aVal - bVal;
            }
            return String(aVal).localeCompare(String(bVal));
        });
    }

    const tbody = document.getElementById('studentsBody');
    tbody.innerHTML = '';

    filtered.forEach(student => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${student.nev}</td>
            <td>${student.evfolyam}</td>
            <td>${student.szak}</td>
            <td>${student.email || ''}</td>
            <td>${student.neptun || ''}</td>
            <td>
                <button class="btn btn-warning btn-sm edit-btn" data-id="${student.id}">Módosít</button>
                <button class="btn btn-danger btn-sm delete-btn" data-id="${student.id}">Töröl</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Export JSON
function exportJSON() {
    const students = loadStudents();
    const dataStr = JSON.stringify(students, null, 2);
    const dataBlob = new Blob([dataStr], { type: 'application/json' });

    const link = document.createElement('a');
    link.href = URL.createObjectURL(dataBlob);
    link.download = 'hallgatok.json';
    link.click();
}

// Import JSON
function importJSON(file) {
    const reader = new FileReader();
    reader.onload = function (e) {
        try {
            const importedData = JSON.parse(e.target.result);
            const clearBefore = document.getElementById('clearBeforeImport').checked;

            let students = clearBefore ? [] : loadStudents();

            // Hozzáadás az importált adatokhoz
            importedData.forEach(student => {
                student.id = Date.now() + Math.random();
                students.push(student);
            });

            saveStudents(students);
            renderTable();
            alert('Import sikeres!');
        } catch (error) {
            alert('Hiba az import során: ' + error.message);
        }
    };
    reader.readAsText(file);
}

// Event delegation for table buttons
document.getElementById('studentsBody').addEventListener('click', function (e) {
    if (e.target.classList.contains('delete-btn')) {
        const id = parseInt(e.target.getAttribute('data-id'));
        deleteStudent(id);
    } else if (e.target.classList.contains('edit-btn')) {
        const id = parseInt(e.target.getAttribute('data-id'));
        editStudent(id);
    }
});

// Event listeners
document.getElementById('studentForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const student = {
        nev: document.getElementById('nev').value,
        evfolyam: parseInt(document.getElementById('evfolyam').value),
        szak: document.getElementById('szak').value,
        email: document.getElementById('email').value,
        neptun: document.getElementById('neptun').value.toUpperCase()
    };

    saveStudent(student);
    this.reset();
});

document.getElementById('searchInput').addEventListener('input', renderTable);
document.getElementById('sortSelect').addEventListener('change', renderTable);
document.getElementById('exportBtn').addEventListener('click', exportJSON);
document.getElementById('importFile').addEventListener('change', function (e) {
    if (e.target.files.length > 0) {
        importJSON(e.target.files[0]);
    }
});

// Kezdeti renderelés
renderTable();
