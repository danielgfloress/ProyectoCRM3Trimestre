const usuariosTableBody = document.getElementById('usuariosTableBody');
const usuarioModal      = document.getElementById('usuarioModal');
const modalTitle        = document.getElementById('modalTitle');
const usuarioForm       = document.getElementById('usuarioForm');
const addUsuarioBtn     = document.getElementById('addUsuarioBtn');
const closeModalBtn     = document.getElementById('closeModalBtn');
const cancelModalBtn    = document.getElementById('cancelModalBtn');

class Usuario {
    constructor(id, nombre, email, password, tipo, estado) {
        this.id       = id;
        this.nombre   = nombre;
        this.email    = email;
        this.password = password;
        this.tipo     = tipo   || 'cliente';
        this.estado   = estado || 'activo';
    }
}

let usuarios = JSON.parse(sessionStorage.getItem('gymUsuarios')) || [
    { id: 1, nombre: 'Daniel García', email: 'dani@admin.gymtonic.com',    password: 'Admin1234', tipo: 'administrador', estado: 'activo'   },
    { id: 2, nombre: 'Carlos Muñoz', email: 'carlos@admin.gymtonic.com',    password: 'Admin1234', tipo: 'administrador', estado: 'activo'   },
    { id: 3, nombre: 'Bruno Quiroa', email: 'bruno@gymtonic.com',    password: 'Admin1234', tipo: 'instructor', estado: 'activo'   },
    { id: 4, nombre: 'Lihuak Romero', email: 'lihu@gymtonic.com',    password: 'Admin1234', tipo: 'instructor', estado: 'activo'   },
    { id: 5, nombre: 'Bermejo Cangrejo', email: 'berme@gymtonic.com',    password: 'Admin1234', tipo: 'instructor', estado: 'activo'   },
    { id: 6, nombre: 'David Bustamante', email: 'david@gymtonic.com',    password: 'Admin1234', tipo: 'instructor', estado: 'activo'   },
    { id: 7, nombre: 'Renzo Papi', email: 'renzo@gymtonic.com',    password: 'Admin1234', tipo: 'instructor', estado: 'activo'   },
    { id: 8, nombre: 'Diego Sniper', email: 'diego@gymtonic.com',    password: 'Admin1234', tipo: 'instructor', estado: 'activo'   },
    { id: 9, nombre: 'Diego Gargoles', email: 'diego@xtart.com',    password: 'Admin1234', tipo: 'cliente', estado: 'activo'   },
    { id: 10, nombre: 'Javier Vega', email: 'javi@xtart.com',    password: 'Admin1234', tipo: 'cliente', estado: 'activo'   },
];

let editingUsuarioId = null;

function getInitials(name) {
    return name.split(' ').map(part => part[0]).join('').toUpperCase().slice(0, 2);
}

function getEstadoBadge(estado) {
    switch (estado) {
        case 'activo':   return { cls: 'badge--success', txt: 'Activo'   };
        case 'inactivo': return { cls: 'badge--danger',  txt: 'Inactivo' };
        default:         return { cls: 'badge--primary', txt: estado      };
    }
}

function getTipoBadge(tipo) {
    switch (tipo) {
        case 'administrador': return { cls: 'badge--danger',  txt: 'Administrador' };
        case 'instructor':    return { cls: 'badge--primary', txt: 'Instructor'    };
        case 'cliente':       return { cls: 'badge--success', txt: 'Cliente'       };
        default:              return { cls: 'badge--primary', txt: tipo            };
    }
}

function syncUsuarioToEntidad(usuario, password) {
    if (usuario.tipo === 'cliente' && usuario.clienteId) {
        let clientes = JSON.parse(sessionStorage.getItem('gymClients')) || [];
        const idx = clientes.findIndex(c => c.id === usuario.clienteId);
        if (idx !== -1) {
            clientes[idx].nombre = usuario.nombre;
            clientes[idx].email  = usuario.email;
            clientes[idx].estado = usuario.estado;
            sessionStorage.setItem('gymClients', JSON.stringify(clientes));
        }
    }

    if (usuario.tipo === 'instructor' && usuario.instructorId) {
        let instructores = JSON.parse(sessionStorage.getItem('gymInstructores')) || [];
        const idx = instructores.findIndex(i => i.id === usuario.instructorId);
        if (idx !== -1) {
            instructores[idx].nombre = usuario.nombre;
            instructores[idx].email  = usuario.email;
            instructores[idx].estado = usuario.estado;
            sessionStorage.setItem('gymInstructores', JSON.stringify(instructores));
        }
    }
}

function deleteEntidadDeUsuario(usuario) {
    if (usuario.tipo === 'cliente' && usuario.clienteId) {
        let clientes = JSON.parse(sessionStorage.getItem('gymClients')) || [];
        clientes = clientes.filter(c => c.id !== usuario.clienteId);
        sessionStorage.setItem('gymClients', JSON.stringify(clientes));
    }
    if (usuario.tipo === 'instructor' && usuario.instructorId) {
        let instructores = JSON.parse(sessionStorage.getItem('gymInstructores')) || [];
        instructores = instructores.filter(i => i.id !== usuario.instructorId);
        sessionStorage.setItem('gymInstructores', JSON.stringify(instructores));
    }
}

