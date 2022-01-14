import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '../views/Home.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/pipeline',
    name: 'Pipeline',
    component: () => import('../views/Pipeline.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
