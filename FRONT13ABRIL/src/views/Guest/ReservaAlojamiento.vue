<template>
    <div>

        <NavbarUser />

        <div class="container-fluid">
            <div class="row mt-5 justify-content-center">
                <div class="col-md-12">
                    <div class="justify-content-center">
                        <b-carousel id="carousel-custom" v-model="slide" :interval="4000" controls indicators
                            background="#ababab" @sliding-start="onSlideStart" @sliding-end="onSlideEnd" class="mt-3">
                            <b-carousel-slide v-for="(image, index) in JSON.parse(accommodation.images)" :key="index"
                                class="carusel_slide">
                                <template #img>

                                    <img :src="image" :alt="'Slide ' + (index + 1)" class="d-block img-fluid w-100"
                                        style="height: 800px; object-fit: cover;" />
                                </template>
                            </b-carousel-slide>
                        </b-carousel>
                    </div>
                    <b-row class="mt-5">
                        <b-col cols="12" lg="6">
                            <h1 class="text-center">{{ accommodation.name }}</h1>
                            <hr>
                            <h5 class="text-justify text-center">
                                {{ accommodation.description }}
                            </h5>
                            <b-col cols="12" class="text-center">
                                <hr>
                                <h4 class="text-center mb-5">Servicios Adicionales</h4>
                                <b-icon icon="house-fill" font-scale="4.5"></b-icon>
                            </b-col>
                            <b-col class="d-flex justify-content-center align-items-center mt-5" >
                                <h5>
                                    <ul v-for="(service, index) in JSON.parse(accommodation.additionalfeatures)">
                                        <li>{{ service }}</li>
                                    </ul>
                                </h5>
                            </b-col>

                            <b-row class="mt-5 text-center">
                                <b-col cols="12">
                                    <hr>
                                    <h4 class="text-center">Ubicación</h4>
                                </b-col>
                                <b-col cols="12">
                                    <b-row>
                                        <b-col cols="12" class="align-items-center mb-3 mt-5">
                                            <b-icon icon="pin-map-fill" font-scale="4.5"></b-icon>
                                        </b-col>
                                    </b-row>
                                    <h4 class="mt-4">{{ accommodation.location }}</h4>

                                </b-col>
                            </b-row>
                            <b-row class="mt-5 text-center">
                                <b-col cols="12">
                                    <hr>
                                    <div class="mt-5" style="overflow-y: scroll; height: 250px;">
                                        <h2 class="mb-5">Reseñas de Huéspedes</h2>
                                        <div v-for="(review, index) in reviews" :key="index" class="mb-4">
                                            <div class="align-items-center">
                                                <div>
                                                    <h5>{{ review.description }}</h5>
                                                    <div class="align-items-center">
                                                        <div class="mr-2">Calificación:</div>
                                                        <div>
                                                            <b-icon icon="star-fill" font-scale="1"
                                                                v-for="i in review.average" :key="i"
                                                                class="text-warning"></b-icon>
                                                        </div>
                                                    </div>
                                                    <div class="text-muted"></div>
                                                </div>
                                            </div>
                                            <p class="mt-2"></p>
                                        </div>
                                    </div>
                                </b-col>
                            </b-row>
                        </b-col>
                        <b-col cols="12" lg="6">
                            <b-card class="card_shadow" border-variant="secondary" header-border-variant="secondary"
                                align="center">
                                <b-card-text></b-card-text>
                                <b-row>
                                    <b-col cols="12">
                                        <h3 class="text-center">Reserva tu Alojamiento</h3>
                                    </b-col>
                                </b-row>

                                <b-row>
                                    <!-- FECHAS DISPONIBLES -->

                                    <b-col cols="12" class="mb-5">
                                        <vc-date-picker :value="null" color="blue" is-expanded
                                            :columns="$screens({ default: 1, lg: 2 })" :attributes="attrs" />
                                    </b-col>


                                    <b-col cols="6">
                                        <label for=""><b>Fecha de llegada:</b></label>
                                        <b-form-input v-model="booking.arrival_date" type="date"
                                            @change="getBooking(startDate, endDate)"></b-form-input>
                                    </b-col>
                                    <b-col cols="6">
                                        <label for=""><b>Fecha de Salida:</b></label>
                                        <b-form-input v-model="booking.departure_date" type="date"
                                            @change="getBooking(startDate, endDate)"></b-form-input>
                                    </b-col>

                                </b-row>

                                <b-row>
                                    <b-col cols="12" class="mt-3">
                                        <label slot="label" for="huespedes">Número de Huéspedes:</label>
                                    </b-col>
                                    <b-col cols="12" class="mt2">
                                        <b-row>
                                            <b-col><b-button @click="restarHuespedes"
                                                    variant="outline-secondary">-</b-button></b-col>
                                            <b-col>
                                                <b-form-input id="huespedes" v-model="booking.num_guest" type="number"
                                                    min="1" max="999" class="text-center"
                                                    style="max-width: 100%;"></b-form-input>
                                            </b-col>
                                            <b-col><b-button @click="sumarHuespedes"
                                                    variant="outline-secondary">+</b-button></b-col>
                                        </b-row>
                                    </b-col>
                                </b-row>
                                <b-row>
                                    <b-col cols="12" class="mt-5 text-center">
                                        <h3>${{ accommodation.price }}</h3>
                                        <h3 v-if="resultado !== null">El total es: {{ resultado }}</h3>
                                    </b-col>
                                    <b-col cols="12" class="mt-3 mb-5 text-center">
                                        <p> {{ this.deposit }} X deposito </p>
                                    </b-col>

                                </b-row>
                                <b-col class="mt-3">
                                    <p v-if="error" style="color: red;">{{ error }}</p>
                                    <b-button block variant="primary" @click="calcular">Calcular</b-button>
                                    <!-- Botón de reservar -->
                                    <b-button block variant="primary" @click="validarCampos"
                                        :disabled="!totalCalculado">Reservar</b-button>

                                </b-col>
                            </b-card>
                        </b-col>
                    </b-row>







                </div>
            </div>
        </div>



    </div>
