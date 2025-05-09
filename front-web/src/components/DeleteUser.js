import React, { useState, useEffect } from 'react';
import axios from 'axios';

const DeleteUser = () => {
    const [currentUser, setCurrentUser] = useState(null); // Estado para el usuario actual
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');

    // Cargar el usuario actual desde la API
    useEffect(() => {
        axios.get('/api/current-user') // Asume que tienes un endpoint que devuelve el usuario actual
            .then((response) => {
                setCurrentUser(response.data);
            })
            .catch((err) => {
                setError('Error al obtener el usuario: ' + (err.response?.data || err.message));
            });
    }, []);

    if (currentUser === null) {
        return <div>Loading...</div>; // Mientras se carga el usuario
    }

    const handleDeleteUser = async () => {
        if (!email) {
            setError("Por favor ingresa un email.");
            return;
        }

        try {
            const response = await axios.delete('/api/auth/eliminar', {
                params: {
                    email: email,
                    adminId: currentUser.id
                }
            });
            setMessage(response.data);
            setError('');
        } catch (err) {
            setError("Error al eliminar el usuario: " + (err.response?.data || err.message));
            setMessage('');
        }
    };

    return (
        <div>
            <h2>Eliminar Usuario</h2>
            <input
                type="email"
                placeholder="Correo del usuario"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <button onClick={handleDeleteUser}>Eliminar Usuario</button>

            {message && <p>{message}</p>}
            {error && <p>{error}</p>}
        </div>
    );
};

export default DeleteUser;
