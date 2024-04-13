<template>
    <div class="container">
      <h1>Lista de Anfitriones</h1>
      <div class="table-wrapper">
        <table class="user-table">
          <thead>
            <tr>
              <th class="header">Nombre</th>
              <th class="header">Apellido</th>
              <th class="header">RFC</th>
              <th class="header">Email</th>
              <th class="header">Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(usuario, index) in usuarios" :key="index">
              <td>{{ usuario.nombre }}</td>
              <td>{{ usuario.apellido }}</td>
              <td>{{ usuario.rfc }}</td>
              <td>{{ usuario.email }}</td>
              <td>
                <button class="aceptar" @click="aceptarUsuario(index)">Aceptar</button>
                <button class="rechazar" @click="rechazarUsuario(index)">Rechazar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        usuarios: [
          { rfc: 'ABC123456XYZ', email: 'usuario1@example.com', nombre: 'Juan', apellido: 'Pérez' },
          { rfc: 'DEF789012UVW', email: 'usuario2@example.com', nombre: 'María', apellido: 'González' },
          { rfc: 'GHI345678RST', email: 'usuario3@example.com', nombre: 'Pedro', apellido: 'López' },
          // Agrega más usuarios si es necesario
        ]
      };
    },
    methods: {
      aceptarUsuario(index) {
        // Lógica para aceptar el usuario en el índice dado
        console.log('Usuario aceptado:', this.usuarios[index]);
      },
      rechazarUsuario(index) {
        // Lógica para rechazar el usuario en el índice dado
        console.log('Usuario rechazado:', this.usuarios[index]);
      }
    }
  };
  </script>
  
  <style scoped>
  .container {
    margin: 20px auto;
    max-width: 800px;
  }
  
  h1 {
    margin-bottom: 20px;
    font-size: 24px;
    text-align: center;
  }
  
  .table-wrapper {
    overflow-x: auto;
  }
  
  .user-table {
    width: 100%;
    border-collapse: collapse;
    border-radius: 8px;
    overflow: hidden;
  }
  
  .user-table th,
  .user-table td {
    border: 1px solid #ddd;
    padding: 10px;
    text-align: left;
  }
  
  .user-table th.header {
    background-color: #808080;
    color: white;
    border-radius: 8px 8px 0 0;
  }
  
  .user-table td {
    background-color: #f9f9f9;
  }
  
  .user-table td:nth-child(odd) {
    background-color: #f9f9f9;
  }
  
  .user-table td:nth-child(even) {
    background-color: #ffffff;
  }
  
  button {
    padding: 8px 16px;
    margin-right: 5px;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  button.aceptar {
    background-color: #4caf50;
    color: #fff;
  }
  
  button.rechazar {
    background-color: #f44336;
    color: #fff;
  }
  
  button:hover {
    background-color: #333;
  }
  </style>
  