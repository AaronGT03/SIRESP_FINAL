<template>
  <div>

    <div class="container-fluid mt-4">
      <div class="row mt-5 justify-content-center">
        <div class="col-md-12 ">
          <b-row class="">
            <b-col cols="12" sm="12" class="text-center ">
              <div>
                <b-card class="container-tarjeta text-center">
                  
                  <div class="mt-5">
                    <h2>Detalles de tu Compra</h2>
                    <hr />
                  </div>
                  <b-row class="mt-2">
                    <b-col>
                      <b-icon scale="2" icon="house-fill"></b-icon>
                    </b-col>
                  </b-row>
                  <b-card-text class="mt-3">
                    <h4>{{ this.bookingName }}</h4>
                  </b-card-text>
                  <b-row>
                    <b-col cols="12">

                    </b-col>
                  </b-row>
                  <b-row class="mt-5">
                    <b-col cols="12" sm="6">
                      <b-card-text class="d-flex text-center justify-content-center align-items-center">
                        <div>
                          <b-icon scale="2" icon="arrow-down-left-circle-fill"></b-icon>
                          <h5 class="mt-3" style="color: black; text-align: center;">Fecha de llegada</h5>
                        </div>
                      </b-card-text>
                      <b-row>
                        <b-col>
                          <p>{{ this.booking.arrivalDate }}</p>
                        </b-col>
                      </b-row>
                    </b-col>
                    <b-col cols="12" sm="6">
                      <b-card-text class="d-flex text-center justify-content-center align-items-center">
                        <div>
                          <b-icon scale="2" icon="arrow-up-right-circle-fill"></b-icon>
                          <h5 class="mt-3" style="color: black; text-align: center;">Fecha de Salida</h5>
                        </div>
                      </b-card-text>
                      <b-row>
                        <b-col>
                          <p>{{ this.booking.departureDate }}</p>
                        </b-col>
                      </b-row>
                    </b-col>
                  </b-row>
                  <b-row>
                    <b-col cols="12" sm="3">
                      <b-card-text class="mt-5">
                        <b-card-text class="d-flex text-center justify-content-center align-items-center">
                          <div>
                            <b-icon scale="2" icon="cash"></b-icon>
                            <h5 class="mt-3" style="color: black; text-align: center;">Costo Dia</h5>
                          </div>
                        </b-card-text>
                        <b-row>
                          <b-col>
                            <p class="mt-2">$ {{ this.bookingA }}</p>
                          </b-col>
                        </b-row>
                      </b-card-text>
                    </b-col>
                    <b-col cols="12" sm="3">
                      <b-card-text class="mt-5">
                        <b-card-text class="d-flex text-center justify-content-center align-items-center">
                          <div>
                            <b-icon scale="2" icon="calendar"></b-icon>
                            <h5 class="mt-3" style="color: black; text-align: center;">Dia Total</h5>
                          </div>
                        </b-card-text>
                        <b-row>
                          <b-col>
                            <p class="mt-2">{{ this.daysTotal }}</p>
                          </b-col>
                        </b-row>
                      </b-card-text>
                    </b-col>
                    <b-col cols="12" sm="3">
                      <b-card-text class="mt-5">
                        <b-card-text class="d-flex text-center justify-content-center align-items-center">
                          <div>
                            <b-icon scale="2" icon="cash"></b-icon>
                            <h5 class="mt-3" style="color: black; text-align: center;">Deposito</h5>
                          </div>
                        </b-card-text>
                        <b-row>
                          <b-col>
                            <p class="mt-2">$ 500</p>
                          </b-col>
                        </b-row>
                      </b-card-text>
                    </b-col>
                    <b-col cols="12" sm="3">
                      <b-card-text class="mt-5">
                        <b-card-text class="d-flex text-center justify-content-center align-items-center">
                          <div>
                            <b-icon scale="2" icon="cash"></b-icon>
                            <h5 class="mt-3" style="color: black; text-align: center;">Costo Total</h5>
                          </div>
                        </b-card-text>
                        <b-row>
                          <b-col>
                            <p class="mt-2">$ {{ this.totalPrecio }}</p>
                          </b-col>
                        </b-row>
                      </b-card-text>
                    </b-col>
                    
                  </b-row>
                </b-card>
              </div>
            </b-col>
            <b-col cols="12" sm="12" class="justify-content-center align-items-center">
              <b-card class="container-tarjeta2 mb-5">


                <form @submit.prevent="processPayment" class="mt-5">
                  <h2 for="card-element" class="text-center">Ingresa los detalles de tu tarjeta:</h2>
                  <hr>
                  <div class="cardd mt-5">
                    <svg id='logo-visa' enable-background="new 0 0 50 70" height="70px" version="1.1"
                      viewBox="0 0 50 50" width="70px" xml:space="preserve" xmlns="http://www.w3.org/2000/svg"
                      xmlns:xlink="http://www.w3.org/1999/xlink">
                      <g>
                        <g>
                          <polygon clip-rule="evenodd" fill="#ffffff" fill-rule="evenodd"
                            points="17.197,32.598 19.711,17.592 23.733,17.592     21.214,32.598   " />
                          <path clip-rule="evenodd"
                            d="M35.768,17.967c-0.797-0.287-2.053-0.621-3.596-0.621    c-3.977,0-6.752,2.029-6.776,4.945c-0.023,2.154,1.987,3.358,3.507,4.08c1.568,0.738,2.096,1.201,2.076,1.861    c0,1.018-1.238,1.471-2.395,1.471c-1.604,0-2.455-0.232-3.773-0.787l-0.53-0.248l-0.547,3.348    c0.929,0.441,2.659,0.789,4.462,0.811c4.217,0,6.943-2.012,6.979-5.135c0.025-1.692-1.053-2.999-3.369-4.071    c-1.393-0.685-2.246-1.134-2.246-1.844c0-0.645,0.723-1.306,2.295-1.306c1.314-0.024,2.268,0.271,3.002,0.58l0.365,0.167    L35.768,17.967z"
                            fill="#ffffff" fill-rule="evenodd" />
                          <path clip-rule="evenodd"
                            d="M46.055,17.616h-3.102c-0.955,0-1.688,0.272-2.117,1.24    l-5.941,13.767h4.201c0,0,0.688-1.869,0.852-2.262c0.469,0,4.547,0,5.133,0c0.123,0.518,0.49,2.262,0.49,2.262h3.711    L46.055,17.616 M41.1,27.277c0.328-0.842,1.609-4.175,1.609-4.175c-0.041,0.043,0.328-0.871,0.529-1.43l0.256,1.281    c0,0,0.773,3.582,0.938,4.324H41.1z"
                            fill="#ffffff" fill-rule="evenodd" />
                          <path clip-rule="evenodd"
                            d="M13.843,17.616L9.905,27.842l-0.404-2.076    c-0.948-2.467-2.836-4.634-5.53-6.163l3.564,12.995h4.243l6.312-14.982H13.843z"
                            fill="#ffffff" fill-rule="evenodd" />
                          <path clip-rule="evenodd"
                            d="M7.232,17.174H0.755l-0.037,0.333    c5.014,1.242,8.358,4.237,9.742,7.841l-1.42-6.884C8.798,17.507,8.105,17.223,7.232,17.174L7.232,17.174z"
                            fill="#ffffff" fill-rule="evenodd" />
                        </g>
                      </g>
                    </svg>
                    <div class="datosT">
                      <h5 style="color: white;">Card Number</h5>
                      <h6 style="color: white;">0000 0000 0000 0000</h6>
                      <h5 style="color: white;">Expiration<span style="margin-left: 5%; color: white;">CVC</span></h5>
                      <h6 style="color: white;">00 / 0000<span style="color: white; margin-left: 5%;">000</span></h6>
                    </div>
                    <div class='wave'></div>
                  </div>

                  <div>
                    <div class="mt-5" id="card-element"></div>
                    <div class="mt-5" id="card-errors" role="alert"></div>
                  </div>
                  <b-row class="mb-5">
                    <b-col class="mt-2 mb-5 text-center" cols="12">
                      <button type="submit" :disabled="processing">
                        {{ processing ? 'Procesando...' : 'Pagar ahora' }}
                      </button>
                    </b-col>
                  </b-row>
                </form>
              </b-card>
            </b-col>
          </b-row>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import Navbar from "../Inicio/Navbar.vue";
