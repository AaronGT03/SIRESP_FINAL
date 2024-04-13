<template>
    <b-container fluid>
        <CategoriesNavbar />
        <NavbarUser />
        <div class="main-content">
            <Modificar />
        </div>
    </b-container>
</template>
<script>
import NavbarUser from "../../components/Inicio/NavbarUser.vue";
import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
import Modificar from "../../components/ModificarAnfitrion/Modificar.vue";

export default {
    name: 'RegistroAnfitrion',
    components: {
        NavbarUser,
        CategoriesNavbar,
        Modificar
    },

}

</script>

<style>
.app-container {
    display: flex;
    flex-direction: column;
}

.main-content {
    margin-top: calc(3.5rem + 50px);
    padding: 1rem;
}
</style>