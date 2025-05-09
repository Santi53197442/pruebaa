import React, { createContext, useState, useEffect } from 'react';

// Creamos el contexto
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [currentUser, setCurrentUser] = useState(null);
    const [loading, setLoading] = useState(true);

    // Al montar el componente, verificamos si el usuario está autenticado
    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user) {
            setCurrentUser(user);
        }
        setLoading(false); // Cambiar el estado a 'false' una vez que se haya hecho la verificación
    }, []);

    // Función de login
    const login = (userData) => {
        // Guardamos el usuario y el token en localStorage
        localStorage.setItem('user', JSON.stringify(userData));
        localStorage.setItem('token', userData.id); // Aquí usamos el ID como token simulado
        setCurrentUser(userData); // Establecemos el estado de currentUser
    };

    // Función de logout
    const logout = () => {
        // Limpiamos el localStorage
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        setCurrentUser(null); // Limpiamos el estado de currentUser
    };

    const value = {
        currentUser,
        login,
        logout
    };

    // Si está cargando, mostramos un mensaje de carga
    return (
        <AuthContext.Provider value={value}>
            {!loading ? children : <div>Loading...</div>} {/* Mostrar "Loading..." mientras verificamos la sesión */}
        </AuthContext.Provider>
    );
};
