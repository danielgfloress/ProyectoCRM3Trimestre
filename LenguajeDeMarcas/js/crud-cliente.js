const clientsTableBody = document.getElementById('clientsTableBody');
const clientModal      = document.getElementById('clientModal');
const modalTitle       = document.getElementById('modalTitle');
const clientForm       = document.getElementById('clientForm');
const addClientBtn     = document.getElementById('addClientBtn');
const closeModalBtn    = document.getElementById('closeModalBtn');
const cancelModalBtn   = document.getElementById('cancelModalBtn');

class Cliente {
    constructor(id, nombre, telefono, email, membresia, vencimiento, estado) {
        this.id          = id;
        this.nombre      = nombre;
        this.telefono    = telefono;
        this.email       = email;
        this.membresia   = membresia   || 'basica';
        this.vencimiento = vencimiento || '';
        this.estado      = estado      || 'activo';
    }
}


let clients = JSON.parse(sessionStorage.getItem('gymClients')) || [
    new Cliente(1, 'Juan Pérez',    '684 943 734', 'juan@xtart.com',   'premium', '2026-12-31', 'activo'),
    new Cliente(2, 'María García',  '653 363 930', 'maria@xtart.com',  'basica',  '2026-11-15', 'activo'),
    new Cliente(3, 'Carlos López',  '692 185 429', 'carlos@xtart.com', 'vip',     '2026-10-30', 'congelado'),
];

let editingClientId = null;

function getInitials(name) {
    return name.split(' ').map(part => part[0]).join('').toUpperCase().slice(0, 2);
}

const formatDate = (dateString) => {
    if (!dateString) return '';
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateString + 'T00:00:00').toLocaleDateString('es-ES', options);
};

function getEstadoBadge(estado) {
    switch (estado) {
        case 'activo':    return { cls: 'badge--success', txt: 'Activo'    };
        case 'inactivo':  return { cls: 'badge--danger',  txt: 'Inactivo'  };
        case 'congelado': return { cls: 'badge--warning', txt: 'Congelado' };
        default:          return { cls: 'badge--primary', txt: estado      };
    }
}

function loadClients() {
    clientsTableBody.innerHTML = '';

    clients.forEach(function(client) {
        const badge = getEstadoBadge(client.estado);

        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="table__td">
                <div class="table__user">
                    <div class="table__avatar">${getInitials(client.nombre)}</div>
                    <div>
                        <div class="table__name">${client.nombre}</div>
                        <div class="table__email">${client.email}</div>
                    </div>
                </div>
            </td>
            <td class="table__td">${client.telefono}</td>
            <td class="table__td">
                <span class="badge badge--primary">${client.membresia}</span>
            </td>
            <td class="table__td">${formatDate(client.vencimiento)}</td>
            <td class="table__td">
                <span class="badge ${badge.cls}">${badge.txt}</span>
            </td>
            <td class="table__td">
                <div class="table__actions">
                    <button class="table__btn table__btn--edit"   onclick="editClient(${client.id})">Editar</button>
                    <button class="table__btn table__btn--delete" onclick="deleteClient(${client.id})">Eliminar</button>
                </div>
            </td>
        `;
        clientsTableBody.appendChild(row);
    });

    sessionStorage.setItem('gymClients', JSON.stringify(clients));
}

function showAddClientModal() {
    clientForm.reset();
    editingClientId = null;
    modalTitle.textContent = 'Nuevo Cliente';
    clientModal.classList.add('modal--active');
}

function editClient(id) {
    const client = clients.find(c => c.id === id);
    if (client) {
        editingClientId = client.id;
        document.getElementById('clientId').value   = client.id;
        document.getElementById('nombre').value      = client.nombre;
        document.getElementById('telefono').value    = client.telefono;
        document.getElementById('email').value       = client.email;
        document.getElementById('membresia').value   = client.membresia;
        document.getElementById('vencimiento').value = client.vencimiento;
        document.getElementById('estado').value      = client.estado;
        modalTitle.textContent = 'Editar Cliente';
        clientModal.classList.add('modal--active');
    }
}

function deleteClient(id) {
    if (confirm('¿Estás seguro de que quieres eliminar este cliente?')) {
        clients = clients.filter(client => client.id !== id);
        loadClients();
    }
}


function saveClient(e) {
    e.preventDefault();

    const id = document.getElementById('clientId').value;

    const clientData = {
        nombre:      document.getElementById('nombre').value.trim(),
        telefono:    document.getElementById('telefono').value.trim(),
        email:       document.getElementById('email').value.trim(),
        membresia:   document.getElementById('membresia').value,
        vencimiento: document.getElementById('vencimiento').value,
        estado:      document.getElementById('estado').value,
    };

    if (!clientData.nombre || !clientData.email) {
        alert('El nombre y el email son obligatorios.');
        return;
    }

    if (id) {
        // UPDATE — editar existente
        const index = clients.findIndex(c => c.id === parseInt(id));
        if (index !== -1) clients[index] = { ...clients[index], ...clientData };
    } else {
        // CREATE — nuevo cliente
        const newId = clients.length > 0 ? Math.max(...clients.map(c => c.id)) + 1 : 1;
        clients.push(new Cliente(
            newId,
            clientData.nombre,
            clientData.telefono,
            clientData.email,
            clientData.membresia,
            clientData.vencimiento,
            clientData.estado
        ));
    }

    loadClients();
    clientModal.classList.remove('modal--active');
}

const searchInput = document.querySelector('.search__input');
if (searchInput) {
    searchInput.addEventListener('input', function(e) {
        const term = e.target.value.toLowerCase();
        const rows = clientsTableBody.querySelectorAll('tr');
        rows.forEach(row => {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(term) ? '' : 'none';
        });
    });
}

addClientBtn.addEventListener('click', showAddClientModal);
closeModalBtn.addEventListener('click',  () => clientModal.classList.remove('modal--active'));
cancelModalBtn.addEventListener('click', () => clientModal.classList.remove('modal--active'));
clientForm.addEventListener('submit', saveClient);

// Cerrar modal al hacer click fuera del contenido
window.addEventListener('click', function(e) {
    if (e.target === clientModal) {
        clientModal.classList.remove('modal--active');
    }
});

(function checkViewport() {
    if (window.innerWidth < 600) {
        const ths = document.querySelectorAll('.table__th');
        // En móvil ocultar columna "Vencimiento" (índice 3)
        [3].forEach(i => { if (ths[i]) ths[i].style.display = 'none'; });
        clientsTableBody.querySelectorAll('tr').forEach(row => {
            const tds = row.querySelectorAll('td');
            [3].forEach(i => { if (tds[i]) tds[i].style.display = 'none'; });
        });
    }
})();

loadClients();