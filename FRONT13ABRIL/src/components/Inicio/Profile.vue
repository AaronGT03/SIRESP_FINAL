<template>
  <div>
    <NavbarUser />

    <div class="container-fluid mt-5">
      <div class="row mt-5 justify-content-center">
        <div class="col-md-12 mt-5">
          <b-row class="mt-5">
            <b-col cols="12" lg="4" class="mb-4">
              <div class="profile-card">
                <div class="profile-picture">
                  <b-img :src="user.profilePicture" rounded="circle" alt="Profile picture" class="profile-image"></b-img>
                </div>
                <div class="profile-info">
                  <h3>{{ user.names }} {{ user.lastName }}</h3>
                  <p class="text-muted">{{ user.address }}</p>
                  
                </div>
              </div>
            </b-col>
            <b-col cols="12" lg="8">
              <div class="profile-details">
                <h4>Información de Perfil</h4>
                <hr>
                <div class="detail-card">
                  <div class="detail-content">
                    <h5>Fecha de Nacimiento:</h5>
                    <p><b-icon icon="calendar"></b-icon> {{ user.date }}</p>
                  </div>
                </div>
                <div class="detail-card">
                  <div class="detail-content">
                    <h5>Correo Electrónico:</h5>
                    <p><b-icon icon="envelope"></b-icon> {{ user.email }}</p>
                  </div>
                </div>
                <div class="detail-card">
                  <div class="detail-content">
                    <h5>Teléfono:</h5>
                    <p><b-icon icon="phone"></b-icon> {{ user.phone }}</p>
                  </div>
                </div>
                <div class="detail-card">
                  <div class="detail-content">
                    <h5>Usuario:</h5>
                    <p><b-icon icon="person"></b-icon> {{ user.username }}</p>
                  </div>
                </div>
                <div class="detail-card">
                  <div class="detail-content">
                    <router-link to="/historial">
                      <b-button pill variant="outline-primary">Ver Historial de Reservas</b-button>
                    </router-link>
                  </div>
                </div>
              </div>
            </b-col>
          </b-row>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.profile-card {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(177, 16, 16, 0.1);
  padding: 20px;
  text-align: center;
  margin-top: 200px;
}

.profile-picture img {
  width: 250px; /* Aumentar el tamaño de la imagen */
  height: 250px; /* Aumentar el tamaño de la imagen */
  border-radius: 50%;
  margin-bottom: 15px;
  margin: 0 auto; /* Centrar la imagen */
  padding: auto;
 
}

.profile-info h3 {
  margin-bottom: 5px;
}

.profile-details {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 10%;

}

.detail-card {
  margin-bottom: 15px;
}

.detail-content h5 {
  margin-bottom: 5px;
}

@media (max-width: 768px) {
  
  .profile-card {
    padding: 5%;
  }

  .profile-details {
    margin: 0 auto;
    max-width: 90%;
    padding-bottom: 15%;
    margin-bottom: 10%;
  }

  .profile-picture img {
    width: 150px;
    height: 150px;
  }

  .profile-info,
  .detail-content {
    text-align: center;
  }

  .detail-card {
    display: flex;
    flex-direction: column;
    align-items: center;
    
  }
  .profile-card {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(177, 16, 16, 0.1);
  padding: 20px;
  text-align: center;
  margin-top: 0;
  
  
}
}
</style>

<script>
import NavbarUser from "../Inicio/NavbarUser.vue";
import CategoriesNavbar from "../Inicio/CategoriesNavbar.vue";

import instance from "../../config/http-client.gateway";

import Historial from "../../views/Guest/Historial.vue";

export default {
  name: 'Perfil',
  components: {
    NavbarUser,
    CategoriesNavbar
  },
  data() {
    return {
      user: {}
    }
  },
  methods: {
    async getUsers() {
      const token = sessionStorage.getItem('token')
      console.log(token);
      const claims = JSON.parse(atob(token.split('.')[1]));
      const username = claims.sub
      console.log(username)
      const response = await instance.doGet(`/user/username/${username}`)
      const fechaNum = response.data.date;
      let fecha = new Date(fechaNum);
      
      let fechaFormateada = fecha.toLocaleDateString('es-ES');;
      console.log(fechaFormateada);

      this.user = response.data;
      this.user.date = fechaFormateada;
      console.log(response);
    }
  },
  mounted() {
    this.getUsers()
  }
}
</script>
