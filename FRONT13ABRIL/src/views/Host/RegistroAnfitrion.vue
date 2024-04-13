<template>
    <b-container fluid>
        <CategoriesNavbar />
        <NavbarUser />
        <div class="main-content">
            <Registro />
        </div>
    </b-container>
</template>
<script>
import NavbarUser from "../../components/Inicio/NavbarUser.vue";
import CategoriesNavbar from "../../components/Inicio/CategoriesNavbar.vue";
import Registro from "../../components/RegistroAnfitrion/Registro.vue";

export default {
    name: 'RegistroAnfitrion',
    components: {
        NavbarUser,
        CategoriesNavbar,
        Registro
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