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
        count: 0,
        activeCustomer: "",
        response: "",
        errors: [],
    },
    mutations: {
        setProducts (state, products) {
            state.products = products;
        },
        changeCustomerType (state) {
            state.businessCustomer = !state.businessCustomer;
        },
        modifyCartItem (state, change) {
            var alreadyAdded = false;

            //Add the item to cart at the frontend
            for (var i = 0; i < state.cart.length; i++) {
                if (state.cart[i].item.id === change.item.id) {
                    if (change.operation === "add") {
                        state.cart[i].amount++;
                        state.cartItems++;
                    } else {
                        state.cart[i].amount--;
                        state.cartItems--;
                        if (state.cart[i].amount <= 0) {
                            state.cart.splice(i, 1);
                        }
                    }
                    alreadyAdded = true;
                    continue;
                }
            }
            if (!alreadyAdded) {
                state.cart.push({item: change.item, amount: 1});
            }

            Vue.axios.get(`/api/modifyCart`, {
                params: {
                    id : change.item.id,
                    username: change.username,
                    operation: change.operation,
                }
            }).then(response => {
                // JSON responses are automatically parsed.
                state.response = response.data;
            }).catch(e => {
                state.errors.push(e)
            })
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

