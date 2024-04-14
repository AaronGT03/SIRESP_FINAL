<template>
  
  <b-container fluid class="airbnb-container">
    <h3>Mis alojamientos </h3>
    <div class="row">

      <div class="col-md-3">
        <div class="filters">

          <b-form-group label="Ubicación" class="airbnb-form-group">
            <b-form-input v-model="searchLocation" @input="filterData" placeholder="Ej: Ciudad, País"></b-form-input>
          </b-form-group>
          <b-form-group label="Precio" class="airbnb-form-group">
            <b-form-input v-model="searchPrice" type="number" @input="filterData"></b-form-input>
          </b-form-group>
          <b-form-group label="Número de huéspedes" class="airbnb-form-group">
            <b-form-input v-model="searchGuests" type="number" @input="filterData"></b-form-input>
          </b-form-group>
          <b-button variant="primary" @click="buscar" class="airbnb-button">Buscar</b-button>

        </div>


      </div>
      
      <div class="col-md-9">
        <div class="row">
          <div class="col-md-12" style="text-align: center;">
            <b-spinner v-if="showSpinner" variant="info" style="width: 70px; height: 70px;"></b-spinner>
          </div>
        </div>
        <div class="row mt-4">

          
          <div class="col-md-4 col-sm-6 mb-4" v-for="(accommodation, index) in accommodations" :key="index">
            
            <div class="card h-100 airbnb-card">
              
              <div class="card-img-top airbnb-card-img-top"
                :style="{ backgroundImage: `url(${JSON.parse(accommodation.images)[0]})` }">
              </div>
              <div class="card-body">
                <h5 class="card-title airbnb-card-title">{{ accommodation.name }}</h5>
                <p class="card-text airbnb-card-text"><strong>Descripción: </strong>{{ accommodation.description }}</p>
                <p class="card-text airbnb-card-text"><strong>Ubicación: </strong>{{ accommodation.location }}</p>
                <p class="card-text airbnb-card-text"><strong>Precio: </strong>{{ accommodation.price }}</p>
                <a class="btn btn-warning" @click="redirectToModificarAlojamiento(accommodation.id)">Modificar</a>



              </div>
            </div>
          </div>
          <div class="col-md-4 col-sm-6 mb-4" v-for="index in ghostCards" :key="'ghost-' + index">
            <div class="card h-100 airbnb-card ghost-card"></div>
          </div>

        </div>

      </div>
      

    </div>
    
  </b-container>
  

 
</template>

<script>
import instance from "../../config/http-client.gateway";
export default {
  data() {
    return {
      searchLocation: '',
      searchPrice: 0,
      searchGuests: 0,
      ghostCards: 3,
      showSpinner: false,
      accommodations: [],
      hosts: [],
      user: [],
      listAcommodations:[]
    }
  },
  mounted() {
    this.spinner();
    

  },
  methods: {
    redirectToModificarAlojamiento(id) {
      this.$router.push({ path: '/modificarAlojamiento', query: { id: id } });
    },

    async getAccommodations(host) {
      const hostId = host.id;
      console.log("HostId: ", hostId);
      try {
        const response = await instance.doGet(`/accommodation/host/${hostId}`);
        this.accommodations = response.data.data;
        this.listAcommodations = response.data.data;
      } catch (error) {
        console.error('Error al obtener los datos de alojamiento:', error);
      }
    },
    async getHosts(user) {
      try {
        const response = await instance.doGet(`/host/user/${user.id}`);
        console.log("Hosts: ");
        console.log(response.data.data);
        this.hosts = response.data.data;
        console.log("Host: ", this.hosts);
        this.getAccommodations(this.hosts);
        
        
      } catch (error) {
        console.error('Error al obtener los datos de los anfitriones:', error);
      }
    },
    async getUsers() {
      const token = sessionStorage.getItem('token')
      console.log(token);
      const claims = JSON.parse(atob(token.split('.')[1]));
      const username = claims.sub
      console.log(username)
      const response = await instance.doGet(`/user/username/${username}`)
      this.user = response.data;
      console.log(response);
      this.getHosts(this.user);

    },
    async buscar() {
    if (this.searchLocation || this.searchPrice || this.searchGuests) {
        console.log("entro ala busqueda")

          this.accommodations = this.listAcommodations.filter(item => {
          const locationMatch = item.location.toLowerCase().includes(this.searchLocation.toLowerCase());
          const departurePrice = this.searchPrice ? item.price <= this.searchPrice : true;
          const guestsMatch = this.searchGuests ? item.numGuest == this.searchGuests : true;
          return locationMatch && departurePrice && guestsMatch;
        });
      } else {
        this.accommodations = filterList;
      }


    },
    spinner() {
      this.showSpinner = true;
      setTimeout(() => {
        this.showSpinner = false;
        this.getUsers();
      }, 1500);
      
    },
    
  }



}
</script>



<style scoped>
.drop-zone {
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px dashed #6B6F76;
  background-color: #f2f2f2;
  width: 100%;
  height: 400px;
  text-align: center;
  color: #666;
  font-size: 18px;
  margin-top: 3%;
}

.airbnb-container {
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
  background-color: #ffffff;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(255, 255, 255, 0.1);
  position: static;
  top: 80px;
}

.filters {
  background-color: #b2b2b2;
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: fixed;
}


@media screen and (max-width: 1120px) {
  .filters {
    position: fixed;
    width: 200px;
  }

}

@media screen and (max-width: 900px) {
  .filters {
    position: static;
    width: auto;
  }

}


.airbnb-form-group {
  margin-bottom: 20px;
}

.airbnb-card {
  border: 10px solid #e4e4e4;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
  margin-bottom: 1.5rem;
  border-radius: 20px;
  width: 400px;
}

.airbnb-card:hover {
  transform: translateY(-5px);
}

.airbnb-card-img-top {
  height: 180px;
  background-size: cover;
  background-position: center;
}

.airbnb-card-title {
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
}

.airbnb-card-text {
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
}

.airbnb-button {
  font-size: 0.9rem;
  padding: 0.5rem 1rem;
}

.ghost-card {
  border: none;
  /* Eliminar el borde para que parezcan "fantasmas" */
  background-color: transparent;
  /* Hacer el fondo transparente */
}
</style>
