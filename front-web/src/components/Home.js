import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';  // Cambié useHistory por useNavigate
import { AuthContext } from '../context/AuthContext';
import './Home.css';  // Asegúrate de que este archivo exista

const Home = () => {
    const { currentUser, logout } = useContext(AuthContext);
    const navigate = useNavigate();  // Usando useNavigate en vez de useHistory

    const handleLogout = () => {
        logout();
        navigate('/login');  // Reemplazo de history.push por navigate
    };

    return (
        <div className="home-container">
            <header>
                <h1>Sistema de Ómnibus</h1>
                <div className="user-info">
                    <span>Bienvenido, {currentUser?.nombre} {currentUser?.apellido}</span>
                    <button onClick={handleLogout}>Cerrar sesión</button>
                </div>
            </header>
            <main>
                <h2>Página Principal</h2>
                <p>Has iniciado sesión exitosamente en el sistema de ómnibus.</p>
                <div className="dashboard">
                    <p>Aquí se mostrará el contenido principal de la aplicación.</p>
                </div>
            </main>
        </div>
    );
};

export default Home;