import CategoriesNavbar from "../Inicio/CategoriesNavbar.vue";
import instance from "../../config/http-client.gateway";
import { loadStripe } from '@stripe/stripe-js';
const today = new Date();

const formattedDate = today.toISOString().split('T')[0];

export default {
  components: {
    Navbar,
    CategoriesNavbar
  },
  data() {
    return {
      stripe: null,
      cardElement: null,
      processing: false,
      id: "",
      bookingA: "",
      bookingName: "",
      totalPrecio: "",
      daysTotal: "",
      deposito: 500,
      pay: {
        payment_date: formattedDate,
        payment_status: 'Pagado',
        
      },
      booking: []

    };
  },
  async mounted() {
    this.stripe = await loadStripe('pk_test_51Ozv0cP1aOKzmNtvqurkgwfB0UwGQYICsjxNPqpJLo1o7VtpnZKLgHyDUFAVqcs3T0cCk9q1n03OremXf82pBMwu00xMzea9ab');
    this.cardElement = this.stripe.elements().create('card');
    this.cardElement.mount('#card-element');
    const id = this.$route.query.id;
    this.bookingId = id
    console.log("Valor de this.bookingId antes de llamar a getBooking():", this.bookingId);
    this.getBooking();

  },
  methods: {
    async getBooking() {
      console.log("id")

      console.log(this.bookingId)
      const response = await instance.doGet(`/booking/${this.bookingId}`);
      this.booking = response.data.data;
      this.bookingId = response.data.data.id;
      this.bookingA = response.data.data.accommodation.price;
      this.bookingName = response.data.data.accommodation.name;
      this.bookingImg = response.data.data.accommodation.images;
      console.log("Booking")
      console.log(this.booking)
      console.log("price accommodation")
      console.log(this.bookingA)
      console.log("name accommodation")
      console.log(this.bookingName)
      console.log("images accommodation")
      console.log(this.bookingIm)
      console.log("id booking")
      console.log(this.bookingId)

      const arrivaleDate = response.data.data.arrivalDate;
      let date = new Date(arrivaleDate);

      let dateFormated = date.toLocaleDateString('es-ES');;
      console.log(dateFormated);

      this.user = response.data;
      this.booking.arrivalDate = dateFormated;

      const departureDate = response.data.data.departureDate;
      let date2 = new Date(departureDate);

      let dateFormated2 = date2.toLocaleDateString('es-ES');;
      console.log(dateFormated2);

      this.user = response.data;
      this.booking.departureDate = dateFormated2;

      const fechaLlegada = new Date(arrivaleDate);
      const fechaSalida = new Date(departureDate);


      // Calcula la diferencia en milisegundos entre las fechas
      const diferenciaTiempo = fechaSalida.getTime() - fechaLlegada.getTime();

      // Calcula la diferencia en días dividiendo por la cantidad de milisegundos en un día y redondeando hacia abajo
      this.daysTotal = Math.floor(diferenciaTiempo / (1000 * 3600 * 24));

      console.log("La estadía es de " + this.daysTotal + " días.");

      const price = response.data.data.accommodation.price;

      this.totalPrecio = (this.daysTotal * price) + this.deposito;

      console.log("Tottal: " + this.totalPrecio);

    },
    async processPayment() {
      this.processing = true;

      const { error, paymentMethod } = await this.stripe.createPaymentMethod({
        type: 'card',
        card: this.cardElement
      });

      if (error) {
        const errorElement = document.getElementById('card-errors');
        errorElement.textContent = error.message;
        this.processing = false;
      } else {
        this.registerPays();
        console.log('Pago exitoso:', paymentMethod);
        // Aquí puedes realizar acciones adicionales después de un pago exitoso
      }
    },
    async registerPays() {
      try {
        const response = await instance.doPost(
          "/pay/", {
          paymentDate: this.pay.payment_date,
          paymentStatus: this.pay.payment_status,
          amount: this.totalPrecio,
          booking: { id: this.bookingId },
        })

        console.log(response); // Manejar la respuesta de registro
        alert("Pago exitoso.");
        window.location.href = '/historial';

      } catch (error) {
        console.error(error);
        console.log(response);
        alert("Hubo un error al hacer el pago.");
      }
    },
  }
};
</script>

