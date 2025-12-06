// IndexedDB inicializálás
const DB_NAME = 'AutoTulajDB_v1';
const DB_VERSION = 1;
let db;

// Adatbázis megnyitása és inicializálása
function initDB() {
    return new Promise((resolve, reject) => {
        const request = indexedDB.open(DB_NAME, DB_VERSION);

        request.onerror = () => reject(request.error);
        request.onsuccess = () => {
            db = request.result;
            resolve(db);
        };

        request.onupgradeneeded = (event) => {
            db = event.target.result;

            // Tulajdonos object store
            if (!db.objectStoreNames.contains('tulajdonos')) {
                const ownerStore = db.createObjectStore('tulajdonos', { keyPath: 'id', autoIncrement: true });
                ownerStore.createIndex('nev', 'nev', { unique: false });
            }

            // Autó object store
            if (!db.objectStoreNames.contains('auto')) {
                const carStore = db.createObjectStore('auto', { keyPath: 'id', autoIncrement: true });
                carStore.createIndex('rendszam', 'rendszam', { unique: false });
                carStore.createIndex('tulajId', 'tulajId', { unique: false });
            }
        };
    });
}

// CRUD - Tulajdonos
function addOwner(owner) {
    return new Promise((resolve, reject) => {
        const transaction = db.transaction(['tulajdonos'], 'readwrite');
        const store = transaction.objectStore('tulajdonos');
        const request = store.add(owner);

        request.onsuccess = () => resolve(request.result);
        request.onerror = () => reject(request.error);
    });
}

function getAllOwners() {
    return new Promise((resolve, reject) => {
        const transaction = db.transaction(['tulajdonos'], 'readonly');
        const store = transaction.objectStore('tulajdonos');
        const request = store.getAll();

        request.onsuccess = () => resolve(request.result);
        request.onerror = () => reject(request.error);
    });
}

function deleteOwner(id) {
    return new Promise((resolve, reject) => {
        const transaction = db.transaction(['tulajdonos'], 'readwrite');
        const store = transaction.objectStore('tulajdonos');
        const request = store.delete(id);

        request.onsuccess = () => resolve();
        request.onerror = () => reject(request.error);
    });
}

// CRUD - Autó
function addCar(car) {
    return new Promise((resolve, reject) => {
        const transaction = db.transaction(['auto'], 'readwrite');
        const store = transaction.objectStore('auto');
        const request = store.add(car);

        request.onsuccess = () => resolve(request.result);
        request.onerror = () => reject(request.error);
    });
}

function getAllCars() {
    return new Promise((resolve, reject) => {
        const transaction = db.transaction(['auto'], 'readonly');
        const store = transaction.objectStore('auto');
        const request = store.getAll();

        request.onsuccess = () => resolve(request.result);
        request.onerror = () => reject(request.error);
    });
}

function deleteCar(id) {
    return new Promise((resolve, reject) => {
        const transaction = db.transaction(['auto'], 'readwrite');
        const store = transaction.objectStore('auto');
        const request = store.delete(id);

        request.onsuccess = () => resolve();
        request.onerror = () => reject(request.error);
    });
}

// Tulajdonos lista renderelése
async function renderOwners() {
    const owners = await getAllOwners();
    const searchTerm = document.getElementById('ownerSearch').value.toLowerCase();

    const filtered = owners.filter(owner =>
        owner.nev.toLowerCase().includes(searchTerm)
    );

    const ownersList = document.getElementById('ownersList');

    if (filtered.length === 0) {
        ownersList.innerHTML = '<p>Nincs megjeleníthető tulajdonos.</p>';
    } else {
        ownersList.innerHTML = filtered.map(owner => `
            <div class="item-card">
                <h3>${owner.nev}</h3>
                <p><strong>Cím:</strong> ${owner.cim}</p>
                <p><strong>Telefon:</strong> ${owner.telefon}</p>
                <button class="btn btn-danger btn-sm" onclick="handleDeleteOwner(${owner.id})">Töröl</button>
            </div>
        `).join('');
    }

    // Frissítjük a select mezőket
    updateOwnerSelects(owners);
}

// Autó lista renderelése
async function renderCars() {
    const cars = await getAllCars();
    const owners = await getAllOwners();
    const searchTerm = document.getElementById('carSearch').value.toLowerCase();
    const filterOwner = document.getElementById('carFilter').value;

    let filtered = cars.filter(car => {
        const matchesSearch = car.rendszam.toLowerCase().includes(searchTerm) ||
            car.tipus.toLowerCase().includes(searchTerm) ||
            car.szin.toLowerCase().includes(searchTerm);
        const matchesFilter = !filterOwner || car.tulajId == filterOwner;
        return matchesSearch && matchesFilter;
    });

    const carsList = document.getElementById('carsList');

    if (filtered.length === 0) {
        carsList.innerHTML = '<p>Nincs megjeleníthető autó.</p>';
    } else {
        carsList.innerHTML = filtered.map(car => {
            const owner = owners.find(o => o.id === car.tulajId);
            const ownerName = owner ? owner.nev : 'Ismeretlen';

            return `
                <div class="item-card">
                    <h3>${car.rendszam}</h3>
                    <p><strong>Típus:</strong> ${car.tipus}</p>
                    <p><strong>Szín:</strong> ${car.szin}</p>
                    <p><strong>Kor:</strong> ${car.kor} év</p>
                    <p><strong>Ár:</strong> ${car.ar.toLocaleString('hu-HU')} Ft</p>
                    <p><strong>Tulajdonos:</strong> ${ownerName}</p>
                    <button class="btn btn-danger btn-sm" onclick="handleDeleteCar(${car.id})">Töröl</button>
                </div>
            `;
        }).join('');
    }
}

