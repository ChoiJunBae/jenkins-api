<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>
      go jenkins page :
      <a href="http://100.0.0.159:8080" target="_blank" rel="noopener">Jenkins UI</a>
    </p>
    <h3>Jenkins pipeline tester</h3>
<!--    <ul>-->
<!--      <li><a href="" target="_blank" rel="noopener">crumb</a></li>-->
<!--      <li><a href="" target="_blank" rel="noopener">router</a></li>-->
<!--    </ul>-->
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
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
