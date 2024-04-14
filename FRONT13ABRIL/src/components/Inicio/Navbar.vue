<template>
  <div>
    <b-modal v-model="showModal" title="Registro de Usuario" hide-footer size="lg" modal-class="custom-modal"
      header-bg-variant="primary" header-text-variant="light">
      <b-form @submit.prevent="onSubmit" enctype="multipart/form-data" class="modal-form">
        <b-row>
          <b-col cols="6">
            <b-form-group label="Nombre">
              <b-form-input v-validate="'required|alpha_spaces|min:3'" name="names" v-model="user.names" type="text" />
              <span class="text-danger">{{ errors.first("names") }}</span>
            </b-form-group>
          </b-col>
          <b-col cols="6">
            <b-form-group label="Apellidos">
              <b-form-input v-validate="'required|alpha_spaces|min:3'" name="lastName" v-model="user.lastName"
                type="text" />
              <span class="text-danger">{{ errors.first("lastName") }}</span>
            </b-form-group>
          </b-col>
        </b-row>

        <b-row>
          <b-col cols="6">
            <b-form-group label="Usuario">
              <b-form-input v-validate="'required|alpha_num'" name="username" v-model="user.username" type="text" />
              <span class="text-danger">{{ errors.first("username") }}</span>
            </b-form-group>
          </b-col>

          <b-col cols="6">
            <b-form-group label="Rol">
              <b-form-select v-validate="'required'" name="role" v-model="user.role">
                <option value="">Seleccionar rol</option>
                <option value="ROLE_USER">Usuario</option>
              </b-form-select>
              <span class="text-danger">{{ errors.first("role") }}</span>
            </b-form-group>
          </b-col>
        </b-row>

        <b-row>
          <b-col cols="12">
            <b-form-group label="address">
              <b-form-input v-validate="'required'" name="address" v-model="user.address" type="text" />
              <span class="text-danger">{{ errors.first("address") }}</span>
            </b-form-group>
          </b-col>
        </b-row>

        <b-row>
          <b-col cols="6">
            <b-form-group label="Fecha de Nacimiento">
              <b-form-datepicker v-validate="'required|adult'" name="date" v-model="user.date" type="text" />
              <span class="text-danger">{{ errors.first("date") }}</span>
            </b-form-group>
          </b-col>
          <b-col cols="6">
            <b-form-group label="Teléfono">
              <b-form-input v-validate="'required|digits:10'" name="phone" v-model="user.phone" type="text" />
              <span class="text-danger">{{ errors.first("phone") }}</span>
            </b-form-group>
          </b-col>
        </b-row>

        <b-row>
          <b-col cols="6">
            <b-form-group label="Género">
              <b-form-select v-validate="'required'" name="gender" v-model="user.gender">
                <option value="">Seleccionar Género</option>
                <option value="masculino">Masculino</option>
                <option value="femenino">Femenino</option>
              </b-form-select>
              <span class="text-danger">{{ errors.first("gender") }}</span>
            </b-form-group>
          </b-col>

          <b-col cols="6">
            <b-form-group label="Foto de Perfil">
              <b-form-file v-validate="'required|imageSize'" name="profilePicture" v-model="user.profilePicture"
                :state="Boolean(user.profilePicture)" placeholder="Seleccione una imagen" accept="image/*">
              </b-form-file>
              <span class="text-danger">{{
                errors.first("profilePicture")
              }}</span>
            </b-form-group>
          </b-col>
        </b-row>
        <b-row>
          <b-col cols="6">
            <b-form-group label="Correo electrónico">
              <b-form-input v-validate="'required'" name="email" v-model="user.email" type="text" />
              <span class="text-danger">{{ errors.first("email") }}</span>
            </b-form-group>
          </b-col>
          <b-col cols="6">
            <b-form-group label="password">
              <b-form-input v-validate="'required|min:8|noSpace'" name="password" v-model="user.password"
                type="password" :class="{ 'is-danger': errors.has('password') }" ref="password" />
              <span class="text-danger">{{ errors.first("password") }}</span>
            </b-form-group>
          </b-col>
        </b-row>

        <!-- Botón Enviar -->
        <div class="text-center">
          <b-button style="margin-top: 5%" type="submit" variant="primary">Enviar</b-button>
        </div>
      </b-form>
    </b-modal>
    <b-modal v-model="showLoginModal3" title="Recuperacón de Contraseña" hide-footer size="md"
      header-bg-variant="primary" header-text-variant="light">

      <b-form @submit.prevent="passwordrecovery" class="modal-form">
        <b-row>
          <b-col cols="12">
            <b-form-group label="Escribe tu correo, para que te llegue el código de verificacion">
              <b-form-input v-validate="'required'" name="loginEmail" v-model="emailSearch" type="email"
                placeholder="Ingrese tu direccion de correo" />
              <span class="text-danger">{{ errors.first("loginEmail") }}</span>
              <div class="text-center">
                <b-button type="submit" variant="primary">Enviar Código</b-button>
              </div>

            </b-form-group>

          </b-col>
        </b-row>
      </b-form>

    </b-modal>
    <b-modal v-model="showLoginModal4" title="Verificacion de Código" hide-footer size="md" header-bg-variant="primary"
      header-text-variant="light">

      <b-form @submit.prevent="changePassword" class="modal-form">
        <b-row>
          <b-col cols="12">
            <b-form-group label="Verificacion">
              <b-form-input v-validate="'required'" name="code" v-model="codeSend" type="text"
                placeholder="Ingrese el codigo que se le envio  para verificar su contrasena" />
              <span class="text-danger">{{ errors.first("code") }}</span>
              <div class="text-center">
                <b-button type="submit" variant="primary">Verificar Código</b-button>
              </div>

            </b-form-group>

          </b-col>
        </b-row>
      </b-form>

    </b-modal>
    <b-modal v-model="showLoginModal5" title="Cambio de contraseñas" hide-footer size="md" header-bg-variant="primary"
      header-text-variant="light">

      <b-form @submit.prevent="updatePassword" class="modal-form">
        <b-row>
          <b-col cols="12">
            <b-form-group>
              <label>Registre su nueva Contraseña</label>
              <b-form-input v-validate="'required|min:8'" name="paasoword1" v-model="password1" type="password"
                placeholder="Ingrese Su nueva Contraseña" />
              <span class="text-danger">{{ errors.first("paasoword1") }}</span>
              <br>
              <label>Repita se Contraseña</label>
              <div class="text-center">
                <b-form-input v-validate="'required|min:8'" name="paasoword2" v-model="password2" type="password"
                  placeholder="Ingrese Nuevamente su Contraseña" />
                <span class="text-danger">{{ errors.first("paasoword2") }}</span>

                <b-button type="submit" variant="primary">Actualizar Conatrseñas</b-button>
              </div>

            </b-form-group>

          </b-col>
        </b-row>
      </b-form>

    </b-modal>

    <b-modal v-model="showLoginModal" title="Inicio de Sesión" hide-footer size="md" header-bg-variant="primary"
      header-text-variant="light">
      <b-form @submit.prevent="onLoginSubmit" class="modal-form">
        <b-row>
          <b-col cols="12">
            <b-form-group label="Username">
              <b-form-input v-validate="'required'" name="loginEmail" v-model="loginEmail" type="text"
                placeholder="Ingrese su username" />
              <span class="text-danger">{{ errors.first("loginEmail") }}</span>

            </b-form-group>
          </b-col>
        </b-row>

        <b-row>
          <b-col cols="12">
            <b-form-group label="Contraseña">
              <b-form-input v-validate="'required|min:8'" name="loginPassword" v-model="loginPassword" type="password"
                placeholder="Ingrese su contraseña" />
              <span class="text-danger">{{
                errors.first("loginPassword")
              }}</span>
            </b-form-group>
          </b-col>
        </b-row>
        <div class="text-center">
          <b-link @click="
            showLoginModal3 = true;
          showModal = false;
          showLoginModal = false
            " variant="link">
            Olvide Micontraseña</b-link>
        </div>

        <div class="text-center">
          <b-button type="submit" variant="primary">Iniciar Sesión</b-button>
        </div>
      </b-form>
      <br />
      <!--No estas registrado?-->
      <div class="text-center">
        ¿No estás registrado?
        <b-link @click="
          showLoginModal = false;
        showModal = true;
        " variant="link">
          - Registrate aquí</b-link>
      </div>
    </b-modal>

    <div class="d-flex justify-content-center align-items-center">
      <b-spinner v-if="showSpinner" variant="info" style="width: 8rem; height: 8rem; margin-top: 15%"></b-spinner>
    </div>

    <nav class="navbar">
      <div class="container">
        <a class="navbar-brand button-effect" href="/">
          <img src="../../assets/img/Logo.jpeg" alt="Vue.js" width="40" height="40" />
          <span class="title">SIRESP</span>
        </a>
        <div class="navbar-menu">
          <div class="dropdown" @click="toggleDropdown">
            <a class="dropdown-item button-effect">Perfil <b-icon icon="caret-down" /></a>
            <div class="dropdown-menu" v-if="isDropdownOpen">
              <a class="dropdown-item" v-on:click="showLoginModal = true">
                <b-icon icon="person" /> Iniciar Sesión</a>
              <a class="dropdown-item" v-on:click="showModal = true">
                <b-icon icon="person-plus" /> Registrarse</a>
            </div>
          </div>
        </div>
        <div class="bottom-tab-nav" v-if="isMobile">
          <a class="tab-item" href="/">
            <b-icon icon="house-door" font-scale="1.5"></b-icon>
            <span>Inicio</span>
          </a>
          <a class="tab-item" v-on:click="showLoginModal = true">
            <b-icon icon="person" font-scale="1.5"></b-icon>
            <span>Iniciar sesión</span>
          </a>
        </div>
      </div>
    </nav>
  </div>
