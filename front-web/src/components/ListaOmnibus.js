import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Header from './Header';
import './ListaOmnibus.css';

const ListaOmnibus = () => {
    const [omnibus, setOmnibus] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchOmnibus = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/omnibus/ordenados');
                setOmnibus(response.data);
            } catch (error) {
                console.error("Error al obtener los ómnibus:", error);
            }
        };

        fetchOmnibus();
    }, []);

    const handleBack = () => {
        navigate(-1);
    };

    return (
        <>
            <Header />
            <div className="lista-omnibus-container">
                <h2>Lista de Ómnibus</h2>
                <table>
                    <thead>
                    <tr>
                        <th>Matrícula</th>
                        <th>Modelo</th>
                        <th>Asientos</th>
                        <th>Estado</th>
                        <th>Localidad Actual</th>
                    </tr>
                    </thead>
                    <tbody>
                    {omnibus.map((bus) => (
                        <tr key={bus.id}>
                            <td>{bus.matricula}</td>
                            <td>{bus.modelo}</td>
                            <td>{bus.cantidadAsientos}</td>
                            <td>{bus.estado}</td>
                            <td>{bus.localidadActual?.nombre || 'N/A'}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <button onClick={handleBack} className="back-button">Volver</button>
            </div>
        </>
    );
};

export default ListaOmnibus;
