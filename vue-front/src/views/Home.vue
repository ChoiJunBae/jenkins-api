<template>
  <div class="home">
    <img src="../assets/jenkins.png">
    <HelloWorld msg="Jenkins with REST API TEST"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld
  }
}
</script>
