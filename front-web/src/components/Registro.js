import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { registrarUsuario } from '../api';
import './Registro.css';

const Registro = () => {
    const [formData, setFormData] = useState({
        nombre: '',
        apellido: '',
        ci: '',
        contrasenia: '',
        confirmarContrasenia: '',
        email: '',
        telefono: '',
        fechaNac: ''
    });

    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if (formData.contrasenia !== formData.confirmarContrasenia) {
            return setError('Las contraseñas no coinciden');
        }

        setLoading(true);

        const userData = {
            nombre: formData.nombre,
            apellido: formData.apellido,
            ci: parseInt(formData.ci),
            contrasenia: formData.contrasenia,
            email: formData.email,
            telefono: parseInt(formData.telefono),
            fechaNac: formData.fechaNac
        };

        try {
            await registrarUsuario(userData);
            alert('Usuario registrado con éxito');
            navigate('/login');
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="registro-container">
            <div className="registro-form-container">
                <h2>Registro de Usuario</h2>
                {error && <div className="error-message">{error}</div>}
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Nombre</label>
                        <input
                            type="text"
                            name="nombre"
                            value={formData.nombre}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Apellido</label>
                        <input
                            type="text"
                            name="apellido"
                            value={formData.apellido}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>CI</label>
                        <input
                            type="number"
                            name="ci"
                            value={formData.ci}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Teléfono</label>
                        <input
                            type="number"
                            name="telefono"
                            value={formData.telefono}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Email</label>
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Contraseña</label>
                        <input
                            type="password"
                            name="contrasenia"
                            value={formData.contrasenia}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Confirmar Contraseña</label>
                        <input
                            type="password"
                            name="confirmarContrasenia"
                            value={formData.confirmarContrasenia}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Fecha de Nacimiento</label>
                        <input
                            type="date"
                            name="fechaNac"
                            value={formData.fechaNac}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <button type="submit" disabled={loading}>
                        {loading ? 'Procesando...' : 'Registrarse'}
                    </button>
                </form>
                <p>
                    ¿Ya tienes una cuenta? <Link to="/login">Inicia sesión aquí</Link>
                </p>
            </div>
        </div>
    );
};

export default Registro;
