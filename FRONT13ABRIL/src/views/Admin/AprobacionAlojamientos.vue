<template>
  <div>
    <NavbarUser />  
    <div class="divMedia" >
      <div class="row mt-5">
        <div class="col-md-12">
          <div class="mt-2">
            <h4 class="text-center">Aprobación de Alojamientos</h4>
          </div>
          <div v-for="(accommodation, index) in accommodations" :key="index" class="mt-4">
            <div class="col-lg-8 mx-auto">
              <b-card class="card_shadow" style="display: flex;">
                <div class="flex-fill" style="font-size: 20px;">
                  <strong>Nombre: </strong>{{accommodation.name}} 
                  <strong>Price: </strong>{{accommodation.price}} 
                  <strong>Descripción: </strong>{{accommodation.description}} 
                  <strong>Baños: </strong>{{accommodation.numBathrooms}} 
                  <strong>Camas: </strong>{{accommodation.numBeds}} 
                  <strong>Huepedes: </strong>{{accommodation.numGuest}} 
                  <strong>Localización: </strong>{{accommodation.location}} 
                  <strong>Nombre del Anfitrion: </strong>{{accommodation.host.user.names}} <br><br>
                  <div class="d-flex justify-content-center flex-wrap">
                    <div v-for="(image, index) in JSON.parse(accommodation.images)" :key="index" class="m-2">
                      <img :src="image" class="img-fluid img_historial" alt="Accommodation Image">
                    </div>
                  </div>
                  <br>
                  <div class="text-center">
                    <b-button variant="success" style="margin: 5px;" @click="updateStatusAprobar(accommodation)">APROBAR</b-button>
                    <b-button variant="danger" style="margin: 5px;" @click="updateStatusRechazar(accommodation)">RECHAZAR</b-button>
                  </div>
                </div>
              </b-card>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavbarUser from "../../components/Inicio/NavbarUser.vue";
import { ValidationProvider } from "vee-validate";
import instance from "../../config/http-client.gateway";
import Swal from "sweetalert2";

export default {
  name: 'AboutView',
  components: {
    NavbarUser,
    ValidationProvider,
  },
  data() {
    return {
      accommodations:[],
    };
  },
  methods: {
    async getAccommodations(){
      const response = await instance.doGet('/accommodation/');
      this.accommodations = response.data.data.filter(item => item.availability.includes("Por Aprobar"));
    },
    async updateStatusAprobar(accommodation){
      const motivo = await Swal.fire({
        title: 'Escibe un comentario para el Anfitrion!',
        input: "text",
        icon: 'info',
        showCancelButton: true,
        confirmButtonText: 'Aceptar'
      });
      if (motivo.isConfirmed) {
        await instance.doPost("/accommodation/status/", {
          id: accommodation.id,
          status: "Habilitado"
        });
        await instance.doPost("http://localhost:8080/api-sirep/email/", {
          transmitter: "202tn@utez.edu.mx",
          receptor: "vvaldes190@gmail.com",
          title: `Validacion de solicitud respecto a el Alojamiento: ${accommodation.name}`,
          text: `Motivo: ${motivo.value}`
        });
        Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Usuario actualizado exitosamente."
        });
        this.getAccommodations();
      }
    },
    async updateStatusRechazar(accommodation){
      const motivo = await Swal.fire({
        title: 'Escibe el motivo del rechazo!',
        input: "text",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Aceptar'
      });
      if (motivo.isConfirmed) {
        await instance.doPost("/accommodation/status/", {
          id: accommodation.id,
          status: "Inhabilitado"
        });
        await instance.doPost("http://localhost:8080/api-sirep/email/", {
          transmitter: "202tn@utez.edu.mx",
          receptor: accommodation.host.user.email,
          title: `Hola ${accommodation.host.user.names} seRechazo de solicitud respecto al alojamiento ${accommodation.name}`,
          text: `Motivo : ${motivo.value}`,
          body: accommodation.host.user.names
        });
        Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Usuario ha sido rechazado."
        });
        this.getAccommodations();
      }
    }
  },
  mounted(){
    this.getAccommodations();
  }
}
</script>

<style>
.img_historial {
  max-height: 200px;
  max-width: 300px;
}

.card_shadow {
  box-shadow: 3px 3px 3px 2px rgba(32, 32, 32, 0.3);
}
.divMedia {
  margin-top: 5%;

}
@media screen and (max-width: 768px){
  .divMedia {
    margin-top: 30%;
    margin-bottom: 20%;
  }
  
}

</style>
