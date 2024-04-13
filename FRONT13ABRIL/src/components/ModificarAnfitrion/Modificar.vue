<template>
  <div id="app" class="container-fluid">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card airbnb-card mt-5">
          <div class="card-header airbnb-header">
            <h5 class="card-title text-center mb-0">
              <i class="title"></i>
              Modificacion de Anfitrión
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
                <label for="gmail">Gmail:</label>
                <input type="email" class="form-control" id="gmail" v-model="form.gmail" v-validate="'required|email'"
                  :class="{ 'is-invalid': errors.has('gmail') }" name="gmail">
                <span v-if="errors.has('gmail')" class="invalid-feedback">{{ errors.first('gmail') }}</span>
              </div>
              <div class="form-group">
                <label for="telefono">Teléfono:</label>
                <input type="tel" class="form-control" id="telefono" v-model="form.telefono"
                  v-validate="'required|phone_format'" :class="{ 'is-invalid': errors.has('telefono') }"
                  name="telefono">
                <span v-if="errors.has('telefono')" class="invalid-feedback">{{ errors.first('telefono') }}</span>
              </div>

              <div class="form-group">
                <b-form-file  name="identificationImage" v-model="form.identificationImage">
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

export default {
  data() {
    return {
      form: {
        curp: '',
        identificationImage: '',
        rfc: '',
        usuario: {
          id: 1,
        }
      },
      imageName: ''
    }
  },
  methods: {
    onSubmit() {
      this.$validator.validateAll().then((valid) => {
        if (valid) {
          alert("Formulario enviado");
        } else {
          console.log("Falta completar campos");
          
        }
      });
      },
    onFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        this.form.identificationImage = file;
        this.imageName = file.name;
      }
    }
  }
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
