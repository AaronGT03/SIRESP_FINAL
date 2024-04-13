<template>
  <div>
    <b-container fluid>
    <CategoriesNavbar />
    <NavbarUser />
      <HistorialVerifAlojamientos />
    </b-container>
  </div>
  </template>
  
  <script>
  import NavbarUser from "../../components/Inicio/NavbarUser.vue";
    import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
    import HistorialVerifAlojamientos from "../../components/HistVerifAlojView/HistorialVerifAlojamientos.vue"

    export default {
      name: 'HistorialVerifAlojView',
      components: {
        NavbarUser,
        CategoriesNavbar,
        HistorialVerifAlojamientos
      }
    }

  </script>
  
  <style scoped>
  </style>
  