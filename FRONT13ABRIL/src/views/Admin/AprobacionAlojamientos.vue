<template>
  <div>
    <NavbarUser />  
    <div class="container-fluid" style="margin-top: 5%;">
      <div class="row mt-5">
        <div class="col-md-12">
          <div class="mt-2">
            <h2 class="text-center">Aprobación de Alojamientos</h2>
          </div>
          <div class="" v-for="(accommodation, index) in accommodations" :key="index">
            <div class="row justify-content-center mt-4">
            <div class="col-xl-12">
              <b-card class="card_shadow" style="display: flex;">
                <div  class="flex-fill" style="font-size: 20px;">
                  
                  <strong>Nombre: </strong>{{accommodation.name}} 
                  <strong>Price: </strong>{{accommodation.price}} 
                  <strong>Descripción: </strong>{{accommodation.description}} 
                  <strong>Baños: </strong>{{accommodation.numBathrooms}} 
                  <strong>Camas: </strong>{{accommodation.numBeds}} 
                  <strong>Huepedes: </strong>{{accommodation.numGuest}} 
                  <strong>Localización: </strong>{{accommodation.location}} 
                  <strong>Nombre del Anfitrion: </strong>{{accommodation.host.user.names}} <br><br>
               
                  <div style="display: flex; justify-content: center;">
                        <div v-for="(accommodation, index) in JSON.parse(accommodation.images)" :key="index" style="margin-right: 10px;">
                            <img  style="width: 300px;height: 200px;" :src="accommodation">
                        </div>
                  </div> <br>
                  <b-button variant="success" style="margin: 5px;" @click="updateStatusAprobar(accommodation)">APROBAR</b-button>
                  <b-button variant="danger" style="margin: 5px;" @click="updateStatusRechazar(accommodation)">RECHAZAR</b-button>

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
import Swal from "sweetalert2";
import axios from "axios";


export default {
  name: 'AboutView',
  components: {
    NavbarUser,
    CategoriesNavbar,
    ValidationProvider,
  },
  data() {
    return {
      accommodations:[],
      showModal: false,
      showModal2: false,
      review: "",
      rating: 0, 
      showConfirmModal: false,
      cancellationReason: ""
    };
  },
  methods: {
   

   
      confirmCancellation() {
        this.showModal2 = false;
        this.showConfirmModal = false;
        window.location.reload();
      },

    async getAccommodations(){
      const response = await instance.doGet('/accommodation/');
      const listFiter = response.data.data;
      console.log(listFiter)
      this.accommodations  = listFiter.filter(item=>
        item.availability.toLowerCase().includes("pendiente")
      )
      console.log("lista")
      console.log(this.accommodations)
      
    } ,
    async updateStatusAprobar(accommodation){

      Swal.fire({
        title: 'Escibe un comentario para el Anfitrion!',
        input:"text",
        icon: 'info',
        showCancelButton: true,
        confirmButtonText: 'Aceptar'
      }).then( async (result) => {
      if (result.isConfirmed) {
        Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Usuario actualizado exitosamente.",
        });


        const response2 = await instance.doPost("/accommodation/status/",{
        id:`${accommodation.id}`,
        status:"Habilitado"

      })
      
      
      

        const motivo = result.value;
        const response = await instance.doPost("http://localhost:8080/api-sirep/email/",{
          transmitter:"202tn@utez.edu.mx",
          receptor:"vvaldes190@gmail.com",
          title:`Validacion  de solicitud respecto a el Alojamiento: ${accommodation.name}`,
          text:`Motivo: ${motivo}`
        })
        
       
      } 
    });

      

      
    },
      async updateStatusRechazar(accommodation){
        console.log("accomodation")
        console.log(accommodation)
        Swal.fire({
        title: 'Escibe el motivo del rechazo!',
        input:"text",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Aceptar'
      }).then( async (result) => {
      if (result.isConfirmed) {
        Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Usuario ha sido rechazado.",
        });
        const response2 = await instance.doPost("/accommodation/status/",{
          id:`${accommodation.id}`,
          status:"Inhabilitado"
         })
        const motivo = result.value;
        const response = await instance.doPost("http://localhost:8080/api-sirep/email/",{
          transmitter:"202tn@utez.edu.mx",
          receptor:`${accommodation.host.user.email}`,
          title:`
          Hola ${accommodation.host.user.names}
          seRechazo de solicitud respecto al alojamiento ${accommodation.name}`,
          text:`Motivo : ${motivo}`,
          body:accommodation.host.user.names
        })
       
      } 
    });
      }
  },
  mounted(){
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