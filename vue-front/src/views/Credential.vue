<template>
  <div>
    <h1>Create Credential</h1>
    <h4 style="color: gray">jenkins credential 생성을 Rest api 요청을 통해 생성</h4>
    <div align="center">
      <input v-model="username" class="input" style="display: block" placeholder="Username"/>
      <input v-model="password" class="input" style="display: block" placeholder="Password"/>
      <input v-model="id" class="input" style="display: block" placeholder="ID"/>
    </div>
    <div style="margin: 50px">
      <button v-on:click="addCreden()">생성하기</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default{
  data(){
    return{
      username:"",
      password:"",
      id:"",
      result:""
    }
  },
  methods:{
    addCreden(){
      var params = new URLSearchParams();
      params.append("username", this.username);
      params.append("password", this.password);
      params.append("id", this.id);
      axios.post('credential/add', params)
      .then(res=> {
        this.result = res.data
        console.log(res);

      }).catch(err=>{
        console.log(err);
      })
    }
  }
}
</script>

<style>
.input{
  margin: 10px;
  width: 300px;
  height: 50px;
  font-weight: bold;
  padding-left: 25px;
}
</style>
