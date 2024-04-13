<template>
  <b-container fluid class="category-admin">
    <b-row class="mb-3">
      <b-col>
        <h2>Administración de Categorías</h2>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-button variant="primary" @click="showAddModal = true">Agregar Categoría</b-button>
      </b-col>
    </b-row>
    <b-row class="mt-3">
      <b-col>
        <b-table class="category-table" striped hover :items="categories" :fields="fields">
          <template #cell(actions)="data">
            <b-button size="sm" variant="warning" @click="openEditModal(data.item)"><b-icon
                icon="pencil-square"></b-icon></b-button>
                <b-button size="sm" variant="danger" @click="deleteCategory(data.item.id)"><b-icon
                icon="trash"></b-icon></b-button>
          </template>
        </b-table>
      </b-col>
    </b-row>

    <b-modal v-model="showAddModal" title="Agregar Categoría" @ok="addCategory" header-bg-variant="dark"
      header-text-variant="light" body-bg-variant="light" footer-bg-variant="dark" footer-text-variant="light">
      <b-form @submit.prevent="addCategory">
        <b-form-group label="Nombre de la categoría" label-for="add-category-name">
          <b-form-input id="add-category-name" v-model="newCategory.name" required></b-form-input>
        </b-form-group>

        <b-form-group label="Descripción" label-for="add-category-description">
          <b-form-textarea v-validate="'required|min:10'" id="add-category-description" name="description"
            v-model="newCategory.description" />
          <span class="text-danger">{{
            errors.first("description")
          }}</span>
        </b-form-group>


        <b-form-group label="Icono de la categoría" label-for="add-category-icon">
          <b-form-select id="add-category-icon" v-model="newCategory.icon" :options="iconOptions"></b-form-select>
          <div class="icon-preview">
            <b-icon :icon="newCategory.icon" font-scale="2"></b-icon>
          </div>
        </b-form-group>
      </b-form>
    </b-modal>

    <!-- Modal para editar categoría -->
    <b-modal v-model="showEditModal" title="Editar Categoría" @ok="updateCategory" header-bg-variant="dark"
      header-text-variant="light" footer-bg-variant="dark" footer-text-variant="light">
      <b-form @submit.prevent="updateCategory">
        <b-form-group label="Nombre de la categoría" label-for="edit-category-name">
          <b-form-input id="edit-category-name" v-model="editCategory.name" required></b-form-input>
        </b-form-group>

        <b-form-group label="Descripción" label-for="edit-category-description">
          <b-form-textarea v-validate="'required|min:10'" id="edit-category-description" name="description"
            v-model="editCategory.description" />
          <span class="text-danger">{{
            errors.first("description")
          }}</span>
        </b-form-group>


        <b-form-group label="Icono de la categoría" label-for="edit-category-icon">
          <b-form-select id="edit-category-icon" v-model="editCategory.icon" :options="iconOptions"></b-form-select>
          <div class="icon-preview">
            <b-icon :icon="editCategory.icon" font-scale="2"></b-icon>
          </div>
        </b-form-group>
      </b-form>
    </b-modal>
  </b-container>
</template>

<script>
import instance from '../../config/http-client.gateway';
export default {
  name: 'AdministracionCategorias',
  data() {
    return {
      categories: [
      ],
      fields: [
        { key: 'name', label: 'Nombre' },
        { key: 'actions', label: 'Acciones' }
      ],
      showAddModal: false,
      showEditModal: false,
      newCategory: {
        name: '',
        description: '',
        icon: ''
      },
      editCategory: {
        id: null,
        name: '',
        description: '',
        icon: ''
      }
    };
  },
  computed: {
    iconOptions() {
      return [
        { value: 'house-fill', text: 'Casa' },
        { value: 'building', text: 'Apartamento' },
        { value: 'umbrella', text: 'Playa' },
        { value: 'tree', text: 'Villa' },
        { value: 'building', text: 'Edificio' },
        { value: 'tree', text: 'Cabaña' },
        { value: 'snow', text: 'Montaña' },
        { value: 'suit-heart', text: 'Romántico' },
        { value: 'briefcase', text: 'Negocios' },
        { value: 'shop', text: 'Compras' },
        { value: 'music-note', text: 'Música' },
        { value: 'palette', text: 'Arte' },
        { value: 'book', text: 'Cultura' },
        { value: 'heart', text: 'Familiar' },
        { value: 'star', text: 'Lujo' },
        { value: 'lightning', text: 'Aventura' },
        { value: 'cloud-sun', text: 'Rural' },
        { value: 'cloud-moon', text: 'Nocturno' }

      ];
    }
  },

  methods: {
    addCategory() {
      if (this.newCategory.name.trim() !== '') {
        instance
          .doPost('/category/', {
            name: this.newCategory.name,
            description: this.newCategory.description,
            icon: this.newCategory.icon
          })
          .then(response => {
            this.categories.push(response.data);
            alert('Categoría creada correctamente');
            window.location.reload();
            this.showAddModal = false;
          })
          .catch(error => {
            console.error('Error al crear la categoría:', error);
          });
      }
    },
    async category() {
      const response = await instance.doGet('/category/')
      this.categories = response.data.data;
      console.log(response.data.data);

    },
    openEditModal(category) {
      this.editCategory = { ...category };
      this.showEditModal = true;
    },

    async updateCategory() {
      try {
        const response = await instance
          .doPost('/category/actualizar/', {
            id: this.editCategory.id,
            name: this.editCategory.name,
            description: this.editCategory.description,
            icon: this.editCategory.icon
          })

        console.log(response.data);

        alert("Categoria actualizada exitosamente.");
        window.location.reload();
      } catch (error) {
        console.error(error);
        alert("Hubo un error al actualizar el usuario.");
      }
    },


    async deleteCategory(id) {
  try {
    const response = await instance.doDelete(`/category/${id}`);

    // Filtrar la categoría eliminada de la lista local
    this.categories = this.categories.filter(category => category.id !== id);

    alert("Categoría eliminada exitosamente.");
  } catch (error) {
    console.error(error);
    alert("Hubo un error al eliminar la categoría.");
  }
}

},
mounted(){
  this.category();
}
};

</script>

<style scoped>
.modal-header,
.modal-body,
.modal-footer {
  background-color: #343a40;
  /* Color de fondo oscuro */
  color: #ffffff;
  /* Color de texto claro */
}

.modal-content {
  border-color: #343a40;
  /* Color del borde del modal */
}

.close {
  color: #ff0000;
  /* Color del botón de cierre */
}

.category-admin {
  padding: 1rem;
}

.b-table tr {
  /*cover*/
  background-color: #007bff;
}


.icon-preview {
  margin-top: 1rem;
  display: flex;
  justify-content: center;
}
</style>
