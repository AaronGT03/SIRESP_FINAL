<template>
    <b-container fluid>
    <CategoriesNavbar />
    <NavbarUser />
    <div class="main-content">
        <Pago />
    </div>
    </b-container>
  </template>
  
  <script>
    import NavbarUser from "../../components/Inicio/NavbarUser.vue";
    import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
    import Pago from "../../components/PagoView/Pago.vue";

    export default {
      name: 'Plantilla',
      components: {
        NavbarUser,
        CategoriesNavbar,
        Pago
      }
    }

  </script>
  
  <style scoped>

.main-content {

  margin-top: calc(3.5rem + 50px); 
  padding: 1rem;

}
  </style>
  