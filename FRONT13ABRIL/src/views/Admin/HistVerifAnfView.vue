<template>
  <div>
    <b-row>
      <b-col cols="12">
        <CategoriesNavbar />
        <NavbarUser />
      </b-col>
    </b-row>

    <div class="container-fluid" style="margin-top: 10%">
      <div class="row mt-5">
        <div class="col-md-12">
          <div class="mt-2">
            <h2 class="text-center">
              Historial de Verificación de Anfitriones
            </h2>
          </div>

          <div class="table-responsive mt-4">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Usuario ID</th>
                  <th>CURP</th>
                  <th>RFC</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(host, index) in hosts" :key="index">
                  <td>{{ host.id }}</td>
                  <td>{{ host.user.id }}</td>
                  <td>{{ host.curp }}</td>
                  <td>{{ host.rfc }}</td>
                  <td>
                    <b-button
                      variant="success"
                      @click="approveVerification(host)"
                      >Aprobar</b-button
                    >
                    <b-button variant="danger" @click="rejectVerification(host)"
                      >Rechazar</b-button
                    >
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
import Swal from "sweetalert2";
const token = sessionStorage.getItem("token");

export default {
  name: "HistorialVerifAnfitriones",
  components: {
    NavbarUser,
    CategoriesNavbar,
  },
  data() {
    return {
      hosts: [],
    };
  },
  methods: {
    async getHosts() {
      try {
        const response = await instance.doGet("/host/");
        console.log("Hosts: ");
        console.log(response.data.data);
        this.hosts = response.data.data;
      } catch (error) {
        console.error("Error al obtener los datos de los anfitriones:", error);
      }
    },

    async approveVerification(host) {
      const userId = host.user.id; // Obtén el ID del usuario del objeto host
      console.log(`Verificación aprobada con ID de usuario: ${userId}`);
      try {
        const response = await instance.doPost("/user/actualizar/rol", {
          id: `${userId}`,
          role: "ROLE_HOST",
        });
        console.log(`Verificación aprobada con ID de usuario: ${userId}`);
        // Aquí puedes recargar los datos o realizar alguna acción adicional si es necesario

        const response2 = await instance.doPost("/user/actualizar/status", {
          id: `${userId}`,
          status: "Habilitado",
        });
        console.log(`Verificación aprobada con ID de usuario: ${userId}`);
        Swal.fire({
          icon: "success",
          title: "Éxito",
          text: `Verificación aprobada con ID de usuario: ${userId}`,
        });
      } catch (error) {
        console.error("Error al aprobar la verificación:", error);
      }
    },

    async rejectVerification(host) {
      const userId = host.user.id; // Obtén el ID del usuario del objeto host
      console.log(`Verificación rechazada con ID de usuario: ${userId}`);
      try {
        const response = await instance.doPost("/user/actualizar/rol", {
          id: `${userId}`,
          role: "ROLE_USER", // Cambia el rol según lo que necesites
        });
        const response2 = await instance.doPost("/user/actualizar/status", {
          id: `${userId}`,
          status: "pendiente",
        });
        console.log(`Verificación rechazada con ID de usuario: ${userId}`);
        Swal.fire({
          icon: "warning",
          title: "Advertencia",
          text: `Verificación rechazada con ID de usuario: ${userId}`,
        });
      } catch (error) {
        console.error("Error al rechazar la verificación:", error);
      }
    },
  },

  mounted() {
    this.getHosts();
  },
};
</script>

<style>
/* Estilos personalizados aquí */
</style>