<style>


/* Estilos para dispositivos móviles */
@media (max-width: 768px) {
  h5 {
    font-size: 10px;
    /* Reducir el tamaño del texto para dispositivos móviles */
  }

  h6 {
    font-size: 8px;
    /* Reducir el tamaño del texto para dispositivos móviles */
  }
}


.cardd {
  width: 100%;
  /* Establece el ancho al 100% del contenedor padre */
  max-width: 350px;
  /* Establece un máximo para el ancho */
  height: 190px;
  /* Mantén el alto fijo */
  position: relative;
  margin: 0 auto;
  background-color: #1F83F4;
  overflow: hidden;
  z-index: 1;
  border-radius: 6px;
  /* Redondear bordes */
  box-shadow: 0 15px 24px rgba(37, 44, 65, 0.32);
  /* Sombra */
}


.card-content {
  width: 100%;
  padding: 30px;
  position: relative;
  float: left;
  z-index: 1;
}

#logo-visa {
  position: relative;
  margin-top: -10px;
  left: 190px;
}

/* Estilos para el SVG en dispositivos móviles */
@media (max-width: 768px) {
  #logo-visa {
    left: 155px;
    /* Cambia el valor de left para dispositivos móviles */
  }
}


.wave {
  height: 350px;
  width: 350px;
  position: relative;
  background: #2f1df2;
  z-index: -1;
}

.container-tarjeta2 {
  background-color: #bababa !important;
  height: calc(75vh - 55px);
  /* Altura igual a la altura de la pantalla menos la altura del Navbar */
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-bottom: 2%;

  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.1);
}

/* Ajuste de la posición del segundo col */
.container-tarjeta {
  background-color: #bababa !important;
  height: calc(75vh - 55px);
  /* Altura igual a la altura de la pantalla menos la altura del Navbar */
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin-bottom: 2%;

  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.1);
}

/* Media query para pantallas de teléfono */
@media (max-width: 576px) {
  .container-tarjeta {
    height: calc(140vh - 55px); /* Ajusta la altura para pantallas pequeñas (teléfonos) */
  }
}


/* Estilo del formulario */
.container-tarjeta form {
  margin-top: auto;
  /* Empujar el formulario hacia abajo */
}

.datosT {
  margin-left: 5%;
  padding: 1%;
}

button {
  background-color: #1F83F4;
  color: #ffffff;
  border: none;
  border-radius: 4.5rem;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}

button:hover {
  background-color: #165aad;
}
</style>