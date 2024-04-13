<template>
  <div>
    <b-row>
      <b-col cols="12">
        <NavbarUser />
      </b-col>
    </b-row>

    <div class="container-fluid" style="margin-top: 10%;">
      <b-modal
        v-model="showModal2"
        title="Cancelar Reserva"
        hide-footer
        size="lg"
        header-bg-variant="danger"
        header-text-variant="light"
      >
        <b-form @submit.prevent="onSubmit2" class="modal-form">
  
          <b-form-group label="Motivo de la cancelación">
            <b-form-textarea v-model="cancellationReason" type="text" v-validate="'required|noContarEspacios:10'"  name="cancellationReason" />
            <span class="text-danger">{{ errors.first("cancellationReason") }}</span>
          </b-form-group>
  
          <!-- Botón Enviar -->
          <div class="text-center">
            <b-button style="margin-top: 5%" @click="onSubmit2" variant="danger">Aceptar Cancelación</b-button>
          </div>
        </b-form>
      </b-modal>


      <b-modal v-model="showModal" :modal-class="['custom-modal']" title="Reseña" hide-footer size="lg"
        header-bg-variant="primary" header-text-variant="light">
        <b-form @submit.prevent="onSubmit" class="modal-form">

          <b-form-group label="Valoración">
            <b-form-rating v-model="rating" variant="warning" v-validate="'required|min_value:1|max_value:5'"
              name="rating" style="font-size:xx-large;"></b-form-rating>
            <span class="text-danger">{{ errors.first("rating") }}</span>
          </b-form-group>

          <b-form-group label="Comentarios">
            <b-form-textarea v-model="review" type="text" v-validate="'required|min:10'" name="review" />
            <span class="text-danger">{{ errors.first("review") }}</span>
          </b-form-group>

          <div class="text-center">
            <b-button style="margin-top: 5%" variant="primary" @click="onSubmit">Enviar</b-button>
          </div>
        </b-form>
      </b-modal>

      <div class="row mt-5">
        <div class="col-md-12">
          <div class="mt-2">
            <h2 class="text-center">Historial de Reservas</h2>
          </div>
        <div v-for="(booking, index) in bookings">
          <div class="row justify-content-center mt-4">
            <div class="col-xl-12">
              <b-card class="card_shadow">
                <div class="row">
                  <div class="col-md-3 text-center">
                    <img class="img_historial" :src=JSON.parse(booking.accommodation.images)[0] alt="">
                  </div>
                  <div class="col-md-4 text-center">
                    <div class="row">
                      <div class="col-md-4">
                        <h5>Fecha de llegada</h5>
                        <p>{{booking.arrivalDate}}</p>
                      </div>
                      <div class="col-md-4">
                        <h5>Fecha de salida</h5>
                        <p>{{booking.departureDate}}</p>
                      </div>
                      <div class="col-md-3">
                        <h5>Número de Huespedes</h5>
                        <p>{{booking.numGuests}}</p>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-2 text-center">
                    <h5>Status</h5>
                    <b-badge variant="secondary">{{booking.stateBooking}}</b-badge>
                  </div>
                  <div class="col-md-2 text-center">
                    <h5>Acciones</h5>
                    <b-button @click="showReview(booking.accommodation.id)" v-if="getButton(booking.accommodation.id,booking.user.id)==false" variant="secondary" style="margin-right: 10px">Agregar Reseña</b-button>
                    <b-button @click="showCancellation(booking.id)" v-if="getButtonCancellation(booking.id)==false"  variant="danger">Enviar  Cancelacion</b-button>
                  </div>
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
import Swal from 'sweetalert2';
const token = sessionStorage.getItem("token")

export default {
  name: 'AboutView',
  components: {
    NavbarUser,
    CategoriesNavbar,
    ValidationProvider,
  },
  data() {
    return {
      showModal: false,
      showModal2: false,
      review: "",
      rating: 0, 
      idSelected:0,
      idUser:"",
      showConfirmModal: false,
      cancellationReason: "",
      bookings:[],
      listReviews:[],
      listCancellations:[]
    };
  },
  methods: {
    onSubmit() {
      this.$validator.validateAll().then((valid) => {
        if (!valid) {
          console.log('Falta completar campos');
          alert('Debes completar todos los campos');
        } else {
          console.log(this.review,this.rating,this.idSelected,this.idUser)
          const response = instance.doPost("/review/",{
            description:this.review,
            average:this.rating,
            accommodation:{id:this.idSelected},
            user:{id:this.idUser}
          }).then(result=>{
            this.showModal = false;
            if (result.status=200) {
            Swal.fire({
              title: 'Reseña enviada',
              icon: 'success',
            }).then(()=>{
              window.location.reload()
            })
          } else {
            Swal.fire({
              title: 'Error en el envio',
              icon: 'error',
            })
          }
          })
         
      
         
         
        }
      });
    },

    onSubmit2() {
        this.$validator.validateAll().then((valid) => {
          if (valid) {
            Swal.fire({
              title: "¿Seguro que quieres cancelar?",
              text: "Ya no podras cambiar la accion",
              icon: "warning",
              showCancelButton: true,
              confirmButtonColor: "#3085d6",
              cancelButtonColor: "#d33",
              confirmButtonText: "Yes, delete it!"
            }).then(async (result) => {
              /* Read more about isConfirmed, isDenied below */
              if (result.isConfirmed) {
                 await instance.doPost("/cancellation/",{
                  reason:this.cancellationReason,
                  booking:{id:this.idSelected}
                })
                 await instance.doPost("/booking/actualizar/state/",{
                  id:`${this.idSelected}`,
                  state:"Cancelado"
                })
                  Swal.fire({
                    title: "Deleted!",
                    text: "Your file has been deleted.",
                    icon: "success"
                  }).then(()=>{
                    window.location.reload()
                  })
                  
                }
            });
          } else {
            console.log('Falta completar campos');
            alert('Debes completar todos los campos');
          }
        });
      },
      confirmCancellation() {
        this.showModal2 = false;
        this.showConfirmModal = false;
        window.location.reload();
      },
      async getbooking(){
        const claims = JSON.parse(atob(token.split('.')[1]));

        const response = await  instance.doGet(`/user/username/${claims.sub}`)
        this.idUser = response.data.id;
    
        const response2 = await instance.doGet(`/booking/user/${this.idUser}`)
        this.bookings = response2.data.data;
        console.log(response2)
      },
       showReview(id){
          this.idSelected=id;
          this.showModal = true
      },
      showCancellation(id){
          this.idSelected=id;
          this.showModal2 = true
      },
      async getReview(){
          const response = await instance.doGet("/review/")
          this.listReviews = response.data.data;
      
          
      },
      async getCancellations(){
          const response = await instance.doGet("/cancellation/")
          this.listCancellations = response.data.data;
          console.log("lista de Cancelaciones")
          console.log(this.listCancellations)
          
      },
       getButton(id,idUser){
        let value = false
        for (let index = 0; index <  this.listReviews.length; index++) {
         
          if(this.listReviews[index].accommodation.id==id&&this.listReviews[index].user.id==idUser){
         
            value=true
          }
          
        }
       
        return   value; 
      },
      getButtonCancellation(id){
        let value = false
        for (let index = 0; index <  this.listCancellations.length; index++) {
          
          if(this.listCancellations[index].booking.id==id){
            console.log("entro el if")
            value=true
          }
          
        }
       
        return   value; 
      }
  },
  mounted(){
    this.getbooking()
    this.getReview()
    this.getCancellations()
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