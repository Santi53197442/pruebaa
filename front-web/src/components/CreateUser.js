import React, { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './CreateUser.css'; // Importamos los estilos

const CreateUser = () => {
    const { currentUser } = useContext(AuthContext); // Obtener el usuario actual (debe ser administrador)
    const navigate = useNavigate();

    // Estado para los datos del formulario
    const [formData, setFormData] = useState({
        nombre: '',
        apellido: '',
        ci: '',
        contrasenia: '',
        email: '',
        telefono: '',
        fechaNac: '',
        rol: 'Cliente', // Rol predeterminado
    });

    // Manejo del cambio en los campos del formulario
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    // Enviar el formulario
    const handleSubmit = async (e) => {
        e.preventDefault();

        if (currentUser?.rol !== 'Administrador') {
            alert('No tienes permisos para realizar esta acción');
            navigate('/home'); // Redirige a la página principal si no es admin
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/auth/crear', formData, {
                params: { adminId: currentUser.id } // Enviar el ID del administrador que está creando el usuario
            });
            alert('Usuario creado exitosamente');
            navigate('/home'); // Redirige a la página principal después de crear el usuario
        } catch (error) {
            console.error(error);
            alert('Hubo un error al crear el usuario');
        }
    };

    if (currentUser?.rol !== 'Administrador') {
        return <h2 className="access-denied">Acceso Denegado: Solo los administradores pueden crear usuarios.</h2>;
    }

    return (
        <div className="create-user-container">
            <h2>Crear Nuevo Usuario</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nombre</label>
                    <input
                        type="text"
                        name="nombre"
                        value={formData.nombre}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Apellido</label>
                    <input
                        type="text"
                        name="apellido"
                        value={formData.apellido}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Cédula de Identidad</label>
                    <input
                        type="number"
                        name="ci"
                        value={formData.ci}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Contraseña</label>
                    <input
                        type="password"
                        name="contrasenia"
                        value={formData.contrasenia}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Email</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Teléfono</label>
                    <input
                        type="number"
                        name="telefono"
                        value={formData.telefono}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Fecha de Nacimiento</label>
                    <input
                        type="date"
                        name="fechaNac"
                        value={formData.fechaNac}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Rol</label>
                    <select name="rol" value={formData.rol} onChange={handleChange}>
                        <option value="Cliente">Cliente</option>
                        <option value="Vendedor">Vendedor</option>
                        <option value="Administrador">Administrador</option>
                    </select>
                </div>
                <button type="submit">Crear Usuario</button>
            </form>

            {/* Botón para volver atrás */}
            <button className="back-button" onClick={() => navigate('/home')}>Volver a la página principal</button>
        </div>
    );
};

export default CreateUser;
