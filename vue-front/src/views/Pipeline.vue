<template>
  <div class="pipeline">
    <h1>This is an about page</h1>
    <button v-on:click="getCrumb()"><h1>crumb 가져오기</h1></button>
    <div>
      <a>crumb token = {{token}}</a>
    </div>
    <button v-on:click="buildPr()">test 프로젝트 실행하기</button>
    <div>
      <a>build status = {{result}}</a>
    </div>
    <div>

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
      axios.post('pipeline/build')
      .then(res => {
        this.result=res.data;
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
