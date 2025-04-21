// Contador.jsx
import React, { useState } from 'react';

function Contador() {
  // Estado con Hooks
  const [contador, setContador] = useState(0);
  
  // Funciones para manipular estado
  const incrementar = () => setContador(contador + 1);
  const decrementar = () => {
    if (contador > 0) {
      setContador(contador - 1);
    }
  };

  return (
    <div className="contador-container">
      <h1 style={{ color: 'blue' }}>Componente de ejemplo en React</h1>
      <p>El valor actual es: {contador}</p>
      <button 
        onClick={incrementar}
        style={{ padding: '10px', marginRight: '10px' }}
      >
        Incrementar
      </button>
      <button 
        onClick={decrementar}
        style={{ padding: '10px' }}
      >
        Decrementar
      </button>
    </div>
  );
}

export default Contador;