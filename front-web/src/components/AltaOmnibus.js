import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';  // Cambiar useHistory por useNavigate
import './AltaOmnibus.css';



const AltaOmnibus = () => {
    const [formData, setFormData] = useState({
        matricula: '',
        modelo: '',
        cantidadAsientos: '',
        estado: 'ACTIVO',
        localidadActual: '' // se guarda el ID como string
    });

    const [localidades, setLocalidades] = useState([]);
    const navigate = useNavigate();  // Usamos useNavigate

    useEffect(() => {
        const fetchLocalidades = async () => {
            try {
                const response = await axios.get('http://localhost:8081/api/localidades');
                setLocalidades(response.data);
            } catch (error) {
                console.error("Error al cargar las localidades:", error);
            }
        };

        fetchLocalidades();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.matricula || !formData.modelo || !formData.cantidadAsientos || !formData.localidadActual) {
            alert("Por favor, complete todos los campos.");
            return;
        }

        const payload = {
            ...formData,
            cantidadAsientos: parseInt(formData.cantidadAsientos),
            localidadActual: {
                id: parseInt(formData.localidadActual)
            }
        };

        try {
            await axios.post('http://localhost:8080/api/omnibus', payload);
            alert("Ómnibus creado exitosamente");
            setFormData({
                matricula: '',
                modelo: '',
                cantidadAsientos: '',
                estado: 'ACTIVO',
                localidadActual: ''
            });
        } catch (error) {
            console.error("Error al crear el ómnibus:", error);
            alert("Error al crear el ómnibus. Verifique los datos.");
        }
    };

    const handleBack = () => {
        navigate(-1);  // Usamos navigate para ir atrás
    };

    return (
        <div className="alta-omnibus-container">
            <h2>Alta de Ómnibus</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Matrícula:</label>
                    <input
                        type="text"
                        name="matricula"
                        value={formData.matricula}
                        onChange={handleChange}
                    />
                </div>
                <div className="form-group">
                    <label>Modelo:</label>
                    <input
                        type="text"
                        name="modelo"
                        value={formData.modelo}
                        onChange={handleChange}
                    />
                </div>
                <div className="form-group">
                    <label>Cantidad de Asientos:</label>
                    <input
                        type="number"
                        name="cantidadAsientos"
                        value={formData.cantidadAsientos}
                        onChange={handleChange}
                    />
                </div>
                <div className="form-group">
                    <label>Estado:</label>
                    <select
                        name="estado"
                        value={formData.estado}
                        onChange={handleChange}
                    >
                        <option value="ACTIVO">Activo</option>
                        <option value="INACTIVO">Inactivo</option>
                    </select>
                </div>
                <div className="form-group">
                    <label>Localidad:</label>
                    <select
                        name="localidadActual"
                        value={formData.localidadActual}
                        onChange={handleChange}
                    >
                        <option value="">Seleccione una localidad</option>
                        {localidades.map(localidad => (
                            <option key={localidad.id} value={localidad.id}>
                                {localidad.nombre}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="buttons-container">
                    <button type="submit">Crear Ómnibus</button>
                    <button type="button" onClick={handleBack} className="back-button">Volver</button>
                </div>
            </form>
        </div>
    );
};

export default AltaOmnibus;
