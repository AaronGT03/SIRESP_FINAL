<template>
  <b-container fluid>
    <NavbarUser @ejecutar-funcion="activarEvento"/>
    <b-container fluid class="main-content d-flex justify-content-center align-items-center">
      <div class="admin-container">
        <AdministracionCategorias ref="busqueda"/>
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
import AdministracionCategorias from "../../components/AdminCategorias/AdministracionCategorias.vue";

export default {
  name: 'Plantilla',
  components: {
    NavbarUser,
    CategoriesNavbar,
    AdministracionCategorias
  },
  methods:{
    activarEvento(evento) {
      // Llamamos a la función del componente BusquedadeAlojamiento utilizando una referencia
      this.$refs.busqueda.filterCategory(evento);
    }
  }
}
</script>
  

  