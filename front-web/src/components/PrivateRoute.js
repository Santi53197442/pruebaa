import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

const PrivateRoute = ({ children, requiredRole }) => {
    const { currentUser } = useContext(AuthContext);

    if (!currentUser) {
        return <Navigate to="/login" />;
    }

    if (requiredRole && currentUser.rol !== requiredRole) {
        return <Navigate to="/home" />;
    }

    return children;
};

export default PrivateRoute;
