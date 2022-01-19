<template>
  <div class="pipeline">
    <h1>This is an about page</h1>
    <button v-on:click="getCrumb()">crumb 가져오기</button>
    <div>
      <a>crumb token = {{token}}</a>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return{
      token: null,
      result: null,
    }
  },
  methods:{
    getCrumb() {
      axios.get('/pipeline/crumb')
        .then(res => {
          this.token=res.data;
          console.log(res);
        }).catch(err=>{
          console.log(err);
      })
    },
    buildPr(){
      axios.post('/build')
      .then(res => {
        this.result=res;
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    }
  },
  // created() {
  //   this.getCrumb();
  // }
}
</script>
