import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './AltaLocalidad.css';

const AltaLocalidad = () => {
    const [formData, setFormData] = useState({ nombre: '', departamento: '' });
    const [mensaje, setMensaje] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMensaje('');
        setError('');
        try {
            const response = await axios.post('http://localhost:8081/api/localidades', formData);
            setMensaje('✅ Localidad creada correctamente.');
            setFormData({ nombre: '', departamento: '' });
        } catch (error) {
            if (error.response?.status === 409) {
                setError('⚠️ La localidad ya existe.');
            } else {
                setError('❌ Error al crear la localidad.');
            }
        }
    };

    return (
        <div className="alta-localidad-container">
            <h2 className="alta-localidad-title">Alta de Localidad</h2>

            {mensaje && <p className="message success">{mensaje}</p>}
            {error && <p className="message error">{error}</p>}

            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Nombre:</label>
                    <input type="text" name="nombre" value={formData.nombre} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label>Departamento:</label>
                    <input type="text" name="departamento" value={formData.departamento} onChange={handleChange} required />
                </div>
                <div className="button-group">
                    <button type="submit" className="button">Crear Localidad</button>
                    <button type="button" className="button back" onClick={() => navigate(-1)}>Volver</button>
                </div>
            </form>
        </div>
    );
};

export default AltaLocalidad;
