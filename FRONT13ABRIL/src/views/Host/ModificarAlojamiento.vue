<template>
    <div>
        <b-container fluid >
            <CategoriesNavbar />
            <NavbarUser />
            <div class="main-content">
                <AlojamientoModificar />
            </div>
        </b-container>
    </div>

</template>

<script>
import NavbarUser from "../../components/Inicio/NavbarUser.vue";
import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
import AlojamientoModificar from "../../components/FormAlojamiento/AlojamientoModificar.vue";

export default {
    name: 'Inicio',
    components: {
        NavbarUser,
        CategoriesNavbar,
        AlojamientoModificar
    },

}

</script>

<style scoped>
.app-container {
    display: flex;
    flex-direction: column;
}

.main-content {
    margin-top: calc(3.5rem + 100px);
    padding: 1rem;
}

</style>