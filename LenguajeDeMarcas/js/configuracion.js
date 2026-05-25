const saveConfigBtn       = document.getElementById('saveConfigBtn');
const membresiasTableBody = document.getElementById('membresiasTableBody');
const addMembresiaBtn     = document.getElementById('addMembresiaBtn');
const membresiaModal      = document.getElementById('membresiaModal');
const membresiaForm       = document.getElementById('membresiaForm');
const modalTitle          = document.getElementById('modalTitle');
const closeModalBtn       = document.getElementById('closeModalBtn');
const cancelModalBtn      = document.getElementById('cancelModalBtn');
const saveMembresiaBtn    = document.getElementById('saveMembresiaBtn');

class Membresia {
    constructor(id, nombre, precio, duracion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion || '1 mes';
    }
}

const configDefaults = {
    gymNombre:      'GymTonic',
    gymEmail:       'info@gymtonic.com',
    gymTelefono:    '621 003 320',
    gymDireccion:   'Calle Tridente 67, Alicante',
    horarioLVAbre:  '07:00',
    horarioLVCierra:'22:00',
    horarioSabAbre: '09:00',
    horarioSabCierra:'20:00',
    adminNombre:    'Admin',
    adminEmail:     'admin@gymtonic.com',
};

let config = JSON.parse(sessionStorage.getItem('gymConfig')) || { ...configDefaults };


let membresias = JSON.parse(sessionStorage.getItem('gymMembresias')) || [
    new Membresia(1, 'Básica',      '€25', '1 mes'),
    new Membresia(2, 'Premium',     '€40', '1 mes'),
    new Membresia(3, 'VIP',         '€65', '1 mes'),
    new Membresia(4, 'Estudiante',  '€20', '1 mes'),
];

let editingMembresiaId = null;


function getMembresiasBadgeClass(nombre) {
    const n = nombre.toLowerCase();
    if (n.includes('básica') || n.includes('basica')) return 'badge--primary';
    if (n.includes('premium'))   return 'badge--warning';
    if (n.includes('vip'))       return 'badge--danger';
    if (n.includes('estudiante')) return 'badge--success';
    return 'badge--primary';
}

const getField = (id) => document.getElementById(id);

function loadConfig() {
    getField('gymNombre').value        = config.gymNombre;
    getField('gymEmail').value         = config.gymEmail;
    getField('gymTelefono').value      = config.gymTelefono;
    getField('gymDireccion').value     = config.gymDireccion;
    getField('horarioLVAbre').value    = config.horarioLVAbre;
    getField('horarioLVCierra').value  = config.horarioLVCierra;
    getField('horarioSabAbre').value   = config.horarioSabAbre;
    getField('horarioSabCierra').value = config.horarioSabCierra;
    getField('adminNombre').value      = config.adminNombre;
    getField('adminEmail').value       = config.adminEmail;

    const usernameEl = document.querySelector('.header__username');
    if (usernameEl) usernameEl.textContent = config.adminNombre;

    const sidebarTitle = document.querySelector('.sidebar__title');
    if (sidebarTitle) {
        sidebarTitle.innerHTML = config.gymNombre.replace('CRM', '<span class="sidebar__title--highlight">CRM</span>');
    }
}

function saveConfig(e) {
    if (e) e.preventDefault();

    const adminPass        = getField('adminPass').value;
    const adminPassConfirm = getField('adminPassConfirm').value;

    if (adminPass && adminPass !== adminPassConfirm) {
        alert('Las contraseñas no coinciden.');
        return;
    }

    config = {
        gymNombre:        getField('gymNombre').value,
        gymEmail:         getField('gymEmail').value,
        gymTelefono:      getField('gymTelefono').value,
        gymDireccion:     getField('gymDireccion').value,
        horarioLVAbre:    getField('horarioLVAbre').value,
        horarioLVCierra:  getField('horarioLVCierra').value,
        horarioSabAbre:   getField('horarioSabAbre').value,
        horarioSabCierra: getField('horarioSabCierra').value,
        adminNombre:      getField('adminNombre').value,
        adminEmail:       getField('adminEmail').value,
    };

    sessionStorage.setItem('gymConfig', JSON.stringify(config));
    loadConfig();
    showToast('Configuración guardada correctamente.');
}

