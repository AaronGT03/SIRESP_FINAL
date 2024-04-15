<template>
    <div>
        <b-row>
            <b-col cols="12">

                <NavbarUser />
            </b-col>
        </b-row>

        <div class="container-fluid" style="margin-top: 10%;">
            <div class="row mt-5">
                <div class="col-md-12">
                    <div class="mt-5">
                        <h2 class="text-center">Historial de Reservas</h2>
                    </div>
                    <div class="row justify-content-center mt-4">
                        <div class="col-xl-12">
                            <b-card class="card_shadow mt-3" v-for="reserva in booking" :key="reserva.id">
                                <div class="row">
                                    <div class="col-md-2 text-center">
                                        <h4>{{  reserva.accommodation.name }}</h4>
                                    </div>
                                    <div class="col-md-8 text-center">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <h4>Fecha de llegada</h4>
                                                <p>{{  formatDate(reserva.arrivalDate) }}</p>
                                            </div>
                                            <div class="col-md-3">
                                                <h4>Fecha de Salida</h4>
                                                <p>{{ formatDate(reserva.departureDate) }}</p>
                                            </div>
                                            <div class="col-md-3">
                                                <h4>Número de Huespedes</h4>
                                                <p>{{ reserva.numGuests }}</p>
                                            </div>
                                            <div class="col-md-3 text-center">
                                                <h4 class="text-center">Estado de la Reserva</h4>
                                                
                                                <b-badge :variant="reserva.stateBooking === 'Confirmado' ? 'success' : 'warning'">{{
                                                    reserva.stateBooking }}</b-badge>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="col-md-2 text-center">
                                        <div class="row">
                                            <div class="col-md-4 text-center mt-2">
                                                <b-button @click=cancelModal(reserva.id) variant="danger" v-if="reserva.stateBooking !== 'cancelado'">Cancelar Reserva</b-button>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </b-card>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
            <b-form-textarea v-model="reason" type="text" v-validate="'required|noContarEspacios:10'"  name="cancellationReason" />
            <span class="text-danger">{{ errors.first("cancellationReason") }}</span>
          </b-form-group>
  
          <!-- Botón Enviar -->
          <div class="text-center">
            <b-button style="margin-top: 5%" @click="onSubmit2" variant="danger">Confirmar Cancelación</b-button>
          </div>
        </b-form>
      </b-modal>

      <b-modal
        v-if="showConfirmModal"
        v-model="showConfirmModal"
        title="Confirmar Cancelación"
        hide-footer
        size="sm"
        header-bg-variant="warning"
        header-text-variant="light"
      >
        <p>¿Estás seguro de cancelar la reserva?</p>
        <div class="text-center">
          <b-button style="margin-top: 5%" @click="showConfirmModal = false" variant="outline-secondary">Cancelar</b-button>
          <b-button style="margin-top: 5%" @click=confirmCancellation() variant="danger">Confirmar Cancelación</b-button>
        </div>
      </b-modal>
    </div>
</template>

<script>
import NavbarUser from "../../components/Inicio/NavbarUser.vue";
import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
import instance from "../../config/http-client.gateway";
import Swal from "sweetalert2";


export default {
    name: "ReservasView",
    components: {
        NavbarUser,
        CategoriesNavbar,
    },
    data() {
        return {
            showModal2: false,
            showConfirmModal: false,
            reason: "",
            modalVisible: false,
            selectedReserva: {}, // Inicializamos selectedReserva aquí
            booking: [],
            idSelected: "",
           
        };
    },
    mounted() {

        this.getBooking();
        this.getCancellation();
    },
    methods: {
        onSubmit2() {
        this.$validator.validateAll().then((valid) => {
          if (valid) {
            this.showConfirmModal = true;
          } else {
            console.log('Falta completar campos');
            Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Por favor completa todos los campos obligatorios.',
        });
          }
        });
      },

      async cancelModal(id) {
        console.log(id);
        this.idSelected = id;


        this.showModal2 = true;
        
        
      },
        

      async confirmCancellation() {
      try {
        
        const response = await instance.doPost(
          "/cancellation/",{ 
            reason: this.reason,
            booking:{id: this.idSelected}                  
          });

    await instance.doPost("/booking/actualizar/state/", {
      id: `${this.idSelected}`,
      state: "cancelado"
    });

          Swal.fire({
          icon: 'success',
          title: 'Éxito',
          text: 'Se ha cancelado la reserva exitosamente.',
        });
        this.showModal2 = false;
        this.showConfirmModal = false;
        window.location.reload();
      } catch (error) {

        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Hubo un error al cancelar la reserva.',
        });
        console.error(error);
        console.log("aqui se supone estan los datos de booking");
        console.log(this.reason); 
        console.log(this.idSelected);
       
      }
    },
        async getCancellation() {
            try {
                const response = await instance.doGet('/cancellation/');
                console.log("Cancellation: ");
                console.log(response);
                this.cancellation = response.data.data;
            } catch (error) {
                console.error('Error al obtener los datos de cancelaciones:', error);
            }
        },



        async getBooking() {
            try {
                const response = await instance.doGet('/booking/');
                console.log("Booking: ");
                console.log(response.data.data);
                this.booking = response.data.data;
               
              



            } catch (error) {
                console.error('Error al obtener los datos de reservas:', error);
            }
        },
        formatDate(timestamp) {
            const date = new Date(timestamp);
            const year = date.getFullYear().toString().slice(-2);
            const month = ('0' + (date.getMonth() + 1)).slice(-2);
            const day = ('0' + date.getDate()).slice(-2);
            return `${year}-${month}-${day}`;
        },

      

       
   
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