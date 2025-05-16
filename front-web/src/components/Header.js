// src/components/Header.js
import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import logo from '../assets/images/logo.png';  // Importa la imagen desde src/assets/images
import './Header.css';

const Header = () => {
    const { currentUser, logout } = useContext(AuthContext);
    const [showDropdown, setShowDropdown] = useState(false);
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    const handleEdit = () => {
        navigate('/editarPerfil');
    };

    return (
        <header className="main-header">
            <div className="header-left">
                <img src={logo} alt="Logo" className="logo" />
                <h1>Sistema de Ómnibus</h1>
            </div>
            <div className="header-right">
                <div
                    className="user-menu"
                    onClick={() => setShowDropdown(!showDropdown)}
                >
                    {currentUser?.nombre} {currentUser?.apellido}
                    {showDropdown && (
                        <div className="dropdown">
                            <button onClick={handleEdit}>Editar Datos</button>
                            <button onClick={handleLogout}>Cerrar Sesión</button>
                        </div>
                    )}
                </div>
            </div>
        </header>
    );
};

export default Header;
