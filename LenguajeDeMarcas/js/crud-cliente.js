const clientsTableBody = document.getElementById('clientsTableBody');
const clientModal      = document.getElementById('clientModal');
const modalTitle       = document.getElementById('modalTitle');
const clientForm       = document.getElementById('clientForm');
const addClientBtn     = document.getElementById('addClientBtn');
const closeModalBtn    = document.getElementById('closeModalBtn');
const cancelModalBtn   = document.getElementById('cancelModalBtn');

class Cliente {
    constructor(id, nombre, telefono, email, membresia, vencimiento, estado, usuarioId) {
        this.id          = id;
        this.nombre      = nombre;
        this.telefono    = telefono;
        this.email       = email;
        this.membresia   = membresia   || 'basica';
        this.vencimiento = vencimiento || '';
        this.estado      = estado      || 'activo';
        this.usuarioId   = usuarioId   || null;
    }
}

let clients = JSON.parse(sessionStorage.getItem('gymClients')) || [
    new Cliente(1, 'Juan Pérez',   '684 943 734', 'juan@xtart.com',   'premium', '2026-12-31', 'activo',    4),
    new Cliente(2, 'María García', '653 363 930', 'maria@xtart.com',  'basica',  '2026-11-15', 'activo',    5),
    new Cliente(3, 'Carlos López', '692 185 429', 'carlos@xtart.com', 'vip',     '2026-10-30', 'congelado', null),
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

function syncClienteToUsuario(cliente, password) {
    let usuarios = JSON.parse(sessionStorage.getItem('gymUsuarios')) || [];

    if (cliente.usuarioId) {
        const idx = usuarios.findIndex(u => u.id === cliente.usuarioId);
        if (idx !== -1) {
            usuarios[idx].nombre = cliente.nombre;
            usuarios[idx].email  = cliente.email;
            usuarios[idx].estado = cliente.estado === 'congelado' ? 'inactivo' : cliente.estado;
            if (password) usuarios[idx].password = password;
        }
    } else {
        const newId = usuarios.length > 0 ? Math.max(...usuarios.map(u => u.id)) + 1 : 1;
        usuarios.push({
            id:        newId,
            nombre:    cliente.nombre,
            email:     cliente.email,
            password:  password,
            tipo:      'cliente',
            estado:    cliente.estado === 'congelado' ? 'inactivo' : cliente.estado,
            clienteId: cliente.id
        });
        cliente.usuarioId = newId;
    }

    sessionStorage.setItem('gymUsuarios', JSON.stringify(usuarios));
}

function deleteUsuarioDeCliente(usuarioId) {
    let usuarios = JSON.parse(sessionStorage.getItem('gymUsuarios')) || [];
    usuarios = usuarios.filter(u => u.id !== usuarioId);
    sessionStorage.setItem('gymUsuarios', JSON.stringify(usuarios));
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
    document.getElementById('clientId').value = '';
    document.getElementById('passwordHelp').style.display = 'none';
    document.getElementById('password').required = true;
    modalTitle.textContent = 'Nuevo Cliente';
    clientModal.classList.add('modal--active');
}

function editClient(id) {
    const client = clients.find(c => c.id === id);
    if (client) {
        editingClientId = client.id;
        document.getElementById('clientId').value           = client.id;
        document.getElementById('nombre').value              = client.nombre;
        document.getElementById('telefono').value            = client.telefono;
        document.getElementById('email').value               = client.email;
        document.getElementById('password').value            = '';
        document.getElementById('membresia').value           = client.membresia;
        document.getElementById('vencimiento').value         = client.vencimiento;
        document.getElementById('estado').value              = client.estado;
        document.getElementById('password').required         = false;
        document.getElementById('passwordHelp').style.display = 'block';
        modalTitle.textContent = 'Editar Cliente';
        clientModal.classList.add('modal--active');
    }
}

function deleteClient(id) {
    if (confirm('¿Estás seguro de que quieres eliminar este cliente?')) {
        const cliente = clients.find(c => c.id === id);
        if (cliente && cliente.usuarioId) {
            deleteUsuarioDeCliente(cliente.usuarioId);
        }
        clients = clients.filter(c => c.id !== id);
        loadClients();
    }
}


function saveClient(e) {
    e.preventDefault();

    const id          = document.getElementById('clientId').value;
    const nombre      = document.getElementById('nombre').value.trim();
    const telefono    = document.getElementById('telefono').value.trim();
    const email       = document.getElementById('email').value.trim();
    const password    = document.getElementById('password').value;
    const membresia   = document.getElementById('membresia').value;
    const vencimiento = document.getElementById('vencimiento').value;
    const estado      = document.getElementById('estado').value;

    if (!nombre || !email) {
        alert('El nombre y el email son obligatorios.');
        return;
    }

    const emailDuplicado = clients.some(c =>
        c.email.toLowerCase() === email.toLowerCase() && c.id !== parseInt(id)
    );
    if (emailDuplicado) {
        alert('Ya existe un cliente con ese email.');
        return;
    }

    if (!id) {
        const usuarios = JSON.parse(sessionStorage.getItem('gymUsuarios')) || [];
        const emailEnUsuarios = usuarios.some(u => u.email.toLowerCase() === email.toLowerCase());
        if (emailEnUsuarios) {
            alert('Ya existe un usuario con ese email en el sistema.');
            return;
        }
        if (!password) {
            alert('La contraseña es obligatoria para nuevos clientes.');
            return;
        }
    }

    if (id) {
        const index = clients.findIndex(c => c.id === parseInt(id));
        if (index !== -1) {
            clients[index] = { ...clients[index], nombre, telefono, email, membresia, vencimiento, estado };
            syncClienteToUsuario(clients[index], password || null);
        }
    } else {
        const newId = clients.length > 0 ? Math.max(...clients.map(c => c.id)) + 1 : 1;
        const nuevo = new Cliente(newId, nombre, telefono, email, membresia, vencimiento, estado, null);
        clients.push(nuevo);
        syncClienteToUsuario(nuevo, password);
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
            row.style.display = row.textContent.toLowerCase().includes(term) ? '' : 'none';
        });
    });
}

addClientBtn.addEventListener('click', showAddClientModal);
closeModalBtn.addEventListener('click',  () => clientModal.classList.remove('modal--active'));
cancelModalBtn.addEventListener('click', () => clientModal.classList.remove('modal--active'));
clientForm.addEventListener('submit', saveClient);

window.addEventListener('click', function(e) {
    if (e.target === clientModal) {
        clientModal.classList.remove('modal--active');
    }
});

(function checkViewport() {
    if (window.innerWidth < 600) {
        const ths = document.querySelectorAll('.table__th');
        [3].forEach(i => { if (ths[i]) ths[i].style.display = 'none'; });
        clientsTableBody.querySelectorAll('tr').forEach(row => {
            const tds = row.querySelectorAll('td');
            [3].forEach(i => { if (tds[i]) tds[i].style.display = 'none'; });
        });
    }
})();

if (new URLSearchParams(window.location.search).get('action') === 'new') {
    showAddClientModal();
}

loadClients();