<template>
  <v-app id="app" theme="dark">
    <h1><b>Shopkeeper</b></h1>
    <div id="nav">
      <router-link to="/"><v-btn color="blue"><b>Products</b></v-btn></router-link> |
      <router-link to="/cart"><v-btn color="green"><b>Cart</b></v-btn></router-link> |
      <router-link to="/multipage"><b><v-btn color="red" @click="newCart()"><b>New Cart</b></v-btn></b></router-link> |
      <v-btn color="yellow" @click=""><b>Change customer</b></v-btn>
    </div>

    <router-view class="view one" name="default"></router-view>
    <router-view class="view two" name="a"></router-view>
    <router-view class="view three" name="b"></router-view>
  </v-app>
</template>

<script>
  import Vue from 'vue'
export default {
  name: 'app',
  data () {
    return {
      dashboard: {},
    }
  },
  methods: {
    loadDashboard() {
      Vue.axios.get(`/api/loadDashboardVue`)
              .then(response => {
                // JSON responses are automatically parsed.
                this.dashboard = response.data;
              }).catch(e => {
        this.errors.push(e)
      })
    },
    test() {
      alert("worked.");
      console.log("---> test");
    },
  },
  created() {
    this.$eventHub.$on("change", this.test);
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
