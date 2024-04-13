<template>
  <b-container fluid>
    <CategoriesNavbar @ejecutar-funcion="activarEvento"/>
    <Navbar />
    <div class="main-content">
      <BusquedaAlojamientos ref="busqueda" />
    </div>
  </b-container>
</template>

<script>
import Navbar from "../../components/Inicio/Navbar.vue";
import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
import BusquedaAlojamientos from "../../components/Inicio/BusquedaAlojamientos.vue";

export default {
  name: 'Plantilla',
  components: {
    Navbar,
    CategoriesNavbar,
    BusquedaAlojamientos
  },
  methods:{
    activarEvento(evento) {
      // Llamamos a la función del componente BusquedadeAlojamiento utilizando una referencia
      this.$refs.busqueda.filterCategory(evento);
    }
  }

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