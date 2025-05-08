import React, { useState, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';  // Cambié useHistory por useNavigate
import { AuthContext } from '../context/AuthContext';
import { loginUser } from '../api';
// import './Login.css'; // Descomenté esta línea si la necesitas

const Login = () => {
    const [email, setEmail] = useState('');
    const [contrasenia, setContrasenia] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const { login } = useContext(AuthContext);
    const navigate = useNavigate();  // Usando useNavigate en lugar de useHistory

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        try {
            const userData = await loginUser(email, contrasenia);
            login(userData);
            navigate('/home');  // Usando navigate en lugar de history.push
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-container">
            <div className="login-form-container">
                <h2>Iniciar Sesión</h2>
                {error && <div className="error-message">{error}</div>}
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Email</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>Contraseña</label>
                        <input
                            type="password"
                            value={contrasenia}
                            onChange={(e) => setContrasenia(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" disabled={loading}>
                        {loading ? 'Procesando...' : 'Iniciar Sesión'}
                    </button>
                </form>
                <p>
                    ¿No tienes una cuenta? <Link to="/registro">Regístrate aquí</Link>
                </p>
            </div>
        </div>
    );
};

export default Login;
