
export default {
    state: {
      isAuth: false,
      isAdmin: false,
      user: null
    },
    mutations: {
        login(state, { isAdmin, user }) {
            state.isAuth = true;
            state.isAdmin = isAdmin;
            state.user = user;
            localStorage.setItem('isAuthenticated', true);
            localStorage.setItem('userRole', isAdmin ? 'admin' : 'user');
          },
          logout(state) {
            state.isAuth = false;
            state.isAdmin = false;
            state.user = null;
            localStorage.removeItem('isAuthenticated');
            localStorage.removeItem('userRole');
          }
    },
    actions: {
        async login({ commit }, { username, password }) {
            try {
              // Realizar la lógica de autenticación, por ejemplo, enviar una solicitud al servidor
              // Si la autenticación es exitosa, actualizar el estado
              const user = await authService.login(username, password); // Suponiendo que tienes un servicio de autenticación
              const isAdmin = user.role === 'admin'; // Suponiendo que el servidor devuelve el rol del usuario
              commit('login', { isAdmin, user });
              return user;
            } catch (error) {
              // Manejar errores de autenticación, por ejemplo, mostrar un mensaje de error al usuario
              throw error;
            }
          },
          async logout({ commit }) {
            try {
              // Realizar la lógica de cierre de sesión, por ejemplo, enviar una solicitud al servidor
              // Si el cierre de sesión es exitoso, actualizar el estado
              await authService.logout(); // Suponiendo que tienes un servicio de autenticación
              commit('logout');
            } catch (error) {
              // Manejar errores de cierre de sesión
              throw error;
            }
          }
    },
    getters: {
      // Getter para verificar si el usuario está autenticado
      isAuthenticated: state => state.isAuth
    }
  };