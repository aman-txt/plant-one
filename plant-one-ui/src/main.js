import Vue from 'vue'
import App from './App.vue'

import BootstrapVue from 'bootstrap-vue/dist/bootstrap-vue.esm';
import Vuelidate from 'vuelidate'
import VueCookies from 'vue-cookies'

import "@/components"
import "@/interceptors/axios"

import {
  default as AppRouter
} from '@/router'

import AppStore from '@/store/store'

import VuePlantOnePlugin from "@/plugins"

Vue.use(BootstrapVue);
Vue.use(Vuelidate);
Vue.use(VueCookies)
Vue.use(VuePlantOnePlugin);

Vue.config.productionTip = false

new Vue({
  router: AppRouter,
  store: AppStore,
  render: h => h(App),
}).$mount('#app')
