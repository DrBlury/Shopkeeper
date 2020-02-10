<template>
  <div class="dashboard">
    <!-- <img src="./../assets/logo.png"> -->
    <v-card>
      <v-card-title>
        <h2><b>Products</b></h2>

        <v-spacer></v-spacer>
        <v-text-field
                v-model="search"
                append-icon="search"
                label="Search"
                single-line
                hide-details
        ></v-text-field>

        <v-checkbox
                v-model="businessCustomer"
                :label="`Business customer`"
        ></v-checkbox>
      </v-card-title>

      <div v-if='businessCustomer'>
        <v-data-table
                :headers="this.businessHeaders"
                :items="this.products"
                :search="search"
                :items-per-page="5"
                class="elevation-1"
        >
          <template v-slot:item.producer="{ item }">
            <b> {{ item.producer }} </b>
          </template>

          <template v-slot:item.productName="{ item }">
            <b> {{ item.productName }} </b>
          </template>

          <template v-slot:item.businessCustomerBrutto="{ item }">
            <v-chip color="green lighten-1">{{ item.businessCustomerBrutto }} €</v-chip>
          </template>

          <template v-slot:item.businessCustomerNetto="{ item }">
            <v-chip color="green lighten-3">{{ item.businessCustomerNetto }} €</v-chip>
          </template>

          <template v-slot:item.stock="{ item }">
            <v-chip :color="getColor(item.stock)" dark>{{ item.stock }}</v-chip>
          </template>

          <template v-slot:item.action="{ item }">
            <v-icon class="mr-2" @click="addProduct(item)">
              add
            </v-icon>
            <v-icon @click="removeProduct(item)">
              delete
            </v-icon>
          </template>

        </v-data-table>
      </div>
      <div v-else>
        <v-data-table
                :headers="this.privateHeaders"
                :items="this.products"
                :search="search"
                :items-per-page="5"
                class="elevation-1"
        >
          <template v-slot:item.productName="{ item }">
            <b> {{ item.productName }} </b>
          </template>

          <template v-slot:item.producer="{ item }">
            <b> {{ item.producer }} </b>
          </template>

          <template v-slot:item.privateCustomerBrutto="{ item }">
            <v-chip color="blue lighten-1">{{ item.privateCustomerBrutto }} €</v-chip>
          </template>

          <template v-slot:item.privateCustomerNetto="{ item }">
            <v-chip color="blue lighten-3">{{ item.privateCustomerNetto }} €</v-chip>
          </template>

          <template v-slot:item.stock="{ item }">
            <v-chip :color="getColor(item.stock)">{{ item.stock }}</v-chip>
          </template>

          <template v-slot:item.action="{ item }">
            <v-icon
                    class="mr-2"
                    @click="addProduct(item)"
            >
              add
            </v-icon>
            <v-icon
                    @click="removeProduct(item)"
            >
              delete
            </v-icon>
          </template>

        </v-data-table>
       </div>
     </v-card>
   </div>
</template>

<script>
  import Vue from 'vue'
  export default {
    data() {
      return {
        cart: [],
        cartItems: 0,
        products: [],
        businessHeaders: [
          { text: 'Producer', value: 'producer' },
          {
            text: 'Product Title',
            align: 'left',
            sortable: false,
            value: 'productName',
          },
          { text: 'Brutto', value: 'businessCustomerBrutto' },
          { text: 'Netto', value: 'businessCustomerNetto' },
          { text: 'Stock', value: 'stock' },
          { text: 'Action', value: 'action' },
        ],
        privateHeaders: [
          { text: 'Producer', value: 'producer' },
          {
            text: 'Product Title',
            align: 'left',
            sortable: false,
            value: 'productName',
          },
          { text: 'Brutto', value: 'privateCustomerBrutto'},
          { text: 'Netto', value: 'privateCustomerNetto' },
          { text: 'Stock', value: 'stock' },
          { text: 'Action', value: 'action' },

        ],
        product: {name: "", price: ""},
        username: "",
        response: "",
        errors: [],
        search: "",
        cartSearch: "",
        businessCustomer: false,
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
      addProduct (itemToAdd) {
        var alreadyAdded = false;
        //Add the item to cart at the frontend
        for (var i = 0; i < this.cart.length; i++) {
          if (this.cart[i].id == itemToAdd.id) {
            this.cart[i].amount++;
            alreadyAdded = true;
          }
        }
        if (!alreadyAdded) {
          this.cart.push({id: itemToAdd.id, amount: 1})
        }
        this.cartItems++;

        Vue.axios.get(`/api/addToCart`, {
          params: {
            id : itemToAdd.id,
            username: this.username,
          }
        }).then(response => {
          // JSON responses are automatically parsed.
          console.log(response.data)
          this.response = response.data;
        }).catch(e => {
          this.errors.push(e)
        })


      },
      removeProduct (itemToRemove) {
        for (var i = 0; i < this.cart.length; i++) {
          if (this.cart[i].id == itemToRemove.id) {
            this.cart[i].amount--;
            this.cartItems--;
            if (this.cart[i].amount <= 0) {
              this.cart.splice(i, 1);
            }
          }
        }
      },
      getColor (stock) {
        if (stock > 5) return 'red'
        else if (stock > 10) return 'orange'
        else return 'green'
      },
      reloadTable () {
        Vue.axios.get(`/api/getProducts`)
          .then(response => {
            // JSON responses are automatically parsed.
            this.response = response.data;
            this.products = response.data;
          }).catch(e => {
            this.errors.push(e)
          })
      },
      event () {
        this.$eventHub.$emit('change');
      },
      sendToBackend () {
        Vue.axios.get(`/api/addBook`, {
          params: {
            book: this.book
          }
        }).then(response => {
          // JSON responses are automatically parsed.
          this.response = response.data;
          this.reloadTable();
        }).catch(e => {
          this.errors.push(e)
        })
      },
    },
    beforeMount() {
      this.reloadTable();
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
