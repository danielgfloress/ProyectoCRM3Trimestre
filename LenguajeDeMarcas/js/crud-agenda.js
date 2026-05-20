const agendaTableBody = document.getElementById('agendaTableBody');
const eventoModal = document.getElementById('eventoModal');
const eventoForm = document.getElementById('eventoForm');
const addEventoBtn = document.getElementById('addEventoBtn');
const closeEventoModalBtn = document.getElementById('closeEventoModalBtn');
const cancelEventoModalBtn = document.getElementById('cancelEventoModalBtn');
const saveEventoBtn = document.getElementById('saveEventoBtn');

class Evento {
    constructor(id, clase, fecha, hora, cliente, estado) {
        this.id = id;
        this.clase = clase;
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente || '';
    }
}

let eventos = JSON.parse(sessionStorage.getItem('gymAgenda')) || [

    // Semana 1
    new Evento(1, 'Spinning', '2026-05-08', '09:00', 'Juan Pérez'),
    new Evento(2, 'Yoga', '2026-05-08', '10:30', 'María García'),
    new Evento(3, 'Zumba', '2026-05-09', '20:00', 'Laura Ruiz'),
    new Evento(4, 'Pilates', '2026-05-10', '12:00', 'Carlos López'),
    new Evento(5, 'Musculación Guiada', '2026-05-10', '18:00', 'Javier Soto'),

    new Evento(6, 'Boxeo', '2026-05-11', '20:00', 'Sergio Díaz'),
    new Evento(7, 'Spinning', '2026-05-11', '09:00', 'Juan Pérez'),

    new Evento(8, 'Boxeo', '2026-05-12', '20:00', 'Sergio Díaz'),
    new Evento(9, 'Musculación Guiada', '2026-05-12', '18:00', 'Javier Soto'),

    new Evento(10, 'Crossfit', '2026-05-13', '18:30', 'Marta León'),
    new Evento(11, 'Boxeo', '2026-05-13', '20:00', 'Sergio Díaz'),

    new Evento(12, 'Yoga Avanzado', '2026-05-14', '17:00', 'Lucía Torres'),
    new Evento(13, 'Boxeo', '2026-05-14', '20:00', 'Sergio Díaz'),

    new Evento(14, 'HIIT', '2026-05-15', '19:00', 'David Romero'),
    new Evento(15, 'Boxeo', '2026-05-15', '20:00', 'Sergio Díaz'),

    new Evento(16, 'Zumba', '2026-05-16', '11:00', 'Laura Ruiz'),
    new Evento(17, 'Pilates', '2026-05-17', '10:00', 'Carlos López'),

    // Semana 2
    new Evento(18, 'Spinning', '2026-05-18', '09:00', 'Juan Pérez'),
    new Evento(19, 'Boxeo', '2026-05-18', '20:00', 'Sergio Díaz'),

    new Evento(20, 'Body Pump', '2026-05-19', '18:00', 'Andrea Gil'),
    new Evento(21, 'Boxeo', '2026-05-19', '20:00', 'Sergio Díaz'),

    new Evento(22, 'Yoga', '2026-05-20', '10:30', 'María García'),
    new Evento(23, 'Boxeo', '2026-05-20', '20:00', 'Sergio Díaz'),

    new Evento(24, 'TRX', '2026-05-21', '17:30', 'Raúl Medina'),
    new Evento(25, 'Boxeo', '2026-05-21', '20:00', 'Sergio Díaz'),

    new Evento(26, 'Funcional', '2026-05-22', '19:00', 'Elena Castro'),
    new Evento(27, 'Boxeo', '2026-05-22', '20:00', 'Sergio Díaz'),

    new Evento(28, 'Zumba', '2026-05-23', '12:00', 'Laura Ruiz'),
    new Evento(29, 'Estiramientos', '2026-05-24', '11:00', 'Patricia Vega'),

    // Semana 3
    new Evento(30, 'Spinning', '2026-05-25', '09:00', 'Juan Pérez'),
    new Evento(31, 'Boxeo', '2026-05-25', '20:00', 'Sergio Díaz'),

    new Evento(32, 'Pilates', '2026-05-26', '18:00', 'Carlos López'),
    new Evento(33, 'Boxeo', '2026-05-26', '20:00', 'Sergio Díaz'),

    new Evento(34, 'Crossfit', '2026-05-27', '19:00', 'Marta León'),
    new Evento(35, 'Boxeo', '2026-05-27', '20:00', 'Sergio Díaz'),

    new Evento(36, 'Yoga Relajante', '2026-05-28', '17:00', 'Lucía Torres'),
    new Evento(37, 'Boxeo', '2026-05-28', '20:00', 'Sergio Díaz'),

    new Evento(38, 'HIIT', '2026-05-29', '18:30', 'David Romero'),
    new Evento(39, 'Boxeo', '2026-05-29', '20:00', 'Sergio Díaz'),

    new Evento(40, 'Zumba', '2026-05-30', '11:00', 'Laura Ruiz'),
    new Evento(41, 'Musculación Guiada', '2026-05-31', '12:00', 'Javier Soto'),

    // Semana 4
    new Evento(42, 'Spinning', '2026-06-01', '09:00', 'Juan Pérez'),
    new Evento(43, 'Boxeo', '2026-06-01', '20:00', 'Sergio Díaz'),

    new Evento(44, 'TRX', '2026-06-02', '18:00', 'Raúl Medina'),
    new Evento(45, 'Boxeo', '2026-06-02', '20:00', 'Sergio Díaz'),

    new Evento(46, 'Body Pump', '2026-06-03', '19:00', 'Andrea Gil'),
    new Evento(47, 'Boxeo', '2026-06-03', '20:00', 'Sergio Díaz'),

    new Evento(48, 'Yoga', '2026-06-04', '10:30', 'María García'),
    new Evento(49, 'Boxeo', '2026-06-04', '20:00', 'Sergio Díaz'),

    new Evento(50, 'Funcional', '2026-06-05', '18:30', 'Elena Castro'),
    new Evento(51, 'Boxeo', '2026-06-05', '20:00', 'Sergio Díaz'),

    new Evento(52, 'Pilates', '2026-06-06', '11:00', 'Carlos López'),
    new Evento(53, 'Zumba', '2026-06-07', '12:00', 'Laura Ruiz'),

    // Semana 5
    new Evento(54, 'Spinning', '2026-06-08', '09:00', 'Juan Pérez'),
    new Evento(55, 'Boxeo', '2026-06-08', '20:00', 'Sergio Díaz'),

    new Evento(56, 'Pilates', '2026-06-09', '18:00', 'Carlos López'),
    new Evento(57, 'Boxeo', '2026-06-09', '20:00', 'Sergio Díaz'),

    new Evento(58, 'Crossfit', '2026-06-10', '19:00', 'Marta León'),
    new Evento(59, 'Boxeo', '2026-06-10', '20:00', 'Sergio Díaz'),

    new Evento(60, 'Yoga Relajante', '2026-06-11', '17:00', 'Lucía Torres'),
    new Evento(61, 'Boxeo', '2026-06-11', '20:00', 'Sergio Díaz'),

    new Evento(62, 'HIIT', '2026-06-12', '18:30', 'David Romero'),
    new Evento(63, 'Boxeo', '2026-06-12', '20:00', 'Sergio Díaz'),

    new Evento(64, 'Zumba', '2026-06-13', '11:00', 'Laura Ruiz'),
    new Evento(65, 'Musculación Guiada', '2026-06-14', '12:00', 'Javier Soto'),
];

