<template>
    <b-container fluid>
      <NavbarUser style="margin-bottom: 5%;"/>
      <b-container fluid>
        <div >
          <Deshabilitarusuarios />
        </div>
      </b-container>
    </b-container>
  </template>
  
  <style scoped>

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
    
  
    