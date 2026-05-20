const instructoresTableBody = document.getElementById('instructoresTableBody');
const instructorModal       = document.getElementById('instructorModal');
const modalTitle            = document.getElementById('modalTitle');
const instructorForm        = document.getElementById('instructorForm');
const addInstructorBtn      = document.getElementById('addInstructorBtn');
const closeModalBtn         = document.getElementById('closeModalBtn');
const cancelModalBtn        = document.getElementById('cancelModalBtn');

class Instructor {
    constructor(id, nombre, email, estado, usuarioId) {
        this.id        = id;
        this.nombre    = nombre;
        this.email     = email;
        this.estado    = estado    || 'activo';
        this.usuarioId = usuarioId || null;
    }
}

let instructores = JSON.parse(sessionStorage.getItem('gymInstructores')) || [
    new Instructor(1, 'Carlos Ruiz',  'carlos@gymtonic.com', 'activo',   2),
    new Instructor(2, 'Laura Vega',   'laura@gymtonic.com',  'activo',   3),
];

function getInitials(name) {
    return name.split(' ').map(p => p[0]).join('').toUpperCase().slice(0, 2);
}

function getEstadoBadge(estado) {
    switch (estado) {
        case 'activo':   return { cls: 'badge--success', txt: 'Activo'   };
        case 'inactivo': return { cls: 'badge--danger',  txt: 'Inactivo' };
        default:         return { cls: 'badge--primary', txt: estado      };
    }
}

function syncInstructorToUsuario(instructor, password) {
    let usuarios = JSON.parse(sessionStorage.getItem('gymUsuarios')) || [];

    if (instructor.usuarioId) {

        const idx = usuarios.findIndex(u => u.id === instructor.usuarioId);
        if (idx !== -1) {
            usuarios[idx].nombre = instructor.nombre;
            usuarios[idx].email  = instructor.email;
            usuarios[idx].estado = instructor.estado;
            if (password) usuarios[idx].password = password;
        }
    } else {

        const newId = usuarios.length > 0 ? Math.max(...usuarios.map(u => u.id)) + 1 : 1;
        usuarios.push({
            id:        newId,
            nombre:    instructor.nombre,
            email:     instructor.email,
            password:  password,
            tipo:      'instructor',
            estado:    instructor.estado,
            instructorId: instructor.id
        });
        instructor.usuarioId = newId;
    }

    sessionStorage.setItem('gymUsuarios', JSON.stringify(usuarios));
}

function deleteUsuarioDeInstructor(usuarioId) {
    let usuarios = JSON.parse(sessionStorage.getItem('gymUsuarios')) || [];
    usuarios = usuarios.filter(u => u.id !== usuarioId);
    sessionStorage.setItem('gymUsuarios', JSON.stringify(usuarios));
}

