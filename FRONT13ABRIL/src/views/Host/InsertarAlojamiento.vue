<template>
    <div>
        <b-container fluid >

            <NavbarUser />
            <div class="main-content">
                <AlojamientoCrear />
            </div>
        </b-container>
    </div>

</template>

<script>
import NavbarUser from "../../components/Inicio/NavbarUser.vue";
import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
import AlojamientoCrear from "../../components/FormAlojamiento/AlojamientoCrear.vue";

export default {
    name: 'Inicio',
    components: {
        NavbarUser,
        CategoriesNavbar,
        AlojamientoCrear
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