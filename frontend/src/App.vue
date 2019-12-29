<template>
  <v-app id="app">
    <div id="nav">
      <router-link to="/">Dashboard</router-link> |
      <router-link to="/login">Login</router-link> |
      <router-link to="/multipage">Multipage</router-link> |
    </div>

    <v-btn @click="loadDashboard" color="primary" dark>
      load Dashboard...
      <v-icon dark right>mdi-cached</v-icon>
    </v-btn>
    {{dashboard}}
<!--
    <router-view class="view one" name="default"></router-view>
    <router-view class="view two" name="a"></router-view>
    <router-view class="view three" name="b"></router-view>
    -->
  </v-app>
</template>

<script>
  import Vue from 'vue'
export default {
  name: 'app',
  data () {
    return {
      dashboard: {},
      person: {name: "Julian", age: "21"}
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
