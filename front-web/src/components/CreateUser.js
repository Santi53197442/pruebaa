import React, { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './CreateUser.css';

const CreateUser = () => {
    const { currentUser } = useContext(AuthContext);
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        nombre: '',
        apellido: '',
        ci: '',
        contrasenia: '',
        email: '',
        telefono: '',
        fechaNac: '',
        rol: 'Cliente',
    });

    const [csvFile, setCsvFile] = useState(null);
    const [modoCarga, setModoCarga] = useState('individual'); // 'individual' o 'csv'

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleCSVChange = (e) => {
        setCsvFile(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (currentUser?.rol !== 'Administrador') {
            alert('No tienes permisos para realizar esta acción');
            navigate('/home');
            return;
        }

        try {
            if (modoCarga === 'individual') {
                await axios.post('http://localhost:8080/api/auth/crear', formData, {
                    params: { adminId: currentUser.id },
                });
                alert('Usuario creado exitosamente');
            } else if (modoCarga === 'csv') {
                if (!csvFile) return alert('Selecciona un archivo CSV');

                const formDataCSV = new FormData();
                formDataCSV.append('archivo', csvFile); // clave que espera el backend

                await axios.post('http://localhost:8080/api/auth/crear-masivo', formDataCSV, {
                    params: { adminId: currentUser.id },
                });

                alert('Usuarios cargados exitosamente desde CSV');
            }

            navigate('/home');
        } catch (error) {
            console.error(error);
            alert('Error al crear usuario(s)');
        }
    };

    if (currentUser?.rol !== 'Administrador') {
        return <h2 className="access-denied">Acceso Denegado: Solo los administradores pueden crear usuarios.</h2>;
    }

    return (
        <div className="create-user-container">
            <h2 className="title">Crear Usuario</h2>

            <div className="switch-mode">
                <button
                    className={modoCarga === 'individual' ? 'active' : ''}
                    onClick={() => setModoCarga('individual')}
                >
                    Carga Individual
                </button>
                <button
                    className={modoCarga === 'csv' ? 'active' : ''}
                    onClick={() => setModoCarga('csv')}
                >
                    Carga Masiva (CSV)
                </button>
            </div>

            <div className="form-container">
                {modoCarga === 'individual' && (
                    <form onSubmit={handleSubmit}>
                        <div className="input-group">
                            <label>Nombre</label>
                            <input
                                type="text"
                                name="nombre"
                                value={formData.nombre}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label>Apellido</label>
                            <input
                                type="text"
                                name="apellido"
                                value={formData.apellido}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label>Cédula</label>
                            <input
                                type="number"
                                name="ci"
                                value={formData.ci}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label>Contraseña</label>
                            <input
                                type="password"
                                name="contrasenia"
                                value={formData.contrasenia}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label>Email</label>
                            <input
                                type="email"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label>Teléfono</label>
                            <input
                                type="number"
                                name="telefono"
                                value={formData.telefono}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label>Fecha Nac.</label>
                            <input
                                type="date"
                                name="fechaNac"
                                value={formData.fechaNac}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label>Rol</label>
                            <select
                                name="rol"
                                value={formData.rol}
                                onChange={handleChange}
                            >
                                <option value="Cliente">Cliente</option>
                                <option value="Vendedor">Vendedor</option>
                                <option value="Administrador">Administrador</option>
                            </select>
                        </div>
                        <div className="button-container">
                            <button type="submit" className="submit-btn">
                                Crear Usuario
                            </button>
                            <button type="button" className="back-btn" onClick={() => navigate('/home')}>
                                Volver
                            </button>
                        </div>
                    </form>
                )}

                {modoCarga === 'csv' && (
                    <form onSubmit={handleSubmit}>
                        <div className="csv-upload">
                            <label>Archivo CSV</label>
                            <input
                                type="file"
                                accept=".csv"
                                onChange={handleCSVChange}
                                required
                            />
                            <p>Formato: nombre,apellido,ci,contrasenia,email,telefono,fechaNac,rol</p>
                        </div>
                        <div className="button-container">
                            <button type="submit" className="submit-btn">
                                Cargar CSV
                            </button>
                            <button type="button" className="back-btn" onClick={() => navigate('/home')}>
                                Volver
                            </button>
                        </div>
                    </form>
                )}
            </div>
        </div>
    );
};

export default CreateUser;