</template>

<style scoped>
.navbar {
  background-color: #007bff;
  color: white;
  padding: 0.5rem 1rem;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
}

.title {
  padding-left: 10px;
  color: white;
}

.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.navbar-menu {
  display: flex;
  align-items: center;
}

.dropdown-item {
  color: white;
  text-decoration: none;
  padding: 0.5rem 0.75rem;
}

.dropdown-item:hover,
.dropdown-menu .dropdown-item:hover {
  background-color: #0056b3;
  color: white;
}

.dropdown-menu {
  display: none;
  position: absolute;
  background-color: #007bff;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1001;
}

.dropdown:hover .dropdown-menu {
  display: block;
}

@media (max-width: 768px) {
  .dropdown-menu {
    position: fixed;
    top: 50px;
    left: 0;
    width: 100%;
    min-width: initial;
    max-height: calc(100vh - 50px);
    overflow-y: auto;
  }

  .bottom-tab-nav {
    display: flex;
    justify-content: space-around;
    align-items: center;
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    background-color: #007bff;
    color: white;
    padding: 0.5rem 0;
    z-index: 1000;
  }

  .tab-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    color: white;
    text-decoration: none;
  }

  .tab-item span {
    font-size: 0.75rem;
    margin-top: 0.25rem;
  }
}

