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

import Vuex from "vuex";
Vue.use(Vuex)

Vue.config.productionTip = false;

Vue.prototype.$eventHub = new Vue();


const store = new Vuex.Store({
    state: {
        customerTemplates: [],
        carts: [],
        cart: [],
        cartItems: 0,
        products: [],
        businessCustomer: false,
        count: 0,
        activeCart: 0,
        response: "",
        errors: [],
        activeCartText: "",
    },
    mutations: {
        setProducts (state, products) {
            state.products = products;
        },
        saveCart (state, customer) {
            customer.id = 0;
            let customerToBeStored = JSON.parse(JSON.stringify(customer))
            state.carts.push(customerToBeStored);
            store.commit('loadCart', 0);
        },
        createNewCart (state, cart) {
            /// Get the new cart ID
            let highest = 0
            for (let i = 0; i < state.carts.length; i++) {
                if (state.carts[i].id >= highest) {
                    highest = state.carts[i].id + 1;
                }
            }
            cart.id = highest;
            let cartToBeStored = JSON.parse(JSON.stringify(cart))
            state.carts.push(cartToBeStored);
            store.commit('loadCart', highest);
        },
        deleteCart (state, id) {
            for (let i = 0; i < state.carts.length; i++) {
                if (state.carts[i].id === id) {
                    state.carts.splice(i, 1);
                }
            }
        },
        loadCart(state, id) {
            console.log("Loading cart:" + id);
            state.cart = [];
            state.cartItems = 0;
                // Find the cart to be loaded
                for (let i = 0; i < state.carts.length; i++) {
                    if (state.carts[i].id === id) {
                        // Overwrite the existing cart
                        state.cart = state.carts[i].customer.cart;
                        // Iterate over every item in the cart
                        for (let j = 0; j < state.cart.length; j++) {
                            state.cartItems += state.cart[j].amount;
                        }
                        state.activeCart = id;
                        state.activeCartText = "Active Cart:\n"
                            + state.carts[state.activeCart].customer.firstname
                            + " "
                            + state.carts[state.activeCart].customer.lastname

                    }
                }
        },
        changeCustomerType (state) {
            state.businessCustomer = !state.businessCustomer;
        },
        createNewCustomer (state, customer) {
            /// Get the new customer ID
            let highest = 0;
            for (let i = 0; i < state.customerTemplates.length; i++) {
                if (state.customerTemplates[i].id >= highest) {
                    highest = state.customerTemplates[i].id + 1;
                }
            }
            customer.id = highest;
            let customerToBeStored = JSON.parse(JSON.stringify(customer))
            state.customerTemplates.push(customerToBeStored);
        },
        updateCustomer (state, customer) {
            console.log(customer.id)
            for (let i = 0; i < state.customerTemplates.length; i++) {
                if (state.customerTemplates[i].id === customer.id) {
                    state.customerTemplates.splice(i, 1);
                }
            }
            store.commit('createNewCustomer', customer);
        },
        modifyCartItem (state, change) {
            let alreadyAdded = false;

            //Add the item to cart at the frontend
            for (let i = 0; i < state.cart.length; i++) {
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
                state.cartItems++;
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

