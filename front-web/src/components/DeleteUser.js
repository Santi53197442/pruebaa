import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
//import './EliminarUsuario.css';

const DeleteUser = () => {
    const [usuarios, setUsuarios] = useState([]);
    const { currentUser } = useContext(AuthContext);
    const navigate = useNavigate();

    const fetchUsuarios = async () => {
        try {
            const response = await axios.get(`http://localhost:8081/api/auth/usuarios?adminId=${currentUser.id}`);
            setUsuarios(response.data);
        } catch (error) {
            console.error('Error al obtener usuarios:', error);
        }
    };

    useEffect(() => {
        fetchUsuarios();
    }, [currentUser]);

    const handleEliminar = async (usuarioId) => {
        if (window.confirm("¿Estás seguro de que deseas eliminar este usuario?")) {
            try {
                await axios.delete(`http://localhost:8081/api/auth/eliminar?usuarioId=${usuarioId}&adminId=${currentUser.id}`);
                alert("Usuario eliminado correctamente");
                fetchUsuarios(); // Volver a cargar la lista actualizada
            } catch (error) {
                alert("Error al eliminar el usuario");
                console.error(error);
            }
        }
    };

    return (
        <div className="eliminar-usuarios-container">
            <h2>Eliminar Usuarios</h2>
            <table>
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Email</th>
                    <th>CI</th>
                    <th>Rol</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                {usuarios.map((user) => (
                    <tr key={user.id}>
                        <td>{user.nombre}</td>
                        <td>{user.apellido}</td>
                        <td>{user.email}</td>
                        <td>{user.ci}</td>
                        <td>{user.rol}</td>
                        <td>
                            <button onClick={() => handleEliminar(user.id)}>Eliminar</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <button onClick={() => navigate('/home')}>Volver</button>
        </div>
    );
};

export default DeleteUser;
