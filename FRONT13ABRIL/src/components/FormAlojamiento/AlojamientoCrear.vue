<template>
  <div id="formulario">
    <b-container>
      <b-row class="justify-content-center">
        <b-col cols="12" lg="8">
          <b-card
            title="Creación de Alojamiento"
            class="shadow p-3 mb-5 bg-white rounded"
          >
            <b-card-body>
              <b-form @submit.prevent="onSubmit">
                <b-row>
                  <b-col cols="6">
                    <b-form-group label="Nombre">
                      <b-form-input
                        v-validate="'required|min:3'"
                        name="name"
                        v-model="accommodation.name"
                        type="text"
                      />
                      <span class="text-danger">{{
                        errors.first("name")
                      }}</span>
                    </b-form-group>
                  </b-col>
                  <b-col cols="6">
                    <b-form-group label="Descripción">
                      <b-form-textarea
                        v-validate="'required|min:10'"
                        name="description"
                        v-model="accommodation.Description"
                      />
                      <span class="text-danger">{{
                        errors.first("description")
                      }}</span>
                    </b-form-group>
                  </b-col>
                </b-row>

                <b-row>
                  <b-col cols="6">
                    <b-form-group label="Ubicación">
                      <b-form-input
                        v-validate="'required'"
                        name="location"
                        v-model="accommodation.location"
                        type="text"
                      />
                      <span class="text-danger">{{
                        errors.first("location")
                      }}</span>
                    </b-form-group>
                  </b-col>
                  <b-col cols="6">
                    <b-form-group label="Precio">
                      <b-form-input
                        v-validate="'required|numeric|min_value:1'"
                        name="price"
                        v-model="accommodation.price"
                        type="number"
                      />
                      <span class="text-danger">{{
                        errors.first("price")
                      }}</span>
                    </b-form-group>
                  </b-col>
                </b-row>

                <b-row>
                  <b-col cols="6">
                    <b-form-group label="Recamaras">
                      <b-form-input
                        v-validate="'required|numeric|min_value:1'"
                        name="NumRooms"
                        v-model="accommodation.NumRooms"
                        type="number"
                      />
                      <span class="text-danger">{{
                        errors.first("NumGuest")
                      }}</span>
                    </b-form-group>
                  </b-col>
                  <b-col cols="6">
                    <b-form-group label="Huespedes">
                      <b-form-input
                        v-validate="'required|numeric|min_value:1'"
                        name="NumGuest"
                        v-model="accommodation.NumGuest"
                        type="number"
                      />
                      <span class="text-danger">{{
                        errors.first("NumGuest")
                      }}</span>
                    </b-form-group>
                  </b-col>
                </b-row>

                <b-row>
                  <b-col cols="6">
                    <b-form-group label="Camas">
                      <b-form-input
                        v-validate="'required|numeric|min_value:1'"
                        name="NumBeds"
                        v-model="accommodation.NumBeds"
                        type="number"
                      />
                      <span class="text-danger">{{
                        errors.first("NumBeds")
                      }}</span>
                    </b-form-group>
                  </b-col>

                  <b-col cols="6">
                    <b-form-group label="Baños">
                      <b-form-input
                        v-validate="'required|numeric|min_value:1'"
                        name="NumBathrooms"
                        v-model="accommodation.NumBathrooms"
                        type="number"
                      />
                      <span class="text-danger">{{
                        errors.first("NumBathrooms")
                      }}</span>
                    </b-form-group>
                  </b-col>
                  <b-col cols="12">
                    <b-form-group label="Categoria">
                      <b-form-select
                        v-validate="'required'"
                        v-model="accommodation.category"
                        :options="categoryOptions"
                      />
                      <span class="text-danger">{{
                        errors.first("category")
                      }}</span>
                    </b-form-group>
                  </b-col>
                </b-row>

                <b-row>
                  <b-col cols="12">
                    <b-form-group label="Extras">
                      <b-form-checkbox-group v-model="accommodation.extras">
                        <b-form-checkbox value="wifi">Wifi</b-form-checkbox>
                        <b-form-checkbox value="tv">TV</b-form-checkbox>
                        <b-form-checkbox value="kitchen"
                          >Cocina</b-form-checkbox
                        >
                        <b-form-checkbox value="Alberca"
                          >Alberca</b-form-checkbox
                        >
                        <b-form-checkbox value="Estacionamiento"
                          >Estacionamiento</b-form-checkbox
                        >
                      </b-form-checkbox-group>
                    </b-form-group>
                  </b-col>
                </b-row>

                <b-form-group label="imagenes">
                  <b-form-file
                    name="images"
                    v-model="accommodation.images"
                    accept="image/*"
                    multiple
                    @change="putImages"
                  >
                  </b-form-file>
                  <div class="image-grid">
                    <div
                      v-for="(imageData, index) in listImagesView"
                      :key="index"
                    >
                      <img
                        style="width: 100px; height: 100px"
                        :src="imageData"
                        alt="Selected Image"
                      />
                    </div>
                  </div>
                </b-form-group>

                <b-row class="justify-content-center">
                  <b-col cols="12" lg="8">
                    <b-button
                      type="submit"
                      variant="primary"
                      class="mt-3 d-block mx-auto"
                    >
                      Enviar
                    </b-button>
                  </b-col>
                </b-row>
              </b-form>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import { ValidationProvider } from "vee-validate";
