import { createRouter, createWebHistory } from 'vue-router'
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
  },
  {
    path: '/info',
    name: 'Info',
    component: () => import('../views/Info.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
