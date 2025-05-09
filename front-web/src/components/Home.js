import React, { useContext } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import './Home.css';

const Home = () => {
    const { currentUser, logout } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
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

                    {/* Mostrar opciones para Administrador */}
                    {currentUser?.rol === 'Administrador' && (
                        <div>
                            <Link to="/createUser">Crear Usuario</Link><br />
                            <Link to="/deleteUser">Eliminar Usuario</Link><br />
                        </div>
                    )}

                    {/* Mostrar opciones para Vendedor */}
                    {currentUser?.rol === 'Vendedor' && (
                        <div>
                            <Link to="/altaOmnibus">Alta Ómnibus</Link><br />
                            <Link to="/altaLocalidad">Alta Localidad</Link><br />
                            <Link to="/listaOmnibus">Ver Ómnibus</Link><br />
                        </div>
                    )}
                </div>
            </main>
        </div>
    );
};

export default Home;
