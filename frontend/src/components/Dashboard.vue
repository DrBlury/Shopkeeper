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
                :items-per-page="15"
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
            <v-btn color="success" @click="modifyCartItem(item, 'add')">
              <v-icon
                      small
                      class="mr-2"
              >
                add
              </v-icon>
              Add to cart
            </v-btn>
          </template>

        </v-data-table>
      </div>
      <div v-else>
        <v-data-table
                :headers="this.privateHeaders"
                :items="this.products"
                :search="search"
                :items-per-page="15"
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
            <v-btn color="success" @click="modifyCartItem(item, 'add')">
              <v-icon
                      small
                      class="mr-2"
              >
                add
              </v-icon>
              Add to cart
            </v-btn>
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

        search: "",
        cartSearch: "",
    }},
    methods: {

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
