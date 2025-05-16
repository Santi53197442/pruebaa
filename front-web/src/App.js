import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Login from './components/Login';
import Registro from './components/Registro';
import Home from './components/Home';
import CreateUser from './components/CreateUser';
import DeleteUser from './components/DeleteUser';
import AltaOmnibus from './components/AltaOmnibus';
import AltaLocalidad from './components/AltaLocalidad';
import ListaOmnibus from './components/ListaOmnibus';
import ListarUsuarios from './components/ListaUsuarios';
import CrearViaje from './components/CrearViaje';
import PrivateRoute from './components/PrivateRoute';

import './App.css';

function App() {
    return (
        <AuthProvider>
            <Router>
                <div className="App">
                    <Routes>
                        {/* Rutas públicas */}
                        <Route path="/login" element={<Login />} />
                        <Route path="/registro" element={<Registro />} />

                        {/* Ruta común para todos los roles */}
                        <Route
                            path="/home"
                            element={
                                <PrivateRoute>
                                    <Home />
                                </PrivateRoute>
                            }
                        />

                        {/* Rutas exclusivas para el Administrador */}
                        <Route
                            path="/createUser"
                            element={
                                <PrivateRoute requiredRole="Administrador">
                                    <CreateUser />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/deleteUser"
                            element={
                                <PrivateRoute requiredRole="Administrador">
                                    <DeleteUser />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/listarUsuarios"
                            element={
                                <PrivateRoute requiredRole="Administrador">
                                    <ListarUsuarios />
                                </PrivateRoute>
                            }
                        />

                        {/* Rutas exclusivas para el Vendedor */}
                        <Route
                            path="/altaOmnibus"
                            element={
                                <PrivateRoute requiredRole="Vendedor">
                                    <AltaOmnibus />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/altaLocalidad"
                            element={
                                <PrivateRoute requiredRole="Vendedor">
                                    <AltaLocalidad />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/listaOmnibus"
                            element={
                                <PrivateRoute requiredRole="Vendedor">
                                    <ListaOmnibus />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/crearViaje"
                            element={
                                <PrivateRoute requiredRole="Vendedor">
                                    <CrearViaje />
                                </PrivateRoute>
                            }
                        />

                        {/* Redirección por defecto */}
                        <Route path="*" element={<Navigate to="/login" />} />
                    </Routes>
                </div>
            </Router>
        </AuthProvider>
    );
}

export default App;
