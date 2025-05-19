import React, { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './CreateUser.css';

const CreateUser = () => {
    const { currentUser } = useContext(AuthContext); // debe tener el rol y el id
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        nombre: '',
        apellido: '',
        ci: '',
        contrasenia: '',
        email: '',
        telefono: '',
        fechaNac: '',
        rol: 'Cliente', // Por defecto
    });

    const baseURL =
        process.env.NODE_ENV === 'production'
            ? 'https://pruebaa-production.up.railway.app/api/auth'
            : 'http://localhost:8081/api/auth';

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!currentUser || currentUser.rol !== 'Administrador') {
            alert('Acceso denegado. Solo administradores pueden crear usuarios.');
            return navigate('/home');
        }

        try {
            await axios.post(`${baseURL}/crear`, formData, {
                params: { adminId: currentUser.id },
            });

            alert('Usuario creado exitosamente');
            navigate('/home');
        } catch (error) {
            console.error(error);
            alert('Error al crear el usuario');
        }
    };

    if (currentUser?.rol !== 'Administrador') {
        return <h2 className="access-denied">Acceso Denegado: Solo los administradores pueden crear usuarios.</h2>;
    }

    return (
        <div className="create-user-container">
            <h2>Crear Usuario</h2>
            <form onSubmit={handleSubmit} className="create-user-form">
                <input type="text" name="nombre" placeholder="Nombre" value={formData.nombre} onChange={handleChange} required />
                <input type="text" name="apellido" placeholder="Apellido" value={formData.apellido} onChange={handleChange} required />
                <input type="text" name="ci" placeholder="CI" value={formData.ci} onChange={handleChange} required />
                <input type="password" name="contrasenia" placeholder="Contraseña" value={formData.contrasenia} onChange={handleChange} required />
                <input type="email" name="email" placeholder="Correo Electrónico" value={formData.email} onChange={handleChange} required />
                <input type="tel" name="telefono" placeholder="Teléfono" value={formData.telefono} onChange={handleChange} required />
                <input type="date" name="fechaNac" value={formData.fechaNac} onChange={handleChange} required />

                <select name="rol" value={formData.rol} onChange={handleChange}>
                    <option value="Cliente">Cliente</option>
                    <option value="Vendedor">Vendedor</option>
                    <option value="Administrador">Administrador</option>
                </select>

                <button type="submit">Crear Usuario</button>
            </form>
        </div>
    );
};

export default CreateUser;