</template>



<script>

import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
import NavbarUser from "../../components/Inicio/NavbarUser.vue";
import instance from "../../config/http-client.gateway";

export default {
    name: 'AboutView',
    components: {
        NavbarUser,
        CategoriesNavbar
    },
    data() {
        return {
            slide: 0,
            sliding: null,
            fechaLlegada: '',
            fechaSalida: '',
            startDate: '',
            endDate: '',
            numHuespedes: 1,
            error: '',
            accommodation: [],
            disabledDates: [],
            existingBookings: [],
            accommodationId: "",
            id: "",
            deposit: 500,
            booking: {
                arrival_date: '',
                departure_date: '',
                num_guest: 1,
                state_booking: 'Confirmado',

            },
            user: {
                id: "",
                names: "",
                lastName: "",
                username: "",
                role: "ROLE_USER",
                address: "",
                date: "",
                phone: "",
                gender: "",
                profilePicture: null,
                email: "",
                password: "",
                status: "Activo",
            },
            attrs: [],
            reviews: [],
            resultado: null,
            totalCalculado: false,
        }
    },
    methods: {
        async getInfo() {
            const response = await instance.doGet(`/accommodation/${this.id}`);
            this.accommodation = response.data.data;
            this.accommodationId = response.data.data.id;
            console.log("acomodation")
            console.log(this.accommodation)
            console.log("id")
            console.log(this.accommodationId)

        },
        async getUsers() {
            const token = sessionStorage.getItem('token')
            console.log(token);
            const claims = JSON.parse(atob(token.split('.')[1]));
            const username = claims.sub
            console.log(username)
            const response = await instance.doGet(`/user/username/${username}`)
            const fechaNum = response.data.date;
            let fecha = new Date(fechaNum);

            const dia = fecha.getDate().toString().padStart(2, '0');
            const mes = (fecha.getMonth() + 1).toString().padStart(2, '0');
            const año = fecha.getFullYear();

            const fechaFormateada = `${año}-${mes}-${dia}`; // Formato YYYY-MM-DD
            console.log(fechaFormateada);


            this.user = response.data;
            this.user.names = response.data.names;
            this.user.lastName = response.data.lastName;
            this.user.address = response.data.address;
            this.user.date = fechaFormateada;
            this.user.phone = response.data.phone;
            this.user.gender = response.data.gender;
            this.user.role = response.data.role;
            this.user.profilePicture = response.data.profilePicture;
            this.user.email = response.data.email;
            console.log("usuario");
            console.log(this.user.id);
        },
        mostrarModal() {
            this.$refs.modal.show()
        },
        onSlideStart(slide) {
            this.sliding = true
        },
        onSlideEnd(slide) {
            this.sliding = false
        },
        sumarHuespedes() {
            this.booking.num_guest++;
        },
        restarHuespedes() {
            if (this.booking.num_guest > 1) {
                this.booking.num_guest--;
            }
        },
        calcular() {
            const fechaLlegada = new Date(this.booking.arrival_date);
            const fechaSalida = new Date(this.booking.departure_date);


            // Calcula la diferencia en milisegundos entre las fechas
            const diferenciaTiempo = fechaSalida.getTime() - fechaLlegada.getTime();

            // Calcula la diferencia en días dividiendo por la cantidad de milisegundos en un día y redondeando hacia abajo
            const diferenciaDias = Math.floor(diferenciaTiempo / (1000 * 3600 * 24));

            console.log("La estadía es de " + diferenciaDias + " días.");

            const price = this.accommodation.price;

            const deposit = this.deposit;

            const total = (diferenciaDias * price) + deposit;
            this.resultado = total;

            console.log("Tottal: " + total)
            this.totalCalculado = true;

            this.verificarDisponibilidadReserva();

        },
        validarCampos() {
            const fechaActual = new Date();
            const fechaLlegada = new Date(this.booking.arrival_date);
            const fechaSalida = new Date(this.booking.departure_date);

            if (!this.booking.arrival_date || !this.booking.departure_date || !this.booking.num_guest) {
                this.error = 'Todos los campos son obligatorios';
                return;
            }

            if (fechaLlegada <= fechaActual) {
                this.error = "La fecha de llegada debe ser mayor a la fecha actual.";
                return;
            }

            if (fechaSalida <= fechaLlegada) {
                this.error = "La fecha de salida debe ser mayor a la fecha de llegada.";
                return;
            }


            if (this.booking.num_guest <= 0) {
                this.error = "El número de huéspedes debe ser mayor a 0.";
                return false;
            }


            this.error = '';
            this.registerBooking();

        },

        async getAccommodations() {
            try {
                // Realiza la solicitud GET a la ruta de alojamiento
                const response = await instance.doGet('/accommodation/', {
                    headers: {
                        // Agrega el token de autenticación si es necesario
                        Authorization: `Bearer ${sessionStorage.getItem('token')}`
                    }
                });

                // Guarda los datos de alojamiento en la variable accommodations
                this.accommodations = response.data;
                console.log("Accommodation")
                console.log(response.data.data);

                console.log(this.accommodation)

            } catch (error) {
                // Maneja los errores de la solicitud
                console.error('Error al obtener los datos de alojamiento:', error);
                // Puedes mostrar un mensaje de error al usuario si lo deseas
            }
        },
        checkDateOverlap() {
            const checkInDate = new Date(this.booking.arrival_date);
            const checkOutDate = new Date(this.booking.departure_date);

            if (checkInDate && checkOutDate && checkInDate <= checkOutDate) {
                this.getBooking(checkInDate, checkOutDate);
            }
        },
        async getBooking() {
            this.error = '';

            try {
                const response = await instance.doGet(`/booking/`);
                const accommodationId = this.accommodation.id;

                this.existingBookings = response.data.data.filter(booking => {
                    return accommodationId === booking.accommodation.id;
                });


                // Almacena las fechas reservadas en un array
                const reservedDates = this.existingBookings.map(booking => {
                    const start = new Date(booking.arrivalDate);
                    const end = new Date(booking.departureDate);
                    return { start, end };
                });

                // Agrega las fechas reservadas al arreglo attrs
                reservedDates.forEach(date => {
                    this.attrs.push({
                        highlight: {
                            start: { fillMode: 'outline' },
                            base: { fillMode: 'light' },
                            end: { fillMode: 'outline' },
                        },
                        dates: date
                    });
                });

                console.log("Existing bookings:");
                console.log(this.existingBookings);


            } catch (error) {
                console.error('Error al verificar disponibilidad de fechas:', error);
                return;
            }
        },

        async getReviews() {
            try {
                const response = await instance.doGet(`/review/`);
                const listReviews = response.data.data;
                console.log("Reviews");
                console.log(listReviews);
                // Utiliza una función de filtro para seleccionar solo las reseñas relacionadas con la propiedad actual
                this.reviews = listReviews.filter((item) => {
                    return item.accommodation.id == this.id; // Retorna true si el ID de la propiedad coincide
                });
                console.log("Reviews: ", this.reviews);
            } catch (error) {
                console.error("Error al obtener las reseñas:", error);
            }
        },

        verificarDisponibilidadReserva() {
            this.error = '';

            const fechaLlegada = new Date(this.booking.arrival_date);
            const fechaSalida = new Date(this.booking.departure_date);

            if (fechaSalida <= fechaLlegada) {
                this.error = "La fecha de salida debe ser mayor a la fecha de llegada.";
                return;
            }

            const isFechaReservada = this.existingBookings.some(reserva => {
                const reservaLlegada = new Date(reserva.arrivalDate);
                const reservaSalida = new Date(reserva.departureDate);

                if (
                    (fechaLlegada >= reservaLlegada && fechaLlegada < reservaSalida) ||
                    (fechaSalida > reservaLlegada && fechaSalida <= reservaSalida) ||
                    (fechaLlegada <= reservaLlegada && fechaSalida >= reservaSalida)
                ) {
                    this.error = "Las fechas seleccionadas ya están reservadas. Por favor, elija otras fechas.";
                    return true; // Indicamos que las fechas están reservadas
                } else {
                    return false; // Indicamos que las fechas no están reservadas
                }
            });

            if (!isFechaReservada) {
                // Si ninguna reserva coincide con las fechas seleccionadas, no hay error
                this.error = '';
            }


        },



        async registerBooking() {
            try {
                console.log(this.user.id)
                const response = await instance.doPost("/booking/", {
                    arrivalDate: this.booking.arrival_date,
                    departureDate: this.booking.departure_date,
                    numGuests: this.booking.num_guest,
                    stateBooking: this.booking.state_booking,
                    accommodation: { id: this.accommodationId },
                    user: { id: this.user.id }
                })

                const bookingId = response.data.data.id;

                // Navega a la ruta /pagoUser y pasa el ID del booking como un parámetro de consulta
                this.$router.push({ path: '/pagoUser', query: { id: bookingId } });

                console.log(response); // Manejar la respuesta de registro
                alert("Reserva registrada exitosamente.");

            } catch (error) {
                console.error(error);
                alert("Hubo un error al registrar la Reserva.");
            }
        }

    },
    mounted() {
        const id = this.$route.query.id;
        this.id = id;
        this.getInfo();
        this.getUsers()
        this.getBooking();
        this.getReviews();
    }
}
</script>