let editingEventoId = null;

function getInitials(name) {
    if (!name) return '?';
    return name.split(' ').map(part => part[0]).join('').toUpperCase().slice(0, 2);
}

const formatDate = (dateString) => {
    if (!dateString) return '';
    const options = { year: 'numeric', month: 'short', day: '2-digit' };
    return new Date(dateString + 'T00:00:00').toLocaleDateString('es-ES', options);
};

function loadEventos() {
    agendaTableBody.innerHTML = '';

    eventos.forEach(function(evento) {
        const initials = getInitials(evento.cliente);
        const emailFake = evento.cliente
            ? evento.cliente.toLowerCase().replace(' ', '') + '@xtart.com'
            : '—';

        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="table__td">
                <div class="table__user">
                    <div class="table__avatar">${initials}</div>
                    <div>
                        <div class="table__name">${evento.cliente || '— Sin cliente —'}</div>
                        <div class="table__email">${evento.cliente ? emailFake : ''}</div>
                    </div>
                </div>
            </td>
            <td class="table__td">${evento.clase}</td>
            <td class="table__td">${formatDate(evento.fecha)}</td>
            <td class="table__td">${evento.hora}</td>
            <td class="table__td">
                <div class="table__actions">
                    <button class="table__btn table__btn--edit"   onclick="editEvento(${evento.id})">Editar</button>
                    <button class="table__btn table__btn--delete" onclick="deleteEvento(${evento.id})">Cancelar</button>
                </div>
            </td>
        `;
        agendaTableBody.appendChild(row);
    });

    sessionStorage.setItem('gymAgenda', JSON.stringify(eventos));
    renderCalendario();
}

function showAddEventoModal() {
    eventoForm.reset();
    editingEventoId = null;
    document.querySelector('#eventoModal .modal__title').textContent = 'Nuevo Evento';
    eventoModal.classList.add('modal--active');
}

function editEvento(id) {
    const evento = eventos.find(e => e.id === id);
    if (evento) {
        editingEventoId = evento.id;
        document.getElementById('eventoClase').value   = evento.clase;
        document.getElementById('eventoFecha').value   = evento.fecha;
        document.getElementById('eventoHora').value    = evento.hora;
        document.getElementById('eventoCliente').value = evento.cliente;
        document.querySelector('#eventoModal .modal__title').textContent = 'Editar Evento';
        eventoModal.classList.add('modal--active');
    }
}

function deleteEvento(id) {
    if (confirm('¿Cancelar este evento de la agenda?')) {
        eventos = eventos.filter(e => e.id !== id);
        loadEventos();
    }
}

function saveEvento() {
    const clase   = document.getElementById('eventoClase').value;
    const fecha   = document.getElementById('eventoFecha').value;
    const hora    = document.getElementById('eventoHora').value;
    const cliente = document.getElementById('eventoCliente').value;

    if (!fecha || !hora) {
        alert('Por favor completa la fecha y la hora.');
        return;
    }

    if (editingEventoId) {
        const index = eventos.findIndex(e => e.id === editingEventoId);
        if (index !== -1) {
            eventos[index] = { ...eventos[index], clase, fecha, hora, cliente };
        }
    } else {
        const newId = eventos.length > 0 ? Math.max(...eventos.map(e => e.id)) + 1 : 1;
        eventos.push(new Evento(newId, clase, fecha, hora, cliente, 'reservado'));
    }

    loadEventos();
    eventoModal.classList.remove('modal--active');
}

const searchInput = document.querySelector('.search__input');
if (searchInput) {
    searchInput.addEventListener('input', function(e) {
        const term = e.target.value.toLowerCase();
        const rows = agendaTableBody.querySelectorAll('tr');
        rows.forEach(row => {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(term) ? '' : 'none';
        });
    });
}

function getColorClass(clase) {
    const c = clase.toLowerCase();
    if (c.includes('yoga') || c.includes('pilates') || c.includes('zumba')) return 'agenda-event--green';
    if (c.includes('spinning'))   return '';
    if (c.includes('musculación') || c.includes('musculacion')) return 'agenda-event--blue';
    if (c.includes('boxeo'))      return 'agenda-event--red';
    return '';
}

function renderCalendario() {
    const dias = document.querySelectorAll('.agenda-day');
    if (!dias.length) return;

    const hoy = new Date();
    const lunes = new Date(hoy);
    lunes.setDate(hoy.getDate() - ((hoy.getDay() + 6) % 7));

    dias.forEach((diaEl, i) => {
        const fecha = new Date(lunes);
        fecha.setDate(lunes.getDate() + i);

        const yyyy = fecha.getFullYear();
        const mm   = String(fecha.getMonth() + 1).padStart(2, '0');
        const dd   = String(fecha.getDate()).padStart(2, '0');
        const fechaStr = `${yyyy}-${mm}-${dd}`;

        const body = diaEl.querySelector('.agenda-day__body');
        if (!body) return;
        body.innerHTML = '';

        const del_dia = eventos
            .filter(e => e.fecha === fechaStr && e.estado !== 'cancelado')
            .sort((a, b) => a.hora.localeCompare(b.hora));

        del_dia.forEach(e => {
            const colorCls = getColorClass(e.clase);
            const div = document.createElement('div');
            div.className = 'agenda-event' + (colorCls ? ' ' + colorCls : '');
            div.innerHTML = `
                <div class="agenda-event__time">${e.hora}</div>
                <div class="agenda-event__name">${e.clase}</div>
            `;
            body.appendChild(div);
        });
    });
}

(function resaltarHoy() {
    const hoy = new Date();
    const diaSemana = hoy.getDay();
    const dias = document.querySelectorAll('.agenda-day');
    
    const mapJS = [6, 0, 1, 2, 3, 4, 5]; 
    const idx = mapJS[diaSemana];
    if (dias[idx]) {
        dias[idx].classList.add('agenda-day--today');
    }
    
    const lunes = new Date(hoy);
    lunes.setDate(hoy.getDate() - ((hoy.getDay() + 6) % 7));
    dias.forEach((dia, i) => {
        const fecha = new Date(lunes);
        fecha.setDate(lunes.getDate() + i);
        const numEl = dia.querySelector('.agenda-day__num');
        if (numEl) numEl.textContent = fecha.getDate();
    });
})();

addEventoBtn.addEventListener('click', showAddEventoModal);
closeEventoModalBtn.addEventListener('click', () => eventoModal.classList.remove('modal--active'));
cancelEventoModalBtn.addEventListener('click', () => eventoModal.classList.remove('modal--active'));
saveEventoBtn.addEventListener('click', saveEvento);

window.addEventListener('click', function(e) {
    if (e.target === eventoModal) {
        eventoModal.classList.remove('modal--active');
    }
});

if (new URLSearchParams(window.location.search).get('action') === 'new') {
    showAddEventoModal();
}

loadEventos();