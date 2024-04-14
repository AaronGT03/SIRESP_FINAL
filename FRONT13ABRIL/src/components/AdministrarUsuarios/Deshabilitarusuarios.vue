<template>
  <div>
    <div class="container-fluid" v-for="(user, index) in userData">
      <div class="row justify-content-center mt-4">
        <div class="col-xl-12">
          <b-card class="card_shadow">
            <div class="row">
              <div class="col-md-3 text-center">
                <img class="img_historial" :src="user.profilePicture" />
              </div>
              <div class="col-md-4 text-center">
                <div class="row">
                  <div class="col-md-4">
                    <h5>Nombre</h5>
                    <p>{{ user.names }}</p>
                  </div>
                  <div class="col-md-4">
                    <h5>Apellido</h5>
                    <p>{{ user.lastName }}</p>
                  </div>
                  <div class="col-md-4">
                    <h5>email</h5>
                    <p>{{ user.email }}</p>
                  </div>
                  <div class="col-md-4">
                    <h5>Teléfono</h5>
                    <p>{{ user.phone }}</p>
                  </div>
                  <div class="col-md-4">
                    <h5>Username</h5>
                    <p>{{ user.username }}</p>
                  </div>
                </div>
              </div>
              <div class="col-md-2 text-center">
                <h5>Status</h5>
                <b-badge :variant="user.status === 'Activo' ? 'success' : 'danger'">{{ user.status }}</b-badge>

              </div>
              <div class="col-md-2 text-center">
                <h5>Acciones</h5>
                <b-button
                  @click="toggleUserStatus(user)"
                  :variant="user.status === 'Activo' ? 'danger' : 'success'"
                >
                  {{
                    user.status === "Activo" ? "Deshabilitado" : "Activar"
                  }}
                </b-button>
              </div>
            </div>
          </b-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import instance from "../../config/http-client.gateway";
import Swal from "sweetalert2";
export default {
  data() {
    return {
      userData: [],
    };
  },
  methods: {
    async toggleUserStatus(user) {
      const newStatus =
        user.status === "Activo" ? "Deshabilitado" : "Activo";
      try {
        const response = await instance.doPost("/user/actualizar/status", {
          id: `${user.id}`,
          status: newStatus,
        });
        user.status = newStatus; // Actualiza el estado localmente
        Swal.fire({
          icon: "success",
          title: "Éxito",
          text: `El estado del usuario ${user.id} se ha cambiado a ${newStatus}`,
        });
      } catch (error) {
        console.error(error);
        Swal.fire({
          icon: "error",
          title: "Error",
          text: `Hubo un error al cambiar el estado del usuario ${user.id}`,
        });
      }
    },

    async getUsers() {
      try {
        const response = await instance.doGet("/user/");
        this.userData = response.data.data;
      } catch (error) {
        console.error(error);
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "Hubo un error al obtener los usuarios.",
        });
      }
    },
  },
  mounted() {
    this.getUsers();
  },
};
</script>

<style>
.img_historial {
  height: 100px;
  width: 130px;
}

.card_shadow {
  box-shadow: 3px 3px 3px 2px rgba(32, 32, 32, 0.3);
}
</style>
