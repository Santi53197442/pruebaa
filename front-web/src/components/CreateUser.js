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

    // Definir la base URL según entorno
    const baseURL =
        process.env.NODE_ENV === 'production'
            ? 'https://pruebaa-production.up.railway.app/api/auth'
            : 'http://localhost:8080/api/auth';

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
                await axios.post(
                    `${baseURL}/crear`,
                    formData,
                    {
                        params: { adminId: currentUser.id },
                        // withCredentials: true, // habilitar si usas cookies para auth
                    }
                );
                alert('Usuario creado exitosamente');
            } else if (modoCarga === 'csv') {
                if (!csvFile) return alert('Selecciona un archivo CSV');

                const formDataCSV = new FormData();
                formDataCSV.append('archivo', csvFile);

                await axios.post(
                    `${baseURL}/crear-masivo`,
                    formDataCSV,
                    {
                        params: { adminId: currentUser.id },
                        headers: { 'Content-Type': 'multipart/form-data' },
                        // withCredentials: true,
                    }
                );

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
            {/* resto del JSX igual */}
            {/* ... */}
        </div>
    );
};

export default CreateUser;
