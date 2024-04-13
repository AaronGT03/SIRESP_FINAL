<template>
  <div class="categories-navbar">
    <div class="category-item">
      <button class="category-item btn btn-succes" @click="$emit('ejecutar-funcion',0 )">
      <b-icon icon="door-open-fill" font-scale="2" ></b-icon>
      <span >Todos</span>
    </button>
    </div>
    
    <div class="category-item" v-for="category in categories" :key="category.id">
      <button class="category-item btn btn-succes" @click="$emit('ejecutar-funcion',category.id )">
      <b-icon  :icon="category.icon" font-scale="2" ></b-icon>
      <span >{{ category.name }}</span>
    </button>

    </div>
  </div>
</template>

<script>
import instance from '../../config/http-client.gateway';
export default {
  props: {
    categories: []
  },
  methods: {
    async category() {
      const response = await instance.doGet('/category/')
      this.categories= response.data.data;
      console.log(response.data.data);
      
    },

  },
  mounted(){
this.category();
  }
    
  
}
</script>

<style scoped>
.categories-navbar {
  background-color: #f8f9fa;
  color: #007bff;
  padding: 0.5rem 1rem;
  position: fixed;
  top: 3.5rem;
  left: 0;
  width: 100%;
  z-index: 1000;
  display: flex;
  justify-content: center;
  flex-wrap: nowrap;
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
}

.category-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-decoration: none;
  padding: 0.5rem 0.35rem;
  margin: 0 10px;
}

.category-item span {
  margin-top: 0.5rem;
  color: #007bff;
}

.category-item:hover {
  background-color: #e2e6ea;
  border-radius: 0.25rem;
  cursor: pointer;
}

.category-item b-icon {
  margin-bottom: 0.5rem;
  color: #007bff;
}
</style>
