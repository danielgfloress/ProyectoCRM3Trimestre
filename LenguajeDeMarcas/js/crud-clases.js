const clasesTableBody     = document.getElementById('clasesTableBody');
const claseModal          = document.getElementById('claseModal');
const claseModalTitle     = document.getElementById('claseModalTitle');
const claseForm           = document.getElementById('claseForm');
const addClaseBtn         = document.getElementById('addClaseBtn');
const closeClaseModalBtn  = document.getElementById('closeClaseModalBtn');
const cancelClaseModalBtn = document.getElementById('cancelClaseModalBtn');
const saveClaseBtn        = document.getElementById('saveClaseBtn');

class Clase {
    constructor(id, nombre, instructorId, horario, capacidadMaxima, nivel) {
        this.id              = id;
        this.nombre          = nombre;
        this.instructorId    = instructorId || null;
        this.horario         = horario;
        this.capacidadMaxima = parseInt(capacidadMaxima, 10);
        this.inscritos       = 0;
        this.nivel           = nivel || 'principiante';
    }
}

let clases = JSON.parse(sessionStorage.getItem('gymClasses')) || [
    Object.assign(new Clase(1, 'Spinning',           1, 'Lun, Mié y Vie — 09:00', 20, 'avanzado'),      { inscritos: 20 }),
    Object.assign(new Clase(2, 'Yoga',               2, 'Mar y Jue — 10:30',      20, 'principiante'),  { inscritos: 15 }),
    Object.assign(new Clase(3, 'Pilates',            2, 'Lun y Mié — 12:00',      15, 'intermedio'),    { inscritos: 12 }),
    Object.assign(new Clase(4, 'Boxeo',              1, 'Mar y Jue — 19:00',      18, 'intermedio'),    { inscritos: 10 }),
    Object.assign(new Clase(5, 'Musculación Guiada', 1, 'Lun a Vie — 18:00',      10, 'avanzado'),      { inscritos: 8  }),
    Object.assign(new Clase(6, 'Zumba',              2, 'Mié y Vie — 20:00',      25, 'principiante'),  { inscritos: 18 }),
];

let editingClaseId = null;

const getNivelBadge = (nivel) => {
    switch (nivel) {
        case 'principiante': return { cls: 'badge--success', txt: 'Principiante' };
        case 'intermedio':   return { cls: 'badge--primary', txt: 'Intermedio'   };
        case 'avanzado':     return { cls: 'badge--danger',  txt: 'Avanzado'     };
        default:             return { cls: 'badge--primary', txt: nivel           };
    }
};

function getClaseIcon(nombre) {
    const n = nombre.toLowerCase();
    if (n.includes('spinning') || n.includes('ciclo')) return 'fa-bicycle';
    if (n.includes('yoga'))                             return 'fa-spa';
    if (n.includes('pilates') || n.includes('funcional')) return 'fa-running';
    if (n.includes('boxeo') || n.includes('box'))       return 'fa-fist-raised';
    if (n.includes('zumba') || n.includes('baile'))     return 'fa-music';
    if (n.includes('musculación') || n.includes('pesas')) return 'fa-dumbbell';
    return 'fa-dumbbell';
}

function getInstructorNombre(instructorId) {
    if (!instructorId) return '—';
    const instructores = JSON.parse(sessionStorage.getItem('gymInstructores')) || [];
    const instructor = instructores.find(i => i.id === instructorId);
    return instructor ? instructor.nombre : '—';
}

function poblarSelectInstructores(selectIdSeleccionado) {
    const select = document.getElementById('instructorSelect');
    if (!select) return;

    const instructores = JSON.parse(sessionStorage.getItem('gymInstructores')) || [];
    select.innerHTML = '<option value="">— Sin instructor —</option>';

    instructores.forEach(function(instructor) {
        if (instructor.estado === 'activo') {
            const opt = document.createElement('option');
            opt.value       = instructor.id;
            opt.textContent = instructor.nombre;
            if (instructor.id === selectIdSeleccionado) opt.selected = true;
            select.appendChild(opt);
        }
    });
}

