function abrirModalCrear() {
    document.getElementById('modalCrear').style.display = 'block';
}

function abrirModalEditar(id, dni, nombre, rol) {
    document.getElementById('editarId').value = id;
    document.getElementById('editarDni').value = dni;
    document.getElementById('editarNombre').value = nombre;
    document.getElementById('editarRol').value = rol;
    document.getElementById('modalEditar').style.display = 'block';
}

function abrirModalEliminar(id) {
    window.usuarioAEliminar = id;
    document.getElementById('modalEliminar').style.display = 'block';
}

function cerrarModal(idModal) {
    document.getElementById(idModal).style.display = 'none';
}

function limpiarCamposCrear() {
    document.getElementById('dniCrear').value = '';
    document.getElementById('nombreCrear').value = '';
    document.getElementById('contrasenaCrear').value = '';
    document.getElementById('rolCrear').value = 'ADMIN';
}

function cargarUsuarios() {
    const tablaUsuarios = document.getElementById('tablaUsuarios');
    const tablaAsignaciones = document.getElementById('tablaAsignaciones');

    tablaUsuarios.innerHTML = '';
    tablaAsignaciones.innerHTML = '';

    fetch('/api/usuarios')
        .then(response => response.json())
        .then(usuarios => {
            usuarios.forEach(usuario => {
                agregarFilaUsuario(usuario);
                if (usuario.rol === 'PROFESOR' || usuario.rol === 'ALUMNO') {
                    agregarFilaAsignacion(usuario);
                }
            });
        })
        .catch(error => console.error('Error al cargar usuarios:', error));
}

function crearUsuario() {
    const dni = document.getElementById('dniCrear').value;
    const nombre = document.getElementById('nombreCrear').value;
    const contrasena = document.getElementById('contrasenaCrear').value;
    const rol = document.getElementById('rolCrear').value;

    const usuario = {
        dni,
        nombre,
        contrasena,
        rol,
        activo: true
    };

    fetch('/api/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuario)
    })
    .then(response => {
        if (!response.ok) throw new Error('Error al guardar usuario');
        return response.json();
    })
    .then(data => {
        cerrarModal('modalCrear');
        limpiarCamposCrear();
        cargarUsuarios();
    })
    .catch(error => {
        alert("Error al crear usuario: " + error.message);
        console.error(error);
    });
}

function agregarFilaUsuario(usuario) {
    const tabla = document.getElementById('tablaUsuarios');
    const fila = document.createElement('tr');
    fila.innerHTML = `
        <td>${usuario.dni}</td>
        <td>${usuario.nombre}</td>
        <td>${usuario.rol}</td>
        <td>
            <button class="btn" onclick="abrirModalEditar(${usuario.id}, '${usuario.dni}', '${usuario.nombre}', '${usuario.rol}')">Editar</button>
            <button class="btn" onclick="abrirModalEliminar(${usuario.id})">Eliminar</button>
        </td>
    `;
    tabla.appendChild(fila);
}

function actualizarUsuario() {
    const id = document.getElementById('editarId').value;
    const dni = document.getElementById('editarDni').value;
    const nombre = document.getElementById('editarNombre').value;
    const rol = document.getElementById('editarRol').value;

    const usuario = { dni, nombre, rol };

    fetch(`/api/usuarios/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuario)
    })
    .then(response => {
        if (!response.ok) throw new Error('Error al actualizar usuario');
        return response.json();
    })
    .then(() => {
        location.reload();
    })
    .catch(error => {
        alert("Error al actualizar usuario: " + error.message);
        console.error(error);
    });
}

function confirmarEliminar() {
    fetch(`/api/usuarios/${usuarioAEliminar}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (!response.ok) throw new Error('Error al eliminar usuario');
        cargarUsuarios();
        cerrarModal('modalEliminar');
    })
    .catch(error => {
        alert("Error al eliminar usuario: " + error.message);
        console.error(error);
    });
}

function agregarFilaAsignacion(usuario) {
    const tabla = document.getElementById('tablaAsignaciones');
    const fila = document.createElement('tr');
    fila.innerHTML = `
        <td>${usuario.dni}</td>
        <td>${usuario.nombre}</td>
        <td>${usuario.rol}</td>
        <td>
            <button class="btn" onclick="abrirModalAsignar(${usuario.id}, '${usuario.nombre}', '${usuario.rol}')">Asignar</button>
        </td>
    `;
    tabla.appendChild(fila);
}

// ✅ Nuevo: llenar selects dinámicamente desde el backend
function abrirModalAsignar(id, nombre, rol) {
    document.getElementById('asignarId').value = id;
    document.getElementById('asignarNombre').textContent = nombre;
    document.getElementById('asignarRol').textContent = rol;

    cargarSelect('/api/grados', 'grado');
    cargarSelect('/api/secciones', 'seccion');
    cargarSelect('/api/cursos', 'curso');

    document.getElementById('modalAsignar').style.display = 'block';
}

function cargarSelect(url, idSelect) {
    const select = document.getElementById(idSelect);
    select.innerHTML = '<option value="">Cargando...</option>';

    fetch(url)
        .then(response => response.json())
        .then(data => {
            select.innerHTML = '';
            data.forEach(item => {
                const option = document.createElement('option');
                option.value = item.id;
                option.textContent = item.nombre;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.error(`Error al cargar ${idSelect}:`, error);
            select.innerHTML = `<option value="">Error</option>`;
        });
}

function guardarAsignacion() {
    const id = document.getElementById('asignarId').value;
    const grado = document.getElementById('grado').value;
    const seccion = document.getElementById('seccion').value;
    const curso = document.getElementById('curso').value;

    alert(`Asignación guardada (simulada):\nID: ${id}\nGrado: ${grado}\nSección: ${seccion}\nCurso: ${curso}`);
    cerrarModal('modalAsignar');
}

// ✅ Al cargar la página, se llenan las tablas
window.addEventListener('DOMContentLoaded', cargarUsuarios);