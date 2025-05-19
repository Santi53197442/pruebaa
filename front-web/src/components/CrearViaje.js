import React, { useEffect, useState } from "react";
import axios from "axios";
import "./CrearViaje.css";


const CrearViaje = () => {
    const [localidades, setLocalidades] = useState([]);
    const [omnibuses, setOmnibuses] = useState([]);
    const [formulario, setFormulario] = useState({
        fecha: "",
        horaSalida: "",
        horaLlegada: "",
        origenId: "",
        destinoId: "",
        busId: ""
    });
    const [mensaje, setMensaje] = useState("");

    useEffect(() => {
        axios.get("http://localhost:8081/api/localidades")
            .then(res => setLocalidades(res.data))
            .catch(err => console.error("Error cargando localidades:", err));

        axios.get("http://localhost:8081/api/omnibus")
            .then(res => setOmnibuses(res.data))
            .catch(err => console.error("Error cargando ómnibuses:", err));
    }, []);

    const handleChange = e => {
        setFormulario({ ...formulario, [e.target.name]: e.target.value });
    };

    const handleSubmit = async e => {
        e.preventDefault();
        try {
            await axios.post("http://localhost:8081/api/viajes/crear", formulario);
            setMensaje("✅ Viaje creado con éxito.");
        } catch (error) {
            setMensaje(error.response?.data || "❌ Error al crear el viaje.");
        }
    };

    const handleVolver = () => {
        window.history.back(); // Vuelve a la página anterior
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
            <div className="form-container">
                <h2 className="form-title">Alta de Nuevo Viaje</h2>

                {mensaje && (
                    <div className={`mb-4 text-sm font-medium p-3 rounded ${mensaje.startsWith("✅") ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"}`}>
                        {mensaje}
                    </div>
                )}

                <form onSubmit={handleSubmit} className="space-y-4">
                    <div className="input-group">
                        <label>Fecha del viaje</label>
                        <input type="date" name="fecha" onChange={handleChange} required />
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                        <div className="input-group">
                            <label>Hora de salida</label>
                            <input type="time" name="horaSalida" onChange={handleChange} required />
                        </div>
                        <div className="input-group">
                            <label>Hora de llegada</label>
                            <input type="time" name="horaLlegada" onChange={handleChange} required />
                        </div>
                    </div>

                    <div className="input-group">
                        <label>Origen</label>
                        <select name="origenId" onChange={handleChange} required>
                            <option value="">-- Seleccionar origen --</option>
                            {localidades.map(loc => (
                                <option key={loc.id} value={loc.id}>{loc.nombre}</option>
                            ))}
                        </select>
                    </div>

                    <div className="input-group">
                        <label>Destino</label>
                        <select name="destinoId" onChange={handleChange} required>
                            <option value="">-- Seleccionar destino --</option>
                            {localidades.map(loc => (
                                <option key={loc.id} value={loc.id}>{loc.nombre}</option>
                            ))}
                        </select>
                    </div>

                    <div className="input-group">
                        <label>Ómnibus</label>
                        <select name="busId" onChange={handleChange} required>
                            <option value="">-- Seleccionar ómnibus --</option>
                            {omnibuses.map(bus => (
                                <option key={bus.id} value={bus.id}>#{bus.id} - {bus.matricula}</option>
                            ))}
                        </select>
                    </div>

                    <div className="button-row">
                        <button type="button" onClick={handleVolver} className="button-cancel">⬅ Volver</button>
                        <button type="submit" className="button-submit">Crear Viaje</button>
                    </div>
                </form>
            </div>
        </div>

    );
};

export default CrearViaje;
