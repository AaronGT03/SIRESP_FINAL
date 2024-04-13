<template>
  <div>
    <b-modal
      v-model="showModal"
      title="Modificacion de Usuario"
      hide-footer
      size="lg"
      modal-class="custom-modal"
      header-bg-variant="primary"
      header-text-variant="light"
      footer-bg-variant="dark"
      footer-text-variant="light"
      style="border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1)"
    >
      <b-form @submit.prevent="onSubmit" class="modal-form">
        <b-row>
          <b-col cols="6">
            <b-form-group label="Nombre">
              <b-form-input
                v-validate="'required|alpha_spaces|min:3'"
                name="name"
                v-model="user.names"
                type="text"
              />
              <span class="text-danger">{{ errors.first("name") }}</span>
            </b-form-group>
          </b-col>
          <b-col cols="6">
            <b-form-group label="Apellidos">
              <b-form-input
                v-validate="'required|alpha_spaces|min:3'"
                name="surname"
                v-model="user.lastName"
                type="text"
              />
              <span class="text-danger">{{ errors.first("surname") }}</span>
            </b-form-group>
          </b-col>
        </b-row>

        <b-row>
          <b-col cols="12">
            <b-form-group label="Dirección">
              <b-form-input
                v-validate="'required'"
                name="address"
                v-model="user.address"
                type="text"
              />
              <span class="text-danger">{{ errors.first("address") }}</span>
            </b-form-group>
          </b-col>
        </b-row>

        <b-row>
          <b-col cols="6">
            <b-form-group label="Fecha de Nacimiento">
              <b-form-input
                v-validate="'required|adult'"
                name="birthdate"
                v-model="user.date"
                type="date"
              />
              <span class="text-danger">{{ errors.first("birthdate") }}</span>
            </b-form-group>
          </b-col>
          <b-col cols="6">
            <b-form-group label="Teléfono">
              <b-form-input
                v-validate="'required|digits:10'"
                name="telephone"
                v-model="user.phone"
                type="text"
              />
              <span class="text-danger">{{ errors.first("telephone") }}</span>
            </b-form-group>
          </b-col>
        </b-row>

        <b-row>
          <b-col cols="6">
            <b-form-group label="Género">
              <b-form-select
                v-validate="'required'"
                name="gender"
                v-model="user.gender"
              >
                <option value="">Seleccionar Género</option>
                <option value="masculino">Masculino</option>
                <option value="femenino">Femenino</option>
              </b-form-select>
              <span class="text-danger">{{ errors.first("gender") }}</span>
            </b-form-group>
          </b-col>

          <b-col cols="6">
            <b-form-group label="Imagen de perfil">
              <b-form-file
                v-validate="'required'"
                name="profileImage"
                v-model="user.profilePicture"
                placeholder="Seleccione una imagen"
                accept="image/*"
              >
              </b-form-file>

              <img
                v-if="user.profilePicture"
                :src="user.profilePicture"
                alt="Imagen modificada"
                style="max-width: 100px; max-height: 100px"
              />
              <span class="text-danger">{{
                errors.first("profileImage")
              }}</span>
            </b-form-group>
          </b-col>
        </b-row>
        <b-row>
          <b-col cols="12">
            <b-form-group label="Correo electrónico">
              <b-form-input
                v-validate="'required|regex:^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]'"
                name="email"
                v-model="user.email"
                type="email"
              />
              <span class="text-danger">{{ errors.first("email") }}</span>
            </b-form-group>
          </b-col>
        </b-row>

        <div class="text-center">
          <b-button style="margin-top: 5%" type="submit" variant="primary"
            >Enviar</b-button
          >
        </div>
      </b-form>
    </b-modal>

    <nav class="navbar">
      <div class="container">
        <a class="navbar-brand button-effect" href="/userHome">
          <img
            src="../../assets/img/Logo.jpeg"
            alt="Vue.js"
            width="40"
            height="40"
          />
          <span class="title">SIRESP</span>
        </a>

        <div class="navbar-menu">
          <div class="dropdown" @click="toggleSubmenu('perfil')">
            <a class="dropdown-item button-effect name-user">
              {{ user.names }} {{ user.lastName }} <b-icon :icon="'caret-down'"
            /></a>

            <div class="dropdown-menu" v-if="isSubmenuOpen.perfil">
              <a class="dropdown-item" href="/perfil"
                ><b-icon icon="person" /> Ver Perfil</a
              >
              <a class="dropdown-item" @click="showModal = true">
                <b-icon icon="pencil" /> Modificar Perfil</a
              >
            </div>
          </div>

          <div
            class="dropdown"
            v-if="(isUser && !isPendiente()) || isHost"
            @click="toggleSubmenu('anfitrion')"
          >
            <a class="dropdown-item button-effect"
              >Anfitrión <b-icon :icon="'caret-down'"
            /></a>
            <div class="dropdown-menu" v-if="isSubmenuOpen.anfitrion">
              <a class="dropdown-item" v-if="isUser" href="/registroAnfitrion"
                ><b-icon icon="person-plus" /> Registrarse como Anfitrión</a
              >
              <a class="dropdown-item" href="/alojamiento" v-if="isHost"
                ><b-icon icon="house-door" /> Mostrar mis alojamientos</a
              >
              <a
                class="dropdown-item"
                href="/insertarAlojamiento"
                v-if="isHost"
              >
                <b-icon icon="plus-circle" /> Insertar Alojamiento</a
              >
              
            </div>
          </div>

          <div
            class="dropdown"
            v-if="isAdmin"
            @click="toggleSubmenu('administracion')"
          >
            <a class="dropdown-item button-effect"
              >Administrador <b-icon :icon="'caret-down'"
            /></a>
            <div class="dropdown-menu" v-if="isSubmenuOpen.administracion">
              <a class="dropdown-item" href="/aprobacionAlojamientos"
                ><b-icon icon="check2-square" /> Aprobación de Alojamientos</a
              >
              <a class="dropdown-item" href="/administrarCategorias">
                <b-icon icon="list-ul" /> Administrar Categorías</a
              >
              <a class="dropdown-item" href="/administrarUsuarios">
                <b-icon icon="people" /> Administrar Usuarios</a
              >
              <a class="dropdown-item" href="/historialVerifAnf">
                <b-icon icon="clock-history" /> Historial de Anfitriones</a
              >
              <a class="dropdown-item" href="/historialVerifAloj">
                <b-icon icon="clock-history" /> Historial de Alojamientos</a
              >
              <a class="dropdown-item" href="/historialPagos">
                <b-icon icon="credit-card" /> Historial de Pagos</a
              >
              <a class="dropdown-item" href="/historialReservas">
                <b-icon icon="calendar-check" /> Historial de Reservas</a
              >
              <a class="dropdown-item" href="/historialCancelaciones">
                <b-icon icon="calendar-x" /> Historial de Cancelaciones</a
              >
            </div>
          </div>

          <a class="dropdown-item logout button-effect" @click="logout"
            ><b-icon icon="door-closed"
          /></a>
        </div>

        <div class="bottom-tab-nav" v-if="isMobile">
          <a class="tab-item" href="/userHome">
            <b-icon icon="house-door" font-scale="1.5"></b-icon>
            <span>Inicio</span>
          </a>
          <a class="tab-item" href="/perfil">
            <b-icon icon="person" font-scale="1.5"></b-icon>
            <span>Perfil</span>
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