import axios from "axios";
import instance from "../../config/http-client.gateway";
import Swal from "sweetalert2";
const token = sessionStorage.getItem("token");

export default {
  components: {
    ValidationProvider,
  },
  data() {
    return {
      accommodation: {
        id: 0,
        name: "",
        Description: "",
        location: "",
        price: 0,
        NumGuest: 0,
        NumBeds: 0,
        NumBathrooms: 0,
        NumRooms: 0,
        images: [],
        extras: [],
        availability: "Pendiente",
        user: "",
        category: "",
      },
      idHost: 0,
      listCategory: [],
      listImages: [],
      listImagesView: [],
      listImagesurl: [],
    };
  },
  methods: {
    async onSubmit() {
      try {
        await this.validateForm();
      } catch (error) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Por favor completa todos los campos obligatorios.",
        });
        return;
      }
      for (let i = 0; i < this.listImages.length; i++) {
        try {
          const formData = new FormData();
          formData.append("profilePicture", this.listImages[i]);

          const response2 = await axios.post(
            "http://localhost:8080/api-sirep/accommodation/upload",
            formData,
            {}
          );
          this.listImagesurl.push(response2.data);
        } catch (error) {
          console.log(error);
        }
      }
      const listString = JSON.stringify(this.listImagesurl);
      const listExtrasString = JSON.stringify(this.accommodation.extras);

      const response = await instance.doPost("/accommodation/", {
        name: this.accommodation.name,
        description: this.accommodation.Description,
        location: this.accommodation.location,
        price: this.accommodation.price,
        numGuest: this.accommodation.NumGuest,
        numRooms: this.accommodation.NumRooms,
        numBeds: this.accommodation.NumBeds,
        numBathrooms: this.accommodation.NumBathrooms,
        images: listString,
        additionalfeatures: listExtrasString,
        availability: this.accommodation.availability,
        host: { id: this.accommodation.user },
        category: {
          id: this.accommodation.category,
        },
      });
      Swal.fire({
        icon: "success",
        title: "Alojamiento creado",
        text: "El alojamiento ha sido creado exitosamente",
      });
      window.location.href = "/userhome";
    },
    putImages(event) {
      const files = event.target.files;
      for (let i = 0; i < files.length; i++) {
        const file = files[i];
        if (file) {
          this.listImages.push(file);
          const reader = new FileReader();
          reader.readAsDataURL(file);
          reader.onload = () => {
            this.listImagesView.push(reader.result);
          };
        }
      }
    },
    validateForm() {
      return new Promise((resolve, reject) => {
        this.$validator.validateAll().then((result) => {
          if (result) {
            resolve(true); // Todos los campos son válidos
          } else {
            reject(false); // Al menos un campo es inválido
          }
        });
      });
    },
    checkReceptionTime(value) {
      return value > this.accommodation.checkoutTime;
    },
    checkCheckoutTime(value) {
      return value < this.accommodation.receptionTime;
    },
    validLocation(value) {
      return value !== "Invalid";
    },

    async getUser() {
      const claims = JSON.parse(atob(token.split(".")[1]));
      const response = await instance.doGet(`/user/username/${claims.sub}`);
      this.accommodation.user = response.data.id;
      this.getHOst();
    },

    async getHOst() {
      console.log(this.accommodation.user);
      const response = await instance.doGet(
        `/host/user/${this.accommodation.user}`
      );
      this.accommodation.user = response.data.data.id;
    },
    async getCategory() {
      const response = await instance.doGet(`/category/`);
      this.listCategory = response.data.data;
    },
  },
  mounted() {
    this.getUser();
    this.getCategory();
  },
  computed: {
    categoryOptions() {
      return this.listCategory.map((category) => ({
        value: category.id,
        text: category.name,
      }));
    },
  },
};
</script>
<style>
#formulario {
  background-color: aliceblue;
}

.b-card {
  border-radius: 15px;
}

.text-danger {
  font-size: 14px;
}

.b-form-group {
  margin-bottom: 20px;
}

@media (max-width: 991px) {
  .b-card {
    padding: 20px;
  }

  .b-form-group {
    margin-bottom: 15px;
  }
}
.image-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-auto-rows: minmax(20px, auto);
  gap: 2px; /* Espacio entre las imágenes */
}
</style>
