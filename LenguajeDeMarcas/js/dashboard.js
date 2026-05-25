(function initDashboard() {
    const hoy = new Date().toISOString().slice(0, 10);

    const clientes = JSON.parse(sessionStorage.getItem("gymClients")) || [];
    const agenda = JSON.parse(sessionStorage.getItem("gymAgenda")) || [];
    const usuarios = JSON.parse(sessionStorage.getItem("gymUsuarios")) || [];

    const clientesActivos = clientes.filter((c) => c.estado === "activo").length;
    document.getElementById("cardClientesActivos").textContent = clientesActivos;
    document.getElementById("cardClientesFooter").textContent =
        clientes.length > 0
            ? clientes.length + " clientes en total"
            : "Sin clientes registrados";

    const clasesHoy = agenda.filter((e) => e.fecha === hoy);
    const manana = clasesHoy.filter((e) => parseInt(e.hora) < 14).length;
    const tarde = clasesHoy.filter((e) => parseInt(e.hora) >= 14).length;
    document.getElementById("cardClasesHoy").textContent = clasesHoy.length;
    document.getElementById("cardClasesFooter").textContent =
        clasesHoy.length > 0
            ? manana + " por la mañana · " + tarde + " por la tarde"
            : "Sin clases programadas hoy";

    document.getElementById("cardTotalUsuarios").textContent = usuarios.length;
    document.getElementById("cardUsuariosFooter").textContent =
        usuarios.filter((u) => u.estado === "activo").length + " usuarios activos";

    const instructoresActivos = usuarios.filter(
        (u) => u.tipo === "instructor" && u.estado === "activo",
    ).length;
    document.getElementById("cardInstructores").textContent = instructoresActivos;
    document.getElementById("cardInstructoresFooter").textContent =
        usuarios.filter((u) => u.tipo === "instructor").length +
        " instructores en total";

    const tbody = document.getElementById("clasesHoyBody");
    if (clasesHoy.length === 0) {
        tbody.innerHTML = `
                    <tr>
                        <td class="table__td" colspan="4" style="text-align:center;padding:32px;color:#6b7280;">
                            <i class="fas fa-calendar-check" style="font-size:1.5rem;display:block;margin-bottom:8px;"></i>
                            No hay clases programadas para hoy
                        </td>
                    </tr>`;
    } else {
        const ordenadas = clasesHoy
            .slice()
            .sort((a, b) => a.hora.localeCompare(b.hora));
        ordenadas.forEach(function (evento) {
            
            const row = document.createElement("tr");
            row.innerHTML = `
                        <td class="table__td">${evento.hora}</td>
                        <td class="table__td">${evento.clase}</td>
                        <td class="table__td">${evento.cliente}</td>
                    `;
            tbody.appendChild(row);
        });
    }
})();
