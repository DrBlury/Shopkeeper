import Vue from 'vue'
import App from './App.vue'
import router from './router'

import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);

axios.defaults.baseURL = "http://localhost:8090";
axios.defaults.headers.common['Accept'] = "application/json";


// Add Vuetify to the Project
import Vuetify from "vuetify";
import 'vuetify/dist/vuetify.css'
import 'vuetify/dist/vuetify.js'
Vue.use(Vuetify);

// Add BootstrapVue to the Project
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
Vue.use(BootstrapVue);

Vue.use(Vuex)
import Vuex from "vuex";

Vue.config.productionTip = false;

Vue.prototype.$eventHub = new Vue();


const store = new Vuex.Store({
    state: {
        cart: [],
        cartItems: 0,
        products: [],
        businessCustomer: false,
        count: 0
    },
    mutations: {
        setProducts (state, products) {
            state.products = products;
        },
        switchCustomerType (state) {
            state.businessCustomer = !state.businessCustomer;
        },
        addCartItem (state, itemToAdd) {
            var alreadyAdded = false;
            state.cartItems++;
            //Add the item to cart at the frontend
            for (var i = 0; i < state.cart.length; i++) {
                if (state.cart[i].id == itemToAdd.id) {
                    state.cart[i].amount++;
                    alreadyAdded = true;
                }
            }
            if (!alreadyAdded) {
                state.cart.push({id: itemToAdd.id, amount: 1})
            }
        },
        removeCartItem (state, itemToRemove) {
            for (var i = 0; i < state.cart.length; i++) {
                if (state.cart[i].id === itemToRemove.id) {
                    state.cart[i].amount--;
                    state.cartItems--;
                    if (state.cart[i].amount <= 0) {
                        state.cart.splice(i, 1);
                    }
                }
            }
        },
    }
})



new Vue({
    vuetify: new Vuetify(),
    router,
    store,
    render: h => h(App)
})
    .$mount('#app');

