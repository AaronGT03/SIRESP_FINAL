<template>
  <div>
    <b-row>
      <b-col cols="12">
        <CategoriesNavbar />
        <NavbarUser />
      </b-col>
    </b-row>

    <div class="container-fluid" style="margin-top: 10%">
      <div class="row mt-5">
        <div class="col-md-12">
          <div class="mt-5">
            <h2 class="text-center">Historial de Pagos</h2>
          </div>
          <div class="row justify-content-center mt-4">
            <div class="col-xl-12">
              <b-card
                class="card_shadow mt-3"
                v-for="pago in pays"
                :key="pago.id"
              >
                <div class="row">
                  <div class="col-md-3 col-sm-12 text-center">
                    <b-row>
                      <b-col cols="12">
                        <h4>Nombre Alojamiento</h4>
                        <p class="text-center">
                          {{ pago.booking.accommodation.name }}
                        </p>
                      </b-col>
                      <b-col>
                        <button
                          v-if="pago.paymentStatus !== 'Reembolsado'"
                          @click="
                            returnDeposit(
                              pago.id,
                              pago.amount,
                              pago.paymentDate,
                              pago.paymentStatus,
                              pago.booking.id
                            )
                          "
                        >
                          Regresar Deposito
                        </button>
                      </b-col>
                    </b-row>
                  </div>
                  <div class="col-md-9 text-center">
                    <div class="row">
                      <div class="col-md-4">
                        <b-icon
                          scale="2"
                          icon="arrow-down-left-circle-fill"
                        ></b-icon>
                        <h4>Fecha de llegada</h4>
                        <p>{{ formatDate(pago.booking.arrivalDate) }}</p>
                        <!-- Aquí llamamos a la función formatDate -->
                      </div>
                      <div class="col-md-4">
                        <b-icon
                          scale="2"
                          icon="arrow-up-right-circle-fill"
                        ></b-icon>
                        <h4>Fecha de Salida</h4>
                        <p>{{ formatDate(pago.booking.departureDate) }}</p>
                        <!-- Aquí llamamos a la función formatDate -->
                      </div>
                      <div class="col-md-4">
                        <b-icon scale="2" icon="person-fill"></b-icon>
                        <h4>Huespedes</h4>
                        <p>{{ pago.booking.numGuests }}</p>
                      </div>
                    </div>
                    <div class="row mt-3">
                      <div class="col-md-4">
                        <b-icon scale="2" icon="calendar"></b-icon>
                        <h4>Fecha de Pago</h4>
                        <p>{{ formatDate(pago.paymentDate) }}</p>
                        <!-- Aquí llamamos a la función formatDate -->
                      </div>
                      <div class="col-md-4">
                        <b-icon scale="2" icon="cash"></b-icon>
                        <h4>Monto</h4>
                        <p>{{ pago.amount }}</p>
                      </div>
                      <div class="col-md-4">
                        <b-icon scale="2" icon="toggles2"></b-icon>
                        <h4>Estado del Pago</h4>
                        <b-badge
                          :variant="
                            pago.paymentStatus === 'Pagado'
                              ? 'success'
                              : 'warning'
                          "
                          >{{ pago.paymentStatus }}</b-badge
                        >
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
  </div>
</template>

<script>
import NavbarUser from "../../components/Inicio/NavbarUser.vue";
import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
import instance from "../../config/http-client.gateway";
import Swal from "sweetalert2";
export default {
  name: "AboutView",
  components: {
    NavbarUser,
    CategoriesNavbar,
  },
  data() {
    return {
      pays: [],
    };
  },
  mounted() {
    this.getPays();
  },
  methods: {
    async getPays() {
      try {
        const response = await instance.doGet("/pay/");
        this.pays = response.data.data;
        console.log(this.pays);
      } catch (error) {
        console.error("Error al obtener los datos de alojamiento:", error);
      }
    },
    async returnDeposit(payId, payAmount, payDate, payStatus, payIdBooking) {
      try {
        // Aquí utilizas el ID del pago para realizar operaciones como actualizar en el backend, etc.
        console.log("ID del pago:", payId);
        console.log("monto del pago:", payAmount);
        console.log("date del pago:", payDate);
        console.log("status del pago:", payStatus);
        console.log("id booking del pago:", payIdBooking);
        const NewPay = payAmount - 500;

        console.log("Pago reembolso: ", NewPay);

        const response = await instance.doPost("/pay/actualizar/", {
          id: payId,
          paymentDate: payDate,
          paymentStatus: "Reembolsado",
          amount: NewPay,
          booking: { id: payIdBooking },
        });

        console.log(response); // Manejar la respuesta de registro

        Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Reembolso exitoso",
          showConfirmButton: true, // Mostrar el botón de confirmación
        }).then((result) => {
          if (result.isConfirmed) {
            location.reload(); // Recargar la página si el usuario confirma
          }
        });
      } catch (error) {
        console.error("Error al procesar el reembolso:", error);
      }
    },
    formatDate(timestamp) {
      const date = new Date(timestamp);
      const year = date.getFullYear().toString().slice(-2);
      const month = ("0" + (date.getMonth() + 1)).slice(-2);
      const day = ("0" + date.getDate()).slice(-2);
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