// Select mezők frissítése
function updateOwnerSelects(owners) {
    const carTulajSelect = document.getElementById('carTulaj');
    const carFilterSelect = document.getElementById('carFilter');

    // Autó form select
    const currentValue = carTulajSelect.value;
    carTulajSelect.innerHTML = '<option value="">Válassz tulajdonost</option>';
    owners.forEach(owner => {
        const option = document.createElement('option');
        option.value = owner.id;
        option.textContent = owner.nev;
        carTulajSelect.appendChild(option);
    });
    if (currentValue) carTulajSelect.value = currentValue;

    // Szűrő select
    const currentFilter = carFilterSelect.value;
    carFilterSelect.innerHTML = '<option value="">Összes tulajdonos</option>';
    owners.forEach(owner => {
        const option = document.createElement('option');
        option.value = owner.id;
        option.textContent = owner.nev;
        carFilterSelect.appendChild(option);
    });
    if (currentFilter) carFilterSelect.value = currentFilter;
}

// Event handlers
async function handleDeleteOwner(id) {
    if (confirm('Biztosan törölni szeretnéd ezt a tulajdonost?')) {
        await deleteOwner(id);
        await renderOwners();
        await renderCars();
    }
}

async function handleDeleteCar(id) {
    if (confirm('Biztosan törölni szeretnéd ezt az autót?')) {
        await deleteCar(id);
        await renderCars();
    }
}

// Export/Import funkciók
async function exportAllData() {
    const owners = await getAllOwners();
    const cars = await getAllCars();

    const data = {
        tulajdonosok: owners,
        autok: cars
    };

    const dataStr = JSON.stringify(data, null, 2);
    const dataBlob = new Blob([dataStr], { type: 'application/json' });

    const link = document.createElement('a');
    link.href = URL.createObjectURL(dataBlob);
    link.download = 'autotulaj_export.json';
    link.click();
}

async function importAllData(file) {
    const reader = new FileReader();
    reader.onload = async function (e) {
        try {
            const data = JSON.parse(e.target.result);

            // Töröljük az összes adatot
            const owners = await getAllOwners();
            const cars = await getAllCars();

            for (const car of cars) {
                await deleteCar(car.id);
            }
            for (const owner of owners) {
                await deleteOwner(owner.id);
            }

            // Importáljuk az új adatokat
            if (data.tulajdonosok) {
                for (const owner of data.tulajdonosok) {
                    delete owner.id; // Az autoIncrement új ID-t ad
                    await addOwner(owner);
                }
            }

            if (data.autok) {
                for (const car of data.autok) {
                    delete car.id; // Az autoIncrement új ID-t ad
                    await addCar(car);
                }
            }

            await renderOwners();
            await renderCars();
            alert('Import sikeres!');
        } catch (error) {
            alert('Hiba az import során: ' + error.message);
        }
    };
    reader.readAsText(file);
}

// Form event listeners
document.getElementById('ownerForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const owner = {
        nev: document.getElementById('ownerNev').value,
        cim: document.getElementById('ownerCim').value,
        telefon: document.getElementById('ownerTelefon').value
    };

    await addOwner(owner);
    await renderOwners();
    this.reset();
});

document.getElementById('carForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const car = {
        rendszam: document.getElementById('carRendszam').value,
        tipus: document.getElementById('carTipus').value,
        szin: document.getElementById('carSzin').value,
        kor: parseInt(document.getElementById('carKor').value),
        ar: parseInt(document.getElementById('carAr').value),
        tulajId: parseInt(document.getElementById('carTulaj').value)
    };

    await addCar(car);
    await renderCars();
    this.reset();
});

// Search és filter event listeners
document.getElementById('ownerSearch').addEventListener('input', renderOwners);
document.getElementById('carSearch').addEventListener('input', renderCars);
document.getElementById('carFilter').addEventListener('change', renderCars);

// Export/Import event listeners
document.getElementById('exportAllBtn').addEventListener('click', exportAllData);
document.getElementById('importAllFile').addEventListener('change', function (e) {
    if (e.target.files.length > 0) {
        importAllData(e.target.files[0]);
    }
});

// Inicializálás
initDB().then(() => {
    renderOwners();
    renderCars();
}).catch(error => {
    console.error('Hiba az adatbázis inicializálása során:', error);
    alert('Hiba az adatbázis inicializálása során!');
});
