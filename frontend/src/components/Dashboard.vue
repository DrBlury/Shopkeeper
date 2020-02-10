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
      </v-card-title>

      <div v-if='this.businessCustomer'>
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
    }},
    methods: {
      findById (id) {
        for (var i = 0; i < this.products.length; i++) {
          if (this.products[i].id == id) {
            return this.products[i];
          }
        }
      },
      addProduct (itemToAdd) {
        this.$store.commit('addCartItem', itemToAdd);

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
            this.$store.commit('setProducts', response.data);
          }).catch(e => {
            this.errors.push(e)
          })
      },
      event () {
        this.$eventHub.$emit('change');
      },
    },
    beforeMount() {
      this.reloadTable();
    },
    computed: {
      count () {
        return this.$store.state.count
      },
      businessCustomer () {
        return this.$store.state.businessCustomer
      },
      products () {
        return this.$store.state.products
      },
      businessCustomer () {
        return this.$store.state.businessCustomer
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
