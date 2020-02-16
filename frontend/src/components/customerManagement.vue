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

      </v-data-table>

    </v-card>


  </div>
</template>

<script>
  import Vue from 'vue'
  export default {
    data() {
      return {}
    },
    methods: {
      createNewCustomerTemplate() {
        this.dialog = false;
        this.$store.commit('createNewCustomerTemplate', {
                  customer: {
                    firstname: this.firstname,
                    lastname: this.lastname,
                    email: this.email,
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
      beforeMount() {

      },
    },
    computed: {
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