.button-effect {
  position: relative;
  overflow: hidden;
}

.button-effect::after {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.2);
  transition: left 0.4s ease;
}

.button-effect:hover::after {
  left: 100%;
}
</style>

<script>
import { ValidationProvider } from "vee-validate";
import axios from "axios";
import instance from "../../config/http-client.gateway";
import Swal from "sweetalert2";
const token = sessionStorage.getItem("token");

export default {
  name: "Navbar",
  components: {
    ValidationProvider,
  },
  data() {
    return {
      infoUser: "",
      password2: "",
      password1: "",
      isDropdownOpen: false,
      showModal: false,
      showLoginModal: false,
      showLoginModal2: false,
      showLoginModal3: false,
      showLoginModal4: false,
      showLoginModal5: false,
      showSpinner: false,
      codeSend: "",
      user: {
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
      emailSearch: "",
      isMobile: window.innerWidth <= 768,
      loginEmail: "",
      loginPassword: "",
    };
  },

  mounted() {
    window.addEventListener("resize", this.handleResize);
    this.spinner();
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.handleResize);
  },
  methods: {
    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    onSubmit() {
      this.$validator.validate().then((valid) => {
        if (!valid) {
        } else {
          console.log(this.$data);
          this.showModal = false;
        }
      });
    },

    spinner() {
      this.showSpinner = true;
      setTimeout(() => {
        this.showSpinner = false;
        this.getLibros();
      }, 1500);
    },
    toggleNavbar() {
      this.isNavbarOpen = !this.isNavbarOpen;
    },
    onLoginSubmit() {
      this.$validator.validate().then(async (valid) => {
        if (!valid) {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Por favor completa todos los campos obligatorios.",
          });
        } else {
          try {
            this.showSpinner = true;
            const response = await axios.post("http://localhost:8080/login", {
              username: this.loginEmail,
              password: this.loginPassword,
            });
            const token = response.data.token;

            // Guardar el token en el almacenamiento local
            sessionStorage.setItem("token", token);

            sessionStorage.setItem("isAuthenticated", true);
            this.$router.push("/userHome");
            this.showLoginModal = false;
            this.showSpinner = false;
            window.location.reload();
          } catch (error) {
            Swal.fire({
              icon: "warning",
              title: "Error",
              text: "Nombre de usuario o contraseña incorrectos.",
            });
            console.error("Error al iniciar sesión:", error);
          }
        }
      });
    },
    passwordrecovery() {
      this.$validator.validate().then(async (valid) => {
        if (!valid) {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Por favor completa todos los campos obligatorios.",
          });
        } else {
          try {
            const response = await instance.doGet(`/user/email/${this.emailSearch}`)
            this.infoUser = response.data
            let codigo = "";

            for (let i = 0; i < 6; i++) {
              codigo += Math.floor(Math.random() * 10);
            }

            const response2 = await instance.doPost("/user/actualizar/code", {
              id: `${this.infoUser.id}`,
              code: `${codigo}`
            })
            const response3 = await axios.post("http://localhost:8080/api-sirep/email/", {
              transmitter: "20213tn082@utez.edu.mx",
              receptor: `${response.data.email}`,
              title: `Hola ${response.data.names} Este es tu Código de Verificación ${codigo}`,
              text: `Motivo : Recuperacion de Constraseña`,
              body: "Copia el código y pegalo donde se te pide"
            })

            Swal.fire({
              icon: "success",
              title: "Correcto",
              text: "El código se ah enviado a su correo",
            });
            this.showLoginModal3 = false
            this.showLoginModal4 = true


          } catch (error) {
            Swal.fire({
              icon: "warning",
              title: "Error",
              text: "El  email ingresado no existe en la base",
            });
            console.error("Error al recuperar contrasena", error);
          }
        }
      });
    },

    async changePassword() {
      this.$validator.validate().then(async (valid) => {
        if (!valid) {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Por favor completa todos los campos obligatorios.",
          });
        } else {
          try {

            const response = await instance.doGet(`/user/email/${this.infoUser.email}`)
            if (response.data.verificationCode == this.codeSend) {
              this.showLoginModal4 = false,
                this.showLoginModal5 = true
            } else {
              Swal.fire({
                icon: "warning",
                title: "Error",
                text: "NO es Correcto el código",
              });
            }

          } catch (error) {
            Swal.fire({
              icon: "warning",
              title: "Error",
              text: "Hubo un error al enviar el código de verficiación",
            });
            console.error("Error al recuperar contrasena", error);
          }
        }
      });
    },
    async updatePassword() {
      this.$validator.validate().then(async (valid) => {
        if (!valid) {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Por favor completa todos los campos obligatorios.",
          });
        } else {
          try {
            const response = await instance.doGet(`/user/email/${this.infoUser.emailSearch}`)
            if (this.password1 == this.password2) {
              const response2 = await instance.doPost("/user/actualizar/password", {
                id: `${this.infoUser.id}`,
                password: `${this.password1}`

              })
              Swal.fire({
                icon: "success",
                title: "Éxito",
                text: "Contraseña modificada exitosamente.",
              }).then(() => {
                window.location.reload();
              });
              
            } else {
              Swal.fire({
                icon: "infgo",
                title: "Error",
                text: "Las contraseñas deben ser iguales",
              });
            }

          } catch (error) {
            Swal.fire({
              icon: "warning",
              title: "Error",
              text: "Hubo un error al enviar el código de verficiación",
            });
            console.error("Error al recuperar contrasena", error);
          }
        }
      });
    },


    async onSubmit() {
      this.showSpinner = true;
      const isValid = await this.$validator.validateAll();

      if (!isValid) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Por favor completa todos los campos obligatorios.",
        });
        this.showSpinner = false;
        return;
      }

      try {
        const response = await instance.doGet(`/user/username/${this.user.username}`);
        const response2 = await instance.doGet(`/user/email/${this.user.email}`);
        if (response.data != null) {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "No puedes poner ese username porque ya existe.",
          });
        } else if (response2.data != null) {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "No puedes poner ese email porque ya existe.",
          });
        } else {
          const formData = new FormData();
          formData.append("profilePicture", this.user.profilePicture);

          const uploadResponse = await axios.post(
            "http://localhost:8080/api-sirep/user/upload",
            formData
          );

          this.user.profilePicture = `${uploadResponse.data}`;

          await this.registerUser();

          Swal.fire({
            icon: "success",
            title: "Éxito",
            text: "Usuario registrado exitosamente.",
          });

          this.showModal = false;
        }
      } catch (error) {
        console.error(error);
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "Hubo un error al procesar la solicitud.",
        });
      } finally {
        this.showSpinner = false; // Ocultar el spinner después de completar la operación
      }
    },


    async registerUser() {
      try {
        const response = await instance.doPost("/user/", {
          names: this.user.names,
          lastName: this.user.lastName,
          email: this.user.email,
          phone: this.user.phone,
          address: this.user.address,
          date: this.user.date,
          gender: this.user.gender,
          profilePicture: this.user.profilePicture,
          role: this.user.role,
          username: this.user.username,
          password: this.user.password,
          status: this.user.status,
        });
        if ((response.status = 200)) {
          Swal.fire({
            icon: "success",
            title: "Usuario Registrado Correctamente",
          }).then(() => {
            window.location.reload();
          });
        } else {
          Swal.fire({
            icon: "Error",
            title: "Hubo un error al registrar el usuario",
          });
        }
      } catch (error) {
        console.error(error);
        alert("Hubo un error al registrar el usuario.");
      }
    },

    handleResize() {
      this.isMobile = window.innerWidth <= 768;
    },
    hideLoginModal() {
      this.showLoginModal = false;
    },
  },
};
</script>
