import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import store from './store'
import vueMoment from 'vue-moment'

Vue.config.productionTip = false
Vue.use(vueMoment);

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app')