.navbar-toggler {
  display: none;
  background: none;
  border: none;
  color: white;
}

.navbar-brand,
.navbar-menu {
  display: flex;
  align-items: center;
}

.navbar-item {
  color: white;
  text-decoration: none;
  padding: 0.5rem 0.75rem;
}

.navbar-item:hover,
.dropdown-item:hover {
  background-color: #0056b3;
  color: white;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-menu {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1001;
}

.dropdown-item {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
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

.dropdown-submenu .dropdown-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dropdown-item {
  color: white;
  background-color: #007bff;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  transition: background-color 0.3s ease;
}

.dropdown-menu {
  background-color: #007bff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.logout {
  background-color: transparent;
  color: white;
  padding: 10px 15px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.logout:hover {
  background-color: #750303;
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

.name-user {
  color: white;
  font-weight: bold;
}
</style>

<script>
import axios from "axios";
import instance from "../../config/http-client.gateway";
import Swal from "sweetalert2";

export default {
  name: "Navbar",
  data() {
    return {
      isDropdownOpen: false,
      showModal: false,
      isMobile: window.innerWidth <= 768,
      showSpinner: false,
      isSubmenuOpen: {
        perfil: false,
        anfitrion: false,
        administracion: false,
      },
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
      image: null,
    };
  },
  mounted() {
    this.getUsers();
    this.checkUserRole();
    this.spinner();
    
    window.addEventListener("resize", this.handleResize);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.handleResize);
  },
  methods: {
    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },

    async onSubmit() {
      this.$validator.validate().then(async (valid) => {
        if (!valid) {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Por favor completa todos los campos obligatorios.",
          });
        } else {
          try {
            console.log(this.user.profilePicture);

            if (typeof this.user.profilePicture === "string") {
              this.user.profilePicture = this.user.profilePicture;
            } else {
              const formData = new FormData();
              formData.append("profilePicture", this.user.profilePicture);

              const response = await axios.post(
                "http://localhost:8080/api-sirep/user/upload",
                formData
              );
              this.user.profilePicture = response.data;
              console.log(response.data);
            }

            this.updateUsers();

            this.showModal = false;
          } catch (error) {
            console.error(error);
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Hubo un error al registrar el usuario.",
            });
          }
        }
      });
    },
    async updateUsers() {
      try {
        const response = await instance.doPost(`/user/actualizar/`, {
          id: this.user.id,
          names: this.user.names,
          lastName: this.user.lastName,
          username: this.user.username,
          role: this.user.role,
          address: this.user.address,
          date: this.user.date,
          phone: this.user.phone,
          gender: this.user.gender,
          email: this.user.email,
          profilePicture: this.user.profilePicture,
          password: this.user.password,
          status: this.user.status,
        });

        console.log(response.data);

        Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Usuario actualizado exitosamente.",
        });
        window.location.reload();
      } catch (error) {
        console.error(error);
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Hubo un error al actualizar el usuario.",
        });
      }
    },
    async getUsers() {
      const token = sessionStorage.getItem("token");
      console.log(token);
      const claims = JSON.parse(atob(token.split(".")[1]));
      const username = claims.sub;
      console.log(username);
      const response = await instance.doGet(`/user/username/${username}`);
      const fechaNum = response.data.date;
      let fecha = new Date(fechaNum);

      const dia = fecha.getDate().toString().padStart(2, "0");
      const mes = (fecha.getMonth() + 1).toString().padStart(2, "0");
      const año = fecha.getFullYear();

      const fechaFormateada = `${año}-${mes}-${dia}`;
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
      console.log(response);
    },
    onLoginSubmit() {
      this.$validator.validate().then((valid) => {
        if (!valid) {
        } else {
          console.log(
            "Email:",
            this.loginEmail,
            "Password:",
            this.loginPassword
          );
          this.showLoginModal = false;
        }
      });
    },
    isPendiente() {
      return this.user.status === "pendiente";
    },
    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    handleResize() {
      this.isMobile = window.innerWidth <= 768;
    },
    logout() {
      sessionStorage.removeItem("token");
      sessionStorage.removeItem("isAuthenticated");
      this.$router.push("/");
      window.location.reload();
    },
    checkUserRole() {
      const userRole = sessionStorage.getItem("token");
      if (userRole) {
        const claims = JSON.parse(atob(userRole.split(".")[1]));
        this.isAdmin = claims.authorities.includes(
          '[{"authority":"ROLE_ADMIN"}]'
        );
        this.isUser = claims.authorities.includes(
          '[{"authority":"ROLE_USER"}]'
        );
        this.isHost = claims.authorities.includes(
          '[{"authority":"ROLE_HOST"}]'
        );
      }
    },

    spinner() {
      this.showSpinner = true;
      setTimeout(() => {
        this.showSpinner = false;
      }, 2000);
    },

    toggleSubmenu(submenu) {
      Object.keys(this.isSubmenuOpen).forEach((key) => {
        if (key !== submenu) {
          this.isSubmenuOpen[key] = false;
        }
      });
      this.isSubmenuOpen[submenu] = !this.isSubmenuOpen[submenu];
    },
  },
};
</script>
