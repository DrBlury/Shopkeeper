<template>
  <div class="login">
    <img src="./../assets/spring-boot-vuejs-logo.png">
    WILLKOMMEN AUF DER LOGIN SEITE!!!
    <h1>{{ msg }}</h1>
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
