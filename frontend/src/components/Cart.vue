<template>
  <div class="cart">
    <div v-if='businessCustomer'>
      <v-card>
        <v-card-title>
          <h2><b>Cart</b></h2>

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

          <v-checkbox
                  v-model="businessCustomer"
                  :label="`Business customer`"
          ></v-checkbox>
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
                    class="mr-2"
                    @click="addProduct(item)"
            >
              edit
            </v-icon>
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

      <v-card>
        <v-card-title>
          <h2><b>Cart</b></h2>

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

          <v-checkbox
                  v-model="businessCustomer"
                  :label="`Business customer`"
          ></v-checkbox>
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
                    class="mr-2"
                    @click="addProduct(item)"
            >
              edit
            </v-icon>
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
    return { msg: 'LOGIN MSG' }
  },
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
