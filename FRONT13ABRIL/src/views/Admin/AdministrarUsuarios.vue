<template>
    <b-container fluid>
      <NavbarUser />
      <b-container fluid style="margin-top: 5%;" >
        <div >
          <Deshabilitarusuarios />
        </div>
      </b-container>
    </b-container>
  </template>
  
  <style scoped>
  .main-content {
    margin-top: calc(3.5rem + 50px); /* Ajusta según la altura de tus componentes Navbar y CategoriesNavbar */
    padding: 1rem;
  }
  
  .admin-container {
    max-width: 1800px;
    height: auto;
    margin: 0 auto;
    padding: 20px;
    margin-top: 20px;
    margin-bottom: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background-color: #f7f7f7;
    border-radius: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    position:static;
    top: 80px;
  }
  
  
  .admin-container .b-form {
    width: 100%;
  }
  
  </style>
  
  
  <script>
  import NavbarUser from "../../components/Inicio/NavbarUser.vue";
  import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
  import Deshabilitarusuarios from "../../components/AdministrarUsuarios/Deshabilitarusuarios.vue";
  
  export default {
    name: 'Plantilla',
    components: {
      NavbarUser,
      CategoriesNavbar,
      Deshabilitarusuarios
    },
  }
  </script>
    
  
    