function showToast(msg) {
    let toast = document.getElementById('configToast');
    if (!toast) {
        toast = document.createElement('div');
        toast.id = 'configToast';
        toast.style.cssText = [
            'position:fixed', 'bottom:24px', 'right:24px',
            'background:var(--color-success, #22c55e)', 'color:#fff',
            'padding:12px 20px', 'border-radius:8px',
            'font-size:14px', 'font-weight:500',
            'box-shadow:0 4px 12px rgba(0,0,0,.15)',
            'z-index:9999', 'transition:opacity .3s'
        ].join(';');
        document.body.appendChild(toast);
    }
    toast.textContent = msg;
    toast.style.opacity = '1';
    window.setTimeout(function() {
        toast.style.opacity = '0';
    }, 2800);
}

function loadMembresias() {
    membresiasTableBody.innerHTML = '';

    membresias.forEach(function(m) {
        const badgeCls = getMembresiasBadgeClass(m.nombre);
        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="table__td"><span class="badge ${badgeCls}">${m.nombre}</span></td>
            <td class="table__td">${m.precio}</td>
            <td class="table__td">${m.duracion}</td>
            <td class="table__td">
                <div class="table__actions">
                    <button type="button" class="table__btn table__btn--edit"   onclick="editMembresia(${m.id})">Editar</button>
                    <button type="button" class="table__btn table__btn--delete" onclick="deleteMembresia(${m.id})">Eliminar</button>
                </div>
            </td>
        `;
        membresiasTableBody.appendChild(row);
    });

    sessionStorage.setItem('gymMembresias', JSON.stringify(membresias));
}

function showAddMembresiaModal() {
    membresiaForm.reset();
    editingMembresiaId = null;
    modalTitle.textContent = 'Nuevo tipo de membresia';
    membresiaModal.classList.add('modal--active');
}

function editMembresia(id) {
    const membresia = membresias.find(m => m.id === id);
    if (membresia) {
        editingMembresiaId = membresia.id;
        document.getElementById('membresiaId').value = membresia.id;
        document.getElementById('nombre').value = membresia.nombre;
        document.getElementById('precio').value = membresia.precio;
        document.getElementById('duracion').value = membresia.duracion;
        modalTitle.textContent = 'Editar Membresia';
        membresiaModal.classList.add('modal--active');
    }
}

function deleteMembresia(id) {
    if (confirm('¿Eliminar este tipo de membresía?')) {
        membresias = membresias.filter(m => m.id !== id);
        loadMembresias();
    }
}

function saveMembresia(e) {
    const id = document.getElementById('membresiaId').value;

    const membresiaData = {
        nombre:   document.getElementById('nombre').value.trim(),
        precio:   document.getElementById('precio').value.trim(),
        duracion: document.getElementById('duracion').value.trim(),
    };

    if (!membresiaData.nombre || !membresiaData.precio || !membresiaData.duracion) {
        alert('El nombre, el precio y la duración son obligatorios.');
        return;
    }

    if (id) {

        const index = membresias.findIndex(m => m.id === parseInt(id));
        if (index !== -1) membresias[index] = { ...membresias[index], ...membresiaData };
    } else {

        const newId = membresias.length > 0 ? Math.max(...membresias.map(m => m.id)) + 1 : 1;
        membresias.push(new Membresia(
            newId,
            membresiaData.nombre,
            membresiaData.precio,
            membresiaData.duracion,
        ));
    }

    loadMembresias();
    membresiaModal.classList.remove('modal--active');
}

(function checkViewport() {
    if (window.innerWidth < 480) {
        const configSections = document.querySelectorAll('.config-section');
        configSections.forEach(section => {
            section.style.marginBottom = '12px';
        });
    }
})();

window.editMembresia   = editMembresia;
window.deleteMembresia = deleteMembresia;

saveConfigBtn.addEventListener('click', saveConfig);
addMembresiaBtn.addEventListener('click', showAddMembresiaModal);
closeModalBtn.addEventListener('click',  () => membresiaModal.classList.remove('modal--active'));
cancelModalBtn.addEventListener('click', () => membresiaModal.classList.remove('modal--active'));
saveMembresiaBtn.addEventListener('click', saveMembresia);
membresiaForm.addEventListener('submit', function(e) { e.preventDefault(); });

window.addEventListener('click', function(e) {
    if (e.target === membresiaModal) {
        membresiaModal.classList.remove('modal--active');
    }
});

loadConfig();
loadMembresias();