function updateCards() {
    const cardTotal        = document.getElementById('cardTotal');
    const cardAdmins       = document.getElementById('cardAdmins');
    const cardInstructores = document.getElementById('cardInstructores');
    const cardActivos      = document.getElementById('cardActivos');
    if (!cardTotal) return;
    cardTotal.textContent        = usuarios.length;
    cardAdmins.textContent       = usuarios.filter(u => u.tipo === 'administrador').length;
    cardInstructores.textContent = usuarios.filter(u => u.tipo === 'instructor').length;
    cardActivos.textContent      = usuarios.filter(u => u.estado === 'activo').length;
}

function loadUsuarios() {
    usuariosTableBody.innerHTML = '';

    usuarios.forEach(function(usuario) {
        const estadoBadge = getEstadoBadge(usuario.estado);
        const tipoBadge   = getTipoBadge(usuario.tipo);

        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="table__td">
                <div class="table__user">
                    <div class="table__avatar">${getInitials(usuario.nombre)}</div>
                    <div>
                        <div class="table__name">${usuario.nombre}</div>
                        <div class="table__email">${usuario.email}</div>
                    </div>
                </div>
            </td>
            <td class="table__td">
                <span class="badge ${tipoBadge.cls}">${tipoBadge.txt}</span>
            </td>
            <td class="table__td">
                <div class="table__actions">
                    <button class="table__btn table__btn--edit"   onclick="editUsuario(${usuario.id})">Editar</button>
                    <button class="table__btn table__btn--delete" onclick="deleteUsuario(${usuario.id})">Eliminar</button>
                </div>
            </td>
        `;
        usuariosTableBody.appendChild(row);
    });

    sessionStorage.setItem('gymUsuarios', JSON.stringify(usuarios));
    updateCards();
}

function showAddUsuarioModal() {
    usuarioForm.reset();
    editingUsuarioId = null;
    document.getElementById('usuarioId').value = '';
    document.getElementById('passwordHelp').style.display = 'none';
    document.getElementById('password').required = true;
    modalTitle.textContent = 'Nuevo Usuario';
    usuarioModal.classList.add('modal--active');
}

function editUsuario(id) {
    const usuario = usuarios.find(u => u.id === id);
    if (usuario) {
        editingUsuarioId = usuario.id;
        document.getElementById('usuarioId').value = usuario.id;
        document.getElementById('nombre').value    = usuario.nombre;
        document.getElementById('email').value     = usuario.email;
        document.getElementById('password').value  = '';
        document.getElementById('tipo').value      = usuario.tipo;
        document.getElementById('estado').value    = usuario.estado;
        document.getElementById('password').required = false;
        document.getElementById('passwordHelp').style.display = 'block';
        modalTitle.textContent = 'Editar Usuario';
        usuarioModal.classList.add('modal--active');
    }
}

function deleteUsuario(id) {
    if (confirm('¿Estás seguro de que quieres eliminar este usuario?')) {
        const usuario = usuarios.find(u => u.id === id);
        if (usuario) deleteEntidadDeUsuario(usuario);
        usuarios = usuarios.filter(u => u.id !== id);
        loadUsuarios();
    }
}

function saveUsuario(e) {
    e.preventDefault();

    const id       = document.getElementById('usuarioId').value;
    const nombre   = document.getElementById('nombre').value.trim();
    const email    = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;
    const tipo     = document.getElementById('tipo').value;
    const estado   = document.getElementById('estado').value;

    if (!nombre || !email) {
        alert('El nombre y el email son obligatorios.');
        return;
    }

    const emailDuplicado = usuarios.some(u =>
        u.email.toLowerCase() === email.toLowerCase() && u.id !== parseInt(id)
    );
    if (emailDuplicado) {
        alert('Ya existe un usuario con ese email.');
        return;
    }

    if (id) {
        const index = usuarios.findIndex(u => u.id === parseInt(id));
        if (index !== -1) {
            usuarios[index] = {
                ...usuarios[index],
                nombre,
                email,
                tipo,
                estado,
                ...(password ? { password } : {})
            };
            syncUsuarioToEntidad(usuarios[index], password || null);
        }
    } else {
        if (!password) {
            alert('La contraseña es obligatoria para nuevos usuarios.');
            return;
        }
        const newId = usuarios.length > 0 ? Math.max(...usuarios.map(u => u.id)) + 1 : 1;
        usuarios.push(new Usuario(newId, nombre, email, password, tipo, estado));
    }

    loadUsuarios();
    usuarioModal.classList.remove('modal--active');
}

const searchInput = document.querySelector('.search__input');
if (searchInput) {
    searchInput.addEventListener('input', function(e) {
        const term = e.target.value.toLowerCase();
        const rows = usuariosTableBody.querySelectorAll('tr');
        rows.forEach(row => {
            row.style.display = row.textContent.toLowerCase().includes(term) ? '' : 'none';
        });
    });
}

addUsuarioBtn.addEventListener('click', showAddUsuarioModal);
closeModalBtn.addEventListener('click',  () => usuarioModal.classList.remove('modal--active'));
cancelModalBtn.addEventListener('click', () => usuarioModal.classList.remove('modal--active'));
usuarioForm.addEventListener('submit', saveUsuario);

window.addEventListener('click', function(e) {
    if (e.target === usuarioModal) {
        usuarioModal.classList.remove('modal--active');
    }
});

(function checkViewport() {
    if (window.innerWidth < 600) {
        const ths = document.querySelectorAll('.table__th');
        [1].forEach(i => { if (ths[i]) ths[i].style.display = 'none'; });
        usuariosTableBody.querySelectorAll('tr').forEach(row => {
            const tds = row.querySelectorAll('td');
            [1].forEach(i => { if (tds[i]) tds[i].style.display = 'none'; });
        });
    }
})();

loadUsuarios();