function loadInstructores() {
    instructoresTableBody.innerHTML = '';

    instructores.forEach(function(instructor) {
        const badge = getEstadoBadge(instructor.estado);

        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="table__td">
                <div class="table__user">
                    <div class="table__avatar">${getInitials(instructor.nombre)}</div>
                    <div>
                        <div class="table__name">${instructor.nombre}</div>
                        <div class="table__email">${instructor.email}</div>
                    </div>
                </div>
            </td>
            <td class="table__td">
                <span class="badge ${badge.cls}">${badge.txt}</span>
            </td>
            <td class="table__td">
                <div class="table__actions">
                    <button class="table__btn table__btn--edit"   onclick="editInstructor(${instructor.id})">Editar</button>
                    <button class="table__btn table__btn--delete" onclick="deleteInstructor(${instructor.id})">Eliminar</button>
                </div>
            </td>
        `;
        instructoresTableBody.appendChild(row);
    });

    sessionStorage.setItem('gymInstructores', JSON.stringify(instructores));
    updateCards();
}

function updateCards() {
    const cardTotal    = document.getElementById('cardTotal');
    const cardActivos  = document.getElementById('cardActivos');
    if (!cardTotal) return;
    cardTotal.textContent   = instructores.length;
    cardActivos.textContent = instructores.filter(i => i.estado === 'activo').length;
}

function showAddInstructorModal() {
    instructorForm.reset();
    document.getElementById('instructorId').value = '';
    document.getElementById('passwordHelp').style.display = 'none';
    document.getElementById('password').required = true;
    modalTitle.textContent = 'Nuevo Instructor';
    instructorModal.classList.add('modal--active');
}

function editInstructor(id) {
    const instructor = instructores.find(i => i.id === id);
    if (instructor) {
        document.getElementById('instructorId').value = instructor.id;
        document.getElementById('nombre').value        = instructor.nombre;
        document.getElementById('email').value         = instructor.email;
        document.getElementById('password').value      = '';
        document.getElementById('estado').value        = instructor.estado;
        document.getElementById('password').required   = false;
        document.getElementById('passwordHelp').style.display = 'block';
        modalTitle.textContent = 'Editar Instructor';
        instructorModal.classList.add('modal--active');
    }
}

function deleteInstructor(id) {
    if (confirm('¿Estás seguro de que quieres eliminar este instructor?')) {
        const instructor = instructores.find(i => i.id === id);
        if (instructor && instructor.usuarioId) {
            deleteUsuarioDeInstructor(instructor.usuarioId);
        }
        instructores = instructores.filter(i => i.id !== id);
        loadInstructores();
    }
}

function saveInstructor(e) {
    e.preventDefault();

    const id       = document.getElementById('instructorId').value;
    const nombre   = document.getElementById('nombre').value.trim();
    const email    = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;
    const estado   = document.getElementById('estado').value;

    if (!nombre || !email) {
        alert('El nombre y el email son obligatorios.');
        return;
    }

    const emailDuplicado = instructores.some(i =>
        i.email.toLowerCase() === email.toLowerCase() && i.id !== parseInt(id)
    );
    if (emailDuplicado) {
        alert('Ya existe un instructor con ese email.');
        return;
    }

    const usuarios = JSON.parse(sessionStorage.getItem('gymUsuarios')) || [];
    const emailEnUsuarios = usuarios.some(u =>
        u.email.toLowerCase() === email.toLowerCase() && u.id !== (parseInt(id) ? instructores.find(i => i.id === parseInt(id))?.usuarioId : null)
    );
    if (emailEnUsuarios && !id) {
        alert('Ya existe un usuario con ese email en el sistema.');
        return;
    }

    if (id) {

        const index = instructores.findIndex(i => i.id === parseInt(id));
        if (index !== -1) {
            instructores[index] = { ...instructores[index], nombre, email, estado };
            syncInstructorToUsuario(instructores[index], password || null);
        }
    } else {

        if (!password) {
            alert('La contraseña es obligatoria para nuevos instructores.');
            return;
        }
        const newId = instructores.length > 0 ? Math.max(...instructores.map(i => i.id)) + 1 : 1;
        const nuevo = new Instructor(newId, nombre, email, estado, null);
        instructores.push(nuevo);
        syncInstructorToUsuario(nuevo, password);
    }

    loadInstructores();
    instructorModal.classList.remove('modal--active');
}

const searchInput = document.querySelector('.search__input');
if (searchInput) {
    searchInput.addEventListener('input', function(e) {
        const term = e.target.value.toLowerCase();
        const rows = instructoresTableBody.querySelectorAll('tr');
        rows.forEach(row => {
            row.style.display = row.textContent.toLowerCase().includes(term) ? '' : 'none';
        });
    });
}

addInstructorBtn.addEventListener('click', showAddInstructorModal);
closeModalBtn.addEventListener('click',   () => instructorModal.classList.remove('modal--active'));
cancelModalBtn.addEventListener('click',  () => instructorModal.classList.remove('modal--active'));
instructorForm.addEventListener('submit', saveInstructor);

window.addEventListener('click', function(e) {
    if (e.target === instructorModal) {
        instructorModal.classList.remove('modal--active');
    }
});

loadInstructores();

if (new URLSearchParams(window.location.search).get('action') === 'new') {
    showAddInstructorModal();
}