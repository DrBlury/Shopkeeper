<template>
  <div class="cart">
    <div v-if='this.$store.businessCustomer'>
      <v-card>
        <v-card-title>
          <h2><b>Business Customer Cart</b></h2>

          <v-spacer></v-spacer>
          <b>All Brutto: {{ this.getAllBusinessBrutto(this.cart) }} €</b>
          <v-spacer></v-spacer>
          <b>All Netto: {{ this.getAllBusinessNetto(this.cart) }} €</b>

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
                :headers="this.businessCartHeaders"
                :items="this.cart"
                :search="search"
                :items-per-page="5"
                class="elevation-1"
        >
          <template v-slot:item.productName="{ item }">
            <b> {{ findById(item.id).productName }} </b>
          </template>

          <template v-slot:item.producer="{ item }">
            <b> {{ findById(item.id).producer }} </b>
          </template>

          <template v-slot:item.businessCustomerBrutto="{ item }">
            <v-chip color="green lighten-1">{{ findById(item.id).businessCustomerBrutto }} €</v-chip>
          </template>

          <template v-slot:item.businessCustomerNetto="{ item }">
            <v-chip color="green lighten-3">{{ findById(item.id).businessCustomerNetto }} €</v-chip>
          </template>

          <template v-slot:item.amount="{ item }">
            <v-chip>{{ item.amount }}</v-chip>
          </template>

          <template v-slot:item.action="{ item }">
            <v-icon
                    small
                    @click="removeProduct(item)"
            >
              delete
            </v-icon>
          </template>
        </v-data-table>
      </v-card>
    </div>
    <div v-else>
      <v-card>
        <v-card-title>
          <h2><b>Private Customer Cart</b></h2>

          <v-spacer></v-spacer>
          <b>All Brutto: {{ this.getAllPrivateBrutto(this.cart) }} €</b>
          <v-spacer></v-spacer>
          <b>All Netto: {{ this.getAllPrivateNetto(this.cart) }} €</b>

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
                :headers="this.privateCartHeaders"
                :items="this.cart"
                :search="cartSearch"
                :items-per-page="5"
                class="elevation-1"
        >
          <template v-slot:item.productName="{ item }">
            <b> {{ findById(item.id).productName }} </b>
          </template>

          <template v-slot:item.producer="{ item }">
            <b> {{ findById(item.id).producer }} </b>
          </template>

          <template v-slot:item.privateCustomerBrutto="{ item }">
            <v-chip color="blue lighten-1">{{ findById(item.id).privateCustomerBrutto }} €</v-chip>
          </template>

          <template v-slot:item.privateCustomerNetto="{ item }">
            <v-chip color="blue lighten-3">{{ findById(item.id).privateCustomerNetto }} €</v-chip>
          </template>

          <template v-slot:item.amount="{ item }">
            <v-chip>{{ item.amount }}</v-chip>
          </template>

          <template v-slot:item.action="{ item }">
            <v-icon
                    small
                    @click="removeProduct(item)"
            >
              delete
            </v-icon>
          </template>
        </v-data-table>

      </v-card>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  export default {
    data() {
      return {
        cartSearch: "",
        privateCartHeaders: [
          { text: 'Producer', value: 'producer' },
          {
            text: 'Product Title',
            align: 'left',
            sortable: false,
            value: 'productName',
          },
          { text: 'Brutto', value: 'privateCustomerBrutto' },
          { text: 'Netto', value: 'privateCustomerNetto' },
          { text: 'Amount', value: 'amount' },
          { text: 'Action', value: 'action' },
        ],
        businessCartHeaders: [
          { text: 'Producer', value: 'producer' },
          {
            text: 'Product Title',
            align: 'left',
            sortable: false,
            value: 'productName',
          },
          { text: 'Brutto', value: 'businessCustomerBrutto' },
          { text: 'Netto', value: 'businessCustomerNetto' },
          { text: 'Amount', value: 'amount' },
          { text: 'Action', value: 'action' },
        ],
        response: "",
        errors: [],
        cartSearch: "",
    }},
    methods: {
      checkout () {
        Vue.axios.get(`/api/generateInvoice`, {
          params: {}
        }).then(response => {
          // JSON responses are automatically parsed.
          this.response = response.data;
        }).catch(e => {
          this.errors.push(e)
        })
      },
      getAllPrivateNetto (cart) {
        var sum = 0;
        for (var i = 0; i < cart.length; i++) {
          sum += this.findById(cart[i].id).privateCustomerNetto * cart[i].amount;
        }
        return sum.toFixed(2);
      },
      getAllPrivateBrutto (cart) {
        var sum = 0;
        for (var i = 0; i < cart.length; i++) {
          sum += this.findById(cart[i].id).privateCustomerBrutto * cart[i].amount;
        }
        return sum.toFixed(2);
      },
      getAllBusinessNetto (cart) {
        var sum = 0;
        for (var i = 0; i < cart.length; i++) {
          sum += this.findById(cart[i].id).businessCustomerNetto * cart[i].amount;
        }
        return sum.toFixed(2);
      },
      getAllBusinessBrutto (cart) {
        var sum = 0;
        for (var i = 0; i < cart.length; i++) {
          sum += this.findById(cart[i].id).businessCustomerBrutto * cart[i].amount;
        }
        return sum.toFixed(2);
      },
      findById (id) {
        for (var i = 0; i < this.products.length; i++) {
          if (this.products[i].id == id) {
            return this.products[i];
          }
        }
      },
      removeProduct (itemToRemove) {
        this.$store.commit('removeCartItem', itemToRemove);
      },
      getColor (stock) {
        if (stock > 5) return 'red'
        else if (stock > 10) return 'orange'
        else return 'green'
      },
      event () {
        this.$eventHub.$emit('change');
      },
    },
    beforeMount() {
    },
    computed: {
      businessCustomer () {
        return this.$store.state.businessCustomer
      },
      cart () {
        return this.$store.state.cart
      },
      cartItems () {
        return this.$store.state.cartItems
      },
      products () {
        return this.$store.state.products
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
