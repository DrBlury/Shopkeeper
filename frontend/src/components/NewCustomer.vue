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
              :items="this.customers"
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
            <v-btn>
              <v-icon
                      small
                      class="mr-2"
                      @click="loadCustomer(item.id)"
              >
                add
              </v-icon>
              Load
            </v-btn>
            <v-btn>
              <v-icon
                      small
                      @click="deleteCustomer(item.id)"
              >
                delete
              </v-icon>
              Delete
            </v-btn>


        </template>
      </v-data-table>

    </v-card>




    <!-- CREATING A NEW CUSTOMER -->
    <v-card>
      <v-row justify="center">
        <v-dialog v-model="dialog" persistent max-width="600px">
          <template v-slot:activator="{ on }">
            <v-btn color="primary" dark v-on="on">New customer</v-btn>
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
              <v-btn color="blue darken-1" text @click="createNewCustomer">Create</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>

    </v-card>
  </div>
</template>

<script>
  import Vue from 'vue'
  export default {
    data() {
      return {
        businessCustomer: false,
        cartSearch: "",
        dialog: false,
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
      createNewCustomer() {
        this.dialog = false;
        this.$store.commit('addNewCustomer', {
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
      loadCustomer(customerId) {
        console.log("Loading customer:")
        console.log(customerId);
        this.$store.commit('loadCart', customerId);
      },
      deleteCustomer(customerId) {
        console.log("Delete customer:")
        console.log(customerId);
        this.$store.commit('deleteCustomer', customerId);
      },
      beforeMount() {

      },
    },
    computed: {
      customers () {
        return this.$store.state.customers;
      },
      cart () {
        return this.$store.state.cart;
      }
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
