import React, { useContext, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import logo from '../assets/images/logo.png';  // Importa la imagen desde src/assets/images
import './Home.css';

const Home = () => {
    const { currentUser, logout } = useContext(AuthContext);
    const [showUserOptions, setShowUserOptions] = useState(false);
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <div className="home-container">
            <header className="home-header">
                <div className="logo-titulo">
                    <img src={logo} alt="Logo" className="logo" />
                    <div className="titulo-dropdown">
                        <h1>Sistema de Ómnibus</h1>
                        <div className="menu-dropdown">
                            <button className="menu-button">Menú ▼</button>
                            <div className="menu-options">
                                {currentUser?.rol === 'Administrador' && (
                                    <>
                                        <Link to="/createUser">Crear Usuario</Link>
                                        <Link to="/deleteUser">Eliminar Usuario</Link>
                                        <Link to="/listarUsuarios">Listar Usuarios</Link>
                                    </>
                                )}
                                {currentUser?.rol === 'Vendedor' && (
                                    <>
                                        <Link to="/altaOmnibus">Alta Ómnibus</Link>
                                        <Link to="/altaLocalidad">Alta Localidad</Link>
                                        <Link to="/listaOmnibus">Ver Ómnibus</Link>
                                        <Link to="/crearViaje">Alta Viaje</Link>
                                    </>
                                )}
                            </div>
                        </div>
                    </div>
                </div>

                <div className="user-dropdown">
                    <span onClick={() => setShowUserOptions(!showUserOptions)}>
                        Bienvenido, {currentUser?.nombre} {currentUser?.apellido} ▼
                    </span>
                    {showUserOptions && (
                        <div className="dropdown">
                            <button onClick={() => navigate('/editarPerfil')}>Editar Datos</button>
                            <button onClick={handleLogout}>Cerrar sesión</button>
                        </div>
                    )}
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
