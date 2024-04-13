<template>
  <b-container fluid class="airbnb-container">
    <div class="row">
      <div class="col-md-3">
        <div class="filters">
          <b-form-group label="Ubicación" class="airbnb-form-group">
            <b-form-input v-model="searchLocation" name="ubicación" @input="filterData" placeholder="Ej: Ciudad, País"
              v-validate="'alpha_spaces'"></b-form-input>
            <span class="text-danger">{{ errors.first("ubicación") }}</span>
          </b-form-group>

          <b-form-group label="Precio" class="airbnb-form-group">
            <b-form-input v-model="searchPrice" name="precio" type="number" @input="filterData"
              v-validate="'numeric|min_value:0'"></b-form-input>
            <span class="text-danger">{{ errors.first("precio") }}</span>
          </b-form-group>
          <b-form-group label="Número de huéspedes" class="airbnb-form-group">
            <b-form-input v-model="searchGuests" name="numGuest" type="number" @input="filterData"
              v-validate="'integer|min_value:0'"></b-form-input>
            <span class="text-danger">{{ errors.first("numGuest") }}</span>
          </b-form-group>
          <b-button variant="primary" @click="buscar" class="airbnb-button">Buscar</b-button>

          <div id="dropZone" class="drop-zone" @drop="handleDrop" @dragover="handleDragOver">
            <h5>Arrastra aquí para ver más</h5>
          </div>
        </div>
      </div>
      <div class="col-md-9">
        <div class="d-flex justify-content-center align-items-center">
                <b-spinner v-if="showSpinner" variant="info"
                  style="width: 8rem; height: 8rem; margin-top: 15%"></b-spinner>
              </div>
        <div class="row mt-4">
          <div class="col-md-4 col-sm-6 mb-4" v-for="(accommodation, index) in accommodations" :key="index"
            draggable="true" @dragstart="handleDragStart($event, accommodation)">
            <div class="card h-100 airbnb-card">
              <div class="card-img-top airbnb-card-img-top" :style="{
                backgroundImage: `url(${JSON.parse(accommodation.images)[0]
                  })`,
              }"></div>
              
              <div class="card-body">
                <h5 class="card-title airbnb-card-title">
                  {{ accommodation.name }}
                </h5>
                <p class="card-text airbnb-card-text">
                  <strong>Descripción: </strong>{{ accommodation.description }}
                </p>
                <p class="card-text airbnb-card-text">
                  <strong>Ubicación: </strong>{{ accommodation.location }}
                </p>
                <p class="card-text airbnb-card-text">
                  <strong>Precio: </strong>{{ accommodation.price }}
                </p>
                <p class="card-text airbnb-card-text">
                  <strong>Huespedes: </strong>{{ accommodation.numGuest }}
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-4 col-sm-6 mb-4" v-for="index in ghostCards" :key="'ghost-' + index">
            <!-- Contenido de la card fantasma -->
            <div class="card h-100 airbnb-card ghost-card"></div>
          </div>
        </div>
      </div>
    </div>
  </b-container>
</template>

<script>
import instance from "../../config/http-client.gateway";
import { ValidationProvider } from "vee-validate";
import Swal from "sweetalert2";
export default {
  components: {
    ValidationProvider,
  },
  data() {
    return {
      searchLocation: "",
      searchPrice: 0,
      searchGuests: 0,
      accommodations: [],
      ghostCards: 3,
      showSpinner: false,
    };
  },
  mounted() {
    this.spinner();
  },
  methods: {
    handleDragStart(event, myDataa) {
      event.dataTransfer.setData("text/plain", JSON.stringify(myDataa));
    },
    handleDrop(event) {
      event.preventDefault();
      const myDataa = JSON.parse(event.dataTransfer.getData("text/plain"));
      this.$router.push({
        path: "/infoalojamiento",
        query: { id: myDataa.id },
      });
    },
    handleDragOver(event) {
      event.preventDefault();
    },
    async getAccommodations() {
      try {
        const response = await instance.doGet("/accommodation/");
        const listAcommodations =response.data.data;
        this.accommodations = listAcommodations.filter(accommodation => accommodation.availability.includes("Habilitado"));
      } catch (error) {
        console.error("Error al obtener los datos de alojamiento:", error);
      }
    },
    async filterCategory(evento) {
      try {
        if (evento === 0) {
          const response = await instance.doGet("/accommodation/");
          const listAcommodations =response.data.data;
        this.accommodations = listAcommodations.filter(accommodation => accommodation.availability.includes("Habilitado"));
        } else {
          const response = await instance.doGet(
            `/accommodation/category/${evento}`
          );
          console.log(response.data.data);
          const listAcommodations =response.data.data;
        this.accommodations = listAcommodations.filter(accommodation => accommodation.availability.includes("Habilitado"));
        }
      } catch (error) {
        console.error("Error al obtener los datos de alojamiento:", error);
      }
    },
    spinner() {
      this.showSpinner = true;
      setTimeout(() => {
        this.showSpinner = false;
        this.getAccommodations();
      }, 1500);
    },
    async buscar() {
      const isValid = await this.$validator.validateAll();

      if (!isValid) {
        return;
      }

      const response = await instance.doGet("/accommodation/");
      const filterList = response.data.data;
      console.log(response.data.data);

      if (
        this.searchLocation ||
        this.searchPrice > 0 ||
        this.searchGuests > 0
      ) {
        this.accommodations = filterList.filter((item) => {
  const availabilityMatch = item.availability.includes("Habilitado");
  const locationMatch = item.location.toLowerCase().includes(this.searchLocation.toLowerCase());
  const priceMatch = this.searchPrice > 0 ? item.price <= this.searchPrice : true;
  const guestsMatch = this.searchGuests > 0 ? item.numGuest == this.searchGuests : true;

  return availabilityMatch && locationMatch && priceMatch && guestsMatch;
});

     
      } else {
        this.accommodations = filterList;
      }


    },
  },
};
</script>

<style scoped>
.drop-zone {
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px dashed #6b6f76;
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
  background-color: #e4e4e4;
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
