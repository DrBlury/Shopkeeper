<template>
  <div class="newCustomer">
    <!-- SHOW ALL CARTS-->

    <v-card>
      <v-card-title>
        <h2><b>Open Carts</b></h2>
        <v-spacer></v-spacer>
        <v-text-field
                v-model="cartSearch"
                append-icon="search"
                label="Search"
                single-line
                hide-details
        ></v-text-field>
      </v-card-title>

      <v-data-table
              :headers="this.cartListHeaders"
              :items="this.carts"
              :search="cartSearch"
              :items-per-page="5"
              class="elevation-1"
      >
        <template v-slot:item.firstName="{ item }">
          <b> {{ item.customer.firstname }} </b>
        </template>

        <template v-slot:item.lastName="{ item }">
          <b> {{ item.customer.lastname }} </b>
        </template>

        <template v-slot:item.action="{ item }">
          <div v-if='item.id != activeCart'>
            <v-btn color="success" @click="loadCart(item.id)">
              <v-icon>mdi-cached</v-icon>
              Load
            </v-btn>
            <v-btn color="error" @click="deleteCart(item.id)">
              <v-icon>delete</v-icon>
              Delete
            </v-btn>
          </div>
          <div v-else>
            Currently loaded
          </div>

        </template>
      </v-data-table>

    </v-card>


    <div v-if="carts.length === 0">
      <!-- SAVE THE CUSTOMER -->
      <v-card>
        <v-row justify="center">
          <v-dialog v-model="dialog" persistent max-width="600px">

            <template v-slot:activator="{ on }">
              <v-btn color="primary" dark v-on="on">Save Cart</v-btn>
            </template>

            <v-card>
              <v-card-title>
                <span class="headline">Whom was the cart for? (customer of existing cart)</span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field v-model="firstname" label="First name*" required></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field v-model="lastname" label="Last name" required></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field v-model="email" label="Email"></v-text-field>
                    </v-col>
                    <v-card-title>
                      <span class="headline">Address</span>
                    </v-card-title>
                    <v-col cols="12">
                      <v-text-field v-model="streetname" label="Street name"></v-text-field>
                      <v-text-field v-model="number" label="Number"></v-text-field>
                      <v-text-field v-model="zipcode" label="Zipcode"></v-text-field>
                      <v-text-field v-model="city" label="City"></v-text-field>
                      <v-text-field v-model="country" label="Country"></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
                <small>*indicates required field</small>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="dialog = false">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="saveCart">Save</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
      </v-card>
    </div>
    <div v-else>
      <!-- CREATE NEW CUSTOMER -->
      <v-card>
        <v-row justify="center">
          <v-dialog v-model="dialog" persistent max-width="600px">

            <template v-slot:activator="{ on }">
              <v-btn color="primary" dark v-on="on">Create Cart (New Customer)</v-btn>
            </template>

            <v-card>
              <v-card-title>
                <span class="headline">Whom is the cart for?</span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field v-model="firstname" label="First name*" required></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="4">
                      <v-text-field v-model="lastname" label="Last name" required></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field v-model="email" label="Email"></v-text-field>
                    </v-col>
                    <v-card-title>
                      <span class="headline">Address</span>
                    </v-card-title>
                    <v-col cols="12">
                      <v-text-field v-model="streetname" label="Street name"></v-text-field>
                      <v-text-field v-model="number" label="Number"></v-text-field>
                      <v-text-field v-model="zipcode" label="Zipcode"></v-text-field>
                      <v-text-field v-model="city" label="City"></v-text-field>
                      <v-text-field v-model="country" label="Country"></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
                <small>*indicates required field</small>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="dialog = false">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="createNewCart">Create</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

      <!-- CREATE NEW CUSTOMER -->
          <v-dialog v-model="customerLoading" persistent max-width="600px">

            <template v-slot:activator="{ on }">
              <v-btn color="yellow" dark v-on="on">Create Cart (Existing Customer)</v-btn>
            </template>

            <v-card>
              <v-card-title>
                <h2><b>Load Customer</b></h2>
                <v-spacer></v-spacer>
                <v-text-field
                        v-model="customerSearch"
                        append-icon="search"
                        label="Search"
                        single-line
                        hide-details
                ></v-text-field>
              </v-card-title>

              <v-data-table
                      :headers="this.customerHeaders"
                      :items="this.customerTemplates"
                      :search="customerSearch"
                      :items-per-page="5"
                      class="elevation-1"
              >
                <template v-slot:item.firstName="{ item }">
                  <b> {{ item.customer.firstname }} </b>
                </template>

                <template v-slot:item.lastName="{ item }">
                  <b> {{ item.customer.lastname }} </b>
                </template>

                <template v-slot:item.businessCustomer="{ item }">
                  <b> {{ item.customer.businessCustomer }} </b>
                </template>

                <template v-slot:item.discount="{ item }">
                  <b> {{ item.customer.discount }} </b>
                </template>

                <template v-slot:item.action="{ item }">
                  <v-btn color="success" @click="loadCustomer(item)">
                    <v-icon>mdi-cached</v-icon>
                    Load
                  </v-btn>
                </template>
              </v-data-table>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="customerLoading = false">Cancel</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
      </v-card>

    </div>


  </div>
