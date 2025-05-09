import React from 'react';

const Menu = ({ rol }) => {
    switch (rol) {
        case "Administrador":
            return (
                <ul>
                    <li><a href="/admin">Panel de administración</a></li>
                    <li><a href="/usuarios">Gestión de usuarios</a></li>
                    <li><a href="/reportes">Reportes</a></li>
                </ul>
            );
        case "Vendedor":
            return (
                <ul>
                    <li><a href="/ventas">Vender pasajes</a></li>
                    <li><a href="/consultas">Consultar ventas</a></li>
                </ul>
            );
        case "Cliente":
            return (
                <ul>
                    <li><a href="/comprar">Comprar pasajes</a></li>
                    <li><a href="/viajes">Mis viajes</a></li>
                </ul>
            );
        default:
            return <p>Rol no reconocido</p>;
    }
};

export default Menu;
