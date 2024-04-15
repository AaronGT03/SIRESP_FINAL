<template>
  <div class="divAnf">
    <b-row>
            <b-col cols="12">
                <NavbarUser />
            </b-col>
        </b-row>
    <div class="container-fluid" style="margin-top: 5%;">
      <h3 class="text-center">Verificación de Anfitriones</h3>
    <div class="container-fluid" v-for="(host, index) in hosts" >
      <div class="row justify-content-center mt-4">
        <div class="col-xl-12">
          <b-card class="card_shadow">
            <div class="row">
              <div class="col-md-3 text-center">
                <img class="img_historial" :src="host.identificationImage" />
              </div>
              <div class="col-md-4 text-center">
                <div class="row">
                  <div class="col-md-4">
                    <h5>CURP</h5>
                    <p>{{ host.curp }}</p>
                  </div>
                  <div class="col-md-4">
                    <h5>RFC</h5>
                    <p>{{ host.rfc }}</p>
                  </div>
                </div>
              </div>
              <div class="col-md-2 text-center">
                <h5>Acciones</h5>
                <b-button
                      variant="success"
                      @click="approveVerification(host)"
                      >Aprobar</b-button
                    >
                    <b-button variant="danger" @click="rejectVerification(host)"
                      >Rechazar</b-button
                    >
              </div>
            </div>
          </b-card>
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
        const listHosts = response.data.data;
        this.hosts = listHosts.filter((host) => host.user.status === "pendiente");
      } catch (error) {
        console.error("Error al obtener los datos de los anfitriones:", error);
      }
    },

    async approveVerification(host) {
      const userId = host.user.id;
      try {
        const response = await instance.doPost("/user/actualizar/rol", {
          id: `${userId}`,
          role: "ROLE_HOST",
        });

        const response2 = await instance.doPost("/user/actualizar/status", {
          id: `${userId}`,
          status: "Habilitado",
        });
        Swal.fire({
          icon: "success",
          title: "Éxito",
          text: `Verificación aprobada con ID de usuario: ${userId}`,
        }).then(() => {
          window.location.reload();
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
          status: "Activo",
        });
        const response3 = await instance.doDelete(`/host/${host.id}`);
        console.log(`Verificación rechazada con ID de usuario: ${userId}`);
        Swal.fire({
          icon: "success",
          title: "Accion realizada con exito",
          text: `Verificación rechazada con ID de usuario: ${userId}`,
        }).then(() => {
          window.location.reload();
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
@media screen and (max-width: 780px) {
  .img_historial {
    width: 100px;
    height: 100px;
  }
  .divAnf {
    margin-top: 30%;
    margin-bottom: 10px;
  }
  
}
</style>
