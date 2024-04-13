<template>
    <div>
      <b-row>
        <b-col cols="12">
          <Navbar />
        </b-col>
      </b-row>
      <CategoriesNavbar />
      <div class="container-fluid" style="margin-top: 10%;">
        <div class="row mt-5">
          <div class="col-md-12">
            <div class="mt-5">
              <h2 class="text-center">Reseñas de Alojamientos</h2>
            </div>
            <div class="row justify-content-center mt-4">
              <div class="col-xl-12">
                <b-card class="card_shadow mt-3" v-for="(resena, index) in reseñas" :key="index">
                  <div class="row">
                    <div class="col-md-3 text-center">
                      <img class="img_resena" :src="resena.imagen" alt="">
                    </div>
                    <div class="col-md-9">
                      <div class="row">
                        <div class="col-md-6">
                          <h5>Nombre: {{ resena.nombre }}</h5>
                          <h5>Calificación: {{ resena.calificacion }}/5</h5>
                          <h5>Fecha: {{ resena.fecha }}</h5>
                        </div>
                        <div class="col-md-6">
                          <h5>Opinión:</h5>
                          <p>{{ resena.opinion }}</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </b-card>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import Navbar from "../../components/Navbar.vue";
  import CategoriesNavbar from "../../components/CategoriesNavbar.vue";
  import reseñasData from "../../data/reseñas.json";
  
  export default {
    name: "ReseñasView",
    components: {
      Navbar,
      CategoriesNavbar,
    },
    data() {
      return {
        reseñas: [],
      };
    },
    mounted() {
      this.reseñas = reseñasData;
    },
  };
  </script>
  
  <style>
  .img_resena {
    height: 100px;
    width: 100px;
  }
  
  .card_shadow {
    box-shadow: 3px 3px 3px 2px rgba(32, 32, 32, 0.3);
  }
  </style>
  