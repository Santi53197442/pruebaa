import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
//import './ListarUsuarios.css';

const ListarUsuarios = () => {
    const [usuarios, setUsuarios] = useState([]);
    const { currentUser } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUsuarios = async () => {
            try {
                const response = await axios.get(`http://localhost:8081/api/auth/usuarios?adminId=${currentUser.id}`);
                setUsuarios(response.data);
            } catch (error) {
                console.error('Error al obtener usuarios:', error);
            }
        };

        fetchUsuarios();
    }, [currentUser]);

    return (
        <div className="lista-usuarios-container">
            <h2>Lista de Usuarios</h2>
            <table>
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Email</th>
                    <th>CI</th>
                    <th>Rol</th>
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
                    </tr>
                ))}
                </tbody>
            </table>
            <button onClick={() => navigate('/home')}>Volver</button>
        </div>
    );
};

export default ListarUsuarios;