</template>

<script>
  import Vue from 'vue'
  export default {
    data() {
      return {
        customerSearch: "",
        customerHeaders: [
          { text: 'First name', value: 'firstName' },
          {
            text: 'Last name',
            align: 'left',
            sortable: true,
            value: 'lastName',
          },
          { text: 'Discount', value: 'discount' },
          { text: 'Business Customer', value: 'businessCustomer' },
          { text: 'Action', value: 'action' },
        ],

        cartSearch: "",
        dialog: false,
        customerLoading: false,
        firstname: '',
        lastname: '',
        streetname: '',
        number: '',
        zipcode: '',
        city: '',
        country: '',
        nameRules: [
          v => !!v || 'Name is required',
          v => (v && v.length <= 20) || 'Name must be less than 20 characters',
        ],
        email: '',
        cartListHeaders: [
          { text: 'First name', value: 'firstName' },
          {
            text: 'Last name',
            align: 'left',
            sortable: true,
            value: 'lastName',
          },
          { text: 'Action', value: 'action' },
        ],
      }
    },
    methods: {
      loadCustomer(customer) {
        this.customerLoading = false;
        this.$store.commit('createNewCart', {
                  customer: {
                    firstname: customer.customer.firstname,
                    lastname: customer.customer.lastname,
                    email: customer.customer.email,
                    address: {
                      streetname: customer.customer.streetname,
                      number: customer.customer.number,
                      zipcode: customer.customer.zipcode,
                      city: customer.customer.city,
                      country: customer.customer.country
                    },
                    cart: [],
                  }
                }
        );
        this.businessCustomer = customer.customer.businessCustomer;
      },
      saveCart() {
        this.dialog = false;
        this.$store.commit('saveCart', {
                  customer: {
                    firstname: this.firstname,
                    lastname: this.lastname,
                    email: this.email,
                    address: {
                      streetname: this.streetname,
                      number: this.number,
                      zipcode: this.zipcode,
                      city: this.city,
                      country: this.country
                    },
                    cart: this.cart,
                  }
                }
        )
        this.clearForm();
      },
      createNewCart() {
        this.dialog = false;
        this.businessCustomer = false;
        this.$store.commit('createNewCart', {
                  customer: {
                    firstname: this.firstname,
                    lastname: this.lastname,
                    email: this.email,
                    address: {
                      streetname: this.streetname,
                      number: this.number,
                      zipcode: this.zipcode,
                      city: this.city,
                      country: this.country
                    },
                    cart: [],
                  }
                }
        )
        this.clearForm();
      },
      clearForm() {
        // clear form
        this.firstname = ''
        this.lastname = ''
        this.streetname = ''
        this.number = ''
        this.zipcode = ''
        this.city = ''
        this.country = ''
        this.email = ''
      },
      loadCart(customerId) {
        this.$store.commit('loadCart', customerId);
      },
      deleteCart(customerId) {
        this.$store.commit('deleteCart', customerId);
      },
      beforeMount() {

      },
    },
    computed: {
      customerTemplates () {
        return this.$store.state.customerTemplates
      },
      carts () {
        return this.$store.state.carts;
      },
      cart () {
        return this.$store.state.cart;
      },
      activeCart () {
        return this.$store.state.activeCart;
      },
      businessCustomer: {
        get () {
          return this.$store.state.businessCustomer
        },
        set (value) {
          this.$store.commit('changeCustomerType', value)
        }
      },
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
