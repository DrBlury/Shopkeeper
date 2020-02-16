<template>
  <div class="customerManagement">
    <!-- SHOW ALL CARTS-->

    <v-card>
      <v-card-title>
        <h2><b>Customer Management</b></h2>
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
            <v-btn color="success" @click="editCustomer(item)">
              <v-icon>edit</v-icon>
              edit
            </v-btn>
            <v-btn color="error" @click="deleteCustomer(item.id)">
              <v-icon>delete</v-icon>
              Delete
            </v-btn>
        </template>
      </v-data-table>

    </v-card>


    <v-card>
      <v-row justify="center">
        <v-dialog v-model="dialog" persistent max-width="600px">

          <template v-slot:activator="{ on }">
            <v-btn color="primary" dark v-on="on">Create New Customer</v-btn>
          </template>

          <v-card>
            <v-card-title>
              <span class="headline">Create a new customer:</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="firstname" label="First name*" required></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="lastname" label="Last name"></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-text-field v-model="email" label="Email"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-checkbox v-model="business" label="Business Customer?"></v-checkbox>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="discount" label="Discount in %"></v-text-field>
                  </v-col>
                  <v-col cols="12"></v-col>
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
              <div v-if="!editingACustomer">
                <v-btn color="blue darken-1" text @click="createNewCustomer">Create</v-btn>
              </div>
              <div v-else>
                <v-btn color="blue darken-1" text @click="saveCustomer">Save</v-btn>
              </div>
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
        dialog: false,
        editingACustomer: false,
        firstname: '',
        lastname: '',
        streetname: '',
        number: '',
        zipcode: '',
        city: '',
        country: '',
        discount: '',
        business: false,
        nameRules: [
          v => !!v || 'Name is required',
          v => (v && v.length <= 20) || 'Name must be less than 20 characters',
        ],
        email: '',
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
        editingCustomer: null,
      }
    },
    methods: {
      createNewCustomer() {
        this.dialog = false;
        this.$store.commit('createNewCustomer', {
                  customer: {
                    firstname: this.firstname,
                    lastname: this.lastname,
                    email: this.email,
                    businessCustomer: this.business,
                    discount: this.discount,
                    address: {
                      streetname: this.streetname,
                      number: this.number,
                      zipcode: this.zipcode,
                      city: this.city,
                      country: this.country
                    },
                  }
                }
        )
        this.clearForm();
      },
      editCustomer(item) {
        this.editingACustomer = true,
        // Load Customer Form
        this.firstname = item.customer.firstname;
        this.lastname = item.customer.lastname;
        this.streetname = item.customer.streetname;
        this.number = item.customer.number;
        this.zipcode = item.customer.zipcode;
        this.city = item.customer.city;
        this.country = item.customer.country;
        this.email = item.customer.email;
        this.discount = item.customer.discount;
        this.business = item.customer.businessCustomer;
        this.dialog = true;
        this.editingCustomer = item.id;
      },
      saveCustomer () {
        this.$store.commit('updateCustomer', {
          customer: {
            firstname: this.firstname,
            lastname: this.lastname,
            email: this.email,
            businessCustomer: this.business,
            discount: this.discount,
            address: {
              streetname: this.streetname,
              number: this.number,
              zipcode: this.zipcode,
              city: this.city,
              country: this.country
            },
            id: this.editingCustomer
          }
        })
        this.dialog = false;
        this.clearForm();
      },
      deleteCustomer() {

      },
      clearForm() {
        // clear form
        this.firstname = '';
        this.lastname = '';
        this.streetname = '';
        this.number = '';
        this.zipcode = '';
        this.city = '';
        this.country = '';
        this.email = '';
        this.discount = '';
        this.business = false;
      },
      beforeMount() {

      },
    },
    computed: {
      customerTemplates () {
        return this.$store.state.customerTemplates
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
