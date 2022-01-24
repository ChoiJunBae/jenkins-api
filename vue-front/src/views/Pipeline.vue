<template>
  <div class="pipeline">
    <h1>This is an about page</h1>
    <div>
      <table border="1" align="center">
        <th>목록</th>
        <tr style="margin: 25px" v-for="list in lists">
          {{list}}
        </tr>
      </table>
    </div>
    <div style="margin-top:50px">{{token}}</div>
    <select v-model="job">
      <option disabled value="">실행할 프토젝트 선택</option>
      <option v-for="list in lists">{{list}}</option>
    </select>
    <div>
      <button v-on:click="buildPr()"><h4>test 프로젝트 실행하기</h4></button>
      <button v-on:click="getStatus()"><h4>실행 결과 확인하기</h4></button>
    </div>
    <table align="center" border="1">
      <th>result actions</th>
      <tr>build : {{result}}</tr>
      <tr>status : {{status}}</tr>
    </table>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return{
      job:null,
      token: null,
      result: null,
      status:null,
      lists: [],
    }
  },
  created() {
    this.getCrumb();
    this.getList();
  },
  methods:{
    getList: function (){
      axios.get('pipeline/list')
        .then(res=> {
          this.lists=res.data;
          console.log(res);
        }).catch(err=>{
        console.log(err)
      })
    },
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
      var params = new URLSearchParams();
      params.append('jobName', this.job);
      axios.post('pipeline/build', params)
      .then(res => {
        this.result = res.data
        console.log(res);
      }).catch(err=>{
        console.log(err);
      })
    },
    getStatus(){
      axios.get('pipeline/status',{
        params:{
          jobName: this.job
        }
      })
      .then(res=>{
        this.status=res.data;
        console.log(res);
      }).catch(err=>{
        console.log(err)
      })
    }
  }
}
</script>