function loadClases() {
    clasesTableBody.innerHTML = '';

    clases.forEach(function(clase) {
        const badge          = getNivelBadge(clase.nivel);
        const icon           = getClaseIcon(clase.nombre);
        const instructorNombre = getInstructorNombre(clase.instructorId);

        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="table__td">
                <div class="table__user">
                    <div class="table__avatar"><i class="fas ${icon}"></i></div>
                    <div>
                        <div class="table__name">${clase.nombre}</div>
                        <div class="table__email">${instructorNombre}</div>
                    </div>
                </div>
            </td>
            <td class="table__td">${instructorNombre}</td>
            <td class="table__td">${clase.horario}</td>
            <td class="table__td">${clase.inscritos} / ${clase.capacidadMaxima}</td>
            <td class="table__td">
                <span class="badge ${badge.cls}">${badge.txt}</span>
            </td>
            <td class="table__td">
                <div class="table__actions">
                    <button class="table__btn table__btn--edit"   onclick="editClase(${clase.id})">Editar</button>
                    <button class="table__btn table__btn--delete" onclick="deleteClase(${clase.id})">Eliminar</button>
                </div>
            </td>
        `;
        clasesTableBody.appendChild(row);
    });

    sessionStorage.setItem('gymClasses', JSON.stringify(clases));
}

function showAddClaseModal() {
    claseForm.reset();
    editingClaseId = null;
    poblarSelectInstructores(null);
    claseModalTitle.textContent = 'Nueva Clase';
    claseModal.classList.add('modal--active');
}

function editClase(id) {
    const clase = clases.find(c => c.id === id);
    if (clase) {
        editingClaseId = clase.id;
        document.getElementById('nombreClase').value = clase.nombre;
        document.getElementById('horario').value      = clase.horario;
        document.getElementById('capacidad').value    = clase.capacidadMaxima;
        document.getElementById('nivel').value        = clase.nivel;
        poblarSelectInstructores(clase.instructorId);
        claseModalTitle.textContent = 'Editar Clase';
        claseModal.classList.add('modal--active');
    }
}

function deleteClase(id) {
    if (confirm('¿Estás seguro de que quieres eliminar esta clase?')) {
        clases = clases.filter(c => c.id !== id);
        loadClases();
    }
}

function saveClase() {
    const nombre       = document.getElementById('nombreClase').value.trim();
    const instructorId = document.getElementById('instructorSelect').value
                            ? parseInt(document.getElementById('instructorSelect').value)
                            : null;
    const horario      = document.getElementById('horario').value.trim();
    const capacidad    = document.getElementById('capacidad').value;
    const nivel        = document.getElementById('nivel').value;

    if (!nombre || !horario || !capacidad) {
        alert('Por favor completa todos los campos obligatorios.');
        return;
    }

    if (editingClaseId) {
        const index = clases.findIndex(c => c.id === editingClaseId);
        if (index !== -1) {
            clases[index] = {
                ...clases[index],
                nombre,
                instructorId,
                horario,
                capacidadMaxima: parseInt(capacidad, 10),
                nivel
            };
        }
    } else {
        const newId = clases.length > 0 ? Math.max(...clases.map(c => c.id)) + 1 : 1;
        clases.push(new Clase(newId, nombre, instructorId, horario, capacidad, nivel));
    }

    loadClases();
    claseModal.classList.remove('modal--active');
}

const searchInputClases = document.querySelector('.search__input');
if (searchInputClases) {
    searchInputClases.addEventListener('input', function(e) {
        const term = e.target.value.toLowerCase();
        const rows = clasesTableBody.querySelectorAll('tr');
        rows.forEach(row => {
            row.style.display = row.textContent.toLowerCase().includes(term) ? '' : 'none';
        });
    });
}

addClaseBtn.addEventListener('click', showAddClaseModal);
closeClaseModalBtn.addEventListener('click',  () => claseModal.classList.remove('modal--active'));
cancelClaseModalBtn.addEventListener('click', () => claseModal.classList.remove('modal--active'));
saveClaseBtn.addEventListener('click', saveClase);

window.addEventListener('click', function(e) {
    if (e.target === claseModal) {
        claseModal.classList.remove('modal--active');
    }
});

(function checkViewport() {
    if (window.innerWidth < 600) {
        const ths = document.querySelectorAll('.table__th');
        [2].forEach(i => { if (ths[i]) ths[i].style.display = 'none'; });
        clasesTableBody.querySelectorAll('tr').forEach(row => {
            const tds = row.querySelectorAll('td');
            [2].forEach(i => { if (tds[i]) tds[i].style.display = 'none'; });
        });
    }
})();

loadClases();

if (new URLSearchParams(window.location.search).get('action') === 'new') {
    showAddClaseModal();
}
