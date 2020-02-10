<template>
  <v-app id="app" theme="dark">
    <h1><b>Shopkeeper</b></h1>
    <div id="nav">
      <router-link to="/">                <v-btn color="blue">  <b>Products</b>           </v-btn></router-link> |
      <router-link to="/cart">            <v-btn color="green"> <b>Cart ({{ cartItems }})</b>               </v-btn></router-link> |
      <router-link to="/newCustomer">     <v-btn color="red">   <b>Change customer</b>    </v-btn></router-link> |

      <v-switch
              v-model="businessCustomer"
              :label="`Business customer:`"
      ></v-switch>

    </div>
    <keep-alive>
      <router-view class="view one" name="default"></router-view> 
    </keep-alive>

  </v-app>
</template>

<script>
import Vue from 'vue'
export default {
  name: 'app',
  data () {
    return {
    }
  },
  methods: {
  },
  created() {
    this.$eventHub.$on("change", this.test);
  },
  computed: {
    cartItems () {
      return this.$store.state.cartItems
    },
    businessCustomer: {
      get () {
        return this.$store.state.businessCustomer
      },
      set (value) {
        this.$store.commit('changeCustomerType', value)
      }
    }
  }
}
</script>

<style lang="scss">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 20px;
}

#nav {
  padding: 30px;
  a {
    font-weight: bold;
    color: #2c3e50;
    &.router-link-exact-active {
     color: #42b983;
    }
  }
}
</style>
