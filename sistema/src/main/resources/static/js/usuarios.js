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
        if (!response.ok) {
            throw new Error('Error al guardar usuario');
        }
        return response.json();
    })
    .then(data => {
        agregarFilaUsuario(data);
        cerrarModal('modalCrear');
        limpiarCamposCrear();
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

window.addEventListener('DOMContentLoaded', () => {
    fetch('/api/usuarios')
        .then(response => response.json())
        .then(usuarios => {
            usuarios.forEach(usuario => agregarFilaUsuario(usuario));
        })
        .catch(error => console.error('Error al cargar usuarios:', error));
});

function actualizarUsuario() {
    const id = document.getElementById('editarId').value;
    const dni = document.getElementById('editarDni').value;
    const nombre = document.getElementById('editarNombre').value;
    const rol = document.getElementById('editarRol').value;

    const usuario = {
        dni,
        nombre,
        rol
    };

    fetch(`/api/usuarios/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuario)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al actualizar usuario');
        }
        return response.json();
    })
    .then(data => {
        location.reload(); // para que la tabla se actualice
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
        if (!response.ok) {
            throw new Error('Error al eliminar usuario');
        }
        // Quitar la fila de la tabla
        const tabla = document.getElementById('tablaUsuarios');
        const filas = tabla.querySelectorAll('tr');
        filas.forEach(fila => {
            if (fila.innerHTML.includes(`abrirModalEditar(${usuarioAEliminar},`)) {
                fila.remove();
            }
        });
        cerrarModal('modalEliminar');
    })
    .catch(error => {
        alert("Error al eliminar usuario: " + error.message);
        console.error(error);
    });
}