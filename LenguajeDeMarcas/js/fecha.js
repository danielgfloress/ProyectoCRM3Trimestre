const hoy = new Date();

const fecha = hoy.toLocaleDateString('es-ES', {
    day: 'numeric',
    month: 'long',
    year: 'numeric'
});

document.getElementById('fecha').textContent = fecha;