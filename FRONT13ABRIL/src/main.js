import Vue from 'vue'

import App from './App.vue'
import router from './router'
import VeeValidate  from 'vee-validate'
import { Validator } from 'vee-validate';
import VCalendar from 'v-calendar';



import './assets/main.css'

import { BootstrapVue, IconsPlugin, BootstrapVueIcons } from 'bootstrap-vue'


import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

Vue.use(VCalendar, {
  componentPrefix: 'vc', 
});

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(VeeValidate)


Validator.localize('es', {
  messages: {
    alpha: () => 'Este campo solo puede contener letras.',
    alpha_spaces: 'Solo acepta letras y espacios',
    required: () => 'Este campo es obligatorio.',
    digits:(filedName,[digito])=>`Deben ser ${digito} digitos y solo numeros`,
    date_format : 'El formato debe ser dd/MM/yyyy',
    date_between : "No esta en las fechas de 10/09/1954 al 20/09/2005",
    alpha_num: 'Solo acepta numeros y letras',
    max : (field,[num])=>`Solo ${num} como maximo`, 
    credit_card:"Debe ser una tarjeta de crédito/debito válida.",
    email:"El correo debe ser valido",
    alpha_dash:"Solo puede contener caracteres alfabéticos, números, guiones o guiones bajos.",
    min : (field,[num])=>`Debe de contener ${num} caracteres como minimo`,
    included:"Debes de seleccionar una opcion (Masculino/Femenino)",
    curp: 'El CURP ingresado no es válido.',
    rfc: 'El RFC ingresado no es válido.',
    phone_format:"El telefono no es valido",
    numeric:'Solo acepta numeros',
    min_value:(field,[num])=>`Debe de ser mayor a ${num}`,
  }
});



// Definición y registro de la regla de validación personalizada
const customRules = {
  getMessage(field, args) {
    return 'Debes ser mayor de 18 años.';
  },
  validate(value, args) {
    const currentDate = new Date();
    const birthdate = new Date(value);
    const age = currentDate.getFullYear() - birthdate.getFullYear();
    return age >= 18;
  }
};

Validator.extend('adult', customRules);

const customRules2 = {
  getMessage(field, args) {
    return 'La imagen debe ser menor a 2MB.';
  },
  validate(value, args) {
    // Verificar si el tamaño de la imagen es menor a 2MB
    return value ? value.size / 1024 / 1024 < 2 : false;
  }
};

Validator.extend('imageSize', customRules2);




Validator.extend('noContarEspacios', {
  getMessage(field, args) {
    return "El campo debe tener al menos 10 caracteres (sin contar espacios)";
  },
  validate(value, args) {
    // Remover los espacios en blanco del texto
    const textWithoutSpaces = value.replace(/\s/g, '');
    // Validar la longitud del texto sin espacios
    return textWithoutSpaces.length >= args[0];
  },
});
Validator.extend('curp', {
  validate: value => {
    const curpRegex = /^[A-Z]{4}[0-9]{6}[A-Z]{2}[A-Z]{5}[0-9]{1}$/;
    return curpRegex.test(value);
  }
});

Validator.extend('rfc', {
  validate: value => {
    const rfcRegex = /^[A-Z&Ñ]{3,4}[0-9]{6}[A-V1-9][A-Z1-9][0-9A]$/;
    return rfcRegex.test(value.toUpperCase());
  },
  message: 'El RFC debe ser válido.'
});



Validator.extend('phone_format', {
  validate: value => {
    const phoneRegex = /^[0-9]{10}$/;
    return phoneRegex.test(value);
  }
});

const noSpaceRule = {
  getMessage(field, args) {
    return 'La contraseña no debe contener espacios.';
  },
  validate(value, args) {
    // Verificar si la contraseña no contiene espacios
    return !/\s/.test(value);
  }
};

Validator.extend('noSpace', noSpaceRule);

new Vue({
  router,
  render: (h) => h(App)
}).$mount('#app')