<style scoped>
/* General body styles */
body {
    font-family: 'Arial', sans-serif;
    background-color: #f5f5f5;
}

/* Navbar and general layout improvements */
.container-fluid {
    padding: 20px;
}

/* Carousel styling */
.carousel-custom img {
    height: 500px;
    /* Change based on your preference */
    object-fit: cover;
}

.carousel-custom {
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

/* Cards and content styling */
.card_shadow {
    background-color: white;
    border-radius: 8px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    margin-top: 20px;
}

.info-card,
.booking-card {
    transition: all 0.3s ease-in-out;
}

.info-card:hover,
.booking-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 25px rgba(0, 0, 0, 0.15);
}

/* Text and elements */
h2,
h3 {
    color: #333;
}

.text-justify {
    text-align: justify;
}

.label {
    font-weight: bold;
    color: #555;
}

/* Buttons and input styles */
b-button {
    transition: background-color 0.3s ease-in-out, color 0.3s ease-in-out;
}

b-button:hover {
    background-color: #0056b3;
    color: white;
}

b-form-input {
    border: 2px solid #ccc;
    border-radius: 5px;
    padding: 10px;
    margin: 5px 0;
}

/* Hover effects for interactive elements */
b-form-input:hover,
b-form-input:focus {
    border-color: #0056b3;
}

/* Service and reviews section */
.additional-details {
    background-color: #ffffff;
    padding: 15px;
    border-radius: 8px;
    margin-top: 20px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

/* Star icons and ratings */
.text-warning {
    color: #ffc107;
    /* Gold color for stars */
}

/* Location and host information */
.location-map,
.host-info {
    background-color: #ffffff;
    padding: 20px;
    margin-top: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

/* Adjustments for smaller screens */
@media (max-width: 768px) {
    .carousel-custom img {
        height: 300px;
    }
}
</style>
