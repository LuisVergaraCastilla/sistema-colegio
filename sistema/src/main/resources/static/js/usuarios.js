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
    const dni = document.getElementById('dniCrear').value.trim();
    const nombre = document.getElementById('nombreCrear').value.trim();
    const contrasena = document.getElementById('contrasenaCrear').value.trim();
    const rol = document.getElementById('rolCrear').value;

    if (!dni || !nombre || !contrasena || !rol) {
        alert("Por favor completa todos los campos.");
        return;
    }

    const nuevoUsuario = {
        dni: dni,
        nombre: nombre,
        contrasena: contrasena,
        rol: rol,
        activo: true
    };

    fetch('/api/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuevoUsuario)
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Error al crear usuario");
        }
        return res.json();
    })
    .then(usuario => {
        agregarUsuarioATabla(usuario);
        cerrarModal('modalCrear');
        limpiarCamposCrear();
    })
    .catch(error => {
        console.error("Hubo un error al crear el usuario:", error);
        alert("Hubo un error al crear el usuario.");
    });
}

function agregarUsuarioATabla(usuario) {
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
    const dni = document.getElementById('editarDni').value.trim();
    const nombre = document.getElementById('editarNombre').value.trim();
    const rol = document.getElementById('editarRol').value;

    if (!dni || !nombre || !rol) {
        alert("Por favor completa todos los campos.");
        return;
    }

    const usuarioEditado = {
        dni: dni,
        nombre: nombre,
        rol: rol
    };

    fetch(`/api/usuarios/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuarioEditado)
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Error al actualizar el usuario");
        }
        return res.json();
    })
    .then(usuario => {
        actualizarFilaEnTabla(usuario);
        cerrarModal('modalEditar');
    })
    .catch(error => {
        console.error("Hubo un error al actualizar el usuario:", error);
        alert("Hubo un error al actualizar el usuario.");
    });
}

function actualizarFilaEnTabla(usuario) {
    const filas = document.querySelectorAll("#tablaUsuarios tr");
    filas.forEach(fila => {
        const botonEditar = fila.querySelector("button[onclick^='abrirModalEditar']");
        if (botonEditar && botonEditar.outerHTML.includes(`abrirModalEditar(${usuario.id}`)) {
            fila.innerHTML = `
                <td>${usuario.dni}</td>
                <td>${usuario.nombre}</td>
                <td>${usuario.rol}</td>
                <td>
                    <button class="btn" onclick="abrirModalEditar(${usuario.id}, '${usuario.dni}', '${usuario.nombre}', '${usuario.rol}')">Editar</button>
                    <button class="btn" onclick="abrirModalEliminar(${usuario.id})">Eliminar</button>
                </td>
            `;
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {
    cargarUsuarios();
});

function cargarUsuarios() {
    fetch("/api/usuarios")
        .then(response => {
            if (!response.ok) throw new Error("Error al obtener los usuarios");
            return response.json();
        })
        .then(data => {
            const tabla = document.getElementById('tablaUsuarios');
            tabla.innerHTML = ""; // limpiar la tabla

            data.forEach(usuario => {
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
            });
        })
        .catch(error => {
            console.error("Error al cargar usuarios:", error);
            alert("Hubo un error al cargar los usuarios.");
        });
}

function confirmarEliminar() {
    const id = window.usuarioAEliminar;
    if (!id) return;

    fetch(`/api/usuarios/${id}`, {
        method: 'DELETE'
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Error al eliminar el usuario");
        }
        eliminarFilaDeTabla(id);
        cerrarModal('modalEliminar');
    })
    .catch(error => {
        console.error("Hubo un error al eliminar el usuario:", error);
        alert("Hubo un error al eliminar el usuario.");
    });
}

function eliminarFilaDeTabla(id) {
    const filas = document.querySelectorAll("#tablaUsuarios tr");
    filas.forEach(fila => {
        const botonEliminar = fila.querySelector("button[onclick^='abrirModalEliminar']");

        if (botonEliminar && botonEliminar.outerHTML.includes(`abrirModalEliminar(${id}`)) {
            fila.remove();
        }
    });
}