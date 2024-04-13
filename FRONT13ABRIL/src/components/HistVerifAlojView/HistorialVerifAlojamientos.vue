<template>
  <div>
    <div class="container-fluid" style="margin-top: 5%;">
      <div class="row mt-5">
        <div class="col-md-12">
          <div class="mt-2">
            <h2 class="text-center">Historial de Alojamientos</h2>
          </div>
          <div class="" v-for="(accommodation, index) in accommodations" :key="index">
            <div class="row justify-content-center mt-4" >
              <div class="col-xl-12" style="display: flex; justify-content: center;">
                <b-card class="card_shadow" style="display: flex; ">
                  <div class="flex-fill" style="font-size: 20px; ">

                    <strong>Nombre: </strong>{{ accommodation.name }}
                    <strong>Price: </strong>{{ accommodation.price }}
                    <strong>Descripción: </strong>{{ accommodation.description }}
                    <strong>Baños: </strong>{{ accommodation.numBathrooms }}
                    <strong>Camas: </strong>{{ accommodation.numBeds }}
                    <strong>Huepedes: </strong>{{ accommodation.numGuest }}
                    <strong>Estatus: </strong>{{ accommodation.availability }}
                    <strong>Localización: </strong>{{ accommodation.location }}
                    
                    <strong>Nombre del Anfitrion: </strong>{{ accommodation.host.user.names }} <br><br>

                    <div style="display: flex; justify-content: center;">
                      <div v-for="(accommodation, index) in JSON.parse(accommodation.images)" :key="index"
                        style="margin-right: 10px;">
                        <img style="width: 300px;height: 200px;" :src="accommodation">
                      </div>
                    </div> <br>
                  </div>
                </b-card>
              </div>
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
import { ValidationProvider } from "vee-validate";
import instance from "../../config/http-client.gateway";


export default {
  name: 'AboutView',
  components: {
    NavbarUser,
    CategoriesNavbar,
    ValidationProvider,
  },
  data() {
    return {
      accommodations: [],
      review: "",
      rating: 0,
    };
  },
  methods: {
    async getAccommodations() {
      const response = await instance.doGet('/accommodation/');
      this.accommodations = response.data.data;
    },
  },
  mounted() {
    this.getAccommodations()

  }

}
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