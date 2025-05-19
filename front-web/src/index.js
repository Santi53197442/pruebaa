    import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App'; // Asegúrate de importar App
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <App /> {/* Asegúrate de renderizar App, no Contador */}
    </React.StrictMode>
);

// Si quieres medir el rendimiento, puedes usar reportWebVitals
reportWebVitals();
