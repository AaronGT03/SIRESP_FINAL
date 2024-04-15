<template>
  <b-container fluid>
    <transition name="fade">
      <CategoriesNavbar v-if="isVisible" @ejecutar-funcion="activarEvento"/>
    </transition>
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
  data() {
    return {
      isVisible: true
    }
  },
  methods:{
    activarEvento(evento) {
      this.$refs.busqueda.filterCategory(evento);
    },
    handleScroll() {
      this.isVisible = window.scrollY < 50;
    }
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  }

}

</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}

.app-container {
  display: flex;
  flex-direction: column;
}

.main-content {
  margin-top: calc(3.5rem + 50px);
  padding: 1rem;
}
@media screen and (max-width: 768px){
  .main-content {

    padding: 1rem;
  }
}
</style>