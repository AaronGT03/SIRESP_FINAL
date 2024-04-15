<template>
  <div id="app" class="container-fluid">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card airbnb-card mt-5">
          <div class="card-header airbnb-header">
            <h5 class="card-title text-center mb-0">
              <i class="title"></i>
              Registro de Anfitrión
            </h5>
          </div>
          <div class="card-body">
            <form @submit.prevent="onSubmit" enctype="multipart/form-data">
              <div class="form-group">
                <label for="curp">CURP:</label>
                <input type="text" class="form-control" id="curp" v-model="form.curp" v-validate="'required|curp'"
                  :class="{ 'is-invalid': errors.has('curp') }" name="curp">
                <span v-if="errors.has('curp')" class="invalid-feedback">{{ errors.first('curp') }}</span>
              </div>


              <div class="form-group">
                <label for="identificationImage">INE:</label>
                <b-form-file name="identificationImage" v-model="form.identificationImage">
                </b-form-file>
              </div>

              <div class="form-group">
                <label for="rfc">RFC:</label>
                <input type="text" class="form-control" id="rfc" v-model="form.rfc" v-validate="'required|rfc'"
                  :class="{ 'is-invalid': errors.has('rfc') }" name="rfc">
                <span v-if="errors.has('rfc')" class="invalid-feedback">{{ errors.first('rfc') }}</span>
              </div>
              <button type="submit" class="btn btn-primary btn-block">
                <i class="fas fa-chevron-right"></i>
                Reservar
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
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
      form: {
        curp: "",
        identificationImage: null,
        rfc: "",
        user: "",
      },
    };
  },
  methods: {
    async onSubmit() {
      try {
        await this.validateForm();
      } catch (error) {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Por favor completa todos los campos obligatorios.',
        });
        return;
      }
      try {
        const formData = new FormData();
        formData.append('profilePicture', this.form.identificationImage);

        const response2 = await axios.post("http://localhost:8080/api-sirep/host/upload", formData);

        this.form.identificationImage = response2.data;
      } catch (error) {
        console.log(error);
      }
      try {
        const response = await instance.doPost("/host/", {
          curp: this.form.curp,
          identificationImage: this.form.identificationImage,
          rfc: this.form.rfc,
          user: { id: this.form.user }
        });

        const response3 = await instance.doPost("/user/actualizar/status", {
          id: `${this.form.user}`,
          status: "pendiente"
        });
        const response4 = await axios.get("http://localhost:8080/api-sirep/sendSMS/",
          {
            phoneNumber: "527772599268",
            phoneNumberId: "350805548106713",
            message: "¡Hola un nuevo un usuario quiere ser Anfitrión, revisa tu cuenta para verificar...!"
          });
        Swal.fire({
          icon: 'success',
          title: 'Éxito',
          text: 'Host registrado exitosamente.',
        });

        window.location.href = "/userhome";
      } catch (error) {
        console.log(error);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Hubo un error al registrar el host.',
        });
      }
    },

    validateForm() {
      return new Promise((resolve, reject) => {
        this.$validator.validateAll().then((result) => {
          if (result) {
            resolve(true); 
          } else {
            reject(false); 
          }
        });
      });
    },

    async getUser() {
      try {
        const claims = JSON.parse(atob(token.split('.')[1]));
        const response = await instance.doGet(`/user/username/${claims.sub}`);
        console.log(response);
        this.form.user = response.data.id;

      } catch (error) {
        console.log(error);
      }
    },
  },

  mounted() {
    this.getUser();
  },
};
</script>

<style>
.airbnb-card {
  border: none;
  box-shadow: 0px 2px 12px rgba(0, 0, 0, 0.1);
}

.airbnb-header {
  background-color: #007bff;
  border-radius: 8px 8px 0 0;
  padding: 15px;
}

.airbnb-header h5 {
  font-weight: bold;
  margin-bottom: 0;
}

.card-body {
  padding: 30px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  font-weight: bold;
}

input[type="file"] {
  display: none;
}

.btn-outline-primary {
  color: #007bff;
  border-color: #007bff;
}

.btn-outline-primary:hover {
  color: #fff;
  background-color: #007bff;
  border-color: #007bff;
}

.invalid-feedback {
  display: block;
  color: #dc3545;
  margin-top: 4px;
}
</style>
