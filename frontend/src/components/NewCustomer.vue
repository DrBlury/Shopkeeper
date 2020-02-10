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
              :items="this.cartList"
              :search="cartSearch"
              :items-per-page="5"
              class="elevation-1"
      >
        <template v-slot:item.firstName="{ item }">
          <b> {{ findById(item.id).firstName }} </b>
        </template>

        <template v-slot:item.lastName="{ item }">
          <b> {{ findById(item.id).lastName }} </b>
        </template>


        <template v-slot:item.amount="{ item }">
          <v-chip>{{ item.amount }}</v-chip>
        </template>

        <template v-slot:item.action="{ item }">
          <v-icon
                  small
                  class="mr-2"
                  @click="loadCart(item)"
          >
            edit
          </v-icon>
          <v-icon
                  small
                  @click="deleteCart(item)"
          >
            delete
          </v-icon>
        </template>
      </v-data-table>

    </v-card>




    <!-- CREATING A NEW CUSTOMER -->
    <v-card>
      <v-row justify="center">
        <v-dialog v-model="dialog" persistent max-width="600px">
          <template v-slot:activator="{ on }">
            <v-btn color="primary" dark v-on="on">Create new customer</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">Create a new customer</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field label="First name*" required></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                            label="Last name*"
                            required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field label="Email"></v-text-field>
                  </v-col>
                  <v-card-title>
                    <span class="headline">Address</span>
                  </v-card-title>
                  <v-col cols="12">
                    <v-text-field label="Street name"></v-text-field>
                    <v-text-field label="Number"></v-text-field>
                    <v-text-field label="Zipcode"></v-text-field>
                    <v-text-field label="City"></v-text-field>
                    <v-text-field label="Country"></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
              <small>*indicates required field</small>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
              <v-btn color="blue darken-1" text @click="dialog = false">Save</v-btn>
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
        cartList: [],
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
          { text: 'Products in cart', value: 'cartCount' },
          { text: 'Amount', value: 'amount' },
          { text: 'Action', value: 'action' },
        ],
      }
    },
    methods: {
      beforeMount() {
        this.loadCarts();
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
