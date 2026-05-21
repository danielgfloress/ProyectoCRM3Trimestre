const usuariosTableBody = document.getElementById('usuariosTableBody');
const usuarioModal      = document.getElementById('usuarioModal');
const modalTitle        = document.getElementById('modalTitle');
const usuarioForm       = document.getElementById('usuarioForm');
const addUsuarioBtn     = document.getElementById('addUsuarioBtn');
const closeModalBtn     = document.getElementById('closeModalBtn');
const cancelModalBtn    = document.getElementById('cancelModalBtn');

class Usuario {
    constructor(id, nombre, email, password, tipo) {
        this.id       = id;
        this.nombre   = nombre;
        this.email    = email;
        this.password = password;
        this.tipo     = tipo   || 'cliente';
    }
}

let usuarios = JSON.parse(sessionStorage.getItem('gymUsuarios')) || [
    new Usuario(1, 'Ana Martínez',   'ana@gymtonic.com',    'Admin1234', 'administrador'),
    new Usuario(2, 'Carlos Ruiz',    'carlos@gymtonic.com', 'Instr5678', 'instructor'),
    new Usuario(3, 'Laura Vega',     'laura@gymtonic.com',  'Instr9012', 'instructor'),
    new Usuario(4, 'Juan Pérez',     'juan@xtart.com',      'Juan3456',  'cliente'),
    new Usuario(5, 'María García',   'maria@xtart.com',     'Mari7890',  'cliente'),
];

let editingUsuarioId = null;

function getInitials(name) {
    return name.split(' ').map(part => part[0]).join('').toUpperCase().slice(0, 2);
}

function getTipoBadge(tipo) {
    switch (tipo) {
        case 'administrador': return { cls: 'badge--danger',  txt: 'Administrador' };
        case 'instructor':    return { cls: 'badge--primary', txt: 'Instructor'    };
        case 'cliente':       return { cls: 'badge--success', txt: 'Cliente'       };
        default:              return { cls: 'badge--primary', txt: tipo            };
    }
}

function updateCards() {
    const cardTotal        = document.getElementById('cardTotal');
    const cardAdmins       = document.getElementById('cardAdmins');
    const cardInstructores = document.getElementById('cardInstructores');
    const cardActivos      = document.getElementById('cardActivos');
    if (!cardTotal) return; // por si se reutiliza el JS en otro contexto
    cardTotal.textContent        = usuarios.length;
    cardAdmins.textContent       = usuarios.filter(u => u.tipo === 'administrador').length;
    cardInstructores.textContent = usuarios.filter(u => u.tipo === 'instructor').length;
}

function loadUsuarios() {
    usuariosTableBody.innerHTML = '';

    usuarios.forEach(function(usuario) {
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
        document.getElementById('password').required = false;
        document.getElementById('passwordHelp').style.display = 'block';
        modalTitle.textContent = 'Editar Usuario';
        usuarioModal.classList.add('modal--active');
    }
}

function deleteUsuario(id) {
    if (confirm('¿Estás seguro de que quieres eliminar este usuario?')) {
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
                // Solo actualiza la contraseña si se ha introducido una nueva
                ...(password ? { password } : {})
            };
        }
    } else {
        
        if (!password) {
            alert('La contraseña es obligatoria para nuevos usuarios.');
            return;
        }
        const newId = usuarios.length > 0 ? Math.max(...usuarios.map(u => u.id)) + 1 : 1;
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
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(term) ? '' : 'none';
        });
    });
}

addUsuarioBtn.addEventListener('click', showAddUsuarioModal);
closeModalBtn.addEventListener('click',  () => usuarioModal.classList.remove('modal--active'));
cancelModalBtn.addEventListener('click', () => usuarioModal.classList.remove('modal--active'));
usuarioForm.addEventListener('submit', saveUsuario);

// Cerrar modal al hacer click fuera del contenido
window.addEventListener('click', function(e) {
    if (e.target === usuarioModal) {
        usuarioModal.classList.remove('modal--active');
    }
});

(function checkViewport() {
    if (window.innerWidth < 600) {
        const ths = document.querySelectorAll('.table__th');
        // En móvil ocultar columna "Tipo" (índice 1)
        [1].forEach(i => { if (ths[i]) ths[i].style.display = 'none'; });
        usuariosTableBody.querySelectorAll('tr').forEach(row => {
            const tds = row.querySelectorAll('td');
            [1].forEach(i => { if (tds[i]) tds[i].style.display = 'none'; });
        });
    }
})();

loadUsuarios();
