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
    path: '/credential',
    name: 'Credential',
    component: () => import('../views/Credential.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
