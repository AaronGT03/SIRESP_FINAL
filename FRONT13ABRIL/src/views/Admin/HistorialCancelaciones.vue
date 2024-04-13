<template>
  <div>
    <b-row>
      <b-col cols="12">
        <CategoriesNavbar />
        <NavbarUser />
      </b-col>
    </b-row>

    <div class="container-fluid" style="margin-top: 10%;">
      <div class="row mt-5">
        <div class="col-md-12">
          <div class="mt-2">
            <h2 class="text-center">Historial de Cancelaciones</h2>
          </div>

          <div class="table-responsive mt-4">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Reserva ID</th>
                  <th>Motivo</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(cancellation, index) in cancellations" :key="index">
                  <td>{{ cancellation.id }}</td>
                  <td>{{ cancellation.booking.id }}</td>
                  <td>{{ cancellation.reason }}</td>
                  <td>
                    <b-button variant="success" @click="approveCancellation(cancellation.id)">Aprobar</b-button>
                    <b-button variant="danger" @click="rejectCancellation(cancellation.id)">Rechazar</b-button>
                  </td>
                </tr>
              </tbody>
            </table>
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
