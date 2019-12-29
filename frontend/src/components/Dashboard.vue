<template>
  <div class="dashboard">
    <img src="./../assets/logo.png">

    VUE mit Axios im Frontend / Springboot Backend

    <v-card>
      <v-btn @click="reloadTable" color="primary" dark>
        <v-icon dark right>mdi-cached</v-icon>
      </v-btn>
      <v-btn @click="event" color="primary" dark>
        Event <v-icon dark right>trending_flat</v-icon>
      </v-btn>
      <v-data-table
                :headers="this.headers"
                :items="this.books"
                :items-per-page="5"
                class="elevation-1"
        ></v-data-table>
      <v-text-field
              class = "inputField input-name p-3 styled-input"
              label="Add the Book title here"
              name="Name"
              type="text"
              v-model="book.name"
              color="#01579B"
              box
              required
      />
      <v-text-field
              class = "inputField input-name p-3 styled-input"
              label="Add the Book price here"
              name="Price"
              type="text"
              v-model="book.price"
              color="#01579B"
              box
              required
      />
      <v-btn @click="sendToBackend" class="ma-2" color="primary" dark>
        <v-icon dark right>trending_flat</v-icon>
      </v-btn>
    </v-card>


  </div>
</template>

<script>
  import Vue from 'vue'
  export default {
    data() {
      return {
        books: [],
        headers: [
          {
            text: 'Book Title',
            align: 'left',
            sortable: false,
            value: 'name',
          },
          { text: 'Price', value: 'price' },
        ],
        book: {name: "", price: ""},
        response: "",
        errors: [],
    }},
    methods: {
      reloadTable () {
        Vue.axios.get(`/api/hello`)
          .then(response => {
            // JSON responses are automatically parsed.
            this.response = response.data;
            this.books = response.data;
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
