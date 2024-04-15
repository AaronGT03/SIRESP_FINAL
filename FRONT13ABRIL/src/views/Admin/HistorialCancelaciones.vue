<template>
  <div>
    <b-row>
      <b-col cols="12">
        <NavbarUser />
      </b-col>
    </b-row>

    <div class="container-fluid" style="margin-top: 10%;">
      <div class="row mt-5">
        <div class="col-md-12">
          <div class="mt-2">
            <h2 class="text-center">Historial de Cancelaciones</h2>
          </div>

          <div class="row justify-content-center mt-4">
            <div class="col-xl-12">
              <b-card class="card_shadow mt-3" v-for="(cancellation, index) in cancellations" :key="index">
                <div class="row">
                  <div class="col-md-2 col-sm-12 text-center">
                    <b-row>
                      <b-col cols="12">
                        <h4>Id Cancelación</h4>
                        <p class="text-center">{{cancellation.id }}</p>
                      </b-col>
                    </b-row>
                  </div>
                  <div class="col-md-7 text-center">
                    <div class="row">
                      <div class="col-md-3">
                        <h4>Usuario</h4>
                        <p>{{ cancellation.booking.user.names }} {{ cancellation.booking.user.lastName }}</p>
                      </div>
                      <div class="col-md-3">
                        <h4>Nombre Alojamiento</h4>
                        <p>{{ cancellation.booking.accommodation.name }}</p>
                      </div>
                      <div class="col-md-3">
                        <h4>Fecha Llegada</h4>
                        <p>{{ formatDate(cancellation.booking.arrivalDate) }}</p>
                      </div>
                      <div class="col-md-3">
                        <h4>Fecha Salida</h4>
                        <p>{{ formatDate(cancellation.booking.departureDate) }}</p>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-3 text-center">
                    <div class="row">
                      <div class="col-md-12">
                        <h4>Motivo</h4>
                        <p>{{ cancellation.reason }}</p> <!-- Aquí llamamos a la función formatDate -->
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
import NavbarUser from "../../components/Inicio/NavbarUser.vue";
import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
import instance from "../../config/http-client.gateway";

export default {
  name: 'CancellationApprovalView',
  components: {
    NavbarUser,
    CategoriesNavbar,
  },
  data() {
    return {
      cancellations: [],
    };
  },
  methods: {
    formatDate(timestamp) {
      const date = new Date(timestamp);
      const year = date.getFullYear().toString().slice(-2);
      const month = ('0' + (date.getMonth() + 1)).slice(-2);
      const day = ('0' + date.getDate()).slice(-2);
      return `${year}-${month}-${day}`;
    },
    async getCancellations() {
      try {
        const response = await instance.doGet('/cancellation/');
        console.log("Cancellations: ");
        console.log(response.data.data);
        this.cancellations = response.data.data;
      } catch (error) {
        console.error('Error al obtener las cancelaciones:', error);
      }
    },
    async approveCancellation(id) {
      try {
        // Lógica para aprobar la cancelación con el ID proporcionado
        console.log(`Aprobar cancelación con ID: ${id}`);
      } catch (error) {
        console.error('Error al aprobar la cancelación:', error);
      }
    },
    async rejectCancellation(id) {
      try {
        // Lógica para rechazar la cancelación con el ID proporcionado
        console.log(`Rechazar cancelación con ID: ${id}`);
      } catch (error) {
        console.error('Error al rechazar la cancelación:', error);
      }
    },
  },
  mounted() {
    this.getCancellations();
  },
}
</script>

<style>
/* Estilos personalizados aquí */
</style>
