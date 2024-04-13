<template>
    <b-container fluid>
        <CategoriesNavbar />
        <NavbarUser />
        <div class="main-content ">
            <AprobacionAnfi />
        </div>
    </b-container>
</template>

<script>
import NavbarUser from "../../components/Inicio/NavbarUser.vue";
import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
import AprobacionAnfi from "../../components/AprobacionAnfitriones/AprobacionAnfi.vue";

export default {
    name: 'Inicio',
    components: {
        NavbarUser,
        CategoriesNavbar,
        AprobacionAnfi
    },

}

</script>

<style scoped>
.app-container {
    display: flex;
    flex-direction: column;
}

.main-content {
    margin-top: calc(3.5rem + 70px);
    padding: 1rem;
}
</style>