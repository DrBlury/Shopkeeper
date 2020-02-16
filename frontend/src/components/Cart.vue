<template>
  <div class="cart">
    <div v-if='this.businessCustomer'>
      <v-card>
        <v-card-title>
          <h2><b>Business Customer Cart</b></h2>

          <v-spacer></v-spacer>
          <b>All Brutto: {{ this.getAllBusinessBrutto(this.cart) }} €</b>
          <v-spacer></v-spacer>
          <b>All Netto: {{ this.getAllBusinessNetto(this.cart) }} €</b>
        </v-card-title>

        <v-data-table
                :headers="this.businessCartHeaders"
                :items="this.cart"
                :items-per-page="5"
                class="elevation-1"
        >
          <template v-slot:item.productName="{ item }">
            <b> {{ item.item.productName }} </b>
          </template>

          <template v-slot:item.producer="{ item }">
            <b> {{ item.item.producer }} </b>
          </template>

          <template v-slot:item.businessCustomerBrutto="{ item }">
            <v-chip color="green lighten-1">{{ item.item.businessCustomerBrutto }} €</v-chip>
          </template>

          <template v-slot:item.businessCustomerNetto="{ item }">
            <v-chip color="green lighten-3">{{ item.item.businessCustomerNetto }} €</v-chip>
          </template>

          <template v-slot:item.amount="{ item }">
            <v-chip>{{ item.amount }}</v-chip>
          </template>

          <template v-slot:item.action="{ item }">
            <v-btn @click="modifyCartItem(item.item, 'remove')">
              <v-icon>remove</v-icon>
            </v-btn>
            <v-btn @click="modifyCartItem(item.item, 'add')">
              <v-icon>add</v-icon>
            </v-btn>
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

        </v-card-title>

        <v-data-table
                :headers="this.privateCartHeaders"
                :items="this.cart"
                :items-per-page="5"
                class="elevation-1"
        >
          <template v-slot:item.productName="{ item }">
            <b> {{ item.item.productName }} </b>
          </template>

          <template v-slot:item.producer="{ item }">
            <b> {{ item.item.producer }} </b>
          </template>

          <template v-slot:item.privateCustomerBrutto="{ item }">
            <v-chip color="blue lighten-1">{{ item.item.privateCustomerBrutto }} €</v-chip>
          </template>

          <template v-slot:item.privateCustomerNetto="{ item }">
            <v-chip color="blue lighten-3">{{ item.item.privateCustomerNetto }} €</v-chip>
          </template>

          <template v-slot:item.amount="{ item }">
            <v-chip>{{ item.amount }}</v-chip>
          </template>

          <template v-slot:item.action="{ item }">
            <v-btn @click="modifyCartItem(item.item, 'remove')">
              <v-icon>remove</v-icon>
            </v-btn>
            <v-btn @click="modifyCartItem(item.item, 'add')">
              <v-icon>add</v-icon>
            </v-btn>
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
      modifyCartItem (item, operation) {
        this.$store.commit(
                'modifyCartItem',
                {
                  item: item,
                  customer: this.activeCustomer,
                  operation: operation
                }
        );
      },
      getAllPrivateNetto (cart) {
        let sum = 0;
        for (let i = 0; i < cart.length; i++) {
          sum += cart[i].item.privateCustomerNetto * cart[i].amount;
        }
        return sum.toFixed(2);
      },
      getAllPrivateBrutto (cart) {
        let sum = 0;
        for (let i = 0; i < cart.length; i++) {
          sum += cart[i].item.privateCustomerBrutto * cart[i].amount;
        }
        return sum.toFixed(2);
      },
      getAllBusinessNetto (cart) {
        let sum = 0;
        for (let i = 0; i < cart.length; i++) {
          sum += cart[i].item.businessCustomerNetto * cart[i].amount;
        }
        return sum.toFixed(2);
      },
      getAllBusinessBrutto (cart) {
        let sum = 0;
        for (let i = 0; i < cart.length; i++) {
          sum += cart[i].item.businessCustomerBrutto * cart[i].amount;
        }
        return sum.toFixed(2);
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
      activeCustomer () {
        return this.$store.state.activeCustomer
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
