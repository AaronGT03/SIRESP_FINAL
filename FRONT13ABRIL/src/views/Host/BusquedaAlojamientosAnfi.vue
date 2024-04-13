<template>
    <b-container fluid>
      <CategoriesNavbar />
      <NavbarUser />
      <div class="main-content">
        <AlojamientoAnfi />
      </div>
    </b-container>
  </template>
  
  <script>
  import NavbarUser from "../../components/Inicio/NavbarUser.vue";
  import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
  import AlojamientoAnfi from "../../components/BusquedaAlojamientosAnfi/AlojamientoAnfi.vue";
  
  export default {
    name: 'Inicio',
    components: {
        NavbarUser,
      CategoriesNavbar,
      AlojamientoAnfi
    },
  
  }
  
  </script>
  
  <style scoped>
  .app-container {
    display: flex;
    flex-direction: column;
  }
  
  .main-content {
    margin-top: calc(3.5rem + 50px);
    padding: 1rem;
  }
  </style>