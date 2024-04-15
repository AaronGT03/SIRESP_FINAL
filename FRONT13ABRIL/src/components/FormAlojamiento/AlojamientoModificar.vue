<template>
  <div id="formulario">
    <b-container>
      <b-row class="justify-content-center">
        <b-col cols="12" lg="8">
          <b-card
            style="text-align: center;"
            title="Modificar Alojamiento"
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
                      <b-icon
                        icon="file-earmark-excel"
                        font-scale="1"
                        @click="deleteImages(index)"
                      >
                      </b-icon>
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
const token = sessionStorage.getItem("token");
import Swal from "sweetalert2";
import {decrypt2 } from "../../config/aes";


export default {
  components: {
    ValidationProvider,
  },
  data() {
    return {
      newAccommodation: {
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
        idHost: "",
        category: "",
      },
      accommodation: [],
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
        console.log("vista de listaImages");
        console.log(this.listImages);
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

      for (let i = 0; i < this.accommodation.images.length; i++) {
        if (typeof this.accommodation.images[i] === "string") {
          this.listImagesurl.push(this.accommodation.images[i]);
        }
      }
      const listString = JSON.stringify(this.listImagesurl);
      const listExtrasString = JSON.stringify(this.accommodation.extras);

      console.log("id enviado");
      console.log(this.accommodation.user);

      const response = await instance.doPost("/accommodation/actualizar/", {
        id: this.accommodation.id,
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
        host: { id: this.accommodation.idHost },
        category: {
          id: this.accommodation.category,
        },
      });
      console.log(response.data);
      Swal.fire({
        icon: "success",
        title: "Éxito",
        text: "Alojamiento actualizado correctamente",
      });
      window.location.href = "/userhome";
    },
    async getAccommodation() {
      const response = await instance.doGet(`/accommodation/${this.id}`);

      this.accommodation = response.data.data;
      this.accommodation.id = response.data.data.id;
      this.accommodation.name = response.data.data.name;
      this.accommodation.Description = response.data.data.description;
      this.accommodation.location = response.data.data.location;
      this.accommodation.price = response.data.data.price;
      this.accommodation.NumGuest = response.data.data.numGuest;
      this.accommodation.NumRooms = response.data.data.numRooms;
      this.accommodation.NumBeds = response.data.data.numBeds;
      this.accommodation.NumBathrooms = response.data.data.numBathrooms;
      this.listImagesView = JSON.parse(response.data.data.images);
      this.accommodation.images = JSON.parse(response.data.data.images);
      this.accommodation.extras = JSON.parse(
        response.data.data.additionalfeatures
      );
      this.accommodation.availability = response.data.data.availability;
      this.accommodation.idHost = response.data.data.host.id;
      this.accommodation.category = response.data.data.category.id;
      console.log("id traido ");
      console.log(response.data.data.host.id);
    },
    putImages(event) {
      if (this.listImagesView.length < 5) {
        const files = event.target.files;
        for (let i = 0; i < files.length; i++) {
          const file = files[i];
          if (file != null && files[i].size < 2000000) {
          this.listImages.push(file);
          const reader = new FileReader();
          reader.readAsDataURL(file);
          reader.onload = () => {
            this.listImagesView.push(reader.result);
          };
        } else {
          Swal.fire({
            icon: "error",
            title: "La imagen erronea",
            text: "La imagen no debe de ser mayor a 2mb",
          });
          
        }
        }
      } else {
        Swal.fire({
          title: "Error",
          icon: "info",
          text: "Solo se permiten 5 Imaganes",
        });
      }
    },
    deleteImages(index) {
      this.accommodation.images.splice(index, 1);
      this.listImagesView.splice(index, 1);
      this.listImages.slice(index, 1);
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
    const id = this.$route.query.id;
    const newId = decrypt2(id);
    this.id = newId;
    this.getUser();
    this.getCategory();
    this.getAccommodation();
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
